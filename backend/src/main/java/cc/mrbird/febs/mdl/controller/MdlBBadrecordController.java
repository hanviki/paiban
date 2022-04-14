
package cc.mrbird.febs.mdl.controller;


import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.mdl.service.IMdlBBadrecordService;
import cc.mrbird.febs.mdl.entity.MdlBBadrecord;

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
 * @since 2022-04-12
 */
@Slf4j
@Validated
@RestController
@RequestMapping("mdlBBadrecord")

public class MdlBBadrecordController extends BaseController{

private String message;
@Autowired
public IMdlBBadrecordService iMdlBBadrecordService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/ass/MdlBBadrecord/MdlBBadrecord','ass/MdlBBadrecord/MdlBBadrecord','mdlBBadrecord:view','fork',0,1,NOW());
 SELECT @maxId:=MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'新增','mdlBBadrecord:add',1,1,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'编辑','mdlBBadrecord:update',1,2,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'删除','mdlBBadrecord:delete',1,3,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'导出','mdlBBadrecord:export',1,4,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'导入','mdlBBadrecord:import',1,5,NOW());
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param mdlBBadrecord 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("mdlBBadrecord:view")
public Map<String, Object> List(QueryRequest request, MdlBBadrecord mdlBBadrecord){
    User currentUser= FebsUtil.getCurrentUser();
    mdlBBadrecord.setDeptName(currentUser.getCode());
        return getDataTable(this.iMdlBBadrecordService.findMdlBBadrecords(request, mdlBBadrecord));
        }
    @GetMapping("info")
    public Map<String, Object> List6(QueryRequest request, MdlBBadrecord mdlBBadrecord){
        User currentUser= FebsUtil.getCurrentUser();
        return getDataTable(this.iMdlBBadrecordService.findMdlBBadrecords(request, mdlBBadrecord));
    }
    @GetMapping("sub")
    public List<MdlBBadrecord> List4(QueryRequest request, MdlBBadrecord mdlBBadrecord){
        User currentUser= FebsUtil.getCurrentUser();
        return this.iMdlBBadrecordService.findMdlBBadrecords(request, mdlBBadrecord).getRecords();
    }
    @GetMapping("statistic")
    public Map<String, Object> List2(QueryRequest request, MdlBBadrecord mdlBBadrecord){
        User currentUser= FebsUtil.getCurrentUser();
        //mdlBBadrecord.setDeptName(currentUser.getCode());
        return getDataTable(this.iMdlBBadrecordService.findMdlBBadrecordList(request, mdlBBadrecord));
    }

/**
 * 添加
 * @param  mdlBBadrecord
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("mdlBBadrecord:add")
public void addMdlBBadrecord(@Valid MdlBBadrecord mdlBBadrecord)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        mdlBBadrecord.setCreateUserId(currentUser.getUserId());
        mdlBBadrecord.setDeptName(currentUser.getCode());
        mdlBBadrecord.setUserName(currentUser.getUsername());
        mdlBBadrecord.setName(currentUser.getRealname());
        this.iMdlBBadrecordService.createMdlBBadrecord(mdlBBadrecord);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param mdlBBadrecord
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("mdlBBadrecord:update")
public void updateMdlBBadrecord(@Valid MdlBBadrecord mdlBBadrecord)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      mdlBBadrecord.setModifyUserId(currentUser.getUserId());
        this.iMdlBBadrecordService.updateMdlBBadrecord(mdlBBadrecord);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("mdlBBadrecord:delete")
public void deleteMdlBBadrecords(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iMdlBBadrecordService.deleteMdlBBadrecords(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("mdlBBadrecord:export")
public void export(QueryRequest request, MdlBBadrecord mdlBBadrecord, HttpServletResponse response) throws FebsException {
        try {
        List<MdlBBadrecord> mdlBBadrecords = this.iMdlBBadrecordService.findMdlBBadrecords(request, mdlBBadrecord).getRecords();
        ExcelKit.$Export(MdlBBadrecord.class, response).downXlsx(mdlBBadrecords, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }
@RequestMapping(value = "downTemplate", method = RequestMethod.POST)
@RequiresPermissions("mdlBBadrecord:import")
public void downTemplate(HttpServletResponse response) {
        List<MdlBBadrecord> publishList = new ArrayList<>();
        ExcelKit.$Export(MdlBBadrecord.class, response).downXlsx(publishList, true);
        }
@RequestMapping(value = "import", method = RequestMethod.POST)
@RequiresPermissions("mdlBBadrecord:import")
public ResponseEntity<?> importUser(@RequestParam MultipartFile file)
        throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<MdlBBadrecord> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        List<Map<String, Object>> resultList = Lists.newArrayList();

        User currentUser=FebsUtil.getCurrentUser();


        ExcelKit.$Import(MdlBBadrecord.class)
        .readXlsx(file.getInputStream(), new ExcelReadHandler<MdlBBadrecord>() {

@Override
public void onSuccess(int sheetIndex, int rowIndex, MdlBBadrecord entity) {
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
        for (MdlBBadrecord mdlBBadrecordImport:successList
        ) {
    MdlBBadrecord mdlBBadrecord =new MdlBBadrecord();
        BeanUtil.copyProperties(mdlBBadrecordImport,mdlBBadrecord, CopyOptions.create().setIgnoreNullValue(true));
        this.iMdlBBadrecordService.createMdlBBadrecord(mdlBBadrecord);
        }
        }

        resultList.add(MapUtil.of("data", successList));
        resultList.add(MapUtil.of("haveError", !CollectionUtil.isEmpty(errorList)));
        resultList.add(MapUtil.of("error", errorList));
        resultList.add(MapUtil.of("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L));
        return ResponseEntity.ok(resultList);
        }
@GetMapping("/{id}")
public MdlBBadrecord detail(@NotBlank(message = "{required}") @PathVariable String id) {
    MdlBBadrecord mdlBBadrecord=this.iMdlBBadrecordService.getById(id);
        return mdlBBadrecord;
        }
        }