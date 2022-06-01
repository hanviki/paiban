package cc.mrbird.febs.xxb.service;

import cc.mrbird.febs.xxb.entity.XxbBResultF;
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
 * @since 2022-05-23
 */
public interface IXxbBResultFService extends IService<XxbBResultF> {

        IPage<XxbBResultF> findXxbBResultFs(QueryRequest request, XxbBResultF xxbBResultF);

        IPage<XxbBResultF> findXxbBResultFList(QueryRequest request, XxbBResultF xxbBResultF);

        void createXxbBResultF(XxbBResultF xxbBResultF);

        void updateXxbBResultF(XxbBResultF xxbBResultF);

        void deleteXxbBResultFs(String[]Ids);


        }
