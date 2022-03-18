package cc.mrbird.febs.mdl.service;

import cc.mrbird.febs.mdl.entity.MdlBProfession;
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
 * @since 2022-03-16
 */
public interface IMdlBProfessionService extends IService<MdlBProfession> {

        IPage<MdlBProfession> findMdlBProfessions(QueryRequest request, MdlBProfession mdlBProfession);

        IPage<MdlBProfession> findMdlBProfessionList(QueryRequest request, MdlBProfession mdlBProfession);

        void createMdlBProfession(MdlBProfession mdlBProfession);

        void updateMdlBProfession(MdlBProfession mdlBProfession);

        void deleteMdlBProfessions(String[]Ids);


        }
