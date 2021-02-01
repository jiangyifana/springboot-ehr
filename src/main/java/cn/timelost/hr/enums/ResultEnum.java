package cn.timelost.hr.enums;

import lombok.Getter;

/**
 * @author: Jyf
 * @Date: 2021/1/25 10:04
 */
@Getter
public enum ResultEnum {
    SUCCESS(200, "成功"),
    DEPARTMENT_NOT_EXIST(10, "部门未找到"),
    POSITION_NOT_EXIST(11, "岗位未找到"),
    PERSONAL_NOT_EXIST(12, "员工未找到"),

    UPLOAD_FAIL(50, "服务器上传失败"),
    ERROR(500, "未知错误"),
    PARAM_ERROR(501, "参数错误");

    private final Integer code;
    private final String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
