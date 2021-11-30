package cc.mrbird.febs.sdl.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.sdl.service.ISdlBControlService;
import cc.mrbird.febs.sdl.entity.SdlBControl;

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
 * @since 2021-11-15
 */
@Slf4j
@Validated
@RestController
@RequestMapping("sdlBControl")

public class SdlBControlController extends BaseController{

private String message;
@Autowired
public ISdlBControlService iSdlBControlService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/sdl/SdlBControl/SdlBControl','sdl/SdlBControl/SdlBControl','sdlBControl:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'新增','sdlBControl:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'编辑','sdlBControl:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'删除','sdlBControl:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param sdlBControl 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("sdlBControl:view")
public Map<String, Object> List(QueryRequest request, SdlBControl sdlBControl){
        return getDataTable(this.iSdlBControlService.findSdlBControls(request, sdlBControl));
        }

/**
 * 添加
 * @param  sdlBControl
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("sdlBControl:add")
public void addSdlBControl(@Valid SdlBControl sdlBControl)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        sdlBControl.setCreateUserId(currentUser.getUserId());
        this.iSdlBControlService.createSdlBControl(sdlBControl);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param sdlBControl
 * @return
 */
@Log("修改")
@PutMapping
public void updateSdlBControl(@Valid SdlBControl sdlBControl)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      sdlBControl.setModifyUserId(currentUser.getUserId());
        this.iSdlBControlService.updateSdlBControl(sdlBControl);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("sdlBControl:delete")
public void deleteSdlBControls(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iSdlBControlService.deleteSdlBControls(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("sdlBControl:export")
public void export(QueryRequest request, SdlBControl sdlBControl, HttpServletResponse response) throws FebsException {
        try {
        List<SdlBControl> sdlBControls = this.iSdlBControlService.findSdlBControls(request, sdlBControl).getRecords();
        ExcelKit.$Export(SdlBControl.class, response).downXlsx(sdlBControls, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public SdlBControl detail(@NotBlank(message = "{required}") @PathVariable String id) {
    SdlBControl sdlBControl=this.iSdlBControlService.getById(id);
        return sdlBControl;
        }
        }