package cn.timelost.hr.dao;
import java.util.Collection;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.timelost.hr.pojo.Recruitment;

/**
 * @Entity cn.timelost.hr.pojo.Recruitment
 */
public interface RecruitmentDao {
    /**
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * @mbg.generated
     */
    int insert(Recruitment record);

    /**
     * @mbg.generated
     */
    int insertSelective(Recruitment record);

    /**
     * @mbg.generated
     */
    Recruitment selectByPrimaryKey(Integer id);

    /**
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Recruitment record);

    /**
     * @mbg.generated
     */
    int updateByPrimaryKey(Recruitment record);

    List<Recruitment> selectAll(@Param("departmentName") String departmentName, @Param("recruitStatus") Integer recruitStatus);

    int deleteByIdIn(@Param("idList") Collection<Integer> idList);
}