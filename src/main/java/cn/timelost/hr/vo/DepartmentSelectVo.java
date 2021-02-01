package cn.timelost.hr.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: Jyf
 * @Date: 2021/1/27 23:21
 */
@Data
public class DepartmentSelectVo implements Serializable {

    private static final long serialVersionUID = 8445065663039274650L;
    private Integer id;
    private String departmentName;
}
