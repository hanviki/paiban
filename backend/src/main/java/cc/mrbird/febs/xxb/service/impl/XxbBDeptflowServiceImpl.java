package cc.mrbird.febs.xxb.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.xxb.entity.XxbBDeptflow;
import cc.mrbird.febs.xxb.dao.XxbBDeptflowMapper;
import cc.mrbird.febs.xxb.service.IXxbBDeptflowService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.time.LocalDate;
/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author viki
 * @since 2022-04-08
 */
@Slf4j
@Service("IXxbBDeptflowService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class XxbBDeptflowServiceImpl extends ServiceImpl<XxbBDeptflowMapper, XxbBDeptflow> implements IXxbBDeptflowService {


@Override
public IPage<XxbBDeptflow> findXxbBDeptflows(QueryRequest request, XxbBDeptflow xxbBDeptflow){
        try{
        LambdaQueryWrapper<XxbBDeptflow> queryWrapper=new LambdaQueryWrapper<>();
//        queryWrapper.eq(XxbBDeptflow::getIsDeletemark, 1);//1是未删 0是已删
        if (StringUtils.isNotBlank(xxbBDeptflow.getFlowAccount())) {
        queryWrapper.and(wrap->  wrap.eq(XxbBDeptflow::getFlowAccount, xxbBDeptflow.getFlowAccount()).or()
        .like(XxbBDeptflow::getFlowAccountName, xxbBDeptflow.getFlowAccount()));

        }
        if (xxbBDeptflow.getState()!=null) {
        queryWrapper.eq(XxbBDeptflow::getState, xxbBDeptflow.getState());
        }


        Page<XxbBDeptflow> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<XxbBDeptflow> findXxbBDeptflowList (QueryRequest request, XxbBDeptflow xxbBDeptflow){
        try{
        Page<XxbBDeptflow> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findXxbBDeptflow(page,xxbBDeptflow);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createXxbBDeptflow(XxbBDeptflow xxbBDeptflow){
                xxbBDeptflow.setId(UUID.randomUUID().toString());
        xxbBDeptflow.setCreateTime(new Date());
//        xxbBDeptflow.setIsDeletemark(1);
        this.save(xxbBDeptflow);
        }

@Override
@Transactional
public void updateXxbBDeptflow(XxbBDeptflow xxbBDeptflow){
//        xxbBDeptflow.setModifyTime(new Date());
        this.baseMapper.updateXxbBDeptflow(xxbBDeptflow);
        }

@Override
@Transactional
public void deleteXxbBDeptflows(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }