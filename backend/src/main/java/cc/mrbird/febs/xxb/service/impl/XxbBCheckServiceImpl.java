package cc.mrbird.febs.xxb.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.sdl.entity.SdlBUserMg;
import cc.mrbird.febs.sdl.entity.SdlBUserSearch;
import cc.mrbird.febs.sdl.service.ISdlBUserMgService;
import cc.mrbird.febs.system.domain.User;
import cc.mrbird.febs.xxb.entity.*;
import cc.mrbird.febs.xxb.dao.XxbBCheckMapper;
import cc.mrbird.febs.xxb.service.*;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author viki
 * @since 2022-03-29
 */
@Slf4j
@Service("IXxbBCheckService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class XxbBCheckServiceImpl extends ServiceImpl<XxbBCheckMapper, XxbBCheck> implements IXxbBCheckService {

    @Autowired
    IXxbBCheckDService iXxbBCheckDService;

    @Autowired
    IXxbBDeptflowService iXxbBDeptflowService;

    @Autowired
    IXxbBDeptleaderService iXxbBDeptleaderService;

    @Autowired
    IXxbBProjdeptService iXxbBProjdeptService;

    @Autowired
    ISdlBUserMgService iSdlBUserMgService;

    @Override
    public IPage<XxbBCheck> findXxbBChecks(QueryRequest request, XxbBCheck xxbBCheck, User user) {
        try {
            LambdaQueryWrapper<XxbBCheck> queryWrapper = new LambdaQueryWrapper<>();
            String sql = "is_deletemark = 1";
//            queryWrapper.eq(XxbBCheck::getIsDeletemark, 1);//1是未删 0是已删
            if (xxbBCheck.getProjectType() != null) {
//                queryWrapper.eq(XxbBCheck::getProjectType, xxbBCheck.getProjectType());
                sql += " and project_type = " + xxbBCheck.getProjectType();
            }

            if (xxbBCheck.getState() != null) {
//                queryWrapper.eq(XxbBCheck::getState, xxbBCheck.getState());
                sql += " and state = " + xxbBCheck.getState();
            }

            if (StringUtils.isNotBlank(xxbBCheck.getApplydatFrom()) && StringUtils.isBlank(xxbBCheck.getApplydatTo())) {
//                queryWrapper.ge(XxbBCheck::getApplydat, xxbBCheck.getApplydatFrom());
                sql += " and applydat >= '" + xxbBCheck.getApplydatFrom() + "'";
            }

            if (StringUtils.isNotBlank(xxbBCheck.getApplydatFrom()) && StringUtils.isNotBlank(xxbBCheck.getApplydatTo())) {
//                queryWrapper.ge(XxbBCheck::getApplydat, xxbBCheck.getApplydatFrom())
//                        .le(XxbBCheck::getApplydat, xxbBCheck.getApplydatTo());
                sql += " and applydat >= '" + xxbBCheck.getApplydatFrom() + "' and applydat <= '" + xxbBCheck.getApplydatTo() + "'";
            }

            if (StringUtils.isBlank(xxbBCheck.getApplydatFrom()) && StringUtils.isNotBlank(xxbBCheck.getApplydatTo())) {
//                queryWrapper.le(XxbBCheck::getApplydat, xxbBCheck.getApplydatTo());
                sql += " and applydat <= '" + xxbBCheck.getApplydatTo() + "'";
            }

            if (user != null) {
                sql += " and CREATE_USER_ID = " + user.getUserId();
            }

            queryWrapper.apply(sql);

            Page<XxbBCheck> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            this.page(page, queryWrapper);
            this.getData(page.getRecords());
            return page;
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<XxbBCheck> findXxbBflow(QueryRequest request, XxbBCheck xxbBCheck, User user) {
        try {
            LambdaQueryWrapper<XxbBCheck> queryWrapper = new LambdaQueryWrapper<>();
            String sql = "is_deletemark = 1";

            if (StringUtils.isNotBlank(xxbBCheck.getApplydatFrom()) && StringUtils.isBlank(xxbBCheck.getApplydatTo())) {
                sql += " and applydat >= '" + xxbBCheck.getApplydatFrom() + "'";
            }

            if (StringUtils.isNotBlank(xxbBCheck.getApplydatFrom()) && StringUtils.isNotBlank(xxbBCheck.getApplydatTo())) {
                sql += " and applydat >= '" + xxbBCheck.getApplydatFrom() + "' and applydat <= '" + xxbBCheck.getApplydatTo() + "'";
            }

            if (StringUtils.isBlank(xxbBCheck.getApplydatFrom()) && StringUtils.isNotBlank(xxbBCheck.getApplydatTo())) {
                sql += " and applydat <= '" + xxbBCheck.getApplydatTo() + "'";
            }

            if (StringUtils.isNotBlank(xxbBCheck.getComments())) {
                if (xxbBCheck.getComments().equals("科") && xxbBCheck.getState() != null) {
                    if (xxbBCheck.getState() == 1) {
                        //查询 流程方案 1
//                        sql += " and flownum = 1 and state = 1 and dept_new in (" +
//                                "select dept_name from xxb_b_deptleader where user_account = '" + user.getUsername() + "'" +
//                                ")";
                        // 生成流程DeptFlow
                        // 查询 流程方案 2
                        sql += " and flownum = 1 and state = 1 and id in (" +
                                "select pid from xxb_b_deptflow where is_deletemark = 1 and flownum = 1 and state = 0 and flow_account='" + user.getUsername() + "'" +
                                ")";
                    } else {
                        sql += " and id in (" +
                                "select pid from xxb_b_deptflow where is_deletemark = 1 and flownum = 1 and state = 1 and flow_account = '" + user.getUsername() + "'" +
                                ")";
                    }
                }

                if (xxbBCheck.getComments().equals("多") && xxbBCheck.getState() != null) {
                    if (xxbBCheck.getState() == 1) {
                        //查询 流程方案 1
//                        sql += " and state = 1 and flownum = 2 and project_type =2 and id in (" +
//                                "select pid from xxb_b_projdept where is_deletemark = 1 and state = 0 and dept_name in (" +
//                                "select dept_name from xxb_b_deptleader where user_account = '" + user.getUsername() + "'" +
//                                ")" +
//                                ")";
                        // 生成流程DeptFlow
                        // 查询 流程方案 2
                        sql += " and state = 1 and flownum = 2 and project_type =2 and id in (" +
                                "select pid from xxb_b_deptflow where is_deletemark = 1 and flownum = 2 and state = 0 and flow_account='" + user.getUsername() + "'" +
                                ")";
                    } else {
                        sql += " and project_type = 2 and id in (" +
                                "select pid from xxb_b_deptflow where is_deletemark = 1 and flownum = 2 and state = 1 and flow_account = '" + user.getUsername() + "'" +
                                ")";
                    }
                }

                if (xxbBCheck.getComments().equals("医")) {
                    if (xxbBCheck.getState() == 23) {
                        //查询 流程方案 1
//                        sql += " and 'ywcgl' = '" + user.getUsername() + "' and ((flownum = 2 and state = 1 and project_type in (0,1)) or (flownum = 3 and state = 1 and project_type =2))";

                        // 生成流程DeptFlow
                        // 查询 流程方案 2
                        sql += " and state = 1 and ((flownum = 3 and project_type = 2 and id in (" +
                                "select pid from xxb_b_deptflow where is_deletemark = 1 and flownum = 3 and state = 0 and flow_account='" + user.getUsername() + "'" +
                                ")) or (flownum = 2 and project_type in (0,1) and id in (" +
                                "select pid from xxb_b_deptflow where is_deletemark = 1 and flownum = 2 and state = 0 and flow_account='" + user.getUsername() + "'" +
                                ")))";
                    } else {
                        sql += " and ((id in (" +
                                "select pid from xxb_b_deptflow where is_deletemark = 1 and flownum = 2 and state = 1 and flow_account = '" + user.getUsername() + "'" +
                                ") and state > 1 and project_type in (0,1))" +
                                " or (id in (" +
                                "select pid from xxb_b_deptflow where is_deletemark = 1 and flownum = 3 and state = 1 and flow_account = '" + user.getUsername() + "'" +
                                ") and state > 1 and project_type =2))";
                    }
                }
            } else {
                sql += " and 1=2";
            }

            queryWrapper.apply(sql);

            Page<XxbBCheck> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            this.page(page, queryWrapper);
            this.getData(page.getRecords());
            return page;
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    private void getData(List<XxbBCheck> list) {
        if (list.size() > 0) {
            List<String> idList = new ArrayList<>();
            for (XxbBCheck x : list) {
                if (x.getProjectType() == 2)
                    idList.add(x.getId());
            }
            if (idList.size() > 0) {
                LambdaQueryWrapper<XxbBProjdept> wrapper = new LambdaQueryWrapper<>();
                wrapper.in(XxbBProjdept::getPid, idList);
                wrapper.eq(XxbBProjdept::getIsDeletemark, 1);
                List<XxbBProjdept> pdlist = iXxbBProjdeptService.list(wrapper);
                if (pdlist.size() > 0) {
                    List<XxbBProjdept> queryData = new ArrayList<>();
                    String dataDeptName = "";
                    for (XxbBCheck x : list) {
                        if (x.getProjectType() == 2) {
                            queryData = pdlist.stream().filter(s -> s.getPid().equals(x.getId())).collect(Collectors.toList());
                            dataDeptName = "";
                            for (XxbBProjdept p : queryData) {
                                if (dataDeptName.equals("")) {
                                    dataDeptName = p.getDeptName() + "(" + p.getGxd().toString() + ")";
                                } else {
                                    dataDeptName += "," + p.getDeptName() + "(" + p.getGxd().toString() + ")";
                                }
                            }
                            x.setComments(dataDeptName);
                        }
                    }
                }
            }
        }
    }

    @Override
    public IPage<XxbBCheck> findXxbBCheckList(QueryRequest request, XxbBCheck xxbBCheck) {
        try {
            Page<XxbBCheck> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findXxbBCheck(page, xxbBCheck);
        } catch (Exception e) {
            log.error("获取失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createXxbBCheck(XxbBCheck xxbBCheck) {
        xxbBCheck.setId(UUID.randomUUID().toString());
        xxbBCheck.setCreateTime(new Date());
        xxbBCheck.setIsDeletemark(1);
        this.save(xxbBCheck);
    }

    @Override
    @Transactional
    public String editXxbBCheck(XxbBCheckInfo xxbBCheckInfo, User user) {
        XxbBCheck check = new XxbBCheck();
        Date thisDate = new Date();
        XxbBCheck entity = this.getById(xxbBCheckInfo.getId());
        if (entity == null) {
            check.setId(xxbBCheckInfo.getId());
            check.setCreateTime(thisDate);
            check.setCreateUserId(user.getUserId());
            check.setFlownum(0);
            check.setIsDeletemark(1);
        } else {
            check.setId(xxbBCheckInfo.getId());
            check.setModifyTime(thisDate);
            check.setModifyUserId(user.getUserId());
        }

        // dept_new、projct_name、projct_level、projct_type、isxzyljs、applydat、user_account_name、user_account
        check.setDeptNew(xxbBCheckInfo.getDeptNew()); // 申请科室
        check.setProjectName(xxbBCheckInfo.getProjectName());// 项目名称
        check.setProjectLevel(xxbBCheckInfo.getProjectLevel());// 项目级别
        check.setProjectType(xxbBCheckInfo.getProjectType());//项目类型
        check.setProjectLb(xxbBCheckInfo.getProjectLb());//项目类别
        check.setIsxzyljs(xxbBCheckInfo.getIsxzyljs());//是否为限制类医疗技术
        check.setApplydat(xxbBCheckInfo.getApplydat());//申请日期
        check.setUserAccountName(xxbBCheckInfo.getUserAccountName());//姓名
        check.setUserAccount(xxbBCheckInfo.getUserAccount());//人事编码
        // yggh、sex_name、birthday、userdept、edu、zhicheng、zhiwu、zhuany、zhuanc、telephone、lev、typ、projctcontent
        check.setYggh(xxbBCheckInfo.getYggh());//员工工号
        check.setSexName(xxbBCheckInfo.getSexName());//性别
        check.setBirthday(xxbBCheckInfo.getBirthday());//出生年月
        check.setUserdept(xxbBCheckInfo.getUserdept());//员工科室
        check.setEdu(xxbBCheckInfo.getEdu());//学历、学位
        check.setZhichenglc(xxbBCheckInfo.getZhichenglc());//临床职称
        check.setZhicheng(xxbBCheckInfo.getZhicheng());//职称
        check.setZhiwu(xxbBCheckInfo.getZhiwu());//职务
        check.setZhuany(xxbBCheckInfo.getZhuany());//专业
        check.setZhuanc(xxbBCheckInfo.getZhuanc());//专长
        check.setTelephone(xxbBCheckInfo.getTelephone());//电话
        check.setLev(xxbBCheckInfo.getLev());//级别
        check.setTyp(xxbBCheckInfo.getTyp());//类别
        check.setProjectcontent(xxbBCheckInfo.getProjectcontent());//项目的主要内容及意义
        // lincaqfx、lincjssb、syz、jjz、czgz
        check.setLincaqfx(xxbBCheckInfo.getLincaqfx());//临床应用安全性分析
        check.setLincyyfx(xxbBCheckInfo.getLincyyfx());//技术临床应用有效性分析
        check.setLincjssb(xxbBCheckInfo.getLincjssb());//科室已具备的技术和设备条件
        check.setSyz(xxbBCheckInfo.getSyz());//适应症
        check.setJjz(xxbBCheckInfo.getJjz());//禁忌症
        check.setCzgz(xxbBCheckInfo.getCzgz());//操作规程

        check.setProjectmdyycx(xxbBCheckInfo.getProjectmdyycx());///项目的目的、意义及创新之处
        check.setGnwyjyygk(xxbBCheckInfo.getGnwyjyygk());//国内外研究与应用概况
        check.setKxxfxssjh(xxbBCheckInfo.getKxxfxssjh());//可行性分析及实施计划
        check.setProjectlcwtxyfx(xxbBCheckInfo.getProjectlcwtxyfx());//项目可解决的临床问题及效益分析/
        check.setRyjjpxgwzzqk(xxbBCheckInfo.getRyjjpxgwzzqk());//操作人员技术培训及岗位职责情况
        check.setBlhgbfzffcs(xxbBCheckInfo.getBlhgbfzffcs());//可能造成的不良后果、并发症及相应的防范措施
        check.setZkcs(xxbBCheckInfo.getZkcs());//质控措施
        check.setSfwyzlkmfw(xxbBCheckInfo.getSfwyzlkmfw());//是否在我院诊疗科目范畴
        check.setFlownum(xxbBCheckInfo.getFlownum());
        //传入状态
        check.setState(xxbBCheckInfo.getState());

        // srtdat、enddat、ycdat、shstate、xmjdstate、STATE 未赋值
        List<XxbBCheckD> insertData = new ArrayList<>();
        List<XxbBCheckD> updateData = new ArrayList<>();
        List<XxbBProjdept> insertProjDeptData = new ArrayList<>();
        List<XxbBProjdept> updateProjDeptData = new ArrayList<>();

        int nTime = 0;
        // 新增 修改 项目主要人员
        for (XxbBCheckD item : xxbBCheckInfo.getCheckDataList()) {
            XxbBCheckD data = new XxbBCheckD();
            // user_account_name、user_account、yggh、sex_name、birthday、age、edu、zhicw、zhuanyfm、userdept
            data.setUserAccountName(item.getUserAccountName());
            data.setUserAccount(item.getUserAccount());
            data.setYggh(item.getYggh());
            data.setSexName(item.getSexName());
            data.setBirthday(item.getBirthday());
            if (StringUtils.isBlank(data.getAge())) {
                data.setAge(this.getAge(thisDate, item.getBirthday()));
            } else {
                data.setAge(item.getAge());
            }
            data.setEdu(item.getEdu());
            data.setZhicheng(item.getZhicheng());
            data.setZhichenglc(item.getZhichenglc());
            data.setZhiwu(item.getZhiwu());
            data.setZhuany(item.getZhuany());
            data.setZhuanc(item.getZhuanc());
            data.setUserdept(item.getUserdept());
            data.setTelephone(item.getTelephone());
            data.setRenshizifw(item.getRenshizifw());
            if (item.getId() == null) {
                data.setId(UUID.randomUUID().toString());
                data.setIsDeletemark(1);
                data.setPid(check.getId());
                if (nTime == 0) {
                    data.setCreateTime(thisDate);
                } else {
                    data.setCreateTime(this.addSecond(thisDate, nTime));
                }
                nTime += 1;
                insertData.add(data);
            } else {
                data.setId(item.getId());
                data.setPid(check.getId());
                updateData.add(data);
            }
        }
        nTime = 0;
        // 新增 修改 项目参与专科
        for (XxbBProjdept item : xxbBCheckInfo.getProjDeptList()) {
            XxbBProjdept data = new XxbBProjdept();
            data.setDeptName(item.getDeptName());
            data.setGxd(item.getGxd());
            if (item.getId() == null) {
                data.setId(UUID.randomUUID().toString());
                data.setPid(check.getId());
                data.setState(0);
                data.setIsDeletemark(1);
                if (nTime == 0) {
                    data.setCreateTime(thisDate);
                } else {
                    data.setCreateTime(this.addSecond(thisDate, nTime));
                }
                nTime += 1;
                insertProjDeptData.add(data);
            } else {
                data.setId(item.getId());
                data.setPid(check.getId());
                updateProjDeptData.add(data);
            }
        }
        List<XxbBDeptflow> insertDeptFLowList = new ArrayList<>();
        List<String> delType2DeptFlowIdList = new ArrayList<>();
        //生成流程DeptFlow 流程方案 2
        // check.getState() == 1 传入状态
        if (check.getState() == 1) {
            List<XxbBDeptleader> deptLeaderList = this.getDeptLeaderList(null, null);
            if (deptLeaderList.size() > 0) {
                LambdaQueryWrapper<XxbBDeptflow> wrapperDeptFlow = new LambdaQueryWrapper<>();
                wrapperDeptFlow.eq(XxbBDeptflow::getPid, check.getId());
                wrapperDeptFlow.eq(XxbBDeptflow::getIsDeletemark, 1);
                List<XxbBDeptflow> deptFlowList = this.iXxbBDeptflowService.list(wrapperDeptFlow);
                // entity 查询 当前状态 创建时
                if ((entity == null || (entity != null && entity.getState() == 0)) && deptFlowList.size() == 0) {
                    int flowNum = 1;
                    this.insertDeptFLow(insertDeptFLowList, deptLeaderList, check.getDeptNew(), null, check.getId(), null, flowNum, thisDate);
                    flowNum = 2;
                    if (check.getProjectType() == 2) {
                        for (XxbBProjdept item : insertProjDeptData) {
                            this.insertDeptFLow(insertDeptFLowList, deptLeaderList, item.getDeptName(), null, check.getId(), item.getId(), flowNum, thisDate);
                        }

                        for (XxbBProjdept item : updateProjDeptData) {
                            this.insertDeptFLow(insertDeptFLowList, deptLeaderList, item.getDeptName(), null, check.getId(), item.getId(), flowNum, thisDate);
                        }
                        flowNum = 3;
                    }
                    XxbBDeptflow deptflow3 = new XxbBDeptflow();
                    deptflow3.setId(UUID.randomUUID().toString());
                    deptflow3.setPid(check.getId());
                    deptflow3.setFlownum(flowNum);
                    deptflow3.setState(0);
                    deptflow3.setFlowAccount("ywcgl");
                    deptflow3.setFlowYggh("ywcgl");
                    deptflow3.setFlowAccountName("医务办公室");
                    deptflow3.setFlowdept("医务办公室");
                    deptflow3.setCreateTime(thisDate);
                    deptflow3.setIsDeletemark(1);
                    insertDeptFLowList.add(deptflow3);

                }
                // entity 查询 当前状态 退回时
                if (entity != null && entity.getState() == 3 && check.getProjectType() == 2 && deptFlowList.size() > 0) {
                    List<XxbBDeptflow> deptFlowQuery = new ArrayList<>();
                    String backFlowContent = null;
                    int flowNum = 1;
                    deptFlowQuery = deptFlowList.stream().filter(s -> s.getProjdeptid() == null && s.getFlowdept().equals(check.getDeptNew())).collect(Collectors.toList());
                    backFlowContent = deptFlowQuery.size() == 0 ? null : deptFlowQuery.get(0).getFlowcontent();
                    this.insertDeptFLow(insertDeptFLowList, deptLeaderList, check.getDeptNew(), backFlowContent, check.getId(), null, flowNum, thisDate);

                    flowNum = 2;
                    for (XxbBProjdept item : insertProjDeptData) {
                        deptFlowQuery = deptFlowList.stream().filter(s -> s.getProjdeptid() != null && s.getFlowdept().equals(item.getDeptName())).collect(Collectors.toList());
                        backFlowContent = deptFlowQuery.size() == 0 ? null : deptFlowQuery.get(0).getFlowcontent();
                        this.insertDeptFLow(insertDeptFLowList, deptLeaderList, item.getDeptName(), backFlowContent, check.getId(), item.getId(), flowNum, thisDate);
                    }

                    for (XxbBProjdept item : updateProjDeptData) {
                        deptFlowQuery = deptFlowList.stream().filter(s -> s.getProjdeptid() != null && s.getFlowdept().equals(item.getDeptName())).collect(Collectors.toList());
                        backFlowContent = deptFlowQuery.size() == 0 ? null : deptFlowQuery.get(0).getFlowcontent();
                        this.insertDeptFLow(insertDeptFLowList, deptLeaderList, item.getDeptName(), backFlowContent, check.getId(), item.getId(), flowNum, thisDate);
                    }
                    flowNum = 3;
                    XxbBDeptflow deptflow3 = new XxbBDeptflow();
                    deptflow3.setId(UUID.randomUUID().toString());
                    deptflow3.setPid(check.getId());
                    deptflow3.setFlownum(flowNum);
                    deptflow3.setState(0);
                    deptflow3.setFlowAccount("ywcgl");
                    deptflow3.setFlowYggh("ywcgl");
                    deptflow3.setFlowAccountName("医务办公室");
                    deptflow3.setFlowdept("医务办公室");
                    deptflow3.setCreateTime(thisDate);
                    deptflow3.setIsDeletemark(1);
                    insertDeptFLowList.add(deptflow3);

                    for (XxbBDeptflow item : deptFlowList) {
                        delType2DeptFlowIdList.add(item.getId());
                    }
                }
            }
        }
        if (check.getProjectType() == 2 && entity != null && entity.getState() == 3 && delType2DeptFlowIdList.size() > 0) {
            String[] delDeptFlow = delType2DeptFlowIdList.toArray(new String[delType2DeptFlowIdList.size()]);
            iXxbBDeptflowService.deleteXxbBDeptflows(delDeptFlow);
        }

        if (insertData.size() > 0) {
            iXxbBCheckDService.saveBatch(insertData);
        }
        if (updateData.size() > 0) {
            iXxbBCheckDService.updateBatchById(updateData);
        }

        if (insertProjDeptData.size() > 0) {
            iXxbBProjdeptService.saveBatch(insertProjDeptData);
        }
        if (updateProjDeptData.size() > 0) {
            iXxbBProjdeptService.updateBatchById(updateProjDeptData);
        }

        if (insertDeptFLowList.size() > 0) {
            iXxbBDeptflowService.saveBatch(insertDeptFLowList);
        }

        this.saveOrUpdate(check);
        return check.getId();
    }

    private void insertDeptFLow(List<XxbBDeptflow> insertDeptFlow, List<XxbBDeptleader> deptLeaderList, String deptName, String backFlowContent, String baseId, String projDeptId, int flowNum, Date thisDate) {
        List<XxbBDeptleader> query = new ArrayList<>();
        query = deptLeaderList.stream().filter(s -> s.getDeptName().equals(deptName)).collect(Collectors.toList());
        if (query.size() > 0) {
            XxbBDeptflow item = new XxbBDeptflow();
            item.setId(UUID.randomUUID().toString());
            item.setFlownum(flowNum);
            item.setState(0);
            item.setPid(baseId);
            item.setFlowAccount(query.get(0).getUserAccount());
            item.setFlowAccountName(query.get(0).getUserAccountName());
            item.setFlowYggh(query.get(0).getUserYggh());
            item.setFlowdept(query.get(0).getDeptName());
            item.setBackflowcontent(backFlowContent);
            item.setProjdeptid(projDeptId);
            item.setIsDeletemark(1);
            item.setCreateTime(thisDate);
            insertDeptFlow.add(item);
        }
    }

    private String getAge(Date date1, String dateStr) {
        if (StringUtils.isNotBlank(dateStr)) {
            Date date2 = DateUtil.parse(dateStr);
            Long betweenDay = DateUtil.between(date1, date2, DateUnit.DAY);
            if (betweenDay > 0 && betweenDay < 365) {
                return "1";
            } else {
                return String.valueOf(betweenDay.intValue() / 365);
            }
        }
        return "";
    }

    private Date addSecond(Date date, int t) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.SECOND, t);// 24小时制
        return cal.getTime();
    }

    @Override
    @Transactional
    public void deleteCheckData(String id) {
        this.iXxbBCheckDService.removeById(id);
    }

    @Override
    @Transactional
    public void deleteProjDeptData(String id) {
//        XxbBProjdept projdept = this.iXxbBProjdeptService.getById(id);
//        if(projdept != null) {
//            LambdaQueryWrapper<XxbBDeptflow> wrapper = new LambdaQueryWrapper<>();
//            wrapper.eq(XxbBDeptflow::getPid, projdept.getPid());
//            wrapper.eq(XxbBDeptflow::getIsDeletemark,1);
//            wrapper.eq(XxbBDeptflow::getFlowdept, projdept.getDeptName());
//            List<XxbBDeptflow> list = this.iXxbBDeptflowService.list(wrapper);
//            if(list.size() > 0) {
//                this.iXxbBDeptflowService.removeById(list.get(0).getId());
//            }
//            this.iXxbBProjdeptService.removeById(id);
//        }
        this.iXxbBProjdeptService.removeById(id);
    }

    @Override
    public List<XxbBCheckD> getCheckDataList(String baseId) {
        LambdaQueryWrapper<XxbBCheckD> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(XxbBCheckD::getPid, baseId);
        wrapper.eq(XxbBCheckD::getIsDeletemark, 1);
        List<XxbBCheckD> list = this.iXxbBCheckDService.list(wrapper);
        if (list.size() > 0) {
            list = list.stream().sorted(Comparator.comparing(XxbBCheckD::getCreateTime).reversed()).collect(Collectors.toList());
        }
        return list;
    }

    private List<XxbBDeptleader> getDeptLeaderList(String userAccount, List<String> deptList) {
        List<XxbBDeptleader> list = new ArrayList<>();
        if ((deptList != null && deptList.size() > 0) || StringUtils.isNotBlank(userAccount)) {
            LambdaQueryWrapper<XxbBDeptleader> wrapper = new LambdaQueryWrapper<>();
            if (StringUtils.isNotBlank(userAccount)) {
                wrapper.eq(XxbBDeptleader::getUserAccount, userAccount);
            }

            if (deptList != null && deptList.size() > 0) {
                wrapper.in(XxbBDeptleader::getDeptName, deptList);
            }
            list = this.iXxbBDeptleaderService.list(wrapper);
        } else {
            list = this.iXxbBDeptleaderService.list();
        }
        return list;
    }

    @Override
    public List<XxbBProjdept> getProjDeptList(String baseId) {
        LambdaQueryWrapper<XxbBProjdept> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(XxbBProjdept::getPid, baseId);
        wrapper.eq(XxbBProjdept::getIsDeletemark, 1);
        List<XxbBProjdept> list = this.iXxbBProjdeptService.list(wrapper);
        if (list.size() > 0) {
            list = list.stream().sorted(Comparator.comparing(XxbBProjdept::getCreateTime).reversed()).collect(Collectors.toList());
        }
        return list;
    }

    @Override
    public List<XxbBDeptflow> getDeptFlowList(String baseId, User user) {
        XxbBCheck obj = this.getById(baseId);
        if (obj != null && obj.getState() > 0) {
            int flowNum = obj.getFlownum();
            LambdaQueryWrapper<XxbBDeptflow> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(XxbBDeptflow::getPid, baseId);
            wrapper.eq(XxbBDeptflow::getIsDeletemark, 1);
//                wrapper.and(p -> p.eq(XxbBDeptflow::getState, 0).or().eq(XxbBDeptflow::getState, 1));
            List<XxbBDeptflow> list = this.iXxbBDeptflowService.list(wrapper);

            XxbBDeptflow newFlow = null;
            if (obj.getState() == 1) {
                if (list.size() > 0) {
                    List<XxbBDeptflow> query = new ArrayList<>();
                    query = list.stream().filter(s -> s.getFlownum() == flowNum).collect(Collectors.toList());
                    //科主任
                    if (flowNum == 1) {
                        if (query.size() == 0) {
                            newFlow = this.getStateDeptFlowFlowNum1(obj.getId(), obj.getDeptNew(), flowNum);
                        }
                    }
                    // 多科 科主任
                    if (obj.getProjectType() == 2 && flowNum == 2) {
                        List<XxbBDeptleader> deptLeaderList = this.getDeptLeaderList(user.getUsername(), null);
                        if (deptLeaderList.size() > 0 && StringUtils.isNotBlank(deptLeaderList.get(0).getDeptName())) {
                            XxbBDeptleader deptleader = deptLeaderList.get(0);
                            LambdaQueryWrapper<XxbBProjdept> wrapperProjDept = new LambdaQueryWrapper<>();
                            wrapperProjDept.eq(XxbBProjdept::getPid, obj.getId());
                            wrapperProjDept.eq(XxbBProjdept::getIsDeletemark, 1);
                            wrapperProjDept.eq(XxbBProjdept::getDeptName, deptLeaderList.get(0).getDeptName());
                            List<XxbBProjdept> pdList = iXxbBProjdeptService.list(wrapperProjDept);

                            if (query.size() == 0) {
                                if (pdList.size() > 0) {
                                    newFlow = this.getStateDeptFlowFlowNum2(obj.getId(), deptleader, pdList.get(0), flowNum);
                                }
                            } else {
                                if (pdList.size() > 0) {
                                    query = list.stream().filter(s -> s.getFlownum() == flowNum && s.getFlowdept().equals(deptleader.getDeptName())).collect(Collectors.toList());
                                    if (query.size() == 0) {
                                        newFlow = this.getStateDeptFlowFlowNum2(obj.getId(), deptleader, pdList.get(0), flowNum);
                                    }
                                }
                            }
                        }
                    }
                    // 单科、检查、多科 医务办
                    if ("ywcgl".equals(user.getUsername()) && ((obj.getProjectType() != 2 && flowNum == 2) || (obj.getProjectType() == 2 && flowNum == 3))) {
//                        SdlBUserMg sdlUserMg = iSdlBUserMgService.findObjByAccount(user.getUsername());
                        if (query.size() == 0) {
                            newFlow = new XxbBDeptflow();
                            newFlow.setPid(baseId);
                            newFlow.setFlownum(flowNum);
                            newFlow.setState(0);
                            newFlow.setFlowAccount("ywcgl");
                            newFlow.setFlowYggh("ywcgl");
                            newFlow.setFlowAccountName("医务办公室");
                            newFlow.setFlowdept("医务办公室");
                            newFlow.setIsDeletemark(1);
                        }
                    }
                } else {
                    newFlow = this.getStateDeptFlowFlowNum1(obj.getId(), obj.getDeptNew(), 1);
                }
            }

            if (newFlow != null) {
                list.add(newFlow);
            }

            if (obj.getState() == 1 && list.size() > 0) {
                if (flowNum == 1) {
                    list = list.stream().filter(s -> s.getFlownum() == 1 && s.getFlowAccount().equals(user.getUsername())).sorted(Comparator.comparing(XxbBDeptflow::getFlownum)).collect(Collectors.toList());
                }
                if (flowNum == 2) {
                    list = list.stream().filter(s -> s.getFlownum() == 1 || (
                            s.getFlownum() == 2 && s.getFlowAccount().equals(user.getUsername())
                    )).sorted(Comparator.comparing(XxbBDeptflow::getFlownum)).collect(Collectors.toList());
                }
                if (flowNum == 3) {
                    list = list.stream().sorted(Comparator.comparing(XxbBDeptflow::getFlownum).thenComparing(XxbBDeptflow::getFlowDate)).collect(Collectors.toList());
                }
            } else {
                list = list.stream().sorted(Comparator.comparing(XxbBDeptflow::getFlownum).thenComparing(XxbBDeptflow::getFlowDate)).collect(Collectors.toList());
            }
            return list;
        }
        return null;
    }

    @Override
    @Transactional
    public void updateDeptflow(XxbBDeptflow deptflow) {
        Date thisDate = new Date();
        if (StringUtils.isBlank(deptflow.getId())) {
            deptflow.setId(UUID.randomUUID().toString());
            deptflow.setCreateTime(thisDate);
        }
        XxbBCheck obj = this.getById(deptflow.getPid());
        if (deptflow.getState() == 1) {
            deptflow.setFlowDate(thisDate);
            if (obj.getProjectType() != 2) {
                XxbBCheck update = new XxbBCheck();
                update.setId(obj.getId());
                if (obj.getFlownum() == 1) {
                    update.setFlownum(obj.getFlownum() + 1);
                }
                if (obj.getFlownum() > 1) {
                    update.setState(2);
                }
                this.updateById(update);
            }
            if (obj.getProjectType() == 2) {
                if (obj.getFlownum() == 1) {
                    XxbBCheck update = new XxbBCheck();
                    update.setId(obj.getId());
                    update.setFlownum(obj.getFlownum() + 1);
                    this.updateById(update);
                }
                if (obj.getFlownum() == 2) {
                    LambdaQueryWrapper<XxbBProjdept> wrapperProjDept = new LambdaQueryWrapper<>();
                    wrapperProjDept.eq(XxbBProjdept::getPid, obj.getId());
                    wrapperProjDept.eq(XxbBProjdept::getIsDeletemark, 1);
                    List<XxbBProjdept> pdList = iXxbBProjdeptService.list(wrapperProjDept);
                    int count = 0;
                    XxbBProjdept updateProjectDept = new XxbBProjdept();
                    for (XxbBProjdept item : pdList) {
                        if (item.getDeptName().equals(deptflow.getFlowdept())) {
                            updateProjectDept.setId(item.getId());
                            count += 1;
                        } else {
                            if (item.getState() != null && item.getState() == 1) {
                                count += 1;
                            }
                        }
                    }
                    updateProjectDept.setDeptAccount(deptflow.getFlowAccount());
                    updateProjectDept.setDeptAccountName(deptflow.getFlowAccountName());
                    updateProjectDept.setDeptYggh(deptflow.getFlowYggh());
                    updateProjectDept.setState(1);
                    iXxbBProjdeptService.updateById(updateProjectDept);

                    if (count == pdList.size()) {
                        XxbBCheck update = new XxbBCheck();
                        update.setId(obj.getId());
                        update.setState(1);
                        update.setFlownum(obj.getFlownum() + 1);
                        this.updateById(update);
                    }
                }

                if (obj.getFlownum() == 3) {
                    XxbBCheck update = new XxbBCheck();
                    update.setId(obj.getId());
                    update.setState(2);
                    this.updateById(update);
                }
            }
        }

        // state = 2指的是传进来的参数，不是真是状态
        if (deptflow.getState() == 2) {
            deptflow.setState(0);
            XxbBCheck update = new XxbBCheck();
            update.setId(obj.getId());
            update.setFlownum(0);
            update.setState(3);
            this.updateById(update);

            if (obj.getProjectType() == 2) {
                LambdaQueryWrapper<XxbBProjdept> pdWrapper = new LambdaQueryWrapper<>();
                pdWrapper.eq(XxbBProjdept::getPid, obj.getId());
                pdWrapper.eq(XxbBProjdept::getIsDeletemark, 1);
                XxbBProjdept pdup = new XxbBProjdept();
                pdup.setState(0);
                iXxbBProjdeptService.update(pdup, pdWrapper);
            }
            LambdaQueryWrapper<XxbBDeptflow> dfWrapper = new LambdaQueryWrapper<>();
            dfWrapper.eq(XxbBDeptflow::getPid, obj.getId());
            dfWrapper.eq(XxbBDeptflow::getIsDeletemark, 1);
            XxbBDeptflow dfup = new XxbBDeptflow();
            dfup.setState(0);
            iXxbBDeptflowService.update(dfup, dfWrapper);
        }
        this.iXxbBDeptflowService.saveOrUpdate(deptflow);
    }

    private XxbBDeptflow getStateDeptFlowFlowNum1(String baseId, String deptName, int flowNum) {
        List<String> deptList = CollUtil.newArrayList(deptName);
        List<XxbBDeptleader> list = this.getDeptLeaderList(null, deptList);
        if (list.size() > 0) {
            XxbBDeptflow item = new XxbBDeptflow();
            item.setFlownum(flowNum);
            item.setState(0);
            item.setPid(baseId);
            item.setFlowAccount(list.get(0).getUserAccount());
            item.setFlowAccountName(list.get(0).getUserAccountName());
            item.setFlowYggh(list.get(0).getUserYggh());
            item.setFlowdept(list.get(0).getDeptName());
            item.setIsDeletemark(1);
            return item;
        }
        return null;
    }

    private XxbBDeptflow getStateDeptFlowFlowNum2(String baseId, XxbBDeptleader deptleader, XxbBProjdept projdept, int flowNum) {
        XxbBDeptflow item = new XxbBDeptflow();
        item.setFlownum(flowNum);
        item.setState(0);
        item.setPid(baseId);
        item.setFlowAccount(deptleader.getUserAccount());
        item.setFlowAccountName(deptleader.getUserAccountName());
        item.setFlowYggh(deptleader.getUserYggh());
        item.setFlowdept(deptleader.getDeptName());
        item.setProjdeptid(projdept.getId());
        item.setIsDeletemark(1);
        return item;
    }

    @Override
    @Transactional
    public XxbBCheckD addCheckData(XxbBCheckD xxbBCheckD) {
        xxbBCheckD.setId(UUID.randomUUID().toString());
        xxbBCheckD.setCreateTime(new Date());
        xxbBCheckD.setAge(this.getAge(new Date(), xxbBCheckD.getBirthday()));
        this.iXxbBCheckDService.save(xxbBCheckD);

        return xxbBCheckD;
    }

//    private List<XxbBCheckData> getCheckData (List<XxbBCheckD> checkDataList){
//        List<XxbBCheckData> list = new ArrayList<>();
//        for (XxbBCheckD item : checkDataList) {
//            XxbBCheckData data = new XxbBCheckData();
//            data.setId(item.getId());
//            data.setUserAccountName(item.getUserAccountName());
//            data.setUserAccount(item.getUserAccount());
//            data.setYggh(item.getYggh());
//            data.setSexName(item.getSexName());
//            data.setBirthday(item.getBirthday());
//            data.setAge(item.getAge());
//            data.setEdu(item.getEdu());
//            data.setZhicw(item.getZhicw());
//            data.setZhuanyfm(item.getZhuanyfm());
//            data.setUserdept(item.getUserdept());
//            list.add(data);
//        }
//
//        return list;
//    }


    @Override
    @Transactional
    public void updateXxbBCheck(XxbBCheck xxbBCheck) {
        xxbBCheck.setModifyTime(new Date());
        this.baseMapper.updateXxbBCheck(xxbBCheck);
    }

    @Override
    @Transactional
    public void deleteXxbBChecks(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        LambdaQueryWrapper<XxbBCheck> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(XxbBCheck::getState, 0);
        wrapper.in(XxbBCheck::getId, list);
        XxbBCheck update = new XxbBCheck();
        update.setIsDeletemark(0);
        this.baseMapper.update(update, wrapper);
//        LambdaQueryWrapper<XxbBCheckD> wrapperD = new LambdaQueryWrapper<>();
//        wrapperD.in(XxbBCheckD::getPid, list);
//        wrapperD.eq(XxbBCheckD::getIsDeletemark, 1);
//        List<XxbBCheckD> dList = iXxbBCheckDService.list(wrapperD);
//        List<String> dIdList = dList.stream().map(XxbBCheckD::getId).collect(Collectors.toList());
//        if (dIdList.size() > 0) {
//            String[] dIds = dIdList.toArray(new String[dIdList.size()]);
//            iXxbBCheckDService.deleteXxbBCheckDs(dIds);
//        }
//        this.baseMapper.deleteBatchIds(list);
    }

}