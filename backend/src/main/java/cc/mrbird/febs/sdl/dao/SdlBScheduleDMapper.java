package cc.mrbird.febs.sdl.dao;

import cc.mrbird.febs.sdl.entity.SdlBScheduleD;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 排班子表 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2021-10-13
 */
public interface SdlBScheduleDMapper extends BaseMapper<SdlBScheduleD> {
        void updateSdlBScheduleD(SdlBScheduleD sdlBScheduleD);
        IPage<SdlBScheduleD> findSdlBScheduleD(Page page, @Param("sdlBScheduleD") SdlBScheduleD sdlBScheduleD);
        }
