package cc.mrbird.febs.mdl.dao;

import cc.mrbird.febs.mdl.entity.MdlDPrizeOld;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 新技术获奖记录 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2022-05-25
 */
public interface MdlDPrizeOldMapper extends BaseMapper<MdlDPrizeOld> {
        void updateMdlDPrizeOld(MdlDPrizeOld mdlDPrizeOld);
        IPage<MdlDPrizeOld> findMdlDPrizeOld(Page page, @Param("mdlDPrizeOld") MdlDPrizeOld mdlDPrizeOld);
        }
