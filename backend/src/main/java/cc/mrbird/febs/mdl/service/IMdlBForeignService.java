package cc.mrbird.febs.mdl.service;

import cc.mrbird.febs.mdl.entity.MdlBForeign;
import cc.mrbird.febs.system.domain.User;
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
 * @since 2022-05-16
 */
public interface IMdlBForeignService extends IService<MdlBForeign> {

        IPage<MdlBForeign> findMdlBForeigns(QueryRequest request, MdlBForeign mdlBForeign);

        IPage<MdlBForeign> findMdlBForeignList(QueryRequest request, MdlBForeign mdlBForeign);

        void createMdlBForeign(MdlBForeign mdlBForeign);

        void updateMdlBForeign(MdlBForeign mdlBForeign);

        void deleteMdlBForeigns(String[]Ids);
        String editXxbBCheck(MdlBForeign xxbBCheckInfo, User user);


        }
