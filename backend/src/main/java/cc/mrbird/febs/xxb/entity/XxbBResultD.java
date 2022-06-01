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
 * @since 2022-05-23
 */

@Excel("xxb_b_result_d")
@Data
@Accessors(chain = true)
public class XxbBResultD implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * resultDId
     */

    @ExcelField(value = "resultDId")
    private String id;

    /**
     * resultId
     */

    @ExcelField(value = "resultId")
    private String pid;

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
     * 用户科室
     */

    @ExcelField(value = "用户科室")
    private String userdept;

    /**
     * 年龄
     */

    @ExcelField(value = "年龄")
    private String age;

    /**
     * 电话号码
     */

    @ExcelField(value = "电话号码")
    private String telephone;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")

    @ExcelField(value = "创建时间", writeConverter = DateConverter.class, readConverter = DateReadConverter.class)
    private Date createTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;

    /**
     * 人事子范围
     */

    @ExcelField(value = "人事子范围")
    private String renshizifw;

    /**
     * 是否删除
     */
    @TableField("IS_DELETEMARK")

    @ExcelField(value = "是否删除")
    private Integer isDeletemark;

    /**
     * 序号
     */

    @ExcelField(value = "序号")
    private Integer displayIndex;


    public static final String ID = "id";

    public static final String PID = "pid";

    public static final String USER_ACCOUNT_NAME = "user_account_name";

    public static final String USER_ACCOUNT = "user_account";

    public static final String YGGH = "yggh";

    public static final String SEX_NAME = "sex_name";

    public static final String BIRTHDAY = "birthday";

    public static final String EDU = "edu";

    public static final String ZHICHENG = "zhicheng";

    public static final String ZHICHENGLC = "zhichenglc";

    public static final String ZHIWU = "zhiwu";

    public static final String ZHUANY = "zhuany";

    public static final String ZHUANC = "zhuanc";

    public static final String USERDEPT = "userdept";

    public static final String AGE = "age";

    public static final String TELEPHONE = "telephone";

    public static final String CREATE_TIME = "CREATE_TIME";

    public static final String RENSHIZIFW = "renshizifw";

    public static final String IS_DELETEMARK = "IS_DELETEMARK";

    public static final String DISPLAY_INDEX = "display_index";

}