package cc.mrbird.febs.mdl.service;

import cc.mrbird.febs.mdl.entity.MdlBMdtFile;
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
 * @since 2022-03-23
 */
public interface IMdlBMdtFileService extends IService<MdlBMdtFile> {

        IPage<MdlBMdtFile> findMdlBMdtFiles(QueryRequest request, MdlBMdtFile mdlBMdtFile);

        IPage<MdlBMdtFile> findMdlBMdtFileList(QueryRequest request, MdlBMdtFile mdlBMdtFile);

        void createMdlBMdtFile(MdlBMdtFile mdlBMdtFile);

        void updateMdlBMdtFile(MdlBMdtFile mdlBMdtFile);

        void deleteMdlBMdtFiles(String[]Ids);


        }
