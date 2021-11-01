<template>
  <a-drawer
    title="新增考勤周"
    :maskClosable="false"
    width="650"
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="addVisiable"
    style="height: calc(100% - 55px); overflow: auto; padding-bottom: 53px"
  >
    <a-form :form="form">
    
      <a-form-item v-bind="formItemLayout" label="考勤周">
        <a-week-picker 
        placeholder="请选择考勤周"
         v-decorator="[
            'note',
            { rules: [{ required: true, message: '考勤周不能为空' }] },
          ]"
        @change="handleWeekChange">
        </a-week-picker>
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="已选择日期">
        {{startDate}}-{{endDate}}
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
import moment from 'moment'
const formItemLayout = {
  labelCol: { span: 6 },
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
      dateFormat: "YYYY-MM",
      form: this.$form.createForm(this),
      sdlBSchedule: {},
      startDate: '',
      endDate: '',
    };
  },
  methods: {
    handleWeekChange(weekData ) {
　　this.startDate = moment(weekData).day(1).format('YYYY/MM/DD (dddd)'); // 周一日期
　　this.endDate = moment(weekData).day(7).format('YYYY/MM/DD (dddd)'); // 周日日期
},
    reset() {
      this.loading = false;
      this.sdlBSchedule = {};
      this.startDate='';
      this.endDate='';
      this.form.resetFields();
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
         // this.setFields();
          this.$post("sdlBSchedule", {
            startDate: this.startDate,
            endDate: this.endDate
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
        "startDate",
        "endDate",
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
