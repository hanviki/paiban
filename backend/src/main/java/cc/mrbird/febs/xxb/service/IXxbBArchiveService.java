package cc.mrbird.febs.xxb.service;

import cc.mrbird.febs.xxb.entity.XxbBArchive;
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
 * @since 2022-04-24
 */
public interface IXxbBArchiveService extends IService<XxbBArchive> {

        IPage<XxbBArchive> findXxbBArchives(QueryRequest request, XxbBArchive xxbBArchive);

        IPage<XxbBArchive> findXxbBArchiveList(QueryRequest request, XxbBArchive xxbBArchive);

        void createXxbBArchive(XxbBArchive xxbBArchive);

        void updateXxbBArchive(XxbBArchive xxbBArchive);

        void deleteXxbBArchives(String[]Ids);


        }
