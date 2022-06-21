<template>
  <div class="container">
    <div class="content2">
    <div class="content">
      <div class="top">
        <div class="header">
          <!-- <img width="80" alt="logo" class="logo" src="static/img/logo.png" />
          <span class="title">{{systemName}}</span> -->
        </div>
        <div class="desc">
          <span class="title">{{ systemName }}</span>
        </div>
      </div>
      <component
        :is="componentName"
        @regist="handleRegist"
        :vendorId="vendorId"
        :class="mainContent"
      ></component>
    </div>
    <!-- <global-footer :copyright="copyright" /> -->
    </div>
  </div>
</template>

<script>
import GlobalFooter from "../common/GlobalFooter";
import Login from "./Login";
import Regist from "./Regist";
import Modify from "./UpdateVendorInfo";

export default {
  name: "Common",
  components: { GlobalFooter, Login, Regist, Modify },
  data() {
    return {
      componentName: "Login",
      mainContent: "main-content",
      vendorId: "",
    };
  },
  computed: {
    systemName() {
      return "医师综合管理平台";
    },
    copyright() {
      return this.$store.state.setting.copyright;
    },
  },
  methods: {
    handleRegist(val, val2) {
      console.log("val2:" + val2);
      if (val == "Login") {
        this.mainContent = "main-content";
      } else {
        this.mainContent = "main-content2";
      }
      this.componentName = val;
      if (val2 != null) {
        this.vendorId = val2;
        console.log(val2);
      }
    },
  },
};
</script>

<style lang="less" scoped>
.container {
  display: flex;
  flex-direction: column;
  height: 100vh;
  overflow: auto;
  background: #009180 no-repeat center 110px;
  background-size: 100%;
  .content2{
    padding: 32px 0;
      flex: 1;
  .content {
    position: absolute;
    left: 50%;
    top: 50%;
    min-height: 420px;
    max-width: 420px;
    margin-left: -210px;
    margin-top: -280px;
    padding: 10px;
    background-color: #ffffff;
    border-radius: 8px;
    /* overflow-x: hidden; */
    box-sizing: border-box;
    border: 1px solid #e6e9ed;
    box-shadow: 0px 0px 80px #666;
    .top {
      text-align: center;

      .header {
        background: url(~/static/img/logo3.png);
        width: 398px;
        background-repeat: no-repeat;
        height: 90px;
        margin-top: 10px;
      }

      .desc {
        .title {
          font-size: 28px;
          color: #009180;
          font-family: "Myriad Pro", "Helvetica Neue", Arial, Helvetica,
            sans-serif;
          font-weight: 600;
        }
      }
    }
    .main-content {
      width: 368px;
      margin: 0 auto;
      @media screen and (max-width: 576px) {
        width: 95%;
      }
      @media screen and (max-width: 320px) {
        .captcha-button {
          font-size: 14px;
        }
      }
    }
    .main-content2 {
      width: 736px;
      margin: 0 auto;
      @media screen and (max-width: 576px) {
        width: 95%;
      }
      @media screen and (max-width: 320px) {
        .captcha-button {
          font-size: 14px;
        }
      }
    }
  }
  }
}
</style>
