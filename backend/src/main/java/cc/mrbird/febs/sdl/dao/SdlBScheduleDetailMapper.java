package cc.mrbird.febs.sdl.dao;

import cc.mrbird.febs.sdl.entity.SdlBScheduleDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 排班子表明细 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2021-10-13
 */
public interface SdlBScheduleDetailMapper extends BaseMapper<SdlBScheduleDetail> {
        void updateSdlBScheduleDetail(SdlBScheduleDetail sdlBScheduleDetail);
        IPage<SdlBScheduleDetail> findSdlBScheduleDetail(Page page, @Param("sdlBScheduleDetail") SdlBScheduleDetail sdlBScheduleDetail);
        }
