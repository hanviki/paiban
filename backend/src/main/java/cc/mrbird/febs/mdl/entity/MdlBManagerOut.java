package cc.mrbird.febs.mdl.entity;

import cc.mrbird.febs.common.converter.DateConverter;
import cc.mrbird.febs.common.converter.DateReadConverter;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;

import java.util.Date;

@Data
@Excel("mdl_b_manager2")
public class MdlBManagerOut {


    @ExcelField(value ="医疗组科室")
    private String deptName;
    /**
     * 科室ID
     */

    @ExcelField(value ="科室名称")
    private String deptId;



    /**
     * 类型
     */

    @ExcelField(value ="类型")
    private String type;

    /**
     * 发薪号
     */

    @ExcelField(value ="发薪号")
    private String userAccount;

    /**
     * 姓名
     */

    @ExcelField(value ="姓名")
    private String userAccountName;

    /**
     * 出生年月
     */

    @ExcelField(value ="出生年月")
    private String birthday;

    @ExcelField(value ="电话号码")
    private String tel;

    @ExcelField(value ="邮箱")
    private String email;

    @ExcelField(value ="开始时间", writeConverter = DateConverter.class,readConverter = DateReadConverter.class)
    private Date startDate;

    /**
     * 结束时间
     */

    @ExcelField(value ="结束时间", writeConverter = DateConverter.class,readConverter = DateReadConverter.class)
    private Date endDate;
}
