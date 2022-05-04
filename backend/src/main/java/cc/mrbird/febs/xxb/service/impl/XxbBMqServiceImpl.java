package cc.mrbird.febs.xxb.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.xxb.entity.XxbBMq;
import cc.mrbird.febs.xxb.dao.XxbBMqMapper;
import cc.mrbird.febs.xxb.service.IXxbBMqService;
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
 * 末期反馈表 服务实现类
 * </p>
 *
 * @author viki
 * @since 2022-04-27
 */
@Slf4j
@Service("IXxbBMqService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class XxbBMqServiceImpl extends ServiceImpl<XxbBMqMapper, XxbBMq> implements IXxbBMqService {


@Override
public IPage<XxbBMq> findXxbBMqs(QueryRequest request, XxbBMq xxbBMq){
        try{
        LambdaQueryWrapper<XxbBMq> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(XxbBMq::getIsDeletemark, 1);//1是未删 0是已删

        if (xxbBMq.getState()!=null) {
        queryWrapper.eq(XxbBMq::getState, xxbBMq.getState());
        }
            if (StringUtils.isNotEmpty(xxbBMq.getBaseId())) {
                queryWrapper.eq(XxbBMq::getBaseId, xxbBMq.getBaseId());
            }

        Page<XxbBMq> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<XxbBMq> findXxbBMqList (QueryRequest request, XxbBMq xxbBMq){
        try{
        Page<XxbBMq> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findXxbBMq(page,xxbBMq);
        }catch(Exception e){
        log.error("获取末期反馈表失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createXxbBMq(XxbBMq xxbBMq){
                xxbBMq.setId(UUID.randomUUID().toString());
        xxbBMq.setCreateTime(new Date());
        xxbBMq.setIsDeletemark(1);
        this.save(xxbBMq);
        }

@Override
@Transactional
public void updateXxbBMq(XxbBMq xxbBMq){
        xxbBMq.setModifyTime(new Date());
        this.baseMapper.updateXxbBMq(xxbBMq);
        }

@Override
@Transactional
public void deleteXxbBMqs(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }