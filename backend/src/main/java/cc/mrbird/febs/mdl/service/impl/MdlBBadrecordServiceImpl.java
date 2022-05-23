package cc.mrbird.febs.mdl.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.mdl.entity.MdlBBadrecord;
import cc.mrbird.febs.mdl.dao.MdlBBadrecordMapper;
import cc.mrbird.febs.mdl.service.IMdlBBadrecordService;
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
 * 服务实现类
 * </p>
 *
 * @author viki
 * @since 2022-04-12
 */
@Slf4j
@Service("IMdlBBadrecordService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MdlBBadrecordServiceImpl extends ServiceImpl<MdlBBadrecordMapper, MdlBBadrecord> implements IMdlBBadrecordService {


    @Override
    public IPage<MdlBBadrecord> findMdlBBadrecords(QueryRequest request, MdlBBadrecord mdlBBadrecord) {
        try {
            LambdaQueryWrapper<MdlBBadrecord> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(MdlBBadrecord::getIsDeletemark, 1);//1是未删 0是已删
            if (StringUtils.isNotBlank(mdlBBadrecord.getUserAccount())) {
                queryWrapper.and(wrap -> wrap.eq(MdlBBadrecord::getUserAccount, mdlBBadrecord.getUserAccount()).or()
                        .like(MdlBBadrecord::getUserAccountName, mdlBBadrecord.getUserAccount()));

            }
            if (mdlBBadrecord.getState() != null) {
                queryWrapper.eq(MdlBBadrecord::getState, mdlBBadrecord.getState());
            }

            if (StringUtils.isNotBlank(mdlBBadrecord.getType())) {
                queryWrapper.like(MdlBBadrecord::getType, mdlBBadrecord.getType());
            }
            if (StringUtils.isNotBlank(mdlBBadrecord.getDeptName())) {
               queryWrapper.eq(MdlBBadrecord::getDeptName,mdlBBadrecord.getDeptName());
            }
            if (StringUtils.isNotBlank(mdlBBadrecord.getDeptNew())) {
                queryWrapper.like(MdlBBadrecord::getDeptNew,mdlBBadrecord.getDeptNew());
            }
            if (StringUtils.isNotBlank(mdlBBadrecord.getLb())) {
                queryWrapper.eq(MdlBBadrecord::getLb,mdlBBadrecord.getLb());
            }
            if (StringUtils.isNotBlank(mdlBBadrecord.getCode())) {
                queryWrapper.eq(MdlBBadrecord::getCode,mdlBBadrecord.getCode());
            }
            if (StringUtils.isNotBlank(mdlBBadrecord.getStartDateFrom())) {
                queryWrapper.ge(MdlBBadrecord::getStartDate,mdlBBadrecord.getStartDateFrom());
            }
            if (StringUtils.isNotBlank(mdlBBadrecord.getStartDateTo())) {
                queryWrapper.le(MdlBBadrecord::getStartDate,mdlBBadrecord.getStartDateTo());
            }
            if (mdlBBadrecord.getYear2()>0
            ) {
                queryWrapper.and(warp->warp.eq(MdlBBadrecord::getYear,mdlBBadrecord.getYear2()).or()
                .eq(MdlBBadrecord::getYear,mdlBBadrecord.getYear2()-1));
            }
            if (mdlBBadrecord.getYear()!=null
            ) {
                queryWrapper.eq(MdlBBadrecord::getYear,mdlBBadrecord.getYear());
            }

            Page<MdlBBadrecord> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<MdlBBadrecord> findMdlBBadrecordList(QueryRequest request, MdlBBadrecord mdlBBadrecord) {
        try {
            Page<MdlBBadrecord> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findMdlBBadrecord(page, mdlBBadrecord);
        } catch (Exception e) {
            log.error("获取失败", e);
            return null;
        }
    }
    @Override
    public IPage<MdlBBadrecord> findMdlBBadrecordListYear(QueryRequest request, MdlBBadrecord mdlBBadrecord) {
        try {
            Page<MdlBBadrecord> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findMdlBBadrecordYear(page, mdlBBadrecord);
        } catch (Exception e) {
            log.error("获取失败", e);
            return null;
        }
    }
    @Override
    public IPage<MdlBBadrecord> findMdlBBadrecordListYear2(QueryRequest request, MdlBBadrecord mdlBBadrecord) {
        try {
            Page<MdlBBadrecord> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findMdlBBadrecordYear2(page, mdlBBadrecord);
        } catch (Exception e) {
            log.error("获取失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createMdlBBadrecord(MdlBBadrecord mdlBBadrecord) {
        mdlBBadrecord.setCreateTime(new Date());
        mdlBBadrecord.setIsDeletemark(1);
        this.save(mdlBBadrecord);
    }

    @Override
    @Transactional
    public void updateMdlBBadrecord(MdlBBadrecord mdlBBadrecord) {
        mdlBBadrecord.setModifyTime(new Date());
        this.baseMapper.updateMdlBBadrecord(mdlBBadrecord);
    }

    @Override
    @Transactional
    public void deleteMdlBBadrecords(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
    }

}