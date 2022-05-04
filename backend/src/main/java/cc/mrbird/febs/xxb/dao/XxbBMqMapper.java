package cc.mrbird.febs.xxb.dao;

import cc.mrbird.febs.xxb.entity.XxbBMq;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 末期反馈表 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2022-04-27
 */
public interface XxbBMqMapper extends BaseMapper<XxbBMq> {
        void updateXxbBMq(XxbBMq xxbBMq);
        IPage<XxbBMq> findXxbBMq(Page page, @Param("xxbBMq") XxbBMq xxbBMq);
        }
