package cc.mrbird.febs.xxb.dao;

import cc.mrbird.febs.xxb.entity.XxbBZq;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 中期反馈表 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2022-04-26
 */
public interface XxbBZqMapper extends BaseMapper<XxbBZq> {
        void updateXxbBZq(XxbBZq xxbBZq);
        IPage<XxbBZq> findXxbBZq(Page page, @Param("xxbBZq") XxbBZq xxbBZq);
        }
