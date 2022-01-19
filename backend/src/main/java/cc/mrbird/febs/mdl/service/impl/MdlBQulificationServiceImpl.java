package cc.mrbird.febs.mdl.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.mdl.entity.MdlBQulification;
import cc.mrbird.febs.mdl.dao.MdlBQulificationMapper;
import cc.mrbird.febs.mdl.service.IMdlBQulificationService;
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
 * @since 2022-01-12
 */
@Slf4j
@Service("IMdlBQulificationService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MdlBQulificationServiceImpl extends ServiceImpl<MdlBQulificationMapper, MdlBQulification> implements IMdlBQulificationService {


@Override
public IPage<MdlBQulification> findMdlBQulifications(QueryRequest request, MdlBQulification mdlBQulification){
        try{
        LambdaQueryWrapper<MdlBQulification> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(MdlBQulification::getIsDeletemark, 1);//1是未删 0是已删

        if (mdlBQulification.getState()!=null) {
        queryWrapper.eq(MdlBQulification::getState, mdlBQulification.getState());
        }

                                if (StringUtils.isNotBlank(mdlBQulification.getQlName())) {
                                queryWrapper.like(MdlBQulification::getQlName, mdlBQulification.getQlName());
                                }
                                if (StringUtils.isNotBlank(mdlBQulification.getFzDateFrom()) && StringUtils.isNotBlank(mdlBQulification.getFzDateTo())) {
                                queryWrapper
                                .ge(MdlBQulification::getFzDate, mdlBQulification.getFzDateFrom())
                                .le(MdlBQulification::getFzDate, mdlBQulification.getFzDateTo());
                                }
                                if (StringUtils.isNotBlank(mdlBQulification.getBgDateFrom()) && StringUtils.isNotBlank(mdlBQulification.getBgDateTo())) {
                                queryWrapper
                                .ge(MdlBQulification::getBgDate, mdlBQulification.getBgDateFrom())
                                .le(MdlBQulification::getBgDate, mdlBQulification.getBgDateTo());
                                }

        Page<MdlBQulification> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<MdlBQulification> findMdlBQulificationList (QueryRequest request, MdlBQulification mdlBQulification){
        try{
        Page<MdlBQulification> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findMdlBQulification(page,mdlBQulification);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createMdlBQulification(MdlBQulification mdlBQulification){
                mdlBQulification.setCreateTime(new Date());
        mdlBQulification.setIsDeletemark(1);
        this.save(mdlBQulification);
        }

@Override
@Transactional
public void updateMdlBQulification(MdlBQulification mdlBQulification){
        mdlBQulification.setModifyTime(new Date());
        this.baseMapper.updateMdlBQulification(mdlBQulification);
        }

@Override
@Transactional
public void deleteMdlBQulifications(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }