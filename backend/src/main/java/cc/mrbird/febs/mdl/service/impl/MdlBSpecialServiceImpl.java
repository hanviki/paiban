package cc.mrbird.febs.mdl.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.mdl.entity.MdlBSpecial;
import cc.mrbird.febs.mdl.dao.MdlBSpecialMapper;
import cc.mrbird.febs.mdl.service.IMdlBSpecialService;
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
@Service("IMdlBSpecialService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MdlBSpecialServiceImpl extends ServiceImpl<MdlBSpecialMapper, MdlBSpecial> implements IMdlBSpecialService {


@Override
public IPage<MdlBSpecial> findMdlBSpecials(QueryRequest request, MdlBSpecial mdlBSpecial){
        try{
        LambdaQueryWrapper<MdlBSpecial> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(MdlBSpecial::getIsDeletemark, 1);//1是未删 0是已删
        if (StringUtils.isNotBlank(mdlBSpecial.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(MdlBSpecial::getUserAccount, mdlBSpecial.getUserAccount()).or()
        .like(MdlBSpecial::getUserAccountName, mdlBSpecial.getUserAccount()));

        }
        if (mdlBSpecial.getState()!=null) {
        queryWrapper.eq(MdlBSpecial::getState, mdlBSpecial.getState());
        }


        Page<MdlBSpecial> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<MdlBSpecial> findMdlBSpecialList (QueryRequest request, MdlBSpecial mdlBSpecial){
        try{
        Page<MdlBSpecial> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findMdlBSpecial(page,mdlBSpecial);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createMdlBSpecial(MdlBSpecial mdlBSpecial){
                mdlBSpecial.setCreateTime(new Date());
        mdlBSpecial.setIsDeletemark(1);
        this.save(mdlBSpecial);
        }

@Override
@Transactional
public void updateMdlBSpecial(MdlBSpecial mdlBSpecial){
        mdlBSpecial.setModifyTime(new Date());
        this.baseMapper.updateMdlBSpecial(mdlBSpecial);
        }

@Override
@Transactional
public void deleteMdlBSpecials(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }