package cc.mrbird.febs.mdl.service;

import cc.mrbird.febs.mdl.entity.MdlBSurgeryinfoD;
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
 * @since 2022-05-18
 */
public interface IMdlBSurgeryinfoDService extends IService<MdlBSurgeryinfoD> {

        IPage<MdlBSurgeryinfoD> findMdlBSurgeryinfoDs(QueryRequest request, MdlBSurgeryinfoD mdlBSurgeryinfoD);

        IPage<MdlBSurgeryinfoD> findMdlBSurgeryinfoDList(QueryRequest request, MdlBSurgeryinfoD mdlBSurgeryinfoD);

        void createMdlBSurgeryinfoD(MdlBSurgeryinfoD mdlBSurgeryinfoD);

        void updateMdlBSurgeryinfoD(MdlBSurgeryinfoD mdlBSurgeryinfoD);

        void deleteMdlBSurgeryinfoDs(String[]Ids);


        }
