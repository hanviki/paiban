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
      <a-form-item v-bind="formItemLayout" label="行业资质名称">
        <a-input
          placeholder="请输入行业资质名称"
          v-decorator="[
            'qlName',
            { rules: [{ required: true, message: '行业资质名称不能为空' }] },
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
import moment from "moment";
import UploadSingleFile from "../../common/uploadSingleFile"

const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 },
};
export default {
  name: "MdlBProfessionEdit",
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
      mdlBProfession: {},
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
      this.mdlBProfession.fileId = fileId;
      this.mdlBProfession.fileUrl = fileUrl;
    },
    removeAgent_1() {
      this.mdlBProfession.fileId = "";
      this.mdlBProfession.fileUrl = "";
    },
    setFormValues({ ...mdlBProfession }) {
      let fields = ["qlName", "qlDate", "qlCode", "fileId", "fileUrl"];
      let fieldDates = ["qlDate"];
      Object.keys(mdlBProfession).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key);
          let obj = {};
          if (fieldDates.indexOf(key) !== -1) {
            if (mdlBProfession[key] !== null && mdlBProfession[key] !== "") {
              obj[key] = moment(mdlBProfession[key]);
              this.form.setFieldsValue(obj);
            } else {
              obj[key] = "";
            }
          } else {
            obj[key] = mdlBProfession[key];
            this.form.setFieldsValue(obj);
          }
        }
      });
       this.mdlBProfession.fileId = mdlBProfession.fileId;
      this.mdlBProfession.fileUrl = mdlBProfession.fileUrl;
      this.mdlBProfession.id = mdlBProfession.id;
      let that=this
      setTimeout(()=>{
        that.$refs.fileagent.setForm(mdlBProfession.fileId);
      },100)
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          let mdlBProfession = this.form.getFieldsValue();
          mdlBProfession.id = this.mdlBProfession.id;
          this.$put("mdlBProfession", {
            ...mdlBProfession,
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
