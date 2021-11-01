package cc.mrbird.febs.sdl.dao;

import cc.mrbird.febs.sdl.entity.SdlBSchedule;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

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

        @Update("update sdl_b_schedule set state=#{state},state_apply=0 where id =#{id}")
        void updateStateById( @Param("id") String id,int state);
        }
