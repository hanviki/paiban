package cc.mrbird.febs.mdl.dao;

import cc.mrbird.febs.mdl.entity.MdlBProfession;
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
 * @since 2022-03-16
 */
public interface MdlBProfessionMapper extends BaseMapper<MdlBProfession> {
        void updateMdlBProfession(MdlBProfession mdlBProfession);
        IPage<MdlBProfession> findMdlBProfession(Page page, @Param("mdlBProfession") MdlBProfession mdlBProfession);
        }
