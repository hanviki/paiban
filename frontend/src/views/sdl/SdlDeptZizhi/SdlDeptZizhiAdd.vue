<template>
  <a-drawer
    title="新增"
    :maskClosable="false"
    width="650"
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="addVisiable"
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
            :value='`${d.deptId}`'
          >
            {{ d.deptName }}
          </a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="资质类型">
        <a-select
          @change="zizhiChange"
          v-decorator="[
            'zizhiId',
            { rules: [{ required: true, message: '资质类型不能为空' }] },
          ]"
        >
          <a-select-option v-for="d in zizhiData" :key="d.id" :value="d.id">
            {{ d.muduleName }}
          </a-select-option>
        </a-select>
      </a-form-item>

      <a-form-item v-bind="formItemLayout" label="是否到病区">
        <a-checkbox
          v-decorator="[
            'isBq'
          ]"
        >
          是否到病区
        </a-checkbox>
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="开始时间">
        <a-date-picker 
        placeholder="请选择开始时间"
        :dateFormat="dateFormat"
         v-decorator="[
            'startDate',
            { rules: [{ required: true, message: '开始时间不能为空' }] },
          ]">
        </a-date-picker>
      </a-form-item>
       <a-form-item v-bind="formItemLayout" label="结束时间">
        <a-date-picker 
        placeholder="请选择结束时间"
        :dateFormat="dateFormat"
         v-decorator="[
            'endDate',
            { rules: [{ required: true, message: '结束时间不能为空' }] },
          ]">
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
const formItemLayout = {
  labelCol: { span: 5 },
  wrapperCol: { span: 18 },
};
export default {
  name: "SdlDeptZizhiAdd",
  props: {
    addVisiable: {
      default: false,
    },
  },
  data() {
    return {
      loading: false,
      formItemLayout,
      dateFormat: "YYYY-MM-DD",
      form: this.$form.createForm(this),
      sdlDeptZizhi: {},
      deptData: [],
      zizhiData: [],
      value: "",
    };
  },
  watch: {
    addVisiable() {
      if (this.addVisiable) {
        this.$get("dept/list",{parentId: '0'}).then((res) => {
          this.deptData = res.data;
        });
        this.$get("sdlDZizhi").then((res) => {
          this.zizhiData = res.data.rows;
        });
      }
    },
  },
  methods: {
    reset() {
      this.loading = false;
      this.sdlDeptZizhi = {};
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
          this.$post("sdlDeptZizhi", {
            ...this.sdlDeptZizhi,
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
    zizhiChange(value) {
      let data= this.zizhiData.filter(p=>p.id==value)
      this.sdlDeptZizhi["zizhiName"]=data[0].muduleName
    },
    deptChange(value) {
      let data= this.deptData.filter(p=>p.deptId==value)
      this.sdlDeptZizhi["deptName"]=data[0].deptName
    },
    setFields() {
      let values = this.form.getFieldsValue(["zizhiId", "deptId", "isBq","startDate","endDate"]);
      if (typeof values !== "undefined") {
        Object.keys(values).forEach((_key) => {
          this.sdlDeptZizhi[_key] = values[_key];
        });
      }
    },
  },
};
</script>
