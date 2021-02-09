package cn.timelost.hr.service;

import cn.timelost.hr.pojo.Position;
import cn.timelost.hr.vo.PositionSelectVo;
import cn.timelost.hr.vo.PositionVo;
import cn.timelost.hr.vo.input.PositionForm;
import com.github.pagehelper.PageInfo;

import java.util.Collection;
import java.util.List;

/**
 * @author: Jyf
 * @Date: 2021/1/25 18:30
 */
public interface PositionService {

    PageInfo<PositionVo> findAll(int pageNum, int pageSize, int departmentId, String positionName);

    Position find(int id);

    void insert(PositionForm positionForm);

    void deleteById(Integer id);

    void deleteByIdIn(Collection<Integer> idList);

    void updateById(Integer id, PositionForm positionForm);

    List<PositionSelectVo> findSelect(Integer departmentId);
}
