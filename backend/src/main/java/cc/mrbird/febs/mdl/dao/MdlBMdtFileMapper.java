package cc.mrbird.febs.mdl.dao;

import cc.mrbird.febs.mdl.entity.MdlBMdtFile;
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
 * @since 2022-03-23
 */
public interface MdlBMdtFileMapper extends BaseMapper<MdlBMdtFile> {
        void updateMdlBMdtFile(MdlBMdtFile mdlBMdtFile);
        IPage<MdlBMdtFile> findMdlBMdtFile(Page page, @Param("mdlBMdtFile") MdlBMdtFile mdlBMdtFile);
        }
