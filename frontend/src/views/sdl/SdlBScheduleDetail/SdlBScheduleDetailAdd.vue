<template>
  <a-drawer
    title="新增"
    :maskClosable="false"
    width="650"
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="addVisiable"
    style="height: calc(100% - 55px); overflow: auto; padding-bottom: 53px"
  >
    <a-form :form="form">
      <a-form-item v-bind="formItemLayout" label="主表id">
        <a-input
          placeholder="请输入主表id"
          v-decorator="[
            'baseId',
            { rules: [{ required: true, message: '主表id不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="科室">
        <a-input
          placeholder="请输入科室"
          v-decorator="[
            'deptName',
            { rules: [{ required: true, message: '科室不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="院区">
        <a-input
          placeholder="请输入院区"
          v-decorator="[
            'yqName',
            { rules: [{ required: true, message: '院区不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="病区">
        <a-input
          placeholder="请输入病区"
          v-decorator="[
            'bqName',
            { rules: [{ required: true, message: '病区不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="科室ID">
        <a-input
          placeholder="请输入科室ID"
          v-decorator="[
            'deptId',
            { rules: [{ required: true, message: '科室ID不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="院区ID">
        <a-input
          placeholder="请输入院区ID"
          v-decorator="[
            'yqId',
            { rules: [{ required: true, message: '院区ID不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="病区ID">
        <a-input
          placeholder="请输入病区ID"
          v-decorator="[
            'bqId',
            { rules: [{ required: true, message: '病区ID不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="排班日期">
        <a-date-picker
          v-decorator="[
            'scheduleDate',
            { rules: [{ required: true, message: '排班日期不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="班次id">
        <a-input
          placeholder="请输入班次id"
          v-decorator="[
            'banciId',
            { rules: [{ required: true, message: '班次id不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="班次">
        <a-input
          placeholder="请输入班次"
          v-decorator="[
            'banci',
            { rules: [{ required: true, message: '班次不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="人员类型id 0是一值班">
        <a-input
          placeholder="请输入人员类型id 0是一值班"
          v-decorator="[
            'rylxId',
            {
              rules: [
                { required: true, message: '人员类型id 0是一值班不能为空' },
              ],
            },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="人员类型">
        <a-input
          placeholder="请输入人员类型"
          v-decorator="[
            'rylxName',
            { rules: [{ required: true, message: '人员类型不能为空' }] },
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
const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 },
};
export default {
  name: "SdlBScheduleDetailAdd",
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
      sdlBScheduleDetail: {},
    };
  },
  methods: {
    reset() {
      this.loading = false;
      this.sdlBScheduleDetail = {};
      this.form.resetFields();
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          this.setFields();
          this.$post("sdlBScheduleDetail", {
            ...this.sdlBScheduleDetail,
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
        "baseId",
        "deptName",
        "yqName",
        "bqName",
        "deptId",
        "yqId",
        "bqId",
        "scheduleDate",
        "banciId",
        "banci",
        "rylxId",
        "rylxName",
      ]);
      if (typeof values !== "undefined") {
        Object.keys(values).forEach((_key) => {
          this.sdlBScheduleDetail[_key] = values[_key];
        });
      }
    },
  },
};
</script>
