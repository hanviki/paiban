
package cc.mrbird.febs.mdl.controller;


import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.mdl.service.IMdlBMedicalchinaDService;
import cc.mrbird.febs.mdl.entity.MdlBMedicalchinaD;

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
 * @since 2022-06-08
 */
@Slf4j
@Validated
@RestController
@RequestMapping("mdlBMedicalchinaD")

public class MdlBMedicalchinaDController extends BaseController{

private String message;
@Autowired
public IMdlBMedicalchinaDService iMdlBMedicalchinaDService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/ass/MdlBMedicalchinaD/MdlBMedicalchinaD','ass/MdlBMedicalchinaD/MdlBMedicalchinaD','mdlBMedicalchinaD:view','fork',0,1,NOW());
 SELECT @maxId:=MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'新增','mdlBMedicalchinaD:add',1,1,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'编辑','mdlBMedicalchinaD:update',1,2,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'删除','mdlBMedicalchinaD:delete',1,3,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'导出','mdlBMedicalchinaD:export',1,4,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'导入','mdlBMedicalchinaD:import',1,5,NOW());
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param mdlBMedicalchinaD 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, MdlBMedicalchinaD mdlBMedicalchinaD){
        return getDataTable(this.iMdlBMedicalchinaDService.findMdlBMedicalchinaDs(request, mdlBMedicalchinaD));
        }

/**
 * 添加
 * @param  mdlBMedicalchinaD
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addMdlBMedicalchinaD(@Valid MdlBMedicalchinaD mdlBMedicalchinaD)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        mdlBMedicalchinaD.setCreateUserId(currentUser.getUserId());
        this.iMdlBMedicalchinaDService.createMdlBMedicalchinaD(mdlBMedicalchinaD);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param mdlBMedicalchinaD
 * @return
 */
@Log("修改")
@PutMapping
public void updateMdlBMedicalchinaD(@Valid MdlBMedicalchinaD mdlBMedicalchinaD)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      mdlBMedicalchinaD.setModifyUserId(currentUser.getUserId());
        this.iMdlBMedicalchinaDService.updateMdlBMedicalchinaD(mdlBMedicalchinaD);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
public void deleteMdlBMedicalchinaDs(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iMdlBMedicalchinaDService.deleteMdlBMedicalchinaDs(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("mdlBMedicalchinaD:export")
public void export(QueryRequest request, MdlBMedicalchinaD mdlBMedicalchinaD, HttpServletResponse response) throws FebsException {
        try {
        List<MdlBMedicalchinaD> mdlBMedicalchinaDs = this.iMdlBMedicalchinaDService.findMdlBMedicalchinaDs(request, mdlBMedicalchinaD).getRecords();
        ExcelKit.$Export(MdlBMedicalchinaD.class, response).downXlsx(mdlBMedicalchinaDs, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }
@RequestMapping(value = "downTemplate", method = RequestMethod.POST)
@RequiresPermissions("mdlBMedicalchinaD:import")
public void downTemplate(HttpServletResponse response) {
        List<MdlBMedicalchinaD> publishList = new ArrayList<>();
        ExcelKit.$Export(MdlBMedicalchinaD.class, response).downXlsx(publishList, true);
        }
@RequestMapping(value = "import", method = RequestMethod.POST)
@RequiresPermissions("mdlBMedicalchinaD:import")
public ResponseEntity<?> importUser(@RequestParam MultipartFile file)
        throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<MdlBMedicalchinaD> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        List<Map<String, Object>> resultList = Lists.newArrayList();

        User currentUser=FebsUtil.getCurrentUser();


        ExcelKit.$Import(MdlBMedicalchinaD.class)
        .readXlsx(file.getInputStream(), new ExcelReadHandler<MdlBMedicalchinaD>() {

@Override
public void onSuccess(int sheetIndex, int rowIndex, MdlBMedicalchinaD entity) {
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
        for (MdlBMedicalchinaD mdlBMedicalchinaDImport:successList
        ) {
    MdlBMedicalchinaD mdlBMedicalchinaD =new MdlBMedicalchinaD();
        BeanUtil.copyProperties(mdlBMedicalchinaDImport,mdlBMedicalchinaD, CopyOptions.create().setIgnoreNullValue(true));
        this.iMdlBMedicalchinaDService.createMdlBMedicalchinaD(mdlBMedicalchinaD);
        }
        }

        resultList.add(MapUtil.of("data", successList));
        resultList.add(MapUtil.of("haveError", !CollectionUtil.isEmpty(errorList)));
        resultList.add(MapUtil.of("error", errorList));
        resultList.add(MapUtil.of("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L));
        return ResponseEntity.ok(resultList);
        }
@GetMapping("/{id}")
public MdlBMedicalchinaD detail(@NotBlank(message = "{required}") @PathVariable String id) {
    MdlBMedicalchinaD mdlBMedicalchinaD=this.iMdlBMedicalchinaDService.getById(id);
        return mdlBMedicalchinaD;
        }
        }