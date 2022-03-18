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
       <a-form-item v-bind="formItemLayout" label="文件类型">
       <a-select
        v-decorator="[
            'fileType',
            { rules: [{ required: true, message: '文件类型不能为空' }] },
          ]"
       >
       <a-select-option value="麻精药物">
         麻精药物
       </a-select-option>
        <a-select-option value="抗菌药物">
         抗菌药物
       </a-select-option>
       </a-select>
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="文件名称">
        <a-input
          placeholder="请输入文件名称"
          v-decorator="[
            'fileName',
            { rules: [{ required: true, message: '文件名称不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="文件编号">
        <a-input
          placeholder="请输入文件编号"
          v-decorator="[
            'fileCode',
            { rules: [{ required: true, message: '文件编号不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="文件时间">
        <a-date-picker
          v-decorator="[
            'fileDate',
            { rules: [{ required: true, message: '文件时间不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="备注">
        <a-input
          placeholder="请输入备注"
          v-decorator="[
            'note',
            { rules: [{ required: true, message: '备注不能为空' }] },
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
import UploadSingleFile from "../../common/uploadSingleFile"
const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 },
};
export default {
  name: "MdlBArchiveAdd",
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
      mdlBArchive: {},
    };
  },
  components: {UploadSingleFile},
  methods: {
    reset() {
      this.loading = false;
      this.mdlBArchive = {};
      this.$refs.fileagent.reset();
      this.form.resetFields();
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },
     uploadAgent_1(fileId, fileUrl) {
      this.mdlBArchive.fileId = fileId;
      this.mdlBArchive.fileUrl = fileUrl;
    },
    removeAgent_1() {
      this.mdlBArchive.fileId = "";
      this.mdlBArchive.fileUrl = "";
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          this.setFields();
          this.$post("mdlBArchive", {
            ...this.mdlBArchive,
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
        "fileType",
        "fileName",
        "fileCode",
        "fileDate",
        "note",
      ]);
      if (typeof values !== "undefined") {
        Object.keys(values).forEach((_key) => {
          this.mdlBArchive[_key] = values[_key];
        });
      }
    },
  },
};
</script>
