package cc.mrbird.febs.xxb.service;

import cc.mrbird.febs.xxb.entity.XxbBMq;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 末期反馈表 服务类
 * </p>
 *
 * @author viki
 * @since 2022-04-27
 */
public interface IXxbBMqService extends IService<XxbBMq> {

        IPage<XxbBMq> findXxbBMqs(QueryRequest request, XxbBMq xxbBMq);

        IPage<XxbBMq> findXxbBMqList(QueryRequest request, XxbBMq xxbBMq);

        void createXxbBMq(XxbBMq xxbBMq);

        void updateXxbBMq(XxbBMq xxbBMq);

        void deleteXxbBMqs(String[]Ids);


        }
