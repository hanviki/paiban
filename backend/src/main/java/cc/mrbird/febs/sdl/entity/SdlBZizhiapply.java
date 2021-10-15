package cc.mrbird.febs.sdl.entity;

import java.time.LocalDate;
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
 * 资质变更申请
 * </p>
 *
 * @author viki
 * @since 2021-10-13
 */

@Excel("sdl_b_zizhiapply")
@Data
@Accessors(chain = true)
public class SdlBZizhiapply implements Serializable{

private static final long serialVersionUID=1L;

    /**
     * id
     */
                            
        @ExcelField(value ="id")
    private String id;

    /**
     * 申请人的发薪号
     */
        
        @ExcelField(value ="申请人的发薪号")
    private String userNo;

    /**
     * 申请人的姓名
     */
        
        @ExcelField(value ="申请人的姓名")
    private String name;

    /**
     * 申请人的账号
     */
        
        @ExcelField(value ="申请人的账号")
    private String userName;

    /**
     * 被申请人的账号
     */
        
        @ExcelField(value ="被申请人的账号")
    private String userAccount;

    /**
     * 被申请人的姓名
     */
        
        @ExcelField(value ="被申请人的姓名")
    private String userAccountName;

    /**
     * 人员类型id
     */
        
        @ExcelField(value ="人员类型id")
    private Integer rylxId;

    /**
     * 人员类型
     */
        
        @ExcelField(value ="人员类型")
    private String rylxName;

    /**
     * 科室
     */
        
        @ExcelField(value ="科室")
    private String deptName;

    /**
     * 病区
     */
        
        @ExcelField(value ="病区")
    private String bqName;

    /**
     * 科室ID
     */
        
        @ExcelField(value ="科室ID")
    private String deptId;

    /**
     * 申请日期
     */
        
        @ExcelField(value ="申请日期", writeConverter = DateConverter.class)
    private Date applyDate;
    private transient String applyDateFrom;
    private transient String applyDateTo;

    /**
     * 申请资质
     */
        
        @ExcelField(value ="申请资质")
    private String applyType;

    /**
     * 来院时间
     */
        
        @ExcelField(value ="来院时间", writeConverter = DateConverter.class)
    private Date schoolDate;
    private transient String schoolDateFrom;
    private transient String schoolDateTo;

    /**
     * 现职称
     */
        
        @ExcelField(value ="现职称")
    private String positionName;

    /**
     * 现职称聘任时间
     */
        
        @ExcelField(value ="现职称聘任时间")
    private String positionPrdate;

    /**
     * 原资质
     */
        
        @ExcelField(value ="原资质")
    private String orignType;

    /**
     * 新资质
     */
        
        @ExcelField(value ="新资质")
    private String newType;

    /**
     * 备注
     */
        
        @ExcelField(value ="备注")
    private String note;

    /**
     * 审核意见
     */
        
        @ExcelField(value ="审核意见")
    private String auditSuggestion;

    /**
     * 审核时间
     */
        
        @ExcelField(value ="审核时间", writeConverter = DateConverter.class)
    private Date auditDate;
    private transient String auditDateFrom;
    private transient String auditDateTo;

    /**
     * 状态
     */
        
        @ExcelField(value ="状态")
    private Integer state;

    /**
     * 是否删除
     */
    @TableField("IS_DELETEMARK")
        
        @ExcelField(value ="是否删除")
    private Integer isDeletemark;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
        
        @ExcelField(value ="创建时间", writeConverter = DateConverter.class)
    private Date createTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;

    /**
     * 修改时间
     */
    @TableField("MODIFY_TIME")
        
        @ExcelField(value ="修改时间", writeConverter = DateConverter.class)
    private Date modifyTime;
    private transient String modifyTimeFrom;
    private transient String modifyTimeTo;

    /**
     * 创建人
     */
    @TableField("CREATE_USER_ID")
        
        @ExcelField(value ="创建人")
    private long createUserId;

    /**
     * 修改人
     */
    @TableField("MODIFY_USER_ID")
        
        @ExcelField(value ="修改人")
    private long modifyUserId;



    public static final String ID ="id" ;

    public static final String USER_NO ="user_no" ;

    public static final String NAME ="name" ;

    public static final String USER_NAME ="user_name" ;

    public static final String USER_ACCOUNT ="user_account" ;

    public static final String USER_ACCOUNT_NAME ="user_account_name" ;

    public static final String RYLX_ID ="rylx_id" ;

    public static final String RYLX_NAME ="rylx_name" ;

    public static final String DEPT_NAME ="dept_name" ;

    public static final String BQ_NAME ="bq_name" ;

    public static final String DEPT_ID ="dept_id" ;

    public static final String APPLY_DATE ="apply_date" ;

    public static final String APPLY_TYPE ="apply_type" ;

    public static final String SCHOOL_DATE ="school_date" ;

    public static final String POSITION_NAME ="position_name" ;

    public static final String POSITION_PRDATE ="position_prdate" ;

    public static final String ORIGN_TYPE ="orign_type" ;

    public static final String NEW_TYPE ="new_type" ;

    public static final String NOTE ="note" ;

    public static final String AUDIT_SUGGESTION ="audit_suggestion" ;

    public static final String AUDIT_DATE ="audit_date" ;

    public static final String STATE ="state" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

        }