package cc.mrbird.febs.mdl.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.mdl.entity.MdlDOperation;
import cc.mrbird.febs.mdl.dao.MdlDOperationMapper;
import cc.mrbird.febs.mdl.entity.MdlDSurgery;
import cc.mrbird.febs.mdl.service.IMdlDOperationService;
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
 * 操作目录 服务实现类
 * </p>
 *
 * @author viki
 * @since 2022-06-09
 */
@Slf4j
@Service("IMdlDOperationService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MdlDOperationServiceImpl extends ServiceImpl<MdlDOperationMapper, MdlDOperation> implements IMdlDOperationService {


@Override
public IPage<MdlDOperation> findMdlDOperations(QueryRequest request, MdlDOperation mdlDOperation){
        try{
        LambdaQueryWrapper<MdlDOperation> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(MdlDOperation::getIsDeletemark, 1);//1是未删 0是已删

            if (mdlDOperation.getState() != null) {

                if (mdlDOperation.getState().equals(1)) { //在用
                    queryWrapper.apply("mdl_d_operation.end_date is NULL");
                }
                if (mdlDOperation.getState().equals(2)) { //停用
                    queryWrapper.apply("mdl_d_operation.end_date is not NULL");
                }
            }

            if (StringUtils.isNotBlank(mdlDOperation.getDeptNew())) {
                queryWrapper.like(MdlDOperation::getDeptNew, mdlDOperation.getDeptNew());
            }
            if (StringUtils.isNotBlank(mdlDOperation.getCode())) {
                queryWrapper.like(MdlDOperation::getCode, mdlDOperation.getCode());
            }
            if (StringUtils.isNotBlank(mdlDOperation.getName())) {
                queryWrapper.like(MdlDOperation::getName, mdlDOperation.getName());
            }
            if (StringUtils.isNotBlank(mdlDOperation.getLevel())) {
                queryWrapper.like(MdlDOperation::getLevel, mdlDOperation.getLevel());
            }
        Page<MdlDOperation> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<MdlDOperation> findMdlDOperationList (QueryRequest request, MdlDOperation mdlDOperation){
        try{
        Page<MdlDOperation> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findMdlDOperation(page,mdlDOperation);
        }catch(Exception e){
        log.error("获取操作目录失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createMdlDOperation(MdlDOperation mdlDOperation) throws FebsException {
    LambdaQueryWrapper<MdlDOperation> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(MdlDOperation::getCode, mdlDOperation.getCode());
    queryWrapper.apply("mdl_d_operation.end_date is NULL");
    int count = this.baseMapper.selectCount(queryWrapper);
    if (count > 0) {
        throw new FebsException("请停用当前操作编码，然后再新增");
    }
    mdlDOperation.setCreateTime(new Date());
    mdlDOperation.setIsDeletemark(1);
    this.save(mdlDOperation);
        }

@Override
@Transactional
public void updateMdlDOperation(MdlDOperation mdlDOperation){
        mdlDOperation.setModifyTime(new Date());
        this.baseMapper.updateMdlDOperation(mdlDOperation);
        }

@Override
@Transactional
public void deleteMdlDOperations(String[]Ids){
    List<String> list = Arrays.asList(Ids);
    MdlDOperation surgery = new MdlDOperation();
    surgery.setIsDeletemark(0);
    LambdaQueryWrapper<MdlDOperation> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.in(MdlDOperation::getId, list);
    this.baseMapper.update(surgery, queryWrapper);
        }

        }