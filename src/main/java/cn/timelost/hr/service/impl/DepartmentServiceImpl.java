package cn.timelost.hr.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.timelost.hr.dao.DepartmentDao;
import cn.timelost.hr.enums.ResultEnum;
import cn.timelost.hr.exception.BaseException;
import cn.timelost.hr.pojo.Department;
import cn.timelost.hr.service.DepartmentService;
import cn.timelost.hr.service.PersonalService;
import cn.timelost.hr.service.PositionService;
import cn.timelost.hr.vo.DepartmentSelectVo;
import cn.timelost.hr.vo.PersonalVo;
import cn.timelost.hr.vo.PositionVo;
import cn.timelost.hr.vo.input.DepartmentForm;

/**
 * @author: Jyf
 * @Date: 2021/1/24 21:52
 */
@Service
@CacheConfig(cacheNames = "department")
public class DepartmentServiceImpl implements DepartmentService {
    
    @Resource
    private DepartmentDao departmentDao;
    @Resource
    private PersonalService personalService;
    @Resource
    private PositionService positionService;
    
    @Override
    @Cacheable(key = "#pageNum+'-'+#pageSize")
    public PageInfo<Department> findAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Department> departments = departmentDao.selectAll();
        return new PageInfo<>(departments);
    }
    
    @Override
    @Cacheable(key = "#root.methodName")
    public List<Department> all() {
        return departmentDao.selectAll();
    }
    
    @Override
    @Cacheable(key = "#root.methodName")
    public List<DepartmentSelectVo> findSelect() {
        return departmentDao.selectAllSelect();
    }
    
    @Override
    @Cacheable(key = "#departmentName+'-'+#pageNum+'-'+#pageSize")
    public PageInfo<Department> search(String departmentName, int pageNum, int pageSize) {
        if (ObjectUtils.isEmpty(departmentName)) {
            throw new BaseException(ResultEnum.DEPARTMENT_NOT_EXIST);
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Department> departments = departmentDao.selectAllByDepartmentNameLike(departmentName);
        return new PageInfo<>(departments);
    }
    
    @Override
    public Department find(int id) {
        Department department = departmentDao.selectByPrimaryKey(id);
        if (ObjectUtils.isEmpty(department)) {
            throw new BaseException(ResultEnum.DEPARTMENT_NOT_EXIST);
        }
        return department;
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public void insert(DepartmentForm record) {
        Department department = new Department();
        BeanUtils.copyProperties(record, department);
        department.setCreateTime(new Date());
        department.setUpdateTime(new Date());
        departmentDao.insertSelective(department);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public void deleteById(Integer id) {
        Department department = departmentDao.selectByPrimaryKey(id);
        if (ObjectUtils.isEmpty(department)) {
            throw new BaseException(ResultEnum.DEPARTMENT_NOT_EXIST);
        }
        //校验该部门有没有被绑定，绑定了无法删除
        final List<PersonalVo> personalAll = personalService.All();
        personalAll.forEach(personalVo -> {
            if (id.equals(personalVo.getDepartmentId())) {
                throw new BaseException(ResultEnum.DEPARTMENT_IS_BIND, "员工：" + personalVo.getName());
            }
        });
        final List<PositionVo> positionAll = positionService.all();
        positionAll.forEach(positionVo -> {
            if (id.equals(positionVo.getDepartmentId())) {
                throw new BaseException(ResultEnum.DEPARTMENT_IS_BIND, "岗位：" + positionVo.getPositionName());
            }
        });
        departmentDao.deleteByPrimaryKey(id);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public void deleteByIdIn(Collection<Integer> idList) {
        //校验该部门有没有被绑定，绑定了无法删除
        final List<PersonalVo> personalAll = personalService.All();
        personalAll.forEach(personalVo -> idList.forEach(id -> {
            if (id.equals(personalVo.getDepartmentId())) {
                throw new BaseException(ResultEnum.DEPARTMENT_IS_BIND, "员工：" + personalVo.getName());
            }
        }));
        final List<PositionVo> positionAll = positionService.all();
        positionAll.forEach(positionVo -> idList.forEach(id -> {
            if (id.equals(positionVo.getDepartmentId())) {
                throw new BaseException(ResultEnum.DEPARTMENT_IS_BIND, "岗位：" + positionVo.getPositionName());
            }
        }));
        departmentDao.deleteByIdIn(idList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public void updateById(Integer id, DepartmentForm record) {
        Department department = departmentDao.selectByPrimaryKey(id);
        if (ObjectUtils.isEmpty(department)) {
            throw new BaseException(ResultEnum.DEPARTMENT_NOT_EXIST);
        }
        BeanUtils.copyProperties(record, department);
        department.setId(id);
        department.setUpdateTime(new Date());
        departmentDao.updateByPrimaryKeySelective(department);
    }
    
}