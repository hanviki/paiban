package cc.mrbird.febs.mdl.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.mdl.entity.MdlBBadarchive;
import cc.mrbird.febs.mdl.dao.MdlBBadarchiveMapper;
import cc.mrbird.febs.mdl.service.IMdlBBadarchiveService;
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
 * @since 2022-06-06
 */
@Slf4j
@Service("IMdlBBadarchiveService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MdlBBadarchiveServiceImpl extends ServiceImpl<MdlBBadarchiveMapper, MdlBBadarchive> implements IMdlBBadarchiveService {


@Override
public IPage<MdlBBadarchive> findMdlBBadarchives(QueryRequest request, MdlBBadarchive mdlBBadarchive){
        try{
        LambdaQueryWrapper<MdlBBadarchive> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(MdlBBadarchive::getIsDeletemark, 1);//1是未删 0是已删
        if (StringUtils.isNotBlank(mdlBBadarchive.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(MdlBBadarchive::getUserAccount, mdlBBadarchive.getUserAccount()));

        }
        if (mdlBBadarchive.getState()!=null) {
        queryWrapper.eq(MdlBBadarchive::getState, mdlBBadarchive.getState());
        }


        Page<MdlBBadarchive> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<MdlBBadarchive> findMdlBBadarchiveList (QueryRequest request, MdlBBadarchive mdlBBadarchive){
        try{
        Page<MdlBBadarchive> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findMdlBBadarchive(page,mdlBBadarchive);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createMdlBBadarchive(MdlBBadarchive mdlBBadarchive){
                mdlBBadarchive.setCreateTime(new Date());
        mdlBBadarchive.setIsDeletemark(1);
        this.save(mdlBBadarchive);
        }

@Override
@Transactional
public void updateMdlBBadarchive(MdlBBadarchive mdlBBadarchive){
        mdlBBadarchive.setModifyTime(new Date());
        this.baseMapper.updateMdlBBadarchive(mdlBBadarchive);
        }

@Override
@Transactional
public void deleteMdlBBadarchives(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        LambdaQueryWrapper<MdlBBadarchive> queryWrapper= new LambdaQueryWrapper<>();
        queryWrapper.in(MdlBBadarchive::getId,list );
        MdlBBadarchive mdlBBadarchive=new MdlBBadarchive();
        mdlBBadarchive.setIsDeletemark(0);
        this.baseMapper.update(mdlBBadarchive,queryWrapper);
        }

        }