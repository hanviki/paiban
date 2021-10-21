package cc.mrbird.febs.sdl.dao;

import cc.mrbird.febs.sdl.entity.SdlBZizhiapply;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 资质变更申请 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2021-10-13
 */
public interface SdlBZizhiapplyMapper extends BaseMapper<SdlBZizhiapply> {
        void updateSdlBZizhiapply(SdlBZizhiapply sdlBZizhiapply);
        @Select("SELECT\n" +
                "sdl_b_user.user_account_name,\n" +
                "sdl_b_user.user_account,\n" +
                "sdl_b_user.bq_name,\n" +
                "sdl_b_user.user_type,\n" +
                "sdl_b_user.user_type_name,\n" +
                "sdl_b_user.dept_id,\n" +
                "sdl_b_user.telephone,\n" +
                "sdl_b_user.dept_name,\n" +
                "sdl_b_user.position_name,\n" +
                "sdl_b_user.np_position_name,\n" +
                "sdl_b_user.rylx,\n" +
                "sdl_b_user.yggh,\n" +
                "sdl_b_user.gh_hk,\n" +
                "sdl_b_user.sex_name,\n" +
                "sdl_b_user.qualify_no,\n" +
                "sdl_b_user.birthday,\n" +
                "sdl_b_user.school_date,\n" +
                "sdl_b_user.zyjsgw,\n" +
                "sdl_b_user.bianhao_jx,\n" +
                "sdl_b_user.zyjsgw_lc,\n" +
                "sdl_b_user.xcszyjzc,\n" +
                "sdl_b_user.appointed_date,\n" +
                "sdl_b_user.appointed_date_lc,\n" +
                "sdl_b_user.zizu,\n" +
                "sdl_b_user.yuangongzu,\n" +
                "sdl_b_user.xrgwjb,\n" +
                "sdl_b_user.xrgwjbprsj,\n" +
                "sdl_b_user.zhicheng,\n" +
                "sdl_b_user.zyjs_np,\n" +
                "sdl_b_user.zyjs_date_np,\n" +
                "sdl_b_user.zyjs_npjx,\n" +
                "sdl_b_user.zyjs_date_npjx,\n" +
                "sdl_b_user.edu,\n" +
                "sdl_b_user.edu_school,\n" +
                "sdl_b_user.guoji,\n" +
                "sdl_b_user.minzu,\n" +
                "sdl_b_user.jiguan,\n" +
                "sdl_b_user.political_status,\n" +
                "sdl_b_user.bianhao_lc,\n" +
                "sdl_b_user.xingzhengjiebie,\n" +
                "sdl_b_user.id_card,\n" +
                "sdl_b_user.work_date,\n" +
                "sdl_b_user.yishi_lb,\n" +
                "sdl_b_user.yishi_jb,\n" +
                "sdl_b_user.yishi_zhiyefanwei,\n" +
                "sdl_b_user.yishi_zgzsbianhao,\n" +
                "sdl_b_user.yishi_ziyebianhao,\n" +
                "sdl_b_user.renshizifw,\n" +
                "sdl_b_user.renshizfenlei\n" +
                " from sdl_b_user where user_account=#{userAccount}")
        SdlBZizhiapply getUserByAccount(@Param("userAccount") String userAccount);
        IPage<SdlBZizhiapply> findSdlBZizhiapply(Page page, @Param("sdlBZizhiapply") SdlBZizhiapply sdlBZizhiapply);

        @Update("update sdl_b_user a,sdl_b_zizhiapply b set a.user_type_name=b.user_new_type_name,a.user_type=b.user_new_type where a.user_account=b.user_account and b.id=#{id}")
        void updateUserType(@Param("id") String id);
        }
