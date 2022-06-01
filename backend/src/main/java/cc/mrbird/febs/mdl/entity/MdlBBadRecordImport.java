package cc.mrbird.febs.mdl.entity;

import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;

@Data
@Excel("导入不良积分")
public class MdlBBadRecordImport {
    @ExcelField(value = "发薪号")
    private String userAccount;

    /**
     * 姓名
     */

    @ExcelField(value = "姓名")
    private String userAccountName;



    /**
     * 记分指标编码
     */

    @ExcelField(value = "记分指标编码")
    private String code;

    /**
     * 具体事由
     */

    @ExcelField(value = "具体事由")
    private String note;


    /**
     * 具体事由
     */

    @ExcelField(value = "记录日期")
    private String startDate;

    /**
     * 记分人员
     */

    @ExcelField(value = "记分人员")
    private String jfPerson;
}
