package cc.mrbird.febs.sdl.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.sdl.service.ISdlDeptZizhiService;
import cc.mrbird.febs.sdl.entity.SdlDeptZizhi;

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
@RequestMapping("sdlDeptZizhi")

public class SdlDeptZizhiController extends BaseController{

private String message;
@Autowired
public ISdlDeptZizhiService iSdlDeptZizhiService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/dca/SdlDeptZizhi/SdlDeptZizhi','dca/SdlDeptZizhi/SdlDeptZizhi','sdlDeptZizhi:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'新增','sdlDeptZizhi:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'编辑','sdlDeptZizhi:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'删除','sdlDeptZizhi:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param sdlDeptZizhi 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("sdlDeptZizhi:view")
public Map<String, Object> List(QueryRequest request, SdlDeptZizhi sdlDeptZizhi){
        return getDataTable(this.iSdlDeptZizhiService.findSdlDeptZizhis(request, sdlDeptZizhi));
        }

/**
 * 添加
 * @param  sdlDeptZizhi
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("sdlDeptZizhi:add")
public void addSdlDeptZizhi(@Valid SdlDeptZizhi sdlDeptZizhi)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
     //   sdlDeptZizhi.setCreateUserId(currentUser.getUserId());
        this.iSdlDeptZizhiService.createSdlDeptZizhi(sdlDeptZizhi);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param sdlDeptZizhi
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("sdlDeptZizhi:update")
public void updateSdlDeptZizhi(@Valid SdlDeptZizhi sdlDeptZizhi)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
  //    sdlDeptZizhi.setModifyUserId(currentUser.getUserId());
        this.iSdlDeptZizhiService.updateSdlDeptZizhi(sdlDeptZizhi);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("sdlDeptZizhi:delete")
public void deleteSdlDeptZizhis(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iSdlDeptZizhiService.deleteSdlDeptZizhis(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("sdlDeptZizhi:export")
public void export(QueryRequest request, SdlDeptZizhi sdlDeptZizhi, HttpServletResponse response) throws FebsException {
        try {
        List<SdlDeptZizhi> sdlDeptZizhis = this.iSdlDeptZizhiService.findSdlDeptZizhis(request, sdlDeptZizhi).getRecords();
        ExcelKit.$Export(SdlDeptZizhi.class, response).downXlsx(sdlDeptZizhis, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public SdlDeptZizhi detail(@NotBlank(message = "{required}") @PathVariable String id) {
    SdlDeptZizhi sdlDeptZizhi=this.iSdlDeptZizhiService.getById(id);
        return sdlDeptZizhi;
        }
        }