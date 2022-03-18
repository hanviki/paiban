<template>
  <a-card :bordered="false" class="card-area">
    <div v-if="isShow" style="text-align: center">
      
        <div class="cuDiv"  style="background-color: #1c998c">
          <div class="bigDiv">
            <div class="titleDiv">处方权管理</div>
            <div class="subDiv">
              <a @click="openQulifaction(6, '授权文件')" href="#">
                授权文件
              </a>
            </div>
            <div class="subDiv">
              <a @click="openQulifaction(7, '考试管理')" href="#"> 考试管理 </a>
            </div>
            <div class="subDiv">
              <a @click="openQulifaction(12, '基本处方权')" href="#">
                基本处方权
              </a>
            </div>
            <div class="subDiv">
              <a @click="openQulifaction(8, '麻精药物处方权')" href="#">
                麻精药物处方权
              </a>
            </div>
            <div class="subDiv">
              <a @click="openQulifaction(9, '抗菌药物分级管理')" href="#">
                抗菌药物分级管理
              </a>
            </div>
            <div class="subDiv">
              <a @click="openQulifaction(10, '抗肿瘤药物分级管理')" href="#">
                抗肿瘤药物分级管理
              </a>
            </div>
          </div>
          <div class="imageDiv"><a-avatar :size="50" style=" backgroundColor:white" ><a-icon type="home"  style="font-size: 30px;color:green" :bordered="false" /></a-avatar></div>
        </div>
        <div class="cuDiv"  style="background-color: #a874c7">
          <div class="bigDiv">
            <div class="titleDiv">会诊资质</div>

            <div class="subDiv">
              <a @click="openQulifaction(20, '单科会诊')" href="#">
                单科会诊
              </a>
            </div>
            <div class="subDiv">
              <a @click="openQulifaction(21, '多学科会诊')" href="#">
                多学科会诊
              </a>
            </div>
          </div>
          <div class="imageDiv"><a-avatar :size="50" style=" backgroundColor:white;" ><a-icon type="user"  style="font-size: 30px;color:green" :bordered="false" /></a-avatar></div>
        </div>
        <div class="cuDiv" @click="openQulifaction(1, '值班资质')" style="background-color: #dbb752">
          <div class="bigDiv" >
            <div class="titleDiv">值班资质</div>
          </div>
          <div class="imageDiv"><a-avatar :size="50" style=" backgroundColor:white;padding-top:5px;" ><a-icon type="profile"  style="font-size: 30px;color:green" :bordered="false" /></a-avatar></div>
        </div>

        <div class="cuDiv" @click="openQulifaction(3, 'MDT团队管理')" style="background-color: #51cbdb">
          <div class="bigDiv" >
            <div class="titleDiv">MDT团队管理</div>
          </div>
          <div class="imageDiv"><a-avatar :size="50" style=" backgroundColor:white;padding-top:5px;" ><a-icon type="project"  style="font-size: 30px;color:green" :bordered="false" /></a-avatar></div>
        </div>
      <div class="cuDiv"  style="background-color: #05582f">
          <div class="bigDiv">
            <div class="titleDiv">其他资质</div>

            <div class="subDiv">
              <a @click="openQulifaction(4, '专业类资质')" href="#">
                专业类资质
              </a>
            </div>
            <div class="subDiv">
              <a @click="openQulifaction(5, '行业类资质')" href="#">
                行业类资质
              </a>
            </div>
          </div>
          <div class="imageDiv"><a-avatar :size="50" style=" backgroundColor:white;padding-top:5px;" ><a-icon type="global"  style="font-size: 30px;color:green" :bordered="false" /></a-avatar></div>
        </div>
        <!-- <div class="cuDiv" @click="openQulifaction(4, '专业类资质')"  style="background-color: #abdb51">
          <div class="bigDiv">
            <div class="titleDiv">专业类资质</div>
          </div>
          <div class="imageDiv"><a-avatar :size="50" style=" backgroundColor:white;padding-top:5px;" ><a-icon type="wallet"  style="font-size: 30px;color:green" :bordered="false" /></a-avatar></div>
        </div>

        <div class="cuDiv" @click="openQulifaction(5, '行业类资质')"  style="background-color: #05582f">
          <div class="bigDiv">
            <div class="titleDiv">行业类资质</div>
          </div>
          <div class="imageDiv"><a-avatar :size="50" style=" backgroundColor:white;padding-top:5px;" ><a-icon type="global"  style="font-size: 30px;color:green" :bordered="false" /></a-avatar></div>
        </div> -->

        <!-- <div class="cuDiv" @click="openQulifaction(6, '授权文件')" style="background-color: #1c998c">
          <div class="bigDiv" >
            <div class="titleDiv">授权文件</div>
          </div>
          <div class="imageDiv"><a-avatar :size="50" style=" backgroundColor:white;padding-top:5px;" ><a-icon type="file-done"  style="font-size: 30px;color:green;" :bordered="false" /></a-avatar></div>
        </div> -->
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
      <mdl-b-chufang v-show="subShow == 7" type="考试管理"> </mdl-b-chufang>
      <mdl-b-chufang v-show="subShow == 8" type="麻精药物处方权">
      </mdl-b-chufang>
      <mdl-b-chufang v-show="subShow == 9" type="抗菌药物分级管理">
      </mdl-b-chufang>
      <mdl-b-chufang v-show="subShow == 10" type="抗肿瘤药物分级管理">
      </mdl-b-chufang>
      <chu-fang-report v-show="subShow == 12" type="基本处方权">
      </chu-fang-report>
      <hui-zhen v-show="subShow == 20" type="单科会诊"> </hui-zhen>
      <hui-zhen v-show="subShow == 21" type="多学科会诊"> </hui-zhen>
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
import ChuFangReport from "./MdlBChufang/ChuFangReport.vue";

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
    ChuFangReport,
    ChuFangReport,
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