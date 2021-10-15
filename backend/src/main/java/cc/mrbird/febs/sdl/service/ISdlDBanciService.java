package cc.mrbird.febs.sdl.service;

import cc.mrbird.febs.sdl.entity.SdlDBanci;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 系列名称 服务类
 * </p>
 *
 * @author viki
 * @since 2021-10-13
 */
public interface ISdlDBanciService extends IService<SdlDBanci> {

        IPage<SdlDBanci> findSdlDBancis(QueryRequest request, SdlDBanci sdlDBanci);

        IPage<SdlDBanci> findSdlDBanciList(QueryRequest request, SdlDBanci sdlDBanci);

        void createSdlDBanci(SdlDBanci sdlDBanci);

        void updateSdlDBanci(SdlDBanci sdlDBanci);

        void deleteSdlDBancis(String[]Ids);


        }
