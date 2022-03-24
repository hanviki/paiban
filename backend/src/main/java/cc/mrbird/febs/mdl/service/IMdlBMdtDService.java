package cc.mrbird.febs.mdl.service;

import cc.mrbird.febs.mdl.entity.MdlBMdtD;
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
 * @since 2022-03-21
 */
public interface IMdlBMdtDService extends IService<MdlBMdtD> {

        IPage<MdlBMdtD> findMdlBMdtDs(QueryRequest request, MdlBMdtD mdlBMdtD);

        IPage<MdlBMdtD> findMdlBMdtDList(QueryRequest request, MdlBMdtD mdlBMdtD);

        void createMdlBMdtD(MdlBMdtD mdlBMdtD);

        void updateMdlBMdtD(MdlBMdtD mdlBMdtD);

        void deleteMdlBMdtDs(String[]Ids);


        }
