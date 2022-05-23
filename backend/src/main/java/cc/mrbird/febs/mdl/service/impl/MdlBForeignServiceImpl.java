package cc.mrbird.febs.mdl.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.mdl.entity.MdlBForeign;
import cc.mrbird.febs.mdl.dao.MdlBForeignMapper;
import cc.mrbird.febs.mdl.entity.MdlBForeignD;
import cc.mrbird.febs.mdl.service.IMdlBForeignDService;
import cc.mrbird.febs.mdl.service.IMdlBForeignService;
import cc.mrbird.febs.system.domain.User;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sap.conn.rfc.engine.GUID;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.time.LocalDate;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author viki
 * @since 2022-05-16
 */
@Slf4j
@Service("IMdlBForeignService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MdlBForeignServiceImpl extends ServiceImpl<MdlBForeignMapper, MdlBForeign> implements IMdlBForeignService {

    @Autowired
    private IMdlBForeignDService iMdlBForeignDService;

    @Override
    public IPage<MdlBForeign> findMdlBForeigns(QueryRequest request, MdlBForeign mdlBForeign) {
        try {
            LambdaQueryWrapper<MdlBForeign> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(MdlBForeign::getIsDeletemark, 1);//1是未删 0是已删

            if (mdlBForeign.getState() != null) {
                queryWrapper.eq(MdlBForeign::getState, mdlBForeign.getState());
            }

            if (StringUtils.isNotBlank(mdlBForeign.getTaskSource())) {
                queryWrapper.like(MdlBForeign::getTaskSource, mdlBForeign.getTaskSource());
            }
            if (StringUtils.isNotBlank(mdlBForeign.getTaskType())) {
                queryWrapper.like(MdlBForeign::getTaskType, mdlBForeign.getTaskType());
            }
            if (StringUtils.isNotBlank(mdlBForeign.getCity())) {
                queryWrapper.apply(" id in (select base_id from mdl_b_foreign_d where user_account_name like '%"+mdlBForeign.getCity()+"%' or user_account= '"+mdlBForeign.getCity()+"')");
            }

            Page<MdlBForeign> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<MdlBForeign> findMdlBForeignList(QueryRequest request, MdlBForeign mdlBForeign) {
        try {
            Page<MdlBForeign> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findMdlBForeign(page, mdlBForeign);
        } catch (Exception e) {
            log.error("获取失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createMdlBForeign(MdlBForeign mdlBForeign) {
        mdlBForeign.setId(UUID.randomUUID().toString());
        mdlBForeign.setCreateTime(new Date());
        mdlBForeign.setIsDeletemark(1);
        this.save(mdlBForeign);
    }

    @Override
    @Transactional
    public void updateMdlBForeign(MdlBForeign mdlBForeign) {
        mdlBForeign.setModifyTime(new Date());
        this.baseMapper.updateMdlBForeign(mdlBForeign);
    }

    @Override
    @Transactional
    public void deleteMdlBForeigns(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
    }

    @Override
    @Transactional
    public String editXxbBCheck(MdlBForeign xxbBCheckInfo, User user) {
        // srtdat、enddat、ycdat、shstate、xmjdstate、STATE 未赋值
        List<MdlBForeignD> insertData = new ArrayList<>();
        List<MdlBForeignD> updateData = new ArrayList<>();


        int nTime = 0;
        // 新增 修改 项目主要人员
        for (MdlBForeignD item : xxbBCheckInfo.getCheckDataList()) {
            MdlBForeignD data = new MdlBForeignD();
            // user_account_name、user_account、yggh、sex_name、birthday、age、edu、zhicw、zhuanyfm、userdept
            data.setUserAccountName(item.getUserAccountName());
            data.setUserAccount(item.getUserAccount());

            data.setSexName(item.getSexName());
            data.setBirthday(item.getBirthday());

            data.setTelephone(item.getTelephone());

            if (item.getId() == null) {
                data.setIsDeletemark(1);
                data.setBaseId(xxbBCheckInfo.getId());
                data.setId(UUID.randomUUID().toString());
                data.setCreateTime(new Date());
                insertData.add(data);
            } else {
                data.setId(item.getId());
                data.setBaseId(xxbBCheckInfo.getId());
                data.setModifyTime(new Date());
                updateData.add(data);
            }
        }
        if (insertData.size() > 0) {
            iMdlBForeignDService.saveBatch(insertData);
        }
        if (updateData.size() > 0) {
            iMdlBForeignDService.updateBatchById(updateData);
        }
        this.saveOrUpdate(xxbBCheckInfo);
        return xxbBCheckInfo.getId().toString();
    }

}