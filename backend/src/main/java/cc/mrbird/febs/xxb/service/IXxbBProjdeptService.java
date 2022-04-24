package cc.mrbird.febs.xxb.service;

import cc.mrbird.febs.xxb.entity.XxbBProjdept;
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
 * @since 2022-04-20
 */
public interface IXxbBProjdeptService extends IService<XxbBProjdept> {

        IPage<XxbBProjdept> findXxbBProjdepts(QueryRequest request, XxbBProjdept xxbBProjdept);

        IPage<XxbBProjdept> findXxbBProjdeptList(QueryRequest request, XxbBProjdept xxbBProjdept);

        void createXxbBProjdept(XxbBProjdept xxbBProjdept);

        void updateXxbBProjdept(XxbBProjdept xxbBProjdept);

        void deleteXxbBProjdepts(String[]Ids);


        }
