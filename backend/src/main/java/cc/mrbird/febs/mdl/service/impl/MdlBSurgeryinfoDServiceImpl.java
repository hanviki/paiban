package cc.mrbird.febs.mdl.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.mdl.entity.MdlBSurgeryinfoD;
import cc.mrbird.febs.mdl.dao.MdlBSurgeryinfoDMapper;
import cc.mrbird.febs.mdl.service.IMdlBSurgeryinfoDService;
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
 * @since 2022-05-18
 */
@Slf4j
@Service("IMdlBSurgeryinfoDService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MdlBSurgeryinfoDServiceImpl extends ServiceImpl<MdlBSurgeryinfoDMapper, MdlBSurgeryinfoD> implements IMdlBSurgeryinfoDService {


@Override
public IPage<MdlBSurgeryinfoD> findMdlBSurgeryinfoDs(QueryRequest request, MdlBSurgeryinfoD mdlBSurgeryinfoD){
        try{
        LambdaQueryWrapper<MdlBSurgeryinfoD> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(MdlBSurgeryinfoD::getIsDeletemark, 1);//1是未删 0是已删

        if (mdlBSurgeryinfoD.getState()!=null) {
        queryWrapper.eq(MdlBSurgeryinfoD::getState, mdlBSurgeryinfoD.getState());
        }


        Page<MdlBSurgeryinfoD> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<MdlBSurgeryinfoD> findMdlBSurgeryinfoDList (QueryRequest request, MdlBSurgeryinfoD mdlBSurgeryinfoD){
        try{
        Page<MdlBSurgeryinfoD> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findMdlBSurgeryinfoD(page,mdlBSurgeryinfoD);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createMdlBSurgeryinfoD(MdlBSurgeryinfoD mdlBSurgeryinfoD){
                mdlBSurgeryinfoD.setCreateTime(new Date());
        mdlBSurgeryinfoD.setIsDeletemark(1);
        this.save(mdlBSurgeryinfoD);
        }

@Override
@Transactional
public void updateMdlBSurgeryinfoD(MdlBSurgeryinfoD mdlBSurgeryinfoD){
        mdlBSurgeryinfoD.setModifyTime(new Date());
        this.baseMapper.updateMdlBSurgeryinfoD(mdlBSurgeryinfoD);
        }

@Override
@Transactional
public void deleteMdlBSurgeryinfoDs(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }