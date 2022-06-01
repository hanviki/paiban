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
public class XxbBResultInfo {

    private static final long serialVersionUID = 1L;

    /**
     * resultId
     */

    private String id;

    /**
     * 院区
     */

    private String yuanqu;

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

//    private Integer projectLevel;

    /**
     * 项目类型
     */

//    private Integer projectType;

    /**
     * 项目类别
     */

//    private Integer projectLb;

    /**
     * 是否为限制类医疗技术
     */

//    private Integer isxzyljs;

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

//    private String zhiwu;

    /**
     * 专业
     */

//    private String zhuany;

    /**
     * 专长
     */

//    private String zhuanc;

    /**
     * 手机号码
     */

    private String telephone;

    /**
     * 开展日期
     */

    private Date kzsrtdat;

    /**
     * 开展例数
     */

    private Integer kzls;

    /**
     * 项目的主要内容及意义
     */

    private String projectcontent;

    /**
     * 项目的科学依据
     */

    private String projectkxyj;

    /**
     * 项目采取的方法、技术路线
     */

    private String projectffjslx;

    /**
     * 项目解决的关键问题
     */

    private String projectkey;

    /**
     * 技术存在的主要风险及预案
     */

    private String jsfxya;

    /**
     * 项目产生的经济效益、社会效益
     */

    private String projectjjshxy;

    /**
     * 主要技术文献目录及出处
     */

    private String jswxmlcc;

    /**
     * 新闻报道情况
     */

    private String newbdqk;

    /**
     * 项目开展情况
     */

    private String projectkzqk;


    /**
     * 备注
     */
    private String comments;

    /**
     * 审核状态
     */
    private Integer state;

    /**
     * 流程数
     */

    private Integer flownum;

    /**
     * 分数
     */

    private Double abtotal;

    /**
     * 国际领先
     */

    private Integer gjlx;

    /**
     * 国际先进
     */

    private Integer gjxj;

    /**
     * 国内领先
     */

    private Integer gnlx;

    /**
     * 国内先进
     */

    private Integer glxj;

    /**
     * 省内领先
     */

    private Integer snlx;

    /**
     * 省内先进
     */

    private Integer snxj;

    List<XxbBResultD> resultDataList;
}