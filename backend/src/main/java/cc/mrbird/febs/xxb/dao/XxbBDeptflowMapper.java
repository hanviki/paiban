package cc.mrbird.febs.xxb.dao;

import cc.mrbird.febs.xxb.entity.XxbBDeptflow;
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
 * @since 2022-04-08
 */
public interface XxbBDeptflowMapper extends BaseMapper<XxbBDeptflow> {
        void updateXxbBDeptflow(XxbBDeptflow xxbBDeptflow);
        IPage<XxbBDeptflow> findXxbBDeptflow(Page page, @Param("xxbBDeptflow") XxbBDeptflow xxbBDeptflow);
        }
