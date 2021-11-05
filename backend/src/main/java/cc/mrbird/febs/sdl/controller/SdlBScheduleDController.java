package cc.mrbird.febs.sdl.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.sdl.entity.*;
import cc.mrbird.febs.sdl.service.*;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
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
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author viki
 * @since 2021-10-13
 */
@Slf4j
@Validated
@RestController
@RequestMapping("sdlBScheduleD")

public class SdlBScheduleDController extends BaseController {

    private String message;
    @Autowired
    public ISdlBScheduleDService iSdlBScheduleDService;

    @Autowired
    public ISdlDeptBanciService iSdlDeptBanciService;

    @Autowired
    public ISdlDBanciService iSdlDBanciService;

    @Autowired
    public ISdlBUserService iSdlBUserService;

    @Autowired
    private ISdlBScheduleService iSdlBScheduleService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'排班子表','/dca/SdlBScheduleD/SdlBScheduleD','dca/SdlBScheduleD/SdlBScheduleD','sdlBScheduleD:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'排班子表新增','sdlBScheduleD:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'排班子表编辑','sdlBScheduleD:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'排班子表删除','sdlBScheduleD:delete',1,1,NOW())
 */


    /**
     * 分页查询数据
     *
     * @param request       分页信息
     * @param sdlBScheduleD 查询条件
     * @return
     */
    @GetMapping
    @RequiresPermissions("sdlBScheduleD:view")
    public Map<String, Object> List(QueryRequest request, SdlBScheduleD sdlBScheduleD) {
        return getDataTable(this.iSdlBScheduleDService.findSdlBScheduleDs(request, sdlBScheduleD));
    }

