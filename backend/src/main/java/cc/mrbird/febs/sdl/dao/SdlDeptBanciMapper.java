package cc.mrbird.febs.sdl.dao;

import cc.mrbird.febs.sdl.entity.SdlDeptBanci;
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
 * @since 2021-10-13
 */
public interface SdlDeptBanciMapper extends BaseMapper<SdlDeptBanci> {
        void updateSdlDeptBanci(SdlDeptBanci sdlDeptBanci);
        IPage<SdlDeptBanci> findSdlDeptBanci(Page page, @Param("sdlDeptBanci") SdlDeptBanci sdlDeptBanci);
        }
