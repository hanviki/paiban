package cc.mrbird.febs.sdl.service;

import cc.mrbird.febs.sdl.entity.SdlDHoliday;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 节假日 服务类
 * </p>
 *
 * @author viki
 * @since 2021-11-10
 */
public interface ISdlDHolidayService extends IService<SdlDHoliday> {

        IPage<SdlDHoliday> findSdlDHolidays(QueryRequest request, SdlDHoliday sdlDHoliday);

        IPage<SdlDHoliday> findSdlDHolidayList(QueryRequest request, SdlDHoliday sdlDHoliday);

        void createSdlDHoliday(SdlDHoliday sdlDHoliday);

        void updateSdlDHoliday(SdlDHoliday sdlDHoliday);

        void deleteSdlDHolidays(String[]Ids);


        }
