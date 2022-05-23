package cc.mrbird.febs.mdl.service;

import cc.mrbird.febs.mdl.entity.MdlBForeignD;
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
 * @since 2022-05-16
 */
public interface IMdlBForeignDService extends IService<MdlBForeignD> {

        IPage<MdlBForeignD> findMdlBForeignDs(QueryRequest request, MdlBForeignD mdlBForeignD);

        IPage<MdlBForeignD> findMdlBForeignDList(QueryRequest request, MdlBForeignD mdlBForeignD);

        void createMdlBForeignD(MdlBForeignD mdlBForeignD);

        void updateMdlBForeignD(MdlBForeignD mdlBForeignD);

        void deleteMdlBForeignDs(String[]Ids);


        }
