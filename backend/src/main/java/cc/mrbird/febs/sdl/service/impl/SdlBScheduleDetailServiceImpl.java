package cc.mrbird.febs.sdl.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.sdl.entity.CustomData;
import cc.mrbird.febs.sdl.entity.SdlBScheduleDetail;
import cc.mrbird.febs.sdl.dao.SdlBScheduleDetailMapper;
import cc.mrbird.febs.sdl.service.ISdlBScheduleDetailService;
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
 * 排班子表明细 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-10-13
 */
@Slf4j
@Service("ISdlBScheduleDetailService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class SdlBScheduleDetailServiceImpl extends ServiceImpl<SdlBScheduleDetailMapper, SdlBScheduleDetail> implements ISdlBScheduleDetailService {


    @Override
    public IPage<SdlBScheduleDetail> findSdlBScheduleDetails(QueryRequest request, SdlBScheduleDetail sdlBScheduleDetail) {
        try {
            LambdaQueryWrapper<SdlBScheduleDetail> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SdlBScheduleDetail::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(sdlBScheduleDetail.getUserName())) {
                queryWrapper.like(SdlBScheduleDetail::getUserName, sdlBScheduleDetail.getUserName());
            }

            if (sdlBScheduleDetail.getState() != null) {
                queryWrapper.eq(SdlBScheduleDetail::getState, sdlBScheduleDetail.getState());
            }

            Page<SdlBScheduleDetail> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<SdlBScheduleDetail> findSdlBScheduleDetailList(QueryRequest request, SdlBScheduleDetail sdlBScheduleDetail) {
        try {
            Page<SdlBScheduleDetail> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findSdlBScheduleDetail(page, sdlBScheduleDetail);
        } catch (Exception e) {
            log.error("获取排班子表明细失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createSdlBScheduleDetail(SdlBScheduleDetail sdlBScheduleDetail) {
        sdlBScheduleDetail.setId(UUID.randomUUID().toString());
        sdlBScheduleDetail.setCreateTime(new Date());
        sdlBScheduleDetail.setIsDeletemark(1);
        this.save(sdlBScheduleDetail);
    }

    @Override
    @Transactional
    public void updateSdlBScheduleDetail(SdlBScheduleDetail sdlBScheduleDetail) {
        sdlBScheduleDetail.setModifyTime(new Date());
        this.baseMapper.updateSdlBScheduleDetail(sdlBScheduleDetail);
    }

    @Override
    @Transactional
    public void deleteSdlBScheduleDetails(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
    }

    @Override
    @Transactional
    public List<CustomData> findSdlBScheduleReport(SdlBScheduleDetail sdlBScheduleDetail) {
        return this.baseMapper.findSdlBScheduleReport(sdlBScheduleDetail);
    }


    @Override
    @Transactional
    public List<CustomData> findSdlBScheduleReport2(SdlBScheduleDetail sdlBScheduleDetail) {
        return this.baseMapper.findSdlBScheduleReport2(sdlBScheduleDetail);
    }

    @Override
    @Transactional
    public List<SdlBScheduleDetail> findSdlBScheduleReportAccount(SdlBScheduleDetail sdlBScheduleDetail) {
        return this.baseMapper.findSdlBScheduleReportAccount(sdlBScheduleDetail);
    }
    @Override
    @Transactional
    public List<SdlBScheduleDetail> findSdlBScheduleReportAccount2(SdlBScheduleDetail sdlBScheduleDetail) {
        return this.baseMapper.findSdlBScheduleReportAccount2(sdlBScheduleDetail);
    }


    @Override
    @Transactional
    public List<CustomData> findYeBanReport(SdlBScheduleDetail sdlBScheduleDetail) {
        List<String> idList= this.baseMapper.findAllBaseId(sdlBScheduleDetail);
        sdlBScheduleDetail.setBaseList(idList);
        return this.baseMapper.findYeBanReport(sdlBScheduleDetail);
    }

    @Override
    @Transactional
    public List<CustomData> findYeBanSubReport(SdlBScheduleDetail sdlBScheduleDetail) {
        List<String> idList= this.baseMapper.findAllBaseId(sdlBScheduleDetail);
        sdlBScheduleDetail.setBaseList(idList);
        return this.baseMapper.findYeBanSubReport(sdlBScheduleDetail);
    }
    @Override
    @Transactional
    public List<SdlBScheduleDetail> findYeBanSubReportAccount(SdlBScheduleDetail sdlBScheduleDetail) {
        return this.baseMapper.findYeBanSubReportAccount(sdlBScheduleDetail);
    }

    @Override
    @Transactional
    public List<CustomData> findMenZhenReport(SdlBScheduleDetail sdlBScheduleDetail) {
        List<String> idList= this.baseMapper.findAllBaseId(sdlBScheduleDetail);
        sdlBScheduleDetail.setBaseList(idList);
        return this.baseMapper.findMenZhenReport(sdlBScheduleDetail);
    }

    @Override
    @Transactional
    public List<CustomData> findMenZhenSubReport(SdlBScheduleDetail sdlBScheduleDetail) {
        List<String> idList= this.baseMapper.findAllBaseId(sdlBScheduleDetail);
        sdlBScheduleDetail.setBaseList(idList);
        return this.baseMapper.findMenZhenSubReport(sdlBScheduleDetail);
    }
    @Override
    @Transactional
    public List<SdlBScheduleDetail> findMenZhenSubReportAccount(SdlBScheduleDetail sdlBScheduleDetail) {
        return this.baseMapper.findMenZhenSubReportAccount(sdlBScheduleDetail);
    }
}