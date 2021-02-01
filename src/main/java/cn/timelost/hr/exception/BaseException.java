package cn.timelost.hr.exception;

import cn.timelost.hr.enums.ResultEnum;
import lombok.Data;

/**
 * @author: Jyf
 * @Date: 2021/1/25 11:40
 */
@Data
public class BaseException extends RuntimeException {
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