package cc.mrbird.febs.mdl.entity;

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
 * @since 2022-03-23
 */

@Excel("mdl_b_mdt_file")
@Data
@Accessors(chain = true)
public class MdlBMdtFile implements Serializable{

private static final long serialVersionUID=1L;

    /**
     * 主键
     */
                            
        @ExcelField(value ="主键")
    private String id;

    /**
     * 父表Id
     */
        
        @ExcelField(value ="父表Id")
    private Long baseId;

    /**
     * 姓名
     */
        
        @ExcelField(value ="姓名")
    private String name;

    /**
     * 年度
     */
        
        @ExcelField(value ="年度")
    private String year;


    /**
     * 考核分数
     */

    @ExcelField(value ="考核分数")
    private double score;
    /**
     * 考核结果
     */

    @ExcelField(value ="考核结果")
    private String result;



    /**
     * 备注
     */
        
        @ExcelField(value ="备注")
    private String note;

    /**
     * 附件1
     */
        
        @ExcelField(value ="附件1")
    private String fileId;

    /**
     * 附件1地址
     */
        
        @ExcelField(value ="附件1地址")
    private String fileUrl;

    /**
     * 附件2
     */
        
        @ExcelField(value ="附件2")
    private String zcFileId;

    /**
     * 附件2地址
     */
        
        @ExcelField(value ="附件2地址")
    private String zcFileUrl;

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

    public static final String BASE_ID ="base_id" ;

    public static final String NAME ="name" ;

    public static final String YEAR ="year" ;

    public static final String NOTE ="note" ;

    public static final String FILE_ID ="file_id" ;

    public static final String FILE_URL ="file_url" ;

    public static final String ZC_FILE_ID ="zc_file_id" ;

    public static final String ZC_FILE_URL ="zc_file_url" ;

    public static final String STATE ="state" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

        }