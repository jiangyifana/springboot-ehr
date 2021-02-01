package cn.timelost.hr.dao;
import cn.timelost.hr.vo.PositionSelectVo;
import cn.timelost.hr.vo.PositionVo;
import org.apache.ibatis.annotations.Param;
import java.util.Collection;
import java.util.List;

import cn.timelost.hr.pojo.Position;

/**
 * @Entity cn.timelost.hr.pojo.Position
 */
public interface PositionDao {
    /**
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * @mbg.generated
     */
    int insert(Position record);

    /**
     * @mbg.generated
     */
    int insertSelective(Position record);

    /**
     * @mbg.generated
     */
    Position selectByPrimaryKey(Integer id);

    /**
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Position record);

    /**
     * @mbg.generated
     */
    int updateByPrimaryKey(Position record);

    List<PositionVo> selectAll(@Param("departmentId") Integer departmentId);

    List<PositionVo> selectAllByPositionNameLike(@Param("positionName") String positionName);

    int deleteByIdIn(@Param("idList") Collection<Integer> idList);

    List<PositionSelectVo> selectAllByDepartmentId(@Param("departmentId") Integer departmentId);
}