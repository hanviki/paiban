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
import moment from "moment";
import UploadSingleFile from "../../common/uploadSingleFile"

const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 },
};
export default {
  name: "xxbBArchiveEdit",
  props: {
    editVisiable: {
      default: false,
    },
  },
  components: {UploadSingleFile},
  data() {
    return {
      loading: false,
      formItemLayout,
      form: this.$form.createForm(this),
      xxbBArchive: {},
    };
  },
  methods: {
    reset() {
      this.loading = false;
      this.$refs.fileagent.reset();
      this.form.resetFields();
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },
     uploadAgent_1(fileId, fileUrl) {
      this.xxbBArchive.fileId = fileId;
      this.xxbBArchive.fileUrl = fileUrl;
    },
    removeAgent_1() {
      this.xxbBArchive.fileId = "";
      this.xxbBArchive.fileUrl = "";
    },
    setFormValues({ ...xxbBArchive }) {
      let fields = [
         "fileType",
        "fileName",
        "fileCode",
        "fileDate",
        "note",
      ];
      let fieldDates = ["fileDate"];
      Object.keys(xxbBArchive).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key);
          let obj = {};
          if (fieldDates.indexOf(key) !== -1) {
            if (xxbBArchive[key] !== "") {
              obj[key] = moment(xxbBArchive[key]);
            } else {
              obj[key] = "";
            }
          } else {
            obj[key] = xxbBArchive[key];
          }
          this.form.setFieldsValue(obj);
        }
      });
        this.xxbBArchive.fileId = xxbBArchive.fileId;
      this.xxbBArchive.fileUrl = xxbBArchive.fileUrl;
      this.xxbBArchive.id = xxbBArchive.id;
      let that=this
      setTimeout(()=>{
        that.$refs.fileagent.setForm(xxbBArchive.fileId);
      },100)
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          let xxbBArchive = this.form.getFieldsValue();
          xxbBArchive.id = this.xxbBArchive.id;
          xxbBArchive.fileId = this.xxbBArchive.fileId;
          xxbBArchive.fileUrl = this.xxbBArchive.fileUrl;
          this.$put("xxbBArchive", {
            ...xxbBArchive,
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
