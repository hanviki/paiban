package cc.mrbird.febs.xxb.entity;

import java.time.LocalDate;
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
 * @since 2022-04-25
 */

@Excel("xxb_b_check")
@Data
@Accessors(chain = true)
public class XxbBCheck implements Serializable{

private static final long serialVersionUID=1L;

    /**
     * checkId
     */
                            
        @ExcelField(value ="checkId")
    private String id;

    /**
     * 院区
     */

    @ExcelField(value ="院区")
    private String yuanqu;


    /**
     * 申请科室
     */
        
        @ExcelField(value ="申请科室")
    private String deptNew;

    /**
     * 项目名称
     */
        
        @ExcelField(value ="项目名称")
    private String projectName;

    /**
     * 项目级别
     */
        
        @ExcelField(value ="项目级别")
    private Integer projectLevel;

    /**
     * 项目类型
     */
        
        @ExcelField(value ="项目类型")
    private Integer projectType;

    /**
     * 项目类别
     */
        
        @ExcelField(value ="项目类别")
    private Integer projectLb;

    /**
     * 是否为限制类医疗技术
     */
        
        @ExcelField(value ="是否为限制类医疗技术")
    private Integer isxzyljs;

    /**
     * 申请日期
     */
        
        @ExcelField(value ="申请日期", writeConverter = DateConverter.class,readConverter = DateReadConverter.class)
    private Date applydat;
    private transient String applydatFrom;
    private transient String applydatTo;

    /**
     * 姓名
     */
        
        @ExcelField(value ="姓名")
    private String userAccountName;

    /**
     * 人事编号
     */
        
        @ExcelField(value ="人事编号")
    private String userAccount;

    /**
     * 员工工号
     */
        
        @ExcelField(value ="员工工号")
    private String yggh;

    /**
     * 性别
     */
        
        @ExcelField(value ="性别")
    private String sexName;

    /**
     * 出生年月
     */
        
        @ExcelField(value ="出生年月")
    private String birthday;

    /**
     * 用户科室
     */
        
        @ExcelField(value ="用户科室")
    private String userdept;

    /**
     * 学历、学位
     */
        
        @ExcelField(value ="学历、学位")
    private String edu;

    /**
     * 职称
     */
        
        @ExcelField(value ="职称")
    private String zhicheng;

    /**
     * 临床职称
     */
        
        @ExcelField(value ="临床职称")
    private String zhichenglc;

    /**
     * 职务
     */
        
        @ExcelField(value ="职务")
    private String zhiwu;

    /**
     * 专业
     */
        
        @ExcelField(value ="专业")
    private String zhuany;

    /**
     * 专长
     */
        
        @ExcelField(value ="专长")
    private String zhuanc;

    /**
     * 手机号码
     */
        
        @ExcelField(value ="手机号码")
    private String telephone;

    /**
     * 级别
     */
        
        @ExcelField(value ="级别")
    private Integer lev;

    /**
     * 类别
     */
        
        @ExcelField(value ="类别")
    private Integer typ;

    /**
     * 项目的主要内容及意义
     */
        
        @ExcelField(value ="项目的主要内容及意义")
    private String projectcontent;

    /**
     * 临床应用安全性分析
     */
        
        @ExcelField(value ="临床应用安全性分析")
    private String lincaqfx;

    /**
     * 技术临床应用有效性分析
     */
        
        @ExcelField(value ="技术临床应用有效性分析")
    private String lincyyfx;

    /**
     * 科室已具备的技术和设备条件
     */
        
        @ExcelField(value ="科室已具备的技术和设备条件")
    private String lincjssb;

    /**
     * 适应症
     */
        
        @ExcelField(value ="适应症")
    private String syz;

    /**
     * 禁忌症
     */
        
        @ExcelField(value ="禁忌症")
    private String jjz;

    /**
     * 操作规程
     */
        
        @ExcelField(value ="操作规程")
    private String czgz;

    /**
     * 开始日期
     */
        
        @ExcelField(value ="开始日期", writeConverter = DateConverter.class,readConverter = DateReadConverter.class)
    private Date srtdat;
    private transient String srtdatFrom;
    private transient String srtdatTo;

    /**
     * 结束日期
     */
        
        @ExcelField(value ="结束日期", writeConverter = DateConverter.class,readConverter = DateReadConverter.class)
    private Date enddat;
    private transient String enddatFrom;
    private transient String enddatTo;

    /**
     * 中期日期
     */
        
        @ExcelField(value ="中期日期", writeConverter = DateConverter.class,readConverter = DateReadConverter.class)
    private Date zqDate;
    private transient String zqDateFrom;
    private transient String zqDateTo;

    @ExcelField(value ="延展日期", writeConverter = DateConverter.class,readConverter = DateReadConverter.class)
    private Date yanzhanDate;
    private transient String yanzhanDateFrom;
    private transient String yanzhanDateTo;

    @ExcelField(value ="末期日期", writeConverter = DateConverter.class,readConverter = DateReadConverter.class)
    private Date mqDate;
    private transient String mqDateFrom;
    private transient String mqDateTo;
    /**
     * 上会附件ID
     */
        
        @ExcelField(value ="上会附件ID")
    private String archiveId;

    /**
     * 附件ID
     */
        
        @ExcelField(value ="附件ID")
    private String fileId;

    /**
     * 附件url
     */
        
        @ExcelField(value ="附件url")
    private String fileUrl;

    /**
     * 延长日期
     */
        
