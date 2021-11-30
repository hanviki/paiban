package cc.mrbird.febs.sdl.entity;

import cc.mrbird.febs.common.converter.DateConverter;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Excel("sdl_b_schedule_d2")
@Data
public class CustomData {
    /**
     * 科室ID
     */

    @ExcelField(value ="科室ID")
    private String deptId;


    /**
     * 科室
     */

    @ExcelField(value ="科室")
    private String deptName;

    @ExcelField(value ="病区")
    private String bqName;

    /**
     * 排班日期
     */

    @ExcelField(value ="排班日期", writeConverter = DateConverter.class)
    private Date scheduleDate;
    private  String scheduleDateFrom;
    private  String scheduleDateTo;

    /**
     * 排班人员账号
     */

    @ExcelField(value ="排班人员账号")
    private String accountId;

    /**
     * 排班人员
     */

    @ExcelField(value ="排班人员")
    private String accountName;

    @ExcelField(value ="排班天数")
    private double cishu;

    @ExcelField(value ="金额")
    private double amount;

    @ExcelField(value ="等级")
    private String renshizifw;

    @ExcelField(value ="职称")
    private String zhicheng;
}
