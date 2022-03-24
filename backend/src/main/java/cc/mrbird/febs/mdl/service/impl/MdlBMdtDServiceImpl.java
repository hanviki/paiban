package cc.mrbird.febs.mdl.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.mdl.entity.MdlBMdtD;
import cc.mrbird.febs.mdl.dao.MdlBMdtDMapper;
import cc.mrbird.febs.mdl.service.IMdlBMdtDService;
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
 * @since 2022-03-21
 */
@Slf4j
@Service("IMdlBMdtDService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MdlBMdtDServiceImpl extends ServiceImpl<MdlBMdtDMapper, MdlBMdtD> implements IMdlBMdtDService {


@Override
public IPage<MdlBMdtD> findMdlBMdtDs(QueryRequest request, MdlBMdtD mdlBMdtD){
        try{
        LambdaQueryWrapper<MdlBMdtD> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(MdlBMdtD::getIsDeletemark, 1);//1是未删 0是已删
        if (StringUtils.isNotBlank(mdlBMdtD.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(MdlBMdtD::getUserAccount, mdlBMdtD.getUserAccount()).or()
        .like(MdlBMdtD::getUserAccountName, mdlBMdtD.getUserAccount()));

        }
        if (mdlBMdtD.getState()!=null) {
        queryWrapper.eq(MdlBMdtD::getState, mdlBMdtD.getState());
        }
                if (mdlBMdtD.getType()!=null) {
                        queryWrapper.eq(MdlBMdtD::getType, mdlBMdtD.getType());
                }
                if (mdlBMdtD.getBaseId()!=null ) {
                        queryWrapper.eq(MdlBMdtD::getBaseId, mdlBMdtD.getBaseId());
                }
                else{
                        queryWrapper.eq(MdlBMdtD::getBaseId, "-1");
                }


        Page<MdlBMdtD> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<MdlBMdtD> findMdlBMdtDList (QueryRequest request, MdlBMdtD mdlBMdtD){
        try{
        Page<MdlBMdtD> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findMdlBMdtD(page,mdlBMdtD);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createMdlBMdtD(MdlBMdtD mdlBMdtD){
                mdlBMdtD.setCreateTime(new Date());
        mdlBMdtD.setIsDeletemark(1);
        this.save(mdlBMdtD);
        }

@Override
@Transactional
public void updateMdlBMdtD(MdlBMdtD mdlBMdtD){
        mdlBMdtD.setModifyTime(new Date());
        this.baseMapper.updateMdlBMdtD(mdlBMdtD);
        }

@Override
@Transactional
public void deleteMdlBMdtDs(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }