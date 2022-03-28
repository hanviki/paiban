package cc.mrbird.febs.sdl.entity;

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
 * @since 2021-10-18
 */

@Excel("sdl_b_user")
@Data
@Accessors(chain = true)
public class SdlBUserMg implements Serializable{

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */

    @ExcelField(value ="主键")
    private String id;

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
     * 病区名称
     */

    @ExcelField(value ="病区名称")
    private String bqName;

    /**
     * 资质类型id
     */

    @ExcelField(value ="资质类型id")
    private Integer userType;

    /**
     * 资质类型
     */

    @ExcelField(value ="资质类型")
    private String userTypeName;

    /**
     * 科室ID
     */

    @ExcelField(value ="科室ID")
    private String deptId;

    /**
     * 手机号
     */

    @ExcelField(value ="手机号")
    private String telephone;

    /**
     * 科室名称
     */

    @ExcelField(value ="科室名称")
    private String deptName;

    /**
     * 现岗位职务
     */

    @ExcelField(value ="现岗位职务")
    private String positionName;

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

    /**
     * 拟聘岗位职务
     */

    @ExcelField(value ="拟聘岗位职务")
    private String npPositionName;

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
     * 资质证号
     */

    @ExcelField(value ="资质证号")
    private String qualifyNo;

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
    private  transient  String schoolDateFrom;
    private  transient  String schoolDateTo;
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
     * 是否授权
     */

    @ExcelField(value ="是否授权")
    private String isAuthority;

    /**
     * 附件
     */

    @ExcelField(value ="附件")
    private String fileId;

    /**
     * 是否用于本次评审
     */
    @TableField("IsUse")

    @ExcelField(value ="是否用于本次评审")
    private Boolean IsUse;

    /**
     * 附件地址
     */

    @ExcelField(value ="附件地址")
    private String fileUrl;

    /**
     * 转让效益
     */

    @ExcelField(value ="转让效益")
    private String patentGood;

    /**
     * 本人排名
     */

    @ExcelField(value ="本人排名")
    private Integer patentRanknum;

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
     * 排序
     */

    @ExcelField(value ="排序")
    private Integer displayIndex;

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
     * 审核人
     */
    @TableField("auditMan")

    @ExcelField(value ="审核人")
    private String auditMan;

    /**
     * neipinqingkuang
     */
    @TableField("auditManName")

    @ExcelField(value ="neipinqingkuang")
    private String auditManName;

    /**
     * 审核时间
     */
    @TableField("auditDate")

    @ExcelField(value ="审核时间", writeConverter = DateConverter.class)
    private Date auditDate;
    private transient String auditDateFrom;
    private transient String auditDateTo;

    /**
     * 审核意见
     */
    @TableField("auditSuggestion")

    @ExcelField(value ="审核意见")
    private String auditSuggestion;

    /**
     * 附件（临床）
     */

    @ExcelField(value ="附件（临床）")
    private String fileIdLc;

    /**
     * 附件地址（临床）
     */

    @ExcelField(value ="附件地址（临床）")
    private String fileUrlLc;

    /**
     * 担(兼)任党政职务
     */

    @ExcelField(value ="新科室")
    private String deptNew;

    /**
     * 是否通过初级考核
     */

    @ExcelField(value ="是否通过初级考核")
    private String docType;

    /**
     * 通过初级考核时间
     */

    @ExcelField(value ="通过初级考核时间")
    private String chujikhDate;

    /**
     * 是否通过中级考核
     */

    @ExcelField(value ="是否通过中级考核")
    private Boolean isZhongjikh;

    /**
     * 通过中级考核时间
     */

    @ExcelField(value ="通过中级考核时间")
    private String zhongjikhDate;

    /**
     * 职位时间
     */

    @ExcelField(value ="职位时间")
    private String staffDate;

    /**
     * 照片id
     */

    @ExcelField(value ="照片id")
    private String pictureId;

    /**
     * 照片url
     */

    @ExcelField(value ="照片url")
    private String pictureUrl;

    /**
     * 岗前培训情况
     */

    @ExcelField(value ="岗前培训情况")
    private String gqpxqk;

    /**
     * 规范化医师培训情况
     */

    @ExcelField(value ="规范化医师培训情况")
    private String gfhyspxqk;

    /**
     * 中级水平能力测试情况
     */

    @ExcelField(value ="中级水平能力测试情况")
    private String zjspnlceqk;

    /**
     * 年度
     */

    @ExcelField(value ="年度")
    private String dcaYear;



    public static final String ID ="id" ;

    public static final String USER_ACCOUNT_NAME ="user_account_name" ;

    public static final String USER_ACCOUNT ="user_account" ;

    public static final String BQ_NAME ="bq_name" ;

    public static final String USER_TYPE ="user_type" ;

    public static final String USER_TYPE_NAME ="user_type_name" ;

    public static final String DEPT_ID ="dept_id" ;

    public static final String TELEPHONE ="telephone" ;

    public static final String DEPT_NAME ="dept_name" ;

    public static final String POSITION_NAME ="position_name" ;

    public static final String NP_POSITION_NAME ="np_position_name" ;

    public static final String RYLX ="rylx" ;

    public static final String YGGH ="yggh" ;

    public static final String GH_HK ="gh_hk" ;

    public static final String SEX_NAME ="sex_name" ;

    public static final String QUALIFY_NO ="qualify_no" ;

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

    public static final String IS_AUTHORITY ="is_authority" ;

    public static final String FILE_ID ="file_id" ;

    public static final String ISUSE ="IsUse" ;

    public static final String FILE_URL ="file_url" ;

    public static final String PATENT_GOOD ="patent_good" ;

    public static final String PATENT_RANKNUM ="patent_ranknum" ;

    public static final String STATE ="state" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String DISPLAY_INDEX ="display_index" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

    public static final String AUDITMAN ="auditMan" ;

    public static final String AUDITMANNAME ="auditManName" ;

    public static final String AUDITDATE ="auditDate" ;

    public static final String AUDITSUGGESTION ="auditSuggestion" ;

    public static final String FILE_ID_LC ="file_id_lc" ;

    public static final String FILE_URL_LC ="file_url_lc" ;

    public static final String DJRDZZW ="djrdzzw" ;

    public static final String IS_CHUJIKH ="is_chujikh" ;

    public static final String CHUJIKH_DATE ="chujikh_date" ;

    public static final String IS_ZHONGJIKH ="is_zhongjikh" ;

    public static final String ZHONGJIKH_DATE ="zhongjikh_date" ;

    public static final String STAFF_DATE ="staff_date" ;

    public static final String PICTURE_ID ="picture_id" ;

    public static final String PICTURE_URL ="picture_url" ;

    public static final String GQPXQK ="gqpxqk" ;

    public static final String GFHYSPXQK ="gfhyspxqk" ;

    public static final String ZJSPNLCEQK ="zjspnlceqk" ;

    public static final String DCA_YEAR ="dca_year" ;

}