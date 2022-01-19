package cc.mrbird.febs.mdl.service;

import cc.mrbird.febs.mdl.entity.MdlBManager;
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
 * @since 2022-01-17
 */
public interface IMdlBManagerService extends IService<MdlBManager> {

        IPage<MdlBManager> findMdlBManagers(QueryRequest request, MdlBManager mdlBManager);

        IPage<MdlBManager> findMdlBManagerList(QueryRequest request, MdlBManager mdlBManager);

        void createMdlBManager(MdlBManager mdlBManager);

        void updateMdlBManager(MdlBManager mdlBManager);

        void deleteMdlBManagers(String[]Ids);


        }
