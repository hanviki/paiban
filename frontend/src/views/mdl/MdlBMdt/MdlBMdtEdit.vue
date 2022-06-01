<template>
  <a-drawer
    title="修改"
    :maskClosable="false"
    width="80%"
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="editVisiable"
    style="height: calc(100% - 55px); overflow: auto; padding-bottom: 53px"
  >
     <a-form :form="form">
      <a-card title="团队基本信息" :headStyle="headStyle" >
     
      <a-row>
      <a-col :sm="12">
        <a-form-item v-bind="formItemLayout" label="团队名称">
          <a-input
            placeholder="请输入团队名称"
            v-decorator="[
              'teamName',
              { rules: [{ required: true, message: '团队名称不能为空' }] },
            ]"
          />
        </a-form-item>
      </a-col>
         <a-col :sm="12">
        <a-form-item v-bind="formItemLayout" label="是否开通MDT门诊">
          <a-select
            placeholder="请输入是否开通MDT门诊"
            v-decorator="[
              'isMenzhen',
            ]"
          >
          <a-select-option value="是">
            是
          </a-select-option>
           <a-select-option value="否">
            否
          </a-select-option>
          </a-select>
        </a-form-item>
      </a-col>
      <a-col :sm="12">
        <a-form-item v-bind="formItemLayout" label="牵头科室">
          <a-input
            placeholder="请输入牵头科室"
            v-decorator="[
              'deptHead',
              { rules: [{ required: true, message: '牵头科室不能为空' }] },
            ]"
          />
        </a-form-item>
      </a-col>
    
       <a-col :sm="12">
        <a-form-item v-bind="formItemLayout" label="成立时间">
          <a-date-picker
            placeholder="请选择成立时间"
            :dateFormat="dateFormat"
            v-decorator="['startDate']"
          >
          </a-date-picker>
        </a-form-item>
      </a-col>
      <a-col :sm="12">
        <a-form-item v-bind="formItemLayout" label="废止时间">
          <a-date-picker
            placeholder="请选择废止时间"
            :dateFormat="dateFormat"
            v-decorator="['endDate']"
          >
          </a-date-picker>
        </a-form-item>
      </a-col>
       <a-col :sm="12">
        <a-form-item v-bind="formItemLayout" label="备注">
          <a-textarea
          placeholder="请输入备注"
          v-decorator="[
            'note',
           
          ]"
        />
        </a-form-item>
      </a-col>
        <a-col :sm="12">
       <a-form-item v-bind="formItemLayout" label="团队申报书">
        <upload-single-file
            ref="fileagent"
            @uploadRemove="removeAgent_1"
            @uploadSuc="uploadAgent_1"
          >
          </upload-single-file>
      </a-form-item>
       </a-col>
      </a-row>
       </a-card>
         <a-card title="负责人信息" :headStyle="headStyle" >
           <a-row>
      <a-col :sm="12">
        <a-form-item v-bind="formItemLayout" label="团队负责人1">
          <select-user-remote
            ref="user1"
            v-decorator="[
              'userAccountLeader',
             
            ]"
            @userChang="userChange"
          >
          </select-user-remote>
        </a-form-item>
      </a-col>
       <a-col :sm="12">
        <a-form-item v-bind="formItemLayout" label="团队负责人2">
          <select-user-remote
            ref="user12"
            v-decorator="['userAccountLeader2']"
            @userChang="userChange3"
          >
          </select-user-remote>
        </a-form-item>
      </a-col>
     <a-col :sm="12">
        <a-form-item v-bind="formItemLayout" label="团队负责人电话">
          <a-input
            placeholder="请输入团队负责人电话"
            v-decorator="[
              'telLeader',
            ]"
          />
        </a-form-item>
      </a-col>
       <a-col :sm="12">
        <a-form-item v-bind="formItemLayout" label="团队负责人2电话">
          <a-input
            placeholder="请输入团队负责人2电话"
            v-decorator="[
              'telLeader2',
            ]"
          />
        </a-form-item>
      </a-col>
       <a-col :sm="12">
        <a-form-item v-bind="formItemLayout" label="团队负责人邮箱">
          <a-input
            placeholder="请输入团队负责人邮箱"
            v-decorator="[
              'emailLeader',
            ]"
          />
        </a-form-item>
      </a-col>
        
       <a-col :sm="12">
        <a-form-item v-bind="formItemLayout" label="团队负责人2邮箱">
          <a-input
            placeholder="请输入团队负责人2邮箱"
            v-decorator="[
              'emailLeader2',
            ]"
          />
        </a-form-item>
      </a-col>
         </a-row>
     </a-card>
      <a-card title="团队秘书信息" :headStyle="headStyle" >
     <a-row>
      <a-col :sm="12">
        <a-form-item v-bind="formItemLayout" label="团队秘书">
          <select-user-remote
            ref="user2"
            v-decorator="[
              'userAccountAssist',
           
            ]"
            @userChang="userChange2"
          >
          </select-user-remote>
        </a-form-item>
      </a-col>
      <a-col :sm="12">
        <a-form-item v-bind="formItemLayout" label="团队秘书电话">
          <a-input
            placeholder="请输入团队秘书电话"
            v-decorator="[
              'telAssist',
            ]"
          />
        </a-form-item>
      </a-col>
       <a-col :sm="12">
        <a-form-item v-bind="formItemLayout" label="团队秘书邮箱">
          <a-input
            placeholder="请输入团队秘书邮箱"
            v-decorator="[
              'emailAssist',
            ]"
          />
        </a-form-item>
      </a-col>
     
      <!-- <a-col :sm="12">
        <a-form-item v-bind="formItemLayout" label="团队秘书2">
          <select-user-remote
            ref="user22"
            v-decorator="['userAccountAssist2']"
            @userChang="userChange4"
          >
          </select-user-remote>
        </a-form-item>
      </a-col>
      <a-col :sm="12">
        <a-form-item v-bind="formItemLayout" label="团队秘书2电话">
          <a-input
            placeholder="请输入团队秘书2电话"
            v-decorator="[
              'telAssist2',
            ]"
          />
        </a-form-item>
      </a-col>
       <a-col :sm="12">
        <a-form-item v-bind="formItemLayout" label="团队秘书2邮箱">
          <a-input
            placeholder="请输入团队秘书2邮箱"
            v-decorator="[
              'emailAssist2',
            ]"
          />
        </a-form-item>
      </a-col> -->
      </a-row>
         </a-card>
    </a-form>
        <a-card title="核心成员" :headStyle="headStyle">
        <add-user ref="userHX" :baseId="mdlBMdt.id" :type="0"></add-user>
    </a-card>
    <a-card title="其他成员" :headStyle="headStyle">
        <add-user ref="userQT" :baseId="mdlBMdt.id" :type="1"></add-user>
    </a-card>
      <a-card title="年度总结" :headStyle="headStyle">
        <mdl-b-mdt-file ref="file" :baseId="mdlBMdt.id"></mdl-b-mdt-file>
    </a-card>
    <div class="drawer-bootom-button">
      <a-popconfirm
        title="确定放弃编辑？"
        @confirm="onClose"
        okText="确定"
        cancelText="取消"
      >
        <a-button style="margin-right: 0.8rem">取消</a-button>
      </a-popconfirm>
      <a-button @click="handleSubmit" type="primary" :loading="loading"
        >提交</a-button
      >
    </div>
  </a-drawer>
