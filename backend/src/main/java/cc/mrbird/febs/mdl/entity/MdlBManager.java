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
 * @since 2022-01-19
 */

@Excel("mdl_b_manager")
@Data
@Accessors(chain = true)
public class MdlBManager implements Serializable{

private static final long serialVersionUID=1L;

                    @TableId(value = "id" , type = IdType.AUTO)
                
    private Long id;

    /**
     * 科室ID
     */
        
        @ExcelField(value ="科室ID")
    private String deptId;

    /**
     * 科室名称
     */
        
        @ExcelField(value ="医疗组科室")
    private String deptName;

    /**
     * 类型
     */
        
        @ExcelField(value ="类型")
    private String type;

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
     * 出生年月
     */
        
        @ExcelField(value ="出生年月")
    private String birthday;

    @ExcelField(value ="电话号码")
    private String tel;

    @ExcelField(value ="邮箱")
    private String email;

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



    public static final String ID ="id" ;

    public static final String DEPT_ID ="dept_id" ;

    public static final String DEPT_NAME ="dept_name" ;

    public static final String TYPE ="type" ;

    public static final String USER_ACCOUNT ="user_account" ;

    public static final String USER_ACCOUNT_NAME ="user_account_name" ;

    public static final String BIRTHDAY ="birthday" ;

    public static final String FILE_ID ="file_id" ;

    public static final String FILE_URL ="file_url" ;

    public static final String STATE ="state" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String START_DATE ="start_date" ;

    public static final String END_DATE ="end_date" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

        }