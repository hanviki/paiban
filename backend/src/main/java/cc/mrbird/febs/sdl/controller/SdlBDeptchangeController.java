package cc.mrbird.febs.sdl.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.sdl.service.ISdlBDeptchangeService;
import cc.mrbird.febs.sdl.entity.SdlBDeptchange;

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
 * @since 2021-10-14
 */
@Slf4j
@Validated
@RestController
@RequestMapping("sdlBDeptchange")

public class SdlBDeptchangeController extends BaseController{

private String message;
@Autowired
public ISdlBDeptchangeService iSdlBDeptchangeService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/sdl/SdlBDeptchange/SdlBDeptchange','sdl/SdlBDeptchange/SdlBDeptchange','sdlBDeptchange:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'新增','sdlBDeptchange:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'编辑','sdlBDeptchange:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'删除','sdlBDeptchange:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param sdlBDeptchange 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, SdlBDeptchange sdlBDeptchange){
        return getDataTable(this.iSdlBDeptchangeService.findSdlBDeptchanges(request, sdlBDeptchange));
        }

/**
 * 添加
 * @param  sdlBDeptchange
 * @return
 */
@Log("移入用户")
@PostMapping
public void addSdlBDeptchange(@Valid SdlBDeptchange sdlBDeptchange)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        sdlBDeptchange.setCreateUserId(currentUser.getUserId());

        this.iSdlBDeptchangeService.createSdlBDeptchange(sdlBDeptchange);
        }catch(Exception e){
        message="移入失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param sdlBDeptchange
 * @return
 */
@Log("移出用户")
@PostMapping("delete")
public void updateSdlBDeptchange(@Valid SdlBDeptchange sdlBDeptchange)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        sdlBDeptchange.setModifyUserId(currentUser.getUserId());
        this.iSdlBDeptchangeService.updateSdlBDeptchange(sdlBDeptchange);
        }catch(Exception e){
        message="操作失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("移出用户")
@DeleteMapping("/{ids}")
public void deleteSdlBDeptchanges(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iSdlBDeptchangeService.deleteSdlBDeptchanges(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("sdlBDeptchange:export")
public void export(QueryRequest request, SdlBDeptchange sdlBDeptchange, HttpServletResponse response) throws FebsException {
        try {
        List<SdlBDeptchange> sdlBDeptchanges = this.iSdlBDeptchangeService.findSdlBDeptchanges(request, sdlBDeptchange).getRecords();
        ExcelKit.$Export(SdlBDeptchange.class, response).downXlsx(sdlBDeptchanges, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public SdlBDeptchange detail(@NotBlank(message = "{required}") @PathVariable String id) {
    SdlBDeptchange sdlBDeptchange=this.iSdlBDeptchangeService.getById(id);
        return sdlBDeptchange;
        }
        }