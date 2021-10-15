package cc.mrbird.febs.sdl.dao;

import cc.mrbird.febs.sdl.entity.SdlBSchedule;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 排班主表 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2021-10-13
 */
public interface SdlBScheduleMapper extends BaseMapper<SdlBSchedule> {
        void updateSdlBSchedule(SdlBSchedule sdlBSchedule);
        IPage<SdlBSchedule> findSdlBSchedule(Page page, @Param("sdlBSchedule") SdlBSchedule sdlBSchedule);
        }
