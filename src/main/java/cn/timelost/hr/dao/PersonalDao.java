package cn.timelost.hr.dao;

import cn.timelost.hr.pojo.Personal;
import cn.timelost.hr.vo.PersonalVo;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @Entity cn.timelost.hr.pojo.Personal
 */
public interface PersonalDao {
    /**
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * @mbg.generated
     */
    int insert(Personal record);

    /**
     * @mbg.generated
     */
    int insertSelective(Personal record);

    /**
     * @mbg.generated
     */
    PersonalVo selectByPrimaryKey(Integer id);

    /**
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Personal record);

    /**
     * @mbg.generated
     */
    int updateByPrimaryKey(Personal record);

    List<PersonalVo> selectAll(@Param("departmentId") Integer departmentId, @Param("personalName") String personalName, @Param("workStatus") int workStatus);

    int deleteByIdIn(@Param("idList") Collection<Integer> idList);
}