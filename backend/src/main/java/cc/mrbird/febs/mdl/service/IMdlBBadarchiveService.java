package cc.mrbird.febs.mdl.service;

import cc.mrbird.febs.mdl.entity.MdlBBadarchive;
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
 * @since 2022-06-06
 */
public interface IMdlBBadarchiveService extends IService<MdlBBadarchive> {

        IPage<MdlBBadarchive> findMdlBBadarchives(QueryRequest request, MdlBBadarchive mdlBBadarchive);

        IPage<MdlBBadarchive> findMdlBBadarchiveList(QueryRequest request, MdlBBadarchive mdlBBadarchive);

        void createMdlBBadarchive(MdlBBadarchive mdlBBadarchive);

        void updateMdlBBadarchive(MdlBBadarchive mdlBBadarchive);

        void deleteMdlBBadarchives(String[]Ids);


        }
