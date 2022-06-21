package cc.mrbird.febs.mdl.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.mdl.entity.MdlBMedicalchina;
import cc.mrbird.febs.mdl.dao.MdlBMedicalchinaMapper;
import cc.mrbird.febs.mdl.service.IMdlBMedicalchinaService;
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
 *  服务实现类
 * </p>
 *
 * @author viki
 * @since 2022-06-08
 */
@Slf4j
@Service("IMdlBMedicalchinaService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MdlBMedicalchinaServiceImpl extends ServiceImpl<MdlBMedicalchinaMapper, MdlBMedicalchina> implements IMdlBMedicalchinaService {


@Override
public IPage<MdlBMedicalchina> findMdlBMedicalchinas(QueryRequest request, MdlBMedicalchina mdlBMedicalchina){
        try{
        LambdaQueryWrapper<MdlBMedicalchina> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(MdlBMedicalchina::getIsDeletemark, 1);//1是未删 0是已删
        if (StringUtils.isNotBlank(mdlBMedicalchina.getFileId())) {
                queryWrapper.apply("mdl_b_medicalchina.id in (select base_id from mdl_b_medicalchina_d where user_account_name like '%"+mdlBMedicalchina.getFileId()+"%' or user_account='"+mdlBMedicalchina.getFileId()+"')");
        }
        if (mdlBMedicalchina.getState()!=null) {
        queryWrapper.eq(MdlBMedicalchina::getState, mdlBMedicalchina.getState());
        }

                                if (StringUtils.isNotBlank(mdlBMedicalchina.getName())) {
                                queryWrapper.like(MdlBMedicalchina::getName, mdlBMedicalchina.getName());
                                }

        Page<MdlBMedicalchina> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<MdlBMedicalchina> findMdlBMedicalchinaList (QueryRequest request, MdlBMedicalchina mdlBMedicalchina){
        try{
        Page<MdlBMedicalchina> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findMdlBMedicalchina(page,mdlBMedicalchina);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createMdlBMedicalchina(MdlBMedicalchina mdlBMedicalchina){
                mdlBMedicalchina.setId(UUID.randomUUID().toString());
        mdlBMedicalchina.setCreateTime(new Date());
        mdlBMedicalchina.setIsDeletemark(1);
        this.save(mdlBMedicalchina);
        }

@Override
@Transactional
public void updateMdlBMedicalchina(MdlBMedicalchina mdlBMedicalchina){
        mdlBMedicalchina.setModifyTime(new Date());
        this.baseMapper.updateMdlBMedicalchina(mdlBMedicalchina);
        }

@Override
@Transactional
public void deleteMdlBMedicalchinas(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }