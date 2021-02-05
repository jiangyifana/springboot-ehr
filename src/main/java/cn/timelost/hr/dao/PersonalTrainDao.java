package cn.timelost.hr.dao;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import java.util.Date;

import cn.timelost.hr.pojo.PersonalTrain;

/**
 * @Entity cn.timelost.hr.pojo.PersonalTrain
 */
public interface PersonalTrainDao {
    /**
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * @mbg.generated
     */
    int insert(PersonalTrain record);

    /**
     * @mbg.generated
     */
    int insertSelective(PersonalTrain record);

    /**
     * @mbg.generated
     */
    PersonalTrain selectByPrimaryKey(Integer id);

    /**
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(PersonalTrain record);

    /**
     * @mbg.generated
     */
    int updateByPrimaryKey(PersonalTrain record);

    List<PersonalTrain> selectAllByBeginDateAndEndDate(@Param("beginDate") Date beginDate, @Param("endDate") Date endDate);

    List<PersonalTrain> selectAllByDepartmentName(@Param("departmentName") String departmentName, @Param("personalId") Integer personalId);

    int deleteByIdIn(@Param("idList") Collection<Integer> idList);
}