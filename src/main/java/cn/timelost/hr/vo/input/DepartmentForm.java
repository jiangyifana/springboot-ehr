package cn.timelost.hr.vo.input;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @author: Jyf
 * @Date: 2021/1/25 12:04
 */
@Data
public class DepartmentForm{

    @NotBlank(message = "部门名称必填")
    private String departmentName;
    private String description;
}
