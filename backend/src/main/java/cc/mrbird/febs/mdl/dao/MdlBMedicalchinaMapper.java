package cc.mrbird.febs.mdl.dao;

import cc.mrbird.febs.mdl.entity.MdlBMedicalchina;
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
public interface MdlBMedicalchinaMapper extends BaseMapper<MdlBMedicalchina> {
        void updateMdlBMedicalchina(MdlBMedicalchina mdlBMedicalchina);
        IPage<MdlBMedicalchina> findMdlBMedicalchina(Page page, @Param("mdlBMedicalchina") MdlBMedicalchina mdlBMedicalchina);
        }
