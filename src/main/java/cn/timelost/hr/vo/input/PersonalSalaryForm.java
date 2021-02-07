package cn.timelost.hr.vo.input;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: Jyf
 * @Date: 2021/2/7 9:56
 */
@Data
public class PersonalSalaryForm {
    @NotNull(message = "用户必填")
    private Integer personalId;
    @NotNull(message = "时间必填")
    @JsonFormat(
            pattern = "yyyy-MM-dd",
            timezone = "GMT+8"
    )
    private Date salaryDate;
    @NotNull(message = "基本工资必填")
    private BigDecimal basisSalary;
    private BigDecimal subsidySalary;
    private BigDecimal socialSalary;
    private BigDecimal providentFund;
    private BigDecimal bonus;
}
