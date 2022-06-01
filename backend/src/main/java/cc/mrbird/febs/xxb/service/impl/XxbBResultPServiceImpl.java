package cc.mrbird.febs.xxb.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.xxb.entity.XxbBResultP;
import cc.mrbird.febs.xxb.dao.XxbBResultPMapper;
import cc.mrbird.febs.xxb.service.IXxbBResultPService;
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
 * @since 2022-05-27
 */
@Slf4j
@Service("IXxbBResultPService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class XxbBResultPServiceImpl extends ServiceImpl<XxbBResultPMapper, XxbBResultP> implements IXxbBResultPService {


    @Override
    public IPage<XxbBResultP> findXxbBResultPs(QueryRequest request, XxbBResultP xxbBResultP) {
        try {
            LambdaQueryWrapper<XxbBResultP> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(XxbBResultP::getIsDeletemark, 1);//1是未删 0是已删

            if (xxbBResultP.getState() != null) {
                queryWrapper.eq(XxbBResultP::getState, xxbBResultP.getState());
            }


            Page<XxbBResultP> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<XxbBResultP> findXxbBResultPList(QueryRequest request, XxbBResultP xxbBResultP) {
        try {
            Page<XxbBResultP> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findXxbBResultP(page, xxbBResultP);
        } catch (Exception e) {
            log.error("获取失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createXxbBResultP(XxbBResultP xxbBResultP) {
        xxbBResultP.setId(UUID.randomUUID().toString());
        xxbBResultP.setCreateTime(new Date());
        xxbBResultP.setIsDeletemark(1);
        this.save(xxbBResultP);
    }

    @Override
    @Transactional
    public void updateXxbBResultP(XxbBResultP xxbBResultP) {
//        xxbBResultP.setModifyTime(new Date());
        this.baseMapper.updateXxbBResultP(xxbBResultP);
    }

    @Override
    @Transactional
    public void deleteXxbBResultPs(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
    }

}