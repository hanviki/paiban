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
      <a-form-item v-bind="formItemLayout" label="主表id">
        <a-input
          placeholder="请输入主表id"
          v-decorator="[
            'baseId',
            { rules: [{ required: true, message: '主表id不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="开展例数">
        <a-input
          placeholder="请输入开展例数"
          v-decorator="[
            'kzls',
            { rules: [{ required: true, message: '开展例数不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="文件ID">
        <a-input
          placeholder="请输入文件ID"
          v-decorator="[
            'fileId',
            { rules: [{ required: true, message: '文件ID不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="文件URL">
        <a-input
          placeholder="请输入文件URL"
          v-decorator="[
            'fileUrl',
            { rules: [{ required: true, message: '文件URL不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="主要适应症">
        <a-input
          placeholder="请输入主要适应症"
          v-decorator="[
            'zysyz',
            { rules: [{ required: true, message: '主要适应症不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="是否严格掌握适应症">
        <a-input
          placeholder="请输入是否严格掌握适应症"
          v-decorator="[
            'isYgzwsyz',
            {
              rules: [
                { required: true, message: '是否严格掌握适应症不能为空' },
              ],
            },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="主要禁忌症">
        <a-input
          placeholder="请输入主要禁忌症"
          v-decorator="[
            'zyjjz',
            { rules: [{ required: true, message: '主要禁忌症不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="是否严格掌握禁忌症">
        <a-input
          placeholder="请输入是否严格掌握禁忌症"
          v-decorator="[
            'isYgzwjjz',
            {
              rules: [
                { required: true, message: '是否严格掌握禁忌症不能为空' },
              ],
            },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="治愈几例">
        <a-input
          placeholder="请输入治愈几例"
          v-decorator="[
            'zy',
            { rules: [{ required: true, message: '治愈几例不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="好转几例">
        <a-input
          placeholder="请输入好转几例"
          v-decorator="[
            'hz',
            { rules: [{ required: true, message: '好转几例不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="治愈好转率">
        <a-input
          placeholder="请输入治愈好转率"
          v-decorator="[
            'zyPercent',
            { rules: [{ required: true, message: '治愈好转率不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="经济效益与社会效益">
        <a-input
          placeholder="请输入经济效益与社会效益"
          v-decorator="[
            'economic',
            {
              rules: [
                { required: true, message: '经济效益与社会效益不能为空' },
              ],
            },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="不良反应或并发症">
        <a-input
          placeholder="请输入不良反应或并发症"
          v-decorator="[
            'isBadresult',
            {
              rules: [{ required: true, message: '不良反应或并发症不能为空' }],
            },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="不良反应或并发症几例">
        <a-input
          placeholder="请输入不良反应或并发症几例"
          v-decorator="[
            'bad',
            {
              rules: [
                { required: true, message: '不良反应或并发症几例不能为空' },
              ],
            },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="不良反应或并发症">
        <a-input
          placeholder="请输入不良反应或并发症"
          v-decorator="[
            'badresult',
            {
              rules: [{ required: true, message: '不良反应或并发症不能为空' }],
            },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="拟采取的改进措施">
        <a-input
          placeholder="请输入拟采取的改进措施"
          v-decorator="[
            'cs',
            {
              rules: [{ required: true, message: '拟采取的改进措施不能为空' }],
            },
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
  name: "XxbBZqAdd",
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
      xxbBZq: {},
    };
  },
  methods: {
    reset() {
      this.loading = false;
      this.xxbBZq = {};
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
          this.$post("xxbBZq", {
            ...this.xxbBZq,
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
        "kzls",
        "fileId",
        "fileUrl",
        "zysyz",
        "isYgzwsyz",
        "zyjjz",
        "isYgzwjjz",
        "zy",
        "hz",
        "zyPercent",
        "economic",
        "isBadresult",
        "bad",
        "badresult",
        "cs",
      ]);
      if (typeof values !== "undefined") {
        Object.keys(values).forEach((_key) => {
          this.xxbBZq[_key] = values[_key];
        });
      }
    },
  },
};
</script>
