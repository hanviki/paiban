<template>
  <a-drawer
    title="修改"
    :maskClosable="false"
    width="650"
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="editVisiable"
    style="height: calc(100% - 55px); overflow: auto; padding-bottom: 53px"
  >
    <a-form :form="form">
      <a-form-item v-bind="formItemLayout" label="资质名称">
        <a-input
          placeholder="请输入资质名称"
          v-decorator="[
            'muduleName',
            { rules: [{ required: true, message: '资质名称不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="父ID">
        <a-input
          placeholder="请输入父ID"
          v-decorator="[
            'parentId',
            { rules: [{ required: true, message: '父ID不能为空' }] },
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
import moment from "moment";

const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 },
};
export default {
  name: "SdlDZizhiEdit",
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
      sdlDZizhi: {},
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
    setFormValues({ ...sdlDZizhi }) {
      let fields = ["muduleName", "parentId"];
      let fieldDates = [];
      Object.keys(sdlDZizhi).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key);
          let obj = {};
          if (fieldDates.indexOf(key) !== -1) {
            if (sdlDZizhi[key] !== "") {
              obj[key] = moment(sdlDZizhi[key]);
            } else {
              obj[key] = "";
            }
          } else {
            obj[key] = sdlDZizhi[key];
          }
          this.form.setFieldsValue(obj);
        }
      });
      this.sdlDZizhi.id = sdlDZizhi.id;
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          let sdlDZizhi = this.form.getFieldsValue();
          sdlDZizhi.id = this.sdlDZizhi.id;
          this.$put("sdlDZizhi", {
            ...sdlDZizhi,
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
