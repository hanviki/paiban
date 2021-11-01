package cc.mrbird.febs.sdl.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.sdl.entity.SdlBScheduleD;
import cc.mrbird.febs.sdl.dao.SdlBScheduleDMapper;
import cc.mrbird.febs.sdl.service.ISdlBScheduleDService;
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
 * 排班子表 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-10-13
 */
@Slf4j
@Service("ISdlBScheduleDService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class SdlBScheduleDServiceImpl extends ServiceImpl<SdlBScheduleDMapper, SdlBScheduleD> implements ISdlBScheduleDService {


    @Override
    public IPage<SdlBScheduleD> findSdlBScheduleDs(QueryRequest request, SdlBScheduleD sdlBScheduleD) {
        try {
            LambdaQueryWrapper<SdlBScheduleD> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SdlBScheduleD::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(sdlBScheduleD.getUserName())) {
                queryWrapper.like(SdlBScheduleD::getUserName, sdlBScheduleD.getUserName());
            }
            if (sdlBScheduleD.getState() != null) {
                queryWrapper.eq(SdlBScheduleD::getState, sdlBScheduleD.getState());
            }

            Page<SdlBScheduleD> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<SdlBScheduleD> findSdlBScheduleDList(QueryRequest request, SdlBScheduleD sdlBScheduleD) {
        try {
            Page<SdlBScheduleD> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findSdlBScheduleD(page, sdlBScheduleD);
        } catch (Exception e) {
            log.error("获取排班子表失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createSdlBScheduleD(SdlBScheduleD sdlBScheduleD) {
        sdlBScheduleD.setId(UUID.randomUUID().toString());
        sdlBScheduleD.setCreateTime(new Date());
        sdlBScheduleD.setIsDeletemark(1);
        this.save(sdlBScheduleD);
    }

    @Override
    @Transactional
    public void updateSdlBScheduleD(SdlBScheduleD sdlBScheduleD) {
        sdlBScheduleD.setModifyTime(new Date());
        this.baseMapper.updateSdlBScheduleD(sdlBScheduleD);
    }

    @Override
    @Transactional
    public void deleteSdlBScheduleDs(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
    }

    @Override
    @Transactional
    public List<SdlBScheduleD> getPaiBanZizhi(SdlBScheduleD sdlBScheduleD) {
        return this.baseMapper.getPaiBanZizhi(sdlBScheduleD);
    }

    @Override
    @Transactional
    public void deleteByDeptAndDate(String deptId,String startDate,String endDate){
        this.baseMapper.deleteByDeptAndDate(deptId,startDate,endDate);
    }
}