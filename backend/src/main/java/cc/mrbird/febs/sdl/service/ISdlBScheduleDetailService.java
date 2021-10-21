package cc.mrbird.febs.sdl.service;

import cc.mrbird.febs.sdl.entity.SdlBScheduleDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 排班子表明细 服务类
 * </p>
 *
 * @author viki
 * @since 2021-10-13
 */
public interface ISdlBScheduleDetailService extends IService<SdlBScheduleDetail> {

        IPage<SdlBScheduleDetail> findSdlBScheduleDetails(QueryRequest request, SdlBScheduleDetail sdlBScheduleDetail);

        IPage<SdlBScheduleDetail> findSdlBScheduleDetailList(QueryRequest request, SdlBScheduleDetail sdlBScheduleDetail);

        void createSdlBScheduleDetail(SdlBScheduleDetail sdlBScheduleDetail);

        void updateSdlBScheduleDetail(SdlBScheduleDetail sdlBScheduleDetail);

        void deleteSdlBScheduleDetails(String[]Ids);


        }