package cc.mrbird.febs.sdl.service;

import cc.mrbird.febs.sdl.entity.SdlDeptZizhi;
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
public interface ISdlDeptZizhiService extends IService<SdlDeptZizhi> {

        IPage<SdlDeptZizhi> findSdlDeptZizhis(QueryRequest request, SdlDeptZizhi sdlDeptZizhi);

        IPage<SdlDeptZizhi> findSdlDeptZizhiList(QueryRequest request, SdlDeptZizhi sdlDeptZizhi);

        void createSdlDeptZizhi(SdlDeptZizhi sdlDeptZizhi);

        void updateSdlDeptZizhi(SdlDeptZizhi sdlDeptZizhi);

        void deleteSdlDeptZizhis(String[]Ids);


        }
