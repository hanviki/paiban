package cc.mrbird.febs.mdl.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.mdl.entity.MdlBForeignD;
import cc.mrbird.febs.mdl.dao.MdlBForeignDMapper;
import cc.mrbird.febs.mdl.service.IMdlBForeignDService;
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
 * @since 2022-05-16
 */
@Slf4j
@Service("IMdlBForeignDService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MdlBForeignDServiceImpl extends ServiceImpl<MdlBForeignDMapper, MdlBForeignD> implements IMdlBForeignDService {


@Override
public IPage<MdlBForeignD> findMdlBForeignDs(QueryRequest request, MdlBForeignD mdlBForeignD){
        try{
        LambdaQueryWrapper<MdlBForeignD> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(MdlBForeignD::getIsDeletemark, 1);//1是未删 0是已删
        if (StringUtils.isNotBlank(mdlBForeignD.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(MdlBForeignD::getUserAccount, mdlBForeignD.getUserAccount()).or()
        .like(MdlBForeignD::getUserAccountName, mdlBForeignD.getUserAccount()));

        }
        if (mdlBForeignD.getState()!=null) {
        queryWrapper.eq(MdlBForeignD::getState, mdlBForeignD.getState());
        }

                                if (StringUtils.isNotBlank(mdlBForeignD.getUserAccount())) {
                                queryWrapper.like(MdlBForeignD::getUserAccount, mdlBForeignD.getUserAccount());
                                }

        Page<MdlBForeignD> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<MdlBForeignD> findMdlBForeignDList (QueryRequest request, MdlBForeignD mdlBForeignD){
        try{
        Page<MdlBForeignD> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findMdlBForeignD(page,mdlBForeignD);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createMdlBForeignD(MdlBForeignD mdlBForeignD){
                mdlBForeignD.setCreateTime(new Date());
        mdlBForeignD.setIsDeletemark(1);
        this.save(mdlBForeignD);
        }

@Override
@Transactional
public void updateMdlBForeignD(MdlBForeignD mdlBForeignD){
        mdlBForeignD.setModifyTime(new Date());
        this.baseMapper.updateMdlBForeignD(mdlBForeignD);
        }

@Override
@Transactional
public void deleteMdlBForeignDs(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }