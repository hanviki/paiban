
package cc.mrbird.febs.mdl.controller;


import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.FebsResponse;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.mdl.entity.MdlBMedicalchinaD;
import cc.mrbird.febs.mdl.service.IMdlBMedicalchinaDService;
import cc.mrbird.febs.mdl.service.IMdlBMedicalchinaService;
import cc.mrbird.febs.mdl.entity.MdlBMedicalchina;

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
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.beust.jcommander.internal.Lists;
import com.wuwenze.poi.ExcelKit;
import com.wuwenze.poi.handler.ExcelReadHandler;
import com.wuwenze.poi.pojo.ExcelErrorField;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author viki
 * @since 2022-06-08
 */
@Slf4j
@Validated
@RestController
@RequestMapping("mdlBMedicalchina")

public class MdlBMedicalchinaController extends BaseController {

    private String message;
    @Autowired
    public IMdlBMedicalchinaService iMdlBMedicalchinaService;
    @Autowired
    public IMdlBMedicalchinaDService iMdlBMedicalchinaDService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/ass/MdlBMedicalchina/MdlBMedicalchina','ass/MdlBMedicalchina/MdlBMedicalchina','mdlBMedicalchina:view','fork',0,1,NOW());
 SELECT @maxId:=MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'新增','mdlBMedicalchina:add',1,1,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'编辑','mdlBMedicalchina:update',1,2,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'删除','mdlBMedicalchina:delete',1,3,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'导出','mdlBMedicalchina:export',1,4,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'导入','mdlBMedicalchina:import',1,5,NOW());
 */


    /**
     * 分页查询数据
     *
     * @param request          分页信息
     * @param mdlBMedicalchina 查询条件
     * @return
     */
    @GetMapping
    public Map<String, Object> List(QueryRequest request, MdlBMedicalchina mdlBMedicalchina) {
        IPage<MdlBMedicalchina> page = this.iMdlBMedicalchinaService.findMdlBMedicalchinas(request, mdlBMedicalchina);
        List<MdlBMedicalchina> list = page.getRecords();
        List<String> ids = list.stream().map(p -> p.getId()).collect(Collectors.toList());
        LambdaQueryWrapper<MdlBMedicalchinaD> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(MdlBMedicalchinaD::getBaseId, ids);
        List<MdlBMedicalchinaD> subList = this.iMdlBMedicalchinaDService.list(queryWrapper);
        list.parallelStream().forEach(item -> {
            String userAccounts = subList.stream().filter(p -> p.getBaseId().equals(item.getId())).map(t -> t.getUserAccountName()).collect(Collectors.joining(","));
            item.setFileId(userAccounts);
        });
        return getDataTable(page);
    }

    /**
     * 添加
     *
     * @param mdlBMedicalchina
     * @return
     */
    @Log("新增/按钮")
    @PostMapping
    public void addMdlBMedicalchina(@Valid MdlBMedicalchina mdlBMedicalchina, String subData) throws FebsException {
        try {

            User currentUser = FebsUtil.getCurrentUser();
            mdlBMedicalchina.setCreateUserId(currentUser.getUserId());
            this.iMdlBMedicalchinaService.createMdlBMedicalchina(mdlBMedicalchina);
            if (StringUtils.isNotEmpty(subData)) {
                List<MdlBMedicalchinaD> list = JSON.parseObject(subData, new TypeReference<List<MdlBMedicalchinaD>>() {
                });
                list.stream().forEach(item -> {
                    item.setBaseId(mdlBMedicalchina.getId());
                    iMdlBMedicalchinaDService.createMdlBMedicalchinaD(item);
                });
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
     * @param mdlBMedicalchina
     * @return
     */
    @Log("修改")
    @PutMapping
    public void updateMdlBMedicalchina(@Valid MdlBMedicalchina mdlBMedicalchina, String subData) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            mdlBMedicalchina.setModifyUserId(currentUser.getUserId());
            this.iMdlBMedicalchinaService.updateMdlBMedicalchina(mdlBMedicalchina);
            LambdaQueryWrapper<MdlBMedicalchinaD> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(MdlBMedicalchinaD::getBaseId, mdlBMedicalchina.getId());
            this.iMdlBMedicalchinaDService.remove(queryWrapper);
            if (StringUtils.isNotEmpty(subData)) {
                List<MdlBMedicalchinaD> list = JSON.parseObject(subData, new TypeReference<List<MdlBMedicalchinaD>>() {
                });
                list.stream().forEach(item -> {
                    item.setBaseId(mdlBMedicalchina.getId());
                    iMdlBMedicalchinaDService.createMdlBMedicalchinaD(item);
                });
            }
        } catch (Exception e) {
            message = "修改失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @Log("删除")
    @DeleteMapping("/{ids}")
    public void deleteMdlBMedicalchinas(@NotBlank(message = "{required}") @PathVariable String ids) throws FebsException {
        try {
            String[] arr_ids = ids.split(StringPool.COMMA);
            this.iMdlBMedicalchinaService.deleteMdlBMedicalchinas(arr_ids);
        } catch (Exception e) {
            message = "删除失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excel")
    public void export(QueryRequest request, MdlBMedicalchina mdlBMedicalchina, HttpServletResponse response) throws FebsException {
        try {
            request.setPageNum(1);
            request.setPageSize(10000);
            request.setIsSearchCount(false);
            List<MdlBMedicalchina> mdlBMedicalchinas = this.iMdlBMedicalchinaService.findMdlBMedicalchinas(request, mdlBMedicalchina).getRecords();
            List<String> ids = mdlBMedicalchinas.stream().map(MdlBMedicalchina::getId).collect(Collectors.toList());
            LambdaQueryWrapper<MdlBMedicalchinaD> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(MdlBMedicalchinaD::getBaseId, ids);
            List<MdlBMedicalchinaD> subList = this.iMdlBMedicalchinaDService.list(queryWrapper);
            mdlBMedicalchinas.parallelStream().forEach(item -> {
                String userAccounts = subList.stream().filter(p -> p.getBaseId().equals(item.getId())).map(t -> t.getUserAccountName()).collect(Collectors.joining(","));
                item.setFileId(userAccounts);
            });
            ExcelKit.$Export(MdlBMedicalchina.class, response).downXlsx(mdlBMedicalchinas, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @RequestMapping(value = "downTemplate", method = RequestMethod.POST)
    @RequiresPermissions("mdlBMedicalchina:import")
    public void downTemplate(HttpServletResponse response) {
        List<MdlBMedicalchina> publishList = new ArrayList<>();
        ExcelKit.$Export(MdlBMedicalchina.class, response).downXlsx(publishList, true);
    }

    @RequestMapping(value = "import", method = RequestMethod.POST)
    @RequiresPermissions("mdlBMedicalchina:import")
    public ResponseEntity<?> importUser(@RequestParam MultipartFile file)
            throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<MdlBMedicalchina> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        List<Map<String, Object>> resultList = Lists.newArrayList();

        User currentUser = FebsUtil.getCurrentUser();


        ExcelKit.$Import(MdlBMedicalchina.class)
                .readXlsx(file.getInputStream(), new ExcelReadHandler<MdlBMedicalchina>() {

                    @Override
                    public void onSuccess(int sheetIndex, int rowIndex, MdlBMedicalchina entity) {
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
            for (MdlBMedicalchina mdlBMedicalchinaImport : successList
            ) {
                MdlBMedicalchina mdlBMedicalchina = new MdlBMedicalchina();
                BeanUtil.copyProperties(mdlBMedicalchinaImport, mdlBMedicalchina, CopyOptions.create().setIgnoreNullValue(true));
                this.iMdlBMedicalchinaService.createMdlBMedicalchina(mdlBMedicalchina);
            }
        }

        resultList.add(MapUtil.of("data", successList));
        resultList.add(MapUtil.of("haveError", !CollectionUtil.isEmpty(errorList)));
        resultList.add(MapUtil.of("error", errorList));
        resultList.add(MapUtil.of("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L));
        return ResponseEntity.ok(resultList);
    }

    @GetMapping("/{id}")
    public MdlBMedicalchina detail(@NotBlank(message = "{required}") @PathVariable String id) {
        MdlBMedicalchina mdlBMedicalchina = this.iMdlBMedicalchinaService.getById(id);
        return mdlBMedicalchina;
    }

    @GetMapping("findCheckD")
    public FebsResponse findCheckD(String baseId) {
        ModelMap map = new ModelMap();
        int success = 0;
        List<MdlBMedicalchinaD> xxbBCheckDataList = new ArrayList<>();
        try {
            LambdaQueryWrapper<MdlBMedicalchinaD> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(MdlBMedicalchinaD::getBaseId, baseId);
            wrapper.eq(MdlBMedicalchinaD::getIsDeletemark, 1);
            xxbBCheckDataList = this.iMdlBMedicalchinaDService.list(wrapper);
            if (xxbBCheckDataList.size() > 0) {
                xxbBCheckDataList = xxbBCheckDataList.stream().sorted(Comparator.comparing(MdlBMedicalchinaD::getCreateTime)).collect(Collectors.toList());
            }

            success = 1;
        } catch (Exception e) {
            message = "查询失败.";
            log.error(message, e);
        }

        map.put("success", success);
        map.put("message", message);
        map.put("data", xxbBCheckDataList);
        return new FebsResponse().data(map);
    }

    @GetMapping("delCheckD")
    public FebsResponse getDelId(String id) {
        ModelMap map = new ModelMap();
        int success = 0;
        try {
            this.iMdlBMedicalchinaDService.removeById(id);
            success = 1;
        } catch (Exception e) {
            message = "删除失败.";
            log.error(message, e);
        }
        map.put("success", success);
        map.put("message", message);
        return new FebsResponse().data(map);
    }

    @GetMapping("addCheckD")
    @RequiresPermissions("xxbBCheck:add")
    public FebsResponse addCheckD(MdlBMedicalchinaD checkData) {
        ModelMap map = new ModelMap();
        int success = 0;
        try {
            this.iMdlBMedicalchinaDService.createMdlBMedicalchinaD(checkData);
            if (checkData.getId() != null) {
                success = 1;
            } else {
                checkData = new MdlBMedicalchinaD();
            }
        } catch (Exception e) {
            message = "删除失败.";
            log.error(message, e);
        }
        map.put("success", success);
        map.put("message", message);
        map.put("data", checkData);
        return new FebsResponse().data(map);
    }
}