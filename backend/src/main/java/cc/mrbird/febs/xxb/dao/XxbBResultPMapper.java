package cc.mrbird.febs.xxb.dao;

import cc.mrbird.febs.xxb.entity.XxbBResultP;
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
 * @since 2022-05-27
 */
public interface XxbBResultPMapper extends BaseMapper<XxbBResultP> {
        void updateXxbBResultP(XxbBResultP xxbBResultP);
        IPage<XxbBResultP> findXxbBResultP(Page page, @Param("xxbBResultP") XxbBResultP xxbBResultP);
        }
