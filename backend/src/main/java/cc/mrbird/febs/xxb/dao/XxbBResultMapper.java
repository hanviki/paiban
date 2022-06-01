package cc.mrbird.febs.xxb.dao;

import cc.mrbird.febs.xxb.entity.XxbBResult;
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
 * @since 2022-05-23
 */
public interface XxbBResultMapper extends BaseMapper<XxbBResult> {
        void updateXxbBResult(XxbBResult xxbBResult);
        IPage<XxbBResult> findXxbBResult(Page page, @Param("xxbBResult") XxbBResult xxbBResult);
        }
