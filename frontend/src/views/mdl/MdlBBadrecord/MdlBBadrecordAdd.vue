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
         <a-form-item v-bind="formItemLayout" label="人员">
        <a-select
          v-decorator="[
            'userAccount',
            { rules: [{ required: true, message: '人员必填' }] },
          ]"
          @search="handleSearch"
          :filter-option="false"
          show-search
          @change="userChange"
        >
          <a-select-option
            v-for="d2 in userData"
            :key="d2"
            :value="`${d2.userAccount}`"
          >
            {{ d2.userAccountName + " " + d2.userAccount + " " + d2.birthday+ " " + d2.deptNew }}
          </a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="记分指标">
        <a-select
            v-decorator="[
              'code',
              { rules: [{ required: true, message: '记分指标不能为空' }] },
            ]"
            option-filter-prop="children"
            :filter-option="filterOption"
            show-search
            @change="fileChange"
          >
            <a-select-option v-for="d in indData" :key="d" :value="`${d.code}`">
              {{ d.code + " " + d.name+ " " +d.score+"分"}}
            </a-select-option>
          </a-select>
      </a-form-item>
       <a-form-item v-bind="formItemLayout" label="具体事由">
        <a-textarea
          placeholder="请输入具体事由"
          v-decorator="[
            'note',
            { rules: [{ required: true, message: '具体事由不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="记录时间">
        <a-date-picker
          v-decorator="[
            'startDate',
            { rules: [{ required: true, message: '记录时间不能为空' }] },
          ]"
        />
      </a-form-item>
     <a-form-item v-bind="formItemLayout" label="附件">
        <upload-single-file
            ref="fileagent"
            @uploadRemove="removeAgent_1"
            @uploadSuc="uploadAgent_1"
          >
          </upload-single-file>
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
import UploadSingleFile from "../../common/uploadSingleFile"
const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 },
};
export default {
  name: "MdlBBadrecordAdd",
  props: {
    addVisiable: {
      default: false,
    },
  },
  components: { UploadSingleFile },
  data() {
    return {
      loading: false,
      formItemLayout,
      form: this.$form.createForm(this),
      mdlBBadrecord: {},
      userData: [],
      indData: []
    };
  },
  watch: {
    addVisiable() {
      if (this.addVisiable) {
        this.fetchIndict()
      }
    },
  },
  methods: {
    reset() {
      this.loading = false;
      this.mdlBBadrecord = {};
      this.$refs.fileagent.reset();
      this.form.resetFields();
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },
    uploadAgent_1(fileId, fileUrl) {
      this.mdlBBadrecord.fileId = fileId;
      this.mdlBBadrecord.fileUrl = fileUrl;
    },
    removeAgent_1() {
      this.mdlBBadrecord.fileId = "";
      this.mdlBBadrecord.fileUrl = "";
    },
     filterOption(input, option) {
      return (
        option.componentOptions.children[0].text
          .toLowerCase()
          .indexOf(input.toLowerCase()) >= 0
      );
    },
    handleSearch(value) {
      this.fetchUser(value);
    },
    fetchUser(userAccount) {
      this.$get("sdlBUser/search", {
        userAccount,
      }).then((r) => {
        console.log(r);
        this.userData = r.data;
      });
    },
    fetchIndict() {
      this.$get("mdlDBadscore", {
        pageNum: 1,
        pageSize: 1000
      }).then((r) => {
        console.log(r);
        this.indData = r.data.rows;
      });
    },
    userChange(value, option) {
      console.info(option);
      this.mdlBBadrecord["userAccountName"] = option.key.userAccountName;
      this.mdlBBadrecord["userAccount"] = option.key.userAccount;
      
      this.mdlBBadrecord["type"] = option.key.rylx;
      this.mdlBBadrecord["yggh"] = option.key.yggh;
    },
    fileChange(value, option) {
      this.mdlBBadrecord["indict"] = option.key.name;
      this.mdlBBadrecord["score"] = option.key.score;
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          this.setFields();
          this.$post("mdlBBadrecord", {
            ...this.mdlBBadrecord,
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
        "code",
        "note",
      
        "startDate",
       
      ]);
      if (typeof values !== "undefined") {
        Object.keys(values).forEach((_key) => {
          this.mdlBBadrecord[_key] = values[_key];
        });
      }
    },
  },
};
</script>
