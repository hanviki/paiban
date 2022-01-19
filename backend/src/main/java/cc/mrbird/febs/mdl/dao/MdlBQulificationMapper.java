package cc.mrbird.febs.mdl.dao;

import cc.mrbird.febs.mdl.entity.MdlBQulification;
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
 * @since 2022-01-12
 */
public interface MdlBQulificationMapper extends BaseMapper<MdlBQulification> {
        void updateMdlBQulification(MdlBQulification mdlBQulification);
        IPage<MdlBQulification> findMdlBQulification(Page page, @Param("mdlBQulification") MdlBQulification mdlBQulification);
        }
