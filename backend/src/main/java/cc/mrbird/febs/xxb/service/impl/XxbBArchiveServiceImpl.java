package cc.mrbird.febs.xxb.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.xxb.entity.XxbBArchive;
import cc.mrbird.febs.xxb.dao.XxbBArchiveMapper;
import cc.mrbird.febs.xxb.service.IXxbBArchiveService;
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
 * @since 2022-04-24
 */
@Slf4j
@Service("IXxbBArchiveService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class XxbBArchiveServiceImpl extends ServiceImpl<XxbBArchiveMapper, XxbBArchive> implements IXxbBArchiveService {


@Override
public IPage<XxbBArchive> findXxbBArchives(QueryRequest request, XxbBArchive xxbBArchive){
        try{
        LambdaQueryWrapper<XxbBArchive> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(XxbBArchive::getIsDeletemark, 1);//1是未删 0是已删

        if (xxbBArchive.getState()!=null) {
        queryWrapper.eq(XxbBArchive::getState, xxbBArchive.getState());
        }


        Page<XxbBArchive> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<XxbBArchive> findXxbBArchiveList (QueryRequest request, XxbBArchive xxbBArchive){
        try{
        Page<XxbBArchive> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findXxbBArchive(page,xxbBArchive);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createXxbBArchive(XxbBArchive xxbBArchive){
                xxbBArchive.setCreateTime(new Date());
        xxbBArchive.setIsDeletemark(1);
        this.save(xxbBArchive);
        }

@Override
@Transactional
public void updateXxbBArchive(XxbBArchive xxbBArchive){
        xxbBArchive.setModifyTime(new Date());
        this.baseMapper.updateXxbBArchive(xxbBArchive);
        }

@Override
@Transactional
public void deleteXxbBArchives(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }