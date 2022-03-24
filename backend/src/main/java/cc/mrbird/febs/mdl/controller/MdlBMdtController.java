
package cc.mrbird.febs.mdl.controller;


import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.mdl.entity.MdlBMdtD;
import cc.mrbird.febs.mdl.service.IMdlBMdtDService;
import cc.mrbird.febs.mdl.service.IMdlBMdtService;
import cc.mrbird.febs.mdl.entity.MdlBMdt;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
import cn.hutool.Hutool;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
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

/**
 * @author viki
 * @since 2022-03-21
 */
@Slf4j
@Validated
@RestController
@RequestMapping("mdlBMdt")

public class MdlBMdtController extends BaseController {

    private String message;
    @Autowired
    public IMdlBMdtService iMdlBMdtService;
    @Autowired
    public IMdlBMdtDService iMdlBMdtDService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/ass/MdlBMdt/MdlBMdt','ass/MdlBMdt/MdlBMdt','mdlBMdt:view','fork',0,1,NOW());
 SELECT @maxId:=MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'新增','mdlBMdt:add',1,1,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'编辑','mdlBMdt:update',1,2,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'删除','mdlBMdt:delete',1,3,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'导出','mdlBMdt:export',1,4,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'导入','mdlBMdt:import',1,5,NOW());
 */


    /**
     * 分页查询数据
     *
     * @param request 分页信息
     * @param mdlBMdt 查询条件
     * @return
     */
    @GetMapping
    public Map<String, Object> List(QueryRequest request, MdlBMdt mdlBMdt) {
        return getDataTable(this.iMdlBMdtService.findMdlBMdts(request, mdlBMdt));
    }

    /**
     * 添加
     *
     * @param mdlBMdt
     * @return
     */
    @Log("新增/按钮")
    @PostMapping
    public void addMdlBMdt(@Valid MdlBMdt mdlBMdt, String hx, String qt) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            mdlBMdt.setCreateUserId(currentUser.getUserId());
            this.iMdlBMdtService.createMdlBMdt(mdlBMdt);

            List<MdlBMdtD> list = JSON.parseObject(hx, new TypeReference<List<MdlBMdtD>>() {
            });
            List<MdlBMdtD> list2 = JSON.parseObject(qt, new TypeReference<List<MdlBMdtD>>() {
            });
            list.forEach(p->{
                p.setBaseId(mdlBMdt.getId());
                p.setCreateTime(new Date());
                p.setCreateUserId(currentUser.getUserId());
                this.iMdlBMdtDService.createMdlBMdtD(p);
            });
            list2.forEach(p->{
                p.setBaseId(mdlBMdt.getId());
                p.setCreateTime(new Date());
                p.setCreateUserId(currentUser.getUserId());
                this.iMdlBMdtDService.createMdlBMdtD(p);
            });
        } catch (Exception e) {
            message = "新增/按钮失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 修改
     *
     * @param mdlBMdt
     * @return
     */
    @Log("修改")
    @PutMapping
    public void updateMdlBMdt(@Valid MdlBMdt mdlBMdt, String hx, String qt) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            mdlBMdt.setModifyUserId(currentUser.getUserId());
            this.iMdlBMdtService.updateMdlBMdt(mdlBMdt);
            List<MdlBMdtD> list = JSON.parseObject(hx, new TypeReference<List<MdlBMdtD>>() {
            });
            List<MdlBMdtD> list2 = JSON.parseObject(qt, new TypeReference<List<MdlBMdtD>>() {
            });
            this.iMdlBMdtService.deleteByBaseId(mdlBMdt.getId());
            list.forEach(p->{
                p.setBaseId(mdlBMdt.getId());
                p.setCreateTime(new Date());
                p.setCreateUserId(currentUser.getUserId());
                this.iMdlBMdtDService.createMdlBMdtD(p);
            });
            list2.forEach(p->{
                p.setBaseId(mdlBMdt.getId());
                p.setCreateTime(new Date());
                p.setCreateUserId(currentUser.getUserId());
                this.iMdlBMdtDService.createMdlBMdtD(p);
            });
        } catch (Exception e) {
            message = "修改失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @Log("删除")
    @DeleteMapping("/{ids}")
    public void deleteMdlBMdts(@NotBlank(message = "{required}") @PathVariable String ids) throws FebsException {
        try {
            String[] arr_ids = ids.split(StringPool.COMMA);
            this.iMdlBMdtService.deleteMdlBMdts(arr_ids);
        } catch (Exception e) {
            message = "删除失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excel")
    @RequiresPermissions("mdlBMdt:export")
    public void export(QueryRequest request, MdlBMdt mdlBMdt, HttpServletResponse response) throws FebsException {
        try {
            List<MdlBMdt> mdlBMdts = this.iMdlBMdtService.findMdlBMdts(request, mdlBMdt).getRecords();
            ExcelKit.$Export(MdlBMdt.class, response).downXlsx(mdlBMdts, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @RequestMapping(value = "downTemplate", method = RequestMethod.POST)
    @RequiresPermissions("mdlBMdt:import")
    public void downTemplate(HttpServletResponse response) {
        List<MdlBMdt> publishList = new ArrayList<>();
        ExcelKit.$Export(MdlBMdt.class, response).downXlsx(publishList, true);
    }

    @RequestMapping(value = "import", method = RequestMethod.POST)
    @RequiresPermissions("mdlBMdt:import")
    public ResponseEntity<?> importUser(@RequestParam MultipartFile file)
            throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<MdlBMdt> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        List<Map<String, Object>> resultList = Lists.newArrayList();

        User currentUser = FebsUtil.getCurrentUser();


        ExcelKit.$Import(MdlBMdt.class)
                .readXlsx(file.getInputStream(), new ExcelReadHandler<MdlBMdt>() {

                    @Override
                    public void onSuccess(int sheetIndex, int rowIndex, MdlBMdt entity) {
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
            for (MdlBMdt mdlBMdtImport : successList
            ) {
                MdlBMdt mdlBMdt = new MdlBMdt();
                BeanUtil.copyProperties(mdlBMdtImport, mdlBMdt, CopyOptions.create().setIgnoreNullValue(true));
                this.iMdlBMdtService.createMdlBMdt(mdlBMdt);
            }
        }

        resultList.add(MapUtil.of("data", successList));
        resultList.add(MapUtil.of("haveError", !CollectionUtil.isEmpty(errorList)));
        resultList.add(MapUtil.of("error", errorList));
        resultList.add(MapUtil.of("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L));
        return ResponseEntity.ok(resultList);
    }

    @GetMapping("/{id}")
    public MdlBMdt detail(@NotBlank(message = "{required}") @PathVariable String id) {
        MdlBMdt mdlBMdt = this.iMdlBMdtService.getById(id);
        return mdlBMdt;
    }
}