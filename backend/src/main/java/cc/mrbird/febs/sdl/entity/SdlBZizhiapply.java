package cc.mrbird.febs.sdl.entity;

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
 * 资质变更申请
 * </p>
 *
 * @author viki
 * @since 2021-10-20
 */

@Excel("sdl_b_zizhiapply")
@Data
@Accessors(chain = true)
public class SdlBZizhiapply implements Serializable{

private static final long serialVersionUID=1L;

    /**
     * id
     */
                            
        @ExcelField(value ="id")
    private String id;

    /**
     * 排班员的发薪号
     */
        
        @ExcelField(value ="排班员的发薪号")
    private String userNo;

    /**
     * 排班员的姓名
     */
        
        @ExcelField(value ="排班员的姓名")
    private String name;

    /**
     * 排班员的科室
     */
        
        @ExcelField(value ="排班员的科室")
    private String userDept;

    /**
     * 申请人的账号
     */
        
        @ExcelField(value ="申请人的账号")
    private String userName;

    /**
     * 被申请人的账号
     */
        
        @ExcelField(value ="被申请人的账号")
    private String userAccount;

    /**
     * 被申请人的姓名
     */
        
        @ExcelField(value ="被申请人的姓名")
    private String userAccountName;

    /**
     * 科室
     */
        
        @ExcelField(value ="科室")
    private String deptName;

    /**
     * 病区
     */
        
        @ExcelField(value ="病区")
    private String bqName;

    /**
     * 科室ID
     */
        
        @ExcelField(value ="科室ID")
    private String deptId;

    /**
     * 申请日期
     */
        
        @ExcelField(value ="申请日期", writeConverter = DateConverter.class)
    private Date applyDate;
    private transient String applyDateFrom;
    private transient String applyDateTo;

    /**
     * 变更类型
     */
        
        @ExcelField(value ="变更类型")
    private String applyType;

    /**
     * 原资质
     */
        
        @ExcelField(value ="原资质")
    private String userTypeName;

    /**
     * 新资质
     */
        
        @ExcelField(value ="新资质")
    private String userNewTypeName;

    /**
     * 资质id
     */
        
        @ExcelField(value ="资质id")
    private Integer userType;

    /**
     * 新资质id
     */
        
        @ExcelField(value ="新资质id")
    private Integer userNewType;

    /**
     * 人员类型
     */
        
        @ExcelField(value ="人员类型")
    private String rylx;

    /**
     * 员工工号
     */
        
        @ExcelField(value ="员工工号")
    private String yggh;

    /**
     * 华科人事编号
     */
        
        @ExcelField(value ="华科人事编号")
    private String ghHk;

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
     * 来校工作时间
     */
        
        @ExcelField(value ="来校工作时间")
    private String schoolDate;

    /**
     * 教学职称
     */
        
        @ExcelField(value ="教学职称")
    private String zyjsgw;

    /**
     * 教学证书编号
     */
        
        @ExcelField(value ="教学证书编号")
    private String bianhaoJx;

    /**
     * 临床职称
     */
        
        @ExcelField(value ="临床职称")
    private String zyjsgwLc;

    /**
     * 现从事专业及专长
     */
        
        @ExcelField(value ="现从事专业及专长")
    private String xcszyjzc;

    /**
     * 聘任时间
     */
        
        @ExcelField(value ="聘任时间")
    private String appointedDate;

    /**
     * 聘任时间（临床）
     */
        
        @ExcelField(value ="聘任时间（临床）")
    private String appointedDateLc;

    /**
     * 员工子组
     */
        
        @ExcelField(value ="员工子组")
    private String zizu;

    /**
     * 员工组
     */
        
        @ExcelField(value ="员工组")
    private String yuangongzu;

    /**
     * 现任岗位级别
     */
        
        @ExcelField(value ="现任岗位级别")
    private String xrgwjb;

    /**
     * 现任岗位级别聘任时间
     */
        
        @ExcelField(value ="现任岗位级别聘任时间")
    private String xrgwjbprsj;

    /**
     * 职称
     */
        
        @ExcelField(value ="职称")
    private String zhicheng;

    /**
     * 内聘临床专业技术职务
     */
        
        @ExcelField(value ="内聘临床专业技术职务")
    private String zyjsNp;

    /**
     * 内聘临床时间
     */
        
        @ExcelField(value ="内聘临床时间")
    private String zyjsDateNp;

    /**
     * 内聘教学专业技术职务
     */
        
        @ExcelField(value ="内聘教学专业技术职务")
    private String zyjsNpjx;

    /**
     * 内聘教学时间
     */
        
        @ExcelField(value ="内聘教学时间")
    private String zyjsDateNpjx;

    /**
     * 学历
     */
        
        @ExcelField(value ="学历")
    private String edu;

    /**
     * 毕业学校
     */
        
        @ExcelField(value ="毕业学校")
    private String eduSchool;

    /**
     * 国籍
     */
        
        @ExcelField(value ="国籍")
    private String guoji;

    /**
     * 民族
     */
        
        @ExcelField(value ="民族")
    private String minzu;

    /**
     * 籍贯
     */
        
        @ExcelField(value ="籍贯")
    private String jiguan;

    /**
     * 政治面貌
     */
        
        @ExcelField(value ="政治面貌")
    private String politicalStatus;

    /**
     * 临床证书编号
     */
        
        @ExcelField(value ="临床证书编号")
    private String bianhaoLc;

    /**
     * 行政级别
     */
        
        @ExcelField(value ="行政级别")
    private String xingzhengjiebie;

    /**
     * 身份证号
     */
        
        @ExcelField(value ="身份证号")
    private String idCard;

    /**
     * 参加工作时间
     */
        
