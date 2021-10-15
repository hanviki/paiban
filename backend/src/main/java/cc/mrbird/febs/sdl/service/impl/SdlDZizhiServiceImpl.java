package cc.mrbird.febs.sdl.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.sdl.entity.SdlDZizhi;
import cc.mrbird.febs.sdl.dao.SdlDZizhiMapper;
import cc.mrbird.febs.sdl.service.ISdlDZizhiService;
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
 * 资质字典表 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-10-13
 */
@Slf4j
@Service("ISdlDZizhiService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class SdlDZizhiServiceImpl extends ServiceImpl<SdlDZizhiMapper, SdlDZizhi> implements ISdlDZizhiService {


@Override
public IPage<SdlDZizhi> findSdlDZizhis(QueryRequest request, SdlDZizhi sdlDZizhi){
        try{
        LambdaQueryWrapper<SdlDZizhi> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(SdlDZizhi::getIsDeletemark, 1);//1是未删 0是已删

                                if (sdlDZizhi.getState()!=null) {
                                queryWrapper.eq(SdlDZizhi::getState, sdlDZizhi.getState());
                                }

        Page<SdlDZizhi> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<SdlDZizhi> findSdlDZizhiList (QueryRequest request, SdlDZizhi sdlDZizhi){
        try{
        Page<SdlDZizhi> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findSdlDZizhi(page,sdlDZizhi);
        }catch(Exception e){
        log.error("获取资质字典表失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createSdlDZizhi(SdlDZizhi sdlDZizhi){
                sdlDZizhi.setCreateTime(new Date());
        sdlDZizhi.setIsDeletemark(1);
        this.save(sdlDZizhi);
        }

@Override
@Transactional
public void updateSdlDZizhi(SdlDZizhi sdlDZizhi){
        sdlDZizhi.setModifyTime(new Date());
        this.baseMapper.updateSdlDZizhi(sdlDZizhi);
        }

@Override
@Transactional
public void deleteSdlDZizhis(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }