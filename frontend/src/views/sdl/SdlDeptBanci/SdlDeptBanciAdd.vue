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
          option-filter-prop="children"
         :filter-option="filterOption"
         show-search
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
      <a-form-item v-bind="formItemLayout" label="班次">
        <a-select
          @change="banciChange"
          v-decorator="[
            'banciId',
            { rules: [{ required: true, message: '班次不能为空' }] },
          ]"
        >
          <a-select-option v-for="d in banciData" :key="d.id" :value="d.id">
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
  name: "sdlDeptBanciAdd",
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
      sdlDeptBanci: {},
      deptData: [],
      banciData: [],
      value: "",
    };
  },
  watch: {
    addVisiable() {
      if (this.addVisiable) {
        this.$get("dept/list",{parentId: '0'}).then((res) => {
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
      this.sdlDeptBanci = {};
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
          this.$post("sdlDeptBanci", {
            ...this.sdlDeptBanci,
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
        filterOption(input, option) {
      return (
        option.componentOptions.children[0].text.toLowerCase().indexOf(input.toLowerCase()) >= 0
      );
    },
    banciChange(value) {
      let data= this.banciData.filter(p=>p.id==value)
      this.sdlDeptBanci["banciName"]=data[0].muduleName
    },
    deptChange(value) {
      let data= this.deptData.filter(p=>p.deptId==value)
      this.sdlDeptBanci["deptName"]=data[0].deptName
    },
    setFields() {
      let values = this.form.getFieldsValue(["banciId", "deptId","startDate","endDate"]);
      if (typeof values !== "undefined") {
        Object.keys(values).forEach((_key) => {
          this.sdlDeptBanci[_key] = values[_key];
        });
      }
    },
  },
};
</script>
