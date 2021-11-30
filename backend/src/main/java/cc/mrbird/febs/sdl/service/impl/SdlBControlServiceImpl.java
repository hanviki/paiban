package cc.mrbird.febs.sdl.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.sdl.entity.SdlBControl;
import cc.mrbird.febs.sdl.dao.SdlBControlMapper;
import cc.mrbird.febs.sdl.service.ISdlBControlService;
import cn.hutool.core.date.DateUtil;
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
 * @since 2021-11-15
 */
@Slf4j
@Service("ISdlBControlService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class SdlBControlServiceImpl extends ServiceImpl<SdlBControlMapper, SdlBControl> implements ISdlBControlService {


@Override
public IPage<SdlBControl> findSdlBControls(QueryRequest request, SdlBControl sdlBControl){
        try{
        LambdaQueryWrapper<SdlBControl> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(SdlBControl::getIsDeletemark, 1);//1是未删 0是已删

        if (sdlBControl.getState()!=null) {
        queryWrapper.eq(SdlBControl::getState, sdlBControl.getState());
        }
        int cuurent_year = DateUtil.year(new Date());

                queryWrapper.le(SdlBControl::getYear, String.valueOf(cuurent_year));
                                if (sdlBControl.getState()!=null) {
                                queryWrapper.eq(SdlBControl::getState, sdlBControl.getState());
                                }

        Page<SdlBControl> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<SdlBControl> findSdlBControlList (QueryRequest request, SdlBControl sdlBControl){
        try{
        Page<SdlBControl> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findSdlBControl(page,sdlBControl);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createSdlBControl(SdlBControl sdlBControl){
                sdlBControl.setCreateTime(new Date());
        sdlBControl.setIsDeletemark(1);
        this.save(sdlBControl);
        }

@Override
@Transactional
public void updateSdlBControl(SdlBControl sdlBControl){
        sdlBControl.setModifyTime(new Date());
        this.baseMapper.updateSdlBControl(sdlBControl);
        }

@Override
@Transactional
public void deleteSdlBControls(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }