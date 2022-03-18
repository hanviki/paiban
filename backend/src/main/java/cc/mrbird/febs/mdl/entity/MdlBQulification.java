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
 * @since 2022-03-16
 */

@Excel("mdl_b_qulification")
@Data
@Accessors(chain = true)
public class MdlBQulification implements Serializable{

private static final long serialVersionUID=1L;

                    @TableId(value = "id" , type = IdType.AUTO)
                
    private Long id;

    /**
     * 证件名称
     */
        
        @ExcelField(value ="证件名称")
    private String qlName;

    /**
     * 发证日期
     */
        
        @ExcelField(value ="发证日期", writeConverter = DateConverter.class,readConverter = DateReadConverter.class)
    private Date fzDate;
    private transient String fzDateFrom;
    private transient String fzDateTo;

    /**
     * 变更日期
     */
        
        @ExcelField(value ="变更日期", writeConverter = DateConverter.class,readConverter = DateReadConverter.class)
    private Date bgDate;
    private transient String bgDateFrom;
    private transient String bgDateTo;

    /**
     * 变更事项
     */
        
        @ExcelField(value ="变更事项")
    private String bgItem;

    /**
     * 证件编号
     */
        
        @ExcelField(value ="证件编号")
    private String qlCode;

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

    public static final String QL_NAME ="ql_name" ;

    public static final String FZ_DATE ="fz_date" ;

    public static final String BG_DATE ="bg_date" ;

    public static final String BG_ITEM ="bg_item" ;

    public static final String QL_CODE ="ql_code" ;

    public static final String FILE_ID ="file_id" ;

    public static final String FILE_URL ="file_url" ;

    public static final String STATE ="state" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

        }