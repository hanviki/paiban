package cc.mrbird.febs.sdl.dao;

import cc.mrbird.febs.sdl.entity.SdlBPersoninfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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

        @Select("SELECT DISTINCT\n" +
                "\tsdl_b_personinfo.dept_name,\n" +
                "\tsdl_b_personinfo.user_account_name,\n" +
                "\tsdl_b_personinfo.telephone " +
                "FROM\n" +
                "\tt_user\n" +
                "\tINNER JOIN t_user_role ON t_user.USER_ID = t_user_role.USER_ID \n" +
                "\tAND t_user_role.ROLE_ID = 6\n" +
                "\tINNER JOIN sdl_b_personinfo ON t_user.dept_id = sdl_b_personinfo.dept_id \n" +
                "\tAND sdl_b_personinfo.telephone != '' \n" +
                "WHERE\n" +
                "\tt_user.USERNAME NOT IN ( SELECT user_no FROM sdl_b_schedule WHERE sdl_b_schedule.state = 1 AND sdl_b_schedule.start_date = #{startDate} )")
        List<SdlBPersoninfo> telList(@Param("startDate") String startDate);
        }
