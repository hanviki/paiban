<template>
  <a-drawer
    :title="`新增${type}`"
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
            {{ d2.userAccountName + " " + d2.userAccount + " " + d2.birthday }}
          </a-select-option>
        </a-select>
      </a-form-item>
      <template v-if="handleType('基本处方权')">
        <a-form-item v-bind="formItemLayout" label="培训日期">
          <a-date-picker
            v-decorator="[
              'trainDate',
              { rules: [{ required: true, message: '培训日期不能为空' }] },
            ]"
          />
        </a-form-item>
        <a-form-item v-bind="formItemLayout" label="考核分数">
          <a-input
            placeholder="请输入考核分数"
            v-decorator="[
              'exiamScore',
              { rules: [{ required: true, message: '考核分数不能为空' }] },
            ]"
          />
        </a-form-item>
        <a-form-item v-bind="formItemLayout" label="考核结果">
          <a-input
            placeholder="请输入考核结果"
            v-decorator="[
              'exiamResult',
              { rules: [{ required: true, message: '考核结果不能为空' }] },
            ]"
          />
        </a-form-item>
        <a-form-item v-bind="formItemLayout" label="授权文件">
          <a-select
            v-decorator="[
              'archiveId',
              { rules: [{ required: true, message: '授权文件不能为空' }] },
            ]"
            option-filter-prop="children"
            :filter-option="filterOption"
            show-search
            @change="fileChange"
          >
            <a-select-option v-for="d in fileData" :key="d" :value="`${d.id}`">
              {{ d.fileName + " " + d.fileCode }}
            </a-select-option>
          </a-select>
        </a-form-item>
      </template>
      <template v-else-if="handleType('麻精药物处方权')">
        <a-form-item v-bind="formItemLayout" label="是否处方">
          <a-select
            v-decorator="[
              'isChufang',
              { rules: [{ required: true, message: '是否处方不能为空' }] },
            ]"
          >
            <a-select-option value="是"> 是 </a-select-option>
            <a-select-option value="否"> 否 </a-select-option>
          </a-select>
        </a-form-item>
      </template>
      <template v-else>
        <a-form-item v-bind="formItemLayout" label="级别">
          <a-select
            v-decorator="[
              'level',
              { rules: [{ required: true, message: '级别不能为空' }] },
            ]"
          >
            <a-select-option value="限制级"> 限制级 </a-select-option>
            <a-select-option value="非限制级"> 非限制级 </a-select-option>
            <a-select-option value="特殊使用"> 特殊使用 </a-select-option>
          </a-select>
        </a-form-item>
      </template>
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
  name: "MdlBChufangAdd",
  props: {
    addVisiable: {
      default: false,
    },
    type: {
      default: "",
    },
  },
  data() {
    return {
      loading: false,
      formItemLayout,
      form: this.$form.createForm(this),
      mdlBChufang: {
        type: this.type,
      },
      userData: [],
      fileData: [],
    };
  },
  watch: {
    addVisiable() {
      if (this.addVisiable) {
        this.fetchFile();
        this.mdlBChufang.type = this.type;
      }
    },
  },
  methods: {
    handleType(type) {
      return this.type == type;
    },
    reset() {
      this.loading = false;
      this.mdlBChufang = {};
      this.form.resetFields();
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },
    fetchFile() {
      this.$get("mdlBArchive/list", { parentId: "0" }).then((res) => {
        this.fileData = [];
        this.fileData.push(...res.data);
      });
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
    userChange(value, option) {
      console.info(option);
      this.mdlBChufang["userAccountName"] = option.key.userAccountName;
      this.mdlBChufang["userAccount"] = option.key.userAccount;
    },
    fileChange(value, option) {
      this.mdlBChufang["archiveName"] = option.key.fileName;
      this.mdlBChufang["archiveCode"] = option.key.fileCode;
      this.mdlBChufang["fileId"] = option.key.fileId;
      this.mdlBChufang["fileUrl"] = option.key.fileUrl;
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          this.setFields();
          this.$post("mdlBChufang", {
            ...this.mdlBChufang,
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
      let cls = [
        "trainDate",
        "exiamScore",
        "exiamResult",
        "archiveId",

        "isChufang",
        "level",
      ];
      if (this.type == "基本处方权") {
        cls = ["trainDate", "exiamScore", "exiamResult", "archiveId"];
      } else if (this.type == "麻精药物处方权") {
        cls = ["isChufang"];
      } else {
        cls = ["level"];
      }
      let values = this.form.getFieldsValue(cls);
      if (typeof values !== "undefined") {
        Object.keys(values).forEach((_key) => {
          this.mdlBChufang[_key] = values[_key];
        });
      }
    },
  },
};
</script>
