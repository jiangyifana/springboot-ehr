package cn.timelost.hr.service.impl;

import cn.timelost.hr.dao.RecruitmentDao;
import cn.timelost.hr.enums.ResultEnum;
import cn.timelost.hr.exception.BaseException;
import cn.timelost.hr.pojo.PersonalSalary;
import cn.timelost.hr.pojo.Recruitment;
import cn.timelost.hr.service.PersonalService;
import cn.timelost.hr.service.RecruitmentService;
import cn.timelost.hr.vo.PersonalVo;
import cn.timelost.hr.vo.input.RecruitmentForm;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

/**
 * @author: Jyf
 * @Date: 2021/2/7 19:44
 */
@Service
public class RecruitmentServiceImpl implements RecruitmentService {

    @Resource
    RecruitmentDao recruitmentDao;
    @Resource
    PersonalService personalService;

    @Override
    public PageInfo<Recruitment> findAll(String departmentName, int recruitStatus, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        if (ObjectUtils.isEmpty(departmentName)) {
            departmentName = null;
        }
        List<Recruitment> personalSalaries = recruitmentDao.selectAll(departmentName, recruitStatus);
        return new PageInfo<>(personalSalaries);
    }

    @Override
    public Recruitment find(int id) {
        Recruitment recruitment = recruitmentDao.selectByPrimaryKey(id);
        if (ObjectUtils.isEmpty(recruitment)) {
            throw new BaseException(ResultEnum.RECRUIT_NOT_EXIST);
        }
        return recruitment;
    }

    @Override
    public void insert(RecruitmentForm recruitmentForm) {
        Recruitment recruitment = new Recruitment();
        BeanUtils.copyProperties(recruitmentForm,recruitment);
        recruitmentDao.insertSelective(recruitment);
    }

    @Override
    public void deleteById(Integer id) {
        Recruitment recruitment = recruitmentDao.selectByPrimaryKey(id);
        if (ObjectUtils.isEmpty(recruitment)) {
            throw new BaseException(ResultEnum.RECRUIT_NOT_EXIST);
        }
        recruitmentDao.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteByIdIn(Collection<Integer> idList) {
        recruitmentDao.deleteByIdIn(idList);
    }

    @Override
    public void updateById(Integer id, RecruitmentForm recruitmentForm) {
        Recruitment recruitment = recruitmentDao.selectByPrimaryKey(id);
        if (ObjectUtils.isEmpty(recruitment)) {
            throw new BaseException(ResultEnum.RECRUIT_NOT_EXIST);
        }
        BeanUtils.copyProperties(recruitmentForm,recruitment);
        recruitmentDao.updateByPrimaryKeySelective(recruitment);
    }
}