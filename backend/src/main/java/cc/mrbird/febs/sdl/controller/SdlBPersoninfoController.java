package cc.mrbird.febs.sdl.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.sdl.service.ISdlBPersoninfoService;
import cc.mrbird.febs.sdl.entity.SdlBPersoninfo;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
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
 * @author viki
 * @since 2021-12-10
 */
@Slf4j
@Validated
@RestController
@RequestMapping("sdlBPersoninfo")

public class SdlBPersoninfoController extends BaseController {

    private String message;
    @Autowired
    public ISdlBPersoninfoService iSdlBPersoninfoService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/sdl/SdlBPersoninfo/SdlBPersoninfo','sdl/SdlBPersoninfo/SdlBPersoninfo','sdlBPersoninfo:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'新增','sdlBPersoninfo:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'编辑','sdlBPersoninfo:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'删除','sdlBPersoninfo:delete',1,1,NOW())
 */


    /**
     * 分页查询数据
     *
     * @param request        分页信息
     * @param sdlBPersoninfo 查询条件
     * @return
     */
    @GetMapping
    public Map<String, Object> List(QueryRequest request, SdlBPersoninfo sdlBPersoninfo) {
        return getDataTable(this.iSdlBPersoninfoService.findSdlBPersoninfos(request, sdlBPersoninfo));
    }


    @Log("新增/按钮")
    @PostMapping
    public void addSdlBPersoninfo(String jsonStr) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            List<SdlBPersoninfo> list = JSON.parseObject(jsonStr, new TypeReference<List<SdlBPersoninfo>>() {
            });
            this.iSdlBPersoninfoService.deleteByDeptId(currentUser.getDeptId());
            for (SdlBPersoninfo sdlBPersoninfo : list
            ) {
                sdlBPersoninfo.setDeptId(currentUser.getDeptId());
                sdlBPersoninfo.setDeptName(currentUser.getDeptName());
                sdlBPersoninfo.setCreateUserId(currentUser.getUserId());
                this.iSdlBPersoninfoService.createSdlBPersoninfo(sdlBPersoninfo);
            }

        } catch (Exception e) {
            message = "新增/按钮失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 修改
     *
     * @param sdlBPersoninfo
     * @return
     */
    @Log("修改")
    @PutMapping
    @RequiresPermissions("sdlBPersoninfo:update")
    public void updateSdlBPersoninfo(@Valid SdlBPersoninfo sdlBPersoninfo) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            sdlBPersoninfo.setModifyUserId(currentUser.getUserId());
            this.iSdlBPersoninfoService.updateSdlBPersoninfo(sdlBPersoninfo);
        } catch (Exception e) {
            message = "修改失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @Log("删除")
    @DeleteMapping("/{ids}")
    @RequiresPermissions("sdlBPersoninfo:delete")
    public void deleteSdlBPersoninfos(@NotBlank(message = "{required}") @PathVariable String ids) throws FebsException {
        try {
            String[] arr_ids = ids.split(StringPool.COMMA);
            this.iSdlBPersoninfoService.deleteSdlBPersoninfos(arr_ids);
        } catch (Exception e) {
            message = "删除失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excel")
    @RequiresPermissions("sdlBPersoninfo:export")
    public void export(QueryRequest request, SdlBPersoninfo sdlBPersoninfo, HttpServletResponse response) throws FebsException {
        try {
            List<SdlBPersoninfo> sdlBPersoninfos = this.iSdlBPersoninfoService.findSdlBPersoninfos(request, sdlBPersoninfo).getRecords();
            ExcelKit.$Export(SdlBPersoninfo.class, response).downXlsx(sdlBPersoninfos, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @GetMapping("/{id}")
    public SdlBPersoninfo detail(@NotBlank(message = "{required}") @PathVariable String id) {
        SdlBPersoninfo sdlBPersoninfo = this.iSdlBPersoninfoService.getById(id);
        return sdlBPersoninfo;
    }
}