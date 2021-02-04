package cn.timelost.hr.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: Jyf
 * @Date: 2021/2/2 17:43
 */
@Data
public class PersonalSelectVo implements Serializable {
    private static final long serialVersionUID = 4249534101633399370L;
    private Integer id;
    private String name;
    private Integer departmentId;
    private Integer positionId;
}
