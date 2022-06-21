package cc.mrbird.febs.mdl.dao;

import cc.mrbird.febs.mdl.entity.MdlDOperation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 操作目录 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2022-06-09
 */
public interface MdlDOperationMapper extends BaseMapper<MdlDOperation> {
        void updateMdlDOperation(MdlDOperation mdlDOperation);
        IPage<MdlDOperation> findMdlDOperation(Page page, @Param("mdlDOperation") MdlDOperation mdlDOperation);
        }
