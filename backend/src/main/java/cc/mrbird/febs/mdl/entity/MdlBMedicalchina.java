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
 * @since 2022-06-08
 */

@Excel("mdl_b_medicalchina")
@Data
@Accessors(chain = true)
public class MdlBMedicalchina implements Serializable{

private static final long serialVersionUID=1L;

                            
    private String id;

    /**
     * 序号
     */

    @ExcelField(value ="序号")
    private String code;
    /**
     * 技术名称
     */
        
        @ExcelField(value ="技术名称")
    private String name;





    /**
     * 附件ID
     */
        
        @ExcelField(value ="授权操作人员")
    private String fileId;

    /**
     * 备注
     */

    @ExcelField(value ="备注")
    private String note;

    /**
     * 附件地址
     */

    private String fileUrl;

    /**
     * 状态
     */

    private Integer state;

    /**
     * 是否删除
     */
    @TableField("IS_DELETEMARK")

    private Integer isDeletemark;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
        

    private Date createTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;

    /**
     * 修改时间
     */
    @TableField("MODIFY_TIME")
        

    private Date modifyTime;
    private transient String modifyTimeFrom;
    private transient String modifyTimeTo;

    /**
     * 创建人
     */
    @TableField("CREATE_USER_ID")
        

    private Long createUserId;

    /**
     * 修改人
     */
    @TableField("MODIFY_USER_ID")
        

    private Long modifyUserId;



    public static final String ID ="id" ;

    public static final String NAME ="name" ;

    public static final String CODE ="code" ;

    public static final String NOTE ="note" ;

    public static final String FILE_ID ="file_id" ;

    public static final String FILE_URL ="file_url" ;

    public static final String STATE ="state" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

        }