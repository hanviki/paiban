package cc.mrbird.febs.sdl.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.sdl.entity.SdlBZizhiapply;
import cc.mrbird.febs.sdl.dao.SdlBZizhiapplyMapper;
import cc.mrbird.febs.sdl.service.ISdlBZizhiapplyService;
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
 * 资质变更申请 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-10-13
 */
@Slf4j
@Service("ISdlBZizhiapplyService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class SdlBZizhiapplyServiceImpl extends ServiceImpl<SdlBZizhiapplyMapper, SdlBZizhiapply> implements ISdlBZizhiapplyService {



@Override
public IPage<SdlBZizhiapply> findSdlBZizhiapplys(QueryRequest request, SdlBZizhiapply sdlBZizhiapply){
        try{
        LambdaQueryWrapper<SdlBZizhiapply> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(SdlBZizhiapply::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(sdlBZizhiapply.getUserName())) {
                                queryWrapper.like(SdlBZizhiapply::getUserName, sdlBZizhiapply.getUserName());
                                }
            if (StringUtils.isNotBlank(sdlBZizhiapply.getUserDept())) {
                queryWrapper.eq(SdlBZizhiapply::getUserDept, sdlBZizhiapply.getUserDept());
            }
            if (StringUtils.isNotBlank(sdlBZizhiapply.getUserAccount())) {
                queryWrapper.and( p->p.eq(SdlBZizhiapply::getUserAccount, sdlBZizhiapply.getUserAccount()).or().like(SdlBZizhiapply::getUserAccountName, sdlBZizhiapply.getUserAccount()));
            }
                                if (sdlBZizhiapply.getState()!=null) {
                                queryWrapper.eq(SdlBZizhiapply::getState, sdlBZizhiapply.getState());
                                }

        Page<SdlBZizhiapply> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<SdlBZizhiapply> findSdlBZizhiapplyList (QueryRequest request, SdlBZizhiapply sdlBZizhiapply){
        try{
        Page<SdlBZizhiapply> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findSdlBZizhiapply(page,sdlBZizhiapply);
        }catch(Exception e){
        log.error("获取资质变更申请失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createSdlBZizhiapply(SdlBZizhiapply sdlBZizhiapply){
                sdlBZizhiapply.setId(UUID.randomUUID().toString());
        sdlBZizhiapply.setCreateTime(new Date());
        sdlBZizhiapply.setIsDeletemark(1);
        this.save(sdlBZizhiapply);
        }

@Override
@Transactional
public void updateSdlBZizhiapply(SdlBZizhiapply sdlBZizhiapply){
        sdlBZizhiapply.setModifyTime(new Date());
        this.baseMapper.updateSdlBZizhiapply(sdlBZizhiapply);
  if(sdlBZizhiapply.getState()!=null &&sdlBZizhiapply.getState().equals(3)){
      this.baseMapper.updateUserType(sdlBZizhiapply.getId());
  }
        }

@Override
@Transactional
public void deleteSdlBZizhiapplys(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }