package cc.mrbird.febs.sdl.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.sdl.service.ISdlBScheduleService;
import cc.mrbird.febs.sdl.entity.SdlBSchedule;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
import cn.hutool.core.date.DateUtil;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author viki
 * @since 2021-10-13
 */
@Slf4j
@Validated
@RestController
@RequestMapping("sdlBSchedule")

public class SdlBScheduleController extends BaseController {

    private String message;
    @Autowired
    public ISdlBScheduleService iSdlBScheduleService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'排班主表','/dca/SdlBSchedule/SdlBSchedule','dca/SdlBSchedule/SdlBSchedule','sdlBSchedule:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'排班主表新增','sdlBSchedule:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'排班主表编辑','sdlBSchedule:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'排班主表删除','sdlBSchedule:delete',1,1,NOW())
 */


    /**
     * 分页查询数据
     *
     * @param request      分页信息
     * @param sdlBSchedule 查询条件
     * @return
     */
    @GetMapping
    @RequiresPermissions("sdlBSchedule:view")
    public Map<String, Object> List(QueryRequest request, SdlBSchedule sdlBSchedule) {
        User currentUser = FebsUtil.getCurrentUser();
        sdlBSchedule.setDeptId(currentUser.getDeptId());
        return getDataTable(this.iSdlBScheduleService.findSdlBSchedules(request, sdlBSchedule));
    }

    /**
     * 添加
     *
     * @param sdlBSchedule
     * @return
     */
    @Log("新增/按钮")
    @PostMapping
    @RequiresPermissions("sdlBSchedule:add")
    public void addSdlBSchedule(@Valid SdlBSchedule sdlBSchedule) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            LambdaQueryWrapper<SdlBSchedule> queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.eq(SdlBSchedule::getStartDate,sdlBSchedule.getStartDate());
            queryWrapper.eq(SdlBSchedule::getDeptId,currentUser.getDeptId());
            int cou= this.iSdlBScheduleService.count(queryWrapper);
            if(cou>0){
                throw new FebsException("此周已经开启了排班，请勿重新添加");
            }


            sdlBSchedule.setCreateUserId(currentUser.getUserId());
            sdlBSchedule.setDeptId(currentUser.getDeptId());
            sdlBSchedule.setIsDeletemark(1);
            sdlBSchedule.setDeptName(currentUser.getDeptName());
            sdlBSchedule.setUserNo(currentUser.getUsername());
            sdlBSchedule.setState(0);//未处理
            if (sdlBSchedule.getStartDate().before(DateUtil.beginOfWeek(new Date()))) {
                sdlBSchedule.setStateApply(0);//未发起补登申请
                sdlBSchedule.setStateApplyFlag(0);
                sdlBSchedule.setStateBudeng(1);//补登
            } else {
                sdlBSchedule.setStateBudeng(0);//正常
            }
            this.iSdlBScheduleService.createSdlBSchedule(sdlBSchedule);
        } catch (Exception e) {
            message = "新增/按钮失败";
            log.error(message, e);
            throw new FebsException(e.getMessage());
        }
    }

    /**
     * 修改
     *
     * @param sdlBSchedule
     * @return
     */
    @Log("修改")
    @PutMapping
    @RequiresPermissions("sdlBSchedule:update")
    public void updateSdlBSchedule(@Valid SdlBSchedule sdlBSchedule) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            sdlBSchedule.setModifyUserId(currentUser.getUserId());
            this.iSdlBScheduleService.updateSdlBSchedule(sdlBSchedule);
        } catch (Exception e) {
            message = "修改失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 申请
     *
     * @param sdlBSchedule
     * @return
     */
    @Log("申请")
    @PutMapping("apply")
    public void updateSdlBSchedule_apply(@Valid SdlBSchedule sdlBSchedule) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            sdlBSchedule.setModifyUserId(currentUser.getUserId());
            sdlBSchedule.setStateApply(1);//申请中
            sdlBSchedule.setStateApplyFlag(1);
            sdlBSchedule.setStateBudeng(1);//补登

            this.iSdlBScheduleService.updateSdlBSchedule(sdlBSchedule);
        } catch (Exception e) {
            message = "发起申请失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @Log("删除")
    @DeleteMapping("/{ids}")
    @RequiresPermissions("sdlBSchedule:delete")
    public void deleteSdlBSchedules(@NotBlank(message = "{required}") @PathVariable String ids) throws FebsException {
        try {
            String[] arr_ids = ids.split(StringPool.COMMA);
            this.iSdlBScheduleService.deleteSdlBSchedules(arr_ids);
        } catch (Exception e) {
            message = "删除失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excel")
    @RequiresPermissions("sdlBSchedule:export")
    public void export(QueryRequest request, SdlBSchedule sdlBSchedule, HttpServletResponse response) throws FebsException {
        try {
            List<SdlBSchedule> sdlBSchedules = this.iSdlBScheduleService.findSdlBSchedules(request, sdlBSchedule).getRecords();
            ExcelKit.$Export(SdlBSchedule.class, response).downXlsx(sdlBSchedules, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @GetMapping("/{id}")
    public SdlBSchedule detail(@NotBlank(message = "{required}") @PathVariable String id) {
        SdlBSchedule sdlBSchedule = this.iSdlBScheduleService.getById(id);
        return sdlBSchedule;
    }
}