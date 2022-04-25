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
 * @since 2022-04-08
 */

@Excel("xxb_b_deptflow")
@Data
@Accessors(chain = true)
public class XxbBDeptflow implements Serializable{

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
    private String flowAccountName;

    /**
     * 人事编号
     */
        
        @ExcelField(value ="人事编号")
    private String flowAccount;

    /**
     * 员工工号
     */
        
        @ExcelField(value ="员工工号")
    private String flowYggh;

    /**
     * 用户科室
     */
        
        @ExcelField(value ="用户科室")
    private String flowdept;

    /**
     * 流程内容
     */
        
        @ExcelField(value ="流程内容")
    private String flowcontent;

    /**
     * 退回流程内容
     */

    @ExcelField(value ="退回流程内容")
    private String backflowcontent;

    /**
     * 流程数
     */
        
        @ExcelField(value ="流程数")
    private Integer flownum;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
        
        @ExcelField(value ="创建时间", writeConverter = DateConverter.class,readConverter = DateReadConverter.class)
    private Date createTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;

    /**
     * 审核状态
     */
    @TableField("STATE")
        
        @ExcelField(value ="审核状态")
    private Integer state;

    /**
     * 结果状态
     */
    @TableField("resultstate")

    @ExcelField(value ="结果状态")
    private Integer resultstate;

    /**
     * 审核时间
     */
    @TableField("flowDate")
        
        @ExcelField(value ="审核时间", writeConverter = DateConverter.class,readConverter = DateReadConverter.class)
    private Date flowDate;
    private transient String flowDateFrom;
    private transient String flowDateTo;

    /**
     * 项目科室ID
     */

    @ExcelField(value ="项目科室ID")
    private String projdeptid;

    /**
     * 是否删除
     */
    @TableField("IS_DELETEMARK")

    @ExcelField(value = "是否删除")
    private Integer isDeletemark;



    public static final String ID ="id" ;

    public static final String PID ="pid" ;

    public static final String FLOW_ACCOUNT_NAME ="flow_account_name" ;

    public static final String FLOW_ACCOUNT ="flow_account" ;

    public static final String FLOW_YGGH ="flow_yggh" ;

    public static final String FLOWDEPT ="flowdept" ;

    public static final String FLOWCONTENT ="flowcontent" ;

    public static final String BACKFLOWCONTENT ="backflowcontent" ;

    public static final String FLOWNUM ="flownum" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String STATE ="STATE" ;

    public static final String RESULTSTATE ="resultstate" ;

    public static final String FLOWDATE ="flowDate" ;

    public static final String PROJDEPTID ="projdeptid" ;

    public static final String IS_DELETEMARK = "IS_DELETEMARK";

        }