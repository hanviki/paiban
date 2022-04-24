package cc.mrbird.febs.sdl.service;

import cc.mrbird.febs.sdl.entity.CustomData;
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

        List<CustomData> findSdlBScheduleReport(SdlBScheduleDetail sdlBScheduleDetail);
        List<CustomData> findSdlBScheduleReport2(SdlBScheduleDetail sdlBScheduleDetail);

        List<CustomData>  findYeBanReport(SdlBScheduleDetail sdlBScheduleDetail);
        List<CustomData>  findYeBanSubReport(SdlBScheduleDetail sdlBScheduleDetail);
        List<CustomData>  findMenZhenReport(SdlBScheduleDetail sdlBScheduleDetail);
        List<CustomData>  findMenZhenSubReport(SdlBScheduleDetail sdlBScheduleDetail);
        List<SdlBScheduleDetail> findSdlBScheduleReportAccount(SdlBScheduleDetail sdlBScheduleDetail);
        List<SdlBScheduleDetail> findSdlBScheduleReportAccount2(SdlBScheduleDetail sdlBScheduleDetail);
        List<SdlBScheduleDetail> findYeBanSubReportAccount(SdlBScheduleDetail sdlBScheduleDetail);

        List<SdlBScheduleDetail> findMenZhenSubReportAccount(SdlBScheduleDetail sdlBScheduleDetail);


}
