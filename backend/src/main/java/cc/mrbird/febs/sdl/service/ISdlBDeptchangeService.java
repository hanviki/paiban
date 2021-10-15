package cc.mrbird.febs.sdl.service;

import cc.mrbird.febs.sdl.entity.SdlBDeptchange;
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
 * @since 2021-10-14
 */
public interface ISdlBDeptchangeService extends IService<SdlBDeptchange> {

        IPage<SdlBDeptchange> findSdlBDeptchanges(QueryRequest request, SdlBDeptchange sdlBDeptchange);

        IPage<SdlBDeptchange> findSdlBDeptchangeList(QueryRequest request, SdlBDeptchange sdlBDeptchange);

        void createSdlBDeptchange(SdlBDeptchange sdlBDeptchange);

        void updateSdlBDeptchange(SdlBDeptchange sdlBDeptchange);

        void deleteSdlBDeptchanges(String[]Ids);


        }
