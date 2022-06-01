
package cc.mrbird.febs.xxb.controller;


import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.FebsResponse;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.common.properties.FebsProperties;
import cc.mrbird.febs.export.pdf.XxbBPdfInfo;
import cc.mrbird.febs.scm.entity.ComFile;
import cc.mrbird.febs.scm.service.IComFileService;
import cc.mrbird.febs.xxb.entity.*;
import cc.mrbird.febs.xxb.service.IXxbBCheckService;
import cc.mrbird.febs.xxb.service.IXxbBResultDService;
import cc.mrbird.febs.xxb.service.IXxbBResultService;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
import cn.hutool.Hutool;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.beust.jcommander.internal.Lists;
import com.google.common.collect.ImmutableMap;
import com.wuwenze.poi.ExcelKit;
import com.wuwenze.poi.handler.ExcelReadHandler;
import com.wuwenze.poi.pojo.ExcelErrorField;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author viki
 * @since 2022-05-23
 */
@Slf4j
@Validated
@RestController
@RequestMapping("xxbBResult")

public class XxbBResultController extends BaseController {

    private String message;

    @Autowired
    FebsProperties febsProperties;
    @Autowired
    public IXxbBResultService iXxbBResultService;

    @Autowired
    IXxbBResultDService iXxbBResultDService;

    @Autowired
    IXxbBCheckService iXxbBCheckService;

