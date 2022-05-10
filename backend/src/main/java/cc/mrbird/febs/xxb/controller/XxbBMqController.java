
package cc.mrbird.febs.xxb.controller;


import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.FebsResponse;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.common.utils.DocUtil;
import cc.mrbird.febs.xxb.entity.XxbBCheck;
import cc.mrbird.febs.xxb.service.IXxbBCheckService;
import cc.mrbird.febs.xxb.service.IXxbBMqService;
import cc.mrbird.febs.xxb.entity.XxbBMq;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
import cn.hutool.Hutool;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
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
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author viki
 * @since 2022-04-27
 */
@Slf4j
@Validated
@RestController
@RequestMapping("xxbBMq")

public class XxbBMqController extends BaseController{

private String message;
@Autowired
public IXxbBMqService iXxbBMqService;

    @Autowired
    public IXxbBCheckService iXxbBCheckService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'末期反馈表','/ass/XxbBMq/XxbBMq','ass/XxbBMq/XxbBMq','xxbBMq:view','fork',0,1,NOW());
 SELECT @maxId:=MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'末期反馈表新增','xxbBMq:add',1,1,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'末期反馈表编辑','xxbBMq:update',1,2,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'末期反馈表删除','xxbBMq:delete',1,3,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'末期反馈表导出','xxbBMq:export',1,4,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'末期反馈表导入','xxbBMq:import',1,5,NOW());
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param xxbBMq 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, XxbBMq xxbBMq){
        return getDataTable(this.iXxbBMqService.findXxbBMqs(request, xxbBMq));
        }

/**
 * 添加
 * @param  xxbBMq
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addXxbBMq(@Valid XxbBMq xxbBMq,int state)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        xxbBMq.setCreateUserId(currentUser.getUserId());


            if(StringUtils.isNotEmpty(xxbBMq.getId())){
                xxbBMq.setModifyUserId(currentUser.getUserId());
                this.iXxbBMqService.updateXxbBMq(xxbBMq);
            }
            else {
                xxbBMq.setCreateUserId(currentUser.getUserId());
                this.iXxbBMqService.createXxbBMq(xxbBMq);
            }
            if(state==1){
                XxbBCheck update= new XxbBCheck();
                update.setId(xxbBMq.getBaseId());
                update.setXmjdstate(7);
                this.iXxbBCheckService.updateXxbBCheck(update);
            }
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param xxbBMq
 * @return
 */
@Log("修改")
@PutMapping
public void updateXxbBMq(@Valid XxbBMq xxbBMq)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      xxbBMq.setModifyUserId(currentUser.getUserId());
        this.iXxbBMqService.updateXxbBMq(xxbBMq);

            XxbBCheck update= new XxbBCheck();
            update.setId(xxbBMq.getBaseId());
            update.setXmjdstate(xxbBMq.getAuditState());

            this.iXxbBCheckService.updateXxbBCheck(update);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
