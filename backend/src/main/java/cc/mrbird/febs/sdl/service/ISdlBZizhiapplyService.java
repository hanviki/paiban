package cc.mrbird.febs.sdl.service;

import cc.mrbird.febs.sdl.entity.SdlBZizhiapply;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 资质变更申请 服务类
 * </p>
 *
 * @author viki
 * @since 2021-10-13
 */
public interface ISdlBZizhiapplyService extends IService<SdlBZizhiapply> {

        IPage<SdlBZizhiapply> findSdlBZizhiapplys(QueryRequest request, SdlBZizhiapply sdlBZizhiapply);

        IPage<SdlBZizhiapply> findSdlBZizhiapplyList(QueryRequest request, SdlBZizhiapply sdlBZizhiapply);

        void createSdlBZizhiapply(SdlBZizhiapply sdlBZizhiapply);

        void updateSdlBZizhiapply(SdlBZizhiapply sdlBZizhiapply);

        void deleteSdlBZizhiapplys(String[]Ids);


        }
