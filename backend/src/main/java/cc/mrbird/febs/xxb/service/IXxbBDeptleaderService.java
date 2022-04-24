package cc.mrbird.febs.xxb.service;

import cc.mrbird.febs.xxb.entity.XxbBDeptleader;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 部门负责人 服务类
 * </p>
 *
 * @author viki
 * @since 2022-04-20
 */
public interface IXxbBDeptleaderService extends IService<XxbBDeptleader> {

        IPage<XxbBDeptleader> findXxbBDeptleaders(QueryRequest request, XxbBDeptleader xxbBDeptleader);

        IPage<XxbBDeptleader> findXxbBDeptleaderList(QueryRequest request, XxbBDeptleader xxbBDeptleader);

        void createXxbBDeptleader(XxbBDeptleader xxbBDeptleader);

        void updateXxbBDeptleader(XxbBDeptleader xxbBDeptleader);

        void deleteXxbBDeptleaders(String[]Ids);


        }
