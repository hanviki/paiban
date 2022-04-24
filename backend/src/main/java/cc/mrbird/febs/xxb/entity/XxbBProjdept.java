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
 * 
 * </p>
 *
 * @author viki
 * @since 2022-04-20
 */

@Excel("xxb_b_projdept")
@Data
@Accessors(chain = true)
public class XxbBProjdept implements Serializable{

private static final long serialVersionUID=1L;

    /**
     * deptflowId
     */
                            
        @ExcelField(value ="deptflowId")
    private String id;

    /**
     * checkId
     */
        
        @ExcelField(value ="checkId")
    private String pid;

    /**
     * 姓名
     */
        
        @ExcelField(value ="姓名")
    private String deptAccountName;

    /**
     * 人事编号
     */
        
        @ExcelField(value ="人事编号")
    private String deptAccount;

    /**
     * 员工工号
     */
        
        @ExcelField(value ="员工工号")
    private String deptYggh;

    /**
     * 科室名称
     */
        
        @ExcelField(value ="科室名称")
    private String deptName;

    /**
     * 贡献度
     */
        
        @ExcelField(value ="贡献度")
    private Double gxd;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
        
        @ExcelField(value ="创建时间", writeConverter = DateConverter.class,readConverter = DateReadConverter.class)
    private Date createTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;

    /**
     * 状态
     */
    @TableField("STATE")
        
        @ExcelField(value ="状态")
    private Integer state;

    /**
     * 是否删除
     */
    @TableField("IS_DELETEMARK")

    @ExcelField(value = "是否删除")
    private Integer isDeletemark;


    public static final String ID ="id" ;

    public static final String PID ="pid" ;

    public static final String DEPT_ACCOUNT_NAME ="dept_account_name" ;

    public static final String DEPT_ACCOUNT ="dept_account" ;

    public static final String DEPT_YGGH ="dept_yggh" ;

    public static final String DEPT_NAME ="dept_name" ;

    public static final String GXD ="gxd" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String STATE ="STATE" ;

    public static final String IS_DELETEMARK = "IS_DELETEMARK";

        }