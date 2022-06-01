
package cc.mrbird.febs.mdl.controller;


import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.mdl.service.IMdlDPrizeOldService;
import cc.mrbird.febs.mdl.entity.MdlDPrizeOld;

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
@RequestMapping("mdlDPrizeOld")

public class MdlDPrizeOldController extends BaseController{

private String message;
@Autowired
public IMdlDPrizeOldService iMdlDPrizeOldService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'新技术获奖记录','/ass/MdlDPrizeOld/MdlDPrizeOld','ass/MdlDPrizeOld/MdlDPrizeOld','mdlDPrizeOld:view','fork',0,1,NOW());
 SELECT @maxId:=MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'新技术获奖记录新增','mdlDPrizeOld:add',1,1,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'新技术获奖记录编辑','mdlDPrizeOld:update',1,2,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'新技术获奖记录删除','mdlDPrizeOld:delete',1,3,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'新技术获奖记录导出','mdlDPrizeOld:export',1,4,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'新技术获奖记录导入','mdlDPrizeOld:import',1,5,NOW());
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param mdlDPrizeOld 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, MdlDPrizeOld mdlDPrizeOld){
        return getDataTable(this.iMdlDPrizeOldService.findMdlDPrizeOlds(request, mdlDPrizeOld));
        }

/**
 * 添加
 * @param  mdlDPrizeOld
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("mdlDPrizeOld:add")
public void addMdlDPrizeOld(@Valid MdlDPrizeOld mdlDPrizeOld)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        mdlDPrizeOld.setCreateUserId(currentUser.getUserId());
        this.iMdlDPrizeOldService.createMdlDPrizeOld(mdlDPrizeOld);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param mdlDPrizeOld
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("mdlDPrizeOld:update")
public void updateMdlDPrizeOld(@Valid MdlDPrizeOld mdlDPrizeOld)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      mdlDPrizeOld.setModifyUserId(currentUser.getUserId());
        this.iMdlDPrizeOldService.updateMdlDPrizeOld(mdlDPrizeOld);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("mdlDPrizeOld:delete")
public void deleteMdlDPrizeOlds(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iMdlDPrizeOldService.deleteMdlDPrizeOlds(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
public void export(QueryRequest request, MdlDPrizeOld mdlDPrizeOld, HttpServletResponse response) throws FebsException {
        try {
            request.setPageNum(1);
            request.setIsSearchCount(false);
            request.setPageSize(10000);
        List<MdlDPrizeOld> mdlDPrizeOlds = this.iMdlDPrizeOldService.findMdlDPrizeOlds(request, mdlDPrizeOld).getRecords();
        ExcelKit.$Export(MdlDPrizeOld.class, response).downXlsx(mdlDPrizeOlds, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }
@RequestMapping(value = "downTemplate", method = RequestMethod.POST)
@RequiresPermissions("mdlDPrizeOld:import")
public void downTemplate(HttpServletResponse response) {
        List<MdlDPrizeOld> publishList = new ArrayList<>();
        ExcelKit.$Export(MdlDPrizeOld.class, response).downXlsx(publishList, true);
        }
@RequestMapping(value = "import", method = RequestMethod.POST)
@RequiresPermissions("mdlDPrizeOld:import")
public ResponseEntity<?> importUser(@RequestParam MultipartFile file)
        throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<MdlDPrizeOld> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        List<Map<String, Object>> resultList = Lists.newArrayList();

        User currentUser=FebsUtil.getCurrentUser();


        ExcelKit.$Import(MdlDPrizeOld.class)
        .readXlsx(file.getInputStream(), new ExcelReadHandler<MdlDPrizeOld>() {

@Override
public void onSuccess(int sheetIndex, int rowIndex, MdlDPrizeOld entity) {
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
        for (MdlDPrizeOld mdlDPrizeOldImport:successList
        ) {
    MdlDPrizeOld mdlDPrizeOld =new MdlDPrizeOld();
        BeanUtil.copyProperties(mdlDPrizeOldImport,mdlDPrizeOld, CopyOptions.create().setIgnoreNullValue(true));
        this.iMdlDPrizeOldService.createMdlDPrizeOld(mdlDPrizeOld);
        }
        }

        resultList.add(MapUtil.of("data", successList));
        resultList.add(MapUtil.of("haveError", !CollectionUtil.isEmpty(errorList)));
        resultList.add(MapUtil.of("error", errorList));
        resultList.add(MapUtil.of("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L));
        return ResponseEntity.ok(resultList);
        }
@GetMapping("/{id}")
public MdlDPrizeOld detail(@NotBlank(message = "{required}") @PathVariable String id) {
    MdlDPrizeOld mdlDPrizeOld=this.iMdlDPrizeOldService.getById(id);
        return mdlDPrizeOld;
        }
        }