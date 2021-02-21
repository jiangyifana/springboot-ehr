package cn.timelost.hr.service.impl;

import cn.timelost.hr.dao.PersonalSalaryDao;
import cn.timelost.hr.enums.ResultEnum;
import cn.timelost.hr.exception.BaseException;
import cn.timelost.hr.pojo.PersonalReward;
import cn.timelost.hr.pojo.PersonalSalary;
import cn.timelost.hr.service.PersonalSalaryService;
import cn.timelost.hr.service.PersonalService;
import cn.timelost.hr.vo.PersonalVo;
import cn.timelost.hr.vo.input.PersonalSalaryForm;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

/**
 * @author: Jyf
 * @Date: 2021/2/7 9:58
 */
@Service
@CacheConfig(cacheNames = "salary")
public class PersonalSalaryServiceImpl implements PersonalSalaryService {

    @Resource
    PersonalSalaryDao personalSalaryDao;
    @Resource
    PersonalService personalService;

    @Override
    @Cacheable(key = "#year+'-'+#month+'-'+#departmentName+'-'+#personalId+'-'+#pageNum+'-'+#pageSize")
    public PageInfo<PersonalSalary> findAll(int year, int month, String departmentName, int personalId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        if (ObjectUtils.isEmpty(departmentName)) {
            departmentName = null;
        }
        List<PersonalSalary> personalSalaries = personalSalaryDao.selectAll(year, month, departmentName, personalId);
        return new PageInfo<>(personalSalaries);
    }

    @Override
    @Cacheable(key = "#root.methodName")
    public List<PersonalSalary> all() {
        return personalSalaryDao.selectAll(0, 0, null, 0);
    }

    @Override
    public PersonalSalary find(int id) {
        PersonalSalary personalSalaries = personalSalaryDao.selectByPrimaryKey(id);
        if (ObjectUtils.isEmpty(personalSalaries)) {
            throw new BaseException(ResultEnum.PERSONAL_SALARY_NOT_EXIST);
        }
        return personalSalaries;
    }

    @Override
    @CacheEvict(allEntries = true)
    public void insert(PersonalSalaryForm personalSalaryForm) {
        PersonalVo personalVo = personalService.find(personalSalaryForm.getPersonalId());
        PersonalSalary personalSalary = new PersonalSalary();
        BeanUtils.copyProperties(personalSalaryForm, personalSalary);
        personalSalary.setPersonalName(personalVo.getName());
        personalSalary.setDepartmentName(personalVo.getDepartmentName());
        personalSalary.setPositionName(personalVo.getPositionName());
        BigDecimal bigDecimalSum = getBigDecimalSum(personalSalaryForm.getBasisSalary(), personalSalaryForm.getSubsidySalary(),
                personalSalaryForm.getSocialSalary(), personalSalaryForm.getProvidentFund(),
                personalSalaryForm.getBonus());
        personalSalary.setAllSalary(bigDecimalSum);
        personalSalaryDao.insertSelective(personalSalary);
    }

    public static BigDecimal getBigDecimalSum(BigDecimal i, BigDecimal... arg) {
        BigDecimal sum = i;
        for (BigDecimal b : arg) {
            sum = sum.add(b);
        }
        return sum;
    }

    @Override
    @CacheEvict(allEntries = true)
    public void deleteById(Integer id) {
        PersonalSalary personalSalary = personalSalaryDao.selectByPrimaryKey(id);
        if (ObjectUtils.isEmpty(personalSalary)) {
            throw new BaseException(ResultEnum.PERSONAL_SALARY_NOT_EXIST);
        }
        personalSalaryDao.deleteByPrimaryKey(id);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void deleteByIdIn(Collection<Integer> idList) {
        personalSalaryDao.deleteByIdIn(idList);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void updateById(Integer id, PersonalSalaryForm personalSalaryForm) {
        PersonalSalary personalSalary = personalSalaryDao.selectByPrimaryKey(id);
        if (ObjectUtils.isEmpty(personalSalary)) {
            throw new BaseException(ResultEnum.PERSONAL_SALARY_NOT_EXIST);
        }
        BeanUtils.copyProperties(personalSalaryForm, personalSalary);
        PersonalVo personalVo = personalService.find(personalSalaryForm.getPersonalId());
        personalSalary.setPersonalName(personalVo.getName());
        personalSalary.setDepartmentName(personalVo.getDepartmentName());
        personalSalary.setPositionName(personalVo.getPositionName());
        BigDecimal bigDecimalSum = getBigDecimalSum(personalSalaryForm.getBasisSalary(), personalSalaryForm.getSubsidySalary(),
                personalSalaryForm.getSocialSalary(), personalSalaryForm.getProvidentFund(),
                personalSalaryForm.getBonus());
        personalSalary.setAllSalary(bigDecimalSum);
        personalSalaryDao.updateByPrimaryKeySelective(personalSalary);
    }
}
