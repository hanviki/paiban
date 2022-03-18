package cc.mrbird.febs.mdl.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.mdl.entity.MdlBManager;
import cc.mrbird.febs.mdl.dao.MdlBManagerMapper;
import cc.mrbird.febs.mdl.service.IMdlBManagerService;
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
 * @since 2022-01-17
 */
@Slf4j
@Service("IMdlBManagerService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MdlBManagerServiceImpl extends ServiceImpl<MdlBManagerMapper, MdlBManager> implements IMdlBManagerService {


@Override
public IPage<MdlBManager> findMdlBManagers(QueryRequest request, MdlBManager mdlBManager){
        try{
        LambdaQueryWrapper<MdlBManager> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(MdlBManager::getIsDeletemark, 1);//1是未删 0是已删
        if (StringUtils.isNotBlank(mdlBManager.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(MdlBManager::getUserAccount, mdlBManager.getUserAccount()).or()
        .like(MdlBManager::getUserAccountName, mdlBManager.getUserAccount()));

        }
        if(StringUtils.isNotBlank(mdlBManager.getType())){
                queryWrapper.eq(MdlBManager::getType,mdlBManager.getType());
        }
                if(StringUtils.isNotBlank(mdlBManager.getModifyTimeFrom())){
                        queryWrapper.ge(MdlBManager::getModifyTime,mdlBManager.getModifyTimeFrom());
                }
                if(StringUtils.isNotBlank(mdlBManager.getModifyTimeTo())){
                        queryWrapper.le(MdlBManager::getModifyTime,mdlBManager.getModifyTimeTo());
                }
                if(StringUtils.isNotBlank(mdlBManager.getStartDateFrom())){
                    if(StringUtils.isNotBlank(mdlBManager.getStartDateTo())){
                        queryWrapper.le(MdlBManager::getStartDate,mdlBManager.getStartDateTo());
                        queryWrapper.and(p->p.ge(MdlBManager::getEndDate,mdlBManager.getStartDateFrom()).or().isNull(MdlBManager::getEndDate));
                    }
                    else{
                        queryWrapper.le(MdlBManager::getStartDate,"9999-01-01");
                        queryWrapper.and(p->p.ge(MdlBManager::getEndDate,mdlBManager.getStartDateFrom()).or().isNull(MdlBManager::getEndDate));
                    }
                }

        if (mdlBManager.getState()!=null) {
        queryWrapper.eq(MdlBManager::getState, mdlBManager.getState());
        }

                                if (StringUtils.isNotBlank(mdlBManager.getDeptId())) {
                                queryWrapper.eq(MdlBManager::getDeptId, mdlBManager.getDeptId());
                                }

        Page<MdlBManager> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<MdlBManager> findMdlBManagerList (QueryRequest request, MdlBManager mdlBManager){
        try{
        Page<MdlBManager> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findMdlBManager(page,mdlBManager);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createMdlBManager(MdlBManager mdlBManager){
                mdlBManager.setCreateTime(new Date());
        mdlBManager.setIsDeletemark(1);
        this.save(mdlBManager);
        }

@Override
@Transactional
public void updateMdlBManager(MdlBManager mdlBManager){
        mdlBManager.setModifyTime(new Date());
        this.baseMapper.updateMdlBManager(mdlBManager);
        }

@Override
@Transactional
public void deleteMdlBManagers(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }