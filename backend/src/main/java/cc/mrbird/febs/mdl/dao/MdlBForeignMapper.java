package cc.mrbird.febs.mdl.dao;

import cc.mrbird.febs.mdl.entity.MdlBForeign;
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
 * @since 2022-05-16
 */
public interface MdlBForeignMapper extends BaseMapper<MdlBForeign> {
        void updateMdlBForeign(MdlBForeign mdlBForeign);
        IPage<MdlBForeign> findMdlBForeign(Page page, @Param("mdlBForeign") MdlBForeign mdlBForeign);
        }
