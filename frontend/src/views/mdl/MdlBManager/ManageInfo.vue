<template>
 <a-card :bordered="false"  class="card-area" >
     <div v-if="isShow" style="text-align:center">
    <a-space >
    <a-card class="cuhov"  style="width: 300px;background-color:#32ccbc;">
       <div style="font-size:30px;color:white " @click="openQulifaction(0)">医疗主任</div>
    </a-card>
    <a-card  class="cuhov" style="width: 300px;background-color:#32ccbc;">
      <div style="font-size:30px;color:white" @click="openQulifaction(1)">医疗组长</div>
    </a-card>
      <a-card  class="cuhov" style="width: 300px;background-color:#32ccbc;">
      <div style="font-size:30px;color:white" @click="openQulifaction(2)">质控员</div>
    </a-card>
  </a-space>
     </div>
     <div v-else="isShow">
         <a-button
           type="primary"
          @click="Back"
          >返回</a-button
        >
         <mdl-b-manage-list v-show="subShow==0" type="医疗主任">
         </mdl-b-manage-list>
        <mdl-b-manage-list v-show="subShow==1" type="医疗组长">
         </mdl-b-manage-list>
          <mdl-b-manage-list v-show="subShow==2" type="质控员">
         </mdl-b-manage-list>
             
     </div>
 </a-card>
</template>
<script>
import MdlBManageList from "./MdlBManagerList.vue";

export default {
  name: 'BaseInfo',
  data() {
    return {
      isShow: true,
      subShow: 0,
      type: '医疗主任'
    };
  },
  components: { MdlBManageList},
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