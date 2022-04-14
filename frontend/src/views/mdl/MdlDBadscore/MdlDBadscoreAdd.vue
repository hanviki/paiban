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
      <a-form-item v-bind="formItemLayout" label="序号">
        <a-input
          placeholder="请输入序号"
          v-decorator="[
            'code',
            { rules: [{ required: true, message: '序号不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="不良执业行为">
        <a-input
          placeholder="请输入不良执业行为"
          v-decorator="[
            'name',
            { rules: [{ required: true, message: '不良执业行为不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="扣分">
        <a-input
          placeholder="请输入扣分"
          v-decorator="[
            'score',
            { rules: [{ required: true, message: '扣分不能为空' }] },
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
  name: "MdlDBadscoreAdd",
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
      mdlDBadscore: {},
    };
  },
  methods: {
    reset() {
      this.loading = false;
      this.mdlDBadscore = {};
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
          this.$post("mdlDBadscore", {
            ...this.mdlDBadscore,
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
      let values = this.form.getFieldsValue(["code", "name", "score"]);
      if (typeof values !== "undefined") {
        Object.keys(values).forEach((_key) => {
          this.mdlDBadscore[_key] = values[_key];
        });
      }
    },
  },
};
</script>
