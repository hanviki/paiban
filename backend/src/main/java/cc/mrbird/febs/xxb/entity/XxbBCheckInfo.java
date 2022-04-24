package cc.mrbird.febs.xxb.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author viki
 * @since 2022-03-29
 */

@Data
@Accessors(chain = true)
public class XxbBCheckInfo {

    private static final long serialVersionUID = 1L;

    /**
     * checkId
     */

    private String id;

    /**
     * 申请科室
     */

    private String deptNew;

    /**
     * 项目名称
     */

    private String projectName;

    /**
     * 项目级别
     */

    private Integer projectLevel;

    /**
     * 项目类型
     */

    private Integer projectType;

    /**
     * 项目类别
     */

    private Integer projectLb;

    /**
     * 是否为限制类医疗技术
     */

    private Integer isxzyljs;

    /**
     * 申请日期
     */

    private Date applydat;

    /**
     * 姓名
     */

    private String userAccountName;

    /**
     * 人事编号
     */

    private String userAccount;

    /**
     * 员工工号
     */

    private String yggh;

    /**
     * 性别
     */

    private String sexName;

    /**
     * 出生年月
     */

    private String birthday;

    /**
     * 用户科室
     */

    private String userdept;

    /**
     * 学历、学位
     */

    private String edu;

    /**
     * 职称
     */

    private String zhicheng;

    /**
     * 临床职称
     */

    private String zhichenglc;

    /**
     * 职务
     */

    private String zhiwu;

    /**
     * 专业
     */

    private String zhuany;

    /**
     * 专长
     */

    private String zhuanc;

    /**
     * 手机号码
     */

    private String telephone;

    /**
     * 级别
     */

    private Integer lev;

    /**
     * 类别
     */

    private Integer typ;

    /**
     * 项目的主要内容及意义
     */

    private String projectcontent;

    /**
     * 临床应用安全性分析
     */

    private String lincaqfx;

    /**
     * 技术临床应用有效性分析
     */

    private String lincyyfx;

    /**
     * 科室已具备的技术和设备条件
     */

    private String lincjssb;

    /**
     * 适应症
     */

    private String syz;

    /**
     * 禁忌症
     */

    private String jjz;

    /**
     * 操作规程
     */

    private String czgz;

    /**
     * 项目的目的、意义及创新之处
     */

    private String projectmdyycx;

    /**
     * 国内外研究与应用概况
     */

    private String gnwyjyygk;

    /**
     * 可行性分析及实施计划
     */

    private String kxxfxssjh;

    /**
     * 项目可解决的临床问题及效益分析
     */

    private String projectlcwtxyfx;

    /**
     * 操作人员技术培训及岗位职责情况
     */

    private String ryjjpxgwzzqk;

    /**
     * 可能造成的不良后果、并发症及相应的防范措施
     */

    private String blhgbfzffcs;

    /**
     * 质控措施
     */

    private String zkcs;

    /**
     * 是否在我院诊疗科目范畴
     */

    private String sfwyzlkmfw;

    /**
     * 开始日期
     */

    private Date srtdat;

    /**
     * 结束日期
     */

    private Date enddat;

    /**
     * 延长日期
     */

    private Date ycdat;

    /**
     * 上会状态
     */

    private Integer shstate;

    /**
     * 项目进度状态
     */

    private Integer xmjdstate;

    /**
     * 备注
     */
    private String comments;

    /**
     * 审核状态
     */
    private Integer state;

    private Integer flownum;

    List<XxbBCheckD> checkDataList;

    List<XxbBProjdept> projDeptList;
}