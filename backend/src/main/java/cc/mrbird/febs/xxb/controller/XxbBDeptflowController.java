
package cc.mrbird.febs.xxb.controller;


import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.xxb.service.IXxbBDeptflowService;
import cc.mrbird.febs.xxb.entity.XxbBDeptflow;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
import cn.hutool.Hutool;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.beust.jcommander.internal.Lists;
import com.wuwenze.poi.ExcelKit;
import com.wuwenze.poi.handler.ExcelReadHandler;
import com.wuwenze.poi.pojo.ExcelErrorField;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author viki
 * @since 2022-04-08
 */
@Slf4j
@Validated
@RestController
@RequestMapping("xxbBDeptflow")

public class XxbBDeptflowController extends BaseController{

private String message;
@Autowired
public IXxbBDeptflowService iXxbBDeptflowService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/ass/XxbBDeptflow/XxbBDeptflow','ass/XxbBDeptflow/XxbBDeptflow','xxbBDeptflow:view','fork',0,1,NOW());
 SELECT @maxId:=MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','xxbBDeptflow:add',1,1,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','xxbBDeptflow:update',1,2,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','xxbBDeptflow:delete',1,3,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','xxbBDeptflow:export',1,4,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','xxbBDeptflow:import',1,5,NOW());
*/


/**
 * ??????????????????
 *
 * @param  request ????????????
 * @param xxbBDeptflow ????????????
 * @return
 */
@GetMapping
@RequiresPermissions("xxbBDeptflow:view")
public Map<String, Object> List(QueryRequest request, XxbBDeptflow xxbBDeptflow){
        return getDataTable(this.iXxbBDeptflowService.findXxbBDeptflows(request, xxbBDeptflow));
        }

/**
 * ??????
 * @param  xxbBDeptflow
 * @return
 */
@Log("??????/??????")
@PostMapping
@RequiresPermissions("xxbBDeptflow:add")
public void addXxbBDeptflow(@Valid XxbBDeptflow xxbBDeptflow)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
//        xxbBDeptflow.setCreateUserId(currentUser.getUserId());
        this.iXxbBDeptflowService.createXxbBDeptflow(xxbBDeptflow);
        }catch(Exception e){
        message="??????/????????????" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * ??????
 * @param xxbBDeptflow
 * @return
 */
@Log("??????")
@PutMapping
@RequiresPermissions("xxbBDeptflow:update")
public void updateXxbBDeptflow(@Valid XxbBDeptflow xxbBDeptflow)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
//      xxbBDeptflow.setModifyUserId(currentUser.getUserId());
        this.iXxbBDeptflowService.updateXxbBDeptflow(xxbBDeptflow);
        }catch(Exception e){
        message="????????????" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("??????")
@DeleteMapping("/{ids}")
@RequiresPermissions("xxbBDeptflow:delete")
public void deleteXxbBDeptflows(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iXxbBDeptflowService.deleteXxbBDeptflows(arr_ids);
        }catch(Exception e){
        message="????????????" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("xxbBDeptflow:export")
public void export(QueryRequest request, XxbBDeptflow xxbBDeptflow, HttpServletResponse response) throws FebsException {
        try {
        List<XxbBDeptflow> xxbBDeptflows = this.iXxbBDeptflowService.findXxbBDeptflows(request, xxbBDeptflow).getRecords();
        ExcelKit.$Export(XxbBDeptflow.class, response).downXlsx(xxbBDeptflows, false);
        } catch (Exception e) {
        message = "??????Excel??????";
        log.error(message, e);
        throw new FebsException(message);
        }
        }
@RequestMapping(value = "downTemplate", method = RequestMethod.POST)
@RequiresPermissions("xxbBDeptflow:import")
public void downTemplate(HttpServletResponse response) {
        List<XxbBDeptflow> publishList = new ArrayList<>();
        ExcelKit.$Export(XxbBDeptflow.class, response).downXlsx(publishList, true);
        }
@RequestMapping(value = "import", method = RequestMethod.POST)
@RequiresPermissions("xxbBDeptflow:import")
public ResponseEntity<?> importUser(@RequestParam MultipartFile file)
        throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<XxbBDeptflow> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        List<Map<String, Object>> resultList = Lists.newArrayList();

        User currentUser=FebsUtil.getCurrentUser();


        ExcelKit.$Import(XxbBDeptflow.class)
        .readXlsx(file.getInputStream(), new ExcelReadHandler<XxbBDeptflow>() {

@Override
public void onSuccess(int sheetIndex, int rowIndex, XxbBDeptflow entity) {
        successList.add(entity); // ??????????????????????????????????????????
        }

@Override
public void onError(int sheetIndex, int rowIndex,
        java.util.List<ExcelErrorField> errorFields) {
        // ????????????????????????????????????????????????????????????
        errorList.add(MapUtil.of("sheetIndex", sheetIndex));
        errorList.add(MapUtil.of("rowIndex", rowIndex));
        errorList.add(MapUtil.of("errorFields", errorFields));
        }
        });

        // TODO: ??????successList??????????????????
        if(CollectionUtil.isEmpty(errorList)){
        for (XxbBDeptflow xxbBDeptflowImport:successList
        ) {
    XxbBDeptflow xxbBDeptflow =new XxbBDeptflow();
        BeanUtil.copyProperties(xxbBDeptflowImport,xxbBDeptflow, CopyOptions.create().setIgnoreNullValue(true));
        this.iXxbBDeptflowService.createXxbBDeptflow(xxbBDeptflow);
        }
        }

        resultList.add(MapUtil.of("data", successList));
        resultList.add(MapUtil.of("haveError", !CollectionUtil.isEmpty(errorList)));
        resultList.add(MapUtil.of("error", errorList));
        resultList.add(MapUtil.of("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L));
        return ResponseEntity.ok(resultList);
        }
@GetMapping("/{id}")
public XxbBDeptflow detail(@NotBlank(message = "{required}") @PathVariable String id) {
    XxbBDeptflow xxbBDeptflow=this.iXxbBDeptflowService.getById(id);
        return xxbBDeptflow;
        }
        }