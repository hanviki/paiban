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
      <a-form-item v-bind="formItemLayout" label="开始日期">
        <a-date-picker
          v-decorator="[
            'startDate',
            { rules: [{ required: true, message: '开始日期不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="结束日期">
        <a-date-picker
          v-decorator="[
            'endDate',
            { rules: [{ required: true, message: '结束日期不能为空' }] },
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
      <a-form-item v-bind="formItemLayout" label="审核时间">
        <a-date-picker
          v-decorator="[
            'auditDate',
            { rules: [{ required: true, message: '审核时间不能为空' }] },
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
  name: "SdlBScheduleAdd",
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
      sdlBSchedule: {},
    };
  },
  methods: {
    reset() {
      this.loading = false;
      this.sdlBSchedule = {};
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
          this.$post("sdlBSchedule", {
            ...this.sdlBSchedule,
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
        "deptName",
        "yqName",
        "deptId",
        "yqId",
        "startDate",
        "endDate",
        "note",
        "auditDate",
      ]);
      if (typeof values !== "undefined") {
        Object.keys(values).forEach((_key) => {
          this.sdlBSchedule[_key] = values[_key];
        });
      }
    },
  },
};
</script>
