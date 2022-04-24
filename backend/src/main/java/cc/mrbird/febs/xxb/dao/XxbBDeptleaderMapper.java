package cc.mrbird.febs.xxb.dao;

import cc.mrbird.febs.xxb.entity.XxbBDeptleader;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 部门负责人 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2022-04-20
 */
public interface XxbBDeptleaderMapper extends BaseMapper<XxbBDeptleader> {
        void updateXxbBDeptleader(XxbBDeptleader xxbBDeptleader);
        IPage<XxbBDeptleader> findXxbBDeptleader(Page page, @Param("xxbBDeptleader") XxbBDeptleader xxbBDeptleader);
        }
