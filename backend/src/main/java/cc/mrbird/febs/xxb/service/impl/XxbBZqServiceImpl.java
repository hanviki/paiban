package cc.mrbird.febs.xxb.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.xxb.entity.XxbBZq;
import cc.mrbird.febs.xxb.dao.XxbBZqMapper;
import cc.mrbird.febs.xxb.service.IXxbBZqService;
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
 * 中期反馈表 服务实现类
 * </p>
 *
 * @author viki
 * @since 2022-04-26
 */
@Slf4j
@Service("IXxbBZqService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class XxbBZqServiceImpl extends ServiceImpl<XxbBZqMapper, XxbBZq> implements IXxbBZqService {


    @Override
    public IPage<XxbBZq> findXxbBZqs(QueryRequest request, XxbBZq xxbBZq) {
        try {
            LambdaQueryWrapper<XxbBZq> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(XxbBZq::getIsDeletemark, 1);//1是未删 0是已删

            if (xxbBZq.getState() != null) {
                queryWrapper.eq(XxbBZq::getState, xxbBZq.getState());
            }
            if (StringUtils.isNotEmpty(xxbBZq.getBaseId())) {
                queryWrapper.eq(XxbBZq::getBaseId, xxbBZq.getBaseId());
            }

            Page<XxbBZq> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<XxbBZq> findXxbBZqList(QueryRequest request, XxbBZq xxbBZq) {
        try {
            Page<XxbBZq> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findXxbBZq(page, xxbBZq);
        } catch (Exception e) {
            log.error("获取中期反馈表失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createXxbBZq(XxbBZq xxbBZq) {
        xxbBZq.setId(UUID.randomUUID().toString());
        xxbBZq.setCreateTime(new Date());
        xxbBZq.setIsDeletemark(1);
        this.save(xxbBZq);
    }

    @Override
    @Transactional
    public void updateXxbBZq(XxbBZq xxbBZq) {
        xxbBZq.setModifyTime(new Date());
        this.baseMapper.updateXxbBZq(xxbBZq);
    }

    @Override
    @Transactional
    public void deleteXxbBZqs(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
    }

}