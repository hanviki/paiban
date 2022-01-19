package cc.mrbird.febs.mdl.service;

import cc.mrbird.febs.mdl.entity.MdlBArchive;
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
public interface IMdlBArchiveService extends IService<MdlBArchive> {

        IPage<MdlBArchive> findMdlBArchives(QueryRequest request, MdlBArchive mdlBArchive);

        IPage<MdlBArchive> findMdlBArchiveList(QueryRequest request, MdlBArchive mdlBArchive);

        void createMdlBArchive(MdlBArchive mdlBArchive);

        void updateMdlBArchive(MdlBArchive mdlBArchive);

        void deleteMdlBArchives(String[]Ids);


        }
