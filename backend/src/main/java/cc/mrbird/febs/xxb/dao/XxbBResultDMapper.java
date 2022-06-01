package cc.mrbird.febs.xxb.dao;

import cc.mrbird.febs.xxb.entity.XxbBResultD;
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
public interface XxbBResultDMapper extends BaseMapper<XxbBResultD> {
        void updateXxbBResultD(XxbBResultD xxbBResultD);
        IPage<XxbBResultD> findXxbBResultD(Page page, @Param("xxbBResultD") XxbBResultD xxbBResultD);
        }
