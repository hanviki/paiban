package cc.mrbird.febs.mdl.service;

import cc.mrbird.febs.mdl.entity.MdlDSurgery;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 手术目录 服务类
 * </p>
 *
 * @author viki
 * @since 2022-05-10
 */
public interface IMdlDSurgeryService extends IService<MdlDSurgery> {

        IPage<MdlDSurgery> findMdlDSurgerys(QueryRequest request, MdlDSurgery mdlDSurgery);

        IPage<MdlDSurgery> findMdlDSurgeryList(QueryRequest request, MdlDSurgery mdlDSurgery);

        void createMdlDSurgery(MdlDSurgery mdlDSurgery);

        void updateMdlDSurgery(MdlDSurgery mdlDSurgery);

        void deleteMdlDSurgerys(String[]Ids);


        }
