<template>
 <a-card :bordered="false"  class="card-area" >
     <div v-if="isShow" style="text-align:center;">
    <a-space >
    <a-card class="cuhov"  @click="openQulifaction(0)" style="width: 200px;height:150px;background-color:#32ccbc;">
       <div style="font-size:30px;color:white ">考试管理</div>
    </a-card>
    <a-card class="cuhov"  @click="openQulifaction(4)" style="width: 200px;height:150px;background-color:#32ccbc;">
       <div style="font-size:30px;color:white ">处方权管理</div>
    </a-card>
    <a-card  class="cuhov"  @click="openQulifaction(1)" style="width: 200px;height:150px;background-color:#32ccbc;">
      <div style="font-size:30px;color:white" @click="openQulifaction(1)">麻精药物处方权</div>
    </a-card>
      <a-card  class="cuhov" @click="openQulifaction(2)" style="width: 200px;height:150px;background-color:#32ccbc;">
      <div style="font-size:30px;color:white" >抗菌药物分级管理</div>
    </a-card>
     <a-card  class="cuhov" @click="openQulifaction(3)" style="width: 200px;height:150px;background-color:#32ccbc;">
      <div style="font-size:30px;color:white" >抗肿瘤药物分级管理</div>
    </a-card>
  </a-space>
     </div>
     <div v-else="isShow">
         <a-button
           type="primary"
          @click="Back"
          >返回</a-button
        >
         <mdl-b-chufang v-show="subShow==0" type="考试管理">
         </mdl-b-chufang>
        <mdl-b-chufang v-show="subShow==1" type="麻精药物处方权">
         </mdl-b-chufang>
          <mdl-b-chufang v-show="subShow==2" type="抗菌药物分级管理">
         </mdl-b-chufang>
        <mdl-b-chufang v-show="subShow==3" type="抗肿瘤药物分级管理">
         </mdl-b-chufang>
         <chu-fang-report v-show="subShow==4" type="基本处方权">
         </chu-fang-report>

     </div>
 </a-card>
</template>
<script>
import MdlBChufang from "./MdlBChufang.vue";
import ChuFangReport from "./ChuFangReport.vue"
export default {
  name: 'BaseInfo',
  data() {
    return {
      isShow: true,
      subShow: -1
    };
  },
  components: { MdlBChufang,ChuFangReport},
  methods: {
    openQulifaction(index) {
      this.isShow = false;
      this.subShow = index;
    },
    Back() {
        this.isShow = true
    }
  },
};
</script>
<style lang="less" scoped>
.cuhov:hover{
   background: linear-gradient(to bottom left,#1c998c,#24ada0)
}
</style>
<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>