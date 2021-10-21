package cc.mrbird.febs.sdl.dao;

import cc.mrbird.febs.sdl.entity.SdlBUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2021-10-13
 */
public interface SdlBUserMapper extends BaseMapper<SdlBUser> {
        void updateSdlBUser(SdlBUser sdlBUser);
        IPage<SdlBUser> findSdlBUser(Page page, @Param("sdlBUser") SdlBUser sdlBUser);

        @Select("select user_account from sdl_b_user")
        List<String> getUserAccounts();
        }
