<template>
  <a-drawer
    title="新增"
    :maskClosable="false"
    width="80%"
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="addVisiable"
    style="height: calc(100% - 55px); overflow: auto; padding-bottom: 53px"
  >
    <a-form :form="form">
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
        <a-form-item v-bind="formItemLayout" label="团队负责人">
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
        <a-form-item v-bind="formItemLayout" label="团队负责人2邮箱">
          <a-input
            placeholder="请输入团队负责人2邮箱"
            v-decorator="[
              'emailLeader2',
            ]"
          />
        </a-form-item>
      </a-col>
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
      <a-col :sm="12">
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
      </a-col>
      <a-col :sm="12">
        <a-form-item v-bind="formItemLayout" label="开始时间">
          <a-date-picker
            placeholder="请选择开始时间"
            :dateFormat="dateFormat"
            v-decorator="['startDate']"
          >
          </a-date-picker>
        </a-form-item>
      </a-col>
      <a-col :sm="12">
        <a-form-item v-bind="formItemLayout" label="结束时间">
          <a-date-picker
            placeholder="请选择结束时间"
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
      </a-row>
    </a-form>
    <a-card title="核心成员" headStyle="font-weight:bold">
      <add-user ref="userHX" baseId="" :type="0"></add-user>
    </a-card>
    <a-card title="其他成员" headStyle="font-weight:bold">
      <add-user ref="userQT" baseId="" :type="1"></add-user>
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
import SelectUserRemote from "../../common/SelectUserRemote.vue";
import AddUser from "./AddUser.vue";
const formItemLayout = {
  labelCol: { span: 6 },
  wrapperCol: { span: 18 },
};
export default {
  components: { SelectUserRemote, AddUser },
  name: "MdlBMdtAdd",
  props: {
    addVisiable: {
      default: false,
    },
  },
  data() {
    return {
      loading: false,
      dateFormat: "YYYY-MM-DD",
      formItemLayout,
      form: this.$form.createForm(this),
      mdlBMdt: {},
    };
  },
  methods: {
    reset() {
      this.loading = false;
      this.mdlBMdt = {};
      this.$refs.user1.reset();
      this.$refs.user2.reset();
      this.$refs.user12.reset();
      this.$refs.user22.reset();
      this.$refs.userHX.reset();
      this.$refs.userQT.reset();
      this.form.resetFields();
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          this.setFields();
          let jsond1 = this.$refs.userHX.getAll();
          let jsond2 = this.$refs.userQT.getAll();

          this.$post("mdlBMdt", {
            ...this.mdlBMdt,
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
    userChange(value, option) {
      console.info(22222);
      //  let obj = this.userData.filter((p) => p.userAccount === value );
      this.mdlBMdt["userAccountNameLeader"] = option.key.userAccountName;
      this.form.getFieldDecorator("userAccountLeader");
      this.form.setFieldsValue({ userAccountLeader: option.key.userAccount });
      this.form.getFieldDecorator("telLeader");
      this.form.setFieldsValue({ telLeader: option.key.tel });
      //   this.mdlBMdt["userAccountLeader"] = option.key.userAccount;
    },
    userChange3(value, option) {
      console.info(22222);
      //  let obj = this.userData.filter((p) => p.userAccount === value );
      this.mdlBMdt["userAccountNameLeader2"] = option.key.userAccountName;
      this.form.getFieldDecorator("userAccountLeader2");
      this.form.setFieldsValue({ userAccountLeader2: option.key.userAccount });
        this.form.getFieldDecorator("telLeader2");
      this.form.setFieldsValue({ telLeader2: option.key.tel });
      //   this.mdlBMdt["userAccountLeader"] = option.key.userAccount;
    },
    userChange2(value, option) {
      console.info(option);
      //  let obj = this.userData.filter((p) => p.userAccount === value );
      this.mdlBMdt["userAccountNameAssist"] = option.key.userAccountName;
      this.form.getFieldDecorator("userAccountAssist");
      this.form.setFieldsValue({ userAccountAssist: option.key.userAccount });
        this.form.getFieldDecorator("telAssist");
      this.form.setFieldsValue({ telAssist: option.key.tel });
      // this.mdlBMdt["userAccountAssist"] = option.key.userAccount;
    },
    userChange4(value, option) {
      console.info(option);
      //  let obj = this.userData.filter((p) => p.userAccount === value );
      this.mdlBMdt["userAccountNameAssist2"] = option.key.userAccountName;
      this.form.getFieldDecorator("userAccountAssist2");
      this.form.setFieldsValue({ userAccountAssist2: option.key.userAccount });
        this.form.getFieldDecorator("telAssist2");
      this.form.setFieldsValue({ telAssist2: option.key.tel });
      // this.mdlBMdt["userAccountAssist"] = option.key.userAccount;
    },
    setFields() {
      let values = this.form.getFieldsValue([
        "teamName",
        "deptHead",
        "isMenzhen",
        "userAccountLeader",
        "telLeader",
        "emailLeader",
        "userAccountAssist",
        "telLeader2",
        "emailLeader2",
        "telAssist",
        "emailAssist",
        "telAssist2",
        "emailAssist2",
        "userAccountLeader2",
        "userAccountAssist2",
        "startDate",
        "endDate",
        "note"
      ]);
      if (typeof values !== "undefined") {
        Object.keys(values).forEach((_key) => {
          this.mdlBMdt[_key] = values[_key];
        });
      }
    },
  },
};
</script>
