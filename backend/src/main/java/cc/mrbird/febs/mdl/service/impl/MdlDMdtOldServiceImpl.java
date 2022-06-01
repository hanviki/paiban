package cc.mrbird.febs.mdl.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.mdl.entity.MdlDMdtOld;
import cc.mrbird.febs.mdl.dao.MdlDMdtOldMapper;
import cc.mrbird.febs.mdl.service.IMdlDMdtOldService;
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
 * 新技术新项目旧记录 服务实现类
 * </p>
 *
 * @author viki
 * @since 2022-05-25
 */
@Slf4j
@Service("IMdlDMdtOldService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MdlDMdtOldServiceImpl extends ServiceImpl<MdlDMdtOldMapper, MdlDMdtOld> implements IMdlDMdtOldService {


@Override
public IPage<MdlDMdtOld> findMdlDMdtOlds(QueryRequest request, MdlDMdtOld mdlDMdtOld){
        try{
        LambdaQueryWrapper<MdlDMdtOld> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(MdlDMdtOld::getIsDeletemark, 1);//1是未删 0是已删

        if (mdlDMdtOld.getState()!=null) {
        queryWrapper.eq(MdlDMdtOld::getState, mdlDMdtOld.getState());
        }

                                if (StringUtils.isNotBlank(mdlDMdtOld.getDeptNew())) {
                                queryWrapper.like(MdlDMdtOld::getDeptNew, mdlDMdtOld.getDeptNew());
                                }
                                if (StringUtils.isNotBlank(mdlDMdtOld.getFzr())) {
                                queryWrapper.like(MdlDMdtOld::getFzr, mdlDMdtOld.getFzr());
                                }
                                if (StringUtils.isNotBlank(mdlDMdtOld.getXmmc())) {
                                queryWrapper.like(MdlDMdtOld::getXmmc, mdlDMdtOld.getXmmc());
                                }
                                if (StringUtils.isNotBlank(mdlDMdtOld.getYear())) {
                                queryWrapper.like(MdlDMdtOld::getYear, mdlDMdtOld.getYear());
                                }

        Page<MdlDMdtOld> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<MdlDMdtOld> findMdlDMdtOldList (QueryRequest request, MdlDMdtOld mdlDMdtOld){
        try{
        Page<MdlDMdtOld> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findMdlDMdtOld(page,mdlDMdtOld);
        }catch(Exception e){
        log.error("获取新技术新项目旧记录失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createMdlDMdtOld(MdlDMdtOld mdlDMdtOld){
                mdlDMdtOld.setCreateTime(new Date());
        mdlDMdtOld.setIsDeletemark(1);
        this.save(mdlDMdtOld);
        }

@Override
@Transactional
public void updateMdlDMdtOld(MdlDMdtOld mdlDMdtOld){
        mdlDMdtOld.setModifyTime(new Date());
        this.baseMapper.updateMdlDMdtOld(mdlDMdtOld);
        }

@Override
@Transactional
public void deleteMdlDMdtOlds(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }