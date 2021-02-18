package cn.timelost.hr.dao;

import java.util.Collection;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.timelost.hr.pojo.PersonalReward;

/**
 * @Entity cn.timelost.hr.pojo.PersonalReward
 */
public interface PersonalRewardDao {
    /**
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * @mbg.generated
     */
    int insert(PersonalReward record);

    /**
     * @mbg.generated
     */
    int insertSelective(PersonalReward record);

    /**
     * @mbg.generated
     */
    PersonalReward selectByPrimaryKey(Integer id);

    /**
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(PersonalReward record);

    /**
     * @mbg.generated
     */
    int updateByPrimaryKey(PersonalReward record);

    List<PersonalReward> selectAll(@Param("year") int year, @Param("month") int month,
                                   @Param("departmentName") String departmentName, @Param("personalId") int personalId);

    int deleteByIdIn(@Param("idList") Collection<Integer> idList);
}