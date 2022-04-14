package cc.mrbird.febs.mdl.dao;

import cc.mrbird.febs.mdl.entity.MdlDBadscore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 不良执业行为 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2022-04-12
 */
public interface MdlDBadscoreMapper extends BaseMapper<MdlDBadscore> {
        void updateMdlDBadscore(MdlDBadscore mdlDBadscore);
        IPage<MdlDBadscore> findMdlDBadscore(Page page, @Param("mdlDBadscore") MdlDBadscore mdlDBadscore);
        }
