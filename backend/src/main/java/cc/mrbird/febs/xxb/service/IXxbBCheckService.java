package cc.mrbird.febs.xxb.service;

import cc.mrbird.febs.system.domain.User;
import cc.mrbird.febs.xxb.entity.*;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author viki
 * @since 2022-03-29
 */
public interface IXxbBCheckService extends IService<XxbBCheck> {

        IPage<XxbBCheck> findXxbBChecks(QueryRequest request, XxbBCheck xxbBCheck,User user);

        IPage<XxbBCheck> findXxbBCheckList(QueryRequest request, XxbBCheck xxbBCheck);

        IPage<XxbBCheck> findXxbBflow(QueryRequest request, XxbBCheck xxbBCheck,User user);

        IPage<XxbBCheck> zqList(QueryRequest request, XxbBCheck xxbBCheck);

        IPage<XxbBCheck> zqListAudit(QueryRequest request, XxbBCheck xxbBCheck);

        IPage<XxbBCheck> mqList(QueryRequest request, XxbBCheck xxbBCheck);

        IPage<XxbBCheck> mqListAudit(QueryRequest request, XxbBCheck xxbBCheck);

        void createXxbBCheck(XxbBCheck xxbBCheck);

        void updateXxbBCheck(XxbBCheck xxbBCheck);

        void deleteXxbBChecks(String[]Ids);

        String editXxbBCheck(XxbBCheckInfo xxbBCheckInfo, User user);

        List<XxbBCheckD> getCheckDataList(String baseId);

        List<XxbBDeptflow> getDeptFlowList(String baseId,User user,int state);

        void updateDeptflow(XxbBDeptflow deptflow);

        List<XxbBProjdept> getProjDeptList(String baseId);

        void deleteCheckData(String id);

        void deleteProjDeptData(String id);

        XxbBCheckD addCheckData(XxbBCheckD xxbBCheckD);

        }
