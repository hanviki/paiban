
package cc.mrbird.febs.xxb.controller;


import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.xxb.service.IXxbBDeptleaderService;
import cc.mrbird.febs.xxb.entity.XxbBDeptleader;

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
 * @since 2022-04-20
 */
@Slf4j
@Validated
@RestController
@RequestMapping("xxbBDeptleader")

public class XxbBDeptleaderController extends BaseController{

private String message;
@Autowired
public IXxbBDeptleaderService iXxbBDeptleaderService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'部门负责人','/ass/XxbBDeptleader/XxbBDeptleader','ass/XxbBDeptleader/XxbBDeptleader','xxbBDeptleader:view','fork',0,1,NOW());
 SELECT @maxId:=MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'部门负责人新增','xxbBDeptleader:add',1,1,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'部门负责人编辑','xxbBDeptleader:update',1,2,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'部门负责人删除','xxbBDeptleader:delete',1,3,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'部门负责人导出','xxbBDeptleader:export',1,4,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'部门负责人导入','xxbBDeptleader:import',1,5,NOW());
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param xxbBDeptleader 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("xxbBDeptleader:view")
public Map<String, Object> List(QueryRequest request, XxbBDeptleader xxbBDeptleader){
        return getDataTable(this.iXxbBDeptleaderService.findXxbBDeptleaders(request, xxbBDeptleader));
        }

/**
 * 添加
 * @param  xxbBDeptleader
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("xxbBDeptleader:add")
public void addXxbBDeptleader(@Valid XxbBDeptleader xxbBDeptleader)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        xxbBDeptleader.setCreateUserId(currentUser.getUserId());
        this.iXxbBDeptleaderService.createXxbBDeptleader(xxbBDeptleader);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param xxbBDeptleader
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("xxbBDeptleader:update")
public void updateXxbBDeptleader(@Valid XxbBDeptleader xxbBDeptleader)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      xxbBDeptleader.setModifyUserId(currentUser.getUserId());
        this.iXxbBDeptleaderService.updateXxbBDeptleader(xxbBDeptleader);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("xxbBDeptleader:delete")
public void deleteXxbBDeptleaders(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iXxbBDeptleaderService.deleteXxbBDeptleaders(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("xxbBDeptleader:export")
public void export(QueryRequest request, XxbBDeptleader xxbBDeptleader, HttpServletResponse response) throws FebsException {
        try {
        List<XxbBDeptleader> xxbBDeptleaders = this.iXxbBDeptleaderService.findXxbBDeptleaders(request, xxbBDeptleader).getRecords();
        ExcelKit.$Export(XxbBDeptleader.class, response).downXlsx(xxbBDeptleaders, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }
@RequestMapping(value = "downTemplate", method = RequestMethod.POST)
@RequiresPermissions("xxbBDeptleader:import")
public void downTemplate(HttpServletResponse response) {
        List<XxbBDeptleader> publishList = new ArrayList<>();
        ExcelKit.$Export(XxbBDeptleader.class, response).downXlsx(publishList, true);
        }
@RequestMapping(value = "import", method = RequestMethod.POST)
@RequiresPermissions("xxbBDeptleader:import")
public ResponseEntity<?> importUser(@RequestParam MultipartFile file)
        throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<XxbBDeptleader> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        List<Map<String, Object>> resultList = Lists.newArrayList();

        User currentUser=FebsUtil.getCurrentUser();


        ExcelKit.$Import(XxbBDeptleader.class)
        .readXlsx(file.getInputStream(), new ExcelReadHandler<XxbBDeptleader>() {

@Override
public void onSuccess(int sheetIndex, int rowIndex, XxbBDeptleader entity) {
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
        if(CollectionUtil.isEmpty(errorList)){
        for (XxbBDeptleader xxbBDeptleaderImport:successList
        ) {
    XxbBDeptleader xxbBDeptleader =new XxbBDeptleader();
        BeanUtil.copyProperties(xxbBDeptleaderImport,xxbBDeptleader, CopyOptions.create().setIgnoreNullValue(true));
        this.iXxbBDeptleaderService.createXxbBDeptleader(xxbBDeptleader);
        }
        }

        resultList.add(MapUtil.of("data", successList));
        resultList.add(MapUtil.of("haveError", !CollectionUtil.isEmpty(errorList)));
        resultList.add(MapUtil.of("error", errorList));
        resultList.add(MapUtil.of("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L));
        return ResponseEntity.ok(resultList);
        }
@GetMapping("/{id}")
public XxbBDeptleader detail(@NotBlank(message = "{required}") @PathVariable String id) {
    XxbBDeptleader xxbBDeptleader=this.iXxbBDeptleaderService.getById(id);
        return xxbBDeptleader;
        }
        }