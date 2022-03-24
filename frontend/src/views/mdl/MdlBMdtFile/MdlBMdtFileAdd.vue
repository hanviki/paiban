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
      <a-form-item v-bind="formItemLayout" label="年度">
        <a-input
          placeholder="请输入年度"
          v-decorator="[
            'year',
            { rules: [{ required: true, message: '年度不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="备注">
        <a-textarea
          placeholder="请输入备注"
          v-decorator="[
            'note',
            { rules: [{ required: true, message: '备注不能为空' }] },
          ]"
        />
      </a-form-item>
     <a-form-item v-bind="formItemLayout" label="工作总结">
        <upload-single-file
            ref="fileagent"
            @uploadRemove="removeAgent_1"
            @uploadSuc="uploadAgent_1"
          >
          </upload-single-file>
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="工作量统计">
        <upload-single-file
            ref="fileagent2"
            @uploadRemove="removeAgent_2"
            @uploadSuc="uploadAgent_2"
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
import UploadSingleFile from "../../common/uploadSingleFile"
const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 },
};
export default {
  name: "MdlBMdtFileAdd",
  props: {
    addVisiable: {
      default: false,
    },
    baseId: {
      default: ''
    }
  },
  data() {
    return {
      loading: false,
      formItemLayout,
      form: this.$form.createForm(this),
      mdlBMdtFile: {
        baseId: this.baseId
      },
    };
  },
  components: {UploadSingleFile},
  methods: {
    reset() {
      this.loading = false;
      this.mdlBMdtFile = {};
      this.$refs.fileagent.reset();
      this.$refs.fileagent2.reset();
      this.form.resetFields();
    },
     uploadAgent_1(fileId, fileUrl) {
      this.mdlBMdtFile.fileId = fileId;
      this.mdlBMdtFile.fileUrl = fileUrl;
    },
    removeAgent_1() {
      this.mdlBMdtFile.fileId = "";
      this.mdlBMdtFile.fileUrl = "";
    },
     uploadAgent_2(fileId, fileUrl) {
      this.mdlBMdtFile.zcFileId = fileId;
      this.mdlBMdtFile.zcFileUrl = fileUrl;
    },
    removeAgent_2() {
      this.mdlBMdtFile.zcFileId = "";
      this.mdlBMdtFile.zcFileUrl = "";
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          this.setFields();
          this.$post("mdlBMdtFile", {
            ...this.mdlBMdtFile,
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
        "year",
        "note",
      ]);
      if (typeof values !== "undefined") {
        Object.keys(values).forEach((_key) => {
          this.mdlBMdtFile[_key] = values[_key];
        });
      }
    },
  },
};
</script>
