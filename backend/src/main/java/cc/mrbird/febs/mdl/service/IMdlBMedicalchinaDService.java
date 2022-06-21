package cc.mrbird.febs.mdl.service;

import cc.mrbird.febs.mdl.entity.MdlBMedicalchinaD;
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
public interface IMdlBMedicalchinaDService extends IService<MdlBMedicalchinaD> {

        IPage<MdlBMedicalchinaD> findMdlBMedicalchinaDs(QueryRequest request, MdlBMedicalchinaD mdlBMedicalchinaD);

        IPage<MdlBMedicalchinaD> findMdlBMedicalchinaDList(QueryRequest request, MdlBMedicalchinaD mdlBMedicalchinaD);

        void createMdlBMedicalchinaD(MdlBMedicalchinaD mdlBMedicalchinaD);

        void updateMdlBMedicalchinaD(MdlBMedicalchinaD mdlBMedicalchinaD);

        void deleteMdlBMedicalchinaDs(String[]Ids);


        }
