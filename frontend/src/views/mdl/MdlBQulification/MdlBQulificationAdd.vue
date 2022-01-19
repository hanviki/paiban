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
      
         <a-form-item v-bind="formItemLayout" label="证件名称">
            <a-select style="width: 100%"  v-decorator="['qlName',{ rules: [{ required: true, message: '证件名称不能为空' }] },]">
              <a-select-option value="医疗机构执业许可"> 医疗机构执业许可 </a-select-option>
              <a-select-option value="母婴保健技术执业许可"> 母婴保健技术执业许可 </a-select-option>
               <a-select-option value="人类辅助技术执业许可"> 人类辅助技术执业许可 </a-select-option>
                <a-select-option value="放射诊疗许可"> 放射诊疗许可 </a-select-option>
            </a-select>
          </a-form-item>
    
      <a-form-item v-bind="formItemLayout" label="发证日期">
        <a-date-picker
          v-decorator="[
            'fzDate',
            { rules: [{ required: true, message: '发证日期不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="变更日期">
        <a-date-picker
          v-decorator="[
            'bgDate',
           
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="变更事项">
        <a-textarea
          placeholder="请输入变更事项"
          v-decorator="[
            'bgItem',
           
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="附件">
        
        <upload-single-file
         ref="fileagent"
              @uploadRemove="removeAgent"
              @uploadSuc="uploadAgent">
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
import UploadSingleFile from '../../common/uploadSingleFile.vue' 
const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 },
};
export default {
  name: "MdlBQulificationAdd",
  props: {
    addVisiable: {
      default: false,
    },
  },
  components: {UploadSingleFile},
  data() {
    return {
      loading: false,
      formItemLayout,
      form: this.$form.createForm(this),
      mdlBQulification: {},
    };
  },
  methods: {
    reset() {
      this.loading = false;
      this.mdlBQulification = {};
      this.$refs.fileagent.reset();
      this.form.resetFields();
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },
    uploadAgent (fileId,fileUrl) {
      this.mdlBQulification.fileId = fileId
      this.mdlBQulification.fileUrl = fileUrl
    },
    removeAgent () {
      this.mdlBQulification.fileId = ''
      this.mdlBQulification.fileUrl = ''
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          this.setFields();
          this.$post("mdlBQulification", {
            ...this.mdlBQulification,
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
        "fzDate",
        "bgDate",
        "bgItem",
      ]);
      if (typeof values !== "undefined") {
        Object.keys(values).forEach((_key) => {
          this.mdlBQulification[_key] = values[_key];
        });
      }
    },
  },
};
</script>
