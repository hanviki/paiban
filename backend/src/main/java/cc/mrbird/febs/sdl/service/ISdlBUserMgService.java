package cc.mrbird.febs.sdl.service;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.rfc.CustomUser;
import cc.mrbird.febs.sdl.entity.CustomDept;
import cc.mrbird.febs.sdl.entity.SdlBUserMg;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author viki
 * @since 2021-10-13
 */
public interface ISdlBUserMgService extends IService<SdlBUserMg> {

        IPage<SdlBUserMg> findSdlBUserMgs(QueryRequest request, SdlBUserMg sdlBUser);
        List<cc.mrbird.febs.sdl.entity.CustomUser> findSdlBUserMgs(SdlBUserMg sdlBUser);

        IPage<SdlBUserMg> findSdlBUserMgsYwc(QueryRequest request, SdlBUserMg sdlBUser);

        IPage<SdlBUserMg> findSdlBUserMgsYwc2(QueryRequest request, SdlBUserMg sdlBUser);

        IPage<SdlBUserMg> findSdlBUserMgsHz(QueryRequest request, SdlBUserMg sdlBUser);

        SdlBUserMg findObjByAccount(String account);

        IPage<SdlBUserMg> findSdlBUserMgList(QueryRequest request, SdlBUserMg sdlBUser);

        void createSdlBUserMg(SdlBUserMg sdlBUser);

        void updateSdlBUserMg(SdlBUserMg sdlBUser);

        void deleteSdlBUserMgs(String[] Ids);

        List<CustomUser> getUserAccounts();

        List<SdlBUserMg> findSdlBUserMgs_search(SdlBUserMg sdlBUser);

        List<CustomDept> getDeptNew();

        }
