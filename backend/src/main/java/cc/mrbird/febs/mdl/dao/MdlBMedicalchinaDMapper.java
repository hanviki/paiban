package cc.mrbird.febs.mdl.dao;

import cc.mrbird.febs.mdl.entity.MdlBMedicalchinaD;
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
 * @since 2022-06-08
 */
public interface MdlBMedicalchinaDMapper extends BaseMapper<MdlBMedicalchinaD> {
        void updateMdlBMedicalchinaD(MdlBMedicalchinaD mdlBMedicalchinaD);
        IPage<MdlBMedicalchinaD> findMdlBMedicalchinaD(Page page, @Param("mdlBMedicalchinaD") MdlBMedicalchinaD mdlBMedicalchinaD);
        }
