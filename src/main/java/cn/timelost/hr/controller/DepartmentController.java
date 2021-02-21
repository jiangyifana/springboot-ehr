package cn.timelost.hr.controller;

import cn.timelost.hr.pojo.Department;
import cn.timelost.hr.service.DepartmentService;
import cn.timelost.hr.vo.DepartmentSelectVo;
import cn.timelost.hr.vo.ResultVo;
import cn.timelost.hr.vo.input.DepartmentForm;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author JYF
 * @since 2021-01-24
 */
@RestController
public class DepartmentController {

    @Resource
    DepartmentService departmentService;

    @GetMapping("/department/list")
    public PageInfo<Department> list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                     @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return departmentService.findAll(page, size);
    }

    @GetMapping("/department/all")
    public List<Department> all() {
        return departmentService.all();
    }

    @PostMapping("/department/search")
    public PageInfo<Department> search(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                       @RequestParam(value = "size", defaultValue = "10") Integer size,
                                       @RequestParam(value = "departmentName") String departmentName) {
        return departmentService.search(departmentName, page, size);
    }

    @GetMapping("/department/select")
    public List<DepartmentSelectVo> selectList() {
        return departmentService.findSelect();
    }

    @GetMapping("/department/{id}")
    public Department findById(@PathVariable Integer id) {
        return departmentService.find(id);
    }

    @PostMapping("/department")
    @RequiresRoles("admin")
    public ResultVo insert(@RequestBody @Valid DepartmentForm department) {
        departmentService.insert(department);
        return ResultVo.success();
    }

    @PutMapping("/department/{id}")
    @RequiresRoles("admin")
    public ResultVo update(@RequestBody DepartmentForm department, @PathVariable Integer id) {
        departmentService.updateById(id, department);
        return ResultVo.success();
    }

    @DeleteMapping("/department/{id}")
    @RequiresRoles("admin")
    public ResultVo delete(@PathVariable Integer id) {
        departmentService.deleteById(id);
        return ResultVo.success();
    }

    @DeleteMapping("/department")
    @RequiresRoles("admin")
    public ResultVo deleteIdIn(@RequestBody Set<Integer> id) {
        departmentService.deleteByIdIn(id);
        return ResultVo.success();
    }
}
