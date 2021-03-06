
package cc.mrbird.febs.mdl.controller;


import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.mdl.service.IMdlBSpecialService;
import cc.mrbird.febs.mdl.entity.MdlBSpecial;

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
 * @since 2022-03-16
 */
@Slf4j
@Validated
@RestController
@RequestMapping("mdlBSpecial")

public class MdlBSpecialController extends BaseController{

private String message;
@Autowired
public IMdlBSpecialService iMdlBSpecialService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/ass/MdlBSpecial/MdlBSpecial','ass/MdlBSpecial/MdlBSpecial','mdlBSpecial:view','fork',0,1,NOW());
 SELECT @maxId:=MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','mdlBSpecial:add',1,1,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','mdlBSpecial:update',1,2,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','mdlBSpecial:delete',1,3,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','mdlBSpecial:export',1,4,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','mdlBSpecial:import',1,5,NOW());
*/


/**
 * ??????????????????
 *
 * @param  request ????????????
 * @param mdlBSpecial ????????????
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, MdlBSpecial mdlBSpecial){
        return getDataTable(this.iMdlBSpecialService.findMdlBSpecials(request, mdlBSpecial));
        }

    @GetMapping("all")
    public Map<String, Object> List3(QueryRequest request, MdlBSpecial mdlBSpecial){
        return getDataTable(this.iMdlBSpecialService.findMdlBSpecialList(request, mdlBSpecial));
    }

/**
 * ??????
 * @param  mdlBSpecial
 * @return
 */
@Log("??????/??????")
@PostMapping
public void addMdlBSpecial(@Valid MdlBSpecial mdlBSpecial)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        mdlBSpecial.setCreateUserId(currentUser.getUserId());
            mdlBSpecial.setUserAccount(currentUser.getUsername());
            mdlBSpecial.setUserAccountName(currentUser.getRealname());
        this.iMdlBSpecialService.createMdlBSpecial(mdlBSpecial);
        }catch(Exception e){
        message="??????/????????????" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * ??????
 * @param mdlBSpecial
 * @return
 */
@Log("??????")
@PutMapping
public void updateMdlBSpecial(@Valid MdlBSpecial mdlBSpecial)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      mdlBSpecial.setModifyUserId(currentUser.getUserId());
            mdlBSpecial.setUserAccount(currentUser.getUsername());
            mdlBSpecial.setUserAccountName(currentUser.getRealname());
        this.iMdlBSpecialService.updateMdlBSpecial(mdlBSpecial);
        }catch(Exception e){
        message="????????????" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("??????")
@DeleteMapping("/{ids}")
public void deleteMdlBSpecials(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iMdlBSpecialService.deleteMdlBSpecials(arr_ids);
        }catch(Exception e){
        message="????????????" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("mdlBSpecial:export")
public void export(QueryRequest request, MdlBSpecial mdlBSpecial, HttpServletResponse response) throws FebsException {
        try {
        List<MdlBSpecial> mdlBSpecials = this.iMdlBSpecialService.findMdlBSpecials(request, mdlBSpecial).getRecords();
        ExcelKit.$Export(MdlBSpecial.class, response).downXlsx(mdlBSpecials, false);
        } catch (Exception e) {
        message = "??????Excel??????";
        log.error(message, e);
        throw new FebsException(message);
        }
        }
@RequestMapping(value = "downTemplate", method = RequestMethod.POST)
@RequiresPermissions("mdlBSpecial:import")
public void downTemplate(HttpServletResponse response) {
        List<MdlBSpecial> publishList = new ArrayList<>();
        ExcelKit.$Export(MdlBSpecial.class, response).downXlsx(publishList, true);
        }
@RequestMapping(value = "import", method = RequestMethod.POST)
@RequiresPermissions("mdlBSpecial:import")
public ResponseEntity<?> importUser(@RequestParam MultipartFile file)
        throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<MdlBSpecial> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        List<Map<String, Object>> resultList = Lists.newArrayList();

        User currentUser=FebsUtil.getCurrentUser();


        ExcelKit.$Import(MdlBSpecial.class)
        .readXlsx(file.getInputStream(), new ExcelReadHandler<MdlBSpecial>() {

@Override
public void onSuccess(int sheetIndex, int rowIndex, MdlBSpecial entity) {
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
        for (MdlBSpecial mdlBSpecialImport:successList
        ) {
    MdlBSpecial mdlBSpecial =new MdlBSpecial();
        BeanUtil.copyProperties(mdlBSpecialImport,mdlBSpecial, CopyOptions.create().setIgnoreNullValue(true));
        this.iMdlBSpecialService.createMdlBSpecial(mdlBSpecial);
        }
        }

        resultList.add(MapUtil.of("data", successList));
        resultList.add(MapUtil.of("haveError", !CollectionUtil.isEmpty(errorList)));
        resultList.add(MapUtil.of("error", errorList));
        resultList.add(MapUtil.of("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L));
        return ResponseEntity.ok(resultList);
        }
@GetMapping("/{id}")
public MdlBSpecial detail(@NotBlank(message = "{required}") @PathVariable String id) {
    MdlBSpecial mdlBSpecial=this.iMdlBSpecialService.getById(id);
        return mdlBSpecial;
        }
        }