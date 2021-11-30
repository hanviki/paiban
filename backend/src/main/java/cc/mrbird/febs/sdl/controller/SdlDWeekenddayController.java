package cc.mrbird.febs.sdl.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.sdl.service.ISdlDWeekenddayService;
import cc.mrbird.febs.sdl.entity.SdlDWeekendday;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
import java.util.stream.Collectors;

/**
 *
 * @author viki
 * @since 2021-11-11
 */
@Slf4j
@Validated
@RestController
@RequestMapping("sdlDWeekendday")

public class SdlDWeekenddayController extends BaseController{

private String message;
@Autowired
public ISdlDWeekenddayService iSdlDWeekenddayService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'周六日表','/sdl/SdlDWeekendday/SdlDWeekendday','sdl/SdlDWeekendday/SdlDWeekendday','sdlDWeekendday:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'周六日表新增','sdlDWeekendday:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'周六日表编辑','sdlDWeekendday:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'周六日表删除','sdlDWeekendday:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param sdlDWeekendday 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("sdlDWeekendday:view")
public Map<String, Object> List(QueryRequest request, SdlDWeekendday sdlDWeekendday){
        return getDataTable(this.iSdlDWeekenddayService.findSdlDWeekenddays(request, sdlDWeekendday));
        }

    @GetMapping("all")
    public List<String> List2(){
        return this.iSdlDWeekenddayService.list().stream().map(p->p.getId()).collect(Collectors.toList());
    }
/**
 * 添加
 * @param  sdlDWeekendday
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addSdlDWeekendday(@Valid SdlDWeekendday sdlDWeekendday,int flag)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        sdlDWeekendday.setCreateUserId(currentUser.getUserId());
        if(flag==1) { //新增
            this.iSdlDWeekenddayService.createSdlDWeekendday(sdlDWeekendday);
        }
        else { //删除
            this.iSdlDWeekenddayService.removeById(sdlDWeekendday.getId());
        }
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param sdlDWeekendday
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("sdlDWeekendday:update")
public void updateSdlDWeekendday(@Valid SdlDWeekendday sdlDWeekendday)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      sdlDWeekendday.setModifyUserId(currentUser.getUserId());
        this.iSdlDWeekenddayService.updateSdlDWeekendday(sdlDWeekendday);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("sdlDWeekendday:delete")
public void deleteSdlDWeekenddays(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iSdlDWeekenddayService.deleteSdlDWeekenddays(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("sdlDWeekendday:export")
public void export(QueryRequest request, SdlDWeekendday sdlDWeekendday, HttpServletResponse response) throws FebsException {
        try {
        List<SdlDWeekendday> sdlDWeekenddays = this.iSdlDWeekenddayService.findSdlDWeekenddays(request, sdlDWeekendday).getRecords();
        ExcelKit.$Export(SdlDWeekendday.class, response).downXlsx(sdlDWeekenddays, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public SdlDWeekendday detail(@NotBlank(message = "{required}") @PathVariable String id) {
    SdlDWeekendday sdlDWeekendday=this.iSdlDWeekenddayService.getById(id);
        return sdlDWeekendday;
        }
        }