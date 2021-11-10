package cc.mrbird.febs.sdl.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.sdl.entity.SdlDHoliday;
import cc.mrbird.febs.sdl.dao.SdlDHolidayMapper;
import cc.mrbird.febs.sdl.service.ISdlDHolidayService;
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
 * 节假日 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-11-10
 */
@Slf4j
@Service("ISdlDHolidayService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class SdlDHolidayServiceImpl extends ServiceImpl<SdlDHolidayMapper, SdlDHoliday> implements ISdlDHolidayService {


@Override
public IPage<SdlDHoliday> findSdlDHolidays(QueryRequest request, SdlDHoliday sdlDHoliday){
        try{
        LambdaQueryWrapper<SdlDHoliday> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(SdlDHoliday::getIsDeletemark, 1);//1是未删 0是已删
        if (StringUtils.isNotBlank(sdlDHoliday.getHolidayName())) {

            queryWrapper.like(SdlDHoliday::getHolidayName, sdlDHoliday.getHolidayName());
        }
        if (sdlDHoliday.getState()!=null) {
        queryWrapper.eq(SdlDHoliday::getState, sdlDHoliday.getState());
        }

                                if (StringUtils.isNotBlank(sdlDHoliday.getStartDateFrom()) && StringUtils.isNotBlank(sdlDHoliday.getStartDateTo())) {
                                queryWrapper
                                .ge(SdlDHoliday::getStartDate, sdlDHoliday.getStartDateFrom())
                                .le(SdlDHoliday::getStartDate, sdlDHoliday.getStartDateTo());
                                }
                                if (StringUtils.isNotBlank(sdlDHoliday.getEndDateFrom()) && StringUtils.isNotBlank(sdlDHoliday.getEndDateTo())) {
                                queryWrapper
                                .ge(SdlDHoliday::getEndDate, sdlDHoliday.getEndDateFrom())
                                .le(SdlDHoliday::getEndDate, sdlDHoliday.getEndDateTo());
                                }
                                if (sdlDHoliday.getState()!=null) {
                                queryWrapper.eq(SdlDHoliday::getState, sdlDHoliday.getState());
                                }

        Page<SdlDHoliday> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<SdlDHoliday> findSdlDHolidayList (QueryRequest request, SdlDHoliday sdlDHoliday){
        try{
        Page<SdlDHoliday> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findSdlDHoliday(page,sdlDHoliday);
        }catch(Exception e){
        log.error("获取节假日失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createSdlDHoliday(SdlDHoliday sdlDHoliday){
                sdlDHoliday.setId(UUID.randomUUID().toString());
        sdlDHoliday.setCreateTime(new Date());
        sdlDHoliday.setIsDeletemark(1);
        this.save(sdlDHoliday);
        }

@Override
@Transactional
public void updateSdlDHoliday(SdlDHoliday sdlDHoliday){
        sdlDHoliday.setModifyTime(new Date());
        this.baseMapper.updateSdlDHoliday(sdlDHoliday);
        }

@Override
@Transactional
public void deleteSdlDHolidays(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }