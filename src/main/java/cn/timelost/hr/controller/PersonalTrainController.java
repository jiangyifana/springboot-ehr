package cn.timelost.hr.controller;

import cn.timelost.hr.pojo.PersonalTrain;
import cn.timelost.hr.service.PersonalTrainService;
import cn.timelost.hr.vo.ResultVo;
import cn.timelost.hr.vo.input.PersonalTrainForm;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author: Jyf
 * @Date: 2021/2/2 17:19
 */
@RestController
public class PersonalTrainController {

    @Resource
    PersonalTrainService personalTrainService;

    @GetMapping("/train/list")
    public PageInfo<PersonalTrain> list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                        @RequestParam(value = "size", defaultValue = "10") Integer size,
                                        @RequestParam(value = "departmentName", required = false) String departmentName,
                                        @RequestParam(value = "personalId", defaultValue = "0") Integer personalId,
                                        @RequestParam(value = "beginDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date beginDate,
                                        @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        return personalTrainService.findAll(page, size, departmentName, personalId, beginDate, endDate);
    }

    @GetMapping("/train/all")
    public List<PersonalTrain> all() {
        return personalTrainService.all();
    }

    @GetMapping("/train/{id}")
    public PersonalTrain findById(@PathVariable Integer id) {
        return personalTrainService.find(id);
    }

    @PostMapping("/train")
    @RequiresRoles("admin")
    public ResultVo insert(@RequestBody @Valid PersonalTrainForm personalTrainForm) {
        personalTrainService.insert(personalTrainForm);
        return ResultVo.success();
    }

    @PutMapping("/train/{id}")
    @RequiresRoles("admin")
    public ResultVo update(@RequestBody @Valid PersonalTrainForm personalTrainForm, @PathVariable Integer id) {
        personalTrainService.updateById(id, personalTrainForm);
        return ResultVo.success();
    }

    @DeleteMapping("/train/{id}")
    @RequiresRoles("admin")
    public ResultVo delete(@PathVariable Integer id) {
        personalTrainService.deleteById(id);
        return ResultVo.success();
    }

    @DeleteMapping("/train")
    @RequiresRoles("admin")
    public ResultVo deleteIdIn(@RequestBody Set<Integer> id) {
        personalTrainService.deleteByIdIn(id);
        return ResultVo.success();
    }
}
