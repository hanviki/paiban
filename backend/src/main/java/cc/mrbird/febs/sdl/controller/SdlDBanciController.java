package cc.mrbird.febs.sdl.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.sdl.service.ISdlDBanciService;
import cc.mrbird.febs.sdl.entity.SdlDBanci;

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
@RequestMapping("sdlDBanci")

public class SdlDBanciController extends BaseController{

private String message;
@Autowired
public ISdlDBanciService iSdlDBanciService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'系列名称','/dca/SdlDBanci/SdlDBanci','dca/SdlDBanci/SdlDBanci','sdlDBanci:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'系列名称新增','sdlDBanci:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'系列名称编辑','sdlDBanci:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'系列名称删除','sdlDBanci:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param sdlDBanci 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("sdlDBanci:view")
public Map<String, Object> List(QueryRequest request, SdlDBanci sdlDBanci){
        return getDataTable(this.iSdlDBanciService.findSdlDBancis(request, sdlDBanci));
        }

/**
 * 添加
 * @param  sdlDBanci
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("sdlDBanci:add")
public void addSdlDBanci(@Valid SdlDBanci sdlDBanci)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        sdlDBanci.setCreateUserId(currentUser.getUserId());
        this.iSdlDBanciService.createSdlDBanci(sdlDBanci);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param sdlDBanci
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("sdlDBanci:update")
public void updateSdlDBanci(@Valid SdlDBanci sdlDBanci)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      sdlDBanci.setModifyUserId(currentUser.getUserId());
        this.iSdlDBanciService.updateSdlDBanci(sdlDBanci);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("sdlDBanci:delete")
public void deleteSdlDBancis(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iSdlDBanciService.deleteSdlDBancis(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("sdlDBanci:export")
public void export(QueryRequest request, SdlDBanci sdlDBanci, HttpServletResponse response) throws FebsException {
        try {
        List<SdlDBanci> sdlDBancis = this.iSdlDBanciService.findSdlDBancis(request, sdlDBanci).getRecords();
        ExcelKit.$Export(SdlDBanci.class, response).downXlsx(sdlDBancis, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public SdlDBanci detail(@NotBlank(message = "{required}") @PathVariable String id) {
    SdlDBanci sdlDBanci=this.iSdlDBanciService.getById(id);
        return sdlDBanci;
        }
        }