package cc.mrbird.febs.mdl.service;

import cc.mrbird.febs.mdl.entity.MdlDMdtOld;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 新技术新项目旧记录 服务类
 * </p>
 *
 * @author viki
 * @since 2022-05-25
 */
public interface IMdlDMdtOldService extends IService<MdlDMdtOld> {

        IPage<MdlDMdtOld> findMdlDMdtOlds(QueryRequest request, MdlDMdtOld mdlDMdtOld);

        IPage<MdlDMdtOld> findMdlDMdtOldList(QueryRequest request, MdlDMdtOld mdlDMdtOld);

        void createMdlDMdtOld(MdlDMdtOld mdlDMdtOld);

        void updateMdlDMdtOld(MdlDMdtOld mdlDMdtOld);

        void deleteMdlDMdtOlds(String[]Ids);


        }
