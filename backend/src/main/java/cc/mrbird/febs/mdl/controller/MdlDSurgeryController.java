
package cc.mrbird.febs.mdl.controller;


import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.mdl.entity.MdlDSurgeryImport;
import cc.mrbird.febs.mdl.service.IMdlDSurgeryService;
import cc.mrbird.febs.mdl.entity.MdlDSurgery;

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
 * @since 2022-05-10
 */
@Slf4j
@Validated
@RestController
@RequestMapping("mdlDSurgery")

public class MdlDSurgeryController extends BaseController{

private String message;
@Autowired
public IMdlDSurgeryService iMdlDSurgeryService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'手术目录','/ass/MdlDSurgery/MdlDSurgery','ass/MdlDSurgery/MdlDSurgery','mdlDSurgery:view','fork',0,1,NOW());
 SELECT @maxId:=MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'手术目录新增','mdlDSurgery:add',1,1,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'手术目录编辑','mdlDSurgery:update',1,2,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'手术目录删除','mdlDSurgery:delete',1,3,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'手术目录导出','mdlDSurgery:export',1,4,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'手术目录导入','mdlDSurgery:import',1,5,NOW());
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param mdlDSurgery 查询条件
 * @return
 */
@GetMapping
//@RequiresPermissions("mdlDSurgery:view")
public Map<String, Object> List(QueryRequest request, MdlDSurgery mdlDSurgery){
        return getDataTable(this.iMdlDSurgeryService.findMdlDSurgerys(request, mdlDSurgery));
        }

/**
 * 添加
 * @param  mdlDSurgery
 * @return
 */
@Log("新增/按钮")
@PostMapping
//@RequiresPermissions("mdlDSurgery:add")
public void addMdlDSurgery(@Valid MdlDSurgery mdlDSurgery)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        mdlDSurgery.setCreateUserId(currentUser.getUserId());
        this.iMdlDSurgeryService.createMdlDSurgery(mdlDSurgery);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param mdlDSurgery
 * @return
 */
@Log("修改")
@PutMapping
//@RequiresPermissions("mdlDSurgery:update")
public void updateMdlDSurgery(@Valid MdlDSurgery mdlDSurgery)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      mdlDSurgery.setModifyUserId(currentUser.getUserId());
        this.iMdlDSurgeryService.updateMdlDSurgery(mdlDSurgery);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
//@RequiresPermissions("mdlDSurgery:delete")
public void deleteMdlDSurgerys(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iMdlDSurgeryService.deleteMdlDSurgerys(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
//@RequiresPermissions("mdlDSurgery:export")
public void export(QueryRequest request, MdlDSurgery mdlDSurgery, HttpServletResponse response) throws FebsException {
        try {
        List<MdlDSurgery> mdlDSurgerys = this.iMdlDSurgeryService.findMdlDSurgerys(request, mdlDSurgery).getRecords();
        ExcelKit.$Export(MdlDSurgery.class, response).downXlsx(mdlDSurgerys, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }
@RequestMapping(value = "downTemplate", method = RequestMethod.POST)
public void downTemplate(HttpServletResponse response) {
        List<MdlDSurgeryImport> publishList = new ArrayList<>();
        ExcelKit.$Export(MdlDSurgeryImport.class, response).downXlsx(publishList, true);
        }
@RequestMapping(value = "import", method = RequestMethod.POST)
//@RequiresPermissions("mdlDSurgery:import")
public ResponseEntity<?> importUser(@RequestParam MultipartFile file)
        throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<MdlDSurgeryImport> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        List<Map<String, Object>> resultList = Lists.newArrayList();

        User currentUser=FebsUtil.getCurrentUser();


        ExcelKit.$Import(MdlDSurgeryImport.class)
        .readXlsx(file.getInputStream(), new ExcelReadHandler<MdlDSurgeryImport>() {

@Override
public void onSuccess(int sheetIndex, int rowIndex, MdlDSurgeryImport entity) {
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
        for (MdlDSurgeryImport mdlDSurgeryImport:successList
        ) {
    MdlDSurgery mdlDSurgery =new MdlDSurgery();
        BeanUtil.copyProperties(mdlDSurgeryImport,mdlDSurgery, CopyOptions.create().setIgnoreNullValue(true));
        this.iMdlDSurgeryService.createMdlDSurgery(mdlDSurgery);
        }
        }

        resultList.add(MapUtil.of("data", successList));
        resultList.add(MapUtil.of("haveError", !CollectionUtil.isEmpty(errorList)));
        resultList.add(MapUtil.of("error", errorList));
        resultList.add(MapUtil.of("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L));
        return ResponseEntity.ok(resultList);
        }
@GetMapping("/{id}")
public MdlDSurgery detail(@NotBlank(message = "{required}") @PathVariable String id) {
    MdlDSurgery mdlDSurgery=this.iMdlDSurgeryService.getById(id);
        return mdlDSurgery;
        }
        }