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
        <a-select
          @change="deptChange"
          v-decorator="[
            'deptId',
            { rules: [{ required: true, message: '科室不能为空' }] },
          ]"
        >
          <a-select-option
            v-for="d in deptData"
            :key="d.deptId"
            :value="`${d.deptId}`"
          >
            {{ d.deptName }}
          </a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="班次">
        <a-select
          @change="banciChange"
          v-decorator="[
            'banciId',
            { rules: [{ required: true, message: '班次不能为空' }] },
          ]"
        >
          <a-select-option
            v-for="d in banciData"
            :key="`${d.id}`"
            :value="`${d.id}`"
          >
            {{ d.muduleName }}
          </a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="开始时间">
        <a-date-picker
          placeholder="请选择开始时间"
          :dateFormat="dateFormat"
          v-decorator="[
            'startDate',
            { rules: [{ required: true, message: '开始时间不能为空' }] },
          ]"
        >
        </a-date-picker>
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="结束时间">
        <a-date-picker
          placeholder="请选择结束时间"
          :dateFormat="dateFormat"
          v-decorator="[
            'endDate',
            { rules: [{ required: true, message: '结束时间不能为空' }] },
          ]"
        >
        </a-date-picker>
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
  labelCol: { span: 5 },
  wrapperCol: { span: 18 },
};
export default {
  name: "SdlDeptBanciEdit",
  props: {
    editVisiable: {
      default: false,
    },
  },
  data() {
    return {
      loading: false,
      formItemLayout,
       dateFormat: "YYYY-MM-DD",
      form: this.$form.createForm(this),
      sdlDeptBanci: {},
      deptData: [],
      banciData: [],
      isDefaultCheck: true,
    };
  },
  watch: {
    editVisiable() {
      if (this.editVisiable) {
        this.$get("dept/list", { parentId: "0" }).then((res) => {
          this.deptData = res.data;
        });
        this.$get("sdlDBanci").then((res) => {
          this.banciData = res.data.rows;
        });
      }
    },
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
    banciChange(value) {
      let data = this.banciData.filter((p) => p.id == value);
      this.sdlDeptBanci["banciName"] = data[0].muduleName;
    },
    deptChange(value) {
      let data = this.deptData.filter((p) => p.deptId == value);
      this.sdlDeptBanci["deptName"] = data[0].deptName;
    },
    setFormValues({ ...sdlDeptBanci }) {
      let fields = ["isBq", "banciId", "deptId", "startDate", "endDate"];
      let fieldDates = ["startDate", "endDate"];
      let that = this;
      Object.keys(sdlDeptBanci).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          that.form.getFieldDecorator(key);
          let obj = {};
          if (fieldDates.indexOf(key) !== -1) {
            if (sdlDeptBanci[key] !== "") {
              obj[key] = moment(sdlDeptBanci[key]);
            } else {
              obj[key] = "";
            }
          } else {
            if (key == "banciId") {
              obj[key] = sdlDeptBanci[key].toString();
            } else {
              obj[key] = sdlDeptBanci[key];
            }
          }
          that.form.setFieldsValue(obj);
        }
      });
      that.sdlDeptBanci.id = sdlDeptBanci.id;
      that.sdlDeptBanci.deptName = sdlDeptBanci.deptName;
      that.sdlDeptBanci.banciName = sdlDeptBanci.banciName;
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          let sdlDeptBanci = this.form.getFieldsValue();
          sdlDeptBanci.id = this.sdlDeptBanci.id;
          sdlDeptBanci.deptName = this.sdlDeptBanci.deptName;
          sdlDeptBanci.banciName = this.sdlDeptBanci.banciName;
          this.$put("sdlDeptBanci", {
            ...sdlDeptBanci,
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
