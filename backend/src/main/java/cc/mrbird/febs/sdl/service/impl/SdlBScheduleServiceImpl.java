package cc.mrbird.febs.sdl.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.sdl.entity.SdlBSchedule;
import cc.mrbird.febs.sdl.dao.SdlBScheduleMapper;
import cc.mrbird.febs.sdl.service.ISdlBScheduleService;
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
 * 排班主表 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-10-13
 */
@Slf4j
@Service("ISdlBScheduleService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class SdlBScheduleServiceImpl extends ServiceImpl<SdlBScheduleMapper, SdlBSchedule> implements ISdlBScheduleService {


    @Override
    public IPage<SdlBSchedule> findSdlBSchedules(QueryRequest request, SdlBSchedule sdlBSchedule) {
        try {
            LambdaQueryWrapper<SdlBSchedule> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SdlBSchedule::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(sdlBSchedule.getUserName())) {
                queryWrapper.like(SdlBSchedule::getUserName, sdlBSchedule.getUserName());
            }
            if (StringUtils.isNotBlank(sdlBSchedule.getStartDateFrom())) {
                queryWrapper
                        .eq(SdlBSchedule::getStartDate, sdlBSchedule.getStartDateFrom())
                        ;
            }
            if (StringUtils.isNotBlank(sdlBSchedule.getEndDateFrom()) && StringUtils.isNotBlank(sdlBSchedule.getEndDateTo())) {
                queryWrapper
                        .ge(SdlBSchedule::getEndDate, sdlBSchedule.getEndDateFrom())
                        .le(SdlBSchedule::getEndDate, sdlBSchedule.getEndDateTo());
            }
            if (sdlBSchedule.getState() != null) {
                queryWrapper.eq(SdlBSchedule::getState, sdlBSchedule.getState());
            }
            if (sdlBSchedule.getStateApply() != null) {
                queryWrapper.eq(SdlBSchedule::getStateApply, sdlBSchedule.getStateApply());
            }
            if (sdlBSchedule.getStateApplyFlag() != null) {
                queryWrapper.eq(SdlBSchedule::getStateApplyFlag, sdlBSchedule.getStateApplyFlag());
            }
            if (sdlBSchedule.getDeptId() != null) {
                queryWrapper.eq(SdlBSchedule::getDeptId, sdlBSchedule.getDeptId());
            }

            Page<SdlBSchedule> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<SdlBSchedule> findSdlBScheduleList(QueryRequest request, SdlBSchedule sdlBSchedule) {
        try {
            Page<SdlBSchedule> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findSdlBSchedule(page, sdlBSchedule);
        } catch (Exception e) {
            log.error("获取排班主表失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createSdlBSchedule(SdlBSchedule sdlBSchedule) {
        sdlBSchedule.setId(UUID.randomUUID().toString());
        sdlBSchedule.setCreateTime(new Date());
        sdlBSchedule.setIsDeletemark(1);
        this.save(sdlBSchedule);
    }

    @Override
    @Transactional
    public void updateSdlBSchedule(SdlBSchedule sdlBSchedule) {
        sdlBSchedule.setModifyTime(new Date());
        this.baseMapper.updateSdlBSchedule(sdlBSchedule);
    }

    @Override
    @Transactional
    public void deleteSdlBSchedules(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
    }

    @Override
    @Transactional
    public void updateStateById(String id,int state){
        this.baseMapper.updateStateById(id,state);
    }
}