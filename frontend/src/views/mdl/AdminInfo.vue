<template>
  <a-card :bordered="false" class="card-area">
    <div v-if="isShow" style="text-align: center">
      <a-row>
        <a-col :sm="16">
          <a-card
            title="处方权管理"
            :bordered="true"
            headStyle="font-size:20px;font-weight: bold; color:gold"
          >
            <a-space>
              <a-card
              class="cu"
                @click="openQulifaction(7, '基本处方权')"
                style="width: 200px"
                :bordered="false"
              >
                <a-icon type="home" style="font-size: 60px" :bordered="false" />
                <div style="font-size: 20px;  margin-top: 10px">
                  基本处方权
                </div>
              </a-card>
              <a-card
              class="cu"
                @click="openQulifaction(8, '麻精药物处方权')"
                style="width: 200px"
                :bordered="false"
              >
                <a-icon type="appstore" style="font-size: 60px" />
                <div
                  style="font-size: 20px;  margin-top: 10px"
                 
                >
                  麻精药物处方权
                </div>
              </a-card>
              <a-card
              class="cu"
                @click="openQulifaction(9, '抗菌药物分级管理')"
                style="width: 220px"
                :bordered="false"
              >
                <a-icon type="control" style="font-size: 60px" />
                <div
                  style="font-size: 20px;  margin-top: 10px"
                 
                >
                  抗菌药物分级管理
                </div>
              </a-card>
              <a-card
              class="cu"
               @click="openQulifaction(10, '抗肿瘤药物分级管理')"
                style="width: 240px"
                :bordered="false"
              >
                <a-icon type="read" style="font-size: 60px" />
                <div
                  style="font-size: 20px; margin-top: 10px"
                  
                >
                  抗肿瘤药物分级管理
                </div>
              </a-card>
            </a-space>
          </a-card>
        </a-col>
        <a-col :sm="8">
          <a-card
            title="会诊资质"
            :bordered="true"
            headStyle="font-size:20px;font-weight: bold; color:gold"
          >
            <a-space>
              <a-card
              class="cu"
                @click="openQulifaction(20, '单科会诊')"
                style="width: 200px"
                :bordered="false"
              >
                <a-icon
                  type="border"
                  style="font-size: 60px"
                  :bordered="false"
                />
                <div style="font-size: 20px;  margin-top: 10px">
                  单科会诊
                </div>
              </a-card>
              <a-card
              class="cu"
                @click="openQulifaction(21, '多单科会诊')"
                style="width: 200px"
                :bordered="false"
              >
                <a-icon type="block" style="font-size: 60px"  />
                <div
                  style="font-size: 20px;  margin-top: 10px"
                >
                  多单科会诊
                </div>
              </a-card>
            </a-space>
          </a-card>
        </a-col>
      </a-row>

      <a-space>
        <a-card
        class="cu"
          @click="openQulifaction(1, '值班资质')"
          style="width: 200px"
          :bordered="false"
        >
          <a-icon type="profile" style="font-size: 60px" :bordered="false" />
          <div style="font-size: 20px;  margin-top: 10px">
            值班资质
          </div>
        </a-card>
        <a-card
        class="cu"
          @click="openQulifaction(3, 'MDT团队管理')"
          style="width: 200px"
          :bordered="false"
        >
          <a-icon type="project" style="font-size: 60px" :bordered="false" />
          <div style="font-size: 20px;  margin-top: 10px">
            MDT团队管理
          </div>
        </a-card>
        <a-card
          class="cu"
          @click="openQulifaction(4, '专业类资质')"
          style="width: 200px"
          :bordered="false"
        >
          <a-icon type="wallet" style="font-size: 60px" :bordered="false" />
          <div style="font-size: 20px;  margin-top: 10px">
            专业类资质
          </div>
        </a-card>
        <a-card
        class="cu"
          @click="openQulifaction(5, '行业类资质')"
          style="width: 200px"
          :bordered="false"
        >
          <a-icon type="global" style="font-size: 60px" :bordered="false" />
          <div style="font-size: 20px;  margin-top: 10px">
            行业类资质
          </div>
        </a-card>
        <a-card
        class="cu"
          @click="openQulifaction(6, '授权文件')"
          style="width: 200px"
          :bordered="false"
        >
          <a-icon type="file-done" style="font-size: 60px" :bordered="false" />
          <div style="font-size: 20px;  margin-top: 10px">
            授权文件
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
      <!-- <chu-fang-info v-show="subShow==0">
        </chu-fang-info> -->
      <mdl-b-special v-show="subShow == 4"> </mdl-b-special>
      <mdl-b-profession v-show="subShow == 5"> </mdl-b-profession>
      <schedule-info v-show="subShow == 1"> </schedule-info>
      <mdl-b-archive-list v-show="subShow == 6"> </mdl-b-archive-list>
      <mdl-b-chufang v-show="subShow == 7" type="基本处方权"> </mdl-b-chufang>
      <mdl-b-chufang v-show="subShow == 8" type="麻精药物处方权">
      </mdl-b-chufang>
      <mdl-b-chufang v-show="subShow == 9" type="抗菌药物分级管理">
      </mdl-b-chufang>
      <mdl-b-chufang v-show="subShow == 10" type="抗肿瘤药物分级管理">
      </mdl-b-chufang>
      <hui-zhen v-show="subShow == 20" type="单科会诊"> </hui-zhen>
      <hui-zhen v-show="subShow == 21" type="多单科会诊"> </hui-zhen>
    </div>
  </a-card>
</template>
<script>
import MdlBSpecial from "./MdlBSpecial/MdlBSpecial.vue";
import MdlBProfession from "./MdlBProfession/MdlBProfession.vue";
import ScheduleInfo from "./MdlBManager/ScheduleInfo.vue";
import MdlBArchiveList from "./MdlBArchive/MdlBArchiveList.vue";
import MdlBChufang from "./MdlBChufang/MdlBChufang.vue";
import HuiZhen from "./MdlBManager/HuiZhen.vue";

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
    MdlBSpecial,
    MdlBProfession,
    ScheduleInfo,
    MdlBArchiveList,
    MdlBChufang,
    HuiZhen,
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
  background: linear-gradient(to bottom left, #1c998c, #24ada0);
}
.cu{
  width:200px;
  color: #08c;
}
.cu:hover {
    background-color: #1c998c;
    color: #fff;
}
</style>
<style lang="less" scoped>
@import "../../../static/less/Common";
</style>