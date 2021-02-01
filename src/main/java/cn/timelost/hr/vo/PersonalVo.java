package cn.timelost.hr.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: Jyf
 * @Date: 2021/1/28 17:38
 */
@Data
public class PersonalVo implements Serializable {

    private static final long serialVersionUID = -4030207494693280106L;
    private Integer id;
    private String name;
    private Integer gender;
    @JsonFormat(
            pattern = "yyyy-MM-dd",
            timezone = "GMT+8"
    )
    private Date birthday;
    private Long phone;
    private String email;
    private Long identity;
    private String education;
    private String school;
    private String imgUrl;
    private Integer workStatus;
    private Integer departmentId;
    private Integer positionId;
    private String departmentName;
    private String positionName;
    private Date beginDate;
}
