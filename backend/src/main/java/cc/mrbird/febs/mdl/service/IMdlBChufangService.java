package cc.mrbird.febs.mdl.service;

import cc.mrbird.febs.mdl.entity.MdlBChufang;
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
 * @since 2022-01-19
 */
public interface IMdlBChufangService extends IService<MdlBChufang> {

        IPage<MdlBChufang> findMdlBChufangs(QueryRequest request, MdlBChufang mdlBChufang);

        IPage<MdlBChufang> findMdlBChufangList(QueryRequest request, MdlBChufang mdlBChufang);

        void createMdlBChufang(MdlBChufang mdlBChufang);

        void updateMdlBChufang(MdlBChufang mdlBChufang);

        void deleteMdlBChufangs(String[]Ids);


        }
