package cc.mrbird.febs.mdl.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.mdl.entity.MdlDSurgery;
import cc.mrbird.febs.mdl.dao.MdlDSurgeryMapper;
import cc.mrbird.febs.mdl.service.IMdlDSurgeryService;
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
 * 手术目录 服务实现类
 * </p>
 *
 * @author viki
 * @since 2022-05-10
 */
@Slf4j
@Service("IMdlDSurgeryService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MdlDSurgeryServiceImpl extends ServiceImpl<MdlDSurgeryMapper, MdlDSurgery> implements IMdlDSurgeryService {


@Override
public IPage<MdlDSurgery> findMdlDSurgerys(QueryRequest request, MdlDSurgery mdlDSurgery){
        try{
        LambdaQueryWrapper<MdlDSurgery> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(MdlDSurgery::getIsDeletemark, 1);//1是未删 0是已删

        if (mdlDSurgery.getState()!=null) {
        queryWrapper.eq(MdlDSurgery::getState, mdlDSurgery.getState());
        }

                                if (StringUtils.isNotBlank(mdlDSurgery.getDeptNew())) {
                                queryWrapper.like(MdlDSurgery::getDeptNew, mdlDSurgery.getDeptNew());
                                }
                                if (StringUtils.isNotBlank(mdlDSurgery.getCode())) {
                                queryWrapper.like(MdlDSurgery::getCode, mdlDSurgery.getCode());
                                }
                                if (StringUtils.isNotBlank(mdlDSurgery.getName())) {
                                queryWrapper.like(MdlDSurgery::getName, mdlDSurgery.getName());
                                }
                                if (StringUtils.isNotBlank(mdlDSurgery.getLevel())) {
                                queryWrapper.like(MdlDSurgery::getLevel, mdlDSurgery.getLevel());
                                }

        Page<MdlDSurgery> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<MdlDSurgery> findMdlDSurgeryList (QueryRequest request, MdlDSurgery mdlDSurgery){
        try{
        Page<MdlDSurgery> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findMdlDSurgery(page,mdlDSurgery);
        }catch(Exception e){
        log.error("获取手术目录失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createMdlDSurgery(MdlDSurgery mdlDSurgery){
                mdlDSurgery.setCreateTime(new Date());
        mdlDSurgery.setIsDeletemark(1);
        this.save(mdlDSurgery);
        }

@Override
@Transactional
public void updateMdlDSurgery(MdlDSurgery mdlDSurgery){
        mdlDSurgery.setModifyTime(new Date());
        this.baseMapper.updateMdlDSurgery(mdlDSurgery);
        }

@Override
@Transactional
public void deleteMdlDSurgerys(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        MdlDSurgery surgery= new MdlDSurgery();
        surgery.setIsDeletemark(0);
        LambdaQueryWrapper<MdlDSurgery> queryWrapper =new LambdaQueryWrapper<>();
        queryWrapper.in(MdlDSurgery::getId,list);
        this.baseMapper.update(surgery,queryWrapper);
       // this.baseMapper.deleteBatchIds(list);
        }

        }