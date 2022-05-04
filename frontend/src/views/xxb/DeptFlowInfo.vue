<template>
  <a-card :bordered="false" class="card-area">
    <div v-if="isShow" style="text-align: center">
      <a-space>
        <a-card
          class="cu"
          @click="openQulifaction(0, '本科室申报项目审核')"
          style="width: 200px"
          :bordered="false"
        >
          <a-icon type="file-text" style="font-size: 60px" :bordered="false" />
          <div style="font-size: 20px; margin-top: 10px">本科室申报项目审核</div>
        </a-card>
        <a-card
          class="cu"
          @click="openQulifaction(1, '参与项目审核')"
          style="width: 200px"
          :bordered="false"
        >
          <a-icon type="file-done" style="font-size: 60px" :bordered="false" />
          <div style="font-size: 20px; margin-top: 10px">参与项目审核</div>
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
      <xxbBDeptMany-list v-show="subShow == 0"> </xxbBDeptMany-list>
      <xxbBFlowDeptMany-list v-show="subShow == 1"> </xxbBFlowDeptMany-list>
    </div>
  </a-card>
</template>
<script>
import xxbBDeptManyList from "../xxb/XxbBFlowDept/XxbBFlowDept";
import xxbBFlowDeptManyList from "../xxb/XxbBFlowDept/XxbBFlowDeptMany";
export default {
  name: "BaseInfo",
  data() {
    return {
      isShow: true,
      subShow: 0,
      title: "",
    };
  },
  components: { xxbBDeptManyList, xxbBFlowDeptManyList },
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