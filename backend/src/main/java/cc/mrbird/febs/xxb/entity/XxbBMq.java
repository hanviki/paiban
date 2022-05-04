package cc.mrbird.febs.xxb.entity;

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
 * 末期反馈表
 * </p>
 *
 * @author viki
 * @since 2022-04-27
 */

@Excel("xxb_b_mq")
@Data
@Accessors(chain = true)
public class XxbBMq implements Serializable{

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
     * 开展例数
     */
        
        @ExcelField(value ="开展例数")
    private Integer kzls;

    /**
     * 文件ID
     */
        
        @ExcelField(value ="文件ID")
    private String fileId;

    /**
     * 文件URL
     */
        
        @ExcelField(value ="文件URL")
    private String fileUrl;

    /**
     * 主要适应症
     */
        
        @ExcelField(value ="主要适应症")
    private String zysyz;

    /**
     * 是否严格掌握适应症
     */
        
        @ExcelField(value ="是否严格掌握适应症")
    private String isYgzwsyz;

    /**
     * 主要禁忌症
     */
        
        @ExcelField(value ="主要禁忌症")
    private String zyjjz;

    /**
     * 是否严格掌握禁忌症
     */
        
        @ExcelField(value ="是否严格掌握禁忌症")
    private String isYgzwjjz;

    /**
     * 治愈几例
     */
        
        @ExcelField(value ="治愈几例")
    private Integer zy;

    /**
     * 好转几例
     */
        
        @ExcelField(value ="好转几例")
    private Integer hz;

    /**
     * 治愈好转率
     */
        
        @ExcelField(value ="治愈好转率")
    private String zyPercent;

    /**
     * 经济效益与社会效益
     */
        
        @ExcelField(value ="经济效益与社会效益")
    private String economic;

    /**
     * 不良反应或并发症
     */
        
        @ExcelField(value ="不良反应或并发症")
    private String isBadresult;

    /**
     * 不良反应或并发症几例
     */
        
        @ExcelField(value ="不良反应或并发症几例")
    private Integer bad;

    /**
     * 不良反应或并发症
     */
        
        @ExcelField(value ="不良反应或并发症")
    private String badresult;

    /**
     * 拟采取的改进措施
     */
        
        @ExcelField(value ="拟采取的改进措施")
    private String cs;

    /**
     * 状态
     */
        
        @ExcelField(value ="状态")
    private Integer state;

    /**
     * 审核状态
     */
        
        @ExcelField(value ="审核状态")
    private Integer auditState;

    /**
     * 审核意见
     */
        
        @ExcelField(value ="审核意见")
    private String auditSuggestion;

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


    private transient  String projectName;

    private transient  String projectType;

    private transient  String deptNew;

    private  transient  String userAccountName;

    private  transient  String year;


    public static final String ID ="id" ;

    public static final String BASE_ID ="base_id" ;

    public static final String KZLS ="kzls" ;

    public static final String FILE_ID ="file_id" ;

    public static final String FILE_URL ="file_url" ;

    public static final String ZYSYZ ="zysyz" ;

    public static final String IS_YGZWSYZ ="is_ygzwsyz" ;

    public static final String ZYJJZ ="zyjjz" ;

    public static final String IS_YGZWJJZ ="is_ygzwjjz" ;

    public static final String ZY ="zy" ;

    public static final String HZ ="hz" ;

    public static final String ZY_PERCENT ="zy_percent" ;

    public static final String ECONOMIC ="economic" ;

    public static final String IS_BADRESULT ="is_badresult" ;

    public static final String BAD ="bad" ;

    public static final String BADRESULT ="badresult" ;

    public static final String CS ="cs" ;

    public static final String STATE ="state" ;

    public static final String AUDIT_STATE ="audit_state" ;

    public static final String AUDIT_SUGGESTION ="audit_suggestion" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

        }