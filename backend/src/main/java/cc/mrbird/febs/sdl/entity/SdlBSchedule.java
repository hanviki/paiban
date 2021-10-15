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
 * 排班主表
 * </p>
 *
 * @author viki
 * @since 2021-10-13
 */

@Excel("sdl_b_schedule")
@Data
@Accessors(chain = true)
public class SdlBSchedule implements Serializable{

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
     * 科室
     */
        
        @ExcelField(value ="科室")
    private String deptName;

    /**
     * 院区
     */
        
        @ExcelField(value ="院区")
    private String yqName;

    /**
     * 科室ID
     */
        
        @ExcelField(value ="科室ID")
    private String deptId;

    /**
     * 院区ID
     */
        
        @ExcelField(value ="院区ID")
    private Integer yqId;

    /**
     * 开始日期
     */
        
        @ExcelField(value ="开始日期", writeConverter = DateConverter.class)
    private Date startDate;
    private transient String startDateFrom;
    private transient String startDateTo;

    /**
     * 结束日期
     */
        
        @ExcelField(value ="结束日期", writeConverter = DateConverter.class)
    private Date endDate;
    private transient String endDateFrom;
    private transient String endDateTo;

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

    public static final String DEPT_NAME ="dept_name" ;

    public static final String YQ_NAME ="yq_name" ;

    public static final String DEPT_ID ="dept_id" ;

    public static final String YQ_ID ="yq_id" ;

    public static final String START_DATE ="start_date" ;

    public static final String END_DATE ="end_date" ;

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