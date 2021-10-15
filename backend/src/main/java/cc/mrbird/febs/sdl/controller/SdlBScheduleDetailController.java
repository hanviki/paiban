package cc.mrbird.febs.sdl.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.sdl.service.ISdlBScheduleDetailService;
import cc.mrbird.febs.sdl.entity.SdlBScheduleDetail;

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
@RequestMapping("sdlBScheduleDetail")

public class SdlBScheduleDetailController extends BaseController{

private String message;
@Autowired
public ISdlBScheduleDetailService iSdlBScheduleDetailService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'排班子表明细','/dca/SdlBScheduleDetail/SdlBScheduleDetail','dca/SdlBScheduleDetail/SdlBScheduleDetail','sdlBScheduleDetail:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'排班子表明细新增','sdlBScheduleDetail:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'排班子表明细编辑','sdlBScheduleDetail:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'排班子表明细删除','sdlBScheduleDetail:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param sdlBScheduleDetail 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("sdlBScheduleDetail:view")
public Map<String, Object> List(QueryRequest request, SdlBScheduleDetail sdlBScheduleDetail){
        return getDataTable(this.iSdlBScheduleDetailService.findSdlBScheduleDetails(request, sdlBScheduleDetail));
        }

/**
 * 添加
 * @param  sdlBScheduleDetail
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("sdlBScheduleDetail:add")
public void addSdlBScheduleDetail(@Valid SdlBScheduleDetail sdlBScheduleDetail)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        sdlBScheduleDetail.setCreateUserId(currentUser.getUserId());
        this.iSdlBScheduleDetailService.createSdlBScheduleDetail(sdlBScheduleDetail);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param sdlBScheduleDetail
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("sdlBScheduleDetail:update")
public void updateSdlBScheduleDetail(@Valid SdlBScheduleDetail sdlBScheduleDetail)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      sdlBScheduleDetail.setModifyUserId(currentUser.getUserId());
        this.iSdlBScheduleDetailService.updateSdlBScheduleDetail(sdlBScheduleDetail);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("sdlBScheduleDetail:delete")
public void deleteSdlBScheduleDetails(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iSdlBScheduleDetailService.deleteSdlBScheduleDetails(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("sdlBScheduleDetail:export")
public void export(QueryRequest request, SdlBScheduleDetail sdlBScheduleDetail, HttpServletResponse response) throws FebsException {
        try {
        List<SdlBScheduleDetail> sdlBScheduleDetails = this.iSdlBScheduleDetailService.findSdlBScheduleDetails(request, sdlBScheduleDetail).getRecords();
        ExcelKit.$Export(SdlBScheduleDetail.class, response).downXlsx(sdlBScheduleDetails, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public SdlBScheduleDetail detail(@NotBlank(message = "{required}") @PathVariable String id) {
    SdlBScheduleDetail sdlBScheduleDetail=this.iSdlBScheduleDetailService.getById(id);
        return sdlBScheduleDetail;
        }
        }