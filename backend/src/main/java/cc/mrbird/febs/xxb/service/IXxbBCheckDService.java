package cc.mrbird.febs.xxb.service;

import cc.mrbird.febs.xxb.entity.XxbBCheckD;
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
 * @since 2022-03-29
 */
public interface IXxbBCheckDService extends IService<XxbBCheckD> {

        IPage<XxbBCheckD> findXxbBCheckDs(QueryRequest request, XxbBCheckD xxbBCheckD);

        IPage<XxbBCheckD> findXxbBCheckDList(QueryRequest request, XxbBCheckD xxbBCheckD);

        void createXxbBCheckD(XxbBCheckD xxbBCheckD);

        void updateXxbBCheckD(XxbBCheckD xxbBCheckD);

        void deleteXxbBCheckDs(String[]Ids);


        }
