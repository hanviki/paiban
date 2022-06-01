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
 * @since 2022-05-23
 */

@Data
@Excel("Sheet1")
public class XxbBResultExcel {

    /**
     * 项目ID
     */

    @ExcelField(value = "项目ID")
    private String id;

    /**
     * 评分年度
     */

    @ExcelField(value = "评分年度")
    private String pfnd;

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
     * 院外评审得分
     */

    @ExcelField(value = "院外评审得分")
    private Double ywtotal;

    /**
     * 院内评审得分
     */

    @ExcelField(value = "院内评审得分")
    private Double yntotal;

    /**
     * 最终得分
     */

    @ExcelField(value = "最终得分")
    private Double abtotal;

    /**
     * 获奖情况
     */

    @ExcelField(value = "获奖情况")
    private String hjqk;

    /**
     * 获奖文件
     */

    @ExcelField(value = "获奖文件")
    private String filename;

    /**
     * 项目成员1
     */

    @ExcelField(value = "项目成员1")
    private String cy1;

    /**
     * 项目成员2
     */

    @ExcelField(value = "项目成员2")
    private String cy2;

    /**
     * 项目成员3
     */

    @ExcelField(value = "项目成员3")
    private String cy3;

    /**
     * 项目成员4
     */

    @ExcelField(value = "项目成员4")
    private String cy4;

    /**
     * 项目成员5
     */

    @ExcelField(value = "项目成员5")
    private String cy5;

    /**
     * 项目成员6
     */

    @ExcelField(value = "项目成员6")
    private String cy6;


}