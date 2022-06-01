package cc.mrbird.febs.mdl.entity;

import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;

@Data
@Excel("手术级别导入")
public class MdlBSurgeryinfoImport {
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
     * 级别
     */

    @ExcelField(value ="级别")
    private String jb;

    @ExcelField(value ="生效时间")
    private String validDate;

    @ExcelField(value ="备注")
    private String note;

}
