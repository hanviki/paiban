package cc.mrbird.febs.sdl.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.sdl.entity.SdlBPersoninfo;
import cc.mrbird.febs.sdl.dao.SdlBPersoninfoMapper;
import cc.mrbird.febs.sdl.service.ISdlBPersoninfoService;
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
 * @since 2021-12-10
 */
@Slf4j
@Service("ISdlBPersoninfoService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class SdlBPersoninfoServiceImpl extends ServiceImpl<SdlBPersoninfoMapper, SdlBPersoninfo> implements ISdlBPersoninfoService {


@Override
public IPage<SdlBPersoninfo> findSdlBPersoninfos(QueryRequest request, SdlBPersoninfo sdlBPersoninfo){
        try{
        LambdaQueryWrapper<SdlBPersoninfo> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(SdlBPersoninfo::getIsDeletemark, 1);//1是未删 0是已删
        if (StringUtils.isNotBlank(sdlBPersoninfo.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(SdlBPersoninfo::getUserAccount, sdlBPersoninfo.getUserAccount()).or()
        .like(SdlBPersoninfo::getUserAccountName, sdlBPersoninfo.getUserAccount()));

        }
        if (sdlBPersoninfo.getState()!=null) {
        queryWrapper.eq(SdlBPersoninfo::getState, sdlBPersoninfo.getState());
        }

                                if (StringUtils.isNotBlank(sdlBPersoninfo.getDeptId())) {
                                queryWrapper.eq(SdlBPersoninfo::getDeptId, sdlBPersoninfo.getDeptId());
                                }
                                if (StringUtils.isNotBlank(sdlBPersoninfo.getUserAccountName())) {
                                queryWrapper.like(SdlBPersoninfo::getUserAccountName, sdlBPersoninfo.getUserAccountName());
                                }
                                if (StringUtils.isNotBlank(sdlBPersoninfo.getUserAccount())) {
                                queryWrapper.eq(SdlBPersoninfo::getUserAccount, sdlBPersoninfo.getUserAccount());
                                }
                                if (sdlBPersoninfo.getState()!=null) {
                                queryWrapper.eq(SdlBPersoninfo::getState, sdlBPersoninfo.getState());
                                }

        Page<SdlBPersoninfo> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<SdlBPersoninfo> findSdlBPersoninfoList (QueryRequest request, SdlBPersoninfo sdlBPersoninfo){
        try{
        Page<SdlBPersoninfo> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findSdlBPersoninfo(page,sdlBPersoninfo);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createSdlBPersoninfo(SdlBPersoninfo sdlBPersoninfo){
                sdlBPersoninfo.setCreateTime(new Date());
        sdlBPersoninfo.setIsDeletemark(1);
        this.save(sdlBPersoninfo);
        }

@Override
@Transactional
public void updateSdlBPersoninfo(SdlBPersoninfo sdlBPersoninfo){
        sdlBPersoninfo.setModifyTime(new Date());
        this.baseMapper.updateSdlBPersoninfo(sdlBPersoninfo);
        }

@Override
@Transactional
public void deleteSdlBPersoninfos(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
    @Override
    @Transactional
    public  void deleteByDeptId(String deptID){
        this.baseMapper.deleteByDeptId(deptID);
    }

    @Override
    @Transactional
    public  List<SdlBPersoninfo> sendMess(String startDate){
       return  this.baseMapper.telList(startDate);
    }
        }