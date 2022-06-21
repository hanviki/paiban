<template>
  <a-drawer
    title="新增"
    :maskClosable="false"
    width="80%"
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="addVisiable"
    style="height: calc(100% - 55px); overflow: auto; padding-bottom: 53px"
  >
    <a-form :form="form">
      <a-form-item v-bind="formItemLayout" label="科室">
         <a-select  v-decorator="[
            'deptNew',
            { rules: [{ required: true, message: '科室不能为空' }] },
          ]"
                  option-filter-prop="children"
         :filter-option="filterOption"
         show-search>
                  <a-select-option
                    v-for="d in deptData"
                    :key="d.deptId"
                    :value="`${d.deptId}`"
                  >
                    {{ d.deptName }}
                  </a-select-option>
                </a-select>
      </a-form-item>
       <a-form-item v-bind="formItemLayout" label="类别">
        <a-input
          placeholder="请输入类别"
          v-decorator="[
            'lb',
            { rules: [{ required: true, message: '类别不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="编码">
        <a-input
          placeholder="请输入编码"
          v-decorator="[
            'code',
            { rules: [{ required: true, message: '编码不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="操作名称">
        <a-input
          placeholder="请输入操作名称"
          v-decorator="[
            'name',
            { rules: [{ required: true, message: '操作名称不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="操作等级">
           <a-select  v-decorator="[
            'level',
            { rules: [{ required: true, message: '操作等级不能为空' }] },
          ]"
                 >
                   <a-select-option
                    value="一级"
                  >
                   一级
                  </a-select-option>
                   <a-select-option
                    value="二级"
                  >
                   二级
                  </a-select-option>
                   <a-select-option
                    value="三级"
                  >
                   三级
                  </a-select-option>
                   <a-select-option
                    value="四级"
                  >
                   四级
                  </a-select-option>
                </a-select>
      </a-form-item>
       <a-form-item v-bind="formItemLayout" label="启用日期">
        <a-date-picker
          v-decorator="[
            'startDate',
            { rules: [{ required: true, message: '启用日期不能为空' }] },
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
  labelCol: { span: 3 },
  wrapperCol: { span: 18 },
};
export default {
  name: "MdlDSurgeryAdd",
  props: {
    addVisiable: {
      default: false,
    },
  },
  watch: {
    addVisiable(){
      if(this.addVisiable){
        this.fetchDept()
      }
   }
  },
  data() {
    return {
      loading: false,
      formItemLayout,
      form: this.$form.createForm(this),
      mdlDSurgery: {},
      deptData: []
    };
  },
  methods: {

    reset() {
      this.loading = false;
      this.mdlDSurgery = {};
      this.form.resetFields();
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },
         filterOption(input, option) {
      return (
        option.componentOptions.children[0].text
          .toLowerCase()
          .indexOf(input.toLowerCase()) >= 0
      );
    },
     fetchDept() {
      this.$get("sdlBUser/deptNew", {  }).then((res) => {
        this.deptData = [];
         this.deptData.push({
          deptId: "-1",
          deptName: "全部",
        });
       if(res.data[0]!=null){
         this.deptData.push(...res.data);
        }
      });
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          this.setFields();
          this.$post("mdlDOperation", {
            ...this.mdlDSurgery,
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
        "deptNew",
        "code",
        "name",
        "level",
        "note",
        "lb",
        "startDate"
      ]);
      if (typeof values !== "undefined") {
        Object.keys(values).forEach((_key) => {
          this.mdlDSurgery[_key] = values[_key];
        });
      }
    },
  },
};
</script>
