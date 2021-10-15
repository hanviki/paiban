package cc.mrbird.febs.sdl.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.sdl.entity.SdlDBanci;
import cc.mrbird.febs.sdl.dao.SdlDBanciMapper;
import cc.mrbird.febs.sdl.service.ISdlDBanciService;
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
 * 系列名称 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-10-13
 */
@Slf4j
@Service("ISdlDBanciService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class SdlDBanciServiceImpl extends ServiceImpl<SdlDBanciMapper, SdlDBanci> implements ISdlDBanciService {


@Override
public IPage<SdlDBanci> findSdlDBancis(QueryRequest request, SdlDBanci sdlDBanci){
        try{
        LambdaQueryWrapper<SdlDBanci> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(SdlDBanci::getIsDeletemark, 1);//1是未删 0是已删

                                if (sdlDBanci.getState()!=null) {
                                queryWrapper.eq(SdlDBanci::getState, sdlDBanci.getState());
                                }

        Page<SdlDBanci> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<SdlDBanci> findSdlDBanciList (QueryRequest request, SdlDBanci sdlDBanci){
        try{
        Page<SdlDBanci> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findSdlDBanci(page,sdlDBanci);
        }catch(Exception e){
        log.error("获取系列名称失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createSdlDBanci(SdlDBanci sdlDBanci){
                sdlDBanci.setCreateTime(new Date());
        sdlDBanci.setIsDeletemark(1);
        this.save(sdlDBanci);
        }

@Override
@Transactional
public void updateSdlDBanci(SdlDBanci sdlDBanci){
        sdlDBanci.setModifyTime(new Date());
        this.baseMapper.updateSdlDBanci(sdlDBanci);
        }

@Override
@Transactional
public void deleteSdlDBancis(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }