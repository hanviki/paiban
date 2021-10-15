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
import moment from "moment";

const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 },
};
export default {
  name: "SdlBScheduleEdit",
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
      sdlBSchedule: {},
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
    setFormValues({ ...sdlBSchedule }) {
      let fields = [
        "deptName",
        "yqName",
        "deptId",
        "yqId",
        "startDate",
        "endDate",
        "note",
        "auditDate",
      ];
      let fieldDates = ["startDate", "endDate", "auditDate"];
      Object.keys(sdlBSchedule).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key);
          let obj = {};
          if (fieldDates.indexOf(key) !== -1) {
            if (sdlBSchedule[key] !== "") {
              obj[key] = moment(sdlBSchedule[key]);
            } else {
              obj[key] = "";
            }
          } else {
            obj[key] = sdlBSchedule[key];
          }
          this.form.setFieldsValue(obj);
        }
      });
      this.sdlBSchedule.id = sdlBSchedule.id;
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          let sdlBSchedule = this.form.getFieldsValue();
          sdlBSchedule.id = this.sdlBSchedule.id;
          this.$put("sdlBSchedule", {
            ...sdlBSchedule,
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
