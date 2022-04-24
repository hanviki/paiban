
package cc.mrbird.febs.xxb.controller;


import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.FebsResponse;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.xxb.service.IXxbBCheckDService;
import cc.mrbird.febs.xxb.entity.XxbBCheckD;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
import cn.hutool.Hutool;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.beust.jcommander.internal.Lists;
import com.wuwenze.poi.ExcelKit;
import com.wuwenze.poi.handler.ExcelReadHandler;
import com.wuwenze.poi.pojo.ExcelErrorField;
import lombok.extern.slf4j.Slf4j;
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
import java.util.List;
import java.util.Map;

/**
 * @author viki
 * @since 2022-03-29
 */
@Slf4j
@Validated
@RestController
@RequestMapping("xxbBCheckD")

public class XxbBCheckDController extends BaseController {

    private String message;
    @Autowired
    public IXxbBCheckDService iXxbBCheckDService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/ass/XxbBCheckD/XxbBCheckD','ass/XxbBCheckD/XxbBCheckD','xxbBCheckD:view','fork',0,1,NOW());
 SELECT @maxId:=MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'新增','xxbBCheckD:add',1,1,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'编辑','xxbBCheckD:update',1,2,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'删除','xxbBCheckD:delete',1,3,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'导出','xxbBCheckD:export',1,4,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'导入','xxbBCheckD:import',1,5,NOW());
 */


    /**
     * 分页查询数据
     *
     * @param request    分页信息
     * @param xxbBCheckD 查询条件
     * @return
     */
    @GetMapping
    @RequiresPermissions("xxbBCheckD:view")
    public Map<String, Object> List(QueryRequest request, XxbBCheckD xxbBCheckD) {
        return getDataTable(this.iXxbBCheckDService.findXxbBCheckDs(request, xxbBCheckD));
    }


    /**
     * 添加
     *
     * @param xxbBCheckD
     * @return
     */
    @Log("新增/按钮")
    @PostMapping
    @RequiresPermissions("xxbBCheckD:add")
    public void addXxbBCheckD(@Valid XxbBCheckD xxbBCheckD) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
//        xxbBCheckD.setCreateUserId(currentUser.getUserId());
            this.iXxbBCheckDService.createXxbBCheckD(xxbBCheckD);
        } catch (Exception e) {
            message = "新增/按钮失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 修改
     *
     * @param xxbBCheckD
     * @return
     */
    @Log("修改")
    @PutMapping
    @RequiresPermissions("xxbBCheckD:update")
    public void updateXxbBCheckD(@Valid XxbBCheckD xxbBCheckD) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
//      xxbBCheckD.setModifyUserId(currentUser.getUserId());
            this.iXxbBCheckDService.updateXxbBCheckD(xxbBCheckD);
        } catch (Exception e) {
            message = "修改失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @Log("删除")
    @DeleteMapping("/{ids}")
    @RequiresPermissions("xxbBCheckD:delete")
    public void deleteXxbBCheckDs(@NotBlank(message = "{required}") @PathVariable String ids) throws FebsException {
        try {
            String[] arr_ids = ids.split(StringPool.COMMA);
            this.iXxbBCheckDService.deleteXxbBCheckDs(arr_ids);
        } catch (Exception e) {
            message = "删除失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excel")
    @RequiresPermissions("xxbBCheckD:export")
    public void export(QueryRequest request, XxbBCheckD xxbBCheckD, HttpServletResponse response) throws FebsException {
        try {
            List<XxbBCheckD> xxbBCheckDs = this.iXxbBCheckDService.findXxbBCheckDs(request, xxbBCheckD).getRecords();
            ExcelKit.$Export(XxbBCheckD.class, response).downXlsx(xxbBCheckDs, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @RequestMapping(value = "downTemplate", method = RequestMethod.POST)
    @RequiresPermissions("xxbBCheckD:import")
    public void downTemplate(HttpServletResponse response) {
        List<XxbBCheckD> publishList = new ArrayList<>();
        ExcelKit.$Export(XxbBCheckD.class, response).downXlsx(publishList, true);
    }

    @RequestMapping(value = "import", method = RequestMethod.POST)
    @RequiresPermissions("xxbBCheckD:import")
    public ResponseEntity<?> importUser(@RequestParam MultipartFile file)
            throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<XxbBCheckD> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        List<Map<String, Object>> resultList = Lists.newArrayList();

        User currentUser = FebsUtil.getCurrentUser();


        ExcelKit.$Import(XxbBCheckD.class)
                .readXlsx(file.getInputStream(), new ExcelReadHandler<XxbBCheckD>() {

                    @Override
                    public void onSuccess(int sheetIndex, int rowIndex, XxbBCheckD entity) {
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
            for (XxbBCheckD xxbBCheckDImport : successList
            ) {
                XxbBCheckD xxbBCheckD = new XxbBCheckD();
                BeanUtil.copyProperties(xxbBCheckDImport, xxbBCheckD, CopyOptions.create().setIgnoreNullValue(true));
                this.iXxbBCheckDService.createXxbBCheckD(xxbBCheckD);
            }
        }

        resultList.add(MapUtil.of("data", successList));
        resultList.add(MapUtil.of("haveError", !CollectionUtil.isEmpty(errorList)));
        resultList.add(MapUtil.of("error", errorList));
        resultList.add(MapUtil.of("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L));
        return ResponseEntity.ok(resultList);
    }

    @GetMapping("/{id}")
    public XxbBCheckD detail(@NotBlank(message = "{required}") @PathVariable String id) {
        XxbBCheckD xxbBCheckD = this.iXxbBCheckDService.getById(id);
        return xxbBCheckD;
    }
}