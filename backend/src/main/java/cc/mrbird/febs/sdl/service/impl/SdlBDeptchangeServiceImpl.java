package cc.mrbird.febs.sdl.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.sdl.entity.SdlBDeptchange;
import cc.mrbird.febs.sdl.dao.SdlBDeptchangeMapper;
import cc.mrbird.febs.sdl.service.ISdlBDeptchangeService;
import cc.mrbird.febs.system.dao.DeptMapper;
import cc.mrbird.febs.system.domain.Dept;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @since 2021-10-14
 */
@Slf4j
@Service("ISdlBDeptchangeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class SdlBDeptchangeServiceImpl extends ServiceImpl<SdlBDeptchangeMapper, SdlBDeptchange> implements ISdlBDeptchangeService {

    @Autowired
    private DeptMapper deptMapper;

@Override
public IPage<SdlBDeptchange> findSdlBDeptchanges(QueryRequest request, SdlBDeptchange sdlBDeptchange){
        try{
        LambdaQueryWrapper<SdlBDeptchange> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(SdlBDeptchange::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(sdlBDeptchange.getUserDept())) {
                queryWrapper.eq(SdlBDeptchange::getUserDept, sdlBDeptchange.getUserDept());
            }
                                if (StringUtils.isNotBlank(sdlBDeptchange.getUserAccount())) {
                                queryWrapper.and( p->p.eq(SdlBDeptchange::getUserAccount, sdlBDeptchange.getUserAccount()).or().like(SdlBDeptchange::getUserAccountName, sdlBDeptchange.getUserAccount()));
                                }
                                if (sdlBDeptchange.getState()!=null) {
                                queryWrapper.eq(SdlBDeptchange::getState, sdlBDeptchange.getState());
                                }

        Page<SdlBDeptchange> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<SdlBDeptchange> findSdlBDeptchangeList (QueryRequest request, SdlBDeptchange sdlBDeptchange){
        try{
        Page<SdlBDeptchange> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findSdlBDeptchange(page,sdlBDeptchange);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createSdlBDeptchange(SdlBDeptchange sdlBDeptchange){

    SdlBDeptchange sdl;
    sdl=this.baseMapper.getUserByAccount(sdlBDeptchange.getUserAccount());
    LambdaQueryWrapper<Dept> queryWrapper= new LambdaQueryWrapper<>();
    queryWrapper.eq(Dept::getDeptId,sdlBDeptchange.getDeptId());
    Dept dept= this.deptMapper.selectOne(queryWrapper);
    String dept_new_id="";
    String dept_new_name="";
    String bq_new_name="";
    if(dept!=null){
        dept_new_id= dept.getDeptId();
        if(dept.getParentId().equals("0")){
            dept_new_name= dept.getDeptName();
        }
        else{
            bq_new_name= dept.getDeptName();
            LambdaQueryWrapper<Dept> queryWrapper2= new LambdaQueryWrapper<>();
            queryWrapper2.eq(Dept::getDeptId,dept.getParentId());
            Dept dept2= this.deptMapper.selectOne(queryWrapper2);
            if(dept2!=null){
                dept_new_name= dept2.getDeptName();
            }
        }
    }
    sdl.setUserDept(sdlBDeptchange.getUserDept());
    sdl.setOperationName(sdlBDeptchange.getOperationName());
    sdl.setDeptNewId(dept_new_id);
    sdl.setDeptNewName(dept_new_name);
    sdl.setBqNewName(bq_new_name);
    sdl.setId(UUID.randomUUID().toString());
    sdl.setCreateTime(new Date());
    sdl.setIsDeletemark(1);
        this.save(sdl);
        //修改用户的科室和病区
        this.baseMapper.updateUserDept(sdlBDeptchange.getUserAccount(),dept_new_id,dept_new_name,bq_new_name);
        }

@Override
@Transactional
public void updateSdlBDeptchange(SdlBDeptchange sdlBDeptchange){
    SdlBDeptchange sdl;
    sdl=this.baseMapper.getUserByAccount(sdlBDeptchange.getUserAccount());
    String dept_new_id="";
    String dept_new_name="";
    String bq_new_name="";
    sdl.setUserDept(sdlBDeptchange.getUserDept());
    sdl.setOperationName(sdlBDeptchange.getOperationName());
    sdl.setDeptNewId(dept_new_id);
    sdl.setDeptNewName(dept_new_name);
    sdl.setBqNewName(bq_new_name);
    sdl.setId(UUID.randomUUID().toString());
    sdl.setCreateTime(new Date());
    sdl.setIsDeletemark(1);
    this.save(sdl);
    //修改用户的科室和病区
    this.baseMapper.updateUserDept(sdlBDeptchange.getUserAccount(),dept_new_id,dept_new_name,bq_new_name);
        }

@Override
@Transactional
public void deleteSdlBDeptchanges(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.updateUserDept(list.get(0),"","","");
        }

        }