    @GetMapping("zizhi")
    public List<SdlBScheduleD> List_zizhi(SdlBScheduleD sdlBScheduleD) {
        User currentUser = FebsUtil.getCurrentUser();
        sdlBScheduleD.setDeptId(currentUser.getDeptId());
        List<SdlBScheduleD> zizhiList = this.iSdlBScheduleDService.getPaiBanZizhi(sdlBScheduleD);
        LambdaQueryWrapper<SdlBScheduleD> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SdlBScheduleD::getDeptId, currentUser.getDeptId());
        List<SdlBScheduleD> sdlBScheduleDList = this.iSdlBScheduleDService.list(queryWrapper);
        zizhiList.forEach(p -> {
                    List<SdlBScheduleD> subList = sdlBScheduleDList.stream().filter(
                            t -> t.getBqId().equals(p.getBqId()) && t.getZizhiId().equals(p.getZizhiId())
                            &&  t.getBaseId().equals(sdlBScheduleD.getBaseId())
                    ).collect(Collectors.toList());
                    p.setDynamicData(subList);
                }

        );
        return zizhiList;
    }
    @GetMapping("zizhidept")
    public List<SdlBScheduleD> List_zizhi2(SdlBScheduleD sdlBScheduleD) {
        User currentUser = FebsUtil.getCurrentUser();
       // sdlBScheduleD.setDeptId(currentUser.getDeptId());
        List<SdlBScheduleD> zizhiList = this.iSdlBScheduleDService.getPaiBanZizhi(sdlBScheduleD);
        LambdaQueryWrapper<SdlBScheduleD> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SdlBScheduleD::getDeptId, sdlBScheduleD.getDeptId());
        List<SdlBScheduleD> sdlBScheduleDList = this.iSdlBScheduleDService.list(queryWrapper);
        zizhiList.forEach(p -> {
                    List<SdlBScheduleD> subList = sdlBScheduleDList.stream().filter(
                            t -> t.getBqId().equals(p.getBqId()) && t.getZizhiId().equals(p.getZizhiId())
                                    &&  t.getBaseId().equals(sdlBScheduleD.getBaseId())
                    ).collect(Collectors.toList());
                    p.setDynamicData(subList);
                }

        );
        return zizhiList;
    }

    @GetMapping("banci")
    public List<SdlDeptBanci> List_banci(SdlBScheduleD sdlBScheduleD) {
        User currentUser = FebsUtil.getCurrentUser();
        sdlBScheduleD.setDeptId(currentUser.getDeptId());

        LambdaQueryWrapper<SdlDeptBanci> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SdlDeptBanci::getDeptId, currentUser.getDeptId());
        List<SdlDeptBanci> sdlDeptBanciList = iSdlDeptBanciService.list(queryWrapper);
        return sdlDeptBanciList;
    }
    @GetMapping("bancidept")
    public List<SdlDeptBanci> List_banci2(SdlBScheduleD sdlBScheduleD) {
        User currentUser = FebsUtil.getCurrentUser();
       // sdlBScheduleD.setDeptId(currentUser.getDeptId());

        LambdaQueryWrapper<SdlDeptBanci> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SdlDeptBanci::getDeptId, sdlBScheduleD.getDeptId());
        List<SdlDeptBanci> sdlDeptBanciList = iSdlDeptBanciService.list(queryWrapper);
        return sdlDeptBanciList;
    }

    /**
     * 添加
     *
     * @param sdlBScheduleD
     * @return
     */
    @Log("新增/按钮")
    @PostMapping
    @RequiresPermissions("sdlBScheduleD:add")
    public void addSdlBScheduleD(@Valid SdlBScheduleD sdlBScheduleD) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            sdlBScheduleD.setCreateUserId(currentUser.getUserId());
            this.iSdlBScheduleDService.createSdlBScheduleD(sdlBScheduleD);
        } catch (Exception e) {
            message = "新增/按钮失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("新增/按钮")
    @PostMapping("add")
    public void addSdlBScheduleD_new(@Valid String jsonStr, String startDate, String endDate) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            List<SdlBScheduleD> list = JSON.parseObject(jsonStr, new TypeReference<List<SdlBScheduleD>>() {
            });
            if(list.size()>0) {
                List<SdlDBanci> banciList = this.iSdlDBanciService.list();
                LambdaQueryWrapper<SdlBUser> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.apply("sdl_b_user.dept_id in (select t_dept.dept_id from t_dept where t_dept.DEPT_ID='" + currentUser.getDeptId() + "' or t_dept.PARENT_ID='" + currentUser.getDeptId() + "' )");
                queryWrapper.ne(SdlBUser::getState, 0);//只显示2或者3的
                List<SdlBUser> users = this.iSdlBUserService.list(queryWrapper);
                this.iSdlBScheduleDService.deleteByDeptAndDate(currentUser.getDeptId(), startDate, endDate);
                list.forEach(sdlBScheduleD -> {
                    sdlBScheduleD.setCreateUserId(currentUser.getUserId());
                    List<String> userAccounts = Arrays.asList(sdlBScheduleD.getAccountId().replace("[", "").replace("]", "").replace("\"", "").split(","));
                    String userAccountNames = users.stream().filter(p -> userAccounts.contains(p.getUserAccount())).map(p ->p.getUserAccount()+"_"+ p.getUserAccountName()).collect(Collectors.joining(",", "", ""));
                    sdlBScheduleD.setAccountName(userAccountNames);
                    String banciName = banciList.stream().filter(p -> p.getId().equals(sdlBScheduleD.getBanciId())).map(p -> p.getMuduleName()).findFirst().get();
                    sdlBScheduleD.setBanci(banciName);
                    this.iSdlBScheduleDService.createSdlBScheduleD(sdlBScheduleD);
                });
                this.iSdlBScheduleService.updateStateById(list.get(0).getBaseId(), 1);//更改状态为已提交，补登申请的状态改为NULL
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
     * @param sdlBScheduleD
     * @return
     */
    @Log("修改")
    @PutMapping
    @RequiresPermissions("sdlBScheduleD:update")
    public void updateSdlBScheduleD(@Valid SdlBScheduleD sdlBScheduleD) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            sdlBScheduleD.setModifyUserId(currentUser.getUserId());
            this.iSdlBScheduleDService.updateSdlBScheduleD(sdlBScheduleD);
        } catch (Exception e) {
            message = "修改失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @Log("删除")
    @DeleteMapping("/{ids}")
    @RequiresPermissions("sdlBScheduleD:delete")
    public void deleteSdlBScheduleDs(@NotBlank(message = "{required}") @PathVariable String ids) throws FebsException {
        try {
            String[] arr_ids = ids.split(StringPool.COMMA);
            this.iSdlBScheduleDService.deleteSdlBScheduleDs(arr_ids);
        } catch (Exception e) {
            message = "删除失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excel")
    @RequiresPermissions("sdlBScheduleD:export")
    public void export(QueryRequest request, SdlBScheduleD sdlBScheduleD, HttpServletResponse response) throws FebsException {
        try {
            List<SdlBScheduleD> sdlBScheduleDs = this.iSdlBScheduleDService.findSdlBScheduleDs(request, sdlBScheduleD).getRecords();
            ExcelKit.$Export(SdlBScheduleD.class, response).downXlsx(sdlBScheduleDs, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @GetMapping("/{id}")
    public SdlBScheduleD detail(@NotBlank(message = "{required}") @PathVariable String id) {
        SdlBScheduleD sdlBScheduleD = this.iSdlBScheduleDService.getById(id);
        return sdlBScheduleD;
    }
}