package cn.timelost.hr.service;

import cn.timelost.hr.pojo.PersonalSalary;
import cn.timelost.hr.vo.input.PersonalRewardForm;
import cn.timelost.hr.vo.input.PersonalSalaryForm;
import com.github.pagehelper.PageInfo;

import java.util.Collection;
import java.util.List;

/**
 * @author: Jyf
 * @Date: 2021/2/7 9:48
 */
public interface PersonalSalaryService {
    PageInfo<PersonalSalary> findAll(int year, int month, String departmentName, int personalId, int pageNum, int pageSize);
    List<PersonalSalary> all();

    PersonalSalary find(int id);

    void insert(PersonalSalaryForm personalSalaryForm);

    void deleteById(Integer id);

    void deleteByIdIn(Collection<Integer> idList);

    void updateById(Integer id, PersonalSalaryForm personalSalaryForm);
}
