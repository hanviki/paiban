<template>
  <a-card :bordered="false" class="card-area">
    <div v-if="isShow" style="text-align: center">
      <a-space>
        <a-card
          class="cu"
          @click="openQulifaction(0)"
         
          :bordered="false"
        >
          <a-icon type="codepen" style="font-size: 60px" :bordered="false" />
          <div style="font-size: 20px;  margin-top: 10px">
            医疗主任
          </div>
        </a-card>

        <a-card
        class="cu"
          @click="openQulifaction(1)"
          style="width: 200px"
          :bordered="false"
        >
          <a-icon
            type="code-sandbox"
             style="font-size: 60px"
            :bordered="false"
          />
          <div style="font-size: 20px;  margin-top: 10px">
            医疗组长
          </div>
        </a-card>

        <a-card
          class="cu"
          @click="openQulifaction(2)"
          style="width: 200px"
          :bordered="false"
        >
          <a-icon
            type="ant-design"
            style="font-size: 60px"
            :bordered="false"
          />
          <div style="font-size: 20px;  margin-top: 10px">
            质控员
          </div>
        </a-card>
      </a-space>
    </div>
    <div v-else="isShow">
      <a-button type="primary" @click="Back">返回</a-button>
      <mdl-b-manage-list v-show="subShow == 0" type="医疗主任">
      </mdl-b-manage-list>
      <mdl-b-manage-list v-show="subShow == 1" type="医疗组长">
      </mdl-b-manage-list>
      <mdl-b-manage-list v-show="subShow == 2" type="质控员">
      </mdl-b-manage-list>
    </div>
  </a-card>
</template>
<script>
import MdlBManageList from "./MdlBManagerList.vue";

export default {
  name: "BaseInfo",
  data() {
    return {
      isShow: true,
      subShow: 0,
      type: "医疗主任",
    };
  },
  components: { MdlBManageList },
  methods: {
    openQulifaction(index) {
      this.isShow = false;
      this.subShow = index;
    },
    Back() {
      this.isShow = true;
    },
  },
};
</script>
<style lang="less" scoped>rgb(47, 112, 106)
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
@import "../../../../static/less/Common";
</style>
