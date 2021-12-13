package cc.mrbird.febs.sdl.dao;

import cc.mrbird.febs.sdl.entity.SdlBPersoninfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2021-12-10
 */
public interface SdlBPersoninfoMapper extends BaseMapper<SdlBPersoninfo> {
        void updateSdlBPersoninfo(SdlBPersoninfo sdlBPersoninfo);

        @Delete("delete from sdl_b_personinfo where dept_id=#{deptId}")
        void deleteByDeptId(String deptId);
        IPage<SdlBPersoninfo> findSdlBPersoninfo(Page page, @Param("sdlBPersoninfo") SdlBPersoninfo sdlBPersoninfo);
        }
