<template>
  <a-drawer
    :title="drawerTitle"
    :maskClosable="false"
    width="80%"
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="editVisiable"
    style="height: calc(100% - 15px); overflow: auto; padding-bottom: 13px"
  >
  <a-row type="flex" justify="center">
      <b>
        <h2>{{flowTitle}}</h2>
      </b>
    </a-row>
    <div style="margin: 5px 0px">
        <a-row type="flex" justify="center">
          <a-col :span="colSpan">
            <table class="formtab">
              <tr>
                <td class="tdRight" style="width: 13%">
                  科室：
                </td>
                <td style="width: 25%">
                  {{ xxbBResult.deptNew }}
                </td>
                <td class="tdRight" style="width: 10%">
                  项目名称：
                </td>
                <td colspan="3" style="width: 52%">
                  {{ xxbBResult.projectName }}
                </td>
              </tr>
              <tr>
                <td class="tdRight" style="width: 13%">
                  申报日期：
                </td>
                <td style="width: 25%">
                  {{ dateformat(xxbBResult.applydat) }}
                </td>
                <td class="tdRight" style="width: 10%">
                  开展时间：
                </td>
                <td style="width: 20%">
                  {{ dateformat(xxbBResult.kzsrtdat) }}
                </td>
                <td class="tdRight" style="width: 12%">
                  开展例数：
                </td>
                <td style="width: 20%">
                  {{ xxbBResult.kzls }}
                </td>
              </tr>
            </table>
          </a-col>
        </a-row>
      </div>
      <!-- 项  目  负  责  人 -->
      <a-row type="flex" justify="center">
        <b>
          <h2>项 目 负 责 人</h2>
        </b>
      </a-row>
      <div style="margin: 5px 0px">
        <a-row type="flex" justify="center">
          <a-col :span="colSpan">
            <table class="formtab">
              <tr>
                <td class="tdRight" style="width: 13%">
                  所在院区：
                </td>
                <td style="width: 25%">
                  {{ xxbBResult.yuanqu }}
                </td>
                <td colspan="4"></td>
              </tr>
              <tr>
                <td class="tdRight" style="width: 13%">
                  负责人：
                </td>
                <td style="width: 25%">
                  {{ xxbBResult.userAccountName }}
                </td>
                <td class="tdRight" style="width: 10%">
                  性别：
                </td>
                <td style="width: 20%">
                  {{ xxbBResult.sexName }}
                </td>
                <td class="tdRight" style="width: 12%">
                  出生年月：
                </td>
                <td style="width: 20%">
                  {{ xxbBResult.birthday }}
                </td>
              </tr>
              <tr>
                <td class="tdRight">
                  学历、学位：
                </td>
                <td>
                  {{ xxbBResult.edu }}
                </td>
                <td class="tdRight">
                  职称：
                </td>
                <td>
                  {{ xxbBResult.zhichenglc }}
                </td>
                <td class="tdRight">
                  <font class="fontColor"></font>手机号码：
                </td>
                <td>
                  {{ xxbBResult.telephone }}
                </td>
              </tr>
            </table>
          </a-col>
        </a-row>
      </div>
      <!-- 项目主要人员 -->
      <a-row type="flex" justify="center">
        <b>
          <h2>项目主要人员</h2>
        </b>
      </a-row>
      <!-- border:1px solid #2894FF; -->
      <div style="margin: 5px 0px">
        <a-row type="flex" justify="center">
          <a-col :span="colSpan">
            <xxbBResultData-look ref="xxbBResultDList"> </xxbBResultData-look>
          </a-col>
        </a-row>
      </div>
      <!-- 项目主要内容 -->
      <a-row type="flex" justify="center">
        <b>
          <h2>项目主要内容</h2>
        </b>
      </a-row>
      <div style="margin: 5px 0px">
        <a-row type="flex" justify="center">
          <a-col :span="colSpan">
            <table class="formtab">
              <tr>
                <td colspan="2" style="width:100%;padding:10px 5px">
                <b>一、项目主要内容、目标和意义（摘要），重点是对学科的推动力：</b>
                </td>
              </tr>
              <tr>
                <td colspan="2" style="width:100%;padding:20px 5px">
                  {{ xxbBResult.projectcontent }}
                </td>
              </tr>
              <tr>
                <td colspan="2" style="width:100%;padding:10px 5px">
                <b>二、项目的科学依据（包括国内外进展、新颖性和创新性）：</b>
                </td>
              </tr>
              <tr>
                <td colspan="2" style="width:100%;padding:20px 5px">
                  {{ xxbBResult.projectkxyj }}
                </td>
              </tr>
              <tr>
                <td colspan="2" style="width:100%;padding:10px 5px">
                <b>三、项目采取的方法、技术路线：</b>
                </td>
              </tr>
              <tr>
                <td colspan="2" style="width:100%;padding:20px 5px">
                  {{ xxbBResult.projectffjslx }}
                </td>
              </tr>
              <tr>
                <td colspan="2" style="width:100%;padding:10px 5px">
                <b>四、项目解决的关键问题：</b>
                </td>
              </tr>
              <tr>
                <td colspan="2" style="width:100%;padding:20px 5px">
                  {{ xxbBResult.projectkey }}
                </td>
              </tr>
              <tr>
                <td colspan="2" style="width:100%;padding:10px 5px">
                <b>五、技术存在的主要风险及预案：</b>
                </td>
              </tr>
              <tr>
                <td colspan="2" style="width:100%;padding:20px 5px">
                  {{ xxbBResult.jsfxya }}
                </td>
              </tr>
              <tr>
                <td colspan="2" style="width:100%;padding:10px 5px">
                <b>六、项目产生的经济效益、社会效益：</b>
                </td>
              </tr>
              <tr>
                <td colspan="2" style="width:100%;padding:20px 5px">
                  {{ xxbBResult.projectjjshxy }}
                </td>
              </tr>
              <tr>
                <td colspan="2" style="width:100%;padding:10px 5px">
                <b>七、主要技术文献目录及出处：</b>
                </td>
              </tr>
              <tr>
                <td colspan="2" style="width:100%;padding:20px 5px">
                  {{ xxbBResult.jswxmlcc }}
                </td>
              </tr>
              <tr>
                <td colspan="2" style="width:100%;padding:10px 5px">
                <b>八、新闻报道情况：</b>
                </td>
              </tr>
              <tr>
                <td colspan="2" style="width:100%;padding:20px 5px">
                  {{ xxbBResult.newbdqk }}
                </td>
              </tr>
              <tr>
                <td colspan="2" style="width:100%;padding:10px 5px">
                <b>九、项目开展情况：</b>
                </td>
              </tr>
              <tr>
                <td colspan="2" style="width:100%;padding:20px 5px">
                  {{ xxbBResult.projectkzqk }}
                </td>
              </tr>
              <tr>
                <td colspan="2" style="width:100%">
                  <a-row>
                    <a-col :span=8>
                      <upload-file
                        ref="xmkzqkFile"
                        :baseId="xxbBResult.id"
                        btnTitle="上传项目开展情况Excel"
                        :isEdit="false"
                        refTab="xxbresult_xmkzqk"
                      >
                      </upload-file>
                    </a-col>
                  </a-row>
                </td>
              </tr>
            </table>
          </a-col>
        </a-row>
      </div>
    <div style="margin: 5px 0px">
      <a-row type="flex" justify="center">
        <a-col :span="colSpan">
          <xxbBResult-f 
            ref="xxbBResultF"
            @isBtnEdit="isBtnEdit"
            @success="success"
          > 
          </xxbBResult-f>
        </a-col>
      </a-row>
    </div>
    <div class="drawer-bootom-button">
      <a-popconfirm
        title="确定放弃编辑？"
        @confirm="onClose"
        okText="确定"
        cancelText="取消"
      >
        <a-button style="margin-right: 0.8rem">取消</a-button>
      </a-popconfirm>
      <a-space size="large">
        <a-button @click="handleSubmit(0)" v-if="isBtn" :loading="loading">保存</a-button>
        <a-popconfirm
          title="确定同意审核？"
          v-if="isBtn"
          @confirm="handleSubmit(1)"
          okText="确定"
          cancelText="取消"
        >
        <a-button v-if="isBtn"  type="primary" :loading="loading"
          >同意审核</a-button>
        </a-popconfirm>
        <a-popconfirm
          title="确定驳回审核？"
          v-if="isBtn"
          @confirm="handleSubmit(2)"
          okText="确定"
          cancelText="取消"
        >
        <a-button v-if="isBtn"  type="danger" :loading="loading"
          >驳回审核</a-button>
        </a-popconfirm>
        <a-popconfirm
          title="确定终止申报"
          v-if="isBtn"
          @confirm="handleSubmit(9)"
          okText="确定"
          cancelText="取消"
        >
        <a-button v-if="isBtn"  type="danger" :loading="loading"
          >终止申报</a-button>
        </a-popconfirm>
      </a-space>
    </div>
  </a-drawer>
