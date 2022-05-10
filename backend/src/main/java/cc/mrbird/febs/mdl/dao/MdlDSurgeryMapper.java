package cc.mrbird.febs.mdl.dao;

import cc.mrbird.febs.mdl.entity.MdlDSurgery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 手术目录 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2022-05-10
 */
public interface MdlDSurgeryMapper extends BaseMapper<MdlDSurgery> {
        void updateMdlDSurgery(MdlDSurgery mdlDSurgery);
        IPage<MdlDSurgery> findMdlDSurgery(Page page, @Param("mdlDSurgery") MdlDSurgery mdlDSurgery);
        }
