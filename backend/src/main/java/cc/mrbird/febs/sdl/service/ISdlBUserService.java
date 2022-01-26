package cc.mrbird.febs.sdl.service;

import cc.mrbird.febs.rfc.CustomUser;
import cc.mrbird.febs.sdl.entity.SdlBUser;
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
public interface ISdlBUserService extends IService<SdlBUser> {

        IPage<SdlBUser> findSdlBUsers(QueryRequest request, SdlBUser sdlBUser);

        IPage<SdlBUser> findSdlBUsersYwc(QueryRequest request, SdlBUser sdlBUser);

        IPage<SdlBUser> findSdlBUsersHz(QueryRequest request, SdlBUser sdlBUser);

        IPage<SdlBUser> findSdlBUserList(QueryRequest request, SdlBUser sdlBUser);

        void createSdlBUser(SdlBUser sdlBUser);

        void updateSdlBUser(SdlBUser sdlBUser);

        void deleteSdlBUsers(String[]Ids);

        List<CustomUser> getUserAccounts();

        List<SdlBUser> findSdlBUsers_search( SdlBUser sdlBUser);

        }
