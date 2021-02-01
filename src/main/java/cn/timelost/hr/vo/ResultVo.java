package cn.timelost.hr.vo;

import cn.timelost.hr.enums.ResultEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: Jyf
 * @Date: 2021/1/25 9:59
 */
@Data
public class ResultVo<T> implements Serializable {

    private static final long serialVersionUID = 5085403015235441808L;
    private Integer code;
    private String msg;
    private T data;
    private Long timestamp = System.currentTimeMillis();

    public static ResultVo<Void> success() {
        ResultVo<Void> ResultVO = new ResultVo<>();
        ResultVO.code = ResultEnum.SUCCESS.getCode();
        ResultVO.msg = ResultEnum.SUCCESS.getMessage();
        return ResultVO;
    }

    public static <T> ResultVo<T> success(T data) {
        ResultVo<T> ResultVO = new ResultVo<>();
        ResultVO.code = ResultEnum.SUCCESS.getCode();
        ResultVO.msg = ResultEnum.SUCCESS.getMessage();
        ResultVO.data = data;
        return ResultVO;
    }

    public static ResultVo<Void> successMsg(String msg) {
        ResultVo<Void> ResultVO = new ResultVo<>();
        ResultVO.code = ResultEnum.SUCCESS.getCode();
        ResultVO.msg = msg;
        return ResultVO;
    }

    public static <T> ResultVo<T> fail(ResultEnum resultEnum) {
        ResultVo<T> ResultVO = new ResultVo<>();
        ResultVO.code = resultEnum.getCode();
        ResultVO.msg = resultEnum.getMessage();
        return ResultVO;
    }

    public static <T> ResultVo<T> fail(ResultEnum resultEnum, String msg) {
        ResultVo<T> ResultVO = new ResultVo<>();
        ResultVO.code = resultEnum.getCode();
        ResultVO.msg = msg;
        return ResultVO;
    }
}
