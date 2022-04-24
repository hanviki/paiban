package cc.mrbird.febs.xxb.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.xxb.entity.XxbBCheckD;
import cc.mrbird.febs.xxb.dao.XxbBCheckDMapper;
import cc.mrbird.febs.xxb.service.IXxbBCheckDService;
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
 * @since 2022-03-29
 */
@Slf4j
@Service("IXxbBCheckDService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class XxbBCheckDServiceImpl extends ServiceImpl<XxbBCheckDMapper, XxbBCheckD> implements IXxbBCheckDService {


    @Override
    public IPage<XxbBCheckD> findXxbBCheckDs(QueryRequest request, XxbBCheckD xxbBCheckD) {
        try {
            LambdaQueryWrapper<XxbBCheckD> queryWrapper = new LambdaQueryWrapper<>();
//            queryWrapper.eq(XxbBCheckD::getIsDeletemark, 1);//1是未删 0是已删
//            if (StringUtils.isNotBlank(xxbBCheckD.getUserAccount())) {
//                queryWrapper.and(wrap -> wrap.eq(XxbBCheckD::getUserAccount, xxbBCheckD.getUserAccount()).or()
//                        .like(XxbBCheckD::getUserAccountName, xxbBCheckD.getUserAccount()));
//
//            }
//            if (xxbBCheckD.getState() != null) {
//                queryWrapper.eq(XxbBCheckD::getState, xxbBCheckD.getState());
//            }
            String sql = "1=2";
            if (StringUtils.isNotBlank(xxbBCheckD.getPid())) {
                sql = "pid = '" + xxbBCheckD.getPid() + "'";
            }

            queryWrapper.apply(sql);

            Page<XxbBCheckD> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<XxbBCheckD> findXxbBCheckDList(QueryRequest request, XxbBCheckD xxbBCheckD) {
        try {
            Page<XxbBCheckD> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findXxbBCheckD(page, xxbBCheckD);
        } catch (Exception e) {
            log.error("获取失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createXxbBCheckD(XxbBCheckD xxbBCheckD) {
        xxbBCheckD.setId(UUID.randomUUID().toString());
//        xxbBCheckD.setCreateTime(new Date());
//        xxbBCheckD.setIsDeletemark(1);
        this.save(xxbBCheckD);
    }

    @Override
    @Transactional
    public void updateXxbBCheckD(XxbBCheckD xxbBCheckD) {
//        xxbBCheckD.setModifyTime(new Date());
        this.baseMapper.updateXxbBCheckD(xxbBCheckD);
    }

    @Override
    @Transactional
    public void deleteXxbBCheckDs(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
    }

}