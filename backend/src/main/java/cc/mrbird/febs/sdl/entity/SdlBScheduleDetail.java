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
import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 * 排班子表明细
 * </p>
 *
 * @author viki
 * @since 2021-11-15
 */

@Excel("sdl_b_schedule_detail")
@Data
@Accessors(chain = true)
public class SdlBScheduleDetail implements Serializable{

private static final long serialVersionUID=1L;

    /**
     * id
     */
                            
        @ExcelField(value ="id")
    private String id;

    /**
     * 主表id
     */
        
        @ExcelField(value ="主表id")
    private String baseId;

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
     * 院区ID
     */
        
        @ExcelField(value ="院区ID")
    private Integer yqId;

    /**
     * 病区ID
     */
        
        @ExcelField(value ="病区ID")
    private String bqId;

    /**
     * 排班日期
     */
        
        @ExcelField(value ="排班日期", writeConverter = DateConverter.class)
    private Date scheduleDate;
    private transient String scheduleDateFrom;
    private transient String scheduleDateTo;


    /**
     * 班次id
     */
        
        @ExcelField(value ="班次id")
    private Integer banciId;

    /**
     * 班次
     */
        
        @ExcelField(value ="班次")
    private String banci;
    @ExcelField(value ="月度")
        private  String month;

    @ExcelField(value ="排班日期字符串")
    private  String sch;

    /**
     * 排班人员账号
     */
        
        @ExcelField(value ="排班人员账号")
    private String accountId;

    /**
     * 排班人员
     */
        
        @ExcelField(value ="排班人员")
    private String accountName;

    /**
     * 人员类型id 0是一值班
     */
        
        @ExcelField(value ="人员类型id 0是一值班")
    private Integer zizhiId;

    /**
     * 人员类型
     */
        
        @ExcelField(value ="人员类型")
    private String zizhiName;

    /**
     * 当前职称
     */
        
        @ExcelField(value ="当前职称")
    private String zhicheng;

    /**
     * 医师序列
     */
        
        @ExcelField(value ="医师序列")
    private String xulie;

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
    private Long createUserId;

    /**
     * 修改人
     */
    @TableField("MODIFY_USER_ID")
        
        @ExcelField(value ="修改人")
    private Long modifyUserId;

    private transient  int areaIndex;



    public static final String ID ="id" ;

    public static final String BASE_ID ="base_id" ;

    public static final String USER_NO ="user_no" ;

    public static final String NAME ="name" ;

    public static final String USER_NAME ="user_name" ;

    public static final String DEPT_NAME ="dept_name" ;

    public static final String YQ_NAME ="yq_name" ;

    public static final String BQ_NAME ="bq_name" ;

    public static final String DEPT_ID ="dept_id" ;

    public static final String YQ_ID ="yq_id" ;

    public static final String BQ_ID ="bq_id" ;

    public static final String SCHEDULE_DATE ="schedule_date" ;

    public static final String BANCI_ID ="banci_id" ;

    public static final String BANCI ="banci" ;

    public static final String ACCOUNT_ID ="account_id" ;

    public static final String ACCOUNT_NAME ="account_name" ;

    public static final String ZIZHI_ID ="zizhi_id" ;

    public static final String ZIZHI_NAME ="zizhi_name" ;

    public static final String ZHICHENG ="zhicheng" ;

    public static final String XULIE ="xulie" ;

    public static final String STATE ="state" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

        }