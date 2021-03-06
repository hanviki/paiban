package cc.mrbird.febs.sdl.dao;

import cc.mrbird.febs.sdl.entity.SdlDeptZizhi;
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
public interface SdlDeptZizhiMapper extends BaseMapper<SdlDeptZizhi> {
        void updateSdlDeptZizhi(SdlDeptZizhi sdlDeptZizhi);
        IPage<SdlDeptZizhi> findSdlDeptZizhi(Page page, @Param("sdlDeptZizhi") SdlDeptZizhi sdlDeptZizhi);
        }
