package cc.mrbird.febs.mdl.entity;

import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;

import java.util.Date;

@Data
@Excel("mdl_b_manager")
public class MdlBManagerImport {


    @ExcelField(value ="医疗组科室")
    private String deptName;
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

    @ExcelField(value ="邮箱")
    private String email;

    @ExcelField(value ="开始时间")
    private String startDate;

}
