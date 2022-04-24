package cc.mrbird.febs.xxb.dao;

import cc.mrbird.febs.xxb.entity.XxbBCheck;
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
 * @since 2022-03-29
 */
public interface XxbBCheckMapper extends BaseMapper<XxbBCheck> {
        void updateXxbBCheck(XxbBCheck xxbBCheck);
        IPage<XxbBCheck> findXxbBCheck(Page page, @Param("xxbBCheck") XxbBCheck xxbBCheck);
        }
