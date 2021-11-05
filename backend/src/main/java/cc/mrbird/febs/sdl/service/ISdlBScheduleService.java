package cc.mrbird.febs.sdl.service;

import cc.mrbird.febs.sdl.entity.SdlBSchedule;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 排班主表 服务类
 * </p>
 *
 * @author viki
 * @since 2021-10-13
 */
public interface ISdlBScheduleService extends IService<SdlBSchedule> {

        IPage<SdlBSchedule> findSdlBSchedules(QueryRequest request, SdlBSchedule sdlBSchedule);

        IPage<SdlBSchedule> findSdlBScheduleList(QueryRequest request, SdlBSchedule sdlBSchedule);
        IPage<SdlBSchedule> findSdlBScheduleList2(QueryRequest request, SdlBSchedule sdlBSchedule);

        void createSdlBSchedule(SdlBSchedule sdlBSchedule);

        void updateSdlBSchedule(SdlBSchedule sdlBSchedule);

        void deleteSdlBSchedules(String[]Ids);

        void updateStateById(String id,int state);


        }
