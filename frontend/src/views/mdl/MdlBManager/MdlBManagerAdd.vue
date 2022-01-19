<template>
  <a-drawer
    :title="`新增${type}`"
    :maskClosable="false"
    width="80%"
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="addVisiable"
    style="height: calc(100% - 55px); overflow: auto; padding-bottom: 53px"
  >
    <a-form :form="form">
      <a-form-item v-bind="formItemLayout" label="科室">
        <a-select
          v-decorator="[
            'deptId',
            { rules: [{ required: true, message: '科室不能为空' }] },
          ]"
          option-filter-prop="children"
          :filter-option="filterOption"
          show-search
          @change="deptChange"
        >
          <a-select-option
            v-for="d in deptData"
            :key="d.deptName"
            :value="`${d.deptId}`"
          >
            {{ d.deptName }}
          </a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="人员">
        <a-select
          v-decorator="[
            'userAccount',
            { rules: [{ required: true, message: '人员必填' }] },
          ]"
          @search="handleSearch"
          :filter-option="false"
          show-search
          @change="userChange"
        >
          <a-select-option
            v-for="d2 in userData"
            :key="d2"
            :value="`${d2.userAccount}`"
          >
            {{ d2.userAccountName + " " + d2.userAccount + " " + d2.birthday }}
          </a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="出生年月">
        <a-input
          placeholder="请输入出生年月"
          disabled
          v-decorator="[
            'birthday',
          ]"
        />
      </a-form-item>
       <a-form-item v-bind="formItemLayout" label="开始日期">
        <a-date-picker
          v-decorator="[
            'startDate',
          ]"
        />
      </a-form-item>
       <a-form-item v-bind="formItemLayout" label="结束日期">
        <a-date-picker
          v-decorator="[
            'endDate',
          ]"
        />
      </a-form-item>
    </a-form>
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
const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 },
};
export default {
  name: "MdlBManagerAdd",
  props: {
    addVisiable: {
      default: false,
    },
    type: {
      default: "",
    },
  },
  data() {
    return {
      loading: false,
      formItemLayout,
      form: this.$form.createForm(this),
      mdlBManager: {
        type: this.type,
      },
      deptData: [],
      userData: [],
    };
  },
  watch: {
    addVisiable() {
      if (this.addVisiable) {
        this.fetchDept();
        this.mdlBManager.type = this.type
      }
    },
  },
  methods: {
    reset() {
      this.loading = false;
      this.mdlBManager = {};
      this.form.resetFields();
    },
    handleSearch(value) {
      this.fetchUser(value);
    },
    fetchUser(userAccount) {
      this.$get("sdlBUser/search", {
        userAccount,
      }).then((r) => {
        console.log(r)
        this.userData = r.data;
      });
    },
    fetchDept() {
      this.$get("dept/list", { parentId: "0" }).then((res) => {
        this.deptData = [];
        this.deptData.push(...res.data);
      });
    },
    filterOption(input, option) {
      return (
        option.componentOptions.children[0].text
          .toLowerCase()
          .indexOf(input.toLowerCase()) >= 0
      );
    },
    userChange(value,option) {
      console.info(option)
    //  let obj = this.userData.filter((p) => p.userAccount === value );
      this.mdlBManager["userAccountName"] = option.key.userAccountName;
       this.mdlBManager["userAccount"] = option.key.userAccount;
       this.mdlBManager["birthday"] = option.key.birthday;
      
      this.form.getFieldDecorator('birthday');
      this.form.setFieldsValue({birthday: option.key.birthday});
    },
    deptChange(value, option) {
      // let data = this.deptData.filter((p) => p.deptId == value);
      this.mdlBManager["deptName"] = option.key;
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          this.setFields();
          this.$post("mdlBManager", {
            ...this.mdlBManager,
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
    setFields() {
      let values = this.form.getFieldsValue([
        //"userAccount",
        "deptId",
        "birthday",
        "startDate",
        "endDate"
      ]);
      if (typeof values !== "undefined") {
        Object.keys(values).forEach((_key) => {
          this.mdlBManager[_key] = values[_key];
        });
      }
    },
  },
};
</script>
