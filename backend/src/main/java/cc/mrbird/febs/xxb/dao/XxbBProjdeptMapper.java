package cc.mrbird.febs.xxb.dao;

import cc.mrbird.febs.xxb.entity.XxbBProjdept;
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
 * @since 2022-04-20
 */
public interface XxbBProjdeptMapper extends BaseMapper<XxbBProjdept> {
        void updateXxbBProjdept(XxbBProjdept xxbBProjdept);
        IPage<XxbBProjdept> findXxbBProjdept(Page page, @Param("xxbBProjdept") XxbBProjdept xxbBProjdept);
        }
