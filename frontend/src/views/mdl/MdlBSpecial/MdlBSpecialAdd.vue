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
      <a-form-item v-bind="formItemLayout" label="专业资质名称">
        <a-input
          placeholder="请输入专业资质名称"
          v-decorator="[
            'qlName',
            { rules: [{ required: true, message: '专业资质名称不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="获得时间">
        <a-date-picker
          v-decorator="[
            'qlDate',
            { rules: [{ required: true, message: '获得时间不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="证书编号">
        <a-input
          placeholder="请输入证书编号"
          v-decorator="[
            'qlCode',
            { rules: [{ required: true, message: '证书编号不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="附件">
        <upload-single-file
            ref="fileagent"
            @uploadRemove="removeAgent_1"
            @uploadSuc="uploadAgent_1"
          >
          </upload-single-file>
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
import UploadSingleFile from "../../common/uploadSingleFile"
export default {
  name: "mdlBSpecialAdd",
  props: {
    addVisiable: {
      default: false,
    },
  },
  components: { UploadSingleFile },
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
      this.mdlBSpecial = {};
       this.$refs.fileagent.reset();
      this.form.resetFields();
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },
    uploadAgent_1(fileId, fileUrl) {
      this.mdlBSpecial.fileId = fileId;
      this.mdlBSpecial.fileUrl = fileUrl;
    },
    removeAgent_1() {
      this.mdlBSpecial.fileId = "";
      this.mdlBSpecial.fileUrl = "";
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          this.setFields();
          this.$post("mdlBSpecial", {
            ...this.mdlBSpecial,
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
        "qlName",
        "qlDate",
        "qlCode"
      ]);
      if (typeof values !== "undefined") {
        Object.keys(values).forEach((_key) => {
          this.mdlBSpecial[_key] = values[_key];
        });
      }
    },
  },
};
</script>
