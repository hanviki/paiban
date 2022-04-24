package cc.mrbird.febs.sdl.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.common.utils.DateUtil;
import cc.mrbird.febs.common.utils.ExportExcelUtils;
import cc.mrbird.febs.rfc.CustomUser;
import cc.mrbird.febs.sdl.entity.CustomData;
import cc.mrbird.febs.sdl.service.ISdlBScheduleDetailService;
import cc.mrbird.febs.sdl.entity.SdlBScheduleDetail;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author viki
 * @since 2021-10-13
 */
@Slf4j
@Validated
@RestController
@RequestMapping("sdlBScheduleDetail")

public class SdlBScheduleDetailController extends BaseController {

    private String message;
    @Autowired
    public ISdlBScheduleDetailService iSdlBScheduleDetailService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'排班子表明细','/dca/SdlBScheduleDetail/SdlBScheduleDetail','dca/SdlBScheduleDetail/SdlBScheduleDetail','sdlBScheduleDetail:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'排班子表明细新增','sdlBScheduleDetail:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'排班子表明细编辑','sdlBScheduleDetail:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'排班子表明细删除','sdlBScheduleDetail:delete',1,1,NOW())
 */


    /**
     * 分页查询数据
     *
     * @param request            分页信息
     * @param sdlBScheduleDetail 查询条件
     * @return
     */
    @GetMapping
    @RequiresPermissions("sdlBScheduleDetail:view")
    public Map<String, Object> List(QueryRequest request, SdlBScheduleDetail sdlBScheduleDetail) {
        return getDataTable(this.iSdlBScheduleDetailService.findSdlBScheduleDetails(request, sdlBScheduleDetail));
    }

