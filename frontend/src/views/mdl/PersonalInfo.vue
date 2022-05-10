<template>
  <a-card :bordered="false" class="card-area myArea">
    <a-form :form="form" layout="vertical">
      <a-row>
        <a-col :span="6">
          <div style="padding: 0px 15px 15px 15px; background-color: white">
            <div style="border-top: 2px solid #00a694">
              <a-form-item label="性别">
                {{ user.sexName }}
              </a-form-item>
              <a-form-item label="工号">
                {{ user.yggh }}
              </a-form-item>
              <a-form-item label="出生年月">
                {{ user.birthday }}
              </a-form-item>
              <a-form-item label="籍贯">
                {{ user.jiguan }}
              </a-form-item>
              <a-form-item label="身份证号">
                {{ user.idCard }}
              </a-form-item>
              <a-form-item label="政治面貌">
                {{ user.politicalStatus }}
              </a-form-item>
              <a-form-item label="最后学位">
                {{ user.edu }}
              </a-form-item>
              <a-form-item label="来院日期">
                {{ user.schoolDate }}
              </a-form-item>
           
              <a-form-item label="职称类型">
                {{ user.zhicheng }}
              </a-form-item>
                 <a-form-item label="教学职称">
                {{ user.zyjsgw }}
              </a-form-item>
                <a-form-item label="临床职称">
                {{ user.zyjsgwLc }}
              </a-form-item>
              <a-form-item label="内聘教学专业技术职务">
                {{ user.zyjsNpjx }}
              </a-form-item>
                <a-form-item label="内聘临床专业技术职务">
                {{ user.zyjsNp }}
              </a-form-item>
            </div>
          </div>
        </a-col>
        <a-col :span="18">
          <div>
            <a-row>
              <a-col :span="12">
                <div style="padding: 0px 15px 15px 15px">
                  <div style="border-top: 2px solid #00a694">
                    <a-form-item label="执业范围">
                      {{ user.yishiZhiyefanwei }}
                    </a-form-item>
                    <a-form-item label="医疗技术专长">
                      {{ user.xcszyjzc }}
                    </a-form-item>
                    <a-form-item label="医疗情况记载"> </a-form-item>
                  </div>
                </div>
              </a-col>
              <a-col :span="12">
                <div
                  style="padding: 0px 15px 15px 15px; background-color: white"
                >
                  <div style="border-top: 2px solid #00a694">
                    <a-form-item label="医师资格证号">
                      {{ user.yishiZgzsbianhao }}
                    </a-form-item>
                    <a-form-item label="医师执业证号">
                      {{ user.yishiZiyebianhao }}
                    </a-form-item>
                    <a-form-item label="所在科室">
                      {{ user.deptName }}
                    </a-form-item>
                  </div>
                </div>
                
              </a-col>
            </a-row>
            <a-row> <a-col :span='24'><div style="text-align:center;"> <a-button type="primary" @click="edit">编辑</a-button></div></a-col></a-row>
          </div>
          <div style="padding: 0px 15px 15px 15px"> 
          <a-card
            style="
              border-top: 2px solid #00a694;
              border-left: 0px;
              border-right: 0px;
              border-bottom: 0px !important;
            "
          >
            <template slot="title">
              <a-icon type="home" style="color: #00a694;font-size:20px;"></a-icon
              ><span style="color: #00a694; font-weight: bold;font-size:20px;"> 资质权限</span>
            </template>
            
          </a-card>
          <a-card title="医疗主任资质" headStyle="font-weight:bold;">
             <person-manager type="医疗主任">
             </person-manager>
          </a-card>
          <a-card title="医疗组长资质" headStyle="font-weight:bold;">
              <person-manager type="医疗组长">
             </person-manager>
          </a-card>
          <a-card title="质控员资质" headStyle="font-weight:bold;">
            <person-manager type="质控员">
             </person-manager>
          </a-card>
          <a-card title="值班资质" headStyle="font-weight:bold;">
            <person-zhi-ban>
            </person-zhi-ban>
          </a-card>
          <a-card title="会诊资质" headStyle="font-weight:bold;">
             {{this.dkHuiZhen()}}</br>{{this.ddkHuiZhen()}}
          </a-card>
          <a-card title="考试管理" headStyle="font-weight:bold;">
            <person-chu-fang type="考试管理">
            </person-chu-fang>
          </a-card>
            <a-card title="基本处方权" headStyle="font-weight:bold;">
            <person-chu-fang type="基本处方权">
            </person-chu-fang>
          </a-card>
          <a-card title="抗菌药物分级管理" headStyle="font-weight:bold;">
            <person-chu-fang type="抗菌药物分级管理">
            </person-chu-fang>
          </a-card>
          <a-card title="麻精药物处方权" headStyle="font-weight:bold;">
             <person-chu-fang type="麻精药物处方权">
            </person-chu-fang>
          </a-card>
          <a-card title="专业类资质" headStyle="font-weight:bold;">
             <mdl-b-special-index>
             </mdl-b-special-index>
          </a-card>
           <a-card title="行业类资质" headStyle="font-weight:bold;">
             <mdl-b-profession-index>
             </mdl-b-profession-index>
          </a-card>
           <a-card title="不良积分记录" headStyle="font-weight:bold;">
             <person-bad-record>
             </person-bad-record>
          </a-card>
           <!-- <a-card
            style="
              margin-top: 10px;
              border-top: 2px solid #00a694;
              border-left: 0px;
              border-right: 0px;
              border-bottom: 0px !important;
            "
          >
            <template slot="title">
              <a-icon type="heat-map" style="color: #00a694;font-size:20px;"></a-icon
              ><span style="color: #00a694; font-weight: bold;font-size:20px;"> 学习、工作经历和职称评定</span>
            </template>
          </a-card>
           <a-card title="主要学习经历" headStyle="font-weight:bold;">
          </a-card>
          <a-card title="国内进修、学习经历" headStyle="font-weight:bold;">
          </a-card>
          <a-card title="国外留学、进修、学习经历" headStyle="font-weight:bold;">
          </a-card>
          <a-card title="主要工作经历" headStyle="font-weight:bold;">
          </a-card>
          <a-card title="职称评定" headStyle="font-weight:bold;">
          </a-card> -->
          </div>
        </a-col>
      </a-row>
    </a-form>
    <user-info-view
      ref="sdlBUserEdit"
      @close="handleEditClose"
      @success="handleEditSuccess"
      :editVisiable="editVisiable"
    >
    </user-info-view>
  </a-card>
</template>
<script>
import moment from "moment";
import PersonManager from './MdlBManager/PersonManager.vue'
import PersonZhiBan from './MdlBManager/PersonZhiBan.vue'
import PersonHuiZhen from './MdlBManager/PersonHuiZhen.vue';
import PersonChuFang from './MdlBChufang/PersonChuFang.vue';
import UserInfoView from './UserInfoView.vue';
import MdlBProfessionIndex from './MdlBProfession/MdlBProfessionIndex.vue';
import MdlBSpecialIndex from './MdlBSpecial/MdlBSpecialIndex.vue';
import PersonBadRecord from './MdlBBadrecord/PersonBadRecord.vue';
const formItemLayout = {
  labelCol: { span: 6 },
  wrapperCol: { span: 18 },
};
export default {
  name: "SdlBScheduleAdd",
  props: {
    addVisiable: {
      default: false,
    },
  },
  components: { PersonManager, PersonZhiBan, PersonHuiZhen, PersonChuFang, UserInfoView, MdlBProfessionIndex, MdlBSpecialIndex, PersonBadRecord },
  data() {
    return {
      loading: false,
      formItemLayout,
      dateFormat: "YYYY-MM",
      form: this.$form.createForm(this),
      sdlBSchedule: {},
      startDate: "",
      endDate: "",
      user: {},
      editVisiable: false
    };
  },
  mounted() {
    this.fetch();
  },
  methods: {
     handleEditSuccess() {
      this.editVisiable = false;
      this.$message.success("修改成功");
      this.fetch();
    },
    handleEditClose() {
      this.editVisiable = false;
    },
    edit() {
      this.$refs.sdlBUserEdit.setFormValues(this.user);
      this.editVisiable = true;
    },
    dkHuiZhen() {
        const {user} = this
         if(user.zhicheng=='中级'||user.zhicheng=='副高'||user.zhicheng=='正高'){
           return '单科会诊资质: 是'
         }
         return '单科会诊资质: 否'
    },
    ddkHuiZhen() {
       const {user} = this
         if(user.zhicheng=='副高'||user.zhicheng=='正高'){
           return '多学科会诊资质: 是'
         }
          if(user.zhicheng=='中级'&&user.docType=='医技'){
           return '多学科会诊资质: 是'
         }
          if(user.zyjsNp!=null&&user.zyjsNp!=='null'&&user.zyjsNp!==''){
           return '多学科会诊资质: 是'
         }
         if(user.zyjsNpjx!=null&&user.zyjsNpjx!=='null'&&user.zyjsNpjx!==''){
           return '多学科会诊资质: 是'
         }
         return '多学科会诊资质: 否'
    },
    fetch() {
      this.$get("sdlBUser", {
        userAccount: this.$store.state.account.user.username,
      }).then((r) => {
        this.user = r.data.rows[0];
      });
    },
  },
};
</script>

<style lang="less">
.myArea{
.ant-col {
  .ant-form-item-label {
    background: #fafafa;
    border-top: 1px #ddd dotted;
    padding: 8px 2px;
    label {
      font-weight: bold !important;
    }
  }
}
.ant-form-item-control {
  padding: 0px 5px;
}
}
</style>
<style lang="less" scoped>
@import "../../../static/less/Common";
</style>