</template>
<script>
import moment from "moment";
import SelectUserRemote from '../../common/SelectUserRemote.vue';
import AddUser from './AddUser.vue';
import MdlBMdtFile from '../MdlBMdtFile/MdlBMdtFile.vue';
import UploadSingleFile from "../../common/uploadSingleFile"

const formItemLayout = {
  labelCol: { span: 6 },
  wrapperCol: { span: 18 },
};
export default {
  name: "MdlBMdtEdit",
  props: {
    editVisiable: {
      default: false,
    },
  },
  components: {SelectUserRemote, AddUser, MdlBMdtFile, UploadSingleFile},
  data() {
    return {
      loading: false,
      formItemLayout,
       dateFormat: "YYYY-MM-DD",
      form: this.$form.createForm(this),
      mdlBMdt: {},
      headStyle: {
        "font-weight": "bold"
      }
    };
  },
  methods: {
    reset() {
      this.loading = false;
      this.$refs.user1.reset();
      this.$refs.user2.reset();
      this.$refs.user12.reset();
     // this.$refs.user22.reset();
       this.$refs.userHX.reset();
      this.$refs.userQT.reset();
      this.$refs.fileagent.reset();
      this.form.resetFields();
    },
     uploadAgent_1(fileId, fileUrl) {
      this.mdlBMdt.fileId = fileId;
      this.mdlBMdt.fileUrl = fileUrl;
    },
    removeAgent_1() {
      this.mdlBMdt.fileId = "";
      this.mdlBMdt.fileUrl = "";
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },
    userChange(value,option) {
      this.form.getFieldDecorator('userAccountNameLeader');
      this.form.setFieldsValue({userAccountNameLeader: option.key.userAccountName});
       this.form.getFieldDecorator('userAccountLeader');
      this.form.setFieldsValue({userAccountLeader: option.key.userAccount});
       this.form.getFieldDecorator('telLeader');
      this.form.setFieldsValue({telLeader: option.key.tel});
    },
     userChange2(value,option) {
      
      this.form.getFieldDecorator('userAccountNameAssist');
      this.form.setFieldsValue({userAccountNameAssist: option.key.userAccountName});
       this.form.getFieldDecorator('userAccountAssist');
      this.form.setFieldsValue({userAccountAssist: option.key.userAccount});
       this.form.getFieldDecorator('telAssist');
      this.form.setFieldsValue({telAssist: option.key.tel});
    },
    userChange3(value,option) {
      this.form.getFieldDecorator('userAccountNameLeader2');
      this.form.setFieldsValue({userAccountNameLeader2: option.key.userAccountName});
       this.form.getFieldDecorator('userAccountLeader2');
      this.form.setFieldsValue({userAccountLeader2: option.key.userAccount});
         this.form.getFieldDecorator('telLeader2');
      this.form.setFieldsValue({telLeader2: option.key.tel});
    },
     userChange4(value,option) {
      
      this.form.getFieldDecorator('userAccountNameAssist2');
      this.form.setFieldsValue({userAccountNameAssist2: option.key.userAccountName});
       this.form.getFieldDecorator('userAccountAssist2');
      this.form.setFieldsValue({userAccountAssist2: option.key.userAccount});
        this.form.getFieldDecorator('telAssist2');
      this.form.setFieldsValue({telAssist2: option.key.tel});
    },
    setFormValues({ ...mdlBMdt }) {
      let fields = [
        "teamName",
        "deptHead",
        "isMenzhen",
        "userAccountNameLeader",
        "userAccountLeader",
      //  "telAssist2",
        "telAssist",
        "telLeader",
        "telLeader2",
          "emailLeader",
        "emailAssist",
         "emailLeader2",
       // "emailAssist2",
        "userAccountNameAssist",
        "userAccountAssist",
        "userAccountNameLeader2",
        "userAccountLeader2",
      //  "userAccountNameAssist2",
      //  "userAccountAssist2",
         "startDate", "endDate","note"
     
      ];
      let fieldDates = ["startDate", "endDate"];
      Object.keys(mdlBMdt).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key);
          let obj = {};
          if (fieldDates.indexOf(key) !== -1) {
            if (mdlBMdt[key] !== null && mdlBMdt[key] !== "") {
              obj[key] = moment(mdlBMdt[key]);
              this.form.setFieldsValue(obj);
            } else {
              obj[key] = "";
            }
          } else {
            obj[key] = mdlBMdt[key];
            this.form.setFieldsValue(obj);
          }
        }
      });
      this.mdlBMdt.fileId = mdlBMdt.fileId;
      this.mdlBMdt.fileUrl = mdlBMdt.fileUrl;
      this.mdlBMdt.id = mdlBMdt.id;
      let that= this
      setTimeout(()=>{
        that.$refs.fileagent.setForm(mdlBMdt.fileId);
      },200)
      setTimeout(()=>{
         that.$refs.user1.setFiledValue(mdlBMdt.userAccountLeader)
         that.$refs.user2.setFiledValue(mdlBMdt.userAccountAssist)
         that.$refs.user12.setFiledValue(mdlBMdt.userAccountLeader2)
      //   that.$refs.user22.setFiledValue(mdlBMdt.userAccountAssist2)
         that.$refs.userHX.fetch()
         that.$refs.userQT.fetch()
         that.$refs.file.fetch()
      },500)
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          let mdlBMdt = this.form.getFieldsValue();
          mdlBMdt.id = this.mdlBMdt.id;
           mdlBMdt.fileId = this.mdlBMdt.fileId;
          mdlBMdt.fileUrl = this.mdlBMdt.fileUrl;
          let jsond1= this.$refs.userHX.getAll()
          let jsond2= this.$refs.userQT.getAll()

          this.$put("mdlBMdt", {
            ...mdlBMdt,
            hx: jsond1,
            qt: jsond2,
            
          })
            .then(() => {
              this.reset();
              this.$emit("success");
            })
            .catch(() => {
              this.loading = false;
            });
        }
      });
    },
  },
};
</script>
