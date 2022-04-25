package cc.mrbird.febs.xxb.dao;

import cc.mrbird.febs.xxb.entity.XxbBArchive;
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
 * @since 2022-04-24
 */
public interface XxbBArchiveMapper extends BaseMapper<XxbBArchive> {
        void updateXxbBArchive(XxbBArchive xxbBArchive);
        IPage<XxbBArchive> findXxbBArchive(Page page, @Param("xxbBArchive") XxbBArchive xxbBArchive);
        }
