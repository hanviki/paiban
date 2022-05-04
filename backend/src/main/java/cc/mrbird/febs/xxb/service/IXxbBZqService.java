package cc.mrbird.febs.xxb.service;

import cc.mrbird.febs.xxb.entity.XxbBZq;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 中期反馈表 服务类
 * </p>
 *
 * @author viki
 * @since 2022-04-26
 */
public interface IXxbBZqService extends IService<XxbBZq> {

        IPage<XxbBZq> findXxbBZqs(QueryRequest request, XxbBZq xxbBZq);

        IPage<XxbBZq> findXxbBZqList(QueryRequest request, XxbBZq xxbBZq);

        void createXxbBZq(XxbBZq xxbBZq);

        void updateXxbBZq(XxbBZq xxbBZq);

        void deleteXxbBZqs(String[]Ids);


        }
