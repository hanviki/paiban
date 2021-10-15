package cc.mrbird.febs.sdl.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.sdl.service.ISdlBZizhiapplyService;
import cc.mrbird.febs.sdl.entity.SdlBZizhiapply;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author viki
 * @since 2021-10-13
 */
@Slf4j
@Validated
@RestController
@RequestMapping("sdlBZizhiapply")

public class SdlBZizhiapplyController extends BaseController{

private String message;
@Autowired
public ISdlBZizhiapplyService iSdlBZizhiapplyService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'资质变更申请','/dca/SdlBZizhiapply/SdlBZizhiapply','dca/SdlBZizhiapply/SdlBZizhiapply','sdlBZizhiapply:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'资质变更申请新增','sdlBZizhiapply:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'资质变更申请编辑','sdlBZizhiapply:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'资质变更申请删除','sdlBZizhiapply:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param sdlBZizhiapply 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("sdlBZizhiapply:view")
public Map<String, Object> List(QueryRequest request, SdlBZizhiapply sdlBZizhiapply){
        return getDataTable(this.iSdlBZizhiapplyService.findSdlBZizhiapplys(request, sdlBZizhiapply));
        }

/**
 * 添加
 * @param  sdlBZizhiapply
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("sdlBZizhiapply:add")
public void addSdlBZizhiapply(@Valid SdlBZizhiapply sdlBZizhiapply)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        sdlBZizhiapply.setCreateUserId(currentUser.getUserId());
        this.iSdlBZizhiapplyService.createSdlBZizhiapply(sdlBZizhiapply);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param sdlBZizhiapply
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("sdlBZizhiapply:update")
public void updateSdlBZizhiapply(@Valid SdlBZizhiapply sdlBZizhiapply)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      sdlBZizhiapply.setModifyUserId(currentUser.getUserId());
        this.iSdlBZizhiapplyService.updateSdlBZizhiapply(sdlBZizhiapply);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("sdlBZizhiapply:delete")
public void deleteSdlBZizhiapplys(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iSdlBZizhiapplyService.deleteSdlBZizhiapplys(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("sdlBZizhiapply:export")
public void export(QueryRequest request, SdlBZizhiapply sdlBZizhiapply, HttpServletResponse response) throws FebsException {
        try {
        List<SdlBZizhiapply> sdlBZizhiapplys = this.iSdlBZizhiapplyService.findSdlBZizhiapplys(request, sdlBZizhiapply).getRecords();
        ExcelKit.$Export(SdlBZizhiapply.class, response).downXlsx(sdlBZizhiapplys, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public SdlBZizhiapply detail(@NotBlank(message = "{required}") @PathVariable String id) {
    SdlBZizhiapply sdlBZizhiapply=this.iSdlBZizhiapplyService.getById(id);
        return sdlBZizhiapply;
        }
        }