</template>

<script>
import XxbBResultDataLook from "./XxbBResultDataLook";
import XxbBResultF from "./XxbBResultF";
import UploadFile from "../common/UploadFile";
import moment from "moment";
const formItemLayout = {
  labelCol: {
    span: 7,
  },
  wrapperCol: {
    span: 14,
  },
};
export default {
  name: "XxbBResultEdit",
  components: {
    XxbBResultDataLook,
    XxbBResultF,
    UploadFile
  },
  props: {
    editVisiable: {
      default: false,
    },
  },
  data() {
    return {
      loading: false,
      formItemLayout,
      isEdit: false,
      isBtn: false,
      drawerTitle: '新增',
      colSpan: 23,
      flowTitle: '',
      xxbBResult: {},
    };
  },
  mounted() {},
  methods: {
    reset() {
      this.loading = false;
      this.isEdit = false;
      this.xxbBResult = {}
      this.$refs.xxbBResultDList.reset();
      this.$refs.xxbBResultF.reset()
    },
    dateformat(date){
      return moment(date).format('YYYY-MM-DD')
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },
    setFormValues(xxbBResult,tit,flowTitle) {
      this.drawerTitle = tit
      this.isBtn = false
      if(tit=='查看') {
        this.isEdit = false;
      } else {
        this.isEdit = true;
      }
      this.flowTitle = flowTitle
      this.xxbBResult = xxbBResult;
      let d = new Date(xxbBResult.applydat)
      let month = '' + (d.getMonth() + 1)
      let day = '' + d.getDate()
      let year = d.getFullYear()

      if (month.length < 2) month = '0' + month;
      if (day.length < 2) day = '0' + day;
      this.xxbBResult.applydat = year + '-' + month + '-' + day
      
      setTimeout(() => {
        this.$refs.xxbBResultDList.setFieldValues(xxbBResult.id);
        this.$refs.xxbBResultF.setFieldValues(xxbBResult.id,1,this.isEdit,
        xxbBResult.gjlx,xxbBResult.gjxj,xxbBResult.gnlx,xxbBResult.gnxj,xxbBResult.snlx,xxbBResult.snxj);
      }, 200);
    },
    isBtnEdit (v) {
      this.isBtn = v
    },
    success () {
      this.$refs.xxbBResultF.reset()
      this.$emit("success");
    },
    handleSubmit(type) {
     this.$refs.xxbBResultF.handleSubmit(type)
    },
  },
};
</script>
<style lang="less" scoped>
.formtab {
  width: 100%;
  padding: 5px;
}
.formtab {
  tr td {
    padding: 5px;
    border: 1px solid #e8e8e8;
    color: rgba(0, 0, 0, 0.85);
    word-wrap: break-word;    word-break: break-all;
  }
  .tdRight {
    text-align:center;
  }
  .tdTitle {
    border: 0px;
    text-align: center;
  }
}
.fontColor{
    color:#f5222d;
  }
</style>
