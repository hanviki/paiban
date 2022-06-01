package cc.mrbird.febs.xxb.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.system.domain.User;
import cc.mrbird.febs.xxb.entity.*;
import cc.mrbird.febs.xxb.dao.XxbBResultMapper;
import cc.mrbird.febs.xxb.service.*;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruiyun.jvppeteer.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.time.LocalDate;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author viki
 * @since 2022-05-23
 */
@Slf4j
@Service("IXxbBResultService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class XxbBResultServiceImpl extends ServiceImpl<XxbBResultMapper, XxbBResult> implements IXxbBResultService {

    @Autowired
    IXxbBResultDService iXxbBResultDService;

    @Autowired
    IXxbBDeptleaderService iXxbBDeptleaderService;

    @Autowired
    IXxbBResultFService iXxbBResultFService;

    @Autowired
    IXxbBResultPService iXxbBResultPService;

    @Override
    public IPage<XxbBResult> findXxbBResults(QueryRequest request, XxbBResult xxbBResult, User user) {
        try {
            LambdaQueryWrapper<XxbBResult> queryWrapper = new LambdaQueryWrapper<>();
            String sql = "is_deletemark = 1";
            if (xxbBResult.getState() != null) {
                sql += " and state = " + xxbBResult.getState();
            }

            if (StringUtils.isNotBlank(xxbBResult.getApplydatFrom()) && StringUtils.isBlank(xxbBResult.getApplydatTo())) {
                sql += " and applydat >= '" + xxbBResult.getApplydatFrom() + "'";
            }

            if (StringUtils.isNotBlank(xxbBResult.getApplydatFrom()) && StringUtils.isNotBlank(xxbBResult.getApplydatTo())) {
                sql += " and applydat >= '" + xxbBResult.getApplydatFrom() + "' and applydat <= '" + xxbBResult.getApplydatTo() + "'";
            }

            if (StringUtils.isBlank(xxbBResult.getApplydatFrom()) && StringUtils.isNotBlank(xxbBResult.getApplydatTo())) {
                sql += " and applydat <= '" + xxbBResult.getApplydatTo() + "'";
            }

            if (user != null) {
                sql += " and CREATE_USER_ID = " + user.getUserId();
            }

            queryWrapper.apply(sql);

            Page<XxbBResult> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<XxbBResult> findXxbBResultReports(QueryRequest request, XxbBResult xxbBResult,boolean isDf) {
        try {
            LambdaQueryWrapper<XxbBResult> queryWrapper = new LambdaQueryWrapper<>();
            String sql = "is_deletemark = 1";
            if (xxbBResult.getState() != null) {
                sql += " and state = " + xxbBResult.getState();
            }

            if (StringUtils.isNotBlank(xxbBResult.getDeptNew())) {
                sql += " and dept_new = '" + xxbBResult.getDeptNew() + "'";
            }

            if (StringUtils.isNotBlank(xxbBResult.getProjectName())) {
                sql += " and project_name like '%" + xxbBResult.getProjectName() + "%'";
            }

            if (StringUtils.isNotBlank(xxbBResult.getApplydatFrom()) && StringUtils.isBlank(xxbBResult.getApplydatTo())) {
                sql += " and applydat >= '" + xxbBResult.getApplydatFrom() + "'";
            }

            if (StringUtils.isNotBlank(xxbBResult.getApplydatFrom()) && StringUtils.isNotBlank(xxbBResult.getApplydatTo())) {
                sql += " and applydat >= '" + xxbBResult.getApplydatFrom() + "' and applydat <= '" + xxbBResult.getApplydatTo() + "'";
            }

            if (StringUtils.isBlank(xxbBResult.getApplydatFrom()) && StringUtils.isNotBlank(xxbBResult.getApplydatTo())) {
                sql += " and applydat <= '" + xxbBResult.getApplydatTo() + "'";
            }

            if (StringUtils.isNotBlank(xxbBResult.getImportdatFrom()) && StringUtils.isBlank(xxbBResult.getImportdatTo())) {
                sql += " and importdat >= '" + xxbBResult.getApplydatFrom() + "'";
            }

            if (StringUtils.isNotBlank(xxbBResult.getImportdatFrom()) && StringUtils.isNotBlank(xxbBResult.getImportdatTo())) {
                sql += " and importdat >= '" + xxbBResult.getImportdatFrom() + "' and applydat <= '" + xxbBResult.getImportdatTo() + "'";
            }

            if (StringUtils.isBlank(xxbBResult.getImportdatFrom()) && StringUtils.isNotBlank(xxbBResult.getImportdatTo())) {
                sql += " and importdat <= '" + xxbBResult.getImportdatTo() + "'";
            }

            if(isDf){
                sql += " and ifnull(abtotal,0) = 0";
            }

            queryWrapper.apply(sql);

            Page<XxbBResult> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<XxbBResult> findXxbBResultflow(QueryRequest request, XxbBResult xxbBResult, User user) {
        try {
            LambdaQueryWrapper<XxbBResult> queryWrapper = new LambdaQueryWrapper<>();
            String sql = "is_deletemark = 1";

            if (StringUtils.isNotBlank(xxbBResult.getApplydatFrom()) && StringUtils.isBlank(xxbBResult.getApplydatTo())) {
                sql += " and applydat >= '" + xxbBResult.getApplydatFrom() + "'";
            }

            if (StringUtils.isNotBlank(xxbBResult.getApplydatFrom()) && StringUtils.isNotBlank(xxbBResult.getApplydatTo())) {
                sql += " and applydat >= '" + xxbBResult.getApplydatFrom() + "' and applydat <= '" + xxbBResult.getApplydatTo() + "'";
            }

            if (StringUtils.isBlank(xxbBResult.getApplydatFrom()) && StringUtils.isNotBlank(xxbBResult.getApplydatTo())) {
                sql += " and applydat <= '" + xxbBResult.getApplydatTo() + "'";
            }

            if (StringUtils.isNotBlank(xxbBResult.getComments())) {
                if (xxbBResult.getComments().equals("科") && xxbBResult.getState() != null) {
                    if (xxbBResult.getState() == 1) {
                        //查询 流程方案 1
//                        sql += " and flownum = 1 and state = 1 and dept_new in (" +
//                                "select dept_name from xxb_b_deptleader where user_account = '" + user.getUsername() + "'" +
//                                ")";
                        // 生成流程DeptFlow
                        // 查询 流程方案 2
                        sql += " and flownum = 1 and state = 1 and id in (" +
                                "select pid from xxb_b_result_f where is_deletemark = 1 and flownum = 1 and state = 0 and flow_account='" + user.getUsername() + "'" +
                                ")";
                    } else {
                        sql += " and id in (" +
                                "select pid from xxb_b_result_f where is_deletemark = 1 and flownum = 1 and state = 1 and flow_account = '" + user.getUsername() + "'" +
                                ")";
                    }
                }
                if (xxbBResult.getComments().equals("医")) {
                    if (xxbBResult.getState() == 2) {
                        //查询 流程方案 1
//                        sql += " and 'ywcgl' = '" + user.getUsername() + "' and flownum = 2 and state = 1)";

                        // 生成流程DeptFlow
                        // 查询 流程方案 2
                        sql += " and state = 1 and flownum = 2 and id in (" +
                                "select pid from xxb_b_result_f where is_deletemark = 1 and flownum = 2 and state = 0 and flow_account='" + user.getUsername() + "'" +
                                ")";
                    } else {
                        queryWrapper.eq(XxbBResult::getState, 2);
                    }
                }
            } else {
                sql += " and 1=2";
            }
            if (StringUtils.isNotEmpty(xxbBResult.getProjectName())) {
                queryWrapper.like(XxbBResult::getProjectName, xxbBResult.getProjectName());
            }
            queryWrapper.apply(sql);

            Page<XxbBResult> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            this.page(page, queryWrapper);
            return page;
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public String editXxbBResult(XxbBResultInfo xxbBResultInfo, User user) {
        XxbBResult result = new XxbBResult();
        Date thisDate = new Date();
        XxbBResult entity = this.getById(xxbBResultInfo.getId());
        if (entity == null) {
            result.setId(xxbBResultInfo.getId());
            result.setCreateTime(thisDate);
            result.setCreateUserId(user.getUserId());
            result.setFlownum(0);
            result.setIsDeletemark(1);
        } else {
            result.setId(xxbBResultInfo.getId());
            result.setModifyTime(thisDate);
            result.setModifyUserId(user.getUserId());
        }

        // dept_new、projct_name、projct_level、projct_type、isxzyljs、applydat、user_account_name、user_account
        result.setDeptNew(xxbBResultInfo.getDeptNew()); // 申请科室
        result.setYuanqu(xxbBResultInfo.getYuanqu()); // 申请科室
        result.setProjectName(xxbBResultInfo.getProjectName());// 项目名称
//        result.setProjectLevel(xxbBResultInfo.getProjectLevel());// 项目级别
//        result.setProjectType(xxbBResultInfo.getProjectType());//项目类型
//        result.setProjectLb(xxbBResultInfo.getProjectLb());//项目类别
//        result.setIsxzyljs(xxbBResultInfo.getIsxzyljs());//是否为限制类医疗技术
        result.setApplydat(xxbBResultInfo.getApplydat());//申请日期
        result.setUserAccountName(xxbBResultInfo.getUserAccountName());//姓名
        result.setUserAccount(xxbBResultInfo.getUserAccount());//人事编码
        // yggh、sex_name、birthday、userdept、edu、zhicheng、zhiwu、zhuany、zhuanc、telephone、lev、typ、projctcontent
        result.setYggh(xxbBResultInfo.getYggh());//员工工号
        result.setSexName(xxbBResultInfo.getSexName());//性别
        result.setBirthday(xxbBResultInfo.getBirthday());//出生年月
        result.setUserdept(xxbBResultInfo.getUserdept());//员工科室
        result.setEdu(xxbBResultInfo.getEdu());//学历、学位
        result.setZhichenglc(xxbBResultInfo.getZhichenglc());//临床职称
        result.setZhicheng(xxbBResultInfo.getZhicheng());//职称
//        result.setZhiwu(xxbBResultInfo.getZhiwu());//职务
//        result.setZhuany(xxbBResultInfo.getZhuany());//专业
//        result.setZhuanc(xxbBResultInfo.getZhuanc());//专长
        result.setTelephone(xxbBResultInfo.getTelephone());//电话
//        result.setLev(xxbBResultInfo.getLev());//级别
//        result.setTyp(xxbBResultInfo.getTyp());//类别
        result.setProjectcontent(xxbBResultInfo.getProjectcontent());//项目的主要内容及意义

        result.setProjectkxyj(xxbBResultInfo.getProjectkxyj());//项目的科学依据
        result.setProjectffjslx(xxbBResultInfo.getProjectffjslx());//项目采取的方法、技术路线
        result.setProjectkey(xxbBResultInfo.getProjectkey());//项目解决的关键问题
        result.setJsfxya(xxbBResultInfo.getJsfxya());//技术存在的主要风险及预案
        result.setProjectjjshxy(xxbBResultInfo.getProjectjjshxy());//项目产生的经济效益、社会效益
        result.setJswxmlcc(xxbBResultInfo.getJswxmlcc());//主要技术文献目录及出处
        result.setNewbdqk(xxbBResultInfo.getNewbdqk());//新闻报道情况
        result.setProjectkzqk(xxbBResultInfo.getProjectkzqk());//项目开展情况

        result.setFlownum(xxbBResultInfo.getFlownum());
        //传入状态
        result.setState(xxbBResultInfo.getState());

        //开展日期
        result.setKzsrtdat(xxbBResultInfo.getKzsrtdat());
        //开展例数
        result.setKzls(xxbBResultInfo.getKzls());

        // srtdat、enddat、ycdat、shstate、xmjdstate、STATE 未赋值
        List<XxbBResultD> insertData = new ArrayList<>();
        List<XxbBResultD> updateData = new ArrayList<>();
        List<String> deleteData = new ArrayList<>();
        List<XxbBResultD> queryData = new ArrayList<>();

        List<XxbBResultF> insertResultFLowList = new ArrayList<>();

        LambdaQueryWrapper<XxbBResultD> wrapperData = new LambdaQueryWrapper<>();
        wrapperData.eq(XxbBResultD::getPid,result.getId());
        List<XxbBResultD> dataList = iXxbBResultDService.list(wrapperData);
        int nTime = 0;
        // 新增 修改 项目主要人员
        for (XxbBResultD item : xxbBResultInfo.getResultDataList()) {
            XxbBResultD data = new XxbBResultD();
            // user_account_name、user_account、yggh、sex_name、birthday、age、edu、zhicw、zhuanyfm、userdept
            data.setUserAccountName(item.getUserAccountName());
            data.setUserAccount(item.getUserAccount());
            data.setYggh(item.getYggh());
            data.setDisplayIndex(item.getDisplayIndex());
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
//            data.setZhiwu(item.getZhiwu());
//            data.setZhuany(item.getZhuany());
//            data.setZhuanc(item.getZhuanc());
            data.setUserdept(item.getUserdept());
            data.setTelephone(item.getTelephone());
            data.setRenshizifw(item.getRenshizifw());
            if (item.getId() == null) {
                data.setId(UUID.randomUUID().toString());
                data.setIsDeletemark(1);
                data.setPid(result.getId());
                if (nTime == 0) {
                    data.setCreateTime(thisDate);
                } else {
                    data.setCreateTime(this.addSecond(thisDate, nTime));
                }
                nTime += 1;
                insertData.add(data);
            } else {
                data.setId(item.getId());
                data.setPid(result.getId());
                updateData.add(data);
            }
        }

        for (XxbBResultD item : dataList) {
            queryData = updateData.stream().filter(s->s.getId().equals(item.getId())).collect(Collectors.toList());
            if(queryData.size() == 0) {
                deleteData.add(item.getId());
            }
        }

        List<String> delResultFlowIdList= new ArrayList<>();
        //生成流程DeptFlow 流程方案 2
        // result.getState() == 1 传入状态
        if (result.getState() == 1) {
            List<XxbBDeptleader> deptLeaderList = this.iXxbBDeptleaderService.getDeptLeaderList(null, null);
            if (deptLeaderList.size() > 0) {
                LambdaQueryWrapper<XxbBResultF> wrapperDeptFlow = new LambdaQueryWrapper<>();
                wrapperDeptFlow.eq(XxbBResultF::getPid, result.getId());
                wrapperDeptFlow.eq(XxbBResultF::getIsDeletemark, 1);
                List<XxbBResultF> resultFlowList = this.iXxbBResultFService.list(wrapperDeptFlow);
                // entity 查询 当前状态 创建时
                if ((entity == null || (entity != null && entity.getState() == 0)) && resultFlowList.size() == 0) {
                    int flowNum = 1;
                    this.insertResultFLow(insertResultFLowList, deptLeaderList, result.getDeptNew(), 0, null, null,result.getId(), flowNum, thisDate, false);
                    flowNum = 2;
                    XxbBResultF resultFlow2 = new XxbBResultF();
                    resultFlow2.setId(UUID.randomUUID().toString());
                    resultFlow2.setPid(result.getId());
                    resultFlow2.setFlownum(flowNum);
                    resultFlow2.setState(0);
                    resultFlow2.setResultstate(0);
                    resultFlow2.setFlowAccount("ywcgl");
                    resultFlow2.setFlowYggh("ywcgl");
                    resultFlow2.setFlowAccountName("医务办公室");
                    resultFlow2.setFlowdept("医务办公室");
                    resultFlow2.setCreateTime(thisDate);
                    resultFlow2.setIsDeletemark(1);
                    insertResultFLowList.add(resultFlow2);

                }
                // entity 查询 当前状态 退回时
                if (entity != null && entity.getState() == 3 && resultFlowList.size() > 0) {
                    List<XxbBResultF> resultFlowQuery = new ArrayList<>();
                    String backFlowContent = null;
                    boolean isYwc = false;
                    Date flowDate = null;
                    isYwc = resultFlowList.stream().filter(s -> s.getFlowAccount().equals("ywcgl") && s.getResultstate().equals(2)).count() == 0 ? false : true;
                    int resultState = 0;
                    int flowNum = 1;
                    resultFlowQuery = resultFlowList.stream().filter(s -> s.getFlownum() == 1 && s.getFlowdept().equals(result.getDeptNew())).collect(Collectors.toList());
                    backFlowContent = resultFlowQuery.size() == 0 ? null : resultFlowQuery.get(0).getFlowcontent();
                    resultState = resultFlowQuery.size() == 0 ? 0 : resultFlowQuery.get(0).getResultstate();
                    flowDate = resultFlowQuery.size() == 0 ? null : resultFlowQuery.get(0).getFlowDate();

                    this.insertResultFLow(insertResultFLowList, deptLeaderList, result.getDeptNew(), resultState, backFlowContent, flowDate,result.getId(), flowNum, thisDate, isYwc);

                    flowNum += 1;
                    resultFlowQuery = resultFlowList.stream().filter(s -> s.getFlownum() == 2 && s.getFlowdept().equals("医务办公室")).collect(Collectors.toList());
                    if (isYwc) {
                        result.setFlownum(2);
                    }
                    backFlowContent = resultFlowQuery.size() == 0 ? null : resultFlowQuery.get(0).getFlowcontent();
                    XxbBResultF resultFlow2 = new XxbBResultF();
                    resultFlow2.setId(UUID.randomUUID().toString());
                    resultFlow2.setPid(result.getId());
                    resultFlow2.setFlownum(flowNum);
                    resultFlow2.setState(0);
                    resultFlow2.setResultstate(0);
                    resultFlow2.setFlowAccount("ywcgl");
                    resultFlow2.setFlowYggh("ywcgl");
                    resultFlow2.setFlowAccountName("医务办公室");
                    resultFlow2.setFlowdept("医务办公室");
                    resultFlow2.setBackflowcontent(backFlowContent);
                    resultFlow2.setCreateTime(thisDate);
                    resultFlow2.setIsDeletemark(1);
                    insertResultFLowList.add(resultFlow2);

                    for (XxbBResultF item : resultFlowList) {
                        delResultFlowIdList.add(item.getId());
                    }
                }
            }
        }

        if (entity != null && entity.getState() == 3 && delResultFlowIdList.size() > 0) {
            String[] delDeptFlow = delResultFlowIdList.toArray(new String[delResultFlowIdList.size()]);
            iXxbBResultFService.deleteXxbBResultFs(delDeptFlow);
        }

        if (insertData.size() > 0) {
            iXxbBResultDService.saveBatch(insertData);
        }
        if (updateData.size() > 0) {
            iXxbBResultDService.updateBatchById(updateData);
        }

        if (insertResultFLowList.size() > 0) {
            iXxbBResultFService.saveBatch(insertResultFLowList);
        }

        if(deleteData.size() > 0) {
            String[] dataIds = Convert.toStrArray(deleteData);
            iXxbBResultDService.deleteXxbBResultDs(dataIds);
        }

        this.saveOrUpdate(result);
        return result.getId();
    }

    @Override
    @Transactional
    public void updateResultFlow (XxbBResultF resultFlow) {
        Date thisDate = new Date();
        if (StringUtils.isBlank(resultFlow.getId())) {
            resultFlow.setId(UUID.randomUUID().toString());
            resultFlow.setCreateTime(thisDate);
        }
        XxbBResult obj = this.getById(resultFlow.getPid());
        // state = 1 指的是同意审核
        if (resultFlow.getState().equals(1)) {
            resultFlow.setBackflowcontent("");
            resultFlow.setResultstate(1);
            resultFlow.setFlowDate(thisDate);

            XxbBResult update = new XxbBResult();
            update.setId(obj.getId());
            if (obj.getFlownum() == 1) {
                update.setFlownum(obj.getFlownum() + 1);
                update.setGjlx(resultFlow.getGjlx());
                update.setGjxj(resultFlow.getGjxj());
                update.setGnlx(resultFlow.getGnlx());
                update.setGnxj(resultFlow.getGnxj());
                update.setSnlx(resultFlow.getSnlx());
                update.setSnxj(resultFlow.getSnxj());
            }
            if (obj.getFlownum() > 1) {
                update.setState(2);
            }
            this.updateById(update);
        }

        // state = 2 指的是传进来的参数，不是真是状态，驳回操作
        if (resultFlow.getState().equals(2)) {
            resultFlow.setResultstate(2);
            resultFlow.setState(1);
            resultFlow.setFlowDate(thisDate);

            XxbBResult update = new XxbBResult();
            update.setId(obj.getId());
            update.setFlownum(0);
            update.setState(3);
            this.updateById(update);

        }
        if(resultFlow.getState().equals(9)){
            XxbBResult update = new XxbBResult();
            update.setState(9);
            update.setId(obj.getId());
            this.updateById(update);

            resultFlow.setResultstate(2);
            resultFlow.setState(1);
        }
        this.iXxbBResultFService.saveOrUpdate(resultFlow);
    }

    @Override
    @Transactional
    public void updateResultReportPf (List<String> ridList,List<XxbBResult> updateList,List<XxbBResultP> edidPList, int state) {
        LambdaQueryWrapper<XxbBResultP> wrapperDelP = new LambdaQueryWrapper<>();
        wrapperDelP.eq(XxbBResultP::getState,state);
        wrapperDelP.in(XxbBResultP::getPid,ridList);
        iXxbBResultPService.getBaseMapper().delete(wrapperDelP);

        iXxbBResultPService.saveBatch(edidPList);

        this.updateBatchById(updateList);

    }
    @Override
    public List<XxbBResultF> getResultFList(String baseId, User user, int state) {
        XxbBResult obj = this.getById(baseId);
        if (obj != null && obj.getState() > 0) {
            int flowNum = obj.getFlownum();
            LambdaQueryWrapper<XxbBResultF> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(XxbBResultF::getPid, baseId);
            wrapper.eq(XxbBResultF::getIsDeletemark, 1);
//                wrapper.and(p -> p.eq(XxbBResultF::getState, 0).or().eq(XxbBResultF::getState, 1));
            List<XxbBResultF> list = this.iXxbBResultFService.list(wrapper);

            XxbBResultF newFlow = null;
            // state 0 只查询 1做业务
            if (state == 1 && obj.getState() == 1) {
                if (list.size() > 0) {
                    List<XxbBResultF> query = new ArrayList<>();
                    query = list.stream().filter(s -> s.getFlownum() == flowNum).collect(Collectors.toList());
                    //科主任
                    if (flowNum == 1) {
                        if (query.size() == 0) {
                            newFlow = this.getStateResultFlowNum1(obj.getId(), obj.getDeptNew(), flowNum);
                        }
                    }
                    // 单科、检查、多科 医务办
                    if ("ywcgl".equals(user.getUsername()) && flowNum == 2) {
//                        SdlBUserMg sdlUserMg = iSdlBUserMgService.findObjByAccount(user.getUsername());
                        if (query.size() == 0) {
                            newFlow = new XxbBResultF();
                            newFlow.setPid(baseId);
                            newFlow.setFlownum(flowNum);
                            newFlow.setState(0);
                            newFlow.setResultstate(0);
                            newFlow.setFlowAccount("ywcgl");
                            newFlow.setFlowYggh("ywcgl");
                            newFlow.setFlowAccountName("医务办公室");
                            newFlow.setFlowdept("医务办公室");
                            newFlow.setIsDeletemark(1);
                        }
                    }
                } else {
                    newFlow = this.getStateResultFlowNum1(obj.getId(), obj.getDeptNew(), 1);
                }
            }

            if (newFlow != null) {
                list.add(newFlow);
            }

            if (state == 1 && obj.getState() == 1 && list.size() > 0) {
                if (flowNum == 1) {
                    list = list.stream().filter(s -> s.getFlownum() == 1 && s.getFlowAccount().equals(user.getUsername())).sorted(Comparator.comparing(XxbBResultF::getFlownum)).collect(Collectors.toList());
                } else {
                    list = list.stream().sorted(Comparator.comparing(XxbBResultF::getFlownum).thenComparing(XxbBResultF::getFlowDate)).collect(Collectors.toList());
                }
            } else {
                list = list.stream().sorted(Comparator.comparing(XxbBResultF::getFlownum)).collect(Collectors.toList());
            }
            return list;
        }
        return null;
    }

    @Override
    public List<XxbBResultP> getResultPList(String baseId, Integer state) {
        List<XxbBResultP> resultPList = new ArrayList<>();
        XxbBResult obj = this.getById(baseId);
        if (obj != null ) {
            LambdaQueryWrapper<XxbBResultP> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(XxbBResultP::getIsDeletemark,1);
            wrapper.eq(XxbBResultP::getPid,baseId);
            if(state!=null) {
                wrapper.eq(XxbBResultP::getState, state);
            }
            resultPList = iXxbBResultPService.list(wrapper);

            if(resultPList.size() > 0) {
                resultPList.sort(Comparator.comparing(XxbBResultP::getState).thenComparing(Comparator.comparing(XxbBResultP::getTotal)));
            }
        }
        return resultPList;
    }

    private XxbBResultF getStateResultFlowNum1(String baseId, String deptName, int flowNum) {
        List<String> deptList = CollUtil.newArrayList(deptName);
        List<XxbBDeptleader> list = this.iXxbBDeptleaderService.getDeptLeaderList(null, deptList);
        if (list.size() > 0) {
            XxbBResultF item = new XxbBResultF();
            item.setFlownum(flowNum);
            item.setState(0);
            item.setResultstate(0);
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

    private void insertResultFLow(List<XxbBResultF> insertResultF, List<XxbBDeptleader> deptLeaderList, String deptName,
                                int resiltState, String backFlowContent, Date flowDate, String baseId, int flowNum, Date thisDate, boolean isYwc) {

            List<XxbBDeptleader> query = new ArrayList<>();
            query = deptLeaderList.stream().filter(s -> s.getDeptName().equals(deptName)).collect(Collectors.toList());
            if (query.size() > 0) {
                XxbBResultF item = new XxbBResultF();
                item.setId(UUID.randomUUID().toString());
                item.setFlownum(flowNum);
                item.setResultstate(isYwc ? 1 : 0);
                item.setState(isYwc ? 1 : 0);
                item.setFlowDate(isYwc ? flowDate : null);
                item.setPid(baseId);
                item.setFlowAccount(query.get(0).getUserAccount());
                item.setFlowAccountName(query.get(0).getUserAccountName());
                item.setFlowYggh(query.get(0).getUserYggh());
                item.setFlowdept(query.get(0).getDeptName());
                item.setFlowcontent(resiltState == 1 ? backFlowContent : null);
                item.setBackflowcontent(resiltState == 2 ? backFlowContent : null);
                item.setIsDeletemark(1);
                item.setCreateTime(thisDate);
                insertResultF.add(item);
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
    public List<XxbBResultD> getResultDataList(String baseId) {
        LambdaQueryWrapper<XxbBResultD> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(XxbBResultD::getPid, baseId);
        wrapper.eq(XxbBResultD::getIsDeletemark, 1);
        List<XxbBResultD> list = this.iXxbBResultDService.list(wrapper);
        if (list.size() > 0) {
            list = list.stream().sorted(Comparator.comparing(XxbBResultD::getCreateTime).reversed()).collect(Collectors.toList());
        }
        return list;
    }

    @Override
    @Transactional
    public void deleteResultData(String id) {
        this.iXxbBResultDService.removeById(id);
    }

    @Override
    public IPage<XxbBResult> findXxbBResultList(QueryRequest request, XxbBResult xxbBResult) {
        try {
            Page<XxbBResult> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findXxbBResult(page, xxbBResult);
        } catch (Exception e) {
            log.error("获取失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createXxbBResult(XxbBResult xxbBResult) {
        xxbBResult.setId(UUID.randomUUID().toString());
        xxbBResult.setCreateTime(new Date());
        xxbBResult.setIsDeletemark(1);
        this.save(xxbBResult);
    }

    @Override
    @Transactional
    public void updateXxbBResult(XxbBResult xxbBResult) {
        xxbBResult.setModifyTime(new Date());
        this.baseMapper.updateXxbBResult(xxbBResult);
    }

    @Override
    @Transactional
    public void deleteXxbBResults(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        LambdaQueryWrapper<XxbBResult> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(XxbBResult::getState, 0);
        wrapper.in(XxbBResult::getId, list);
        XxbBResult update = new XxbBResult();
        update.setIsDeletemark(0);
        this.baseMapper.update(update, wrapper);

//        LambdaQueryWrapper<XxbBResultD> wrapperD = new LambdaQueryWrapper<>();
//        wrapperD.in(XxbBResultD::getPid, list);
//        wrapperD.eq(XxbBResultD::getIsDeletemark, 1);
//        List<XxbBResultD> dList = iXxbBResultDService.list(wrapperD);
//        List<String> dIdList = dList.stream().map(XxbBResultD::getId).collect(Collectors.toList());
//        if (dIdList.size() > 0) {
//            String[] dIds = dIdList.toArray(new String[dIdList.size()]);
//            iXxbBResultDService.deleteXxbBResultDs(dIds);
//        }
//        this.baseMapper.deleteBatchIds(list);
    }

}