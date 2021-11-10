package cc.mrbird.febs.sdl.service;

import cc.mrbird.febs.sdl.entity.SdlDBanci;
import cc.mrbird.febs.sdl.entity.SdlDeptBanci;
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
 * @since 2021-10-13
 */
public interface ISdlDeptBanciService extends IService<SdlDeptBanci> {

        IPage<SdlDeptBanci> findSdlDeptBancis(QueryRequest request, SdlDeptBanci sdlDeptBanci);

        IPage<SdlDeptBanci> findSdlDeptBanciList(QueryRequest request, SdlDeptBanci sdlDeptBanci);

        void createSdlDeptBanci(SdlDeptBanci sdlDeptBanci);

        void updateSdlDeptBanci(SdlDeptBanci sdlDeptBanci);

        void deleteSdlDeptBancis(String[]Ids);

        List<SdlDBanci> findBanci(SdlDeptBanci sdlDeptBanci);


        }