    @Autowired
    IComFileService iComFileService;


/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/ass/XxbBResult/XxbBResult','ass/XxbBResult/XxbBResult','xxbBResult:view','fork',0,1,NOW());
 SELECT @maxId:=MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'新增','xxbBResult:add',1,1,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'编辑','xxbBResult:update',1,2,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'删除','xxbBResult:delete',1,3,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'导出','xxbBResult:export',1,4,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'导入','xxbBResult:import',1,5,NOW());
 */


    /**
     * 分页查询数据
     *
     * @param request    分页信息
     * @param xxbBResult 查询条件
     * @return
     */
    @GetMapping
    @RequiresPermissions("xxbBResult:view")
    public Map<String, Object> List(QueryRequest request, XxbBResult xxbBResult) {
        User currentUser = FebsUtil.getCurrentUser();
        return getDataTable(this.iXxbBResultService.findXxbBResults(request, xxbBResult, currentUser));
    }

    @GetMapping("flowList")
    @RequiresPermissions("xxbBResult:view")
    public Map<String, Object> flowList(QueryRequest request, XxbBResult xxbBResult) {
        User currentUser = FebsUtil.getCurrentUser();
        return getDataTable(this.iXxbBResultService.findXxbBResultflow(request, xxbBResult, currentUser));
    }

    @GetMapping("reportList")
    @RequiresPermissions("xxbBResult:view")
    public Map<String, Object> reportList(QueryRequest request, XxbBResult xxbBResult,boolean isDf) {
        return getDataTable(this.iXxbBResultService.findXxbBResultReports(request, xxbBResult,isDf));
    }

    @GetMapping("getId")
    public FebsResponse getId() {
        return new FebsResponse().data(UUID.randomUUID().toString());
    }

    @Log("新增/按钮")
    @PutMapping("editInfo")
    @RequiresPermissions("xxbBResult:update")
    public FebsResponse AddInfo(String data) {
        ModelMap map = new ModelMap();
        int success = 0;
        String id = "";
        try {
            JSONObject json = JSONObject.parseObject(data);
            XxbBResultInfo xxbBResultInfo = JSON.toJavaObject(json, XxbBResultInfo.class);
            User currentUser = FebsUtil.getCurrentUser();
            id = this.iXxbBResultService.editXxbBResult(xxbBResultInfo, currentUser);
            success = 1;
        } catch (Exception e) {
            message = "新增/按钮失败";
            log.error(message, e);
        }
        map.put("success", success);
        map.put("message", message);
        map.put("data", id);
        return new FebsResponse().data(map);
    }

    @PutMapping("editResultFlow")
    @RequiresPermissions("xxbBResult:update")
    public FebsResponse editDeptFlow(@Valid XxbBResultF resultFlow) {
        ModelMap map = new ModelMap();
        int success = 0;
        try {
            this.iXxbBResultService.updateResultFlow(resultFlow);
            success = 1;
        } catch (Exception e) {
            message = "修改失败";
            log.error(message, e);
        }
        map.put("success", success);
        map.put("message", message);
        return new FebsResponse().data(map);
    }

    @GetMapping("findResultFlow")
    @RequiresPermissions("xxbBResult:view")
    public FebsResponse findDeptFlow(String baseId, int state) {
        ModelMap map = new ModelMap();
        int success = 0;
        List<XxbBResultF> xxbBResultFList = new ArrayList<>();
        try {
            User currentUser = FebsUtil.getCurrentUser();
            xxbBResultFList = this.iXxbBResultService.getResultFList(baseId, currentUser, state);
            success = 1;
        } catch (Exception e) {
            message = "查询失败.";
            log.error(message, e);
        }

        map.put("success", success);
        map.put("message", message);
        map.put("data", xxbBResultFList);
        return new FebsResponse().data(map);
    }

    @GetMapping("findResultP")
    @RequiresPermissions("xxbBResult:view")
    public FebsResponse findResultP(String baseId, Integer state) {
        ModelMap map = new ModelMap();
        int success = 0;
        List<XxbBResultP> xxbBResultPList = new ArrayList<>();
        try {
            xxbBResultPList = this.iXxbBResultService.getResultPList(baseId, state);
            success = 1;
        } catch (Exception e) {
            message = "查询失败.";
            log.error(message, e);
        }

        map.put("success", success);
        map.put("message", message);
        map.put("data", xxbBResultPList);
        return new FebsResponse().data(map);
    }

    @GetMapping("findUserCheck")
    @RequiresPermissions("xxbBResult:view")
    public FebsResponse findUserCheck(String projectName) {
        ModelMap map = new ModelMap();
        int success = 0;
        List<XxbBCheck> xxbBCheckList = new ArrayList<>();
        try {
            User currentUser = FebsUtil.getCurrentUser();
            xxbBCheckList = iXxbBCheckService.findUserCreateCheck(projectName, currentUser);
            success = 1;
        } catch (Exception e) {
            message = "查询失败.";
            log.error(message, e);
        }

        map.put("success", success);
        map.put("message", message);
        map.put("data", xxbBCheckList);
        return new FebsResponse().data(map);
    }

    @GetMapping("selectProjectUserCheckD")
    @RequiresPermissions("xxbBResult:view")
    public FebsResponse selectProjectCheck(String id) {
        ModelMap map = new ModelMap();
        int success = 0;
        List<XxbBCheckD> list = new ArrayList<>();
        try {
            list = iXxbBCheckService.selectUserCheckD(id);
            success = 1;
        } catch (Exception e) {
            message = "查询失败.";
            log.error(message, e);
        }

        map.put("success", success);
        map.put("message", message);
        map.put("data", list);
        return new FebsResponse().data(map);
    }

    @GetMapping("findResultD")
    @RequiresPermissions("xxbBResult:view")
    public FebsResponse findResultD(String baseId) {
        ModelMap map = new ModelMap();
        int success = 0;
        List<XxbBResultD> xxbBResultDataList = new ArrayList<>();
        try {
//            User currentUser = FebsUtil.getCurrentUser();
            xxbBResultDataList = this.iXxbBResultService.getResultDataList(baseId);
            xxbBResultDataList = xxbBResultDataList.stream().sorted(Comparator.comparing(XxbBResultD::getDisplayIndex)).collect(Collectors.toList());
            success = 1;
        } catch (Exception e) {
            message = "查询失败.";
            log.error(message, e);
        }

        map.put("success", success);
        map.put("message", message);
        map.put("data", xxbBResultDataList);
        return new FebsResponse().data(map);
    }

    @GetMapping("delResultD")
    @RequiresPermissions("xxbBResult:delete")
    public FebsResponse getDelId(String id) {
        ModelMap map = new ModelMap();
        int success = 0;
        try {
            this.iXxbBResultService.deleteResultData(id);
            success = 1;
        } catch (Exception e) {
            message = "删除失败.";
            log.error(message, e);
        }
        map.put("success", success);
        map.put("message", message);
        return new FebsResponse().data(map);
    }

    @PostMapping("uploadGlFile")
    public FebsResponse UploadFile(@RequestParam("file") MultipartFile file, String ids) throws FebsException {
        int success = 0;
        message = "";
        String[] arr_ids = ids.split(StringPool.COMMA);
        String filename = file.getOriginalFilename();
        if (!StringUtils.endsWith(filename, ".xlsx")) {
            throw new FebsException("只支持.xlsx类型文件上传");
        }
        ModelMap map = new ModelMap();
        LambdaQueryWrapper<XxbBResult> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(XxbBResult::getId, arr_ids);

        List<XxbBResult> resultList = iXxbBResultService.list(wrapper);

        String suffixName = filename.substring(filename.lastIndexOf("."));  // 后缀名

        String filePath = febsProperties.getUploadPath(); // 上传后的路径
        String serName = UUID.randomUUID().toString() + suffixName;
        String url = "xxbresult" + "/" + serName;
        File dest = new File(filePath + url);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            List<XxbBResult> updateList = new ArrayList<>();
            for(XxbBResult item : resultList) {
                XxbBResult update = new XxbBResult();
                update.setId(item.getId());
                update.setFileurl(url);
                update.setFilename(filename);
                updateList.add(update);
            }
            this.iXxbBResultService.updateBatchById(updateList);
            success = 1;
        } catch (IOException e) {
            message = "上传关联文件失败," + e.getMessage();
            log.error(message);
            throw new FebsException(message);
        }

        map.put("success", success);
        map.put("name", filename);
        map.put("message", message);
        return new FebsResponse().put("data", map);
    }


    @PostMapping("resultExcel")
    public void resultExcelTemplate(HttpServletResponse response, String ids) {
        String[] arr_ids = ids.split(StringPool.COMMA);
        // 构建数据
        List<XxbBResultExcel> list = new ArrayList<>();
        LambdaQueryWrapper<XxbBResult> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(XxbBResult::getId, arr_ids);
        List<XxbBResult> resultList = iXxbBResultService.list(wrapper);

        LambdaQueryWrapper<XxbBResultD> wrapperD = new LambdaQueryWrapper<>();
        wrapperD.in(XxbBResultD::getPid, arr_ids);
        List<XxbBResultD> resultDList = iXxbBResultDService.list(wrapperD);

        List<XxbBResultD> queryDList = new ArrayList<>();
        int count = 1;
        for (XxbBResult item : resultList) {
            count = 1;
            XxbBResultExcel excel = new XxbBResultExcel();
            excel.setId(item.getId());
            excel.setPfnd(item.getPfnd());
            excel.setYuanqu(item.getYuanqu());
            excel.setDeptNew(item.getDeptNew());
            excel.setUserAccountName(item.getUserAccountName());
            excel.setUserAccount(item.getUserAccount());
            excel.setZhichenglc(item.getZhichenglc());
            excel.setProjectName(item.getProjectName());
            excel.setYwtotal(item.getYwtotal());
            excel.setYntotal(item.getYntotal());
            excel.setAbtotal(item.getAbtotal());
            excel.setHjqk(item.getHjqk());
            excel.setFilename(item.getFilename());
            queryDList = resultDList.stream().filter(s -> s.getPid().equals(item.getId())).collect(Collectors.toList());
            if (queryDList.size() > 0) {
                queryDList.sort(Comparator.comparing(XxbBResultD::getDisplayIndex));
                for (XxbBResultD rd : queryDList) {
                    if (count == 1) {
                        excel.setCy1(rd.getUserAccountName() + " " + rd.getUserAccount() + " " + rd.getZhichenglc());
                    } else if (count == 2) {
                        excel.setCy2(rd.getUserAccountName() + " " + rd.getUserAccount() + " " + rd.getZhichenglc());
                    } else if (count == 3) {
                        excel.setCy3(rd.getUserAccountName() + " " + rd.getUserAccount() + " " + rd.getZhichenglc());
                    } else if (count == 4) {
                        excel.setCy4(rd.getUserAccountName() + " " + rd.getUserAccount() + " " + rd.getZhichenglc());
                    } else if (count == 5) {
                        excel.setCy5(rd.getUserAccountName() + " " + rd.getUserAccount() + " " + rd.getZhichenglc());
                    } else if (count == 6) {
                        excel.setCy6(rd.getUserAccountName() + " " + rd.getUserAccount() + " " + rd.getZhichenglc());
                    }
                    count += 1;
                }
            }
            list.add(excel);
        }
        // 构建模板
        ExcelKit.$Export(XxbBResultExcel.class, response).downXlsx(list, true);
    }

    @PostMapping("resultImport")
    public FebsResponse importExcels(@RequestParam("file") MultipartFile file) throws FebsException {
        int success = 0;
        try {
            if (file.isEmpty()) {
                throw new FebsException("导入数据为空");
            }
            String filename = file.getOriginalFilename();
            if (!StringUtils.endsWith(filename, ".xlsx")) {
                throw new FebsException("只支持.xlsx类型文件导入");
            }
            // 开始导入操作
            long beginTimeMillis = System.currentTimeMillis();
            final List<XxbBResultExcel> data = Lists.newArrayList();
            final List<String> idList = Lists.newArrayList();
            final List<Map<String, Object>> error = Lists.newArrayList();
            ExcelKit.$Import(XxbBResultExcel.class).readXlsx(file.getInputStream(), new ExcelReadHandler<XxbBResultExcel>() {
                @Override
                public void onSuccess(int sheet, int row, XxbBResultExcel excel) {
                    // 数据校验成功时，加入集合
                    if (!idList.contains(excel.getId())) {
                        idList.add(excel.getId());
                    }
                    data.add(excel);
                }

                @Override
                public void onError(int sheet, int row, List<ExcelErrorField> errorFields) {
                    // 数据校验失败时，记录到 error集合
                    error.add(ImmutableMap.of("row", row, "errorFields", errorFields));
                }
            });
            if (!data.isEmpty()) {
                // 将合法的记录批量入库
                LambdaQueryWrapper<XxbBResult> wrapper = new LambdaQueryWrapper<>();
                wrapper.in(XxbBResult::getId, idList);
                List<XxbBResult> resultList = iXxbBResultService.list(wrapper);
                List<XxbBResult> updateList = new ArrayList<>();
                if (resultList.size() > 0) {
                    List<XxbBResultExcel> queryList = Lists.newArrayList();
                    for (XxbBResult result : resultList) {
                        queryList = data.stream().filter(s -> StringUtils.isNotBlank(s.getId()) && s.getId().equals(result.getId())).collect(Collectors.toList());
                        if (queryList.size() > 0) {
                            XxbBResultExcel e = queryList.get(0);
                            XxbBResult update = new XxbBResult();
                            update.setId(result.getId());
                            update.setPfnd(e.getPfnd());
                            update.setHjqk(e.getHjqk());
                            updateList.add(update);
                        }
                    }
                }
                if (updateList.size() > 0) {
                    iXxbBResultService.updateBatchById(updateList);
                    success = 1;
                }
            }
            long time = ((System.currentTimeMillis() - beginTimeMillis));
            ImmutableMap<String, Object> result = ImmutableMap.of(
                    "time", time,
                    "data", data,
                    "message", error,
                    "success", success
            );
            return new FebsResponse().data(result);
        } catch (Exception e) {
            message = "导入Excel数据失败," + e.getMessage();
            log.error(message);
            throw new FebsException(message);
        }
    }

    @PostMapping("resultYwExcel")
    public void resultYwExcelTemplate(HttpServletResponse response, String ids) {
        String[] arr_ids = ids.split(StringPool.COMMA);
        // 构建数据
        List<XxbBResultPYwExcel> list = new ArrayList<>();
        LambdaQueryWrapper<XxbBResult> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(XxbBResult::getId, arr_ids);
        List<XxbBResult> resultList = iXxbBResultService.list(wrapper);

        for (XxbBResult item : resultList) {
            XxbBResultPYwExcel excel = new XxbBResultPYwExcel();
            excel.setId(item.getId());
            excel.setYuanqu(item.getYuanqu());
            excel.setDeptNew(item.getDeptNew());
            excel.setUserAccountName(item.getUserAccountName());
            excel.setUserAccount(item.getUserAccount());
            excel.setZhichenglc(item.getZhichenglc());
            excel.setProjectName(item.getProjectName());

            for (int i = 0; i < 3; i++) {
                list.add(excel);
            }
        }
        // 构建模板
        ExcelKit.$Export(XxbBResultPYwExcel.class, response).downXlsx(list, true);
    }

    @PostMapping("resultYwImport")
    public FebsResponse resultYwImports(@RequestParam("file") MultipartFile file) throws FebsException {
        int success = 0;
        try {
            if (file.isEmpty()) {
                throw new FebsException("导入数据为空");
            }
            String filename = file.getOriginalFilename();
            if (!StringUtils.endsWith(filename, ".xlsx")) {
                throw new FebsException("只支持.xlsx类型文件导入");
            }
            // 开始导入操作
            long beginTimeMillis = System.currentTimeMillis();
            final List<XxbBResultPYwExcel> data = Lists.newArrayList();
            final List<String> idList = Lists.newArrayList();
            final List<Map<String, Object>> error = Lists.newArrayList();
            ExcelKit.$Import(XxbBResultPYwExcel.class).readXlsx(file.getInputStream(), new ExcelReadHandler<XxbBResultPYwExcel>() {
                @Override
                public void onSuccess(int sheet, int row, XxbBResultPYwExcel excel) {
                    // 数据校验成功时，加入集合
                    if (!idList.contains(excel.getId())) {
                        idList.add(excel.getId());
                    }
                    if(excel.getCxx()==null) {
                        excel.setCxx(0d);
                    }
                    if(excel.getSyx()==null) {
                        excel.setSyx(0d);
                    }
                    if(excel.getJsnd()==null) {
                        excel.setJsnd(0d);
                    }
                    if(excel.getXyx()==null) {
                        excel.setXyx(0d);
                    }
                    if(excel.getTotal()==null) {
                        excel.setTotal(0d);
                    }
                    data.add(excel);
                }

                @Override
                public void onError(int sheet, int row, List<ExcelErrorField> errorFields) {
                    // 数据校验失败时，记录到 error集合
                    error.add(ImmutableMap.of("row", row, "errorFields", errorFields));
                }
            });
            if (!data.isEmpty()) {
                // 将合法的记录批量入库
                LambdaQueryWrapper<XxbBResult> wrapper = new LambdaQueryWrapper<>();
                wrapper.in(XxbBResult::getId, idList);
                List<XxbBResult> resultList = iXxbBResultService.list(wrapper);
                List<XxbBResult> updateList = new ArrayList<>();
                List<XxbBResultP> editPList = new ArrayList<>();
                List<String> ridList = Lists.newArrayList();
                Double sum = 0d;
                int state = 1;
                if (resultList.size() > 0) {
                    List<XxbBResultPYwExcel> queryList = Lists.newArrayList();
                    Date thisDate = new Date();
                    for (XxbBResult result : resultList) {
                        queryList = data.stream().filter(s -> StringUtils.isNotBlank(s.getId()) && s.getId().equals(result.getId())).collect(Collectors.toList());
                        if (queryList.size() > 0) {
                            sum = 0d;
                            ridList.add(result.getId());
                            Double cxx = queryList.stream().mapToDouble(XxbBResultPYwExcel::getCxx).sum();
                            Double syx = queryList.stream().mapToDouble(XxbBResultPYwExcel::getSyx).sum();
                            Double jsnd = queryList.stream().mapToDouble(XxbBResultPYwExcel::getJsnd).sum();
                            Double xyx = queryList.stream().mapToDouble(XxbBResultPYwExcel::getXyx).sum();

                            cxx = cxx / queryList.size();
                            syx = syx / queryList.size();
                            jsnd = jsnd / queryList.size();
                            xyx = xyx / queryList.size();
                            sum = cxx + syx + jsnd + xyx;
                            XxbBResult update = new XxbBResult();
                            update.setId(result.getId());
                            update.setImportdat(thisDate);
                            update.setYwtotal(sum);
                            if(result.getYntotal() == null) {
                                result.setYntotal(0d);
                            }
                            update.setAbtotal(update.getYwtotal() * 0.5 + result.getYntotal() * 0.5);
                            updateList.add(update);

                            for (XxbBResultPYwExcel pexcel : queryList) {
                                XxbBResultP edit = new XxbBResultP();
                                edit.setId(UUID.randomUUID().toString());
                                edit.setPid(result.getId());
                                edit.setDfname(pexcel.getDfname());
                                edit.setDfwork(pexcel.getDfwork());
                                edit.setDfzhic(pexcel.getDfzhic());
                                edit.setCxx(pexcel.getCxx());
                                edit.setSyx(pexcel.getSyx());
                                edit.setJsnd(pexcel.getJsnd());
                                edit.setXyx(pexcel.getXyx());
                                edit.setTotal(edit.getCxx() + edit.getSyx() + edit.getJsnd() + edit.getXyx());
                                edit.setIsDeletemark(1);
                                edit.setState(state);
                                edit.setCreateTime(thisDate);
                                editPList.add(edit);
                            }
                        }
                    }
                } else {
                    throw new FebsException("未查询到数据");
                }

                if (ridList.size() > 0) {
                    iXxbBResultService.updateResultReportPf(ridList, updateList, editPList, state);
                    success=1;
                } else {
                    throw new FebsException("无数据更新.");
                }
            }
            long time = ((System.currentTimeMillis() - beginTimeMillis));
            ImmutableMap<String, Object> result = ImmutableMap.of(
                    "time", time,
                    "data", data,
                    "message", error,
                    "success",success
            );
            return new FebsResponse().data(result);
        } catch (Exception e) {
            message = "导入Excel数据失败," + e.getMessage();
            log.error(message);
            throw new FebsException(message);
        }
    }

    @PostMapping("resultYnExcel")
    public void resultYnExcelTemplate(HttpServletResponse response, String ids) {
        String[] arr_ids = ids.split(StringPool.COMMA);
        // 构建数据
        List<XxbBResultPYnExcel> list = new ArrayList<>();
        LambdaQueryWrapper<XxbBResult> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(XxbBResult::getId, arr_ids);
        List<XxbBResult> resultList = iXxbBResultService.list(wrapper);

        for (XxbBResult item : resultList) {
            XxbBResultPYnExcel excel = new XxbBResultPYnExcel();
            excel.setId(item.getId());
            excel.setYuanqu(item.getYuanqu());
            excel.setDeptNew(item.getDeptNew());
            excel.setUserAccountName(item.getUserAccountName());
            excel.setUserAccount(item.getUserAccount());
            excel.setZhichenglc(item.getZhichenglc());
            excel.setProjectName(item.getProjectName());

            for (int i = 0; i < 3; i++) {
                list.add(excel);
            }
        }
        // 构建模板
        ExcelKit.$Export(XxbBResultPYnExcel.class, response).downXlsx(list, true);
    }

    @PostMapping("resultYnImport")
    public FebsResponse resultYnImports(@RequestParam("file") MultipartFile file) throws FebsException {
        int success = 0;
        try {
            if (file.isEmpty()) {
                throw new FebsException("导入数据为空");
            }
            String filename = file.getOriginalFilename();
            if (!StringUtils.endsWith(filename, ".xlsx")) {
                throw new FebsException("只支持.xlsx类型文件导入");
            }
            // 开始导入操作
            long beginTimeMillis = System.currentTimeMillis();
            final List<XxbBResultPYnExcel> data = Lists.newArrayList();
            final List<String> idList = Lists.newArrayList();
            final List<Map<String, Object>> error = Lists.newArrayList();
            ExcelKit.$Import(XxbBResultPYnExcel.class).readXlsx(file.getInputStream(), new ExcelReadHandler<XxbBResultPYnExcel>() {
                @Override
                public void onSuccess(int sheet, int row, XxbBResultPYnExcel excel) {
                    // 数据校验成功时，加入集合
                    if (!idList.contains(excel.getId())) {
                        idList.add(excel.getId());
                    }if(excel.getCxx()==null) {
                        excel.setCxx(0d);
                    }
                    if(excel.getSyx()==null) {
                        excel.setSyx(0d);
                    }
                    if(excel.getJsnd()==null) {
                        excel.setJsnd(0d);
                    }
                    if(excel.getXyx()==null) {
                        excel.setXyx(0d);
                    }
                    if(excel.getTotal()==null) {
                        excel.setTotal(0d);
                    }
                    data.add(excel);
                }

                @Override
                public void onError(int sheet, int row, List<ExcelErrorField> errorFields) {
                    // 数据校验失败时，记录到 error集合
                    error.add(ImmutableMap.of("row", row, "errorFields", errorFields));
                }
            });
            if (!data.isEmpty()) {
                // 将合法的记录批量入库
                LambdaQueryWrapper<XxbBResult> wrapper = new LambdaQueryWrapper<>();
                wrapper.in(XxbBResult::getId, idList);
                List<XxbBResult> resultList = iXxbBResultService.list(wrapper);
                List<XxbBResult> updateList = new ArrayList<>();
                List<XxbBResultP> editPList = new ArrayList<>();
                List<String> ridList = Lists.newArrayList();
                int state = 2;
                if (resultList.size() > 0) {
                    List<XxbBResultPYnExcel> queryList = Lists.newArrayList();
                    Date thisDate = new Date();
                    for (XxbBResult result : resultList) {
                        queryList = data.stream().filter(s -> StringUtils.isNotBlank(s.getId()) && s.getId().equals(result.getId())).collect(Collectors.toList());
                        if (queryList.size() > 0) {
                            Optional<XxbBResultPYnExcel> cxxMinExcel = queryList.stream().min((e10, e11) -> Double.compare(e10.getCxx(), e11.getCxx()));
                            Optional<XxbBResultPYnExcel> cxxMaxExcel = queryList.stream().max((e10, e11) -> Double.compare(e10.getCxx(), e11.getCxx()));
                            Optional<XxbBResultPYnExcel> syxMinExcel = queryList.stream().min((e10, e11) -> Double.compare(e10.getSyx(), e11.getSyx()));
                            Optional<XxbBResultPYnExcel> syxMaxExcel = queryList.stream().max((e10, e11) -> Double.compare(e10.getSyx(), e11.getSyx()));
                            Optional<XxbBResultPYnExcel> jsndMinExcel = queryList.stream().min((e10, e11) -> Double.compare(e10.getJsnd(), e11.getJsnd()));
                            Optional<XxbBResultPYnExcel> jsndMaxExcel = queryList.stream().max((e10, e11) -> Double.compare(e10.getJsnd(), e11.getJsnd()));
                            Optional<XxbBResultPYnExcel> xyxMinExcel = queryList.stream().min((e10, e11) -> Double.compare(e10.getXyx(), e11.getXyx()));
                            Optional<XxbBResultPYnExcel> xyxMaxExcel = queryList.stream().max((e10, e11) -> Double.compare(e10.getXyx(), e11.getXyx()));

                            Double cxxMin = cxxMinExcel.get().getCxx();
                            Double cxxMax = cxxMaxExcel.get().getCxx();
                            Double syxMin = syxMinExcel.get().getSyx();
                            Double syxMax = syxMaxExcel.get().getSyx();
                            Double jsndMin = jsndMinExcel.get().getJsnd();
                            Double jsndMax = jsndMaxExcel.get().getJsnd();
                            Double xyxMin = xyxMinExcel.get().getXyx();
                            Double xyxMax = xyxMaxExcel.get().getXyx();

                            ridList.add(result.getId());

                            Double sum = 0d;
                            Double cxx = queryList.stream().mapToDouble(XxbBResultPYnExcel::getCxx).sum();
                            Double syx = queryList.stream().mapToDouble(XxbBResultPYnExcel::getSyx).sum();
                            Double jsnd = queryList.stream().mapToDouble(XxbBResultPYnExcel::getJsnd).sum();
                            Double xyx = queryList.stream().mapToDouble(XxbBResultPYnExcel::getXyx).sum();

                            int count = queryList.size();

                            if (count > 2) {
                                count = count - 2;

                                cxx = cxx - cxxMin - cxxMax;
                                cxx = cxx / count;

                                syx = syx - syxMin - syxMax;
                                syx = syx / count;

                                jsnd = jsnd - jsndMin - jsndMax;
                                jsnd = jsnd / count;

                                xyx = xyx - xyxMin - xyxMax;
                                xyx = xyx / count;

                                sum = cxx + syx + jsnd + xyx;
                            } else {
                                cxx = 0d;
                                syx = 0d;
                                jsnd = 0d;
                                xyx = 0d;
                                sum = 0d;
                            }
                            XxbBResult update = new XxbBResult();
                            update.setId(result.getId());
                            update.setImportdat(thisDate);
                            update.setYntotal(sum);
                            if(result.getYwtotal() == null) {
                                result.setYwtotal(0d);
                            }
                            update.setAbtotal(update.getYntotal() * 0.5 + result.getYwtotal() * 0.5);
                            updateList.add(update);

                            for (XxbBResultPYnExcel pexcel : queryList) {
                                XxbBResultP edit = new XxbBResultP();
                                edit.setId(UUID.randomUUID().toString());
                                edit.setPid(result.getId());
                                edit.setDfname(pexcel.getDfname());
                                edit.setDfwork(pexcel.getDfwork());
                                edit.setDfzhic(pexcel.getDfzhic());
                                edit.setCxx(pexcel.getCxx());
                                edit.setSyx(pexcel.getSyx());
                                edit.setJsnd(pexcel.getJsnd());
                                edit.setXyx(pexcel.getXyx());
                                edit.setTotal(edit.getCxx() + edit.getSyx() + edit.getJsnd() + edit.getXyx());
                                edit.setIsDeletemark(1);
                                edit.setState(state);
                                edit.setCreateTime(thisDate);
                                editPList.add(edit);
                            }
                        }
                    }
                }

                if (ridList.size() > 0) {
                    iXxbBResultService.updateResultReportPf(ridList, updateList, editPList, state);
                    success = 1;
                }
            }
            long time = ((System.currentTimeMillis() - beginTimeMillis));
            ImmutableMap<String, Object> result = ImmutableMap.of(
                    "time", time,
                    "data", data,
                    "message", error,
                    "success", success
            );
            return new FebsResponse().data(result);
        } catch (Exception e) {
            message = "导入Excel数据失败," + e.getMessage();
            log.error(message);
            throw new FebsException(message);
        }
    }

    @PostMapping("downXmkzqkTemplate")
    public void downFile(HttpServletResponse response) throws FebsException {
        try {
            String path = febsProperties.getUploadPath();
            String fileName = "新技术新业务临床应用病例汇总表.xlsx";
            String filePath = path + fileName;
            this.downFile(response, filePath, fileName, false);
        } catch (Exception e) {
            message = "下载失败.";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("downloadFile")
    public void findFiles(QueryRequest request, String id, HttpServletResponse response) throws Exception {
        String filePath = febsProperties.getUploadPath(); // 上传后的路径
        String fileName = filePath + "down/" + UUID.randomUUID().toString() + ".pdf";
        String name = UUID.randomUUID().toString() + ".pdf";
        try {
            File dest = new File(filePath + "down");
            if (!dest.exists()) {
                dest.mkdirs();
            }
            XxbBResult xxbBResult = this.iXxbBResultService.getById(id);
            if (xxbBResult != null) {
                XxbBPdfInfo pdf = new XxbBPdfInfo();
                LambdaQueryWrapper<XxbBResultD> renyWrapper = new LambdaQueryWrapper<>();
                renyWrapper.eq(XxbBResultD::getPid, xxbBResult.getId());
                List<XxbBResultD> renyList = iXxbBResultDService.list(renyWrapper);

                pdf.writeXxbResultPdf(fileName, xxbBResult, renyList);

                this.downFile(response, fileName, name, true);
            }
        } catch (Exception e) {
            message = "下载失败.";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excelFile")
    public void excelFiles(QueryRequest request, String id, HttpServletResponse response) throws Exception {
        String filePath = febsProperties.getUploadPath(); // 上传后的路径
        try {
            XxbBResult xxbBResult = this.iXxbBResultService.getById(id);
            if (xxbBResult != null) {
                LambdaQueryWrapper<ComFile> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(ComFile::getIsDeletemark, 1);
                wrapper.eq(ComFile::getRefTabId, xxbBResult.getId());
                wrapper.eq(ComFile::getRefTabTable, "xxbresult_xmkzqk");
                List<ComFile> excelList = iComFileService.list(wrapper);
                if (excelList.size() > 0) {
                    ComFile cf = excelList.get(0);
                    filePath += cf.getServerName();
                    this.downFile(response, filePath, cf.getClientName(), false);
                }
            }
        } catch (Exception e) {
            message = "下载失败.";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excelGlFile")
    public void excelGlFiles(QueryRequest request, String id, HttpServletResponse response) throws Exception {
        String filePath = febsProperties.getUploadPath(); // 上传后的路径
        try {
            XxbBResult xxbBResult = this.iXxbBResultService.getById(id);
            if (xxbBResult != null) {
                filePath += xxbBResult.getFileurl();
                this.downFile(response, filePath, xxbBResult.getFilename(), false);
            }
        } catch (Exception e) {
            message = "下载失败.";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    private void downFile(HttpServletResponse response, String filePath, String fileName, boolean isDel) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                InputStream ins = new FileInputStream(filePath);
                BufferedInputStream bins = new BufferedInputStream(ins);// 放到缓冲流里面
                OutputStream outs = response.getOutputStream();// 获取文件输出IO流
                BufferedOutputStream bouts = new BufferedOutputStream(outs);
                response.setHeader("Content-Disposition",
                        "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));

                int bytesRead = 0;
                byte[] buffer = new byte[512];
                //开始向网络传输文件流
                while ((bytesRead = bins.read(buffer, 0, 512)) != -1) {
                    bouts.write(buffer, 0, bytesRead);
                }
                bouts.flush();// 这里一定要调用flush()方法
                ins.close();
                bins.close();
                outs.close();
                bouts.close();
            } else {
//                response.sendRedirect("../error.jsp");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (isDel) {
                this.deleteFile(filePath);
            }
        }
    }

    public boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
                flag = true;
            }
        } else {
            flag = true;
        }
        return flag;
    }

    /**
     * 添加
     *
     * @param xxbBResult
     * @return
     */
    @Log("新增/按钮")
    @PostMapping
    @RequiresPermissions("xxbBResult:add")
    public void addXxbBResult(@Valid XxbBResult xxbBResult) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            xxbBResult.setCreateUserId(currentUser.getUserId());
            this.iXxbBResultService.createXxbBResult(xxbBResult);
        } catch (Exception e) {
            message = "新增/按钮失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 修改
     *
     * @param xxbBResult
     * @return
     */
    @Log("修改")
    @PutMapping
    @RequiresPermissions("xxbBResult:update")
    public void updateXxbBResult(@Valid XxbBResult xxbBResult) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            xxbBResult.setModifyUserId(currentUser.getUserId());
            this.iXxbBResultService.updateXxbBResult(xxbBResult);
        } catch (Exception e) {
            message = "修改失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @Log("删除")
    @DeleteMapping("/{ids}")
    @RequiresPermissions("xxbBResult:delete")
    public void deleteXxbBResults(@NotBlank(message = "{required}") @PathVariable String ids) throws FebsException {
        try {
            String[] arr_ids = ids.split(StringPool.COMMA);
            this.iXxbBResultService.deleteXxbBResults(arr_ids);
        } catch (Exception e) {
            message = "删除失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excel")
    @RequiresPermissions("xxbBResult:export")
    public void export(QueryRequest request, XxbBResult xxbBResult, HttpServletResponse response) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            List<XxbBResult> xxbBResults = this.iXxbBResultService.findXxbBResults(request, xxbBResult, currentUser).getRecords();
            ExcelKit.$Export(XxbBResult.class, response).downXlsx(xxbBResults, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @RequestMapping(value = "downTemplate", method = RequestMethod.POST)
    @RequiresPermissions("xxbBResult:import")
    public void downTemplate(HttpServletResponse response) {
        List<XxbBResult> publishList = new ArrayList<>();
        ExcelKit.$Export(XxbBResult.class, response).downXlsx(publishList, true);
    }

    @RequestMapping(value = "import", method = RequestMethod.POST)
    @RequiresPermissions("xxbBResult:import")
    public ResponseEntity<?> importUser(@RequestParam MultipartFile file)
            throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<XxbBResult> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        List<Map<String, Object>> resultList = Lists.newArrayList();

        User currentUser = FebsUtil.getCurrentUser();


        ExcelKit.$Import(XxbBResult.class)
                .readXlsx(file.getInputStream(), new ExcelReadHandler<XxbBResult>() {

                    @Override
                    public void onSuccess(int sheetIndex, int rowIndex, XxbBResult entity) {
                        successList.add(entity); // 单行读取成功，加入入库队列。
                    }

                    @Override
                    public void onError(int sheetIndex, int rowIndex,
                                        java.util.List<ExcelErrorField> errorFields) {
                        // 读取数据失败，记录了当前行所有失败的数据
                        errorList.add(MapUtil.of("sheetIndex", sheetIndex));
                        errorList.add(MapUtil.of("rowIndex", rowIndex));
                        errorList.add(MapUtil.of("errorFields", errorFields));
                    }
                });

        // TODO: 执行successList的入库操作。
        if (CollectionUtil.isEmpty(errorList)) {
            for (XxbBResult xxbBResultImport : successList
            ) {
                XxbBResult xxbBResult = new XxbBResult();
                BeanUtil.copyProperties(xxbBResultImport, xxbBResult, CopyOptions.create().setIgnoreNullValue(true));
                this.iXxbBResultService.createXxbBResult(xxbBResult);
            }
        }

        resultList.add(MapUtil.of("data", successList));
        resultList.add(MapUtil.of("haveError", !CollectionUtil.isEmpty(errorList)));
        resultList.add(MapUtil.of("error", errorList));
        resultList.add(MapUtil.of("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L));
        return ResponseEntity.ok(resultList);
    }

    @GetMapping("/{id}")
    public XxbBResult detail(@NotBlank(message = "{required}") @PathVariable String id) {
        XxbBResult xxbBResult = this.iXxbBResultService.getById(id);
        return xxbBResult;
    }
}