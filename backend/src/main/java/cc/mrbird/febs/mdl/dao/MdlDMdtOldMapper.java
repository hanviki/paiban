package cc.mrbird.febs.mdl.dao;

import cc.mrbird.febs.mdl.entity.MdlDMdtOld;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 新技术新项目旧记录 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2022-05-25
 */
public interface MdlDMdtOldMapper extends BaseMapper<MdlDMdtOld> {
        void updateMdlDMdtOld(MdlDMdtOld mdlDMdtOld);
        IPage<MdlDMdtOld> findMdlDMdtOld(Page page, @Param("mdlDMdtOld") MdlDMdtOld mdlDMdtOld);
        }
