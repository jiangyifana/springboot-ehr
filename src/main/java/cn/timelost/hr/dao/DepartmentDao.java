package cn.timelost.hr.dao;
import cn.timelost.hr.vo.DepartmentSelectVo;
import org.apache.ibatis.annotations.Param;
import java.util.Collection;
import java.util.List;

import cn.timelost.hr.pojo.Department;

/**
 * @Entity cn.timelost.hr.pojo.Department
 */
public interface DepartmentDao {
    /**
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * @mbg.generated
     */
    int insert(Department record);

    /**
     * @mbg.generated
     */
    int insertSelective(Department record);

    /**
     * @mbg.generated
     */
    Department selectByPrimaryKey(Integer id);

    /**
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Department record);

    /**
     * @mbg.generated
     */
    int updateByPrimaryKey(Department record);

    List<Department> selectAll();

    int deleteByIdIn(@Param("idList") Collection<Integer> idList);

    List<Department> selectAllByDepartmentNameLike(@Param("departmentName") String departmentName);

    List<DepartmentSelectVo> selectAllSelect();
}