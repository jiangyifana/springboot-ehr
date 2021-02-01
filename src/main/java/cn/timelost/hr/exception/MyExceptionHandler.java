package cn.timelost.hr.exception;

import cn.timelost.hr.enums.ResultEnum;
import cn.timelost.hr.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Objects;

/**
 * @author: Jyf
 * @Date: 2021/1/25 11:43
 */
@RestControllerAdvice
@Slf4j
public class MyExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResultVo handlerException(BaseException e) {
        return ResultVo.fail(e.getResultEnum());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResultVo notValidExceptionHandle(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        Objects.requireNonNull(bindingResult.getFieldError());
        log.error(bindingResult.getFieldError().getField() + " " + bindingResult.getFieldError().getDefaultMessage());
        return ResultVo.fail(ResultEnum.PARAM_ERROR,
                bindingResult.getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    @ResponseBody
    public ResultVo SQLIntegrity(SQLIntegrityConstraintViolationException e) {
        log.error(e.getMessage());
        log.error(e.getSQLState());
        return ResultVo.fail(ResultEnum.PARAM_ERROR,"请先删除已有数据！");
    }
}
