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
      <a-col :sm="6">
        <a-form-item v-bind="formItemLayout" label="序号">
          <a-input
            placeholder="请输入序号"
            v-decorator="[
              'code',
              { rules: [{ required: true, message: '序号不能为空' }] },
            ]"
          />
        </a-form-item>
      </a-col>
      <a-col :sm="6">
        <a-form-item v-bind="formItemLayout" label="不良执业行为">
          <a-input
            placeholder="请输入不良执业行为"
            v-decorator="[
              'name',
              { rules: [{ required: true, message: '不良执业行为不能为空' }] },
            ]"
          />
        </a-form-item>
      </a-col>
      <a-col :sm="6">
        <a-form-item v-bind="formItemLayout" label="扣分">
          <a-input
            placeholder="请输入扣分"
            v-decorator="[
              'score',
              { rules: [{ required: true, message: '扣分不能为空' }] },
            ]"
          />
        </a-form-item>
      </a-col>
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

const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 },
};
export default {
  name: "MdlDBadscoreEdit",
  props: {
    editVisiable: {
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
      this.form.resetFields();
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },
    setFormValues({ ...mdlDBadscore }) {
      let fields = ["code", "name", "score"];
      let fieldDates = [];
      Object.keys(mdlDBadscore).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key);
          let obj = {};
          if (fieldDates.indexOf(key) !== -1) {
            if (mdlDBadscore[key] !== null && mdlDBadscore[key] !== "") {
              obj[key] = moment(mdlDBadscore[key]);
              this.form.setFieldsValue(obj);
            } else {
              obj[key] = "";
            }
          } else {
            obj[key] = mdlDBadscore[key];
            this.form.setFieldsValue(obj);
          }
        }
      });
      this.mdlDBadscore.id = mdlDBadscore.id;
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          let mdlDBadscore = this.form.getFieldsValue();
          mdlDBadscore.id = this.mdlDBadscore.id;
          this.$put("mdlDBadscore", {
            ...mdlDBadscore,
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
