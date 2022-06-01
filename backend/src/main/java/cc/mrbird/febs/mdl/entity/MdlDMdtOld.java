package cc.mrbird.febs.mdl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;


import lombok.Data;
import lombok.experimental.Accessors;
import java.util.Date;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import cc.mrbird.febs.common.converter.*;

/**
 * <p>
 * 新技术新项目旧记录
 * </p>
 *
 * @author viki
 * @since 2022-05-25
 */

@Excel("mdl_d_mdt_old")
@Data
@Accessors(chain = true)
public class MdlDMdtOld implements Serializable{

private static final long serialVersionUID=1L;

                    @TableId(value = "id" , type = IdType.AUTO)
                
    private Long id;

    /**
     * 科室
     */
        
        @ExcelField(value ="科室")
    private String deptNew;

    /**
     * 项目负责人
     */
        
        @ExcelField(value ="项目负责人")
    private String fzr;

    /**
     * 职称
     */
        
        @ExcelField(value ="职称")
    private String zhicheng;

    /**
     * 项目参与人1
     */
        
        @ExcelField(value ="项目参与人1")
    private String cy1;

    /**
     * 项目参与人2
     */
        
        @ExcelField(value ="项目参与人2")
    private String cy2;

    /**
     * 项目参与人3
     */
        
        @ExcelField(value ="项目参与人3")
    private String cy3;

    /**
     * 项目参与人4
     */
        
        @ExcelField(value ="项目参与人4")
    private String cy4;

    /**
     * 项目参与人5
     */
        
        @ExcelField(value ="项目参与人5")
    private String cy5;

    /**
     * 项目参与人6
     */
        
        @ExcelField(value ="项目参与人6")
    private String cy6;

    /**
     * 项目参与人7
     */
        
        @ExcelField(value ="项目参与人7")
    private String cy7;

    /**
     * 项目参与人8
     */
        
        @ExcelField(value ="项目参与人8")
    private String cy8;

    /**
     * 项目参与人9
     */
        
        @ExcelField(value ="项目参与人9")
    private String cy9;

    /**
     * 项目参与人10
     */
        
        @ExcelField(value ="项目参与人10")
    private String cy10;

    /**
     * 项目名称
     */
        
        @ExcelField(value ="项目名称")
    private String xmmc;

    /**
     * 年度
     */
        
        @ExcelField(value ="年度")
    private String year;

    /**
     * 项目水平（按领先水平划分）
     */
        
        @ExcelField(value ="项目水平（按领先水平划分）")
    private String xmsp;

    /**
     * 状态
     */
        

    private Integer state;

    /**
     * 是否删除
     */
    @TableField("IS_DELETEMARK")
        

    private Integer isDeletemark;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
        

    private Date createTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;

    /**
     * 修改时间
     */
    @TableField("MODIFY_TIME")
        
        private Date modifyTime;
    private transient String modifyTimeFrom;
    private transient String modifyTimeTo;

    /**
     * 创建人
     */
    @TableField("CREATE_USER_ID")
        

    private Long createUserId;

    /**
     * 修改人
     */
    @TableField("MODIFY_USER_ID")
        

    private Long modifyUserId;



    public static final String ID ="id" ;

    public static final String DEPT_NEW ="dept_new" ;

    public static final String FZR ="fzr" ;

    public static final String ZHICHENG ="zhicheng" ;

    public static final String CY1 ="cy1" ;

    public static final String CY2 ="cy2" ;

    public static final String CY3 ="cy3" ;

    public static final String CY4 ="cy4" ;

    public static final String CY5 ="cy5" ;

    public static final String CY6 ="cy6" ;

    public static final String CY7 ="cy7" ;

    public static final String CY8 ="cy8" ;

    public static final String CY9 ="cy9" ;

    public static final String CY10 ="cy10" ;

    public static final String XMMC ="xmmc" ;

    public static final String YEAR ="year" ;

    public static final String XMSP ="xmsp" ;

    public static final String STATE ="state" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

        }