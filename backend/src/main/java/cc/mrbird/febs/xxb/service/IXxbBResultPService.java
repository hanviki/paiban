package cc.mrbird.febs.xxb.service;

import cc.mrbird.febs.xxb.entity.XxbBResultP;
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
 * @since 2022-05-27
 */
public interface IXxbBResultPService extends IService<XxbBResultP> {

        IPage<XxbBResultP> findXxbBResultPs(QueryRequest request, XxbBResultP xxbBResultP);

        IPage<XxbBResultP> findXxbBResultPList(QueryRequest request, XxbBResultP xxbBResultP);

        void createXxbBResultP(XxbBResultP xxbBResultP);

        void updateXxbBResultP(XxbBResultP xxbBResultP);

        void deleteXxbBResultPs(String[]Ids);


        }
