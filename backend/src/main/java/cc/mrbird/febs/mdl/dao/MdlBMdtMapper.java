package cc.mrbird.febs.mdl.dao;

import cc.mrbird.febs.mdl.entity.MdlBMdt;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2022-03-21
 */
public interface MdlBMdtMapper extends BaseMapper<MdlBMdt> {
        void updateMdlBMdt(MdlBMdt mdlBMdt);
        IPage<MdlBMdt> findMdlBMdt(Page page, @Param("mdlBMdt") MdlBMdt mdlBMdt);

        @Delete("delete from mdl_b_mdt_d where base_id=#{baseId}" )
        void DeletByBaseId(@Param("baseId") Long baseId);
        }
