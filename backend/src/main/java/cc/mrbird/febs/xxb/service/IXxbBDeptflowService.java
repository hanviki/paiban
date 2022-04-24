package cc.mrbird.febs.xxb.service;

import cc.mrbird.febs.xxb.entity.XxbBDeptflow;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author viki
 * @since 2022-04-08
 */
public interface IXxbBDeptflowService extends IService<XxbBDeptflow> {

        IPage<XxbBDeptflow> findXxbBDeptflows(QueryRequest request, XxbBDeptflow xxbBDeptflow);

        IPage<XxbBDeptflow> findXxbBDeptflowList(QueryRequest request, XxbBDeptflow xxbBDeptflow);

        void createXxbBDeptflow(XxbBDeptflow xxbBDeptflow);

        void updateXxbBDeptflow(XxbBDeptflow xxbBDeptflow);

        void deleteXxbBDeptflows(String[]Ids);


        }
