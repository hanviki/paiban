package cc.mrbird.febs.sdl.dao;

import cc.mrbird.febs.sdl.entity.SdlDBanci;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 系列名称 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2021-10-13
 */
public interface SdlDBanciMapper extends BaseMapper<SdlDBanci> {
        void updateSdlDBanci(SdlDBanci sdlDBanci);
        IPage<SdlDBanci> findSdlDBanci(Page page, @Param("sdlDBanci") SdlDBanci sdlDBanci);
        }