        @ExcelField(value ="延长日期", writeConverter = DateConverter.class,readConverter = DateReadConverter.class)
    private Date ycdat;
    private transient String ycdatFrom;
    private transient String ycdatTo;

    /**
     * 上会状态
     */
        
        @ExcelField(value ="上会状态")
    private Integer shstate;

    /**
     * 项目进度状态
     */
        
        @ExcelField(value ="项目进度状态")
    private Integer xmjdstate;

    /**
     * 备注
     */
    @TableField("COMMENTS")
        
        @ExcelField(value ="备注")
    private String comments;

    /**
     * 审核状态
     */
    @TableField("STATE")
        
        @ExcelField(value ="审核状态")
    private Integer state;

    /**
     * 是否删除
     */
    @TableField("IS_DELETEMARK")
        
        @ExcelField(value ="是否删除")
    private Integer isDeletemark;

    /**
     * 修改时间
     */
    @TableField("MODIFY_TIME")
        
        @ExcelField(value ="修改时间", writeConverter = DateConverter.class,readConverter = DateReadConverter.class)
    private Date modifyTime;
    private transient String modifyTimeFrom;
    private transient String modifyTimeTo;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
        
        @ExcelField(value ="创建时间", writeConverter = DateConverter.class,readConverter = DateReadConverter.class)
    private Date createTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;

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

    /**
     * 项目的目的、意义及创新之处
     */
        
        @ExcelField(value ="项目的目的、意义及创新之处")
    private String projectmdyycx;

    /**
     * 国内外研究与应用概况
     */
        
        @ExcelField(value ="国内外研究与应用概况")
    private String gnwyjyygk;

    /**
     * 可行性分析及实施计划
     */
        
        @ExcelField(value ="可行性分析及实施计划")
    private String kxxfxssjh;

    /**
     * 项目可解决的临床问题及效益分析

     */
        
        @ExcelField(value ="项目可解决的临床问题及效益分析")
    private String projectlcwtxyfx;

    /**
     * 操作人员技术培训及岗位职责情况

     */
        
        @ExcelField(value ="操作人员技术培训及岗位职责情况")
    private String ryjjpxgwzzqk;

    /**
     * 可能造成的不良后果、并发症及相应的防范措施

     */
        
        @ExcelField(value ="可能造成的不良后果、并发症及相应的防范措施")
    private String blhgbfzffcs;

    /**
     * 质控措施
     */
        
        @ExcelField(value ="质控措施")
    private String zkcs;

    /**
     * 是否在我院诊疗科目范畴

     */
        
        @ExcelField(value ="是否在我院诊疗科目范畴")
    private String sfwyzlkmfw;

    /**
     * 流程数
     */
        
        @ExcelField(value ="流程数")
    private Integer flownum;


        private  transient List<String> idList;


    public static final String ID ="id" ;

    public static final String DEPT_NEW ="dept_new" ;

    public static final String PROJECT_NAME ="project_name" ;

    public static final String PROJECT_LEVEL ="project_level" ;

    public static final String PROJECT_TYPE ="project_type" ;

    public static final String PROJECT_LB ="project_lb" ;

    public static final String ISXZYLJS ="isxzyljs" ;

    public static final String APPLYDAT ="applydat" ;

    public static final String USER_ACCOUNT_NAME ="user_account_name" ;

    public static final String USER_ACCOUNT ="user_account" ;

    public static final String YGGH ="yggh" ;

    public static final String SEX_NAME ="sex_name" ;

    public static final String BIRTHDAY ="birthday" ;

    public static final String USERDEPT ="userdept" ;

    public static final String EDU ="edu" ;

    public static final String ZHICHENG ="zhicheng" ;

    public static final String ZHICHENGLC ="zhichenglc" ;

    public static final String ZHIWU ="zhiwu" ;

    public static final String ZHUANY ="zhuany" ;

    public static final String ZHUANC ="zhuanc" ;

    public static final String TELEPHONE ="telephone" ;

    public static final String LEV ="lev" ;

    public static final String TYP ="typ" ;

    public static final String PROJECTCONTENT ="projectcontent" ;

    public static final String LINCAQFX ="lincaqfx" ;

    public static final String LINCYYFX ="lincyyfx" ;

    public static final String LINCJSSB ="lincjssb" ;

    public static final String SYZ ="syz" ;

    public static final String JJZ ="jjz" ;

    public static final String CZGZ ="czgz" ;

    public static final String SRTDAT ="srtdat" ;

    public static final String ENDDAT ="enddat" ;

    public static final String ZQ_DATE ="zq_date" ;

    public static final String ARCHIVE_ID ="archive_id" ;

    public static final String FILE_ID ="file_id" ;

    public static final String FILE_URL ="file_url" ;

    public static final String YCDAT ="ycdat" ;

    public static final String SHSTATE ="shstate" ;

    public static final String XMJDSTATE ="xmjdstate" ;

    public static final String COMMENTS ="COMMENTS" ;

    public static final String STATE ="STATE" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

    public static final String PROJECTMDYYCX ="projectmdyycx" ;

    public static final String GNWYJYYGK ="gnwyjyygk" ;

    public static final String KXXFXSSJH ="kxxfxssjh" ;

    public static final String PROJECTLCWTXYFX ="projectlcwtxyfx" ;

    public static final String RYJJPXGWZZQK ="ryjjpxgwzzqk" ;

    public static final String BLHGBFZFFCS ="blhgbfzffcs" ;

    public static final String ZKCS ="zkcs" ;

    public static final String SFWYZLKMFW ="sfwyzlkmfw" ;

    public static final String FLOWNUM ="flownum" ;

        }