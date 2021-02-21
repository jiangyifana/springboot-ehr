package cn.timelost.hr.service.impl;

import cn.timelost.hr.dao.PersonalDao;
import cn.timelost.hr.enums.ResultEnum;
import cn.timelost.hr.exception.BaseException;
import cn.timelost.hr.pojo.Personal;
import cn.timelost.hr.service.DepartmentService;
import cn.timelost.hr.service.PersonalService;
import cn.timelost.hr.service.PositionService;
import cn.timelost.hr.vo.PersonalSelectVo;
import cn.timelost.hr.vo.PersonalVo;
import cn.timelost.hr.vo.input.PersonalForm;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: Jyf
 * @Date: 2021/1/28 17:51
 */
@Service
@CacheConfig(cacheNames = "personal")
public class PersonalServiceImpl implements PersonalService {

    @Resource
    PersonalDao personalDao;
    @Resource
    DepartmentService departmentService;
    @Resource
    PositionService positionService;

    @Override
    @Cacheable(key = "#departmentId+'-'+#personalName+'-'+#workStatus+'-'+#pageNum+'-'+#pageSize")
    public PageInfo<PersonalVo> findAll(int pageNum, int pageSize, int departmentId, String personalName, int workStatus) {
        PageHelper.startPage(pageNum, pageSize);
        if (ObjectUtils.isEmpty(personalName)) {
            personalName = null;
        }
        List<PersonalVo> personals = personalDao.selectAll(departmentId, personalName, workStatus);
        return new PageInfo<>(personals);
    }

    @Override
    @Cacheable(key = "#root.methodName")
    public List<PersonalVo> All() {
        return personalDao.selectAll(0, null, 0);
    }

    @Override
    @Cacheable(key = "#root.methodName")
    public List<PersonalSelectVo> findSelect() {
        List<PersonalVo> personalVos = personalDao.selectAll(0, null, 0);
        List<PersonalSelectVo> collect = personalVos.stream().map(e -> {
            PersonalSelectVo personalSelectVo = new PersonalSelectVo();
            BeanUtils.copyProperties(e, personalSelectVo);
            return personalSelectVo;
        }).collect(Collectors.toList());
        return collect;
    }

    @Override
    public PersonalVo find(int id) {
        PersonalVo personalVo = personalDao.selectByPrimaryKey(id);
        if (ObjectUtils.isEmpty(personalVo)) {
            throw new BaseException(ResultEnum.PERSONAL_NOT_EXIST);
        }
        return personalVo;
    }

    @Override
    @CacheEvict(allEntries = true)
    public void insert(PersonalForm personalForm) {
        if (!ObjectUtils.isEmpty(personalForm.getDepartmentId())) {
            departmentService.find(personalForm.getDepartmentId());
        }
        if (!ObjectUtils.isEmpty(personalForm.getPositionId())) {
            positionService.find(personalForm.getPositionId());
        }
        Personal personal = new Personal();
        BeanUtils.copyProperties(personalForm, personal);
        personalDao.insertSelective(personal);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void deleteById(Integer id) {
        PersonalVo personal = personalDao.selectByPrimaryKey(id);
        if (ObjectUtils.isEmpty(personal)) {
            throw new BaseException(ResultEnum.PERSONAL_NOT_EXIST);
        }
        personalDao.deleteByPrimaryKey(id);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void deleteByIdIn(Collection<Integer> idList) {
        personalDao.deleteByIdIn(idList);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void updateById(Integer id, PersonalForm personalForm) {
        PersonalVo personalVo = personalDao.selectByPrimaryKey(id);
        if (ObjectUtils.isEmpty(personalVo)) {
            throw new BaseException(ResultEnum.POSITION_NOT_EXIST);
        }
        Personal personal = new Personal();
        BeanUtils.copyProperties(personalVo, personal);
        BeanUtils.copyProperties(personalForm, personal);
        personal.setId(id);
        personalDao.updateByPrimaryKeySelective(personal);
    }
}
