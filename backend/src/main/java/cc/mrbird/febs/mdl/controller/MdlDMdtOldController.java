
package cc.mrbird.febs.mdl.controller;


import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.mdl.service.IMdlDMdtOldService;
import cc.mrbird.febs.mdl.entity.MdlDMdtOld;

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
 * @since 2022-05-25
 */
@Slf4j
@Validated
@RestController
@RequestMapping("mdlDMdtOld")

public class MdlDMdtOldController extends BaseController{

private String message;
@Autowired
public IMdlDMdtOldService iMdlDMdtOldService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'新技术新项目旧记录','/ass/MdlDMdtOld/MdlDMdtOld','ass/MdlDMdtOld/MdlDMdtOld','mdlDMdtOld:view','fork',0,1,NOW());
 SELECT @maxId:=MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'新技术新项目旧记录新增','mdlDMdtOld:add',1,1,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'新技术新项目旧记录编辑','mdlDMdtOld:update',1,2,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'新技术新项目旧记录删除','mdlDMdtOld:delete',1,3,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'新技术新项目旧记录导出','mdlDMdtOld:export',1,4,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'新技术新项目旧记录导入','mdlDMdtOld:import',1,5,NOW());
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param mdlDMdtOld 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, MdlDMdtOld mdlDMdtOld){
        return getDataTable(this.iMdlDMdtOldService.findMdlDMdtOlds(request, mdlDMdtOld));
        }

/**
 * 添加
 * @param  mdlDMdtOld
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("mdlDMdtOld:add")
public void addMdlDMdtOld(@Valid MdlDMdtOld mdlDMdtOld)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        mdlDMdtOld.setCreateUserId(currentUser.getUserId());
        this.iMdlDMdtOldService.createMdlDMdtOld(mdlDMdtOld);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param mdlDMdtOld
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("mdlDMdtOld:update")
public void updateMdlDMdtOld(@Valid MdlDMdtOld mdlDMdtOld)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      mdlDMdtOld.setModifyUserId(currentUser.getUserId());
        this.iMdlDMdtOldService.updateMdlDMdtOld(mdlDMdtOld);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("mdlDMdtOld:delete")
public void deleteMdlDMdtOlds(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iMdlDMdtOldService.deleteMdlDMdtOlds(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
public void export(QueryRequest request, MdlDMdtOld mdlDMdtOld, HttpServletResponse response) throws FebsException {
        try {
            request.setPageNum(1);
            request.setIsSearchCount(false);
            request.setPageSize(10000);
        List<MdlDMdtOld> mdlDMdtOlds = this.iMdlDMdtOldService.findMdlDMdtOlds(request, mdlDMdtOld).getRecords();
        ExcelKit.$Export(MdlDMdtOld.class, response).downXlsx(mdlDMdtOlds, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }
@RequestMapping(value = "downTemplate", method = RequestMethod.POST)
@RequiresPermissions("mdlDMdtOld:import")
public void downTemplate(HttpServletResponse response) {
        List<MdlDMdtOld> publishList = new ArrayList<>();
        ExcelKit.$Export(MdlDMdtOld.class, response).downXlsx(publishList, true);
        }
@RequestMapping(value = "import", method = RequestMethod.POST)
@RequiresPermissions("mdlDMdtOld:import")
public ResponseEntity<?> importUser(@RequestParam MultipartFile file)
        throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<MdlDMdtOld> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        List<Map<String, Object>> resultList = Lists.newArrayList();

        User currentUser=FebsUtil.getCurrentUser();


        ExcelKit.$Import(MdlDMdtOld.class)
        .readXlsx(file.getInputStream(), new ExcelReadHandler<MdlDMdtOld>() {

@Override
public void onSuccess(int sheetIndex, int rowIndex, MdlDMdtOld entity) {
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
        for (MdlDMdtOld mdlDMdtOldImport:successList
        ) {
    MdlDMdtOld mdlDMdtOld =new MdlDMdtOld();
        BeanUtil.copyProperties(mdlDMdtOldImport,mdlDMdtOld, CopyOptions.create().setIgnoreNullValue(true));
        this.iMdlDMdtOldService.createMdlDMdtOld(mdlDMdtOld);
        }
        }

        resultList.add(MapUtil.of("data", successList));
        resultList.add(MapUtil.of("haveError", !CollectionUtil.isEmpty(errorList)));
        resultList.add(MapUtil.of("error", errorList));
        resultList.add(MapUtil.of("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L));
        return ResponseEntity.ok(resultList);
        }
@GetMapping("/{id}")
public MdlDMdtOld detail(@NotBlank(message = "{required}") @PathVariable String id) {
    MdlDMdtOld mdlDMdtOld=this.iMdlDMdtOldService.getById(id);
        return mdlDMdtOld;
        }
        }