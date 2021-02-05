package cn.timelost.hr.service.impl;

import cn.timelost.hr.dao.PersonalRewardDao;
import cn.timelost.hr.enums.ResultEnum;
import cn.timelost.hr.exception.BaseException;
import cn.timelost.hr.pojo.PersonalReward;
import cn.timelost.hr.service.PersonalRewardService;
import cn.timelost.hr.service.PersonalService;
import cn.timelost.hr.vo.PersonalVo;
import cn.timelost.hr.vo.input.PersonalRewardForm;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * @author: Jyf
 * @Date: 2021/2/4 12:17
 */
@Service
public class PersonalRewardServiceImpl implements PersonalRewardService {

    @Resource
    PersonalRewardDao personalRewardDao;
    @Resource
    PersonalService personalService;

    @Override
    public PageInfo<PersonalReward> findAll(String departmentName, int personalId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        if (ObjectUtils.isEmpty(departmentName)) {
            departmentName = null;
        }
        List<PersonalReward> personalRewards = personalRewardDao.selectAll(departmentName, personalId);
        return new PageInfo<>(personalRewards);
    }

    @Override
    public PageInfo<PersonalReward> search(int year, int month, int personalId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<PersonalReward> personalRewards = personalRewardDao.selectAllByDate(year, month, personalId);
        return new PageInfo<>(personalRewards);
    }

    @Override
    public PersonalReward find(int id) {
        PersonalReward personalReward = personalRewardDao.selectByPrimaryKey(id);
        if (ObjectUtils.isEmpty(personalReward)) {
            throw new BaseException(ResultEnum.PERSONAL_REWARD_NOT_EXIST);
        }
        return personalReward;
    }

    @Override
    public void insert(PersonalRewardForm personalRewardForm) {
        PersonalVo personalVo = personalService.find(personalRewardForm.getPersonalId());
        PersonalReward personalReward = new PersonalReward();
        BeanUtils.copyProperties(personalRewardForm, personalReward);
        personalReward.setPersonalName(personalVo.getName());
        personalReward.setDepartmentName(personalVo.getDepartmentName());
        personalReward.setPositionName(personalVo.getPositionName());
        personalRewardDao.insertSelective(personalReward);
    }

    @Override
    public void deleteById(Integer id) {
        PersonalReward personalReward = personalRewardDao.selectByPrimaryKey(id);
        if (ObjectUtils.isEmpty(personalReward)) {
            throw new BaseException(ResultEnum.PERSONAL_REWARD_NOT_EXIST);
        }
        personalRewardDao.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteByIdIn(Collection<Integer> idList) {
        personalRewardDao.deleteByIdIn(idList);
    }

    @Override
    public void updateById(Integer id, PersonalRewardForm personalRewardForm) {
        PersonalReward personalReward = personalRewardDao.selectByPrimaryKey(id);
        if (ObjectUtils.isEmpty(personalReward)) {
            throw new BaseException(ResultEnum.PERSONAL_REWARD_NOT_EXIST);
        }
        BeanUtils.copyProperties(personalRewardForm, personalReward);
        PersonalVo personalVo = personalService.find(personalRewardForm.getPersonalId());
        personalReward.setPersonalName(personalVo.getName());
        personalReward.setDepartmentName(personalVo.getDepartmentName());
        personalReward.setPositionName(personalVo.getPositionName());
        personalRewardDao.updateByPrimaryKeySelective(personalReward);
    }
}