public void deleteXxbBMqs(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iXxbBMqService.deleteXxbBMqs(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
public void export(QueryRequest request, XxbBMq xxbBMq, HttpServletResponse response) throws FebsException {
        try {
        List<XxbBMq> xxbBMqs = this.iXxbBMqService.findXxbBMqs(request, xxbBMq).getRecords();
        ExcelKit.$Export(XxbBMq.class, response).downXlsx(xxbBMqs, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

    @PostMapping("doc")
    public void export3(QueryRequest request, String baseId, HttpServletResponse response) throws FebsException {
        try {
            ArrayList<String> mergeAddPdfList = new ArrayList<>();


            String fileName =  "协和医院新技术新项目总结反馈表.docx";
            LambdaQueryWrapper<XxbBMq> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(XxbBMq::getBaseId,baseId);


            List<XxbBMq> list= this.iXxbBMqService.list(queryWrapper);

            XxbBCheck check= this.iXxbBCheckService.getById(baseId);
            if(list.size()>0){
                String destfile ="D:\\协和医院新技术新项目总结反馈表.docx";
                XxbBMq mq= list.get(0);
                mq.setProjectName(check.getProjectName());
                mq.setYear(String.valueOf(DateUtil.year(check.getMqDate())));
                mq.setProjectType(check.getProjectType().toString());
                mq.setDeptNew(check.getDeptNew());
                mq.setUserAccountName(check.getUserAccountName());

                InputStream inputStream2 =new FileInputStream(new File(destfile));
                OutputStream out = response.getOutputStream();
                response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
                response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

                DocUtil<XxbBMq> mqDocUtil= new DocUtil<>();
                mqDocUtil.writeDoc1(mq,inputStream2,out);
            }
            else{
                throw new FebsException("尚未添加总结报告");
            }




        } catch (Exception e) {
            message = "导出docx失败";
            log.error(message, e);
            throw new FebsException(e.getMessage());
        }
    }

    @PostMapping("excelFile")
    public FebsResponse downFiles(QueryRequest request, String baseId, HttpServletResponse response) throws Exception {

        ModelMap map = new ModelMap();
        int success=0;
        String mergeFileName="";
        LambdaQueryWrapper<XxbBMq> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(XxbBMq::getBaseId,baseId);

        List<XxbBMq> list= this.iXxbBMqService.list(queryWrapper);
        if(list.size()>0) {
            mergeFileName = list.get(0).getFileUrl();;
            success= 1;

        }
        map.put("success", success);
        map.put("data", mergeFileName);
        return new FebsResponse().data(map);
    }
    private void downFile(HttpServletResponse response, String filePath, String fileName, boolean isDel) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                InputStream ins = new FileInputStream(filePath);
                BufferedInputStream bins = new BufferedInputStream(ins);// 放到缓冲流里面
                OutputStream outs = response.getOutputStream();// 获取文件输出IO流
                BufferedOutputStream bouts = new BufferedOutputStream(outs);
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition",
                        "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));

                int bytesRead = 0;
                byte[] buffer = new byte[512];
                //开始向网络传输文件流
                while ((bytesRead = bins.read(buffer, 0, 512)) != -1) {
                    bouts.write(buffer, 0, bytesRead);
                }
                bouts.flush();// 这里一定要调用flush()方法
                ins.close();
                bins.close();
                outs.close();
                bouts.close();
            } else {
//                response.sendRedirect("../error.jsp");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

    @RequestMapping(value = "downTemplate", method = RequestMethod.POST)
@RequiresPermissions("xxbBMq:import")
public void downTemplate(HttpServletResponse response) {
        List<XxbBMq> publishList = new ArrayList<>();
        ExcelKit.$Export(XxbBMq.class, response).downXlsx(publishList, true);
        }
@RequestMapping(value = "import", method = RequestMethod.POST)
@RequiresPermissions("xxbBMq:import")
public ResponseEntity<?> importUser(@RequestParam MultipartFile file)
        throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<XxbBMq> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        List<Map<String, Object>> resultList = Lists.newArrayList();

        User currentUser=FebsUtil.getCurrentUser();


        ExcelKit.$Import(XxbBMq.class)
        .readXlsx(file.getInputStream(), new ExcelReadHandler<XxbBMq>() {

@Override
public void onSuccess(int sheetIndex, int rowIndex, XxbBMq entity) {
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
        if(CollectionUtil.isEmpty(errorList)){
        for (XxbBMq xxbBMqImport:successList
        ) {
    XxbBMq xxbBMq =new XxbBMq();
        BeanUtil.copyProperties(xxbBMqImport,xxbBMq, CopyOptions.create().setIgnoreNullValue(true));
        this.iXxbBMqService.createXxbBMq(xxbBMq);
        }
        }

        resultList.add(MapUtil.of("data", successList));
        resultList.add(MapUtil.of("haveError", !CollectionUtil.isEmpty(errorList)));
        resultList.add(MapUtil.of("error", errorList));
        resultList.add(MapUtil.of("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L));
        return ResponseEntity.ok(resultList);
        }
@GetMapping("/{id}")
public XxbBMq detail(@NotBlank(message = "{required}") @PathVariable String id) {
    XxbBMq xxbBMq=this.iXxbBMqService.getById(id);
        return xxbBMq;
        }
        }