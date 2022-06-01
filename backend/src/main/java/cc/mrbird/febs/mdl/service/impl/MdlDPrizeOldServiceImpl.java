package cc.mrbird.febs.mdl.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.mdl.entity.MdlDPrizeOld;
import cc.mrbird.febs.mdl.dao.MdlDPrizeOldMapper;
import cc.mrbird.febs.mdl.service.IMdlDPrizeOldService;
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
 * 新技术获奖记录 服务实现类
 * </p>
 *
 * @author viki
 * @since 2022-05-25
 */
@Slf4j
@Service("IMdlDPrizeOldService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MdlDPrizeOldServiceImpl extends ServiceImpl<MdlDPrizeOldMapper, MdlDPrizeOld> implements IMdlDPrizeOldService {


@Override
public IPage<MdlDPrizeOld> findMdlDPrizeOlds(QueryRequest request, MdlDPrizeOld mdlDPrizeOld){
        try{
        LambdaQueryWrapper<MdlDPrizeOld> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(MdlDPrizeOld::getIsDeletemark, 1);//1是未删 0是已删

        if (mdlDPrizeOld.getState()!=null) {
        queryWrapper.eq(MdlDPrizeOld::getState, mdlDPrizeOld.getState());
        }

                                if (StringUtils.isNotBlank(mdlDPrizeOld.getYear())) {
                                queryWrapper.like(MdlDPrizeOld::getYear, mdlDPrizeOld.getYear());
                                }
                                if (StringUtils.isNotBlank(mdlDPrizeOld.getDeptNew())) {
                                queryWrapper.like(MdlDPrizeOld::getDeptNew, mdlDPrizeOld.getDeptNew());
                                }
                                if (StringUtils.isNotBlank(mdlDPrizeOld.getXmfzr())) {
                                queryWrapper.like(MdlDPrizeOld::getXmfzr, mdlDPrizeOld.getXmfzr());
                                }
                                if (StringUtils.isNotBlank(mdlDPrizeOld.getXmmc())) {
                                queryWrapper.like(MdlDPrizeOld::getXmmc, mdlDPrizeOld.getXmmc());
                                }

        Page<MdlDPrizeOld> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<MdlDPrizeOld> findMdlDPrizeOldList (QueryRequest request, MdlDPrizeOld mdlDPrizeOld){
        try{
        Page<MdlDPrizeOld> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findMdlDPrizeOld(page,mdlDPrizeOld);
        }catch(Exception e){
        log.error("获取新技术获奖记录失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createMdlDPrizeOld(MdlDPrizeOld mdlDPrizeOld){
                mdlDPrizeOld.setCreateTime(new Date());
        mdlDPrizeOld.setIsDeletemark(1);
        this.save(mdlDPrizeOld);
        }

@Override
@Transactional
public void updateMdlDPrizeOld(MdlDPrizeOld mdlDPrizeOld){
        mdlDPrizeOld.setModifyTime(new Date());
        this.baseMapper.updateMdlDPrizeOld(mdlDPrizeOld);
        }

@Override
@Transactional
public void deleteMdlDPrizeOlds(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }