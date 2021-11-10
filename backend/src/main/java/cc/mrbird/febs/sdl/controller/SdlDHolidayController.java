package cc.mrbird.febs.sdl.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.sdl.service.ISdlDHolidayService;
import cc.mrbird.febs.sdl.entity.SdlDHoliday;

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
 * @since 2021-11-10
 */
@Slf4j
@Validated
@RestController
@RequestMapping("sdlDHoliday")

public class SdlDHolidayController extends BaseController{

private String message;
@Autowired
public ISdlDHolidayService iSdlDHolidayService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'节假日','/sdl/SdlDHoliday/SdlDHoliday','sdl/SdlDHoliday/SdlDHoliday','sdlDHoliday:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'节假日新增','sdlDHoliday:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'节假日编辑','sdlDHoliday:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'节假日删除','sdlDHoliday:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param sdlDHoliday 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("sdlDHoliday:view")
public Map<String, Object> List(QueryRequest request, SdlDHoliday sdlDHoliday){
        return getDataTable(this.iSdlDHolidayService.findSdlDHolidays(request, sdlDHoliday));
        }

/**
 * 添加
 * @param  sdlDHoliday
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("sdlDHoliday:add")
public void addSdlDHoliday(@Valid SdlDHoliday sdlDHoliday)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        sdlDHoliday.setCreateUserId(currentUser.getUserId());
        this.iSdlDHolidayService.createSdlDHoliday(sdlDHoliday);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param sdlDHoliday
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("sdlDHoliday:update")
public void updateSdlDHoliday(@Valid SdlDHoliday sdlDHoliday)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      sdlDHoliday.setModifyUserId(currentUser.getUserId());
        this.iSdlDHolidayService.updateSdlDHoliday(sdlDHoliday);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("sdlDHoliday:delete")
public void deleteSdlDHolidays(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iSdlDHolidayService.deleteSdlDHolidays(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("sdlDHoliday:export")
public void export(QueryRequest request, SdlDHoliday sdlDHoliday, HttpServletResponse response) throws FebsException {
        try {
        List<SdlDHoliday> sdlDHolidays = this.iSdlDHolidayService.findSdlDHolidays(request, sdlDHoliday).getRecords();
        ExcelKit.$Export(SdlDHoliday.class, response).downXlsx(sdlDHolidays, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public SdlDHoliday detail(@NotBlank(message = "{required}") @PathVariable String id) {
    SdlDHoliday sdlDHoliday=this.iSdlDHolidayService.getById(id);
        return sdlDHoliday;
        }
        }