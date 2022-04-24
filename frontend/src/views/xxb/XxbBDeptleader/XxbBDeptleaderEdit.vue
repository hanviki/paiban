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
      <a-col :sm="6">
        <a-form-item v-bind="formItemLayout" label="部门名称">
          <a-select
          option-filter-prop="children"
          placeholder="请选择科室名称"
          v-decorator="[
            'deptName',
            { rules: [{ required: true, message: '科室名称不能为空' }] },
          ]"
          show-search
        >
          <a-select-option
            v-for="d in deptData"
            :key="d.deptId"
            :value="`${d.deptId}`"
          >
            {{ d.deptName }}
          </a-select-option>
        </a-select>
        </a-form-item>
      </a-col>
      <a-col :sm="6">
        <a-form-item v-bind="formItemLayout" label="姓名">
          <a-input
            placeholder="请输入姓名"
            v-decorator="[
              'userAccountName',
              { rules: [{ required: true, message: '姓名不能为空' }] },
            ]"
          />
        </a-form-item>
      </a-col>
      <a-col :sm="6">
        <a-form-item v-bind="formItemLayout" label="人事编号">
          <a-input
            placeholder="请输入人事编号"
            v-decorator="[
              'userAccount',
              { rules: [{ required: true, message: '人事编号不能为空' }] },
            ]"
          />
        </a-form-item>
      </a-col>
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
  labelCol: {
    span: 3,
  },
  wrapperCol: {
    span: 18,
  },
};
export default {
  name: "XxbBDeptleaderEdit",
  props: {
    editVisiable: {
      default: false,
    },
  },
  data() {
    return {
      loading: false,
      formItemLayout,
      form: this.$form.createForm(this),
      xxbBDeptleader: {},
    };
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
    setFormValues(xxbBDeptleader, deptData) {
      this.deptData = deptData
      let fields = ["deptName", "userAccountName", "userAccount"];
      let fieldDates = [];
      Object.keys(xxbBDeptleader).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key);
          let obj = {};
          if (fieldDates.indexOf(key) !== -1) {
            if (xxbBDeptleader[key] !== null && xxbBDeptleader[key] !== "") {
              obj[key] = moment(xxbBDeptleader[key]);
              this.form.setFieldsValue(obj);
            } else {
              obj[key] = "";
            }
          } else {
            obj[key] = xxbBDeptleader[key];
            this.form.setFieldsValue(obj);
          }
        }
      });
      this.xxbBDeptleader.id = xxbBDeptleader.id;
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          let xxbBDeptleader = this.form.getFieldsValue();
          xxbBDeptleader.id = this.xxbBDeptleader.id;
          this.$put("xxbBDeptleader", {
            ...xxbBDeptleader,
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
