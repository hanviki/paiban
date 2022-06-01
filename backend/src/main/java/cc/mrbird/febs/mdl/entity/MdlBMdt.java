package cc.mrbird.febs.mdl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
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
 * 
 * </p>
 *
 * @author viki
 * @since 2022-03-28
 */

@Excel("mdl_b_mdt")
@Data
@Accessors(chain = true)
public class MdlBMdt implements Serializable{

private static final long serialVersionUID=1L;

                    @TableId(value = "id" , type = IdType.AUTO)
                
    private Long id;

    /**
     * 团队名称
     */
        
        @ExcelField(value ="团队名称")
    private String teamName;

    /**
     * 牵头科室
     */
        
        @ExcelField(value ="牵头科室")
    private String deptHead;

    /**
     * 是否开通MDT门诊
     */
        
        @ExcelField(value ="是否开通MDT门诊")
    private String isMenzhen;

    /**
     * 团队负责人
     */
        
        @ExcelField(value ="团队负责人")
    private String userAccountNameLeader;

    /**
     * 团队负责人发薪号
     */
        
        @ExcelField(value ="团队负责人发薪号")
    private String userAccountLeader;

    /**
     * 团队负责人联系电话
     */
        
        @ExcelField(value ="团队负责人联系电话")
    private String telLeader;

    /**
     * 团队负责人邮箱
     */
        
        @ExcelField(value ="团队负责人邮箱")
    private String emailLeader;

    /**
     * 团队秘书
     */
        
        @ExcelField(value ="团队秘书")
    private String userAccountNameAssist;

    /**
     * 团队负责人2
     */
        
        @ExcelField(value ="团队负责人2")
    private String userAccountNameLeader2;

    /**
     * 团队负责人发薪号2
     */
        
        @ExcelField(value ="团队负责人发薪号2")
    private String userAccountLeader2;

    /**
     * 团队负责人2联系电话
     */
        
        @ExcelField(value ="团队负责人2联系电话")
    private String telLeader2;

    /**
     * 团队负责人2邮箱
     */
        
        @ExcelField(value ="团队负责人2邮箱")
    private String emailLeader2;

    /**
     * 团队秘书邮箱
     */
        
        @ExcelField(value ="团队秘书邮箱")
    private String telAssist;

    /**
     * 团队秘书邮箱
     */
        
        @ExcelField(value ="团队秘书邮箱")
    private String emailAssist;

    /**
     * 团队秘书2邮箱
     */
        
        @ExcelField(value ="团队秘书2邮箱")
    private String telAssist2;

    /**
     * 团队秘书2邮箱
     */
        
        @ExcelField(value ="团队秘书2邮箱")
    private String emailAssist2;

    /**
     * 团队秘书2
     */
        
        @ExcelField(value ="团队秘书2")
    private String userAccountNameAssist2;

    /**
     * 团队秘书发薪号2
     */
        
        @ExcelField(value ="团队秘书发薪号2")
    private String userAccountAssist2;

    /**
     * 团队秘书发薪号
     */
        
        @ExcelField(value ="团队秘书发薪号")
    private String userAccountAssist;

    /**
     * 状态
     */
        
        @ExcelField(value ="状态")
    private Integer state;

    /**
     * 开始时间
     */
        
        @ExcelField(value ="开始时间", writeConverter = DateConverter.class,readConverter = DateReadConverter.class)
    private Date startDate;
    private transient String startDateFrom;
    private transient String startDateTo;

    /**
     * 结束时间
     */
        
        @ExcelField(value ="结束时间", writeConverter = DateConverter.class,readConverter = DateReadConverter.class)
    private Date endDate;
    private transient String endDateFrom;
    private transient String endDateTo;

    /**
     * 备注
     */
        
        @ExcelField(value ="备注")
    private String note;

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
        
        @ExcelField(value ="创建时间", writeConverter = DateConverter.class,readConverter = DateReadConverter.class)
    private Date createTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;

    /**
     * 修改时间
     */
    @TableField("MODIFY_TIME")
        
        @ExcelField(value ="修改时间", writeConverter = DateConverter.class,readConverter = DateReadConverter.class)
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

    /**
     * 附件ID
     */

    @ExcelField(value ="附件ID")
    private String fileId;

    /**
     * 附件地址
     */

    @ExcelField(value ="附件地址")
    private String fileUrl;


    public static final String ID ="id" ;

    public static final String TEAM_NAME ="team_name" ;

    public static final String DEPT_HEAD ="dept_head" ;

    public static final String IS_MENZHEN ="is_menzhen" ;

    public static final String USER_ACCOUNT_NAME_LEADER ="user_account_name_leader" ;

    public static final String USER_ACCOUNT_LEADER ="user_account_leader" ;

    public static final String TEL_LEADER ="tel_leader" ;

    public static final String EMAIL_LEADER ="email_leader" ;

    public static final String USER_ACCOUNT_NAME_ASSIST ="user_account_name_assist" ;

    public static final String USER_ACCOUNT_NAME_LEADER2 ="user_account_name_leader2" ;

    public static final String USER_ACCOUNT_LEADER2 ="user_account_leader2" ;

    public static final String TEL_LEADER2 ="tel_leader2" ;

    public static final String EMAIL_LEADER2 ="email_leader2" ;

    public static final String TEL_ASSIST ="tel_assist" ;

    public static final String EMAIL_ASSIST ="email_assist" ;

    public static final String TEL_ASSIST2 ="tel_assist2" ;

    public static final String EMAIL_ASSIST2 ="email_assist2" ;

    public static final String USER_ACCOUNT_NAME_ASSIST2 ="user_account_name_assist2" ;

    public static final String USER_ACCOUNT_ASSIST2 ="user_account_assist2" ;

    public static final String USER_ACCOUNT_ASSIST ="user_account_assist" ;

    public static final String STATE ="state" ;

    public static final String START_DATE ="start_date" ;

    public static final String END_DATE ="end_date" ;

    public static final String NOTE ="note" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

        }