package cc.mrbird.febs.mdl.entity;

import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;

@Data
@Excel("mdl_d_opertaion")
public class MdlDOperationImport {
    /**
     * 科室
     */

    @ExcelField(value ="科室")
    private String deptNew;

    @ExcelField(value ="操作类别")
    private String lb;
    /**
     * 编码
     */

    @ExcelField(value ="编码")
    private String code;

    /**
     * 手术名称
     */

    @ExcelField(value ="操作名称")
    private String name;

    /**
     * 手术等级
     */

    @ExcelField(value ="操作等级")
    private String level;



    @ExcelField(value ="启用时间")
    private String startDate;
    /**
     * 备注
     */

    @ExcelField(value ="备注")
    private String note;
}
