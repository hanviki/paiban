<template>
  <a-drawer
    title="修改"
    :maskClosable="false"
    width="650"
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="editVisiable"
    style="height: calc(100% - 55px); overflow: auto; padding-bottom: 53px"
  >
    <a-form :form="form">
      <a-form-item v-bind="formItemLayout" label="系列id">
        <a-input
          placeholder="请输入系列id"
          v-decorator="[
            'banciId',
            { rules: [{ required: true, message: '系列id不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="部门id">
        <a-input
          placeholder="请输入部门id"
          v-decorator="[
            'deptId',
            { rules: [{ required: true, message: '部门id不能为空' }] },
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
  name: "SdlDeptBanciEdit",
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
      sdlDeptBanci: {},
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
    setFormValues({ ...sdlDeptBanci }) {
      let fields = ["banciId", "deptId"];
      let fieldDates = [];
      Object.keys(sdlDeptBanci).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key);
          let obj = {};
          if (fieldDates.indexOf(key) !== -1) {
            if (sdlDeptBanci[key] !== "") {
              obj[key] = moment(sdlDeptBanci[key]);
            } else {
              obj[key] = "";
            }
          } else {
            obj[key] = sdlDeptBanci[key];
          }
          this.form.setFieldsValue(obj);
        }
      });
      this.sdlDeptBanci.id = sdlDeptBanci.id;
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          let sdlDeptBanci = this.form.getFieldsValue();
          sdlDeptBanci.id = this.sdlDeptBanci.id;
          this.$put("sdlDeptBanci", {
            ...sdlDeptBanci,
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
