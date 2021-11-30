package cc.mrbird.febs.sdl.service;

import cc.mrbird.febs.sdl.entity.SdlBControl;
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
 * @since 2021-11-15
 */
public interface ISdlBControlService extends IService<SdlBControl> {

        IPage<SdlBControl> findSdlBControls(QueryRequest request, SdlBControl sdlBControl);

        IPage<SdlBControl> findSdlBControlList(QueryRequest request, SdlBControl sdlBControl);

        void createSdlBControl(SdlBControl sdlBControl);

        void updateSdlBControl(SdlBControl sdlBControl);

        void deleteSdlBControls(String[]Ids);


        }
