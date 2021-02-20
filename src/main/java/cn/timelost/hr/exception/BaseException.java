package cn.timelost.hr.exception;

import cn.timelost.hr.enums.ResultEnum;
import lombok.Data;
import lombok.Getter;

/**
 * @author: Jyf
 * @Date: 2021/1/25 11:40
 */
@Getter
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = -4088898423313593435L;
    private String msg;
    private ResultEnum resultEnum;

    public BaseException() {
    }

    public BaseException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public BaseException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.resultEnum = resultEnum;
    }
}