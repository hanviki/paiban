package cc.mrbird.febs.mdl.dao;

import cc.mrbird.febs.mdl.entity.MdlBArchive;
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
 * @since 2022-01-17
 */
public interface MdlBArchiveMapper extends BaseMapper<MdlBArchive> {
        void updateMdlBArchive(MdlBArchive mdlBArchive);
        IPage<MdlBArchive> findMdlBArchive(Page page, @Param("mdlBArchive") MdlBArchive mdlBArchive);
        }
