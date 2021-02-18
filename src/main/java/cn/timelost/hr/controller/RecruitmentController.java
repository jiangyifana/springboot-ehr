package cn.timelost.hr.controller;

import cn.timelost.hr.pojo.Recruitment;
import cn.timelost.hr.service.RecruitmentService;
import cn.timelost.hr.vo.ResultVo;
import cn.timelost.hr.vo.input.RecruitmentForm;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * @author: Jyf
 * @Date: 2021/2/7 21:07
 */
@RestController
public class RecruitmentController {
    @Resource
    RecruitmentService recruitmentService;

    @GetMapping("/recruit/list")
    public PageInfo<Recruitment> list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                      @RequestParam(value = "size", defaultValue = "10") Integer size,
                                      @RequestParam(value = "departmentName", required = false) String departmentName,
                                      @RequestParam(value = "recruitStatus", defaultValue = "0") Integer recruitStatus) {
        return recruitmentService.findAll(departmentName, recruitStatus, page, size);
    }
    @GetMapping("/recruit/all")
    public List<Recruitment> list() {
        return recruitmentService.all();
    }

    @GetMapping("/recruit/{id}")
    public Recruitment findById(@PathVariable Integer id) {
        return recruitmentService.find(id);
    }

    @PostMapping("/recruit")
    public ResultVo insert(@RequestBody @Valid RecruitmentForm recruitmentForm) {
        recruitmentService.insert(recruitmentForm);
        return ResultVo.success();
    }

    @PutMapping("/recruit/{id}")
    public ResultVo update(@RequestBody RecruitmentForm recruitmentForm, @PathVariable Integer id) {
        recruitmentService.updateById(id, recruitmentForm);
        return ResultVo.success();
    }

    @DeleteMapping("/recruit/{id}")
    public ResultVo delete(@PathVariable Integer id) {
        recruitmentService.deleteById(id);
        return ResultVo.success();
    }

    @DeleteMapping("/recruit")
    public ResultVo deleteIdIn(@RequestBody Set<Integer> id) {
        recruitmentService.deleteByIdIn(id);
        return ResultVo.success();
    }
}
