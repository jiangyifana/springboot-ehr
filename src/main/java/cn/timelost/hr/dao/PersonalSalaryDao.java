package cn.timelost.hr.dao;

import java.util.Collection;

import java.util.Date;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.timelost.hr.pojo.PersonalSalary;

/**
 * @Entity cn.timelost.hr.pojo.PersonalSalary
 */
public interface PersonalSalaryDao {
    /**
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * @mbg.generated
     */
    int insert(PersonalSalary record);

    /**
     * @mbg.generated
     */
    int insertSelective(PersonalSalary record);

    /**
     * @mbg.generated
     */
    PersonalSalary selectByPrimaryKey(Integer id);

    /**
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(PersonalSalary record);

    /**
     * @mbg.generated
     */
    int updateByPrimaryKey(PersonalSalary record);

    List<PersonalSalary> selectAll(
            @Param("year") Integer year, @Param("month") Integer month,
            @Param("departmentName") String departmentName, @Param("personalId") Integer personalId);

    int deleteByIdIn(@Param("idList") Collection<Integer> idList);
}