package cc.mrbird.febs.common.utils;

import cc.mrbird.febs.webService.Mess.SmsService;
import cc.mrbird.febs.webService.Mess.SmsServicePortType;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class SendMessUtil {
    public void SendMessByTel(String tel, String message) {
        try {
            SmsService smsService = new SmsService();
            SmsServicePortType ssp = smsService.getSmsServiceHttpPort();
            String in0 = "YWC_YSPBXT@2022";
            String in1 = "YWC_YSPBXT@2022";
            String in2 = "MAC";
            String in3 = "FC";
            String in4 = tel;
            String in5 = message;
            String sms = ssp.service(in0, in1, in2, in3, in4, in5);
           log.info("已发送数据"+tel+":" +DateUtil.format(new Date(),"yyyyMMdd"));
        } catch (Exception e) {
            log.error(DateUtil.format(new Date(),"yyyyMMdd")+":"+e.getMessage());
        }

    }
}
