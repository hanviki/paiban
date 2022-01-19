package cc.mrbird.febs.mdl.dao;

import cc.mrbird.febs.mdl.entity.MdlBChufang;
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
 * @since 2022-01-19
 */
public interface MdlBChufangMapper extends BaseMapper<MdlBChufang> {
        void updateMdlBChufang(MdlBChufang mdlBChufang);
        IPage<MdlBChufang> findMdlBChufang(Page page, @Param("mdlBChufang") MdlBChufang mdlBChufang);
        }
