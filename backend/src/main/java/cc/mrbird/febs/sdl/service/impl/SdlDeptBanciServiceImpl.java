package cc.mrbird.febs.sdl.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.sdl.entity.SdlDBanci;
import cc.mrbird.febs.sdl.entity.SdlDeptBanci;
import cc.mrbird.febs.sdl.dao.SdlDeptBanciMapper;
import cc.mrbird.febs.sdl.service.ISdlDeptBanciService;
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
 * @since 2021-10-13
 */
@Slf4j
@Service("ISdlDeptBanciService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class SdlDeptBanciServiceImpl extends ServiceImpl<SdlDeptBanciMapper, SdlDeptBanci> implements ISdlDeptBanciService {


@Override
public IPage<SdlDeptBanci> findSdlDeptBancis(QueryRequest request, SdlDeptBanci sdlDeptBanci){
        try{
        LambdaQueryWrapper<SdlDeptBanci> queryWrapper=new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(sdlDeptBanci.getDeptName()))
        {
                queryWrapper.like(SdlDeptBanci::getDeptName,sdlDeptBanci.getDeptName());
        }

        Page<SdlDeptBanci> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<SdlDeptBanci> findSdlDeptBanciList (QueryRequest request, SdlDeptBanci sdlDeptBanci){
        try{
        Page<SdlDeptBanci> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findSdlDeptBanci(page,sdlDeptBanci);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createSdlDeptBanci(SdlDeptBanci sdlDeptBanci){
        this.save(sdlDeptBanci);
        }

@Override
@Transactional
public void updateSdlDeptBanci(SdlDeptBanci sdlDeptBanci){
        this.baseMapper.updateSdlDeptBanci(sdlDeptBanci);
        }

@Override
@Transactional
public void deleteSdlDeptBancis(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
        @Override
        @Transactional
        public  List<SdlDBanci> findBanci(SdlDeptBanci sdlDeptBanci){
           return  this.baseMapper.findBanci(sdlDeptBanci);
        }
        }