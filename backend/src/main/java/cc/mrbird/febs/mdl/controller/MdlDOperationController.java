
package cc.mrbird.febs.mdl.controller;


import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.FebsConstant;
import cc.mrbird.febs.common.domain.Tree;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.common.utils.TreeUtil;

import cc.mrbird.febs.mdl.entity.MdlDOperationImport;

import cc.mrbird.febs.mdl.service.IMdlDOperationService;
import cc.mrbird.febs.mdl.entity.MdlDOperation;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
import cn.hutool.Hutool;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
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
import java.util.*;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 *
 * @author viki
 * @since 2022-05-10
 */
@Slf4j
@Validated
@RestController
@RequestMapping("mdlDOperation")

public class MdlDOperationController extends BaseController{

    private String message;
    @Autowired
    public IMdlDOperationService iMdlDOperationService;


/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'手术目录','/ass/MdlDOperation/MdlDOperation','ass/MdlDOperation/MdlDOperation','mdlDOperation:view','fork',0,1,NOW());
 SELECT @maxId:=MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'手术目录新增','mdlDOperation:add',1,1,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'手术目录编辑','mdlDOperation:update',1,2,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'手术目录删除','mdlDOperation:delete',1,3,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'手术目录导出','mdlDOperation:export',1,4,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'手术目录导入','mdlDOperation:import',1,5,NOW());
 */


    /**
     * 分页查询数据
     *
     * @param  request 分页信息
     * @param mdlDOperation 查询条件
     * @return
     */
    @GetMapping
//@RequiresPermissions("mdlDOperation:view")
    public Map<String, Object> List(QueryRequest request, MdlDOperation mdlDOperation){
        return getDataTable(this.iMdlDOperationService.findMdlDOperations(request, mdlDOperation));
    }

    /**
     * 添加
     * @param  mdlDOperation
     * @return
     */
    @Log("新增/按钮")
    @PostMapping
//@RequiresPermissions("mdlDOperation:add")
    public void addMdlDOperation(@Valid MdlDOperation mdlDOperation)throws FebsException{
        try{
            User currentUser= FebsUtil.getCurrentUser();
            mdlDOperation.setCreateUserId(currentUser.getUserId());
            this.iMdlDOperationService.createMdlDOperation(mdlDOperation);
        }catch(Exception e){
            message="新增/按钮失败" ;
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 修改
     * @param mdlDOperation
     * @return
     */
    @Log("修改")
    @PutMapping
//@RequiresPermissions("mdlDOperation:update")
    public void updateMdlDOperation(@Valid MdlDOperation mdlDOperation)throws FebsException{
        try{
            User currentUser= FebsUtil.getCurrentUser();
            mdlDOperation.setModifyUserId(currentUser.getUserId());
            this.iMdlDOperationService.updateMdlDOperation(mdlDOperation);
        }catch(Exception e){
            message="修改失败" ;
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    @PutMapping("batch")
    public void updateEndDate(String ids, Date endDate){
        String[] list = ids.split(",");
        LambdaQueryWrapper<MdlDOperation> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(MdlDOperation::getId, list);
        MdlDOperation update = new MdlDOperation();
        update.setEndDate(endDate);
        this.iMdlDOperationService.update(update, wrapper);

    }
    @Log("删除")
    @DeleteMapping("/{ids}")
//@RequiresPermissions("mdlDOperation:delete")
    public void deleteMdlDOperations(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
            String[]arr_ids=ids.split(StringPool.COMMA);
            this.iMdlDOperationService.deleteMdlDOperations(arr_ids);
        }catch(Exception e){
            message="删除失败" ;
            log.error(message,e);
            throw new FebsException(message);
        }
    }
    @PostMapping("excel")
//@RequiresPermissions("mdlDOperation:export")
    public void export(QueryRequest request, MdlDOperation mdlDOperation, HttpServletResponse response) throws FebsException {
        try {
            List<MdlDOperation> mdlDOperations = this.iMdlDOperationService.findMdlDOperations(request, mdlDOperation).getRecords();
            ExcelKit.$Export(MdlDOperation.class, response).downXlsx(mdlDOperations, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
    @RequestMapping(value = "downTemplate", method = RequestMethod.POST)
    public void downTemplate(HttpServletResponse response) {
        List<MdlDOperationImport> publishList = new ArrayList<>();
        ExcelKit.$Export(MdlDOperationImport.class, response).downXlsx(publishList, true);
    }
    @RequestMapping(value = "import", method = RequestMethod.POST)
//@RequiresPermissions("mdlDOperation:import")
    public ResponseEntity<?> importUser(@RequestParam MultipartFile file)
            throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<MdlDOperationImport> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        List<Map<String, Object>> resultList = Lists.newArrayList();

        User currentUser=FebsUtil.getCurrentUser();


        ExcelKit.$Import(MdlDOperationImport.class)
                .readXlsx(file.getInputStream(), new ExcelReadHandler<MdlDOperationImport>() {

                    @Override
                    public void onSuccess(int sheetIndex, int rowIndex, MdlDOperationImport entity) {
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
            for (MdlDOperationImport mdlDOperationImport:successList
            ) {
                MdlDOperation mdlDOperation =new MdlDOperation();
                BeanUtil.copyProperties(mdlDOperationImport,mdlDOperation, CopyOptions.create().setIgnoreNullValue(true));
                mdlDOperation.setStartDate(DateUtil.parseDate(mdlDOperationImport.getStartDate()));
                try {
                    this.iMdlDOperationService.createMdlDOperation(mdlDOperation);
                }
                catch (Exception ex){

                }

            }
        }

        resultList.add(MapUtil.of("data", successList));
        resultList.add(MapUtil.of("haveError", !CollectionUtil.isEmpty(errorList)));
        resultList.add(MapUtil.of("error", errorList));
        resultList.add(MapUtil.of("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L));
        return ResponseEntity.ok(resultList);
    }
    @GetMapping("/{id}")
    public MdlDOperation detail(@NotBlank(message = "{required}") @PathVariable String id) {
        MdlDOperation mdlDOperation=this.iMdlDOperationService.getById(id);
        return mdlDOperation;
    }
}