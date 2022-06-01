package cc.mrbird.febs.mdl.service;

import cc.mrbird.febs.mdl.entity.MdlDPrizeOld;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 新技术获奖记录 服务类
 * </p>
 *
 * @author viki
 * @since 2022-05-25
 */
public interface IMdlDPrizeOldService extends IService<MdlDPrizeOld> {

        IPage<MdlDPrizeOld> findMdlDPrizeOlds(QueryRequest request, MdlDPrizeOld mdlDPrizeOld);

        IPage<MdlDPrizeOld> findMdlDPrizeOldList(QueryRequest request, MdlDPrizeOld mdlDPrizeOld);

        void createMdlDPrizeOld(MdlDPrizeOld mdlDPrizeOld);

        void updateMdlDPrizeOld(MdlDPrizeOld mdlDPrizeOld);

        void deleteMdlDPrizeOlds(String[]Ids);


        }
