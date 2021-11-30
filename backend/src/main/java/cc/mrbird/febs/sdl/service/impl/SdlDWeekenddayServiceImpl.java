package cc.mrbird.febs.sdl.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.sdl.entity.SdlDWeekendday;
import cc.mrbird.febs.sdl.dao.SdlDWeekenddayMapper;
import cc.mrbird.febs.sdl.service.ISdlDWeekenddayService;
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
 * 周六日表 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-11-11
 */
@Slf4j
@Service("ISdlDWeekenddayService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class SdlDWeekenddayServiceImpl extends ServiceImpl<SdlDWeekenddayMapper, SdlDWeekendday> implements ISdlDWeekenddayService {


@Override
public IPage<SdlDWeekendday> findSdlDWeekenddays(QueryRequest request, SdlDWeekendday sdlDWeekendday){
        try{
        LambdaQueryWrapper<SdlDWeekendday> queryWrapper=new LambdaQueryWrapper<>();
      //  queryWrapper.eq(SdlDWeekendday::getIsDeletemark, 1);//1是未删 0是已删

        if (sdlDWeekendday.getState()!=null) {
        queryWrapper.eq(SdlDWeekendday::getState, sdlDWeekendday.getState());
        }

                                if (sdlDWeekendday.getState()!=null) {
                                queryWrapper.eq(SdlDWeekendday::getState, sdlDWeekendday.getState());
                                }

        Page<SdlDWeekendday> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<SdlDWeekendday> findSdlDWeekenddayList (QueryRequest request, SdlDWeekendday sdlDWeekendday){
        try{
        Page<SdlDWeekendday> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findSdlDWeekendday(page,sdlDWeekendday);
        }catch(Exception e){
        log.error("获取周六日表失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createSdlDWeekendday(SdlDWeekendday sdlDWeekendday){
              //  sdlDWeekendday.setId(UUID.randomUUID().toString());
        sdlDWeekendday.setCreateTime(new Date());
        sdlDWeekendday.setIsDeletemark(1);
        this.save(sdlDWeekendday);
        }

@Override
@Transactional
public void updateSdlDWeekendday(SdlDWeekendday sdlDWeekendday){
        sdlDWeekendday.setModifyTime(new Date());
        this.baseMapper.updateSdlDWeekendday(sdlDWeekendday);
        }

@Override
@Transactional
public void deleteSdlDWeekenddays(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }