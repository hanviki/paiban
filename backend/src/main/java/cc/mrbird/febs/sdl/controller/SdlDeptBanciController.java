package cc.mrbird.febs.sdl.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.sdl.service.ISdlDeptBanciService;
import cc.mrbird.febs.sdl.entity.SdlDeptBanci;

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
@RequestMapping("sdlDeptBanci")

public class SdlDeptBanciController extends BaseController{

private String message;
@Autowired
public ISdlDeptBanciService iSdlDeptBanciService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/dca/SdlDeptBanci/SdlDeptBanci','dca/SdlDeptBanci/SdlDeptBanci','sdlDeptBanci:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'新增','sdlDeptBanci:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'编辑','sdlDeptBanci:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'删除','sdlDeptBanci:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param sdlDeptBanci 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("sdlDeptBanci:view")
public Map<String, Object> List(QueryRequest request, SdlDeptBanci sdlDeptBanci){
        return getDataTable(this.iSdlDeptBanciService.findSdlDeptBancis(request, sdlDeptBanci));
        }

/**
 * 添加
 * @param  sdlDeptBanci
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("sdlDeptBanci:add")
public void addSdlDeptBanci(@Valid SdlDeptBanci sdlDeptBanci)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      //  sdlDeptBanci.setCreateUserId(currentUser.getUserId());
        this.iSdlDeptBanciService.createSdlDeptBanci(sdlDeptBanci);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param sdlDeptBanci
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("sdlDeptBanci:update")
public void updateSdlDeptBanci(@Valid SdlDeptBanci sdlDeptBanci)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
   //   sdlDeptBanci.setModifyUserId(currentUser.getUserId());
        this.iSdlDeptBanciService.updateSdlDeptBanci(sdlDeptBanci);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("sdlDeptBanci:delete")
public void deleteSdlDeptBancis(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iSdlDeptBanciService.deleteSdlDeptBancis(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("sdlDeptBanci:export")
public void export(QueryRequest request, SdlDeptBanci sdlDeptBanci, HttpServletResponse response) throws FebsException {
        try {
        List<SdlDeptBanci> sdlDeptBancis = this.iSdlDeptBanciService.findSdlDeptBancis(request, sdlDeptBanci).getRecords();
        ExcelKit.$Export(SdlDeptBanci.class, response).downXlsx(sdlDeptBancis, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public SdlDeptBanci detail(@NotBlank(message = "{required}") @PathVariable String id) {
    SdlDeptBanci sdlDeptBanci=this.iSdlDeptBanciService.getById(id);
        return sdlDeptBanci;
        }
        }