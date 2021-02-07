package cn.timelost.hr.service;

import cn.timelost.hr.pojo.PersonalSalary;
import cn.timelost.hr.vo.input.PersonalRewardForm;
import cn.timelost.hr.vo.input.PersonalSalaryForm;
import com.github.pagehelper.PageInfo;

import java.util.Collection;

/**
 * @author: Jyf
 * @Date: 2021/2/7 9:48
 */
public interface PersonalSalaryService {
    PageInfo<PersonalSalary> findAll(int year, int month, String departmentName, int personalId, int pageNum, int pageSize);

//    PageInfo<PersonalSalary> search(int year, int month, int personalId, int pageNum, int pageSize);

    PersonalSalary find(int id);

    void insert(PersonalSalaryForm personalSalaryForm);

    void deleteById(Integer id);

    void deleteByIdIn(Collection<Integer> idList);

    void updateById(Integer id, PersonalSalaryForm personalSalaryForm);
}
