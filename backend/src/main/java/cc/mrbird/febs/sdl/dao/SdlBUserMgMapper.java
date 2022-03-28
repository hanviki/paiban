package cc.mrbird.febs.sdl.dao;

import cc.mrbird.febs.rfc.CustomUser;
import cc.mrbird.febs.sdl.entity.CustomDept;
import cc.mrbird.febs.sdl.entity.SdlBUserMg;
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
public interface SdlBUserMgMapper extends BaseMapper<SdlBUserMg> {
    void updateSdlBUserMg(SdlBUserMg sdlBUser);
    IPage<SdlBUserMg> findSdlBUserMg(Page page, @Param("sdlBUser") SdlBUserMg sdlBUser);

    @Select("select id,user_account from sdl_b_user_mg")
    List<CustomUser> getUserAccounts();

    List<CustomDept> getDeptNew();

    List<cc.mrbird.febs.sdl.entity.CustomUser> findSdlBUserMgByDeptId(@Param("typeList") List<String> typeList);
}
