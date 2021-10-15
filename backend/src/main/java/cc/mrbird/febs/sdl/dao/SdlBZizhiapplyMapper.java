package cc.mrbird.febs.sdl.dao;

import cc.mrbird.febs.sdl.entity.SdlBZizhiapply;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 资质变更申请 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2021-10-13
 */
public interface SdlBZizhiapplyMapper extends BaseMapper<SdlBZizhiapply> {
        void updateSdlBZizhiapply(SdlBZizhiapply sdlBZizhiapply);
        IPage<SdlBZizhiapply> findSdlBZizhiapply(Page page, @Param("sdlBZizhiapply") SdlBZizhiapply sdlBZizhiapply);
        }
