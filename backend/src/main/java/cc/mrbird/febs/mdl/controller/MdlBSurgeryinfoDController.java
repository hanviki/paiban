
package cc.mrbird.febs.mdl.controller;


import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.mdl.entity.MdlBSurgeryinfo;
import cc.mrbird.febs.mdl.entity.MdlDSurgery;
import cc.mrbird.febs.mdl.service.IMdlBSurgeryinfoDService;
import cc.mrbird.febs.mdl.entity.MdlBSurgeryinfoD;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.mdl.service.IMdlDSurgeryService;
import cc.mrbird.febs.system.domain.User;
import cn.hutool.Hutool;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.beust.jcommander.internal.Lists;
import com.wuwenze.poi.ExcelKit;
import com.wuwenze.poi.handler.ExcelReadHandler;
import com.wuwenze.poi.pojo.ExcelErrorField;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author viki
 * @since 2022-05-18
 */
@Slf4j
@Validated
@RestController
@RequestMapping("mdlBSurgeryinfoD")

public class MdlBSurgeryinfoDController extends BaseController {

    private String message;
    @Autowired
    public IMdlBSurgeryinfoDService iMdlBSurgeryinfoDService;

    @Autowired
    public IMdlDSurgeryService iMdlDSurgeryService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/ass/MdlBSurgeryinfoD/MdlBSurgeryinfoD','ass/MdlBSurgeryinfoD/MdlBSurgeryinfoD','mdlBSurgeryinfoD:view','fork',0,1,NOW());
 SELECT @maxId:=MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'新增','mdlBSurgeryinfoD:add',1,1,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'编辑','mdlBSurgeryinfoD:update',1,2,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'删除','mdlBSurgeryinfoD:delete',1,3,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'导出','mdlBSurgeryinfoD:export',1,4,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'导入','mdlBSurgeryinfoD:import',1,5,NOW());
 */


    /**
     * 分页查询数据
     *
     * @param request          分页信息
     * @param mdlBSurgeryinfoD 查询条件
     * @return
     */
    @GetMapping
    @RequiresPermissions("mdlBSurgeryinfoD:view")
    public Map<String, Object> List(QueryRequest request, MdlBSurgeryinfoD mdlBSurgeryinfoD) {
        return getDataTable(this.iMdlBSurgeryinfoDService.findMdlBSurgeryinfoDs(request, mdlBSurgeryinfoD));
    }

    @GetMapping("tree")
    public List<String> List2(Long baseId) {
        QueryRequest request =new QueryRequest();
        request.setPageNum(1);
        request.setPageSize(10000);
        MdlBSurgeryinfoD mdlBSurgeryinfoD= new MdlBSurgeryinfoD();
        mdlBSurgeryinfoD.setBaseId(baseId);
        request.setIsSearchCount(false);
        List<MdlBSurgeryinfoD> list = this.iMdlBSurgeryinfoDService.findMdlBSurgeryinfoDList(request,mdlBSurgeryinfoD).getRecords();
        return list.stream().map(roleMenu -> String.valueOf(roleMenu.getSugeryId())).collect(Collectors.toList());
    }

    /**
     * 添加
     *
     * @param mdlBSurgeryinfoD
     * @return
     */
    @Log("新增/按钮")
    @PostMapping
    public void addMdlBSurgeryinfoD(String baseId, String surgeryIds) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            LambdaQueryWrapper<MdlBSurgeryinfoD> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(MdlBSurgeryinfoD::getBaseId, baseId);
            this.iMdlBSurgeryinfoDService.remove(queryWrapper);
            String[] idList = surgeryIds.split(",");
            LambdaQueryWrapper<MdlDSurgery> queryWrapper2 = new LambdaQueryWrapper<>();
            queryWrapper2.in(MdlDSurgery::getId, idList);
            List<MdlDSurgery> mdlDSurgeryList = this.iMdlDSurgeryService.list(queryWrapper2);
            List<MdlBSurgeryinfoD> listAdd = new ArrayList<>();
            mdlDSurgeryList.forEach(item -> {
                MdlBSurgeryinfoD mdlBSurgeryinfoD = new MdlBSurgeryinfoD();
                mdlBSurgeryinfoD.setBaseId(Long.parseLong(baseId));
                mdlBSurgeryinfoD.setCode(item.getCode());
                mdlBSurgeryinfoD.setSugeryId(item.getId());
              //  mdlBSurgeryinfoD.setDeptNew(item.getDeptNew());
              //  mdlBSurgeryinfoD.setLb(item.getLb());
              //  mdlBSurgeryinfoD.setLevel(item.getLevel());
              //  mdlBSurgeryinfoD.setName(item.getName());
                mdlBSurgeryinfoD.setCreateTime(new Date());
                mdlBSurgeryinfoD.setIsDeletemark(1);
                listAdd.add(mdlBSurgeryinfoD);
            });
            this.iMdlBSurgeryinfoDService.saveBatch(listAdd);
        } catch (Exception e) {
            log.error(message, e);
            throw new FebsException(e.getMessage());
        }
    }

    /**
     * 修改
     *
     * @param mdlBSurgeryinfoD
     * @return
     */
    @Log("修改")
    @PutMapping
    @RequiresPermissions("mdlBSurgeryinfoD:update")
    public void updateMdlBSurgeryinfoD(@Valid MdlBSurgeryinfoD mdlBSurgeryinfoD) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            mdlBSurgeryinfoD.setModifyUserId(currentUser.getUserId());
            this.iMdlBSurgeryinfoDService.updateMdlBSurgeryinfoD(mdlBSurgeryinfoD);
        } catch (Exception e) {
            message = "修改失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @Log("删除")
    @DeleteMapping("/{ids}")
    @RequiresPermissions("mdlBSurgeryinfoD:delete")
    public void deleteMdlBSurgeryinfoDs(@NotBlank(message = "{required}") @PathVariable String ids) throws FebsException {
        try {
            String[] arr_ids = ids.split(StringPool.COMMA);
            MdlBSurgeryinfoD mdlBSurgeryinfoD= new MdlBSurgeryinfoD();
            mdlBSurgeryinfoD.setIsDeletemark(0);
            LambdaQueryWrapper<MdlBSurgeryinfoD> queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.in(MdlBSurgeryinfoD::getId,arr_ids);
            this.iMdlBSurgeryinfoDService.update(mdlBSurgeryinfoD,queryWrapper);
        } catch (Exception e) {
            message = "删除失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excel")
    @RequiresPermissions("mdlBSurgeryinfoD:export")
    public void export(QueryRequest request, MdlBSurgeryinfoD mdlBSurgeryinfoD, HttpServletResponse response) throws FebsException {
        try {
            List<MdlBSurgeryinfoD> mdlBSurgeryinfoDs = this.iMdlBSurgeryinfoDService.findMdlBSurgeryinfoDs(request, mdlBSurgeryinfoD).getRecords();
            ExcelKit.$Export(MdlBSurgeryinfoD.class, response).downXlsx(mdlBSurgeryinfoDs, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @RequestMapping(value = "downTemplate", method = RequestMethod.POST)
    @RequiresPermissions("mdlBSurgeryinfoD:import")
    public void downTemplate(HttpServletResponse response) {
        List<MdlBSurgeryinfoD> publishList = new ArrayList<>();
        ExcelKit.$Export(MdlBSurgeryinfoD.class, response).downXlsx(publishList, true);
    }

    @RequestMapping(value = "import", method = RequestMethod.POST)
    @RequiresPermissions("mdlBSurgeryinfoD:import")
    public ResponseEntity<?> importUser(@RequestParam MultipartFile file)
            throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<MdlBSurgeryinfoD> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        List<Map<String, Object>> resultList = Lists.newArrayList();

        User currentUser = FebsUtil.getCurrentUser();


        ExcelKit.$Import(MdlBSurgeryinfoD.class)
                .readXlsx(file.getInputStream(), new ExcelReadHandler<MdlBSurgeryinfoD>() {

                    @Override
                    public void onSuccess(int sheetIndex, int rowIndex, MdlBSurgeryinfoD entity) {
                        successList.add(entity); // 单行读取成功，加入入库队列。
                    }

                    @Override
                    public void onError(int sheetIndex, int rowIndex,
                                        java.util.List<ExcelErrorField> errorFields) {
                        // 读取数据失败，记录了当前行所有失败的数据
                        errorList.add(MapUtil.of("sheetIndex", sheetIndex));
                        errorList.add(MapUtil.of("rowIndex", rowIndex));
                        errorList.add(MapUtil.of("errorFields", errorFields));
                    }
                });

        // TODO: 执行successList的入库操作。
        if (CollectionUtil.isEmpty(errorList)) {
            for (MdlBSurgeryinfoD mdlBSurgeryinfoDImport : successList
            ) {
                MdlBSurgeryinfoD mdlBSurgeryinfoD = new MdlBSurgeryinfoD();
                BeanUtil.copyProperties(mdlBSurgeryinfoDImport, mdlBSurgeryinfoD, CopyOptions.create().setIgnoreNullValue(true));
                this.iMdlBSurgeryinfoDService.createMdlBSurgeryinfoD(mdlBSurgeryinfoD);
            }
        }

        resultList.add(MapUtil.of("data", successList));
        resultList.add(MapUtil.of("haveError", !CollectionUtil.isEmpty(errorList)));
        resultList.add(MapUtil.of("error", errorList));
        resultList.add(MapUtil.of("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L));
        return ResponseEntity.ok(resultList);
    }

    @GetMapping("/{id}")
    public MdlBSurgeryinfoD detail(@NotBlank(message = "{required}") @PathVariable String id) {
        MdlBSurgeryinfoD mdlBSurgeryinfoD = this.iMdlBSurgeryinfoDService.getById(id);
        return mdlBSurgeryinfoD;
    }
}