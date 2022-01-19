package cc.mrbird.febs.mdl.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.mdl.entity.MdlBChufang;
import cc.mrbird.febs.mdl.dao.MdlBChufangMapper;
import cc.mrbird.febs.mdl.service.IMdlBChufangService;
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
 * @since 2022-01-19
 */
@Slf4j
@Service("IMdlBChufangService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MdlBChufangServiceImpl extends ServiceImpl<MdlBChufangMapper, MdlBChufang> implements IMdlBChufangService {


@Override
public IPage<MdlBChufang> findMdlBChufangs(QueryRequest request, MdlBChufang mdlBChufang){
        try{
        LambdaQueryWrapper<MdlBChufang> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(MdlBChufang::getIsDeletemark, 1);//1是未删 0是已删
        if (StringUtils.isNotBlank(mdlBChufang.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(MdlBChufang::getUserAccount, mdlBChufang.getUserAccount()).or()
        .like(MdlBChufang::getUserAccountName, mdlBChufang.getUserAccount()));

        }
        if (mdlBChufang.getState()!=null) {
        queryWrapper.eq(MdlBChufang::getState, mdlBChufang.getState());
        }

                                if (StringUtils.isNotBlank(mdlBChufang.getType())) {
                                queryWrapper.eq(MdlBChufang::getType, mdlBChufang.getType());
                                }

        Page<MdlBChufang> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<MdlBChufang> findMdlBChufangList (QueryRequest request, MdlBChufang mdlBChufang){
        try{
        Page<MdlBChufang> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findMdlBChufang(page,mdlBChufang);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createMdlBChufang(MdlBChufang mdlBChufang){
                mdlBChufang.setCreateTime(new Date());
        mdlBChufang.setIsDeletemark(1);
        this.save(mdlBChufang);
        }

@Override
@Transactional
public void updateMdlBChufang(MdlBChufang mdlBChufang){
        mdlBChufang.setModifyTime(new Date());
        this.baseMapper.updateMdlBChufang(mdlBChufang);
        }

@Override
@Transactional
public void deleteMdlBChufangs(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }