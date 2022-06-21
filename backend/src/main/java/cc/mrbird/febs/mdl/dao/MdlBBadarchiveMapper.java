package cc.mrbird.febs.mdl.dao;

import cc.mrbird.febs.mdl.entity.MdlBBadarchive;
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
 * @since 2022-06-06
 */
public interface MdlBBadarchiveMapper extends BaseMapper<MdlBBadarchive> {
        void updateMdlBBadarchive(MdlBBadarchive mdlBBadarchive);
        IPage<MdlBBadarchive> findMdlBBadarchive(Page page, @Param("mdlBBadarchive") MdlBBadarchive mdlBBadarchive);
        }
