package cc.mrbird.febs.mdl.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.mdl.entity.MdlBMdt;
import cc.mrbird.febs.mdl.dao.MdlBMdtMapper;
import cc.mrbird.febs.mdl.service.IMdlBMdtService;
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
@Service("IMdlBMdtService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MdlBMdtServiceImpl extends ServiceImpl<MdlBMdtMapper, MdlBMdt> implements IMdlBMdtService {


@Override
public IPage<MdlBMdt> findMdlBMdts(QueryRequest request, MdlBMdt mdlBMdt){
        try{
        LambdaQueryWrapper<MdlBMdt> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(MdlBMdt::getIsDeletemark, 1);//1是未删 0是已删
        if (StringUtils.isNotBlank(mdlBMdt.getUserAccountLeader())) {
                queryWrapper.and(wrap->  wrap.eq(MdlBMdt::getUserAccountLeader, mdlBMdt.getUserAccountLeader()).or()
                        .like(MdlBMdt::getUserAccountNameLeader, mdlBMdt.getUserAccountLeader()));

            }
            if (StringUtils.isNotBlank(mdlBMdt.getUserAccountAssist())) {
                queryWrapper.and(wrap->  wrap.eq(MdlBMdt::getUserAccountAssist, mdlBMdt.getUserAccountAssist()).or()
                        .like(MdlBMdt::getUserAccountNameAssist, mdlBMdt.getUserAccountAssist()));

            }
        if (mdlBMdt.getState()!=null) {
        queryWrapper.eq(MdlBMdt::getState, mdlBMdt.getState());
        }

                                if (StringUtils.isNotBlank(mdlBMdt.getTeamName())) {
                                queryWrapper.like(MdlBMdt::getTeamName, mdlBMdt.getTeamName());
                                }
                                if (StringUtils.isNotBlank(mdlBMdt.getDeptHead())&& !mdlBMdt.getDeptHead().equals("-1")) {
                                queryWrapper.like(MdlBMdt::getDeptHead, mdlBMdt.getDeptHead());
                                }


            if (StringUtils.isNotBlank(mdlBMdt.getTelLeader2())) { //考核年度
                queryWrapper.apply("mdl_b_mdt.id in (select base_id from mdl_b_mdt_file where IS_DELETEMARK=1 and year='"+mdlBMdt.getTelLeader2()+"')");
            }
            if (StringUtils.isNotBlank(mdlBMdt.getEmailLeader2())) { //考核结果
                queryWrapper.apply("mdl_b_mdt.id in (select base_id from mdl_b_mdt_file where IS_DELETEMARK=1 and result='"+mdlBMdt.getEmailLeader2()+"')");
            }
            if (StringUtils.isNotBlank(mdlBMdt.getTelAssist())) { //考核结果
                queryWrapper.apply("mdl_b_mdt.id in (select base_id from mdl_b_mdt_d where IS_DELETEMARK=1 and user_account='"+mdlBMdt.getTelAssist()+"' or user_account_name like '%"+mdlBMdt.getTelAssist()+"%' )");
            }





        Page<MdlBMdt> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<MdlBMdt> findMdlBMdtList (QueryRequest request, MdlBMdt mdlBMdt){
        try{
        Page<MdlBMdt> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findMdlBMdt(page,mdlBMdt);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createMdlBMdt(MdlBMdt mdlBMdt){
                mdlBMdt.setCreateTime(new Date());
        mdlBMdt.setIsDeletemark(1);
        this.save(mdlBMdt);
        }

@Override
@Transactional
public void updateMdlBMdt(MdlBMdt mdlBMdt){
        mdlBMdt.setModifyTime(new Date());
        this.baseMapper.updateMdlBMdt(mdlBMdt);
        }

@Override
@Transactional
public void deleteMdlBMdts(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

    @Override
    @Transactional
    public void  deleteByBaseId(Long id){
    this.baseMapper.DeletByBaseId(id);
    }
        }