
package cc.mrbird.febs.xxb.controller;


import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.FebsResponse;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.common.properties.FebsProperties;
import cc.mrbird.febs.xxb.entity.*;
import cc.mrbird.febs.xxb.service.IXxbBCheckService;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.beust.jcommander.internal.Lists;
import com.wuwenze.poi.ExcelKit;
import com.wuwenze.poi.handler.ExcelReadHandler;
import com.wuwenze.poi.pojo.ExcelErrorField;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author viki
 * @since 2022-03-29
 */
@Slf4j
@Validated
@RestController
@RequestMapping("xxbBCheck")

public class XxbBCheckController extends BaseController {

    private String message;
    @Autowired
    public IXxbBCheckService iXxbBCheckService;
    @Autowired
    private FebsProperties febsProperties;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/ass/XxbBCheck/XxbBCheck','ass/XxbBCheck/XxbBCheck','xxbBCheck:view','fork',0,1,NOW());
 SELECT @maxId:=MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'新增','xxbBCheck:add',1,1,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'编辑','xxbBCheck:update',1,2,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'删除','xxbBCheck:delete',1,3,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'导出','xxbBCheck:export',1,4,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'导入','xxbBCheck:import',1,5,NOW());
 */


    /**
     * 分页查询数据
     *
     * @param request   分页信息
     * @param xxbBCheck 查询条件
     * @return
     */
    @GetMapping
    @RequiresPermissions("xxbBCheck:view")
    public Map<String, Object> List(QueryRequest request, XxbBCheck xxbBCheck) {
        User currentUser = FebsUtil.getCurrentUser();
        return getDataTable(this.iXxbBCheckService.findXxbBChecks(request, xxbBCheck, currentUser));
    }

    @GetMapping("flowList")
    @RequiresPermissions("xxbBCheck:view")
    public Map<String, Object> flowList(QueryRequest request, XxbBCheck xxbBCheck) {
        User currentUser = FebsUtil.getCurrentUser();
        return getDataTable(this.iXxbBCheckService.findXxbBflow(request, xxbBCheck, currentUser));
    }

    @GetMapping("getId")
    public FebsResponse getId() {
        return new FebsResponse().data(UUID.randomUUID().toString());
    }

    @GetMapping("findCheckD")
    @RequiresPermissions("xxbBCheck:view")
    public FebsResponse findCheckD(String baseId) {
        ModelMap map = new ModelMap();
        int success = 0;
        List<XxbBCheckD> xxbBCheckDataList = new ArrayList<>();
        try {
//            User currentUser = FebsUtil.getCurrentUser();
            xxbBCheckDataList = this.iXxbBCheckService.getCheckDataList(baseId);
            success = 1;
        } catch (Exception e) {
            message = "查询失败.";
            log.error(message, e);
        }

        map.put("success", success);
        map.put("message", message);
        map.put("data", xxbBCheckDataList);
        return new FebsResponse().data(map);
    }

    @GetMapping("findProjDept")
    @RequiresPermissions("xxbBCheck:view")
    public FebsResponse findProjDept(String baseId) {
        ModelMap map = new ModelMap();
        int success = 0;
        List<XxbBProjdept> xxbBCheckDataList = new ArrayList<>();
        try {
//            User currentUser = FebsUtil.getCurrentUser();
            xxbBCheckDataList = this.iXxbBCheckService.getProjDeptList(baseId);
            success = 1;
        } catch (Exception e) {
            message = "查询失败.";
            log.error(message, e);
        }

        map.put("success", success);
        map.put("message", message);
        map.put("data", xxbBCheckDataList);
        return new FebsResponse().data(map);
    }

    @GetMapping("findDeptFlow")
    @RequiresPermissions("xxbBCheck:view")
    public FebsResponse findDeptFlow(String baseId,int state) {
        ModelMap map = new ModelMap();
        int success = 0;
        List<XxbBDeptflow> xxbBCheckDataList = new ArrayList<>();
        try {
            User currentUser = FebsUtil.getCurrentUser();
            xxbBCheckDataList = this.iXxbBCheckService.getDeptFlowList(baseId,currentUser,state);
            success = 1;
        } catch (Exception e) {
            message = "查询失败.";
            log.error(message, e);
        }

        map.put("success", success);
        map.put("message", message);
        map.put("data", xxbBCheckDataList);
        return new FebsResponse().data(map);
    }


    @PutMapping("editDeptFlow")
    @RequiresPermissions("xxbBCheck:update")
    public FebsResponse editDeptFlow(@Valid XxbBDeptflow deptflow) {
        ModelMap map = new ModelMap();
        int success = 0;
        try {
            User currentUser = FebsUtil.getCurrentUser();
            this.iXxbBCheckService.updateDeptflow(deptflow);
            success = 1;
        } catch (Exception e) {
            message = "修改失败";
            log.error(message, e);
        }
        map.put("success", success);
        map.put("message", message);
        return new FebsResponse().data(map);
    }

    @GetMapping("delCheckD")
    @RequiresPermissions("xxbBCheck:delete")
    public FebsResponse getDelId(String id) {
        ModelMap map = new ModelMap();
        int success = 0;
        try {
            this.iXxbBCheckService.deleteCheckData(id);
            success = 1;
        } catch (Exception e) {
            message = "删除失败.";
            log.error(message, e);
        }
        map.put("success", success);
        map.put("message", message);
        return new FebsResponse().data(map);
    }

    @GetMapping("delProjDept")
    @RequiresPermissions("xxbBCheck:delete")
    public FebsResponse getDelProjDeptId(String id) {
        ModelMap map = new ModelMap();
        int success = 0;
        try {
            this.iXxbBCheckService.deleteProjDeptData(id);
            success = 1;
        } catch (Exception e) {
            message = "删除失败.";
            log.error(message, e);
        }
        map.put("success", success);
        map.put("message", message);
        return new FebsResponse().data(map);
    }

    @GetMapping("addCheckD")
    @RequiresPermissions("xxbBCheck:add")
    public FebsResponse addCheckD(XxbBCheckD checkData) {
        ModelMap map = new ModelMap();
        int success = 0;
        try {
            checkData = this.iXxbBCheckService.addCheckData(checkData);
            if (checkData.getId() != null) {
                success = 1;
            } else {
                checkData = new XxbBCheckD();
            }
        } catch (Exception e) {
            message = "删除失败.";
            log.error(message, e);
        }
        map.put("success", success);
        map.put("message", message);
        map.put("data", checkData);
        return new FebsResponse().data(map);
    }

    /**
     * 添加
     *
     * @param xxbBCheck
     * @return
     */
    @Log("新增/按钮")
    @PostMapping
    @RequiresPermissions("xxbBCheck:add")
    public void addXxbBCheck(@Valid XxbBCheck xxbBCheck) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            xxbBCheck.setCreateUserId(currentUser.getUserId());
            this.iXxbBCheckService.createXxbBCheck(xxbBCheck);
        } catch (Exception e) {
            message = "新增/按钮失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("新增/按钮")
    @PutMapping("editInfo")
    @RequiresPermissions("xxbBCheck:update")
    public FebsResponse AddInfo(String data) {
        ModelMap map = new ModelMap();
        int success = 0;
        String id = "";
        try {
            JSONObject json = JSONObject.parseObject(data);
            XxbBCheckInfo xxbBCheckInfo = JSON.toJavaObject(json, XxbBCheckInfo.class);
            User currentUser = FebsUtil.getCurrentUser();
            id = this.iXxbBCheckService.editXxbBCheck(xxbBCheckInfo, currentUser);
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

    /**
     * 修改
     *
     * @param xxbBCheck
     * @return
     */
    @Log("修改")
    @PutMapping
    @RequiresPermissions("xxbBCheck:update")
    public void updateXxbBCheck(@Valid XxbBCheck xxbBCheck) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            xxbBCheck.setModifyUserId(currentUser.getUserId());
            this.iXxbBCheckService.updateXxbBCheck(xxbBCheck);
        } catch (Exception e) {
            message = "修改失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @Log("删除")
    @DeleteMapping("/{ids}")
    @RequiresPermissions("xxbBCheck:delete")
    public void deleteXxbBChecks(@NotBlank(message = "{required}") @PathVariable String ids) throws FebsException {
        try {
            String[] arr_ids = ids.split(StringPool.COMMA);
            this.iXxbBCheckService.deleteXxbBChecks(arr_ids);
        } catch (Exception e) {
            message = "删除失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excel")
    @RequiresPermissions("xxbBCheck:export")
    public void export(QueryRequest request, XxbBCheck xxbBCheck, HttpServletResponse response) throws FebsException {
        try {
            List<XxbBCheck> xxbBChecks = this.iXxbBCheckService.findXxbBChecks(request, xxbBCheck, null).getRecords();
            ExcelKit.$Export(XxbBCheck.class, response).downXlsx(xxbBChecks, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("downloadFile")
    public void findFiles(QueryRequest request, String id, HttpServletResponse response) throws Exception {
//        String filePath = febsProperties.getUploadPath(); // 上传后的路径
//        String fileName = filePath + "down/" + UUID.randomUUID().toString() + ".pdf";
//        String name = UUID.randomUUID().toString() + ".pdf";
//        String mergeFileName = filePath + "down/" + name;
//        List<String> mergeAddPdfList = new ArrayList<>();
//        mergeAddPdfList.add(fileName);
//        try {
//            XxbBCheck xxbBCheck =  this.iXxbBCheckService.getById(id);
//            if (xxbBCheck != null) {
//                XxbBPdfInfo pdf = new XxbBPdfInfo();
//                pdf.writeXxbPdf(fileName, mergeFileName, mergeAddPdfList, xxbBCheck);
//                if (mergeAddPdfList.size() == 1) {
//                    mergeFileName = mergeAddPdfList.get(0);
//                }
//                this.downFile(response, mergeFileName, name, true);
//                this.deleteFile(fileName);            }
//        } catch (Exception e) {
//            message = "下载失败.";
//            log.error(message, e);
//            throw new FebsException(message);
//        }
    }

    @RequestMapping(value = "downTemplate", method = RequestMethod.POST)
    @RequiresPermissions("xxbBCheck:import")
    public void downTemplate(HttpServletResponse response) {
        List<XxbBCheck> publishList = new ArrayList<>();
        ExcelKit.$Export(XxbBCheck.class, response).downXlsx(publishList, true);
    }

    @RequestMapping(value = "import", method = RequestMethod.POST)
    @RequiresPermissions("xxbBCheck:import")
    public ResponseEntity<?> importUser(@RequestParam MultipartFile file)
            throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<XxbBCheck> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        List<Map<String, Object>> resultList = Lists.newArrayList();

        User currentUser = FebsUtil.getCurrentUser();


        ExcelKit.$Import(XxbBCheck.class)
                .readXlsx(file.getInputStream(), new ExcelReadHandler<XxbBCheck>() {

                    @Override
                    public void onSuccess(int sheetIndex, int rowIndex, XxbBCheck entity) {
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
            for (XxbBCheck xxbBCheckImport : successList
            ) {
                XxbBCheck xxbBCheck = new XxbBCheck();
                BeanUtil.copyProperties(xxbBCheckImport, xxbBCheck, CopyOptions.create().setIgnoreNullValue(true));
                this.iXxbBCheckService.createXxbBCheck(xxbBCheck);
            }
        }

        resultList.add(MapUtil.of("data", successList));
        resultList.add(MapUtil.of("haveError", !CollectionUtil.isEmpty(errorList)));
        resultList.add(MapUtil.of("error", errorList));
        resultList.add(MapUtil.of("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L));
        return ResponseEntity.ok(resultList);
    }

    @GetMapping("/{id}")
    public XxbBCheck detail(@NotBlank(message = "{required}") @PathVariable String id) {
        XxbBCheck xxbBCheck = this.iXxbBCheckService.getById(id);
        return xxbBCheck;
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
}