    /**
     * 添加
     *
     * @param sdlBScheduleDetail
     * @return
     */
    @Log("新增/按钮")
    @PostMapping
    @RequiresPermissions("sdlBScheduleDetail:add")
    public void addSdlBScheduleDetail(@Valid SdlBScheduleDetail sdlBScheduleDetail) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            sdlBScheduleDetail.setCreateUserId(currentUser.getUserId());
            this.iSdlBScheduleDetailService.createSdlBScheduleDetail(sdlBScheduleDetail);
        } catch (Exception e) {
            message = "新增/按钮失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 修改
     *
     * @param sdlBScheduleDetail
     * @return
     */
    @Log("修改")
    @PutMapping
    @RequiresPermissions("sdlBScheduleDetail:update")
    public void updateSdlBScheduleDetail(@Valid SdlBScheduleDetail sdlBScheduleDetail) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            sdlBScheduleDetail.setModifyUserId(currentUser.getUserId());
            this.iSdlBScheduleDetailService.updateSdlBScheduleDetail(sdlBScheduleDetail);
        } catch (Exception e) {
            message = "修改失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @Log("删除")
    @DeleteMapping("/{ids}")
    @RequiresPermissions("sdlBScheduleDetail:delete")
    public void deleteSdlBScheduleDetails(@NotBlank(message = "{required}") @PathVariable String ids) throws FebsException {
        try {
            String[] arr_ids = ids.split(StringPool.COMMA);
            this.iSdlBScheduleDetailService.deleteSdlBScheduleDetails(arr_ids);
        } catch (Exception e) {
            message = "删除失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excel")
    @RequiresPermissions("sdlBScheduleDetail:export")
    public void export(QueryRequest request, SdlBScheduleDetail sdlBScheduleDetail, HttpServletResponse response) throws FebsException {
        try {
            List<SdlBScheduleDetail> sdlBScheduleDetails = this.iSdlBScheduleDetailService.findSdlBScheduleDetails(request, sdlBScheduleDetail).getRecords();
            ExcelKit.$Export(SdlBScheduleDetail.class, response).downXlsx(sdlBScheduleDetails, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @GetMapping("/{id}")
    public SdlBScheduleDetail detail(@NotBlank(message = "{required}") @PathVariable String id) {
        SdlBScheduleDetail sdlBScheduleDetail = this.iSdlBScheduleDetailService.getById(id);
        return sdlBScheduleDetail;
    }

    /**
     * 节假日考勤统计  （不包含夜班）
     * @param sdlBScheduleDetail
     * @return
     */
    @GetMapping("dept")
    public List<CustomData> deptStatistic(SdlBScheduleDetail sdlBScheduleDetail) {
        return getStatictid(sdlBScheduleDetail);
    }

    private List<CustomData> getStatictid(SdlBScheduleDetail sdlBScheduleDetail){
        List<CustomData> data = new ArrayList<>();
        List<CustomData> customDataList = this.iSdlBScheduleDetailService.findSdlBScheduleReport(sdlBScheduleDetail);
        Map<String, Double> mp = customDataList.stream().collect(
                Collectors.groupingBy(p->p.getDeptId()+"_"+p.getDeptName(), Collectors.summingDouble(p -> (p.getCishu() > 1 ? 1d : p.getCishu()))));
        mp.entrySet().forEach(entry -> {
            CustomData nc = new CustomData();
            nc.setDeptId(entry.getKey().substring(0,entry.getKey().indexOf("_")));
            nc.setDeptName(entry.getKey().substring(entry.getKey().indexOf("_")+1));
            nc.setCishu(entry.getValue());
            data.add(nc);
        });
        return  data;
    }
    @GetMapping("dept2")
    public List<CustomData> deptStatistic45(SdlBScheduleDetail sdlBScheduleDetail) {
        return getStatictid45(sdlBScheduleDetail);
    }

    private List<CustomData> getStatictid45(SdlBScheduleDetail sdlBScheduleDetail){
        List<CustomData> data = new ArrayList<>();
        List<CustomData> customDataList = this.iSdlBScheduleDetailService.findSdlBScheduleReport2(sdlBScheduleDetail);
        Map<String, Double> mp = customDataList.stream().collect(
                Collectors.groupingBy(p->p.getDeptId()+"_"+p.getDeptName(), Collectors.summingDouble(p -> (p.getCishu() > 1 ? 1d : p.getCishu()))));
        mp.entrySet().forEach(entry -> {
            CustomData nc = new CustomData();
            nc.setDeptId(entry.getKey().substring(0,entry.getKey().indexOf("_")));
            nc.setDeptName(entry.getKey().substring(entry.getKey().indexOf("_")+1));
            nc.setCishu(entry.getValue());
            data.add(nc);
        });
        return  data;
    }
    /**
     * 节假日考勤统计 子表
     * @param sdlBScheduleDetail
     * @return
     */
    @GetMapping("deptSub")
    public List<CustomData> deptStatisticSub(SdlBScheduleDetail sdlBScheduleDetail) {
        List<CustomData> data = new ArrayList<>();
        List<CustomData> customDataList = this.iSdlBScheduleDetailService.findSdlBScheduleReport(sdlBScheduleDetail);

        if(customDataList.size()>0) {
            //final String dept_name = customDataList.get(0).getDeptName();

            Map<String, Double> mp = customDataList.stream().collect(
                    Collectors.groupingBy(p -> p.getDeptName()+"_"+p.getDeptId()+"_"+p.getAccountId() + "_" + p.getAccountName(), Collectors.summingDouble(p -> (p.getCishu() > 1 ? 1d : p.getCishu()))));
            //for(String key in mp.entrySet())
            for (Map.Entry<String,Double> entry:mp.entrySet())
               {
                CustomData nc = new CustomData();
                String[] arr=entry.getKey().split("_");
                nc.setAccountId(arr[2]);
                nc.setAccountName(arr[3]);
                nc.setCishu(entry.getValue());
                nc.setDeptId(arr[1]);
                nc.setDeptName(arr[0]);
                data.add(nc);
            };
           //
        }
        data= data.stream().sorted(Comparator.comparing(CustomData::getDeptName)).collect(Collectors.toList());
        return data;
    }

    @GetMapping("deptSub2")
    public List<CustomData> deptStatisticSub45(SdlBScheduleDetail sdlBScheduleDetail) {
        List<CustomData> data = new ArrayList<>();
        List<CustomData> customDataList = this.iSdlBScheduleDetailService.findSdlBScheduleReport2(sdlBScheduleDetail);

        if(customDataList.size()>0) {
            //final String dept_name = customDataList.get(0).getDeptName();

            Map<String, Double> mp = customDataList.stream().collect(
                    Collectors.groupingBy(p -> p.getDeptName()+"_"+p.getDeptId()+"_"+p.getAccountId() + "_" + p.getAccountName(), Collectors.summingDouble(p -> (p.getCishu() > 1 ? 1d : p.getCishu()))));
            //for(String key in mp.entrySet())
            for (Map.Entry<String,Double> entry:mp.entrySet())
            {
                CustomData nc = new CustomData();
                String[] arr=entry.getKey().split("_");
                nc.setAccountId(arr[2]);
                nc.setAccountName(arr[3]);
                nc.setCishu(entry.getValue());
                nc.setDeptId(arr[1]);
                nc.setDeptName(arr[0]);
                data.add(nc);
            };
            //
        }
        data= data.stream().sorted(Comparator.comparing(CustomData::getDeptName)).collect(Collectors.toList());
        return data;
    }
    /**
     * 全院夜班统计
     * @param sdlBScheduleDetail
     * @return
     */
    @GetMapping("yeban")
    public List<CustomData> deptStatistic2(SdlBScheduleDetail sdlBScheduleDetail) {
//        if(StringUtils.isNotEmpty(sdlBScheduleDetail.getScheduleDateFrom())){
//            List<String> monthDays= DateUtil.getDays(sdlBScheduleDetail.getScheduleDateFrom());
//            sdlBScheduleDetail.setScheduleDateTo(monthDays.stream().map(p->"\'"+p+"\'").collect(Collectors.joining(",")));
//        }
        List<CustomData> customDataList = this.iSdlBScheduleDetailService.findYeBanReport(sdlBScheduleDetail);
        return customDataList;
    }

    /**
     * 全院夜班子表统计
     * @param sdlBScheduleDetail
     * @return
     */
    @GetMapping("yebanSub")
    public List<CustomData> deptStatistic3(SdlBScheduleDetail sdlBScheduleDetail) {
        List<CustomData> customDataList = this.iSdlBScheduleDetailService.findYeBanSubReport(sdlBScheduleDetail);
        return customDataList;
    }

    /**
     * 门诊值班费统计（不在法定节假日，且只取门诊上，门诊下）
     * @param sdlBScheduleDetail
     * @return
     */
    @GetMapping("menzhen")
    public List<CustomData> deptStatistic4(SdlBScheduleDetail sdlBScheduleDetail) {
        List<CustomData> customDataList = this.iSdlBScheduleDetailService.findMenZhenReport(sdlBScheduleDetail);
        return customDataList;
    }
    /**
     * 门诊值班费子表统计
     * @param sdlBScheduleDetail
     * @return
     */
    @GetMapping("menzhenSub")
    public List<CustomData> deptStatistic5(SdlBScheduleDetail sdlBScheduleDetail) {
        List<CustomData> customDataList = this.iSdlBScheduleDetailService.findMenZhenSubReport(sdlBScheduleDetail);
        return customDataList;
    }

    /**
     * 个人明细
     * @param sdlBScheduleDetail
     * @param flag  0是节假日排班  1是夜班 2.门诊班
     * @return
     */
    @GetMapping("accountDetail")
    public List<SdlBScheduleDetail> deptStatisticSub(SdlBScheduleDetail sdlBScheduleDetail,int flag) {
        List<SdlBScheduleDetail> customDataList= new ArrayList<>();
        if(flag==0) {
            customDataList = this.iSdlBScheduleDetailService.findSdlBScheduleReportAccount(sdlBScheduleDetail);
        }
        if(flag==4) {
            customDataList = this.iSdlBScheduleDetailService.findSdlBScheduleReportAccount2(sdlBScheduleDetail);
        }
        if(flag==1) {
            customDataList = this.iSdlBScheduleDetailService.findYeBanSubReportAccount(sdlBScheduleDetail);
        }
        if(flag==2) {
            customDataList = this.iSdlBScheduleDetailService.findMenZhenSubReportAccount(sdlBScheduleDetail);
        }

        return customDataList;
    }

    @PostMapping("deptExcel")
    public void deptExport(QueryRequest request, SdlBScheduleDetail sdlBScheduleDetail, HttpServletResponse response,int flag,String dataJson) throws FebsException {
        try {
            List<CustomData> customDataList =new ArrayList<>();
            if(flag==0){
                customDataList= getStatictid(sdlBScheduleDetail);
            }
            if(flag==4){
                customDataList= getStatictid45(sdlBScheduleDetail);
            }
            if(flag==1){
                customDataList= this.iSdlBScheduleDetailService.findYeBanReport(sdlBScheduleDetail);
            }
            if(flag==2){
                customDataList= this.iSdlBScheduleDetailService.findMenZhenReport(sdlBScheduleDetail);
            }
            ExportExcelUtils.exportCustomExcel_han(response, customDataList,dataJson,"");
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("deptExcelDetail")
    public void deptExportDetail(QueryRequest request, SdlBScheduleDetail sdlBScheduleDetail, HttpServletResponse response,int flag,String dataJson) throws FebsException {
        try {
            List<CustomData> customDataList =new ArrayList<>();
            if(flag==0){
                customDataList= deptStatisticSub(sdlBScheduleDetail);
            }
            if(flag==4){
                customDataList= deptStatisticSub45(sdlBScheduleDetail);
            }
            if(flag==1){
                customDataList= this.iSdlBScheduleDetailService.findYeBanSubReport(sdlBScheduleDetail);
            }
            if(flag==2){
                customDataList= this.iSdlBScheduleDetailService.findMenZhenSubReport(sdlBScheduleDetail);
            }
            ExportExcelUtils.exportCustomExcel_han(response, customDataList,dataJson,"");
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

}