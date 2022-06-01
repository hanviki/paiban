<template>
  <a-drawer
    title="修改"
    :maskClosable="false"
    width="80%"
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="editVisiable"
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
      <a-form-item v-bind="formItemLayout" label="编码">
        <a-input
          placeholder="请输入编码"
          v-decorator="[
            'code',
            { rules: [{ required: true, message: '编码不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="手术名称">
        <a-input
          placeholder="请输入手术名称"
          v-decorator="[
            'name',
            { rules: [{ required: true, message: '手术名称不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="手术等级">
           <a-select  v-decorator="[
            'level',
            { rules: [{ required: true, message: '手术等级不能为空' }] },
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
      <a-form-item v-bind="formItemLayout" label="手术切口类别">
         <a-select   v-decorator="[
            'lb',
            { rules: [{ required: true, message: '手术切口类别不能为空' }] },
          ]"
                 >
                    <a-select-option
                    value="I"
                  >
                   I
                  </a-select-option>
                   <a-select-option
                    value="II"
                  >
                   II
                  </a-select-option>
                   <a-select-option
                    value="III"
                  >
                   III
                  </a-select-option>
                   <a-select-option
                    value="IV"
                  >
                   IV
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
import moment from "moment";

const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 },
};
export default {
  name: "MdlDSurgeryEdit",
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
      mdlDSurgery: {},
      deptData: []
    };
  },
  watch: {
    editVisiable(){
      if(this.editVisiable){
        this.fetchDept()
      }
   }
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
    setFormValues({ ...mdlDSurgery }) {
      let fields = ["deptNew", "code", "name", "level", "lb", "note","startDate"];
      let fieldDates = ["startDate"];
      Object.keys(mdlDSurgery).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key);
          let obj = {};
          if (fieldDates.indexOf(key) !== -1) {
            if (mdlDSurgery[key] !== null && mdlDSurgery[key] !== "") {
              obj[key] = moment(mdlDSurgery[key]);
              this.form.setFieldsValue(obj);
            } else {
              obj[key] = "";
            }
          } else {
            obj[key] = mdlDSurgery[key];
            this.form.setFieldsValue(obj);
          }
        }
      });
      this.mdlDSurgery.id = mdlDSurgery.id;
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          let mdlDSurgery = this.form.getFieldsValue();
          mdlDSurgery.id = this.mdlDSurgery.id;
          this.$put("mdlDSurgery", {
            ...mdlDSurgery,
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
