package cc.mrbird.febs.sdl.dao;

import cc.mrbird.febs.sdl.entity.SdlBScheduleD;
import cc.mrbird.febs.sdl.entity.SdlDBanci;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

        List<SdlBScheduleD> getPaiBanZizhi(@Param("sdlBScheduleD") SdlBScheduleD sdlBScheduleD);

        /**
         * 删除排班表的同时，删除排班表的详细个人排班数据
         * @param deptId
         * @param startDate
         * @param endDate
         */
        @Delete(" delete from sdl_b_schedule_d where dept_id=#{deptId} and schedule_date>=#{startDate} and schedule_date<=#{endDate};" +
                " delete from sdl_b_schedule_detail where dept_id=#{deptId} and schedule_date>=#{startDate} and schedule_date<=#{endDate}")
        void deleteByDeptAndDate(@Param("deptId") String  deptId,@Param("startDate") String  startDate,@Param("endDate") String  endDate);

        }
