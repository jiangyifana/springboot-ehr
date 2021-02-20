package cn.timelost.hr.dao;
import java.util.Collection;
import java.util.List;

import cn.timelost.hr.vo.UserVo;
import org.apache.ibatis.annotations.Param;

import cn.timelost.hr.pojo.User;

/**
 * @Entity cn.timelost.hr.pojo.User
 */
public interface UserDao {
    /**
     *
     * @mbg.generated 2021-02-18 19:04:09
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated 2021-02-18 19:04:09
     */
    int insert(User record);

    /**
     *
     * @mbg.generated 2021-02-18 19:04:09
     */
    int insertSelective(User record);

    /**
     *
     * @mbg.generated 2021-02-18 19:04:09
     */
    User selectByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated 2021-02-18 19:04:09
     */
    int updateByPrimaryKeySelective(User record);

    /**
     *
     * @mbg.generated 2021-02-18 19:04:09
     */
    int updateByPrimaryKey(User record);

    User selectByUsername(@Param("username") String username);

    List<UserVo> selectAll();

    int deleteByIdIn(@Param("idList") Collection<Integer> idList);
}