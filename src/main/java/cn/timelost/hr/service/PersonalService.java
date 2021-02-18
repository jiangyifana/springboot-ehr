package cn.timelost.hr.service;

import cn.timelost.hr.vo.PersonalSelectVo;
import cn.timelost.hr.vo.PersonalVo;
import cn.timelost.hr.vo.input.PersonalForm;
import com.github.pagehelper.PageInfo;

import java.util.Collection;
import java.util.List;

/**
 * @author: Jyf
 * @Date: 2021/1/28 17:37
 */
public interface PersonalService {

    PageInfo<PersonalVo> findAll(int pageNum, int pageSize, int departmentId, String personalName, int workStatus);
    List<PersonalVo> All();

    List<PersonalSelectVo> findSelect();

    PersonalVo find(int id);

    void insert(PersonalForm personalForm);

    void deleteById(Integer id);

    void deleteByIdIn(Collection<Integer> idList);

    void updateById(Integer id, PersonalForm personalForm);
}
