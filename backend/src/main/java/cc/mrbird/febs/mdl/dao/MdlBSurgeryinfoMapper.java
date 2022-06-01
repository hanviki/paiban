package cc.mrbird.febs.mdl.dao;

import cc.mrbird.febs.mdl.entity.MdlBSurgeryinfo;
import cc.mrbird.febs.mdl.entity.MdlBSurgeryinfoD;
import cc.mrbird.febs.mdl.entity.ViewInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2022-05-18
 */
public interface MdlBSurgeryinfoMapper extends BaseMapper<MdlBSurgeryinfo> {
        void updateMdlBSurgeryinfo(MdlBSurgeryinfo mdlBSurgeryinfo);
        IPage<ViewInfo> findMdlBSurgeryinfo(Page page, @Param("mdlBSurgeryinfo") MdlBSurgeryinfo mdlBSurgeryinfo);

        void insertSub (@Param("jbList") String[] jbList,@Param("baseId") Long baseId,@Param("deptNew") String deptNew);
        }
