
package cc.mrbird.febs.xxb.controller;


import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.FebsResponse;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.common.properties.FebsProperties;
import cc.mrbird.febs.common.utils.DocUtil;
import cc.mrbird.febs.xxb.entity.XxbBCheck;
import cc.mrbird.febs.xxb.service.IXxbBCheckService;
import cc.mrbird.febs.xxb.service.IXxbBZqService;
import cc.mrbird.febs.xxb.entity.XxbBZq;

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
import java.util.UUID;

/**
 *
 * @author viki
 * @since 2022-04-26
 */
@Slf4j
@Validated
@RestController
@RequestMapping("xxbBZq")

public class XxbBZqController extends BaseController{

private String message;
@Autowired
public IXxbBZqService iXxbBZqService;
    @Autowired
    public IXxbBCheckService iXxbBCheckService;
    @Autowired
    private FebsProperties febsProperties;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'中期反馈表','/ass/XxbBZq/XxbBZq','ass/XxbBZq/XxbBZq','xxbBZq:view','fork',0,1,NOW());
 SELECT @maxId:=MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'中期反馈表新增','xxbBZq:add',1,1,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'中期反馈表编辑','xxbBZq:update',1,2,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'中期反馈表删除','xxbBZq:delete',1,3,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'中期反馈表导出','xxbBZq:export',1,4,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'中期反馈表导入','xxbBZq:import',1,5,NOW());
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param xxbBZq 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, XxbBZq xxbBZq){
        return getDataTable(this.iXxbBZqService.findXxbBZqs(request, xxbBZq));
        }

/**
 * 添加
 * @param  xxbBZq
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addXxbBZq(@Valid XxbBZq xxbBZq,int state)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();

        if(StringUtils.isNotEmpty(xxbBZq.getId())){
            xxbBZq.setModifyUserId(currentUser.getUserId());
            this.iXxbBZqService.updateXxbBZq(xxbBZq);
        }
        else {
            xxbBZq.setCreateUserId(currentUser.getUserId());
            this.iXxbBZqService.createXxbBZq(xxbBZq);
        }
        if(state==1){
            XxbBCheck update= new XxbBCheck();
            update.setId(xxbBZq.getBaseId());
            update.setXmjdstate(2);
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
 * @param xxbBZq
 * @return
 */
@Log("修改")
@PutMapping
public void updateXxbBZq(@Valid XxbBZq xxbBZq)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      xxbBZq.setModifyUserId(currentUser.getUserId());
        this.iXxbBZqService.updateXxbBZq(xxbBZq);

            XxbBCheck update= new XxbBCheck();
            update.setId(xxbBZq.getBaseId());
            update.setXmjdstate(xxbBZq.getAuditState());
            if(xxbBZq.getYqDate()!=null){
                update.setYanzhanDate(xxbBZq.getYqDate());
                update.setMqDate(DateUtil.offsetMonth(xxbBZq.getYqDate(),-1));
            }
            this.iXxbBCheckService.updateXxbBCheck(update);

        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("xxbBZq:delete")
public void deleteXxbBZqs(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iXxbBZqService.deleteXxbBZqs(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("xxbBZq:export")
public void export(QueryRequest request, XxbBZq xxbBZq, HttpServletResponse response) throws FebsException {
        try {
        List<XxbBZq> xxbBZqs = this.iXxbBZqService.findXxbBZqs(request, xxbBZq).getRecords();
        ExcelKit.$Export(XxbBZq.class, response).downXlsx(xxbBZqs, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

    @PostMapping("excelFile")
    public FebsResponse downFiles(QueryRequest request, String baseId, HttpServletResponse response) throws Exception {

        ModelMap map = new ModelMap();
        int success=0;
        String mergeFileName="";
        LambdaQueryWrapper<XxbBZq> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(XxbBZq::getBaseId,baseId);

        List<XxbBZq> list= this.iXxbBZqService.list(queryWrapper);
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

    @PostMapping("doc")
    public void export3(QueryRequest request, String baseId, HttpServletResponse response) throws FebsException {
        try {
            ArrayList<String> mergeAddPdfList = new ArrayList<>();


            String fileName =  "协和医院新技术新项目中期反馈表.docx";
            LambdaQueryWrapper<XxbBZq> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(XxbBZq::getBaseId,baseId);

            List<XxbBZq> list= this.iXxbBZqService.list(queryWrapper);

            XxbBCheck check= this.iXxbBCheckService.getById(baseId);
            if(list.size()>0){
                String destfile ="D:\\协和医院新技术新项目中期反馈表.docx";
                XxbBZq mq= list.get(0);
                mq.setProjectName(check.getProjectName());
                mq.setProjectType(check.getProjectType().toString());
                mq.setDeptNew(check.getDeptNew());
                mq.setYear(String.valueOf(DateUtil.year(check.getMqDate())));
                mq.setUserAccountName(check.getUserAccountName());
                mq.setYzDateStr(check.getYanzhanDate()==null?"":DateUtil.format(check.getYanzhanDate(),"yyyy-MM-dd"));

                InputStream inputStream2 =new FileInputStream(new File(destfile));
                OutputStream out = response.getOutputStream();
                response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
                response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

                DocUtil<XxbBZq> mqDocUtil= new DocUtil<>();
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

    @RequestMapping(value = "downTemplate", method = RequestMethod.POST)
@RequiresPermissions("xxbBZq:import")
public void downTemplate(HttpServletResponse response) {
        List<XxbBZq> publishList = new ArrayList<>();
        ExcelKit.$Export(XxbBZq.class, response).downXlsx(publishList, true);
        }
@RequestMapping(value = "import", method = RequestMethod.POST)
@RequiresPermissions("xxbBZq:import")
public ResponseEntity<?> importUser(@RequestParam MultipartFile file)
        throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<XxbBZq> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        List<Map<String, Object>> resultList = Lists.newArrayList();

        User currentUser=FebsUtil.getCurrentUser();


        ExcelKit.$Import(XxbBZq.class)
        .readXlsx(file.getInputStream(), new ExcelReadHandler<XxbBZq>() {

@Override
public void onSuccess(int sheetIndex, int rowIndex, XxbBZq entity) {
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
        for (XxbBZq xxbBZqImport:successList
        ) {
    XxbBZq xxbBZq =new XxbBZq();
        BeanUtil.copyProperties(xxbBZqImport,xxbBZq, CopyOptions.create().setIgnoreNullValue(true));
        this.iXxbBZqService.createXxbBZq(xxbBZq);
        }
        }

        resultList.add(MapUtil.of("data", successList));
        resultList.add(MapUtil.of("haveError", !CollectionUtil.isEmpty(errorList)));
        resultList.add(MapUtil.of("error", errorList));
        resultList.add(MapUtil.of("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L));
        return ResponseEntity.ok(resultList);
        }
@GetMapping("/{id}")
public XxbBZq detail(@NotBlank(message = "{required}") @PathVariable String id) {
    XxbBZq xxbBZq=this.iXxbBZqService.getById(id);
        return xxbBZq;
        }
        }