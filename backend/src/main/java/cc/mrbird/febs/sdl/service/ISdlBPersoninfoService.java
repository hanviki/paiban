package cc.mrbird.febs.sdl.service;

import cc.mrbird.febs.sdl.entity.SdlBPersoninfo;
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
 * @since 2021-12-10
 */
public interface ISdlBPersoninfoService extends IService<SdlBPersoninfo> {

        IPage<SdlBPersoninfo> findSdlBPersoninfos(QueryRequest request, SdlBPersoninfo sdlBPersoninfo);

        IPage<SdlBPersoninfo> findSdlBPersoninfoList(QueryRequest request, SdlBPersoninfo sdlBPersoninfo);

        void createSdlBPersoninfo(SdlBPersoninfo sdlBPersoninfo);

        void updateSdlBPersoninfo(SdlBPersoninfo sdlBPersoninfo);

        void deleteSdlBPersoninfos(String[]Ids);

        void deleteByDeptId(String deptID);

        List<SdlBPersoninfo> sendMess(String startDate);

        List<SdlBPersoninfo> getNoSubmit(String startDate,String zhouri);


        }
