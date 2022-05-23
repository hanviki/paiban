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
 * @since 2022-05-23
 */

@Excel("mdl_b_badrecord")
@Data
@Accessors(chain = true)
public class MdlBBadrecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)

    private Long id;

    /**
     * 人员类型
     */

    @ExcelField(value = "人员类型")
    private String type;

    /**
     * 编写姓名
     */

    @ExcelField(value = "编写姓名")
    private String name;

    /**
     * 编写账号
     */

    @ExcelField(value = "编写账号")
    private String userName;

    /**
     * 发薪号
     */

    @ExcelField(value = "发薪号")
    private String userAccount;

    /**
     * 工号
     */

    @ExcelField(value = "工号")
    private String yggh;

    /**
     * 医务人员姓名
     */

    @ExcelField(value = "医务人员姓名")
    private String userAccountName;

    /**
     * 记分部门
     */

    @ExcelField(value = "记分部门")
    private String deptName;

    /**
     * 所在部门
     */

    @ExcelField(value = "所在部门")
    private String deptNew;

    /**
     * 人事子范围
     */

    @ExcelField(value = "人事子范围")
    private String rszfw;

    /**
     * 记分人员
     */

    @ExcelField(value = "记分人员")
    private String jfPerson;

    /**
     * 年度
     */

    @ExcelField(value = "年度")
    private Integer year;

    /**
     * 大类
     */

    @ExcelField(value = "大类")
    private String lb;

    /**
     * 编码
     */

    @ExcelField(value = "编码")
    private String code;

    /**
     * 记分指标
     */

    @ExcelField(value = "记分指标")
    private String indict;

    /**
     * 具体事由
     */

    @ExcelField(value = "具体事由")
    private String note;

    /**
     * 记分额度
     */

    @ExcelField(value = "记分额度")
    private Integer score;

    /**
     * 记录时间
     */

    @ExcelField(value = "记录时间", writeConverter = DateConverter.class, readConverter = DateReadConverter.class)
    private Date startDate;
    private transient String startDateFrom;
    private transient String startDateTo;

    /**
     * 附件ID
     */

    @ExcelField(value = "附件ID")
    private String fileId;

    /**
     * 附件地址
     */

    @ExcelField(value = "附件地址")
    private String fileUrl;

    /**
     * 状态
     */

    @ExcelField(value = "状态")
    private Integer state;

    /**
     * 是否删除
     */
    @TableField("IS_DELETEMARK")

    @ExcelField(value = "是否删除")
    private Integer isDeletemark;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")

    @ExcelField(value = "创建时间", writeConverter = DateConverter.class, readConverter = DateReadConverter.class)
    private Date createTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;

    /**
     * 修改时间
     */
    @TableField("MODIFY_TIME")

    @ExcelField(value = "修改时间", writeConverter = DateConverter.class, readConverter = DateReadConverter.class)
    private Date modifyTime;
    private transient String modifyTimeFrom;
    private transient String modifyTimeTo;

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

    private transient List<MdlBBadrecord> innerData;

    private  transient int year2;

    private transient int score2;



    public static final String ID = "id";

    public static final String TYPE = "type";

    public static final String NAME = "name";

    public static final String USER_NAME = "user_name";

    public static final String USER_ACCOUNT = "user_account";

    public static final String YGGH = "yggh";

    public static final String USER_ACCOUNT_NAME = "user_account_name";

    public static final String DEPT_NAME = "dept_name";

    public static final String DEPT_NEW = "dept_new";

    public static final String RSZFW = "rszfw";

    public static final String JF_PERSON = "jf_person";

    public static final String YEAR = "year";

    public static final String LB = "lb";

    public static final String CODE = "code";

    public static final String INDICT = "indict";

    public static final String NOTE = "note";

    public static final String SCORE = "score";

    public static final String START_DATE = "start_date";

    public static final String FILE_ID = "file_id";

    public static final String FILE_URL = "file_url";

    public static final String STATE = "state";

    public static final String IS_DELETEMARK = "IS_DELETEMARK";

    public static final String CREATE_TIME = "CREATE_TIME";

    public static final String MODIFY_TIME = "MODIFY_TIME";

    public static final String CREATE_USER_ID = "CREATE_USER_ID";

    public static final String MODIFY_USER_ID = "MODIFY_USER_ID";

}