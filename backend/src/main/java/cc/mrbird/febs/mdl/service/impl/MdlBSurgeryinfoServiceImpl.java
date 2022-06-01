package cc.mrbird.febs.mdl.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.mdl.entity.MdlBSurgeryinfo;
import cc.mrbird.febs.mdl.dao.MdlBSurgeryinfoMapper;
import cc.mrbird.febs.mdl.entity.MdlBSurgeryinfoD;
import cc.mrbird.febs.mdl.entity.ViewInfo;
import cc.mrbird.febs.mdl.service.IMdlBSurgeryinfoService;
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
@Service("IMdlBSurgeryinfoService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MdlBSurgeryinfoServiceImpl extends ServiceImpl<MdlBSurgeryinfoMapper, MdlBSurgeryinfo> implements IMdlBSurgeryinfoService {


@Override
public IPage<MdlBSurgeryinfo> findMdlBSurgeryinfos(QueryRequest request, MdlBSurgeryinfo mdlBSurgeryinfo){
        try{
        LambdaQueryWrapper<MdlBSurgeryinfo> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(MdlBSurgeryinfo::getIsDeletemark, 1);//1是未删 0是已删
        if (StringUtils.isNotBlank(mdlBSurgeryinfo.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(MdlBSurgeryinfo::getUserAccount, mdlBSurgeryinfo.getUserAccount()).or()
        .like(MdlBSurgeryinfo::getUserAccountName, mdlBSurgeryinfo.getUserAccount()));

        }
        if (mdlBSurgeryinfo.getState()!=null) {
        queryWrapper.eq(MdlBSurgeryinfo::getState, mdlBSurgeryinfo.getState());
        }

                                if (StringUtils.isNotBlank(mdlBSurgeryinfo.getDeptName())) {
                                queryWrapper.like(MdlBSurgeryinfo::getDeptName, mdlBSurgeryinfo.getDeptName());
                                }

            if (StringUtils.isNotBlank(mdlBSurgeryinfo.getValidDatePot())) {

                queryWrapper.apply("valid_date in ("+
                        "SELECT MAX(valid_date) from mdl_b_surgeryinfo b "+

                        " where b.valid_date<='"+mdlBSurgeryinfo.getValidDatePot() +"'  and b.user_account= mdl_b_surgeryinfo.user_account  GROUP BY user_account)");
            }
                                if (StringUtils.isNotBlank(mdlBSurgeryinfo.getValidDateFrom())) {
                                queryWrapper
                                .ge(MdlBSurgeryinfo::getValidDate, mdlBSurgeryinfo.getValidDateFrom());

                                }
            if ( StringUtils.isNotBlank(mdlBSurgeryinfo.getValidDateTo())) {
                queryWrapper

                        .le(MdlBSurgeryinfo::getValidDate, mdlBSurgeryinfo.getValidDateTo());
            }

        Page<MdlBSurgeryinfo> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<ViewInfo> findMdlBSurgeryinfoList (QueryRequest request, MdlBSurgeryinfo mdlBSurgeryinfo){
        try{
        Page<MdlBSurgeryinfo> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findMdlBSurgeryinfo(page,mdlBSurgeryinfo);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createMdlBSurgeryinfo(MdlBSurgeryinfo mdlBSurgeryinfo){
                mdlBSurgeryinfo.setCreateTime(new Date());
        mdlBSurgeryinfo.setIsDeletemark(1);
        this.save(mdlBSurgeryinfo);
        }

@Override
@Transactional
public void updateMdlBSurgeryinfo(MdlBSurgeryinfo mdlBSurgeryinfo){
        mdlBSurgeryinfo.setModifyTime(new Date());
        this.baseMapper.updateMdlBSurgeryinfo(mdlBSurgeryinfo);
        }

@Override
@Transactional
public void deleteMdlBSurgeryinfos(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
    @Override
    @Transactional
    public void InserSub(MdlBSurgeryinfoD mdlBSurgeryinfoD){
       this.baseMapper.insertSub(mdlBSurgeryinfoD.getLevel().split(","),mdlBSurgeryinfoD.getBaseId(),mdlBSurgeryinfoD.getDeptNew());
    }
        }