package cc.mrbird.febs.mdl.service;

import cc.mrbird.febs.mdl.entity.MdlBSurgeryinfo;
import cc.mrbird.febs.mdl.entity.MdlBSurgeryinfoD;
import cc.mrbird.febs.mdl.entity.ViewInfo;
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
public interface IMdlBSurgeryinfoService extends IService<MdlBSurgeryinfo> {

        IPage<MdlBSurgeryinfo> findMdlBSurgeryinfos(QueryRequest request, MdlBSurgeryinfo mdlBSurgeryinfo);

        IPage<ViewInfo> findMdlBSurgeryinfoList(QueryRequest request, MdlBSurgeryinfo mdlBSurgeryinfo);

        void createMdlBSurgeryinfo(MdlBSurgeryinfo mdlBSurgeryinfo);

        void updateMdlBSurgeryinfo(MdlBSurgeryinfo mdlBSurgeryinfo);

        void deleteMdlBSurgeryinfos(String[]Ids);

        void InserSub(MdlBSurgeryinfoD mdlBSurgeryinfoD);
        }
