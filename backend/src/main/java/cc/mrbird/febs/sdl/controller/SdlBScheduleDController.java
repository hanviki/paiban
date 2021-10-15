package cc.mrbird.febs.sdl.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.sdl.service.ISdlBScheduleDService;
import cc.mrbird.febs.sdl.entity.SdlBScheduleD;

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
@RequestMapping("sdlBScheduleD")

public class SdlBScheduleDController extends BaseController{

private String message;
@Autowired
public ISdlBScheduleDService iSdlBScheduleDService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'排班子表','/dca/SdlBScheduleD/SdlBScheduleD','dca/SdlBScheduleD/SdlBScheduleD','sdlBScheduleD:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'排班子表新增','sdlBScheduleD:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'排班子表编辑','sdlBScheduleD:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'排班子表删除','sdlBScheduleD:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param sdlBScheduleD 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("sdlBScheduleD:view")
public Map<String, Object> List(QueryRequest request, SdlBScheduleD sdlBScheduleD){
        return getDataTable(this.iSdlBScheduleDService.findSdlBScheduleDs(request, sdlBScheduleD));
        }

/**
 * 添加
 * @param  sdlBScheduleD
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("sdlBScheduleD:add")
public void addSdlBScheduleD(@Valid SdlBScheduleD sdlBScheduleD)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        sdlBScheduleD.setCreateUserId(currentUser.getUserId());
        this.iSdlBScheduleDService.createSdlBScheduleD(sdlBScheduleD);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param sdlBScheduleD
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("sdlBScheduleD:update")
public void updateSdlBScheduleD(@Valid SdlBScheduleD sdlBScheduleD)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      sdlBScheduleD.setModifyUserId(currentUser.getUserId());
        this.iSdlBScheduleDService.updateSdlBScheduleD(sdlBScheduleD);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("sdlBScheduleD:delete")
public void deleteSdlBScheduleDs(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iSdlBScheduleDService.deleteSdlBScheduleDs(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("sdlBScheduleD:export")
public void export(QueryRequest request, SdlBScheduleD sdlBScheduleD, HttpServletResponse response) throws FebsException {
        try {
        List<SdlBScheduleD> sdlBScheduleDs = this.iSdlBScheduleDService.findSdlBScheduleDs(request, sdlBScheduleD).getRecords();
        ExcelKit.$Export(SdlBScheduleD.class, response).downXlsx(sdlBScheduleDs, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public SdlBScheduleD detail(@NotBlank(message = "{required}") @PathVariable String id) {
    SdlBScheduleD sdlBScheduleD=this.iSdlBScheduleDService.getById(id);
        return sdlBScheduleD;
        }
        }