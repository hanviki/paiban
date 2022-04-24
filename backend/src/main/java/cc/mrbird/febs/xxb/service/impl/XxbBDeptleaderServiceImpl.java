package cc.mrbird.febs.xxb.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.xxb.entity.XxbBDeptleader;
import cc.mrbird.febs.xxb.dao.XxbBDeptleaderMapper;
import cc.mrbird.febs.xxb.service.IXxbBDeptleaderService;
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
 * 部门负责人 服务实现类
 * </p>
 *
 * @author viki
 * @since 2022-04-20
 */
@Slf4j
@Service("IXxbBDeptleaderService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class XxbBDeptleaderServiceImpl extends ServiceImpl<XxbBDeptleaderMapper, XxbBDeptleader> implements IXxbBDeptleaderService {


@Override
public IPage<XxbBDeptleader> findXxbBDeptleaders(QueryRequest request, XxbBDeptleader xxbBDeptleader){
        try{
        LambdaQueryWrapper<XxbBDeptleader> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(XxbBDeptleader::getIsDeletemark, 1);//1是未删 0是已删
        if (StringUtils.isNotBlank(xxbBDeptleader.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(XxbBDeptleader::getUserAccount, xxbBDeptleader.getUserAccount()).or()
        .like(XxbBDeptleader::getUserAccountName, xxbBDeptleader.getUserAccount()));

        }
        if (xxbBDeptleader.getState()!=null) {
        queryWrapper.eq(XxbBDeptleader::getState, xxbBDeptleader.getState());
        }

                                if (xxbBDeptleader.getState()!=null) {
                                queryWrapper.eq(XxbBDeptleader::getState, xxbBDeptleader.getState());
                                }

        Page<XxbBDeptleader> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<XxbBDeptleader> findXxbBDeptleaderList (QueryRequest request, XxbBDeptleader xxbBDeptleader){
        try{
        Page<XxbBDeptleader> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findXxbBDeptleader(page,xxbBDeptleader);
        }catch(Exception e){
        log.error("获取部门负责人失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createXxbBDeptleader(XxbBDeptleader xxbBDeptleader){
                xxbBDeptleader.setCreateTime(new Date());
        xxbBDeptleader.setIsDeletemark(1);
        this.save(xxbBDeptleader);
        }

@Override
@Transactional
public void updateXxbBDeptleader(XxbBDeptleader xxbBDeptleader){
        xxbBDeptleader.setModifyTime(new Date());
        this.baseMapper.updateXxbBDeptleader(xxbBDeptleader);
        }

@Override
@Transactional
public void deleteXxbBDeptleaders(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }