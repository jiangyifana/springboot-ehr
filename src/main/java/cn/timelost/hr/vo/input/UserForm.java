package cn.timelost.hr.vo.input;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;

/**
 * @author: Jyf
 * @Date: 2021/2/18 21:22
 */
@Data
public class UserForm {

    @NotBlank(message = "用户名必填")
    private String username;

    @NotBlank(message = "密码必填")
    private String password;

    private Integer roleId;
    private String code;
    private String timestamp;
}