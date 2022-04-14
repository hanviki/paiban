package cc.mrbird.febs.mdl.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.mdl.entity.MdlDBadscore;
import cc.mrbird.febs.mdl.dao.MdlDBadscoreMapper;
import cc.mrbird.febs.mdl.service.IMdlDBadscoreService;
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
 * 不良执业行为 服务实现类
 * </p>
 *
 * @author viki
 * @since 2022-04-12
 */
@Slf4j
@Service("IMdlDBadscoreService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MdlDBadscoreServiceImpl extends ServiceImpl<MdlDBadscoreMapper, MdlDBadscore> implements IMdlDBadscoreService {


@Override
public IPage<MdlDBadscore> findMdlDBadscores(QueryRequest request, MdlDBadscore mdlDBadscore){
        try{
        LambdaQueryWrapper<MdlDBadscore> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(MdlDBadscore::getIsDeletemark, 1);//1是未删 0是已删

        if (mdlDBadscore.getState()!=null) {
        queryWrapper.eq(MdlDBadscore::getState, mdlDBadscore.getState());
        }


        Page<MdlDBadscore> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<MdlDBadscore> findMdlDBadscoreList (QueryRequest request, MdlDBadscore mdlDBadscore){
        try{
        Page<MdlDBadscore> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findMdlDBadscore(page,mdlDBadscore);
        }catch(Exception e){
        log.error("获取不良执业行为失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createMdlDBadscore(MdlDBadscore mdlDBadscore){
                mdlDBadscore.setCreateTime(new Date());
        mdlDBadscore.setIsDeletemark(1);
        this.save(mdlDBadscore);
        }

@Override
@Transactional
public void updateMdlDBadscore(MdlDBadscore mdlDBadscore){
        mdlDBadscore.setModifyTime(new Date());
        this.baseMapper.updateMdlDBadscore(mdlDBadscore);
        }

@Override
@Transactional
public void deleteMdlDBadscores(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }