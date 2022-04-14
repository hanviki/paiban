
package cc.mrbird.febs.mdl.controller;


import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.mdl.service.IMdlDBadscoreService;
import cc.mrbird.febs.mdl.entity.MdlDBadscore;

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
@RequestMapping("mdlDBadscore")

public class MdlDBadscoreController extends BaseController{

private String message;
@Autowired
public IMdlDBadscoreService iMdlDBadscoreService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'不良执业行为','/ass/MdlDBadscore/MdlDBadscore','ass/MdlDBadscore/MdlDBadscore','mdlDBadscore:view','fork',0,1,NOW());
 SELECT @maxId:=MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'不良执业行为新增','mdlDBadscore:add',1,1,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'不良执业行为编辑','mdlDBadscore:update',1,2,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'不良执业行为删除','mdlDBadscore:delete',1,3,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'不良执业行为导出','mdlDBadscore:export',1,4,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'不良执业行为导入','mdlDBadscore:import',1,5,NOW());
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param mdlDBadscore 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("mdlDBadscore:view")
public Map<String, Object> List(QueryRequest request, MdlDBadscore mdlDBadscore){
        return getDataTable(this.iMdlDBadscoreService.findMdlDBadscores(request, mdlDBadscore));
        }

/**
 * 添加
 * @param  mdlDBadscore
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("mdlDBadscore:add")
public void addMdlDBadscore(@Valid MdlDBadscore mdlDBadscore)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        mdlDBadscore.setCreateUserId(currentUser.getUserId());
        this.iMdlDBadscoreService.createMdlDBadscore(mdlDBadscore);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param mdlDBadscore
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("mdlDBadscore:update")
public void updateMdlDBadscore(@Valid MdlDBadscore mdlDBadscore)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      mdlDBadscore.setModifyUserId(currentUser.getUserId());
        this.iMdlDBadscoreService.updateMdlDBadscore(mdlDBadscore);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("mdlDBadscore:delete")
public void deleteMdlDBadscores(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iMdlDBadscoreService.deleteMdlDBadscores(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("mdlDBadscore:export")
public void export(QueryRequest request, MdlDBadscore mdlDBadscore, HttpServletResponse response) throws FebsException {
        try {
        List<MdlDBadscore> mdlDBadscores = this.iMdlDBadscoreService.findMdlDBadscores(request, mdlDBadscore).getRecords();
        ExcelKit.$Export(MdlDBadscore.class, response).downXlsx(mdlDBadscores, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }
@RequestMapping(value = "downTemplate", method = RequestMethod.POST)
@RequiresPermissions("mdlDBadscore:import")
public void downTemplate(HttpServletResponse response) {
        List<MdlDBadscore> publishList = new ArrayList<>();
        ExcelKit.$Export(MdlDBadscore.class, response).downXlsx(publishList, true);
        }
@RequestMapping(value = "import", method = RequestMethod.POST)
@RequiresPermissions("mdlDBadscore:import")
public ResponseEntity<?> importUser(@RequestParam MultipartFile file)
        throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<MdlDBadscore> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        List<Map<String, Object>> resultList = Lists.newArrayList();

        User currentUser=FebsUtil.getCurrentUser();


        ExcelKit.$Import(MdlDBadscore.class)
        .readXlsx(file.getInputStream(), new ExcelReadHandler<MdlDBadscore>() {

@Override
public void onSuccess(int sheetIndex, int rowIndex, MdlDBadscore entity) {
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
        for (MdlDBadscore mdlDBadscoreImport:successList
        ) {
    MdlDBadscore mdlDBadscore =new MdlDBadscore();
        BeanUtil.copyProperties(mdlDBadscoreImport,mdlDBadscore, CopyOptions.create().setIgnoreNullValue(true));
        this.iMdlDBadscoreService.createMdlDBadscore(mdlDBadscore);
        }
        }

        resultList.add(MapUtil.of("data", successList));
        resultList.add(MapUtil.of("haveError", !CollectionUtil.isEmpty(errorList)));
        resultList.add(MapUtil.of("error", errorList));
        resultList.add(MapUtil.of("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L));
        return ResponseEntity.ok(resultList);
        }
@GetMapping("/{id}")
public MdlDBadscore detail(@NotBlank(message = "{required}") @PathVariable String id) {
    MdlDBadscore mdlDBadscore=this.iMdlDBadscoreService.getById(id);
        return mdlDBadscore;
        }
        }