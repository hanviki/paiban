<template>
  <a-card :bordered="false" class="card-area">
    <div v-if="isShow" style="text-align: center">
      
        <div class="cuDiv"  style="background-color: #1c998c">
          <div class="bigDiv">
            <div class="titleDiv">新技术新业务管理</div>
            <div class="subDiv">
              <a @click="openQulifaction(1, '部门负责人设置')" href="#">
                部门负责人设置
              </a>
            </div>
            <div class="subDiv">
              <a @click="openQulifaction(2, '新技术新业务审核')" href="#"> 新技术新业务审核 </a>
            </div>
            <div class="subDiv">
              <a @click="openQulifaction(3, '新技术新业务上会文件')" href="#">
                新技术新业务上会文件
              </a>
            </div>
            <div class="subDiv">
              <a @click="openQulifaction(4, '项目中期反馈审核')" href="#">
                项目中期反馈审核
              </a>
            </div>
            <div class="subDiv">
              <a @click="openQulifaction(5, '项目总结反馈审核')" href="#">
                项目总结反馈审核
              </a>
            </div>
            <div class="subDiv">
              <a @click="openQulifaction(6, '新技术新业务报表')" href="#">
                新技术新业务报表
              </a>
            </div>
          </div>
          <div class="imageDiv"><a-avatar :size="50" style=" backgroundColor:white" ><a-icon type="home"  style="font-size: 30px;color:green" :bordered="false" /></a-avatar></div>
        </div>
        <div class="cuDiv"  style="background-color: #a874c7">
          <div class="bigDiv">
            <div class="titleDiv">手术资质</div>

            <div class="subDiv">
              <a @click="openQulifaction(7, '手术目录')" href="#">
                手术目录
              </a>
            </div>
            <div class="subDiv">
              <a @click="openQulifaction(8, '手术资质')" href="#">
                手术资质
              </a>
            </div>
          </div>
          <div class="imageDiv"><a-avatar :size="50" style=" backgroundColor:white;" ><a-icon type="user"  style="font-size: 30px;color:green" :bordered="false" /></a-avatar></div>
        </div>
       <div class="cuDiv"  style="background-color: #05582f">
          <div class="bigDiv">
            <div class="titleDiv">新技术新业务历史记录</div>

            <div class="subDiv">
              <a @click="openQulifaction(9, '（1998-2021年）新技术新业务立项总结')" href="#">
                立项总结
              </a>
            </div>
            <div class="subDiv">
              <a @click="openQulifaction(10, '历年新技术新业务获奖名单')" href="#">
                历年新技术新业务获奖名单
              </a>
            </div>
          </div>
          <div class="imageDiv"><a-avatar :size="50" style=" backgroundColor:white;" ><a-icon type="user"  style="font-size: 30px;color:green" :bordered="false" /></a-avatar></div>
        </div>
    </div>
    <div v-else="isShow">
      <a-row
        ><a-col :sm="6"
          ><a-button type="primary" @click="Back">返回</a-button></a-col
        ><a-col :sm="18"
          ><span style="font-size: 20px; text-align: center; color: green">{{
            title
          }}</span></a-col
        ></a-row
      >
     
      <xxb-b-deptleader v-if="subShow == 1"></xxb-b-deptleader>
      <xxb-b-flow-ywc v-if="subShow == 2"></xxb-b-flow-ywc>
      <xxb-b-archive v-if="subShow == 3"></xxb-b-archive>
      <xxb-b-zq-audit v-if="subShow == 4"></xxb-b-zq-audit>
      <xxb-b-mq-audit v-if="subShow == 5"></xxb-b-mq-audit>
     <xxb-b-check-report v-if="subShow == 6"></xxb-b-check-report>
     <mdl-d-surgery v-if="subShow == 7"></mdl-d-surgery>
     <mdl-b-surgeryinfo v-if="subShow==8" ></mdl-b-surgeryinfo>
     <mdl-d-mdt-old v-if="subShow==9">
     </mdl-d-mdt-old>
     <mdl-d-prize-old v-if="subShow==10">
     </mdl-d-prize-old>
    </div>
  </a-card>
</template>
<script>
import XxbBArchive from '../xxb/XxbBArchive/XxbBArchive.vue';
import XxbBCheckReport from '../xxb/XxbBCheck/XxbBCheckReport.vue';
import XxbBDeptleader from '../xxb/XxbBDeptleader/XxbBDeptleader.vue';
import XxbBFlowYwc from '../xxb/XxbBFlowYwc/XxbBFlowYwc.vue';
import XxbBMqAudit from '../xxb/XxbBMq/XxbBMqAudit.vue';
import XxbBZqAudit from '../xxb/XxbBZq/XxbBZqAudit.vue';
import MdlDSurgery from './MdlDSurgery/MdlDSurgery.vue';
import MdlBSurgeryinfo from './MdlBSurgeryinfo/MdlBSurgeryinfo.vue'
import MdlDMdtOld from './MdlDMdtOld/MdlDMdtOld.vue'
import MdlDPrizeOld from './MdlDPrizeOld/MdlDPrizeOld.vue'


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
    XxbBDeptleader,
    XxbBFlowYwc,
    XxbBArchive,
    XxbBZqAudit,
    XxbBMqAudit,
    XxbBCheckReport,
    MdlDSurgery,
    MdlBSurgeryinfo,
    MdlDMdtOld,
    MdlDPrizeOld
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