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
     
      <a-form-item v-bind="formItemLayout" label="姓名">
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
            {{ d2.userAccountName + " " + d2.userAccount + " "  +d2.sexname}}
          </a-select-option>
         </a-select>
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="科室">
        <a-input
          :disabled="true"
          placeholder="请输入科室"
          v-decorator="[
            'deptName',
          ]"
        />
      </a-form-item>
       <a-form-item v-bind="formItemLayout" label="职称">
        <a-input
         :disabled="true"
          placeholder="请输入职称"
          v-decorator="[
            'zhicheng',
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="级别">
         <a-select
          placeholder="请输入级别"
          v-decorator="[
            'jb',
            { rules: [{ required: true, message: '级别不能为空' }] },
          ]"
        >
        <a-select-option  value="一级">
            一级
        </a-select-option>
        <a-select-option value="二级">
            二级
        </a-select-option>
        <a-select-option value="三级">
            三级
        </a-select-option>
        <a-select-option value="四级">
            四级
        </a-select-option>
         </a-select>
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="生效日期">
        <a-date-picker
          v-decorator="[
            'validDate',
            { rules: [{ required: true, message: '生效日期不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="备注">
        <a-input
          placeholder="请输入备注"
          v-decorator="[
            'note',
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
  name: "MdlBSurgeryinfoAdd",
  props: {
    addVisiable: {
      default: false,
    },
  },
  data() {
    return {
      loading: false,
      formItemLayout,
      form: this.$form.createForm(this),
      mdlBSurgeryinfo: {},
      userData: [],
    };
  },
  methods: {
    reset() {
      this.loading = false;
      this.mdlBSurgeryinfo = {};
      this.form.resetFields();
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },
     handleSearch(value) {
      this.fetchUser(value);
    },
    fetchUser(userAccount) {
      this.$get("sdlBUserMg/search", {
        userAccount,
      }).then((r) => {
        console.log(r);
        this.userData = r.data;
      });
    },
     userChange(value, option) {
      console.info(option);
      this.mdlBSurgeryinfo["userAccountName"] = option.key.userAccountName;
      this.mdlBSurgeryinfo["userAccount"] = option.key.userAccount;
      
      this.mdlBSurgeryinfo["zhicheng"] = option.key.zhicheng;
      this.mdlBSurgeryinfo["birthday"] = option.key.birthday;
      this.mdlBSurgeryinfo["sexName"] = option.key.sexname;
      this.mdlBSurgeryinfo["deptName"] = option.key.deptNew;

      this.form.getFieldDecorator("zhicheng")
         
          this.form.setFieldsValue({zhicheng: option.key.zhicheng})
          this.form.getFieldDecorator("deptName")
         
          this.form.setFieldsValue({deptName: option.key.deptNew})
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          this.setFields();
          this.$post("mdlBSurgeryinfo", {
            ...this.mdlBSurgeryinfo,
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
        
        "jb",
        "validDate",
        "note",
      ]);
      if (typeof values !== "undefined") {
        Object.keys(values).forEach((_key) => {
          this.mdlBSurgeryinfo[_key] = values[_key];
        });
      }
    },
  },
};
</script>
