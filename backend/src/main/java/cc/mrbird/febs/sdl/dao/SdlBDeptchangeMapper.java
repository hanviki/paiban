package cc.mrbird.febs.sdl.dao;

import cc.mrbird.febs.sdl.entity.SdlBDeptchange;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2021-10-14
 */
public interface SdlBDeptchangeMapper extends BaseMapper<SdlBDeptchange> {
        void updateSdlBDeptchange(SdlBDeptchange sdlBDeptchange);
        IPage<SdlBDeptchange> findSdlBDeptchange(Page page, @Param("sdlBDeptchange") SdlBDeptchange sdlBDeptchange);

        @Select("select " +
                "sdl_b_user.user_account_name,\n" +
                "sdl_b_user.user_account,\n" +
                "sdl_b_user.bq_name,\n" +
                "sdl_b_user.user_type,\n" +
                "sdl_b_user.dept_id,\n" +
                "sdl_b_user.telephone,\n" +
                "sdl_b_user.dept_name,\n" +
                "sdl_b_user.position_name,\n" +
                "sdl_b_user.np_position_name,\n" +
                "sdl_b_user.sex_name,\n" +
                "sdl_b_user.birthday,\n" +
                "sdl_b_user.school_date,\n" +
                "sdl_b_user.zyjsgw,\n" +
                "sdl_b_user.zyjsgw_lc,\n" +
                "sdl_b_user.xcszyjzc,\n" +
                "sdl_b_user.appointed_date,\n" +
                "sdl_b_user.patent_ranknum,\n" +
                "sdl_b_user.is_authority,\n" +
                "sdl_b_user.file_id,\n" +
                "sdl_b_user.is_zhuanrang,\n" +
                "sdl_b_user.file_url,\n" +
                "sdl_b_user.patent_good,\n" +
                "sdl_b_user.file_url_lc,\n" +
                "sdl_b_user.yuangongzu,\n" +
                "sdl_b_user.xrgwjb,\n" +
                "sdl_b_user.xrgwjbprsj,\n" +
                "sdl_b_user.djrdzzw,\n" +
                "sdl_b_user.is_chujikh,\n" +
                "sdl_b_user.chujikh_date,\n" +
                "sdl_b_user.is_zhongjikh,\n" +
                "sdl_b_user.zhongjikh_date,\n" +
                "sdl_b_user.political_status,\n" +
                "sdl_b_user.staff_grade,\n" +
                "sdl_b_user.id_card,\n" +
                "sdl_b_user.staff_date,\n" +
                "sdl_b_user.picture_id,\n" +
                "sdl_b_user.picture_url,\n" +
                "sdl_b_user.gqpxqk,\n" +
                "sdl_b_user.gfhyspxqk,\n" +
                "sdl_b_user.zjspnlceqk,\n" +
                "sdl_b_user.dca_year,\n" +
                "sdl_b_user.appointed_date_lc,\n" +
                "sdl_b_user.file_id_lc  from sdl_b_user where user_account=#{userAccount}")
        SdlBDeptchange getUserByAccount(@Param("userAccount") String userAccount);

        @Update("update sdl_b_user set dept_id=#{deptId}, dept_name=#{deptName}, bq_name=#{bqName} where user_account=#{userAccount} ")
        void updateUserDept(@Param("userAccount") String userAccount,@Param("deptId") String deptId,@Param("deptName") String deptName,@Param("bqName") String bqName);
}
