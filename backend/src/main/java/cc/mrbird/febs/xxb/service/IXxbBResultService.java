package cc.mrbird.febs.xxb.service;

import cc.mrbird.febs.system.domain.User;
import cc.mrbird.febs.xxb.entity.*;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author viki
 * @since 2022-05-23
 */
public interface IXxbBResultService extends IService<XxbBResult> {

    IPage<XxbBResult> findXxbBResults(QueryRequest request, XxbBResult xxbBResult, User user);

    IPage<XxbBResult> findXxbBResultReports(QueryRequest request, XxbBResult xxbBResult,boolean isDf);

    IPage<XxbBResult> findXxbBResultList(QueryRequest request, XxbBResult xxbBResult);

    IPage<XxbBResult> findXxbBResultflow(QueryRequest request, XxbBResult xxbBResult, User user);

    void createXxbBResult(XxbBResult xxbBResult);

    void updateXxbBResult(XxbBResult xxbBResult);

    void deleteXxbBResults(String[] Ids);

    void deleteResultData(String id);

    List<XxbBResultD> getResultDataList(String baseId);

    List<XxbBResultP> getResultPList(String baseId, Integer state);

    String editXxbBResult(XxbBResultInfo xxbBResultInfo, User user);

    List<XxbBResultF> getResultFList(String baseId, User user, int state);

    void updateResultFlow (XxbBResultF resultFlow);

    void updateResultReportPf (List<String> ridList,List<XxbBResult> updateList,List<XxbBResultP> edidList, int state);
}
