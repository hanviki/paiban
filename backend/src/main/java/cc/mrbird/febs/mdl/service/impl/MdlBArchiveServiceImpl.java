package cc.mrbird.febs.mdl.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.mdl.entity.MdlBArchive;
import cc.mrbird.febs.mdl.dao.MdlBArchiveMapper;
import cc.mrbird.febs.mdl.service.IMdlBArchiveService;
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
 * @since 2022-01-17
 */
@Slf4j
@Service("IMdlBArchiveService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MdlBArchiveServiceImpl extends ServiceImpl<MdlBArchiveMapper, MdlBArchive> implements IMdlBArchiveService {


@Override
public IPage<MdlBArchive> findMdlBArchives(QueryRequest request, MdlBArchive mdlBArchive){
        try{
        LambdaQueryWrapper<MdlBArchive> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(MdlBArchive::getIsDeletemark, 1);//1是未删 0是已删

        if (mdlBArchive.getState()!=null) {
        queryWrapper.eq(MdlBArchive::getState, mdlBArchive.getState());
        }

                                if (StringUtils.isNotBlank(mdlBArchive.getFileName())) {
                                queryWrapper.like(MdlBArchive::getFileName, mdlBArchive.getFileName());
                                }
                                if (StringUtils.isNotBlank(mdlBArchive.getFileCode())) {
                                queryWrapper.like(MdlBArchive::getFileCode, mdlBArchive.getFileCode());
                                }

        Page<MdlBArchive> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<MdlBArchive> findMdlBArchiveList (QueryRequest request, MdlBArchive mdlBArchive){
        try{
        Page<MdlBArchive> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findMdlBArchive(page,mdlBArchive);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createMdlBArchive(MdlBArchive mdlBArchive){
                mdlBArchive.setCreateTime(new Date());
        mdlBArchive.setIsDeletemark(1);
        this.save(mdlBArchive);
        }

@Override
@Transactional
public void updateMdlBArchive(MdlBArchive mdlBArchive){
        mdlBArchive.setModifyTime(new Date());
        this.baseMapper.updateMdlBArchive(mdlBArchive);
        }

@Override
@Transactional
public void deleteMdlBArchives(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }