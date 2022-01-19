package cc.mrbird.febs.mdl.dao;

import cc.mrbird.febs.mdl.entity.MdlBManager;
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
public interface MdlBManagerMapper extends BaseMapper<MdlBManager> {
        void updateMdlBManager(MdlBManager mdlBManager);
        IPage<MdlBManager> findMdlBManager(Page page, @Param("mdlBManager") MdlBManager mdlBManager);
        }
