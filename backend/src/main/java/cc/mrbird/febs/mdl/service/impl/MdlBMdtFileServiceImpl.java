package cc.mrbird.febs.mdl.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.mdl.entity.MdlBMdtFile;
import cc.mrbird.febs.mdl.dao.MdlBMdtFileMapper;
import cc.mrbird.febs.mdl.service.IMdlBMdtFileService;
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
 * @since 2022-03-23
 */
@Slf4j
@Service("IMdlBMdtFileService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MdlBMdtFileServiceImpl extends ServiceImpl<MdlBMdtFileMapper, MdlBMdtFile> implements IMdlBMdtFileService {


@Override
public IPage<MdlBMdtFile> findMdlBMdtFiles(QueryRequest request, MdlBMdtFile mdlBMdtFile){
        try{
        LambdaQueryWrapper<MdlBMdtFile> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(MdlBMdtFile::getIsDeletemark, 1);//1是未删 0是已删

        if (mdlBMdtFile.getState()!=null) {
                        queryWrapper.eq(MdlBMdtFile::getState, mdlBMdtFile.getState());
                }
                if (mdlBMdtFile.getBaseId()!=null) {
                        queryWrapper.eq(MdlBMdtFile::getBaseId, mdlBMdtFile.getBaseId());
                }
                else{
                        queryWrapper.eq(MdlBMdtFile::getBaseId, "-1");
                }
        Page<MdlBMdtFile> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<MdlBMdtFile> findMdlBMdtFileList (QueryRequest request, MdlBMdtFile mdlBMdtFile){
        try{
        Page<MdlBMdtFile> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findMdlBMdtFile(page,mdlBMdtFile);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createMdlBMdtFile(MdlBMdtFile mdlBMdtFile){
                mdlBMdtFile.setId(UUID.randomUUID().toString());
        mdlBMdtFile.setCreateTime(new Date());
        mdlBMdtFile.setIsDeletemark(1);
        this.save(mdlBMdtFile);
        }

@Override
@Transactional
public void updateMdlBMdtFile(MdlBMdtFile mdlBMdtFile){
        mdlBMdtFile.setModifyTime(new Date());
        this.baseMapper.updateMdlBMdtFile(mdlBMdtFile);
        }

@Override
@Transactional
public void deleteMdlBMdtFiles(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }