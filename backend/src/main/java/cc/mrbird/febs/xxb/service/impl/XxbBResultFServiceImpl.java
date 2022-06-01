package cc.mrbird.febs.xxb.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.xxb.entity.XxbBResultF;
import cc.mrbird.febs.xxb.dao.XxbBResultFMapper;
import cc.mrbird.febs.xxb.service.IXxbBResultFService;
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
 * @since 2022-05-23
 */
@Slf4j
@Service("IXxbBResultFService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class XxbBResultFServiceImpl extends ServiceImpl<XxbBResultFMapper, XxbBResultF> implements IXxbBResultFService {


    @Override
    public IPage<XxbBResultF> findXxbBResultFs(QueryRequest request, XxbBResultF xxbBResultF) {
        try {
            LambdaQueryWrapper<XxbBResultF> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(XxbBResultF::getIsDeletemark, 1);//1是未删 0是已删
//        if (StringUtils.isNotBlank(xxbBResultF.getUserAccount())) {
//        queryWrapper.and(wrap->  wrap.eq(XxbBResultF::getUserAccount, xxbBResultF.getUserAccount()).or()
//        .like(XxbBResultF::getUserAccountName, xxbBResultF.getUserAccount()));
//        }
            if (xxbBResultF.getState() != null) {
                queryWrapper.eq(XxbBResultF::getState, xxbBResultF.getState());
            }


            Page<XxbBResultF> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<XxbBResultF> findXxbBResultFList(QueryRequest request, XxbBResultF xxbBResultF) {
        try {
            Page<XxbBResultF> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findXxbBResultF(page, xxbBResultF);
        } catch (Exception e) {
            log.error("获取失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createXxbBResultF(XxbBResultF xxbBResultF) {
        xxbBResultF.setId(UUID.randomUUID().toString());
        xxbBResultF.setCreateTime(new Date());
        xxbBResultF.setIsDeletemark(1);
        this.save(xxbBResultF);
    }

    @Override
    @Transactional
    public void updateXxbBResultF(XxbBResultF xxbBResultF) {
//        xxbBResultF.setModifyTime(new Date());
        this.baseMapper.updateXxbBResultF(xxbBResultF);
    }

    @Override
    @Transactional
    public void deleteXxbBResultFs(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
    }

}