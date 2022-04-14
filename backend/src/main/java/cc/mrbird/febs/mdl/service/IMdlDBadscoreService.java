package cc.mrbird.febs.mdl.service;

import cc.mrbird.febs.mdl.entity.MdlDBadscore;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 不良执业行为 服务类
 * </p>
 *
 * @author viki
 * @since 2022-04-12
 */
public interface IMdlDBadscoreService extends IService<MdlDBadscore> {

        IPage<MdlDBadscore> findMdlDBadscores(QueryRequest request, MdlDBadscore mdlDBadscore);

        IPage<MdlDBadscore> findMdlDBadscoreList(QueryRequest request, MdlDBadscore mdlDBadscore);

        void createMdlDBadscore(MdlDBadscore mdlDBadscore);

        void updateMdlDBadscore(MdlDBadscore mdlDBadscore);

        void deleteMdlDBadscores(String[]Ids);


        }
