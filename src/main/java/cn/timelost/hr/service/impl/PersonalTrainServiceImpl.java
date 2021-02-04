package cn.timelost.hr.service.impl;

import cn.timelost.hr.dao.PersonalTrainDao;
import cn.timelost.hr.enums.ResultEnum;
import cn.timelost.hr.exception.BaseException;
import cn.timelost.hr.pojo.Department;
import cn.timelost.hr.pojo.Personal;
import cn.timelost.hr.pojo.PersonalTrain;
import cn.timelost.hr.pojo.Position;
import cn.timelost.hr.service.DepartmentService;
import cn.timelost.hr.service.PersonalService;
import cn.timelost.hr.service.PersonalTrainService;
import cn.timelost.hr.service.PositionService;
import cn.timelost.hr.vo.PersonalVo;
import cn.timelost.hr.vo.input.PersonalTrainForm;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author: Jyf
 * @Date: 2021/2/2 16:51
 */
@Service
public class PersonalTrainServiceImpl implements PersonalTrainService {

    @Resource
    PersonalTrainDao personalTrainDao;
//    @Resource
//    PositionService positionService;
//    @Resource
//    DepartmentService departmentService;
    @Resource
    PersonalService personalService;

    @Override
    public PageInfo<PersonalTrain> findAll(int pageNum, int pageSize, String departmentName) {
        PageHelper.startPage(pageNum, pageSize);
        List<PersonalTrain> personalTrains;
        if (ObjectUtils.isEmpty(departmentName)) {
            personalTrains = personalTrainDao.selectAllByDepartmentName(null);
        } else {
            personalTrains = personalTrainDao.selectAllByDepartmentName(departmentName);
        }
        return new PageInfo<>(personalTrains);
    }

    @Override
    public PageInfo<PersonalTrain> searchByDate(Date beginDate, Date endDate, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<PersonalTrain> personalTrains = personalTrainDao.selectAllByBeginDateAndEndDate(beginDate, endDate);
        return new PageInfo<>(personalTrains);
    }

    @Override
    public PersonalTrain find(int id) {
        PersonalTrain personalTrain = personalTrainDao.selectByPrimaryKey(id);
        if (ObjectUtils.isEmpty(personalTrain)) {
            throw new BaseException(ResultEnum.PERSONAL_TRAIN_NOT_EXIST);
        }
        return personalTrain;
    }

    @Override
    public void insert(PersonalTrainForm personalTrainForm) {
        PersonalVo personalVo = personalService.find(personalTrainForm.getPersonalId());
        PersonalTrain personalTrain = new PersonalTrain();
        BeanUtils.copyProperties(personalTrainForm, personalTrain);
        personalTrain.setPersonalName(personalVo.getName());
        personalTrain.setPositionName(personalVo.getPositionName());
        personalTrain.setDepartmentName(personalVo.getDepartmentName());
        personalTrainDao.insertSelective(personalTrain);
    }

    @Override
    public void deleteById(Integer id) {
        PersonalTrain personalTrain = personalTrainDao.selectByPrimaryKey(id);
        if (ObjectUtils.isEmpty(personalTrain)) {
            throw new BaseException(ResultEnum.PERSONAL_TRAIN_NOT_EXIST);
        }
        personalTrainDao.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteByIdIn(Collection<Integer> idList) {
        personalTrainDao.deleteByIdIn(idList);
    }

    @Override
    public void updateById(Integer id, PersonalTrainForm personalTrainForm) {
        PersonalTrain personalTrain = personalTrainDao.selectByPrimaryKey(id);
        if (ObjectUtils.isEmpty(personalTrain)) {
            throw new BaseException(ResultEnum.PERSONAL_TRAIN_NOT_EXIST);
        }
        BeanUtils.copyProperties(personalTrainForm, personalTrain);
        PersonalVo personalVo = personalService.find(personalTrainForm.getPersonalId());
        personalTrain.setPersonalName(personalVo.getName());
        personalTrain.setPositionName(personalVo.getPositionName());
        personalTrain.setDepartmentName(personalVo.getDepartmentName());
        personalTrainDao.updateByPrimaryKeySelective(personalTrain);
    }
}
