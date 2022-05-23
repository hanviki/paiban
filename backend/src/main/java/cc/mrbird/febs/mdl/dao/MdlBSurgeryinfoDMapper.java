package cc.mrbird.febs.mdl.dao;

import cc.mrbird.febs.mdl.entity.MdlBSurgeryinfoD;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2022-05-18
 */
public interface MdlBSurgeryinfoDMapper extends BaseMapper<MdlBSurgeryinfoD> {
        void updateMdlBSurgeryinfoD(MdlBSurgeryinfoD mdlBSurgeryinfoD);
        IPage<MdlBSurgeryinfoD> findMdlBSurgeryinfoD(Page page, @Param("mdlBSurgeryinfoD") MdlBSurgeryinfoD mdlBSurgeryinfoD);
        }
