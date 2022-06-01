package cc.mrbird.febs.xxb.entity;

import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author viki
 * @since 2022-05-27
 */

@Data
@Excel("院内评审得分")
public class XxbBResultPYnExcel {

    /**
     * 项目ID
     */

    @ExcelField(value = "项目ID")
    private String id;

    /**
     * 院区
     */

    @ExcelField(value = "院区")
    private String yuanqu;

    /**
     * 科室
     */

    @ExcelField(value = "科室")
    private String deptNew;

    /**
     * 项目负责人
     */

    @ExcelField(value = "项目负责人")
    private String userAccountName;

    /**
     * 工号
     */

    @ExcelField(value = "工号")
    private String userAccount;

    /**
     * 职称
     */

    @ExcelField(value = "职称")
    private String zhichenglc;

    /**
     * 项目名称
     */

    @ExcelField(value = "项目名称")
    private String projectName;

    /**
     * 院内专家
     */

    @ExcelField(value = "院内专家")
    private String dfname;

    /**
     * 专家科室
     */

    @ExcelField(value = "专家科室")
    private String dfwork;

    /**
     * 专家职称
     */

    @ExcelField(value = "专家职称")
    private String dfzhic;

    /**
     * 创新性
     */

    @ExcelField(value = "创新性(40)")
    private Double cxx;

    /**
     * 实用性
     */

    @ExcelField(value = "实用性(20)")
    private Double syx;

    /**
     * 技术难度
     */

    @ExcelField(value = "技术难度(20)")
    private Double jsnd;

    /**
     * 效益性
     */

    @ExcelField(value = "效益性(20)")
    private Double xyx;

    /**
     * 院内评审最终得分
     */

    @ExcelField(value ="院内评审最终得分")
    private Double total;

}