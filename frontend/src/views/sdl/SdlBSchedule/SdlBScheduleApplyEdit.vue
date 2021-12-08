<template>
  <a-drawer
    title="排班修改申请审核"
    :maskClosable="false"
    width="60%"
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="editVisiable"
    style="height: calc(100% - 55px); overflow: auto; padding-bottom: 53px"
  >
    <a-form :form="form">
      <a-row>
        <a-col :sm="24">
          <a-form-item v-bind="formItemLayout" label="科室">
            <a-input
              :disabled="true"
              v-decorator="[
                'deptName'
              ]"
            />
          </a-form-item>
        </a-col>
        <a-col :sm="24">
          <a-form-item v-bind="formItemLayout" label="开始日期">
            <a-date-picker 
            :disabled="true"
        placeholder="请选择开始日期"
         v-decorator="[
            'startDate'
          ]"
      >
        </a-date-picker>
          </a-form-item>
        </a-col>
        <a-col :sm="24">
         <a-form-item v-bind="formItemLayout" label="结束日期">
            <a-date-picker 
            :disabled="true"
        placeholder="请选择结束日期"
         v-decorator="[
            'endDate'
          ]"
      >
        </a-date-picker>
          </a-form-item>
        </a-col>
        <a-col :sm="24">
          <a-form-item
            :disabled="true"
            v-bind="formItemLayout"
            label="申请理由"
          >
            <a-textarea
              :disabled="true"
              v-decorator="['note']"
              placeholder="请填写申请理由"
              :auto-size="{ minRows: 5 }"
            />
          </a-form-item>
        </a-col>
        <a-col :sm="24">
          <a-form-item
            v-bind="formItemLayout"
            label="审核意见"
          >
            <a-textarea
              v-decorator="[
                'auditApplySuggestion',
                { rules: [{ required: true, message: '审核意见不能为空' }] },
              ]"
              placeholder="请填写审核意见"
              :auto-size="{ minRows: 5,maxRows: 8 }"
            />
          </a-form-item>
        </a-col>
      </a-row>
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
        >审核通过</a-button
      >
      <a-button @click="handleNoSubmit" type="danger" :loading="loading"
        >审核不通过</a-button
      >
    </div>
  </a-drawer>
</template>
<script>
import moment from "moment";

const formItemLayout = {
  labelCol: { span: 6 },
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
      value: "",
      data: [],
      userData: [],
    };
  },
  methods: {
    reset() {
      this.loading = false;
      this.form.resetFields();
      this.value = "";
      this.data = [];
      this.userData = [];
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },

   
    setFormValues({ ...sdlBSchedule }) {
      let fields = [
         "startDate",
        "endDate",
         "deptName",
         "note",
         "auditApplySuggestion"
      ];
      let fieldDates = ["startDate","endDate"];
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
          this.sdlBSchedule[key] = obj[key];
          this.form.setFieldsValue(obj);
        }
      });
      this.sdlBSchedule.id = sdlBSchedule.id;
    },
   
    handleSubmit() {
      let that = this;

      that.form.validateFields((err, values) => {
        if (!err) {
          that.$confirm({
            title: "确定审核通过此记录?",
            content: "当您点击确定按钮后，这些记录将会被审核",
            centered: true,
            onOk() {
              let sdlBSchedule = that.form.getFieldsValue();
              sdlBSchedule.id = that.sdlBSchedule.id;
              sdlBSchedule.stateApply = 4;
              sdlBSchedule.stateApplyFlag = 4;
              that
                .$put("sdlBSchedule", {
                  ...sdlBSchedule,
                })
                .then(() => {
                  that.reset();
                  that.$emit("success");
                })
                .catch(() => {
                  that.loading = false;
                });
            },
            onCancel() {
              that.selectedRowKeys = [];
            },
          });
        }
      });
    },
    handleNoSubmit() {
      let that = this;

      that.form.validateFields((err, values) => {
        if (!err) {
          that.$confirm({
            title: "确定审核不通过此记录?",
            content: "当您点击确定按钮后，这些记录将会被审核不通过",
            centered: true,
            onOk() {
              let sdlBSchedule = that.form.getFieldsValue();
              sdlBSchedule.id = that.sdlBSchedule.id;
              sdlBSchedule.stateApply = 3;
              sdlBSchedule.stateApplyFlag = 3;
              that
                .$put("sdlBSchedule", {
                  ...sdlBSchedule,
                })
                .then(() => {
                  that.reset();
                  that.$emit("success");
                })
                .catch(() => {
                  that.loading = false;
                });
            },
            onCancel() {
              that.selectedRowKeys = [];
            },
          });
        }
      });
    },
    setFields() {
      let values = this.form.getFieldsValue([
        "auditApplySuggestion",
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
<style lang="less" scoped>
   .ant-input-disabled {
  color: rgba(0, 0, 0, 0.65) !important;
  background-color: #fff !important;
}
</style>
