package cn.timelost.hr.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: Jyf
 * @Date: 2021/1/31 16:37
 */
@Data
public class PositionSelectVo implements Serializable {
    private static final long serialVersionUID = 2002035166649651169L;
    private Integer id;
    private String positionName;
}
