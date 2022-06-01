package cc.mrbird.febs.xxb.entity;

import java.time.LocalDate;
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
 * @since 2022-05-23
 */

@Excel("xxb_b_result")
@Data
@Accessors(chain = true)
public class XxbBResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * resultId
     */

    @ExcelField(value = "resultId")
    private String id;

    /**
     * 院区
     */

    @ExcelField(value = "院区")
    private String yuanqu;

    /**
     * 申请科室
     */

    @ExcelField(value = "申请科室")
    private String deptNew;

    /**
     * 项目名称
     */

    @ExcelField(value = "项目名称")
    private String projectName;

    /**
     * 项目级别
     */

    @ExcelField(value = "项目级别")
    private Integer projectLevel;

    /**
     * 项目类型
     */

    @ExcelField(value = "项目类型")
    private Integer projectType;

    /**
     * 项目类别
     */

    @ExcelField(value = "项目类别")
    private Integer projectLb;

    /**
     * 是否为限制类医疗技术
     */

    @ExcelField(value = "是否为限制类医疗技术")
    private Integer isxzyljs;

    /**
     * 申请日期
     */

    @ExcelField(value = "申请日期", writeConverter = DateConverter.class, readConverter = DateReadConverter.class)
    private Date applydat;
    private transient String applydatFrom;
    private transient String applydatTo;

    /**
     * 姓名
     */

    @ExcelField(value = "姓名")
    private String userAccountName;

    /**
     * 人事编号
     */

    @ExcelField(value = "人事编号")
    private String userAccount;

    /**
     * 员工工号
     */

    @ExcelField(value = "员工工号")
    private String yggh;

    /**
     * 性别
     */

    @ExcelField(value = "性别")
    private String sexName;

    /**
     * 出生年月
     */

    @ExcelField(value = "出生年月")
    private String birthday;

    /**
     * 用户科室
     */

    @ExcelField(value = "用户科室")
    private String userdept;

    /**
     * 学历、学位
     */

    @ExcelField(value = "学历、学位")
    private String edu;

    /**
     * 职称
     */

    @ExcelField(value = "职称")
    private String zhicheng;

    /**
     * 临床职称
     */

    @ExcelField(value = "临床职称")
    private String zhichenglc;

    /**
     * 职务
     */

    @ExcelField(value = "职务")
    private String zhiwu;

    /**
     * 专业
     */

    @ExcelField(value = "专业")
    private String zhuany;

    /**
     * 专长
     */

    @ExcelField(value = "专长")
    private String zhuanc;

    /**
     * 手机号码
     */

    @ExcelField(value = "手机号码")
    private String telephone;

    /**
     * 开展日期
     */

    @ExcelField(value = "开展日期", writeConverter = DateConverter.class, readConverter = DateReadConverter.class)
    private Date kzsrtdat;
    private transient String kzsrtdatFrom;
    private transient String kzsrtdatTo;

    /**
     * 开展例数
     */

    @ExcelField(value = "开展例数")
    private Integer kzls;

    /**
     * 项目的主要内容及意义
     */

    @ExcelField(value = "项目的主要内容及意义")
    private String projectcontent;

    /**
     * 项目的科学依据
     */

    @ExcelField(value = "项目的科学依据")
    private String projectkxyj;

    /**
     * 项目采取的方法、技术路线
     */

    @ExcelField(value = "项目采取的方法、技术路线")
    private String projectffjslx;

    /**
     * 项目解决的关键问题
     */

    @ExcelField(value = "项目解决的关键问题")
    private String projectkey;

    /**
     * 技术存在的主要风险及预案
     */

    @ExcelField(value = "技术存在的主要风险及预案")
    private String jsfxya;

    /**
     * 项目产生的经济效益、社会效益
     */

    @ExcelField(value = "项目产生的经济效益、社会效益")
    private String projectjjshxy;

    /**
     * 主要技术文献目录及出处
     */

    @ExcelField(value = "主要技术文献目录及出处")
    private String jswxmlcc;

    /**
     * 新闻报道情况
     */

    @ExcelField(value = "新闻报道情况")
    private String newbdqk;

    /**
     * 项目开展情况
     */

    @ExcelField(value = "项目开展情况")
    private String projectkzqk;

    /**
     * 附件名称
     */

    @ExcelField(value = "附件名称")
    private String filename;

    /**
     * 附件地址
     */

    @ExcelField(value = "附件地址")
    private String fileurl;

    /**
     * 备注
     */
    @TableField("COMMENTS")

    @ExcelField(value = "备注")
    private String comments;

    /**
     * 审核状态
     */
    @TableField("STATE")

    @ExcelField(value = "审核状态")
    private Integer state;

    /**
     * 是否删除
     */
    @TableField("IS_DELETEMARK")

    @ExcelField(value = "是否删除")
    private Integer isDeletemark;

    /**
     * 修改时间
     */
    @TableField("MODIFY_TIME")

    @ExcelField(value = "修改时间", writeConverter = DateConverter.class, readConverter = DateReadConverter.class)
    private Date modifyTime;
    private transient String modifyTimeFrom;
    private transient String modifyTimeTo;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")

    @ExcelField(value = "创建时间", writeConverter = DateConverter.class, readConverter = DateReadConverter.class)
    private Date createTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;

    /**
     * 创建人
     */
    @TableField("CREATE_USER_ID")

    @ExcelField(value = "创建人")
    private Long createUserId;

    /**
     * 修改人
     */
    @TableField("MODIFY_USER_ID")

    @ExcelField(value = "修改人")
    private Long modifyUserId;

    /**
     * 流程数
     */

    @ExcelField(value = "流程数")
    private Integer flownum;

    /**
     * 分数
     */

    @ExcelField(value = "分数")
    private Double abtotal;

    /**
     * 国际领先
     */

    @ExcelField(value = "国际领先")
    private Integer gjlx;

    /**
     * 国际先进
     */

    @ExcelField(value = "国际先进")
    private Integer gjxj;

    /**
     * 国内领先
     */

    @ExcelField(value = "国内领先")
    private Integer gnlx;

    /**
     * 国内先进
     */

    @ExcelField(value = "国内先进")
    private Integer gnxj;

    /**
     * 省内领先
     */

    @ExcelField(value = "省内领先")
    private Integer snlx;

    /**
     * 省内先进
     */

    @ExcelField(value = "省内先进")
    private Integer snxj;

    /**
     * 院内评审得分
     */

    @ExcelField(value = "院内评审得分")
    private Double yntotal;

    /**
     * 院外评审得分
     */

    @ExcelField(value = "院外评审得分")
    private Double ywtotal;


    /**
     * 导入分数日期
     */

    @ExcelField(value = "导入分数日期", writeConverter = DateConverter.class, readConverter = DateReadConverter.class)
    private Date importdat;
    private transient String importdatFrom;
    private transient String importdatTo;

    /**
     * 评分年度
     */

    @ExcelField(value = "评分年度")
    private String pfnd;

    /**
     * 获奖情况
     */

    @ExcelField(value = "获奖情况")
    private String hjqk;


    public static final String ID = "id";

    public static final String YUANQU = "yuanqu";

    public static final String DEPT_NEW = "dept_new";

    public static final String PROJECT_NAME = "project_name";

    public static final String PROJECT_LEVEL = "project_level";

    public static final String PROJECT_TYPE = "project_type";

    public static final String PROJECT_LB = "project_lb";

    public static final String ISXZYLJS = "isxzyljs";

    public static final String APPLYDAT = "applydat";

    public static final String USER_ACCOUNT_NAME = "user_account_name";

    public static final String USER_ACCOUNT = "user_account";

    public static final String YGGH = "yggh";

    public static final String SEX_NAME = "sex_name";

    public static final String BIRTHDAY = "birthday";

    public static final String USERDEPT = "userdept";

    public static final String EDU = "edu";

    public static final String ZHICHENG = "zhicheng";

    public static final String ZHICHENGLC = "zhichenglc";

    public static final String ZHIWU = "zhiwu";

    public static final String ZHUANY = "zhuany";

    public static final String ZHUANC = "zhuanc";

    public static final String TELEPHONE = "telephone";

    public static final String KZSRTDAT = "kzsrtdat";

    public static final String KZLS = "kzls";

    public static final String PROJECTCONTENT = "projectcontent";

    public static final String PROJECTKXYJ = "projectkxyj";

    public static final String PROJECTFFJSLX = "projectffjslx";

    public static final String PROJECTKEY = "projectkey";

    public static final String JSFXYA = "jsfxya";

    public static final String PROJECTJJSHXY = "projectjjshxy";

    public static final String JSWXMLCC = "jswxmlcc";

    public static final String NEWBDQK = "newbdqk";

    public static final String PROJECTKZQK = "projectkzqk";

    public static final String FILENAME = "filename";

    public static final String FILEURL = "fileurl";

    public static final String COMMENTS = "COMMENTS";

    public static final String STATE = "STATE";

    public static final String IS_DELETEMARK = "IS_DELETEMARK";

    public static final String MODIFY_TIME = "MODIFY_TIME";

    public static final String CREATE_TIME = "CREATE_TIME";

    public static final String CREATE_USER_ID = "CREATE_USER_ID";

    public static final String MODIFY_USER_ID = "MODIFY_USER_ID";

    public static final String FLOWNUM = "flownum";

    public static final String ABTOTAL = "abtotal";

    public static final String GJLX = "gjlx";

    public static final String GJXJ = "gjxj";

    public static final String GNLX = "gnlx";

    public static final String GLXJ = "glxj";

    public static final String SNLX = "snlx";

    public static final String SNXJ = "snxj";

    public static final String YNTOTAL = "yntotal";

    public static final String YWTOTAL = "ywtotal";

    public static final String IMPORTDAT = "importdat";

    public static final String PFND = "pfnd";

    public static final String HJQK = "hjqk";

}