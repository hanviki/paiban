
package cc.mrbird.febs.mdl.controller;


import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.FebsResponse;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.mdl.entity.MdlBForeignD;
import cc.mrbird.febs.mdl.service.IMdlBForeignDService;
import cc.mrbird.febs.mdl.service.IMdlBForeignService;
import cc.mrbird.febs.mdl.entity.MdlBForeign;

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
import java.util.ArrayList;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author viki
 * @since 2022-05-16
 */
@Slf4j
@Validated
@RestController
@RequestMapping("mdlBForeign")

public class MdlBForeignController extends BaseController{

private String message;
@Autowired
public IMdlBForeignService iMdlBForeignService;
    @Autowired
    public IMdlBForeignDService iMdlBForeignDService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/ass/MdlBForeign/MdlBForeign','ass/MdlBForeign/MdlBForeign','mdlBForeign:view','fork',0,1,NOW());
 SELECT @maxId:=MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','mdlBForeign:add',1,1,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','mdlBForeign:update',1,2,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','mdlBForeign:delete',1,3,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','mdlBForeign:export',1,4,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','mdlBForeign:import',1,5,NOW());
*/


/**
 * ??????????????????
 *
 * @param  request ????????????
 * @param mdlBForeign ????????????
 * @return
 */
@GetMapping

public Map<String, Object> List(QueryRequest request, MdlBForeign mdlBForeign){
        return getDataTable(this.iMdlBForeignService.findMdlBForeigns(request, mdlBForeign));
        }

/**
 * ??????
 * @param  mdlBForeign
 * @return
 */
@Log("??????/??????")
@PostMapping

public void addMdlBForeign(@Valid MdlBForeign mdlBForeign)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        mdlBForeign.setCreateUserId(currentUser.getUserId());
        this.iMdlBForeignService.createMdlBForeign(mdlBForeign);
        }catch(Exception e){
        message="??????/????????????" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * ??????
 * @param mdlBForeign
 * @return
 */
@Log("??????")
@PutMapping

public void updateMdlBForeign(@Valid MdlBForeign mdlBForeign)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      mdlBForeign.setModifyUserId(currentUser.getUserId());
        this.iMdlBForeignService.updateMdlBForeign(mdlBForeign);
        }catch(Exception e){
        message="????????????" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("??????")
@DeleteMapping("/{ids}")

public void deleteMdlBForeigns(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iMdlBForeignService.deleteMdlBForeigns(arr_ids);
        }catch(Exception e){
        message="????????????" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("mdlBForeign:export")
public void export(QueryRequest request, MdlBForeign mdlBForeign, HttpServletResponse response) throws FebsException {
        try {
        List<MdlBForeign> mdlBForeigns = this.iMdlBForeignService.findMdlBForeigns(request, mdlBForeign).getRecords();
        ExcelKit.$Export(MdlBForeign.class, response).downXlsx(mdlBForeigns, false);
        } catch (Exception e) {
        message = "??????Excel??????";
        log.error(message, e);
        throw new FebsException(message);
        }
        }
@RequestMapping(value = "downTemplate", method = RequestMethod.POST)
@RequiresPermissions("mdlBForeign:import")
public void downTemplate(HttpServletResponse response) {
        List<MdlBForeign> publishList = new ArrayList<>();
        ExcelKit.$Export(MdlBForeign.class, response).downXlsx(publishList, true);
        }
@RequestMapping(value = "import", method = RequestMethod.POST)
@RequiresPermissions("mdlBForeign:import")
public ResponseEntity<?> importUser(@RequestParam MultipartFile file)
        throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<MdlBForeign> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        List<Map<String, Object>> resultList = Lists.newArrayList();

        User currentUser=FebsUtil.getCurrentUser();


        ExcelKit.$Import(MdlBForeign.class)
        .readXlsx(file.getInputStream(), new ExcelReadHandler<MdlBForeign>() {

@Override
public void onSuccess(int sheetIndex, int rowIndex, MdlBForeign entity) {
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
        for (MdlBForeign mdlBForeignImport:successList
        ) {
    MdlBForeign mdlBForeign =new MdlBForeign();
        BeanUtil.copyProperties(mdlBForeignImport,mdlBForeign, CopyOptions.create().setIgnoreNullValue(true));
        this.iMdlBForeignService.createMdlBForeign(mdlBForeign);
        }
        }

        resultList.add(MapUtil.of("data", successList));
        resultList.add(MapUtil.of("haveError", !CollectionUtil.isEmpty(errorList)));
        resultList.add(MapUtil.of("error", errorList));
        resultList.add(MapUtil.of("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L));
        return ResponseEntity.ok(resultList);
        }
@GetMapping("/{id}")
public MdlBForeign detail(@NotBlank(message = "{required}") @PathVariable String id) {
    MdlBForeign mdlBForeign=this.iMdlBForeignService.getById(id);
        return mdlBForeign;
        }

    @GetMapping("findCheckD")
    @RequiresPermissions("xxbBCheck:view")
    public FebsResponse findCheckD(String baseId) {
        ModelMap map = new ModelMap();
        int success = 0;
        List<MdlBForeignD> xxbBCheckDataList = new ArrayList<>();
        try {
            LambdaQueryWrapper<MdlBForeignD> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(MdlBForeignD::getBaseId, baseId);
            wrapper.eq(MdlBForeignD::getIsDeletemark, 1);
            xxbBCheckDataList= this.iMdlBForeignDService.list(wrapper);
            if (xxbBCheckDataList.size() > 0) {
                xxbBCheckDataList = xxbBCheckDataList.stream().sorted(Comparator.comparing(MdlBForeignD::getCreateTime).reversed()).collect(Collectors.toList());
            }

            success = 1;
        } catch (Exception e) {
            message = "????????????.";
            log.error(message, e);
        }

        map.put("success", success);
        map.put("message", message);
        map.put("data", xxbBCheckDataList);
        return new FebsResponse().data(map);
    }
    @GetMapping("delCheckD")
    @RequiresPermissions("xxbBCheck:delete")
    public FebsResponse getDelId(String id) {
        ModelMap map = new ModelMap();
        int success = 0;
        try {
            this.iMdlBForeignDService.removeById(id);
            success = 1;
        } catch (Exception e) {
            message = "????????????.";
            log.error(message, e);
        }
        map.put("success", success);
        map.put("message", message);
        return new FebsResponse().data(map);
    }
    @GetMapping("addCheckD")
    @RequiresPermissions("xxbBCheck:add")
    public FebsResponse addCheckD(MdlBForeignD checkData) {
        ModelMap map = new ModelMap();
        int success = 0;
        try {
            this.iMdlBForeignDService.createMdlBForeignD(checkData);
            if (checkData.getId() != null) {
                success = 1;
            } else {
                checkData = new MdlBForeignD();
            }
        } catch (Exception e) {
            message = "????????????.";
            log.error(message, e);
        }
        map.put("success", success);
        map.put("message", message);
        map.put("data", checkData);
        return new FebsResponse().data(map);
    }
    @Log("??????/??????")
    @PutMapping("editInfo")
    @RequiresPermissions("xxbBCheck:update")
    public FebsResponse AddInfo(String data) {
        ModelMap map = new ModelMap();
        int success = 0;
        String id = "";
        try {
            JSONObject json = JSONObject.parseObject(data);
            MdlBForeign xxbBCheckInfo = JSON.toJavaObject(json, MdlBForeign.class);
            User currentUser = FebsUtil.getCurrentUser();
            id = this.iMdlBForeignService.editXxbBCheck(xxbBCheckInfo, currentUser);
            success = 1;
        } catch (Exception e) {
            message = "??????/????????????";
            log.error(message, e);
        }
        map.put("success", success);
        map.put("message", message);
        map.put("data", id);
        return new FebsResponse().data(map);
    }
        }