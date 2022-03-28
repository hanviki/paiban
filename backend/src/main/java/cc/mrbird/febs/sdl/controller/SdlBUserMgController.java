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
import cc.mrbird.febs.sdl.service.ISdlBUserMgService;
import cc.mrbird.febs.sdl.entity.SdlBUserMg;

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
@RequestMapping("sdlBUserMg")

public class SdlBUserMgController extends BaseController{

    private String message;
    @Autowired
    public ISdlBUserMgService iSdlBUserMgService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/sdl/SdlBUserMg/SdlBUserMg','sdl/SdlBUserMg/SdlBUserMg','sdlBUser:view','fork',0,1,NOW())
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
    public Map<String, Object> List(QueryRequest request, SdlBUserMg sdlBUser){
        return getDataTable(this.iSdlBUserMgService.findSdlBUserMgs(request, sdlBUser));
    }
    @GetMapping("search")
    public List<SdlBUserSearch> List(SdlBUserMg sdlBUser){
        List<SdlBUserMg> userList= this.iSdlBUserMgService.findSdlBUserMgs_search( sdlBUser);
        List<SdlBUserSearch> list= userList.stream().map(p->{
            SdlBUserSearch n= new SdlBUserSearch();
            n.setId(p.getId());
            n.setBirthday(p.getBirthday());
            n.setUserAccount(p.getUserAccount());
            n.setDeptNew(p.getDeptNew());
            n.setZhicheng(p.getZhicheng());
            n.setTel(p.getTelephone());
            n.setUserAccountName(p.getUserAccountName());
            return  n;
        }).collect(Collectors.toList());
        return  list;
    }
    @GetMapping("ywc")
    public Map<String, Object> List2(QueryRequest request, SdlBUserMg sdlBUser){
        return getDataTable(this.iSdlBUserMgService.findSdlBUserMgsYwc(request, sdlBUser));
    }

    /**
     * 医师执业资质 取数逻辑
     * @param request
     * @param sdlBUser
     * @return
     */
    @GetMapping("ywc2")
    public Map<String, Object> List12(QueryRequest request, SdlBUserMg sdlBUser){
        return getDataTable(this.iSdlBUserMgService.findSdlBUserMgsYwc2(request, sdlBUser));
    }
    @GetMapping("hz")
    public Map<String, Object> List3(QueryRequest request, SdlBUserMg sdlBUser){
        return getDataTable(this.iSdlBUserMgService.findSdlBUserMgsHz(request, sdlBUser));
    }
    @GetMapping("dept")
    public Map<String, Object> List_Dept(QueryRequest request, SdlBUserMg sdlBUser){
        User currentUser= FebsUtil.getCurrentUser();
        sdlBUser.setDeptId(currentUser.getDeptId());
        return getDataTable(this.iSdlBUserMgService.findSdlBUserMgs(request, sdlBUser));
    }
    @GetMapping("dept2")
    public List<CustomUser> List_Dept2(SdlBUserMg sdlBUser){
        User currentUser= FebsUtil.getCurrentUser();
        sdlBUser.setDeptId(currentUser.getDeptId());

        List<CustomUser> list= this.iSdlBUserMgService.findSdlBUserMgs(sdlBUser);

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
    public void addSdlBUserMg(@Valid SdlBUserMg sdlBUser)throws FebsException{
        try{
            User currentUser= FebsUtil.getCurrentUser();
            sdlBUser.setCreateUserId(currentUser.getUserId());
            this.iSdlBUserMgService.createSdlBUserMg(sdlBUser);
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
    public void updateSdlBUserMg(@Valid SdlBUserMg sdlBUser)throws FebsException{
        try{
            User currentUser= FebsUtil.getCurrentUser();
            sdlBUser.setModifyUserId(currentUser.getUserId());
            this.iSdlBUserMgService.updateSdlBUserMg(sdlBUser);
        }catch(Exception e){
            message="修改失败" ;
            log.error(message,e);
            throw new FebsException(message);
        }
    }


    @Log("删除")
    @DeleteMapping("/{ids}")
    @RequiresPermissions("sdlBUser:delete")
    public void deleteSdlBUserMgs(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
            String[]arr_ids=ids.split(StringPool.COMMA);
            this.iSdlBUserMgService.deleteSdlBUserMgs(arr_ids);
        }catch(Exception e){
            message="删除失败" ;
            log.error(message,e);
            throw new FebsException(message);
        }
    }
    @PostMapping("excel")
    public void export(QueryRequest request, SdlBUserMg sdlBUser,String dataJson,HttpServletResponse response)throws FebsException{
        try{
            request.setPageNum(1);
            request.setPageSize(20000);
            User currentUser = FebsUtil.getCurrentUser();


            sdlBUser.setDeptId(currentUser.getDeptId());
            sdlBUser.setIsDeletemark(1);
            request.setSortField("user_account");
            request.setSortOrder("ascend");
            List<SdlBUserMg> sdlBUserList=  this.iSdlBUserMgService.findSdlBUserMgs(request, sdlBUser).getRecords();


            //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
            ExportExcelUtils.exportCustomExcel_han(response, sdlBUserList,dataJson,"");
        }catch(Exception e){
            message="导出Excel失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excelYszy")
    public void excelYszy(QueryRequest request, SdlBUserMg sdlBUser,String dataJson,HttpServletResponse response)throws FebsException{
        try{
            request.setPageNum(1);
            request.setPageSize(20000);

            sdlBUser.setIsDeletemark(1);
            request.setSortField("user_account");
            request.setSortOrder("ascend");
            List<SdlBUserMg> sdlBUserList=  this.iSdlBUserMgService.findSdlBUserMgsYwc2(request, sdlBUser).getRecords();


            //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
            ExportExcelUtils.exportCustomExcel_han(response, sdlBUserList,dataJson,"");
        }catch(Exception e){
            message="导出Excel失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excelYwc")
    public void exportYwc(QueryRequest request, SdlBUserMg sdlBUser,String dataJson,HttpServletResponse response)throws FebsException{
        try{
            request.setPageNum(1);
            request.setPageSize(20000);

            sdlBUser.setIsDeletemark(1);
            request.setSortField("user_account");
            request.setSortOrder("ascend");
            List<SdlBUserMg> sdlBUserList=  this.iSdlBUserMgService.findSdlBUserMgsYwc(request, sdlBUser).getRecords();


            //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
            ExportExcelUtils.exportCustomExcel_han(response, sdlBUserList,dataJson,"");
        }catch(Exception e){
            message="导出Excel失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    @GetMapping("/{id}")
    public SdlBUserMg detail(@NotBlank(message = "{required}") @PathVariable String id) {
        SdlBUserMg sdlBUser=this.iSdlBUserMgService.getById(id);
        return sdlBUser;
    }
    @GetMapping("deptNew")
    public List<CustomDept> getAllDept(){
        return  this.iSdlBUserMgService.getDeptNew();
    }
}