package cc.mrbird.febs.mdl.entity;

import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;



@Data
@Excel("mdl_d_surgery")
public class MdlDSurgeryImport {
    /**
     * 科室
     */

    @ExcelField(value ="科室")
    private String deptNew;

    /**
     * 编码
     */

    @ExcelField(value ="编码")
    private String code;

    /**
     * 手术名称
     */

    @ExcelField(value ="手术名称")
    private String name;

    /**
     * 手术等级
     */

    @ExcelField(value ="手术等级")
    private String level;

    /**
     * 手术切口类别
     */

    @ExcelField(value ="手术切口类别")
    private String lb;

    /**
     * 备注
     */

    @ExcelField(value ="备注")
    private String note;
}
