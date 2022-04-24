package cc.mrbird.febs.xxb.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.xxb.entity.XxbBProjdept;
import cc.mrbird.febs.xxb.dao.XxbBProjdeptMapper;
import cc.mrbird.febs.xxb.service.IXxbBProjdeptService;
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
 * 服务实现类
 * </p>
 *
 * @author viki
 * @since 2022-04-20
 */
@Slf4j
@Service("IXxbBProjdeptService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class XxbBProjdeptServiceImpl extends ServiceImpl<XxbBProjdeptMapper, XxbBProjdept> implements IXxbBProjdeptService {


    @Override
    public IPage<XxbBProjdept> findXxbBProjdepts(QueryRequest request, XxbBProjdept xxbBProjdept) {
        try {
            LambdaQueryWrapper<XxbBProjdept> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(XxbBProjdept::getIsDeletemark, 1);//1是未删 0是已删
//        if (StringUtils.isNotBlank(xxbBProjdept.getUserAccount())) {
//        queryWrapper.and(wrap->  wrap.eq(XxbBProjdept::getUserAccount, xxbBProjdept.getUserAccount()).or()
//        .like(XxbBProjdept::getUserAccountName, xxbBProjdept.getUserAccount()));
//        }
            if (xxbBProjdept.getState() != null) {
                queryWrapper.eq(XxbBProjdept::getState, xxbBProjdept.getState());
            }


            Page<XxbBProjdept> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<XxbBProjdept> findXxbBProjdeptList(QueryRequest request, XxbBProjdept xxbBProjdept) {
        try {
            Page<XxbBProjdept> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findXxbBProjdept(page, xxbBProjdept);
        } catch (Exception e) {
            log.error("获取失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createXxbBProjdept(XxbBProjdept xxbBProjdept) {
        xxbBProjdept.setId(UUID.randomUUID().toString());
        xxbBProjdept.setCreateTime(new Date());
//        xxbBProjdept.setIsDeletemark(1);
        this.save(xxbBProjdept);
    }

    @Override
    @Transactional
    public void updateXxbBProjdept(XxbBProjdept xxbBProjdept) {
//        xxbBProjdept.setModifyTime(new Date());
        this.baseMapper.updateXxbBProjdept(xxbBProjdept);
    }

    @Override
    @Transactional
    public void deleteXxbBProjdepts(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
    }

}