package cc.mrbird.febs.mdl.dao;

import cc.mrbird.febs.mdl.entity.MdlBMdtD;
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
 * @since 2022-03-21
 */
public interface MdlBMdtDMapper extends BaseMapper<MdlBMdtD> {
        void updateMdlBMdtD(MdlBMdtD mdlBMdtD);
        IPage<MdlBMdtD> findMdlBMdtD(Page page, @Param("mdlBMdtD") MdlBMdtD mdlBMdtD);
        }
