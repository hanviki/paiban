package cc.mrbird.febs.sdl.dao;

import cc.mrbird.febs.sdl.entity.SdlDZizhi;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 资质字典表 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2021-10-13
 */
public interface SdlDZizhiMapper extends BaseMapper<SdlDZizhi> {
        void updateSdlDZizhi(SdlDZizhi sdlDZizhi);
        IPage<SdlDZizhi> findSdlDZizhi(Page page, @Param("sdlDZizhi") SdlDZizhi sdlDZizhi);
        }
