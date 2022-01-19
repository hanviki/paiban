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
        <a-form-item v-bind="formItemLayout" label="姓名">
       <a-input
          disabled
          v-decorator="[
            'userAccountName',
          ]"
        />
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
import moment from "moment";

const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 },
};
export default {
  name: "MdlBManagerEdit",
  props: {
    editVisiable: {
      default: false,
    },
     type: {
      default: ''
    }
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
    editVisiable() {
      if (this.editVisiable) {
        this.fetchDept();
      }
    },
  },
  methods: {
    reset() {
      this.loading = false;
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
     // this.mdlBManager["userAccountName"] = option.key.userAccountName;
     //  this.mdlBManager["birthday"] = option.key.birthday;
      
      this.form.getFieldDecorator('birthday');
      this.form.setFieldsValue({birthday: option.key.birthday});
       this.form.getFieldDecorator('userAccountName');
        this.form.setFieldsValue({userAccountName: option.key.userAccountName});
    },
    deptChange(value, option) {
      // let data = this.deptData.filter((p) => p.deptId == value);
      this.mdlBManager["deptName"] = option.key;
    },
    setFormValues({ ...mdlBManager }) {
      let fields = ["deptId", "userAccountName","userAccount", "birthday","startDate","endDate"];
      let fieldDates = ["startDate","endDate"];
      Object.keys(mdlBManager).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key);
          let obj = {};
          if (fieldDates.indexOf(key) !== -1) {
            if (mdlBManager[key]!==null&& mdlBManager[key] !== "") {
              obj[key] = moment(mdlBManager[key]);
               this.form.setFieldsValue(obj);
            } else {
              obj[key] = "";
            }
          } else {
            obj[key] = mdlBManager[key];

             this.form.setFieldsValue(obj);
          }
         
        }
      });
       this.mdlBManager.deptName = mdlBManager.deptName;
      this.mdlBManager.type = mdlBManager.type;
      this.mdlBManager.id = mdlBManager.id;
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          let mdlBManager = this.form.getFieldsValue();
          mdlBManager.id = this.mdlBManager.id;
          mdlBManager.type = this.mdlBManager.type;
           mdlBManager.deptName = this.mdlBManager.deptName;
          this.$put("mdlBManager", {
            ...mdlBManager,
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
