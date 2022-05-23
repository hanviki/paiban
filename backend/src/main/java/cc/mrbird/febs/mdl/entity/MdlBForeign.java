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
import java.util.List;

import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import cc.mrbird.febs.common.converter.*;

/**
 * <p>
 * 
 * </p>
 *
 * @author viki
 * @since 2022-05-16
 */

@Excel("mdl_b_foreign")
@Data
@Accessors(chain = true)
public class MdlBForeign implements Serializable{

private static final long serialVersionUID=1L;


                
    private String id;

    /**
     * 任务来源
     */
        
        @ExcelField(value ="任务来源")
    private String taskSource;

    /**
     * 外派类型
     */
        
        @ExcelField(value ="外派类型")
    private String taskType;

    /**
     * 外派事项
     */
        
        @ExcelField(value ="外派事项")
    private String taskItem;

    /**
     * 外派开始时间
     */
        
        @ExcelField(value ="外派开始时间", writeConverter = DateConverter.class,readConverter = DateReadConverter.class)
    private Date startDate;
    private transient String startDateFrom;
    private transient String startDateTo;

    /**
     * 外派结束时间
     */
        
        @ExcelField(value ="外派结束时间", writeConverter = DateConverter.class,readConverter = DateReadConverter.class)
    private Date endDate;
    private transient String endDateFrom;
    private transient String endDateTo;

    /**
     * 省
     */
        
        @ExcelField(value ="省")
    private String province;

    /**
     * 市
     */
        
        @ExcelField(value ="市")
    private String city;

    /**
     * 区
     */
        
        @ExcelField(value ="区")
    private String street;

    /**
     * 地址
     */
        
        @ExcelField(value ="地址")
    private String address;

    /**
     * 链接地址
     */
        
        @ExcelField(value ="链接地址")
    private String linkUrl;

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


    private transient List<MdlBForeignD> checkDataList;


    public static final String ID ="id" ;

    public static final String TASK_SOURCE ="task_source" ;

    public static final String TASK_TYPE ="task_type" ;

    public static final String TASK_ITEM ="task_item" ;

    public static final String START_DATE ="start_date" ;

    public static final String END_DATE ="end_date" ;

    public static final String PROVINCE ="province" ;

    public static final String CITY ="city" ;

    public static final String STREET ="street" ;

    public static final String ADDRESS ="address" ;

    public static final String LINK_URL ="link_url" ;

    public static final String FILE_ID ="file_id" ;

    public static final String FILE_URL ="file_url" ;

    public static final String STATE ="state" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

        }