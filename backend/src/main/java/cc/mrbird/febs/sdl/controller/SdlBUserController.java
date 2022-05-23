package cc.mrbird.febs.sdl.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.common.utils.ExportExcelUtils;
import cc.mrbird.febs.sdl.entity.CustomDept;
import cc.mrbird.febs.sdl.entity.CustomUser;
import cc.mrbird.febs.sdl.entity.SdlBUserSearch;
import cc.mrbird.febs.sdl.service.ISdlBUserService;
import cc.mrbird.febs.sdl.entity.SdlBUser;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
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
 * @since 2021-10-13
 */
@Slf4j
@Validated
@RestController
@RequestMapping("sdlBUser")

public class SdlBUserController extends BaseController{

private String message;
@Autowired
public ISdlBUserService iSdlBUserService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/sdl/SdlBUser/SdlBUser','sdl/SdlBUser/SdlBUser','sdlBUser:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'新增','sdlBUser:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'编辑','sdlBUser:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'删除','sdlBUser:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param sdlBUser 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, SdlBUser sdlBUser){
        return getDataTable(this.iSdlBUserService.findSdlBUsers(request, sdlBUser));
        }
    @GetMapping("search")
    public List<SdlBUserSearch> List(SdlBUser sdlBUser){
       List<SdlBUser> userList= this.iSdlBUserService.findSdlBUsers_search( sdlBUser);
        List<SdlBUserSearch> list= userList.stream().map(p->{
            SdlBUserSearch n= new SdlBUserSearch();
            n.setId(p.getId());
            n.setBirthday(p.getBirthday());
            n.setUserAccount(p.getUserAccount());
            n.setDeptNew(p.getDeptNew());
            n.setZhicheng(p.getZhicheng());
            n.setTel(p.getTelephone());
            n.setRylx(p.getRylx());
            n.setYggh(p.getYggh());
            n.setUserAccountName(p.getUserAccountName());
            n.setRenshizifw(p.getRenshizifw());
            return  n;
        }).collect(Collectors.toList());
        return  list;
    }
    @GetMapping("ywc")
    public Map<String, Object> List2(QueryRequest request, SdlBUser sdlBUser){
        return getDataTable(this.iSdlBUserService.findSdlBUsersYwc(request, sdlBUser));
    }

    /**
     * 医师执业资质 取数逻辑
     * @param request
     * @param sdlBUser
     * @return
     */
    @GetMapping("ywc2")
    public Map<String, Object> List12(QueryRequest request, SdlBUser sdlBUser){
        return getDataTable(this.iSdlBUserService.findSdlBUsersYwc2(request, sdlBUser));
    }
    @GetMapping("hz")
    public Map<String, Object> List3(QueryRequest request, SdlBUser sdlBUser){
        return getDataTable(this.iSdlBUserService.findSdlBUsersHz(request, sdlBUser));
    }
    @GetMapping("dept")
    public Map<String, Object> List_Dept(QueryRequest request, SdlBUser sdlBUser){
        User currentUser= FebsUtil.getCurrentUser();
        sdlBUser.setDeptId(currentUser.getDeptId());
        return getDataTable(this.iSdlBUserService.findSdlBUsers(request, sdlBUser));
    }
    @GetMapping("dept2")
    public List<CustomUser> List_Dept2(SdlBUser sdlBUser){
        User currentUser= FebsUtil.getCurrentUser();
        sdlBUser.setDeptId(currentUser.getDeptId());

        List<CustomUser> list= this.iSdlBUserService.findSdlBUsers(sdlBUser);

        return list;
    }

/**
 * 添加
 * @param  sdlBUser
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("sdlBUser:add")
public void addSdlBUser(@Valid SdlBUser sdlBUser)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        sdlBUser.setCreateUserId(currentUser.getUserId());
        this.iSdlBUserService.createSdlBUser(sdlBUser);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param sdlBUser
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("sdlBUser:update")
public void updateSdlBUser(@Valid SdlBUser sdlBUser)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      sdlBUser.setModifyUserId(currentUser.getUserId());
        this.iSdlBUserService.updateSdlBUser(sdlBUser);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("sdlBUser:delete")
public void deleteSdlBUsers(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iSdlBUserService.deleteSdlBUsers(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
    @PostMapping("excel")
    public void export(QueryRequest request, SdlBUser sdlBUser,String dataJson,HttpServletResponse response)throws FebsException{
        try{
            request.setPageNum(1);
            request.setPageSize(20000);
            User currentUser = FebsUtil.getCurrentUser();


            sdlBUser.setDeptId(currentUser.getDeptId());
            sdlBUser.setIsDeletemark(1);
            request.setSortField("user_account");
            request.setSortOrder("ascend");
            List<SdlBUser> sdlBUserList=  this.iSdlBUserService.findSdlBUsers(request, sdlBUser).getRecords();


            //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
            ExportExcelUtils.exportCustomExcel_han(response, sdlBUserList,dataJson,"");
        }catch(Exception e){
            message="导出Excel失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excelYszy")
    public void excelYszy(QueryRequest request, SdlBUser sdlBUser,String dataJson,HttpServletResponse response)throws FebsException{
        try{
            request.setPageNum(1);
            request.setPageSize(20000);

            sdlBUser.setIsDeletemark(1);
            request.setSortField("user_account");
            request.setSortOrder("ascend");
            List<SdlBUser> sdlBUserList=  this.iSdlBUserService.findSdlBUsersYwc2(request, sdlBUser).getRecords();


            //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
            ExportExcelUtils.exportCustomExcel_han(response, sdlBUserList,dataJson,"");
        }catch(Exception e){
            message="导出Excel失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excelYwc")
    public void exportYwc(QueryRequest request, SdlBUser sdlBUser,String dataJson,HttpServletResponse response)throws FebsException{
        try{
            request.setPageNum(1);
            request.setPageSize(20000);

            sdlBUser.setIsDeletemark(1);
            request.setSortField("user_account");
            request.setSortOrder("ascend");
            List<SdlBUser> sdlBUserList=  this.iSdlBUserService.findSdlBUsersYwc(request, sdlBUser).getRecords();


            //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
            ExportExcelUtils.exportCustomExcel_han(response, sdlBUserList,dataJson,"");
        }catch(Exception e){
            message="导出Excel失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    @GetMapping("/{id}")
public SdlBUser detail(@NotBlank(message = "{required}") @PathVariable String id) {
    SdlBUser sdlBUser=this.iSdlBUserService.getById(id);
        return sdlBUser;
        }
    @GetMapping("deptNew")
    public List<CustomDept> getAllDept(){
       return  this.iSdlBUserService.getDeptNew();
    }
        }