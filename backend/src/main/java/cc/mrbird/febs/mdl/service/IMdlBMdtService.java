package cc.mrbird.febs.mdl.service;

import cc.mrbird.febs.mdl.entity.MdlBMdt;
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
public interface IMdlBMdtService extends IService<MdlBMdt> {

        IPage<MdlBMdt> findMdlBMdts(QueryRequest request, MdlBMdt mdlBMdt);

        IPage<MdlBMdt> findMdlBMdtList(QueryRequest request, MdlBMdt mdlBMdt);

        void createMdlBMdt(MdlBMdt mdlBMdt);

        void updateMdlBMdt(MdlBMdt mdlBMdt);

        void deleteMdlBMdts(String[]Ids);
        void deleteByBaseId(Long id);

        }
