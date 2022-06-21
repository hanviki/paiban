<template>
  <a-drawer
    title="新增"
    :maskClosable="false"
    width="80%"
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="addVisiable"
    style="height: calc(100% - 5px); overflow: auto; padding-bottom: 53px"
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
      <a-form-item v-bind="formItemLayout" label="技术名称">
        <a-input
          placeholder="请输入技术名称"
          v-decorator="[
            'name',
            { rules: [{ required: true, message: '技术名称不能为空' }] },
          ]"
        />
      </a-form-item>
      
      <a-form-item v-bind="formItemLayout" label="备注">
        <a-input
          placeholder="请输入备注"
          v-decorator="[
            'note',
          ]"
        />
      </a-form-item>
       <!-- 项目主要人员 -->
      <a-row type="flex" justify="center">
        <b>
          <h2>授权操作人员</h2>
        </b>
      </a-row>
      <div style="margin: 5px 0px">
        <a-row type="flex" justify="center">
          <a-col :span="colSpan">
            <xxbBCheck-d ref="xxbBCheckDList"> </xxbBCheck-d>
          </a-col>
        </a-row>
      </div>
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
import XxbBCheckD from "./MedicalDetail.vue";
const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 },
};
export default {
  name: "MdlBMedicalchinaAdd",
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
      mdlBMedicalchina: {},
      colSpan: 23,
    };
  },
  components: {XxbBCheckD},
  methods: {
    reset() {
      this.loading = false;
      this.mdlBMedicalchina = {};
       this.$refs.xxbBCheckDList.reset();
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
         let subData= this.$refs.xxbBCheckDList.getFieldValues();
          this.$post("mdlBMedicalchina", {
            ...this.mdlBMedicalchina,
            subData: JSON.stringify(subData)
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
        "name",
        "code",
        "note"
      ]);
      if (typeof values !== "undefined") {
        Object.keys(values).forEach((_key) => {
          this.mdlBMedicalchina[_key] = values[_key];
        });
      }
    },
  },
};
</script>
