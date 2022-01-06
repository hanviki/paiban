package cc.mrbird.febs.job.task;

import cc.mrbird.febs.common.utils.SendMessUtil;
import cc.mrbird.febs.common.utils.WxMessage;

import cc.mrbird.febs.rfc.CustomUser;
import cc.mrbird.febs.rfc.RfcNoc;
import cc.mrbird.febs.sdl.entity.SdlBPersoninfo;
import cc.mrbird.febs.sdl.entity.SdlBUser;
import cc.mrbird.febs.sdl.service.ISdlBPersoninfoService;
import cc.mrbird.febs.sdl.service.ISdlBUserService;
import cc.mrbird.febs.sdl.service.impl.SdlBUserServiceImpl;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class MsgValidTask {

    @Autowired
    private ISdlBUserService iSdlBUserService;

    @Autowired
    private ISdlBPersoninfoService iSdlBPersoninfoService;

    public void user() {
        RfcNoc rfcNoc = new RfcNoc();
        List<SdlBUser> sdlBUserList = rfcNoc.GetUserList();
        List<CustomUser> sdlUserIds = iSdlBUserService.getUserAccounts();

        for (SdlBUser user : sdlBUserList
        ) {
           List<CustomUser> users= sdlUserIds.stream().filter(p->p.getUserAccount().equals(user.getUserAccount())).collect(Collectors.toList());
            if (users.size()>0) {
                user.setId(users.get(0).getId());
                iSdlBUserService.updateSdlBUser(user);
            } else {
                iSdlBUserService.createSdlBUser(user);
            }
        }
    }
    public void sendMess(){
        String startDate = DateUtil.format(DateUtil.beginOfWeek(new Date()),"yyyy-MM-dd");
        String endDate = DateUtil.format(DateUtil.endOfWeek(new Date()),"yyyy-MM-dd");
        List<SdlBPersoninfo> listSend= iSdlBPersoninfoService.sendMess(startDate);
        SendMessUtil sendMessUtil =new SendMessUtil();
        for (SdlBPersoninfo person: listSend
             ) {
            String mess="尊敬的"+person.getDeptName()+"科室排班管理员：您科室"+startDate+"日到"+endDate+"日的排班数据还未提交，截止日期为"+endDate+"日，请尽快使用谷歌浏览器在外网登录http://whuhmedical.qm.whuh.com:8809/#/login，进行科室排班数据提交。";
            log.info(mess);
            sendMessUtil.SendMessByTel(person.getTelephone(),mess);
        }
    }
}
