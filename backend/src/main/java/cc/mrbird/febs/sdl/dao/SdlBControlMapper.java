package cc.mrbird.febs.sdl.dao;

import cc.mrbird.febs.sdl.entity.SdlBControl;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2021-11-15
 */
public interface SdlBControlMapper extends BaseMapper<SdlBControl> {
        void updateSdlBControl(SdlBControl sdlBControl);
        IPage<SdlBControl> findSdlBControl(Page page, @Param("sdlBControl") SdlBControl sdlBControl);
        }
