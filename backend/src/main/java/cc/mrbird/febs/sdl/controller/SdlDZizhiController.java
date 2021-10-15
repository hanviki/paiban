package cc.mrbird.febs.sdl.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.sdl.service.ISdlDZizhiService;
import cc.mrbird.febs.sdl.entity.SdlDZizhi;

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
@RequestMapping("sdlDZizhi")

public class SdlDZizhiController extends BaseController{

private String message;
@Autowired
public ISdlDZizhiService iSdlDZizhiService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'资质字典表','/dca/SdlDZizhi/SdlDZizhi','dca/SdlDZizhi/SdlDZizhi','sdlDZizhi:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'资质字典表新增','sdlDZizhi:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'资质字典表编辑','sdlDZizhi:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'资质字典表删除','sdlDZizhi:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param sdlDZizhi 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, SdlDZizhi sdlDZizhi){
        return getDataTable(this.iSdlDZizhiService.findSdlDZizhis(request, sdlDZizhi));
        }

/**
 * 添加
 * @param  sdlDZizhi
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("sdlDZizhi:add")
public void addSdlDZizhi(@Valid SdlDZizhi sdlDZizhi)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        sdlDZizhi.setCreateUserId(currentUser.getUserId());
        this.iSdlDZizhiService.createSdlDZizhi(sdlDZizhi);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param sdlDZizhi
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("sdlDZizhi:update")
public void updateSdlDZizhi(@Valid SdlDZizhi sdlDZizhi)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      sdlDZizhi.setModifyUserId(currentUser.getUserId());
        this.iSdlDZizhiService.updateSdlDZizhi(sdlDZizhi);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("sdlDZizhi:delete")
public void deleteSdlDZizhis(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iSdlDZizhiService.deleteSdlDZizhis(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("sdlDZizhi:export")
public void export(QueryRequest request, SdlDZizhi sdlDZizhi, HttpServletResponse response) throws FebsException {
        try {
        List<SdlDZizhi> sdlDZizhis = this.iSdlDZizhiService.findSdlDZizhis(request, sdlDZizhi).getRecords();
        ExcelKit.$Export(SdlDZizhi.class, response).downXlsx(sdlDZizhis, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public SdlDZizhi detail(@NotBlank(message = "{required}") @PathVariable String id) {
    SdlDZizhi sdlDZizhi=this.iSdlDZizhiService.getById(id);
        return sdlDZizhi;
        }
        }