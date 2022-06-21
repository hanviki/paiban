package cc.mrbird.febs.mdl.service;

import cc.mrbird.febs.mdl.entity.MdlBMedicalchina;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author viki
 * @since 2022-06-08
 */
public interface IMdlBMedicalchinaService extends IService<MdlBMedicalchina> {

        IPage<MdlBMedicalchina> findMdlBMedicalchinas(QueryRequest request, MdlBMedicalchina mdlBMedicalchina);

        IPage<MdlBMedicalchina> findMdlBMedicalchinaList(QueryRequest request, MdlBMedicalchina mdlBMedicalchina);

        void createMdlBMedicalchina(MdlBMedicalchina mdlBMedicalchina);

        void updateMdlBMedicalchina(MdlBMedicalchina mdlBMedicalchina);

        void deleteMdlBMedicalchinas(String[]Ids);


        }
