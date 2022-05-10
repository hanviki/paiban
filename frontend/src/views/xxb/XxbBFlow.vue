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
        <h2>{{projectType}}</h2>
      </b>
    </a-row>
    <div style="margin: 5px 0px">
      <a-row type="flex" justify="center">
        <a-col :span="colSpan">
          <table class="formtab">
            <tr>
              <td class="tdRight" style="width: 15%;padding:10px 5px"><font class="fontColor">*</font>申请科室：</td>
              <td style="width: 23%;">
                {{ xxbBCheck.deptNew }}
              </td>
              <td class="tdRight" style="width: 10%;padding:10px 5px"><font class="fontColor">*</font>项目名称：</td>
              <td colspan="3" style="width: 52%;">
                {{ xxbBCheck.projectName }}
              </td>
            </tr>
            <tr>
              <td class="tdRight" style="width: 15%;"><font class="fontColor">*</font>
              是否为限制类医疗技术:
              </td>
              <td style="width: 23%;">
                {{ xxbBCheck.isxzyljs == 1 ? '是' : '否' }}
              </td>
              <td class="tdRight" style="width: 10%;"><font class="fontColor">*</font>项目级别：</td>
              <td style="width: 20%">
                  {{ 
                      xxbBCheck.projectLevel == 1 ? '一级' : 
                      xxbBCheck.projectLevel == 2 ? '二级' : 
                      xxbBCheck.projectLevel == 3 ? '三级' : 
                      xxbBCheck.projectLevel == 4 ? '四级' : 
                      xxbBCheck.projectLevel
                  }}
              </td>
              <td class="tdRight" style="width: 12%;"><font class="fontColor">*</font>申请日期：</td>
              <td style="width: 20%;">
                {{ xxbBCheck.applydat }}
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
              <tr> <td class="tdRight" style="width: 15%;"><font class="fontColor">*</font>所在院区：</td>
                <td style="width: 23%;">
                    {{ xxbBCheck.yuanqu }}
                </td><td colspan="4"></td>
                </tr>
            <tr>
              <td class="tdRight" style="width: 15%;padding:10px 5px"><font class="fontColor">*</font>姓　　名：</td>
              <td style="width: 23%;">
                {{ xxbBCheck.userAccountName }}
              </td>
              <td class="tdRight" style="width: 10%;padding:10px 5px">性　　别：</td>
              <td style="width: 20%;">
                {{ xxbBCheck.sexName }}
              </td>
              <td class="tdRight" style="width: 12%;padding:10px 5px">出生年月：</td>
              <td style="width: 20%;">
                {{ xxbBCheck.birthday }}
              </td>
            </tr>
            <tr>
              <td class="tdRight" style="padding:10px 5px">学历、学位：</td>
              <td>
                {{ xxbBCheck.edu }}
              </td>
              <td class="tdRight" style="padding:10px 5px">职　　称：</td>
              <td>
                {{ xxbBCheck.zhichenglc }}
              </td>
              <td class="tdRight" style="padding:10px 5px">职　　务：</td>
              <td>
                {{ xxbBCheck.zhiwu }}
              </td>
            </tr>
            <tr>
              <td class="tdRight" style="padding:10px 5px">专　　业：</td>
              <td>
                {{ xxbBCheck.zhuany }}
              </td>
              <td class="tdRight" style="padding:10px 5px">专　　长：</td>
              <td>
                {{ xxbBCheck.zhuanc }}
              </td>
              <td class="tdRight" style="padding:10px 5px"><font class="fontColor"></font>手机号码：</td>
              <td>
                {{ xxbBCheck.telephone }}
              </td>
            </tr>
          </table>
        </a-col>
      </a-row>
    </div>
    <!-- 项目参与专科 -->
    <a-row type="flex" justify="center" v-show="xxbBCheck.projectType == 2 ? true:false">
      <b>
        <h2>项目参与专科</h2>
      </b>
    </a-row>
    <!-- border:1px solid #2894FF; -->
    <div style="margin: 5px 0px" v-show="xxbBCheck.projectType == 2 ? true:false">
      <a-row type="flex" justify="center">
        <a-col :span="colSpan">
          <xxbBProjDept-look ref="xxbBProjDeptList"> </xxbBProjDept-look>
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
          <xxbBData-look ref="xxbBCheckDList" @jisuan="jisuan"> </xxbBData-look>
        </a-col>
      </a-row>
    </div>
    <!-- 项目主要人员情况 -->
    <a-row type="flex" justify="center">
      <b>
        <h2>项目主要人员情况</h2>
      </b>
    </a-row>
    <div style="margin: 5px 0px;text-align:center">
      <a-row type="flex" justify="center">
        <a-col :span="colSpan">
          <table class="formtab">
            <tr>
              <td class="tdRight" rowspan="3" style="width:20%">职称结构：</td>
              <td colspan="3" style="width:20%">
                医　　生
              </td>
              <td colspan="3" style="width:20%">
                护　　士
              </td>
              <td colspan="3" style="width:20%">
                技术人员
              </td>
              <td colspan="3" style="width:20%">
                其　　他
              </td>
            </tr>
            <tr>
              <td>高级</td>
              <td>中级</td>
              <td>初级</td>
              <td>高级</td>
              <td>中级</td>
              <td>初级</td>
              <td>高级</td>
              <td>中级</td>
              <td>初级</td>
              <td>高级</td>
              <td>中级</td>
              <td>初级</td>
            </tr>
            <tr>
              <td>{{dataRs.zcysgj}}</td>
              <td>{{dataRs.zcyszj}}</td>
              <td>{{dataRs.zcyscj}}</td>
              <td>{{dataRs.zchsgj}}</td>
              <td>{{dataRs.zchszj}}</td>
              <td>{{dataRs.zchscj}}</td>
              <td>{{dataRs.zcjsgj}}</td>
              <td>{{dataRs.zcjszj}}</td>
              <td>{{dataRs.zcjscj}}</td>
              <td>{{dataRs.zcqtgj}}</td>
              <td>{{dataRs.zcqtzj}}</td>
              <td>{{dataRs.zcqtcj}}</td>
            </tr>
            <tr>
              <td class="tdRight" rowspan="3" style="width:20%">学历结构：</td>
              <td colspan="3" style="width:20%">
                博士研究生
              </td>
              <td colspan="3" style="width:20%">
                硕士研究生
              </td>
              <td colspan="3" style="width:20%">
                本科学士
              </td>
              <td colspan="3" style="width:20%">
                专科及其他
              </td>
            </tr>
            <tr>
              <td colspan="3" style="width:20%">
                {{dataRs.xlbs}}
              </td>
              <td colspan="3" style="width:20%">
                {{dataRs.xlyjs}}
              </td>
              <td colspan="3" style="width:20%">
                {{dataRs.xlbk}}
              </td>
              <td colspan="3" style="width:20%">
                {{dataRs.xlqt}}
              </td>
            </tr>
          </table>
        </a-col>
      </a-row>
    </div>
    <!-- 项目级类型 -->
    <a-row type="flex" v-show="xxbBCheck.projectLb == 0 ? false:true" justify="center">
      <b>
        <h2>项目级类型</h2>
      </b>
    </a-row>
    <div style="margin: 5px 0px" v-show="xxbBCheck.projectLb == 0 ? false:true">
      <a-row type="flex" justify="center">
        <a-col :span="colSpan">
          <table class="formtab">
            <tr>
              <td class="tdRight" style="width:20%;padding:10px 5px"><font class="fontColor">*</font>项目类型：</td>
              <td style="width:80%">
                <a-radio-group name="levRadioGroup" v-model="xxbBCheck.projectLb">
                    <a-radio :value="1"> 手术类 </a-radio>
                    <a-radio :value="2"> 治疗操作类 </a-radio>
                    <a-radio :value="3"> 其他类 </a-radio>
                  </a-radio-group>
              </td>
            </tr>
          </table>
        </a-col>
      </a-row>
    </div>
    <!-- 项目第一操作者的最低职称限定 -->
    <a-row type="flex" justify="center">
      <b>
        <h2>项目第一操作者的最低职称限定</h2>
      </b>
    </a-row>
    <div style="margin: 5px 0px">
      <a-row type="flex" justify="center">
        <a-col :span="colSpan">
          <table class="formtab">
            <tr>
              <td class="tdRight" style="width:20%;padding:10px 5px"><font class="fontColor">*</font>级　　别：</td>
              <td style="width:80%">
                <a-radio-group name="levRadioGroup" v-model="xxbBCheck.lev">
                  <a-radio :value="1"> 正高级 </a-radio>
                  <a-radio :value="2"> 副高级以上 </a-radio>
                  <a-radio :value="3"> 中级以上 </a-radio>
                  <a-radio :value="4"> 初级以上 </a-radio>
                  <a-radio :value="5"> 其他 </a-radio>
                </a-radio-group>
              </td>
            </tr>
            <tr>
              <td class="tdRight" style="width:20%;padding:10px 5px"><font class="fontColor">*</font>类　　别：</td>
              <td style="width:80%">
                <a-radio-group name="typRadioGroup" v-model="xxbBCheck.typ">
                    <a-radio :value="1"> 医　　师 </a-radio>
                    <a-radio :value="2"> 护　　士 </a-radio>
                    <a-radio :value="3"> 药剂人员 </a-radio>
                    <a-radio :value="4"> 技术人员 </a-radio>
                    <a-radio :value="5"> 其他 </a-radio>
                  </a-radio-group>
              </td>
            </tr>
            <tr>
              <td class="tdRight" style="width:20%;padding:20px 5px"><font class="fontColor">*</font>
              {{xxbBCheck.projectType ==0? '项目的主要内容及意义：':'项目的主要内容：'}}
              </td>
              <td style="width:80%">
                {{ xxbBCheck.projectcontent }}
              </td>
            </tr>
            <tr v-show="xxbBCheck.projectType !=0">
              <td class="tdRight" style="width:20%;padding:20px 5px"><font class="fontColor">*</font>项目的目的、意义及<br>创新之处：</td>
              <td style="width:80%">
                {{ xxbBCheck.projectmdyycx }}
              </td>
            </tr>
            <tr v-show="xxbBCheck.projectType !=0">
              <td class="tdRight" style="width:20%;padding:20px 5px"><font class="fontColor">*</font>国内外研究与应用概况：</td>
              <td style="width:80%">
                {{ xxbBCheck.gnwyjyygk }}
              </td>
            </tr>
            <tr>
              <td class="tdRight" style="width:20%;padding:20px 5px"><font class="fontColor">*</font>技术临床应用安全性分析：</td>
              <td style="width:80%">
                {{ xxbBCheck.lincaqfx }}
                <upload-file
                  ref="aqxfxFile"
                  :baseId="xxbBCheck.id"
                  btnTitle="上传相关佐证材料PDF"
                  :isEdit="false"
                  refTab="xxbcheck_aqxfx"
                >
                </upload-file>
              </td>
            </tr>
            <tr>
              <td class="tdRight" style="width:20%;padding:20px 5px"><font class="fontColor">*</font>技术临床应用有效性分析：</td>
              <td style="width:80%">
                {{ xxbBCheck.lincyyfx }}
                <upload-file
                  ref="yxxfxFile"
                  :baseId="xxbBCheck.id"
                  btnTitle="上传相关佐证材料PDF"
                  :isEdit="false"
                  refTab="xxbcheck_yxxfx"
                >
                </upload-file>
              </td>
            </tr>
            <tr v-show="xxbBCheck.projectType !=0">
              <td class="tdRight" style="width:20%;padding:20px 5px"><font class="fontColor">*</font>可行性分析及实施计划：</td>
              <td style="width:80%">
                {{ xxbBCheck.kxxfxssjh }}
              </td>
            </tr>
            <tr v-show="xxbBCheck.projectType !=0">
              <td class="tdRight" style="width:20%;padding:20px 5px"><font class="fontColor">*</font>项目可解决的临床问题及<br>效益分析：</td>
              <td style="width:80%">
                {{ xxbBCheck.projectlcwtxyfx }}
              </td>
            </tr>
            <tr>
              <td class="tdRight" style="width:20%;padding:20px 5px"><font class="fontColor">*</font>科室已具备的技术和<br>设备条件：</td>
              <td style="width:80%">
                {{ xxbBCheck.lincjssb }}
              </td>
            </tr>
            <tr v-show="xxbBCheck.projectType !=0">
              <td class="tdRight" style="width:20%;padding:20px 5px"><font class="fontColor">*</font>操作人员技术培训及<br>岗位职责情况：</td>
              <td style="width:80%">
                {{ xxbBCheck.ryjjpxgwzzqk }}
              </td>
            </tr>
            <tr>
              <td class="tdRight" style="width:20%;padding:20px 5px"><font class="fontColor">*</font>适应症：</td>
              <td style="width:80%">
                {{ xxbBCheck.syz }}
              </td>
            </tr>
            <tr>
              <td class="tdRight" style="width:20%;padding:20px 5px"><font class="fontColor">*</font>禁忌症：</td>
              <td style="width:80%">
                {{ xxbBCheck.jjz }}
              </td>
            </tr>
            <tr v-show="xxbBCheck.projectType !=0">
              <td class="tdRight" style="width:20%;padding:20px 5px"><font class="fontColor">*</font>可能造成的不良后果、<br>并发症及相应的防范措施：</td>
              <td style="width:80%">
                {{ xxbBCheck.blhgbfzffcs }}
              </td>
            </tr>
            <tr>
              <td class="tdRight" style="width:20%;padding:20px 5px"><font class="fontColor">*</font>操作规程：</td>
              <td style="width:80%">
                {{ xxbBCheck.czgz }}
                 <upload-file
                  ref="czgzFile"
                  :baseId="xxbBCheck.id"
                  btnTitle="上传相关操作规程PDF"
                  :isEdit="false"
                  refTab="xxbcheck_czgz"
                >
                </upload-file>
              </td>
            </tr>
            <tr v-show="xxbBCheck.projectType !=0">
              <td class="tdRight" style="width:20%;padding:20px 5px"><font class="fontColor">*</font>质控措施：</td>
              <td style="width:80%">
                {{ xxbBCheck.zkcs }}
              </td>
            </tr>
            <tr v-show="xxbBCheck.projectType !=0">
              <td class="tdRight" style="width:20%;padding:20px 5px"><font class="fontColor">*</font>是否在我院诊疗科目范畴：</td>
              <td style="width:80%">
                {{ xxbBCheck.sfwyzlkmfw }}
              </td>
            </tr>
            <tr>
              <td class="tdRight" style="width:20%;padding:10px 5px"><font v-if="isHong" class="fontColor">*(项目级别:一级、二级必须上传PDF)</font>项目查新报告：</td>
              <td style="width:80%">
                  <upload-file
                    ref="xmcxbgFile"
                    :baseId="xxbBCheck.id"
                    btnTitle="上传项目查新报告PDF"
                    :isEdit="false"
                    refTab="xxbcheck_xmcxbg"
                  >
                  </upload-file>
              </td>
            </tr>
            <tr>
              <td class="tdRight" style="width:20%;padding:10px 5px"><font class="fontColor">*</font>新技术新业务临床应用<br>知情同意书：</td>
              <td style="width:80%">
                <upload-file
                  ref="yxxfxFile"
                  :baseId="xxbBCheck.id"
                  btnTitle="上传新技术新业务临床应用知情同意书PDF"
                  :isEdit="false"
                  refTab="xxbcheck_lcyyzqtys"
                >
                </upload-file>
              </td>
            </tr>
          </table>
        </a-col>
      </a-row>
    </div>
    <div style="margin: 5px 0px">
      <a-row type="flex" justify="center">
        <a-col :span="colSpan">
          <xxbBDept-flow
            ref="xxbBDeptFlow"
            @isBtnEdit="isBtnEdit"
            @success="success"
          >
          </xxbBDept-flow>
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
          title="确定终止项目"
          v-if="isBtn"
          @confirm="handleSubmit(9)"
          okText="确定"
          cancelText="取消"
        >
        <a-button v-if="isBtn"  type="danger" :loading="loading"
          >项目终止</a-button>
        </a-popconfirm>
      </a-space>
    </div>
  </a-drawer>
