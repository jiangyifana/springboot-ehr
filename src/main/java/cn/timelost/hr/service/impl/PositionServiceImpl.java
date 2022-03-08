package cn.timelost.hr.service.impl;

import cn.timelost.hr.dao.PositionDao;
import cn.timelost.hr.enums.ResultEnum;
import cn.timelost.hr.exception.BaseException;
import cn.timelost.hr.pojo.Department;
import cn.timelost.hr.pojo.Position;
import cn.timelost.hr.service.DepartmentService;
import cn.timelost.hr.service.PositionService;
import cn.timelost.hr.vo.PositionSelectVo;
import cn.timelost.hr.vo.PositionVo;
import cn.timelost.hr.vo.input.PositionForm;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author: Jyf
 * @Date: 2021/1/25 18:35
 */
@Service
@CacheConfig(cacheNames = "position")
public class PositionServiceImpl implements PositionService {

    @Resource
    PositionDao positionDao;
    @Resource
    DepartmentService departmentService;

    @Override
    @Cacheable(key = "#departmentId+'-'+#positionName+'-'+#workStatus+'-'+#pageNum+'-'+#pageSize")
    public PageInfo<PositionVo> findAll(int pageNum, int pageSize, int departmentId, String positionName) {
        if (ObjectUtils.isEmpty(positionName)) {
            positionName = null;
        }
        if (departmentId != 0) departmentService.find(departmentId);
        PageHelper.startPage(pageNum, pageSize);
        List<PositionVo> positionVos = positionDao.selectAll(departmentId, positionName);
        return new PageInfo<>(positionVos);
    }

    @Override
    @Cacheable(key = "#root.methodName")
    public List<PositionVo> all() {
        return positionDao.selectAll(0, null);
    }

    @Override
    public Position find(int id) {
        Position position = positionDao.selectByPrimaryKey(id);
        if (ObjectUtils.isEmpty(position)) {
            throw new BaseException(ResultEnum.POSITION_NOT_EXIST);
        }
        return position;
    }

    @Override
    @CacheEvict(allEntries = true)
    public void insert(PositionForm positionForm) {
        departmentService.find(positionForm.getDepartmentId());
        Position position = new Position();
        BeanUtils.copyProperties(positionForm, position);
        position.setCreateTime(new Date());
        position.setUpdateTime(new Date());
        positionDao.insertSelective(position);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void deleteById(Integer id) {
        Position position = positionDao.selectByPrimaryKey(id);
        if (ObjectUtils.isEmpty(position)) {
            throw new BaseException(ResultEnum.POSITION_NOT_EXIST);
        }
        positionDao.deleteByPrimaryKey(id);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void deleteByIdIn(Collection<Integer> idList) {
        positionDao.deleteByIdIn(idList);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void updateById(Integer id, PositionForm positionForm) {
        Position position = positionDao.selectByPrimaryKey(id);
        if (ObjectUtils.isEmpty(positionForm.getDepartmentId())) {
            throw new BaseException(ResultEnum.DEPARTMENT_NOT_EXIST);
        }
        departmentService.find(positionForm.getDepartmentId());
        if (ObjectUtils.isEmpty(position)) {
            throw new BaseException(ResultEnum.POSITION_NOT_EXIST);
        }
        BeanUtils.copyProperties(positionForm, position);
        position.setId(id);
        position.setUpdateTime(new Date());
        positionDao.updateByPrimaryKeySelective(position);
    }

    @Override
    public List<PositionSelectVo> findSelect(Integer departmentId) {
        departmentService.find(departmentId);
        return positionDao.selectAllByDepartmentId(departmentId);
    }
}
