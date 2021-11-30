package cc.mrbird.febs.sdl.service;

import cc.mrbird.febs.sdl.entity.SdlDWeekendday;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 周六日表 服务类
 * </p>
 *
 * @author viki
 * @since 2021-11-11
 */
public interface ISdlDWeekenddayService extends IService<SdlDWeekendday> {

        IPage<SdlDWeekendday> findSdlDWeekenddays(QueryRequest request, SdlDWeekendday sdlDWeekendday);

        IPage<SdlDWeekendday> findSdlDWeekenddayList(QueryRequest request, SdlDWeekendday sdlDWeekendday);

        void createSdlDWeekendday(SdlDWeekendday sdlDWeekendday);

        void updateSdlDWeekendday(SdlDWeekendday sdlDWeekendday);

        void deleteSdlDWeekenddays(String[]Ids);


        }
