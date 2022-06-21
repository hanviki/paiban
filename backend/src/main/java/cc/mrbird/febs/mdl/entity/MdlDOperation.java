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
 * 操作目录
 * </p>
 *
 * @author viki
 * @since 2022-06-09
 */

@Excel("mdl_d_operation")
@Data
@Accessors(chain = true)
public class MdlDOperation implements Serializable{

private static final long serialVersionUID=1L;

                    @TableId(value = "id" , type = IdType.AUTO)
                
    private Long id;

    /**
     * 科室
     */
        
        @ExcelField(value ="科室")
    private String deptNew;

    /**
     * 编码
     */
        
        @ExcelField(value ="编码")
    private String code;

    /**
     * 操作名称
     */
        
        @ExcelField(value ="操作名称")
    private String name;

    /**
     * 操作等级
     */
        
        @ExcelField(value ="操作等级")
    private String level;

    /**
     * 手术切口类别
     */
        
        @ExcelField(value ="操作类别")
    private String lb;

    /**
     * 备注
     */
        
        @ExcelField(value ="备注")
    private String note;

    /**
     * 启用时间
     */
        
        @ExcelField(value ="启用时间", writeConverter = DateConverter.class,readConverter = DateReadConverter.class)
    private Date startDate;
    private transient String startDateFrom;
    private transient String startDateTo;

    /**
     * 停用时间
     */
        
        @ExcelField(value ="停用时间", writeConverter = DateConverter.class,readConverter = DateReadConverter.class)
    private Date endDate;
    private transient String endDateFrom;
    private transient String endDateTo;

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

    public static final String DEPT_NEW ="dept_new" ;

    public static final String CODE ="code" ;

    public static final String NAME ="name" ;

    public static final String LEVEL ="level" ;

    public static final String LB ="lb" ;

    public static final String NOTE ="note" ;

    public static final String START_DATE ="start_date" ;

    public static final String END_DATE ="end_date" ;

    public static final String STATE ="state" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

        }