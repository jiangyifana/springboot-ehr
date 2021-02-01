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
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * @author: Jyf
 * @Date: 2021/1/25 18:35
 */
@Service
public class PositionServiceImpl implements PositionService {

    @Resource
    PositionDao positionDao;
    @Resource
    DepartmentService departmentService;

    @Override
    public PageInfo<PositionVo> findAll(int pageNum, int pageSize, int departmentId) {
        List<PositionVo> positions;
        if (departmentId == 0) {
            PageHelper.startPage(pageNum, pageSize);
            positions = positionDao.selectAll(null);
        } else {
            departmentService.find(departmentId);
            PageHelper.startPage(pageNum, pageSize);
            positions = positionDao.selectAll(departmentId);
        }
        return new PageInfo<>(positions);
    }

    @Override
    public PageInfo<PositionVo> search(String positionName, int pageNum, int pageSize) {
        if (ObjectUtils.isEmpty(positionName)) {
            throw new BaseException(ResultEnum.POSITION_NOT_EXIST);
        }
        PageHelper.startPage(pageNum, pageSize);
        List<PositionVo> positionVos = positionDao.selectAllByPositionNameLike(positionName);
        return new PageInfo<>(positionVos);
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
    public void insert(PositionForm positionForm) {
        departmentService.find(positionForm.getDepartmentId());
        Position position = new Position();
        BeanUtils.copyProperties(positionForm, position);
        positionDao.insertSelective(position);
    }

    @Override
    public void deleteById(Integer id) {
        Position position = positionDao.selectByPrimaryKey(id);
        if (ObjectUtils.isEmpty(position)) {
            throw new BaseException(ResultEnum.POSITION_NOT_EXIST);
        }
        positionDao.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteByIdIn(Collection<Integer> idList) {
        positionDao.deleteByIdIn(idList);
    }

    @Override
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
        positionDao.updateByPrimaryKeySelective(position);
    }

    @Override
    public List<PositionSelectVo> findSelect(Integer departmentId) {
        departmentService.find(departmentId);
        return positionDao.selectAllByDepartmentId(departmentId);
    }
}
