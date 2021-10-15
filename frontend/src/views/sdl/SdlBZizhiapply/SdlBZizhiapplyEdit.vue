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
      <a-form-item v-bind="formItemLayout" label="人员类型id">
        <a-input
          placeholder="请输入人员类型id"
          v-decorator="[
            'rylxId',
            { rules: [{ required: true, message: '人员类型id不能为空' }] },
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
      <a-form-item v-bind="formItemLayout" label="科室">
        <a-input
          placeholder="请输入科室"
          v-decorator="[
            'deptName',
            { rules: [{ required: true, message: '科室不能为空' }] },
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
      <a-form-item v-bind="formItemLayout" label="申请日期">
        <a-date-picker
          v-decorator="[
            'applyDate',
            { rules: [{ required: true, message: '申请日期不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="申请资质">
        <a-input
          placeholder="请输入申请资质"
          v-decorator="[
            'applyType',
            { rules: [{ required: true, message: '申请资质不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="来院时间">
        <a-date-picker
          v-decorator="[
            'schoolDate',
            { rules: [{ required: true, message: '来院时间不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="现职称">
        <a-input
          placeholder="请输入现职称"
          v-decorator="[
            'positionName',
            { rules: [{ required: true, message: '现职称不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="现职称聘任时间">
        <a-input
          placeholder="请输入现职称聘任时间"
          v-decorator="[
            'positionPrdate',
            { rules: [{ required: true, message: '现职称聘任时间不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="原资质">
        <a-input
          placeholder="请输入原资质"
          v-decorator="[
            'orignType',
            { rules: [{ required: true, message: '原资质不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="新资质">
        <a-input
          placeholder="请输入新资质"
          v-decorator="[
            'newType',
            { rules: [{ required: true, message: '新资质不能为空' }] },
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
  name: "SdlBZizhiapplyEdit",
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
      sdlBZizhiapply: {},
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
    setFormValues({ ...sdlBUser }) {
      let fields = [
        "rylxId",
        "rylxName",
        "deptName",
        "bqName",
        "deptId",
        "applyDate",
        "applyType",
        "schoolDate",
        "positionName",
        "positionPrdate",
        "orignType",
        "newType",
        "note",
        "auditDate",
      ];
      let fieldDates = ["applyDate", "schoolDate", "auditDate"];
      Object.keys(sdlBZizhiapply).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key);
          let obj = {};
          if (fieldDates.indexOf(key) !== -1) {
            if (sdlBZizhiapply[key] !== "") {
              obj[key] = moment(sdlBZizhiapply[key]);
            } else {
              obj[key] = "";
            }
          } else {
            obj[key] = sdlBZizhiapply[key];
          }
          this.form.setFieldsValue(obj);
        }
      });
      this.sdlBZizhiapply.id = sdlBZizhiapply.id;
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          let sdlBZizhiapply = this.form.getFieldsValue();
          sdlBZizhiapply.id = this.sdlBZizhiapply.id;
          this.$put("sdlBZizhiapply", {
            ...sdlBZizhiapply,
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
