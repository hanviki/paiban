package cc.mrbird.febs.sdl.service;

import cc.mrbird.febs.sdl.entity.SdlDZizhi;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 资质字典表 服务类
 * </p>
 *
 * @author viki
 * @since 2021-10-13
 */
public interface ISdlDZizhiService extends IService<SdlDZizhi> {

        IPage<SdlDZizhi> findSdlDZizhis(QueryRequest request, SdlDZizhi sdlDZizhi);

        IPage<SdlDZizhi> findSdlDZizhiList(QueryRequest request, SdlDZizhi sdlDZizhi);

        void createSdlDZizhi(SdlDZizhi sdlDZizhi);

        void updateSdlDZizhi(SdlDZizhi sdlDZizhi);

        void deleteSdlDZizhis(String[]Ids);


        }
