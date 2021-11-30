package cc.mrbird.febs.sdl.dao;

import cc.mrbird.febs.sdl.entity.SdlDWeekendday;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 周六日表 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2021-11-11
 */
public interface SdlDWeekenddayMapper extends BaseMapper<SdlDWeekendday> {
        void updateSdlDWeekendday(SdlDWeekendday sdlDWeekendday);
        IPage<SdlDWeekendday> findSdlDWeekendday(Page page, @Param("sdlDWeekendday") SdlDWeekendday sdlDWeekendday);

        }
