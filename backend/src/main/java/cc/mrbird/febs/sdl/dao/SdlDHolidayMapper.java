package cc.mrbird.febs.sdl.dao;

import cc.mrbird.febs.sdl.entity.SdlDHoliday;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 节假日 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2021-11-10
 */
public interface SdlDHolidayMapper extends BaseMapper<SdlDHoliday> {
        void updateSdlDHoliday(SdlDHoliday sdlDHoliday);
        IPage<SdlDHoliday> findSdlDHoliday(Page page, @Param("sdlDHoliday") SdlDHoliday sdlDHoliday);
        }
