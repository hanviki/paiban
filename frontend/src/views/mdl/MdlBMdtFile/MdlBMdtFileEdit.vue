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
      <a-form-item v-bind="formItemLayout" label="年度">
        <a-input
          placeholder="请输入年度"
          v-decorator="[
            'year',
            { rules: [{ required: true, message: '年度不能为空' }] },
          ]"
        />
      </a-form-item>
       <a-form-item v-bind="formItemLayout" label="考核分数">
        <a-input-number
          placeholder="请输入考核分数"
          v-decorator="[
            'score',
            { rules: [{ required: true, message: '考核分数不能为空' }] },
          ]"
        />
      </a-form-item>
       <a-form-item v-bind="formItemLayout" label="考核结果">
        <a-select
          placeholder="请输入考核结果"
          v-decorator="[
            'result',
            { rules: [{ required: true, message: '考核结果不能为空' }] },
          ]"
        >
       <a-select-option value="优秀">
          优秀
        </a-select-option>
        <a-select-option value="合格">
          合格
        </a-select-option>
        <a-select-option value="不合格">
          不合格
        </a-select-option>
        </a-select>
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
import moment from "moment";
import UploadSingleFile from "../../common/uploadSingleFile"

const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 },
};
export default {
  name: "MdlBMdtFileEdit",
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
      mdlBMdtFile: {},
    };
  },
  methods: {
    reset() {
      this.loading = false;
      this.$refs.fileagent.reset();
      this.$refs.fileagent2.reset();
      this.form.resetFields();
    },
    onClose() {
      this.reset();
      this.$emit("close");
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
    setFormValues({ ...mdlBMdtFile }) {
      let fields = [
        "year",
        "note",
         "result",
         "score"
      ];
      let fieldDates = [];
      Object.keys(mdlBMdtFile).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key);
          let obj = {};
          if (fieldDates.indexOf(key) !== -1) {
            if (mdlBMdtFile[key] !== null && mdlBMdtFile[key] !== "") {
              obj[key] = moment(mdlBMdtFile[key]);
              this.form.setFieldsValue(obj);
            } else {
              obj[key] = "";
            }
          } else {
            obj[key] = mdlBMdtFile[key];
            this.form.setFieldsValue(obj);
          }
        }
      });
      this.mdlBMdtFile.id = mdlBMdtFile.id;
      this.mdlBMdtFile.baseId = mdlBMdtFile.baseId;
      this.mdlBMdtFile.fileId = mdlBMdtFile.fileId;
      this.mdlBMdtFile.fileUrl = mdlBMdtFile.fileUrl;
        this.mdlBMdtFile.zcFileId = mdlBMdtFile.zcFileId;
      this.mdlBMdtFile.zcFileUrl = mdlBMdtFile.zcFileUrl;
      let that=this
      setTimeout(()=>{
        that.$refs.fileagent.setForm(mdlBMdtFile.fileId);
        that.$refs.fileagent2.setForm(mdlBMdtFile.zcFileId);
      },100)
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          let mdlBMdtFile = this.form.getFieldsValue();
          mdlBMdtFile.id = this.mdlBMdtFile.id;
          mdlBMdtFile.baseId = this.mdlBMdtFile.baseId;
          this.$put("mdlBMdtFile", {
            ...mdlBMdtFile,
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
