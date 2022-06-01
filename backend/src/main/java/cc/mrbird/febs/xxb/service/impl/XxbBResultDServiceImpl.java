package cc.mrbird.febs.xxb.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.xxb.entity.XxbBResultD;
import cc.mrbird.febs.xxb.dao.XxbBResultDMapper;
import cc.mrbird.febs.xxb.service.IXxbBResultDService;
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
@Service("IXxbBResultDService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class XxbBResultDServiceImpl extends ServiceImpl<XxbBResultDMapper, XxbBResultD> implements IXxbBResultDService {


    @Override
    public IPage<XxbBResultD> findXxbBResultDs(QueryRequest request, XxbBResultD xxbBResultD) {
        try {
            LambdaQueryWrapper<XxbBResultD> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(XxbBResultD::getIsDeletemark, 1);//1是未删 0是已删
            if (StringUtils.isNotBlank(xxbBResultD.getUserAccount())) {
                queryWrapper.and(wrap -> wrap.eq(XxbBResultD::getUserAccount, xxbBResultD.getUserAccount()).or()
                        .like(XxbBResultD::getUserAccountName, xxbBResultD.getUserAccount()));

            }
//        if (xxbBResultD.getState()!=null) {
//        queryWrapper.eq(XxbBResultD::getState, xxbBResultD.getState());
//        }

            if (StringUtils.isNotBlank(xxbBResultD.getUserAccount())) {
                queryWrapper.like(XxbBResultD::getUserAccount, xxbBResultD.getUserAccount());
            }

            Page<XxbBResultD> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<XxbBResultD> findXxbBResultDList(QueryRequest request, XxbBResultD xxbBResultD) {
        try {
            Page<XxbBResultD> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findXxbBResultD(page, xxbBResultD);
        } catch (Exception e) {
            log.error("获取失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createXxbBResultD(XxbBResultD xxbBResultD) {
        xxbBResultD.setId(UUID.randomUUID().toString());
        xxbBResultD.setCreateTime(new Date());
        xxbBResultD.setIsDeletemark(1);
        this.save(xxbBResultD);
    }

    @Override
    @Transactional
    public void updateXxbBResultD(XxbBResultD xxbBResultD) {
//        xxbBResultD.setModifyTime(new Date());
        this.baseMapper.updateXxbBResultD(xxbBResultD);
    }

    @Override
    @Transactional
    public void deleteXxbBResultDs(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
    }

}