package cn.timelost.hr.controller;

import cn.timelost.hr.pojo.PersonalReward;
import cn.timelost.hr.service.PersonalRewardService;
import cn.timelost.hr.vo.ResultVo;
import cn.timelost.hr.vo.input.PersonalRewardForm;
import com.github.pagehelper.PageInfo;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author: Jyf
 * @Date: 2021/2/4 17:25
 */
@RestController
public class PersonalRewardController {

    @Resource
    PersonalRewardService personalRewardService;

    @GetMapping("/reward/list")
    public PageInfo<PersonalReward> list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size,
                                         @RequestParam(value = "departmentName", required = false) String departmentName,
                                         @RequestParam(value = "personalId", defaultValue = "0") Integer personalId,
                                         @RequestParam(value = "year", defaultValue = "0") Integer year,
                                         @RequestParam(value = "month", defaultValue = "0") Integer month) {
        return personalRewardService.findAll(year, month, departmentName, personalId, page, size);
    }
    @GetMapping("/reward/all")
    public List<PersonalReward> all() {
        return personalRewardService.all();
    }

    @GetMapping("/reward/{id}")
    public PersonalReward findById(@PathVariable Integer id) {
        return personalRewardService.find(id);
    }

    @PostMapping("/reward")
    public ResultVo insert(@RequestBody @Valid PersonalRewardForm personalRewardForm) {
        personalRewardService.insert(personalRewardForm);
        return ResultVo.success();
    }

    @PutMapping("/reward/{id}")
    public ResultVo update(@RequestBody @Valid PersonalRewardForm personalRewardForm, @PathVariable Integer id) {
        personalRewardService.updateById(id, personalRewardForm);
        return ResultVo.success();
    }

    @DeleteMapping("/reward/{id}")
    public ResultVo delete(@PathVariable Integer id) {
        personalRewardService.deleteById(id);
        return ResultVo.success();
    }

    @DeleteMapping("/reward")
    public ResultVo deleteIdIn(@RequestBody Set<Integer> id) {
        personalRewardService.deleteByIdIn(id);
        return ResultVo.success();
    }
}
