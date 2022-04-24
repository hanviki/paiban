<template>
  <a-card :bordered="false" class="card-area">
    <div v-if="isShow" style="text-align: center">
      <a-space>
        <a-card
          class="cu"
          @click="openQulifaction(0, '临床类-单科申报')"
          style="width: 200px"
          :bordered="false"
        >
          <a-icon type="file-text" style="font-size: 60px" :bordered="false" />
          <div style="font-size: 20px; margin-top: 10px">临床类-单科申报</div>
        </a-card>
        <a-card
          class="cu"
          @click="openQulifaction(1, '临床类-多科联合申报')"
          style="width: 200px"
          :bordered="false"
        >
          <a-icon type="file-done" style="font-size: 60px" :bordered="false" />
          <div style="font-size: 20px; margin-top: 10px">临床类-多科联合申报</div>
        </a-card>
        <a-card
          class="cu"
          @click="openQulifaction(2, '检验检查类')"
          style="width: 200px"
          :bordered="false"
        >
          <a-icon type="file-done" style="font-size: 60px" :bordered="false" />
          <div style="font-size: 20px; margin-top: 10px">检验检查类</div>
        </a-card>
      </a-space>
    </div>
    <div v-else>
      <a-row>
        <a-col :sm="6"
          ><a-button type="primary" @click="Back">返回</a-button>
        </a-col>
        <a-col :sm="18">
          <span style="font-size: 20px; text-align: center; color: green">
            {{ title }}
          </span>
        </a-col>
        </a-row>
      <xxbBLcone-list v-show="subShow == 0"> </xxbBLcone-list>
      <xxbBLcmany-list v-show="subShow == 1"> </xxbBLcmany-list>
      <xxbBCheck-list v-show="subShow == 2"> </xxbBCheck-list>
    </div>
  </a-card>
</template>
<script>
import xxbBCheckList from "../xxb/XxbBCheck/XxbBCheck";
import xxbBLconeList from "../xxb/XxbBLcone/XxbBLcone";
import xxbBLcmanyList from "../xxb/XxbBLcmany/XxbBLcmany";
export default {
  name: "BaseInfo",
  data() {
    return {
      isShow: true,
      subShow: 0,
      title: "",
    };
  },
  components: { xxbBCheckList, xxbBLconeList, xxbBLcmanyList },
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
.cu {
  width: 200px;
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