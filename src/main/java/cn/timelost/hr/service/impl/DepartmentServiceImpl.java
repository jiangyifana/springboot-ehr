package cn.timelost.hr.service.impl;

import cn.timelost.hr.dao.DepartmentDao;
import cn.timelost.hr.enums.ResultEnum;
import cn.timelost.hr.exception.BaseException;
import cn.timelost.hr.pojo.Department;
import cn.timelost.hr.service.DepartmentService;
import cn.timelost.hr.vo.DepartmentSelectVo;
import cn.timelost.hr.vo.input.DepartmentForm;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * @author: Jyf
 * @Date: 2021/1/24 21:52
 */
@Service
@CacheConfig(cacheNames = "department")
public class DepartmentServiceImpl implements DepartmentService {

    @Resource
    DepartmentDao departmentDao;

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
        departmentDao.insertSelective(department);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void deleteById(Integer id) {
        Department department = departmentDao.selectByPrimaryKey(id);
        if (ObjectUtils.isEmpty(department)) {
            throw new BaseException(ResultEnum.DEPARTMENT_NOT_EXIST);
        }
        departmentDao.deleteByPrimaryKey(id);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void deleteByIdIn(Collection<Integer> idList) {
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
        departmentDao.updateByPrimaryKeySelective(department);
    }

}