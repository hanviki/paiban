package cc.mrbird.febs.xxb.service;

import cc.mrbird.febs.xxb.entity.XxbBResultD;
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
public interface IXxbBResultDService extends IService<XxbBResultD> {

        IPage<XxbBResultD> findXxbBResultDs(QueryRequest request, XxbBResultD xxbBResultD);

        IPage<XxbBResultD> findXxbBResultDList(QueryRequest request, XxbBResultD xxbBResultD);

        void createXxbBResultD(XxbBResultD xxbBResultD);

        void updateXxbBResultD(XxbBResultD xxbBResultD);

        void deleteXxbBResultDs(String[]Ids);


        }
