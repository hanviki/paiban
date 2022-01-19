<template>
 <a-card :bordered="false"  class="card-area" >
     <div v-if="isShow" style="text-align:center">
    <a-space >
    <a-card class="cuhov"  style="width: 300px;background-color:#32ccbc;">
       <div style="font-size:30px;color:white " @click="openQulifaction(0)">机构执业资质</div>
    </a-card>
    <a-card  class="cuhov" style="width: 300px;background-color:#32ccbc;">
      <div style="font-size:30px;color:white" @click="openQulifaction(1)">医师执业资质</div>
    </a-card>
  </a-space>
     </div>
     <div v-else="isShow">
         <a-button
           type="primary"
          @click="Back"
          >返回</a-button
        >
         <mdl-b-qulification-list v-show="subShow==0">
         </mdl-b-qulification-list>
         <user-info v-show="subShow==1"></user-info>
             
     </div>
 </a-card>
</template>
<script>
import MdlBQulificationList from "../mdl/MdlBQulification/MdlBQulificationList";
import UserInfo from "../mdl/UserInfo.vue";
export default {
  name: 'BaseInfo',
  data() {
    return {
      isShow: true,
      subShow: 0,
    };
  },
  components: { MdlBQulificationList , UserInfo},
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
@import "../../../static/less/Common";
</style>