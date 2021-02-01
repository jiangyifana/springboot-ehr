package cn.timelost.hr.vo.input;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author: Jyf
 * @Date: 2021/1/25 18:32
 */
@Data
public class PositionForm {

    @NotBlank(message = "岗位名称必填")
    private String positionName;
    private String description;
    @NotNull(message = "部门ID必填")
    private Integer departmentId;
}
