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
        <a-form-item v-bind="formItemLayout" label="专业资质名称">
          <a-input
            placeholder="请输入专业资质名称"
            v-decorator="[
              'qlName',
              { rules: [{ required: true, message: '专业资质名称不能为空' }] },
            ]"
          />
        </a-form-item>
      </a-col>
      <a-col :sm="6">
        <a-form-item v-bind="formItemLayout" label="附件ID">
          <a-input
            placeholder="请输入附件ID"
            v-decorator="[
              'fileId',
              { rules: [{ required: true, message: '附件ID不能为空' }] },
            ]"
          />
        </a-form-item>
      </a-col>
      <a-col :sm="6">
        <a-form-item v-bind="formItemLayout" label="附件地址">
          <a-input
            placeholder="请输入附件地址"
            v-decorator="[
              'fileUrl',
              { rules: [{ required: true, message: '附件地址不能为空' }] },
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
  labelCol: { span: 3 },
  wrapperCol: { span: 18 },
};
export default {
  name: "MdlBSpecialEdit",
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
      mdlBSpecial: {},
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
    setFormValues({ ...mdlBSpecial }) {
      let fields = ["qlName", "fileId", "fileUrl"];
      let fieldDates = [];
      Object.keys(mdlBSpecial).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key);
          let obj = {};
          if (fieldDates.indexOf(key) !== -1) {
            if (mdlBSpecial[key] !== null && mdlBSpecial[key] !== "") {
              obj[key] = moment(mdlBSpecial[key]);
              this.form.setFieldsValue(obj);
            } else {
              obj[key] = "";
            }
          } else {
            obj[key] = mdlBSpecial[key];
            this.form.setFieldsValue(obj);
          }
        }
      });
      this.mdlBSpecial.id = mdlBSpecial.id;
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          let mdlBSpecial = this.form.getFieldsValue();
          mdlBSpecial.id = this.mdlBSpecial.id;
          this.$put("mdlBSpecial", {
            ...mdlBSpecial,
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
