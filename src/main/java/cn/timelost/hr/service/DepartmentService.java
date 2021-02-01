package cn.timelost.hr.service;

import cn.timelost.hr.pojo.Department;
import cn.timelost.hr.vo.DepartmentSelectVo;
import cn.timelost.hr.vo.input.DepartmentForm;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @author: Jyf
 * @Date: 2021/1/24 21:50
 */
public interface DepartmentService {

    PageInfo<Department> findAll(int pageNum, int pageSize);

    List<DepartmentSelectVo> findSelect();

    PageInfo<Department> search(String departmentName, int pageNum, int pageSize);

    Department find(int id);

    void insert(DepartmentForm departmentForm);

    void deleteById(Integer id);

    void deleteByIdIn(Collection<Integer> idList);

    void updateById(Integer id, DepartmentForm departmentForm);
}
