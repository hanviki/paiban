
package cc.mrbird.febs.mdl.controller;


import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.mdl.service.IMdlBMdtDService;
import cc.mrbird.febs.mdl.entity.MdlBMdtD;

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
 * @since 2022-03-21
 */
@Slf4j
@Validated
@RestController
@RequestMapping("mdlBMdtD")

public class MdlBMdtDController extends BaseController{

private String message;
@Autowired
public IMdlBMdtDService iMdlBMdtDService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/ass/MdlBMdtD/MdlBMdtD','ass/MdlBMdtD/MdlBMdtD','mdlBMdtD:view','fork',0,1,NOW());
 SELECT @maxId:=MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','mdlBMdtD:add',1,1,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','mdlBMdtD:update',1,2,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','mdlBMdtD:delete',1,3,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','mdlBMdtD:export',1,4,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','mdlBMdtD:import',1,5,NOW());
*/


/**
 * ??????????????????
 *
 * @param  request ????????????
 * @param mdlBMdtD ????????????
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, MdlBMdtD mdlBMdtD){
        return getDataTable(this.iMdlBMdtDService.findMdlBMdtDs(request, mdlBMdtD));
        }

/**
 * ??????
 * @param  mdlBMdtD
 * @return
 */
@Log("??????/??????")
@PostMapping
@RequiresPermissions("mdlBMdtD:add")
public void addMdlBMdtD(@Valid MdlBMdtD mdlBMdtD)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        mdlBMdtD.setCreateUserId(currentUser.getUserId());
        this.iMdlBMdtDService.createMdlBMdtD(mdlBMdtD);
        }catch(Exception e){
        message="??????/????????????" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * ??????
 * @param mdlBMdtD
 * @return
 */
@Log("??????")
@PutMapping
@RequiresPermissions("mdlBMdtD:update")
public void updateMdlBMdtD(@Valid MdlBMdtD mdlBMdtD)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      mdlBMdtD.setModifyUserId(currentUser.getUserId());
        this.iMdlBMdtDService.updateMdlBMdtD(mdlBMdtD);
        }catch(Exception e){
        message="????????????" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("??????")
@DeleteMapping("/{ids}")
@RequiresPermissions("mdlBMdtD:delete")
public void deleteMdlBMdtDs(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iMdlBMdtDService.deleteMdlBMdtDs(arr_ids);
        }catch(Exception e){
        message="????????????" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("mdlBMdtD:export")
public void export(QueryRequest request, MdlBMdtD mdlBMdtD, HttpServletResponse response) throws FebsException {
        try {
        List<MdlBMdtD> mdlBMdtDs = this.iMdlBMdtDService.findMdlBMdtDs(request, mdlBMdtD).getRecords();
        ExcelKit.$Export(MdlBMdtD.class, response).downXlsx(mdlBMdtDs, false);
        } catch (Exception e) {
        message = "??????Excel??????";
        log.error(message, e);
        throw new FebsException(message);
        }
        }
@RequestMapping(value = "downTemplate", method = RequestMethod.POST)
@RequiresPermissions("mdlBMdtD:import")
public void downTemplate(HttpServletResponse response) {
        List<MdlBMdtD> publishList = new ArrayList<>();
        ExcelKit.$Export(MdlBMdtD.class, response).downXlsx(publishList, true);
        }
@RequestMapping(value = "import", method = RequestMethod.POST)
@RequiresPermissions("mdlBMdtD:import")
public ResponseEntity<?> importUser(@RequestParam MultipartFile file)
        throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<MdlBMdtD> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        List<Map<String, Object>> resultList = Lists.newArrayList();

        User currentUser=FebsUtil.getCurrentUser();


        ExcelKit.$Import(MdlBMdtD.class)
        .readXlsx(file.getInputStream(), new ExcelReadHandler<MdlBMdtD>() {

@Override
public void onSuccess(int sheetIndex, int rowIndex, MdlBMdtD entity) {
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
        for (MdlBMdtD mdlBMdtDImport:successList
        ) {
    MdlBMdtD mdlBMdtD =new MdlBMdtD();
        BeanUtil.copyProperties(mdlBMdtDImport,mdlBMdtD, CopyOptions.create().setIgnoreNullValue(true));
        this.iMdlBMdtDService.createMdlBMdtD(mdlBMdtD);
        }
        }

        resultList.add(MapUtil.of("data", successList));
        resultList.add(MapUtil.of("haveError", !CollectionUtil.isEmpty(errorList)));
        resultList.add(MapUtil.of("error", errorList));
        resultList.add(MapUtil.of("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L));
        return ResponseEntity.ok(resultList);
        }
@GetMapping("/{id}")
public MdlBMdtD detail(@NotBlank(message = "{required}") @PathVariable String id) {
    MdlBMdtD mdlBMdtD=this.iMdlBMdtDService.getById(id);
        return mdlBMdtD;
        }
        }