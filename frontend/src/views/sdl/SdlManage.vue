<template>
  <a-card :bordered="false" class="card-area">
    <div v-if="isShow" style="text-align: center">
      
        <div class="cuDiv"  style="background-color: #1c998c">
          <div class="bigDiv">
            <div class="titleDiv">信息查询</div>
            <div class="subDiv">
              <a @click="openQulifaction(1, '科室人员信息')" href="#">
                科室人员信息
              </a>
            </div>
            <div class="subDiv">
              <a @click="openQulifaction(2, '移入移出记录')" href="#"> 移入移出记录 </a>
            </div>
            <div class="subDiv">
              <a @click="openQulifaction(3, '未排班数据')" href="#">
                未排班数据
              </a>
            </div>
           
          </div>
          <div class="imageDiv"><a-avatar :size="50" style=" backgroundColor:white" ><a-icon type="home"  style="font-size: 30px;color:green" :bordered="false" /></a-avatar></div>
        </div>
        <div class="cuDiv"  style="background-color: #a874c7">
          <div class="bigDiv">
            <div class="titleDiv">数据审核</div>

            <div class="subDiv">
              <a @click="openQulifaction(4, '资质申请审核')" href="#">
                资质申请审核
              </a>
            </div>
            <div class="subDiv">
              <a @click="openQulifaction(5, '排班数据审核')" href="#">
                排班数据审核
              </a>
            </div>
            <div class="subDiv">
              <a @click="openQulifaction(6, '排班修改申请审核')" href="#">
                排班修改申请审核
              </a>
            </div>
          </div>
          <div class="imageDiv"><a-avatar :size="50" style=" backgroundColor:white;" ><a-icon type="user"  style="font-size: 30px;color:green" :bordered="false" /></a-avatar></div>
        </div>
      

      <div class="cuDiv"  style="background-color: #05582f">
          <div class="bigDiv">
            <div class="titleDiv">报表查询</div>

            <div class="subDiv">
              <a @click="openQulifaction(7, '排班打印')" href="#">
                排班打印
              </a>
            </div>
            <div class="subDiv">
              <a @click="openQulifaction(8, '夜班费')" href="#">
                夜班费
              </a>
            </div>
          </div>
          <div class="imageDiv"><a-avatar :size="50" style=" backgroundColor:white;padding-top:5px;" ><a-icon type="global"  style="font-size: 30px;color:green" :bordered="false" /></a-avatar></div>
        </div>
    </div>
    <div v-else="isShow">
      <a-row
        ><a-col :sm="6"
          ><a-button type="primary"  icon="rollback" @click="Back">返回</a-button></a-col
        ><a-col :sm="18"
          ><span style="font-size: 20px; text-align: center; color: green">{{
            title
          }}</span></a-col
        ></a-row
      >
      <dept-user-list v-if="subShow==1"></dept-user-list>
   <sdl-b-zizhiapply-audit v-if="subShow==4"></sdl-b-zizhiapply-audit>
   <sdl-b-schedule-audit v-if="subShow==6"></sdl-b-schedule-audit>
   <sdl-b-schedule-list-audit v-if="subShow==5"></sdl-b-schedule-list-audit>
   <sdl-b-deptchange-list v-if="subShow==2"></sdl-b-deptchange-list>
   <sdl-b-print-report v-if="subShow==7"></sdl-b-print-report>
   <no-submit-report v-if="subShow==3"></no-submit-report>
   <sdl-b-ye-ban-report v-if="subShow==8"></sdl-b-ye-ban-report>
    </div>
  </a-card>
</template>
<script>

import DeptUserList from '../sdl/SdlBUser/DeptUserList.vue'
import SdlBDeptchangeList from './SdlBDeptchange/SdlBDeptchangeList.vue';
import NoSubmitReport from './SdlBSchedule/NoSubmitReport.vue';
import SdlBScheduleAudit from './SdlBSchedule/SdlBScheduleAudit.vue';
import SdlBScheduleListAudit from './SdlBSchedule/SdlBScheduleListAudit.vue';
import SdlBYeBanReport from './SdlBSchedule/SdlBYeBanReport.vue';
import SdlBPrintReport from './SdlBScheduleD/SdlBPrintReport.vue';
import SdlBZizhiapplyAudit from './SdlBZizhiapply/SdlBZizhiapplyAudit.vue';

export default {
  name: "BaseInfo",
  data() {
    return {
      isShow: true,
      subShow: 0,
      title: "",
    };
  },
  components: {
DeptUserList,
    SdlBZizhiapplyAudit,
    SdlBScheduleAudit,
    SdlBScheduleListAudit,
    SdlBDeptchangeList,
    SdlBPrintReport,
    NoSubmitReport,
    SdlBYeBanReport
  },
  methods: {
    openQulifaction(index, title) {
      this.isShow = false;
      this.subShow = index;
      this.title = title;
    },
    Back() {
      this.isShow = true;
    },
  },
};
</script>
<style lang="less" scoped>
.cuhov:hover {
  background: linear-gradient(to bottom left, #05582f, #24ada0);
}
.cuDiv {
  width: 240px;
  height: 180px;
  text-align: left;
  padding: 10px;
  margin-left: 40px;
  margin-top: 20px;
  float: left;
}
.imageDiv {
  float: left;
  width: 50px;
  height: 180px;
  padding-top:10px;
}
.bigDiv {
  float: left;
  width: 170px;
  height: 180px;
  color: white;
}
.titleDiv {
  width: 100%;
  padding-top: 10px;
  //padding-bottom: 10px;
  font-weight: bold;
  font-size: 20px;
  color: #fff;
}
.subDiv {
  height: 20px;
  width: 100%;
  font-size: 12px;
  padding-top: 5px;
  color: #fff;
  a {
    color: #fff;
  }
}
</style>
<style lang="less" scoped>
@import "../../../static/less/Common";
</style>