package cc.mrbird.febs.mdl.service;

import cc.mrbird.febs.mdl.entity.MdlBBadrecord;
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
 * @since 2022-04-12
 */
public interface IMdlBBadrecordService extends IService<MdlBBadrecord> {

        IPage<MdlBBadrecord> findMdlBBadrecords(QueryRequest request, MdlBBadrecord mdlBBadrecord);

        IPage<MdlBBadrecord> findMdlBBadrecordList(QueryRequest request, MdlBBadrecord mdlBBadrecord);

        void createMdlBBadrecord(MdlBBadrecord mdlBBadrecord);

        void updateMdlBBadrecord(MdlBBadrecord mdlBBadrecord);

        void deleteMdlBBadrecords(String[]Ids);


        }
