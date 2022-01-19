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

@Excel("mdl_b_chufang")
@Data
@Accessors(chain = true)
public class MdlBChufang implements Serializable{

private static final long serialVersionUID=1L;

                    @TableId(value = "id" , type = IdType.AUTO)
                
    private Long id;

    /**
     * 类型
     */
        
        @ExcelField(value ="类型")
    private String type;

    /**
     * 姓名
     */
        
        @ExcelField(value ="姓名")
    private String userAccountName;

    /**
     * 发薪号
     */
        
        @ExcelField(value ="发薪号")
    private String userAccount;

    /**
     * 培训日期
     */
        
        @ExcelField(value ="培训日期", writeConverter = DateConverter.class,readConverter = DateReadConverter.class)
    private Date trainDate;
    private transient String trainDateFrom;
    private transient String trainDateTo;

    /**
     * 考核分数
     */
        
        @ExcelField(value ="考核分数")
    private String exiamScore;

    /**
     * 考核结果
     */
        
        @ExcelField(value ="考核结果")
    private String exiamResult;

    /**
     * 授权文件
     */
        
        @ExcelField(value ="授权文件")
    private Long archiveId;

    /**
     * 授权文件名称
     */
        
        @ExcelField(value ="授权文件名称")
    private String archiveName;

    /**
     * 授权文件编码
     */
        
        @ExcelField(value ="授权文件编码")
    private String archiveCode;

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
     * 是否处方
     */
        
        @ExcelField(value ="是否处方")
    private String isChufang;

    /**
     * 级别
     */
        
        @ExcelField(value ="级别")
    private String level;

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

    public static final String TYPE ="type" ;

    public static final String USER_ACCOUNT_NAME ="user_account_name" ;

    public static final String USER_ACCOUNT ="user_account" ;

    public static final String TRAIN_DATE ="train_date" ;

    public static final String EXIAM_SCORE ="exiam_score" ;

    public static final String EXIAM_RESULT ="exiam_result" ;

    public static final String ARCHIVE_ID ="archive_id" ;

    public static final String ARCHIVE_NAME ="archive_name" ;

    public static final String ARCHIVE_CODE ="archive_code" ;

    public static final String FILE_ID ="file_id" ;

    public static final String FILE_URL ="file_url" ;

    public static final String IS_CHUFANG ="is_chufang" ;

    public static final String LEVEL ="level" ;

    public static final String STATE ="state" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

        }