</template>

<script>
import XxbBDataLook from "./XxbBDataLook";
import XxbBProjDeptLook from "./XxbBProjDeptLook";
import XxbBDeptFlow from "./XxbBDeptFlow";
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
  name: "XxbBCheckEdit",
  components: {
    XxbBDataLook,
    XxbBProjDeptLook,
    UploadFile,
    XxbBDeptFlow
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
      isHong: true,
      drawerTitle: '新增',
      dataRs: {
        zcysgj: 0,
        zcyszj: 0,
        zcyscj: 0,
        zchsgj: 0,
        zchszj: 0,
        zchscj: 0,
        zcjsgj: 0,
        zcjszj: 0,
        zcjscj: 0,
        zcqtgj: 0,
        zcqtzj: 0,
        zcqtcj: 0,
        xlbs: 0,
        xlyjs: 0,
        xlbk: 0,
        xlqt: 0
      },
      projectType: '',
      colSpan: 23,
      xxbBCheck: {},
    };
  },
  mounted() {},
  methods: {
    reset() {
      this.loading = false;
      this.isEdit = false;
      this.xxbBCheck = {}
      this.$refs.xxbBCheckDList.reset();
      this.$refs.xxbBDeptFlow.reset()
      this.$refs.yxxfxFile.reset();
      this.$refs.aqxfxFile.reset();
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },
    jisuan(dataRs){
      this.dataRs = dataRs
    },
    setFormValues(xxbBCheck,tit,projectType) {
      this.projectType = projectType
      this.drawerTitle = tit
      this.isBtn = false
      if(tit=='查看') {
        this.isEdit = false;
      } else {
        this.isEdit = true;
      }
      this.xxbBCheck = xxbBCheck;
      let d = new Date(xxbBCheck.applydat)
      let month = '' + (d.getMonth() + 1)
      let day = '' + d.getDate()
      let year = d.getFullYear()

      if (month.length < 2) month = '0' + month;
      if (day.length < 2) day = '0' + day;
      this.xxbBCheck.applydat = year + '-' + month + '-' + day
      
      setTimeout(() => {
        this.$refs.xxbBCheckDList.setFieldValues(xxbBCheck.id);
        this.$refs.xxbBProjDeptList.setFieldValues(xxbBCheck.id);
        this.$refs.xxbBDeptFlow.setFieldValues(xxbBCheck.id,xxbBCheck.projectType,1,this.isEdit);
      }, 200);
      if (this.xxbBCheck.projectLevel == 1 || this.xxbBCheck.projectLevel == 2) {
        this.isHong = true
      } else {
        this.isHong = false
      }
    },
    isBtnEdit (v) {
      this.isBtn = v
    },
    success () {
      this.$refs.xxbBDeptFlow.reset()
      this.$emit("success");
    },
    handleSubmit(type) {
     this.$refs.xxbBDeptFlow.handleSubmit(type)
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
