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
      <a-form-item v-bind="formItemLayout" label="任务来源">
        <a-input
          placeholder="请输入任务来源"
          v-decorator="[
            'taskSource',
            { rules: [{ required: true, message: '任务来源不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="外派类型">
        <a-input
          placeholder="请输入外派类型"
          v-decorator="[
            'taskType',
            { rules: [{ required: true, message: '外派类型不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="外派事项">
        <a-input
          placeholder="请输入外派事项"
          v-decorator="[
            'taskItem',
            { rules: [{ required: true, message: '外派事项不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="外派开始时间">
        <a-date-picker
          v-decorator="[
            'startDate',
            { rules: [{ required: true, message: '外派开始时间不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="外派结束时间">
        <a-date-picker
          v-decorator="[
            'endDate',
            { rules: [{ required: true, message: '外派结束时间不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="省">
        <a-input
          placeholder="请输入省"
          v-decorator="[
            'province',
            { rules: [{ required: true, message: '省不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="市">
        <a-input
          placeholder="请输入市"
          v-decorator="[
            'city',
            { rules: [{ required: true, message: '市不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="区">
        <a-input
          placeholder="请输入区"
          v-decorator="[
            'street',
            { rules: [{ required: true, message: '区不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="地址">
        <a-input
          placeholder="请输入地址"
          v-decorator="[
            'address',
            { rules: [{ required: true, message: '地址不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="链接地址">
        <a-input
          placeholder="请输入链接地址"
          v-decorator="[
            'linkUrl',
            { rules: [{ required: true, message: '链接地址不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="附件ID">
        <a-input
          placeholder="请输入附件ID"
          v-decorator="[
            'fileId',
            { rules: [{ required: true, message: '附件ID不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="附件地址">
        <a-input
          placeholder="请输入附件地址"
          v-decorator="[
            'fileUrl',
            { rules: [{ required: true, message: '附件地址不能为空' }] },
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
  name: "MdlBForeignAdd",
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
      mdlBForeign: {},
    };
  },
  methods: {
    reset() {
      this.loading = false;
      this.mdlBForeign = {};
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
          this.$post("mdlBForeign", {
            ...this.mdlBForeign,
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
        "taskSource",
        "taskType",
        "taskItem",
        "startDate",
        "endDate",
        "province",
        "city",
        "street",
        "address",
        "linkUrl",
        "fileId",
        "fileUrl",
      ]);
      if (typeof values !== "undefined") {
        Object.keys(values).forEach((_key) => {
          this.mdlBForeign[_key] = values[_key];
        });
      }
    },
  },
};
</script>
