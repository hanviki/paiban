package cc.mrbird.febs.mdl.dao;

import cc.mrbird.febs.mdl.entity.MdlBBadrecord;
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
 * @since 2022-04-12
 */
public interface MdlBBadrecordMapper extends BaseMapper<MdlBBadrecord> {
        void updateMdlBBadrecord(MdlBBadrecord mdlBBadrecord);
        IPage<MdlBBadrecord> findMdlBBadrecord(Page page, @Param("mdlBBadrecord") MdlBBadrecord mdlBBadrecord);
        }
