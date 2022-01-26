<template>
 <a-card :bordered="false"  class="card-area" >
     <div v-if="isShow" style="text-align:center">
    <a-space >
    <!-- <a-card class="cuhov"  style="width: 300px;background-color:#32ccbc;">
       <div style="font-size:30px;color:white " @click="openQulifaction(0)">机构执业资质</div>
    </a-card>
    <a-card  class="cuhov" style="width: 300px;background-color:#32ccbc;">
      <div style="font-size:30px;color:white" @click="openQulifaction(1)">医师执业资质</div>
    </a-card> -->
     <a-card
          @click="openQulifaction(0,'机构执业资质')"
          style="width: 200px"
          :bordered="false"
        >
          <a-icon type="file-text" style="font-size: 60px" :bordered="false" />
          <div style="font-size: 20px; color: green; margin-top: 10px">
            机构执业资质
          </div>
        </a-card>
     <a-card
          @click="openQulifaction(1,'医师执业资质')"
          style="width: 200px"
          :bordered="false"
        >
          <a-icon type="file-done" style="font-size: 60px" :bordered="false" />
          <div style="font-size: 20px; color: green; margin-top: 10px">
            医师执业资质
          </div>
        </a-card>
  </a-space>
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
      title: ''
    };
  },
  components: { MdlBQulificationList , UserInfo},
  methods: {
    openQulifaction(index,title) {
      this.isShow = false;
      this.subShow = index;
      this.title= title
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