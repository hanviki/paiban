package cc.mrbird.febs.mdl.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.mdl.entity.MdlBMedicalchinaD;
import cc.mrbird.febs.mdl.dao.MdlBMedicalchinaDMapper;
import cc.mrbird.febs.mdl.service.IMdlBMedicalchinaDService;
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
@Service("IMdlBMedicalchinaDService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MdlBMedicalchinaDServiceImpl extends ServiceImpl<MdlBMedicalchinaDMapper, MdlBMedicalchinaD> implements IMdlBMedicalchinaDService {


@Override
public IPage<MdlBMedicalchinaD> findMdlBMedicalchinaDs(QueryRequest request, MdlBMedicalchinaD mdlBMedicalchinaD){
        try{
        LambdaQueryWrapper<MdlBMedicalchinaD> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(MdlBMedicalchinaD::getIsDeletemark, 1);//1是未删 0是已删
        if (StringUtils.isNotBlank(mdlBMedicalchinaD.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(MdlBMedicalchinaD::getUserAccount, mdlBMedicalchinaD.getUserAccount()).or()
        .like(MdlBMedicalchinaD::getUserAccountName, mdlBMedicalchinaD.getUserAccount()));

        }
        if (mdlBMedicalchinaD.getState()!=null) {
        queryWrapper.eq(MdlBMedicalchinaD::getState, mdlBMedicalchinaD.getState());
        }

                                if (StringUtils.isNotBlank(mdlBMedicalchinaD.getUserAccount())) {
                                queryWrapper.like(MdlBMedicalchinaD::getUserAccount, mdlBMedicalchinaD.getUserAccount());
                                }

        Page<MdlBMedicalchinaD> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<MdlBMedicalchinaD> findMdlBMedicalchinaDList (QueryRequest request, MdlBMedicalchinaD mdlBMedicalchinaD){
        try{
        Page<MdlBMedicalchinaD> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findMdlBMedicalchinaD(page,mdlBMedicalchinaD);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createMdlBMedicalchinaD(MdlBMedicalchinaD mdlBMedicalchinaD){
                mdlBMedicalchinaD.setId(UUID.randomUUID().toString());
        mdlBMedicalchinaD.setCreateTime(new Date());
        mdlBMedicalchinaD.setIsDeletemark(1);
        this.save(mdlBMedicalchinaD);
        }

@Override
@Transactional
public void updateMdlBMedicalchinaD(MdlBMedicalchinaD mdlBMedicalchinaD){
        mdlBMedicalchinaD.setModifyTime(new Date());
        this.baseMapper.updateMdlBMedicalchinaD(mdlBMedicalchinaD);
        }

@Override
@Transactional
public void deleteMdlBMedicalchinaDs(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }