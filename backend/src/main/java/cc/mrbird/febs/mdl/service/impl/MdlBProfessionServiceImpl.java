package cc.mrbird.febs.mdl.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.mdl.entity.MdlBProfession;
import cc.mrbird.febs.mdl.dao.MdlBProfessionMapper;
import cc.mrbird.febs.mdl.service.IMdlBProfessionService;
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
 * @since 2022-01-20
 */
@Slf4j
@Service("IMdlBProfessionService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MdlBProfessionServiceImpl extends ServiceImpl<MdlBProfessionMapper, MdlBProfession> implements IMdlBProfessionService {


@Override
public IPage<MdlBProfession> findMdlBProfessions(QueryRequest request, MdlBProfession mdlBProfession){
        try{
        LambdaQueryWrapper<MdlBProfession> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(MdlBProfession::getIsDeletemark, 1);//1是未删 0是已删
        if (StringUtils.isNotBlank(mdlBProfession.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(MdlBProfession::getUserAccount, mdlBProfession.getUserAccount()).or()
        .like(MdlBProfession::getUserAccountName, mdlBProfession.getUserAccount()));

        }
        if (mdlBProfession.getState()!=null) {
        queryWrapper.eq(MdlBProfession::getState, mdlBProfession.getState());
        }


        Page<MdlBProfession> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<MdlBProfession> findMdlBProfessionList (QueryRequest request, MdlBProfession mdlBProfession){
        try{
        Page<MdlBProfession> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findMdlBProfession(page,mdlBProfession);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createMdlBProfession(MdlBProfession mdlBProfession){
                mdlBProfession.setCreateTime(new Date());
        mdlBProfession.setIsDeletemark(1);
        this.save(mdlBProfession);
        }

@Override
@Transactional
public void updateMdlBProfession(MdlBProfession mdlBProfession){
        mdlBProfession.setModifyTime(new Date());
        this.baseMapper.updateMdlBProfession(mdlBProfession);
        }

@Override
@Transactional
public void deleteMdlBProfessions(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }