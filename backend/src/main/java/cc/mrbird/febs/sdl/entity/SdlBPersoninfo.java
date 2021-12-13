package cc.mrbird.febs.sdl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2021-12-10
 */

@Excel("sdl_b_personinfo")
@Data
@Accessors(chain = true)
public class SdlBPersoninfo implements Serializable{

private static final long serialVersionUID=1L;

                    @TableId(value = "id" , type = IdType.AUTO)
                
    private Long id;

    /**
     * 部门ID
     */
        
        @ExcelField(value ="部门ID")
    private String deptId;

    /**
     * 部门名称
     */
        
        @ExcelField(value ="部门名称")
    private String deptName;

    /**
     * 姓名
     */
        
        @ExcelField(value ="姓名")
    private String userAccountName;

    /**
     * 账号
     */
        
        @ExcelField(value ="账号")
    private String userAccount;

    /**
     * 院区
     */
        
        @ExcelField(value ="院区")
    private Integer areaIndex;

    /**
     * 电话
     */
        
        @ExcelField(value ="电话")
    private String telephone;

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



    public static final String ID ="id" ;

    public static final String DEPT_ID ="dept_id" ;

    public static final String DEPT_NAME ="dept_name" ;

    public static final String USER_ACCOUNT_NAME ="user_account_name" ;

    public static final String USER_ACCOUNT ="user_account" ;

    public static final String AREA_INDEX ="area_index" ;

    public static final String TELEPHONE ="telephone" ;

    public static final String STATE ="state" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

        }