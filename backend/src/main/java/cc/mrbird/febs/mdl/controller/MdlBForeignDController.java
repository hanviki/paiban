
package cc.mrbird.febs.mdl.controller;


import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.FebsConstant;
import cc.mrbird.febs.common.domain.Tree;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.common.utils.TreeUtil;
import cc.mrbird.febs.mdl.service.IMdlBForeignDService;
import cc.mrbird.febs.mdl.entity.MdlBForeignD;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
import cn.hutool.Hutool;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.beust.jcommander.internal.Lists;
import com.wuwenze.poi.ExcelKit;
import com.wuwenze.poi.handler.ExcelReadHandler;
import com.wuwenze.poi.pojo.ExcelErrorField;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author viki
 * @since 2022-05-16
 */
@Slf4j
@Validated
@RestController
@RequestMapping("mdlBForeignD")

public class MdlBForeignDController extends BaseController{

private String message;
@Autowired
public IMdlBForeignDService iMdlBForeignDService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/ass/MdlBForeignD/MdlBForeignD','ass/MdlBForeignD/MdlBForeignD','mdlBForeignD:view','fork',0,1,NOW());
 SELECT @maxId:=MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','mdlBForeignD:add',1,1,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','mdlBForeignD:update',1,2,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','mdlBForeignD:delete',1,3,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','mdlBForeignD:export',1,4,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','mdlBForeignD:import',1,5,NOW());
*/


/**
 * ??????????????????
 *
 * @param  request ????????????
 * @param mdlBForeignD ????????????
 * @return
 */
@GetMapping
@RequiresPermissions("mdlBForeignD:view")
public Map<String, Object> List(QueryRequest request, MdlBForeignD mdlBForeignD){
        return getDataTable(this.iMdlBForeignDService.findMdlBForeignDs(request, mdlBForeignD));
        }

/**
 * ??????
 * @param  mdlBForeignD
 * @return
 */
@Log("??????/??????")
@PostMapping
@RequiresPermissions("mdlBForeignD:add")
public void addMdlBForeignD(@Valid MdlBForeignD mdlBForeignD)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        mdlBForeignD.setCreateUserId(currentUser.getUserId());
        this.iMdlBForeignDService.createMdlBForeignD(mdlBForeignD);
        }catch(Exception e){
        message="??????/????????????" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * ??????
 * @param mdlBForeignD
 * @return
 */
@Log("??????")
@PutMapping
@RequiresPermissions("mdlBForeignD:update")
public void updateMdlBForeignD(@Valid MdlBForeignD mdlBForeignD)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      mdlBForeignD.setModifyUserId(currentUser.getUserId());
        this.iMdlBForeignDService.updateMdlBForeignD(mdlBForeignD);
        }catch(Exception e){
        message="????????????" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("??????")
@DeleteMapping("/{ids}")
@RequiresPermissions("mdlBForeignD:delete")
public void deleteMdlBForeignDs(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iMdlBForeignDService.deleteMdlBForeignDs(arr_ids);
        }catch(Exception e){
        message="????????????" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("mdlBForeignD:export")
public void export(QueryRequest request, MdlBForeignD mdlBForeignD, HttpServletResponse response) throws FebsException {
        try {
        List<MdlBForeignD> mdlBForeignDs = this.iMdlBForeignDService.findMdlBForeignDs(request, mdlBForeignD).getRecords();
        ExcelKit.$Export(MdlBForeignD.class, response).downXlsx(mdlBForeignDs, false);
        } catch (Exception e) {
        message = "??????Excel??????";
        log.error(message, e);
        throw new FebsException(message);
        }
        }
@RequestMapping(value = "downTemplate", method = RequestMethod.POST)
@RequiresPermissions("mdlBForeignD:import")
public void downTemplate(HttpServletResponse response) {
        List<MdlBForeignD> publishList = new ArrayList<>();
        ExcelKit.$Export(MdlBForeignD.class, response).downXlsx(publishList, true);
        }
@RequestMapping(value = "import", method = RequestMethod.POST)
@RequiresPermissions("mdlBForeignD:import")
public ResponseEntity<?> importUser(@RequestParam MultipartFile file)
        throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<MdlBForeignD> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        List<Map<String, Object>> resultList = Lists.newArrayList();

        User currentUser=FebsUtil.getCurrentUser();


        ExcelKit.$Import(MdlBForeignD.class)
        .readXlsx(file.getInputStream(), new ExcelReadHandler<MdlBForeignD>() {

@Override
public void onSuccess(int sheetIndex, int rowIndex, MdlBForeignD entity) {
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
        for (MdlBForeignD mdlBForeignDImport:successList
        ) {
    MdlBForeignD mdlBForeignD =new MdlBForeignD();
        BeanUtil.copyProperties(mdlBForeignDImport,mdlBForeignD, CopyOptions.create().setIgnoreNullValue(true));
        this.iMdlBForeignDService.createMdlBForeignD(mdlBForeignD);
        }
        }

        resultList.add(MapUtil.of("data", successList));
        resultList.add(MapUtil.of("haveError", !CollectionUtil.isEmpty(errorList)));
        resultList.add(MapUtil.of("error", errorList));
        resultList.add(MapUtil.of("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L));
        return ResponseEntity.ok(resultList);
        }
@GetMapping("/{id}")
public MdlBForeignD detail(@NotBlank(message = "{required}") @PathVariable String id) {
    MdlBForeignD mdlBForeignD=this.iMdlBForeignDService.getById(id);
        return mdlBForeignD;
        }
        }