        @ExcelField(value ="参加工作时间")
    private String workDate;

    /**
     * 医师类别
     */
        
        @ExcelField(value ="医师类别")
    private String yishiLb;

    /**
     * 医师级别
     */
        
        @ExcelField(value ="医师级别")
    private String yishiJb;

    /**
     * 医师执业范围
     */
        
        @ExcelField(value ="医师执业范围")
    private String yishiZhiyefanwei;

    /**
     * 医师资格证书编号
     */
        
        @ExcelField(value ="医师资格证书编号")
    private String yishiZgzsbianhao;

    /**
     * 医师执业证书编码
     */
        
        @ExcelField(value ="医师执业证书编码")
    private String yishiZiyebianhao;

    /**
     * 人事子范围
     */
        
        @ExcelField(value ="人事子范围")
    private String renshizifw;

    /**
     * 人事子范围分类
     */
        
        @ExcelField(value ="人事子范围分类")
    private String renshizfenlei;

    /**
     * 备注
     */
        
        @ExcelField(value ="备注")
    private String note;

    /**
     * 审核意见
     */
        
        @ExcelField(value ="审核意见")
    private String auditSuggestion;

    /**
     * 审核时间
     */
        
        @ExcelField(value ="审核时间", writeConverter = DateConverter.class)
    private Date auditDate;
    private transient String auditDateFrom;
    private transient String auditDateTo;

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

    /**
     * 现岗位职务
     */
    @ExcelField(value = "现岗位职务")
    private transient String positionName;

    public String getPositionName() {
        return (zyjsgw == null ? "" : zyjsgw) +(zyjsgw == null||zyjsgw.equals("")?"":"/")+ (zyjsgwLc == null ? "" : zyjsgwLc);
    }

    /**
     * 专业技术岗位时间  appointedDate+ appointedDateLc
     */
    @ExcelField(value = "现职务聘任时间")
    private transient String zygwDate;

    public String getZygwDate() {
        return (appointedDate == null ? "" : appointedDate) +(appointedDate == null||appointedDate.equals("")?"":"/")+ (appointedDateLc == null ? "" : appointedDateLc);
    }




    public static final String ID ="id" ;

    public static final String USER_NO ="user_no" ;

    public static final String NAME ="name" ;

    public static final String USER_DEPT ="user_dept" ;

    public static final String USER_NAME ="user_name" ;

    public static final String USER_ACCOUNT ="user_account" ;

    public static final String USER_ACCOUNT_NAME ="user_account_name" ;

    public static final String DEPT_NAME ="dept_name" ;

    public static final String BQ_NAME ="bq_name" ;

    public static final String DEPT_ID ="dept_id" ;

    public static final String APPLY_DATE ="apply_date" ;

    public static final String APPLY_TYPE ="apply_type" ;

    public static final String USER_TYPE_NAME ="user_type_name" ;

    public static final String USER_NEW_TYPE_NAME ="user_new_type_name" ;

    public static final String USER_TYPE ="user_type" ;

    public static final String USER_NEW_TYPE ="user_new_type" ;

    public static final String RYLX ="rylx" ;

    public static final String YGGH ="yggh" ;

    public static final String GH_HK ="gh_hk" ;

    public static final String SEX_NAME ="sex_name" ;

    public static final String BIRTHDAY ="birthday" ;

    public static final String SCHOOL_DATE ="school_date" ;

    public static final String ZYJSGW ="zyjsgw" ;

    public static final String BIANHAO_JX ="bianhao_jx" ;

    public static final String ZYJSGW_LC ="zyjsgw_lc" ;

    public static final String XCSZYJZC ="xcszyjzc" ;

    public static final String APPOINTED_DATE ="appointed_date" ;

    public static final String APPOINTED_DATE_LC ="appointed_date_lc" ;

    public static final String ZIZU ="zizu" ;

    public static final String YUANGONGZU ="yuangongzu" ;

    public static final String XRGWJB ="xrgwjb" ;

    public static final String XRGWJBPRSJ ="xrgwjbprsj" ;

    public static final String ZHICHENG ="zhicheng" ;

    public static final String ZYJS_NP ="zyjs_np" ;

    public static final String ZYJS_DATE_NP ="zyjs_date_np" ;

    public static final String ZYJS_NPJX ="zyjs_npjx" ;

    public static final String ZYJS_DATE_NPJX ="zyjs_date_npjx" ;

    public static final String EDU ="edu" ;

    public static final String EDU_SCHOOL ="edu_school" ;

    public static final String GUOJI ="guoji" ;

    public static final String MINZU ="minzu" ;

    public static final String JIGUAN ="jiguan" ;

    public static final String POLITICAL_STATUS ="political_status" ;

    public static final String BIANHAO_LC ="bianhao_lc" ;

    public static final String XINGZHENGJIEBIE ="xingzhengjiebie" ;

    public static final String ID_CARD ="id_card" ;

    public static final String WORK_DATE ="work_date" ;

    public static final String YISHI_LB ="yishi_lb" ;

    public static final String YISHI_JB ="yishi_jb" ;

    public static final String YISHI_ZHIYEFANWEI ="yishi_zhiyefanwei" ;

    public static final String YISHI_ZGZSBIANHAO ="yishi_zgzsbianhao" ;

    public static final String YISHI_ZIYEBIANHAO ="yishi_ziyebianhao" ;

    public static final String RENSHIZIFW ="renshizifw" ;

    public static final String RENSHIZFENLEI ="renshizfenlei" ;

    public static final String NOTE ="note" ;

    public static final String AUDIT_SUGGESTION ="audit_suggestion" ;

    public static final String AUDIT_DATE ="audit_date" ;

    public static final String STATE ="state" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

        }