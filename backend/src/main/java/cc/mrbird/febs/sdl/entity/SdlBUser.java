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
 * @since 2021-10-13
 */

@Excel("sdl_b_user")
@Data
@Accessors(chain = true)
public class SdlBUser implements Serializable{

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
     * 0是一直班
     */
        
        @ExcelField(value ="0是一直班")
    private Integer userType;

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

    /**
     * 拟聘岗位职务
     */
        
        @ExcelField(value ="拟聘岗位职务")
    private String npPositionName;

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
     * 现专业技术岗位
     */
        
        @ExcelField(value ="现专业技术岗位")
    private String zyjsgw;

    /**
     * 现专业技术岗位（临床）
     */
        
        @ExcelField(value ="现专业技术岗位（临床）")
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
     * 本人排名
     */
        
        @ExcelField(value ="本人排名")
    private Integer patentRanknum;

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
     * 是否转让
     */
        
        @ExcelField(value ="是否转让")
    private String isZhuanrang;

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
     * 是否用于本次评审
     */
    @TableField("IsUse")
        
        @ExcelField(value ="是否用于本次评审")
    private Boolean IsUse;

    /**
     * 聘任时间（临床）
     */
        
        @ExcelField(value ="聘任时间（临床）")
    private String appointedDateLc;

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
     * 担(兼)任党政职务
     */
        
        @ExcelField(value ="担(兼)任党政职务")
    private String djrdzzw;

    /**
     * 是否通过初级考核
     */
        
        @ExcelField(value ="是否通过初级考核")
    private Boolean isChujikh;

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
     * 政治面貌
     */
        
        @ExcelField(value ="政治面貌")
    private String politicalStatus;

    /**
     * 职员职级
     */
        
        @ExcelField(value ="职员职级")
    private String staffGrade;

    /**
     * 身份证号
     */
        
        @ExcelField(value ="身份证号")
    private String idCard;

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

        
    private String dcaYear;



    public static final String ID ="id" ;

    public static final String USER_ACCOUNT_NAME ="user_account_name" ;

    public static final String USER_ACCOUNT ="user_account" ;

    public static final String BQ_NAME ="bq_name" ;

    public static final String USER_TYPE ="user_type" ;

    public static final String DEPT_ID ="dept_id" ;

    public static final String TELEPHONE ="telephone" ;

    public static final String DEPT_NAME ="dept_name" ;

    public static final String POSITION_NAME ="position_name" ;

    public static final String NP_POSITION_NAME ="np_position_name" ;

    public static final String SEX_NAME ="sex_name" ;

    public static final String BIRTHDAY ="birthday" ;

    public static final String SCHOOL_DATE ="school_date" ;

    public static final String ZYJSGW ="zyjsgw" ;

    public static final String ZYJSGW_LC ="zyjsgw_lc" ;

    public static final String XCSZYJZC ="xcszyjzc" ;

    public static final String APPOINTED_DATE ="appointed_date" ;

    public static final String PATENT_RANKNUM ="patent_ranknum" ;

    public static final String IS_AUTHORITY ="is_authority" ;

    public static final String FILE_ID ="file_id" ;

    public static final String IS_ZHUANRANG ="is_zhuanrang" ;

    public static final String FILE_URL ="file_url" ;

    public static final String PATENT_GOOD ="patent_good" ;

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

    public static final String ISUSE ="IsUse" ;

    public static final String APPOINTED_DATE_LC ="appointed_date_lc" ;

    public static final String FILE_ID_LC ="file_id_lc" ;

    public static final String FILE_URL_LC ="file_url_lc" ;

    public static final String YUANGONGZU ="yuangongzu" ;

    public static final String XRGWJB ="xrgwjb" ;

    public static final String XRGWJBPRSJ ="xrgwjbprsj" ;

    public static final String DJRDZZW ="djrdzzw" ;

    public static final String IS_CHUJIKH ="is_chujikh" ;

    public static final String CHUJIKH_DATE ="chujikh_date" ;

    public static final String IS_ZHONGJIKH ="is_zhongjikh" ;

    public static final String ZHONGJIKH_DATE ="zhongjikh_date" ;

    public static final String POLITICAL_STATUS ="political_status" ;

    public static final String STAFF_GRADE ="staff_grade" ;

    public static final String ID_CARD ="id_card" ;

    public static final String STAFF_DATE ="staff_date" ;

    public static final String PICTURE_ID ="picture_id" ;

    public static final String PICTURE_URL ="picture_url" ;

    public static final String GQPXQK ="gqpxqk" ;

    public static final String GFHYSPXQK ="gfhyspxqk" ;

    public static final String ZJSPNLCEQK ="zjspnlceqk" ;

    public static final String DCA_YEAR ="dca_year" ;

        }