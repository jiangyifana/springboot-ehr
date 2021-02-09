package cn.timelost.hr.controller;

import cn.timelost.hr.pojo.Position;
import cn.timelost.hr.service.PositionService;
import cn.timelost.hr.vo.DepartmentSelectVo;
import cn.timelost.hr.vo.PositionSelectVo;
import cn.timelost.hr.vo.PositionVo;
import cn.timelost.hr.vo.ResultVo;
import cn.timelost.hr.vo.input.PositionForm;
import com.github.pagehelper.PageInfo;
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
public class PositionController {

    @Resource
    PositionService positionService;

    @GetMapping("/position/list")
    public PageInfo<PositionVo> list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                     @RequestParam(value = "size", defaultValue = "10") Integer size,
                                     @RequestParam(value = "departmentId", defaultValue = "0") Integer departmentId,
                                     @RequestParam(value = "positionName", required = false) String positionName) {
        return positionService.findAll(page, size, departmentId, positionName);
    }

    @GetMapping("/position/select/{departmentId}")
    public List<PositionSelectVo> selectList(@PathVariable Integer departmentId) {
        return positionService.findSelect(departmentId);
    }

    @GetMapping("/position/{id}")
    public Position findById(@PathVariable Integer id) {
        return positionService.find(id);
    }

    @PostMapping("/position")
    public ResultVo insert(@RequestBody @Valid PositionForm Position) {
        positionService.insert(Position);
        return ResultVo.success();
    }

    @PutMapping("/position/{id}")
    public ResultVo update(@RequestBody PositionForm Position, @PathVariable Integer id) {
        positionService.updateById(id, Position);
        return ResultVo.success();
    }

    @DeleteMapping("/position/{id}")
    public ResultVo delete(@PathVariable Integer id) {
        positionService.deleteById(id);
        return ResultVo.success();
    }

    @DeleteMapping("/position")
    public ResultVo deleteIdIn(@RequestBody Set<Integer> id) {
        positionService.deleteByIdIn(id);
        return ResultVo.success();
    }
}
