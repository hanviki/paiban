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
      <a-form-item v-bind="formItemLayout" label="姓名">
        <a-input disabled v-decorator="['userAccountName']" />
      </a-form-item>
      <template v-if="handleType('考试管理')">
        <a-form-item v-bind="formItemLayout" label="考试日期">
          <a-date-picker
            v-decorator="[
              'trainDate',
              { rules: [{ required: true, message: '考试日期不能为空' }] },
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
         <a-form-item v-bind="formItemLayout" label="授权日期">
          <a-date-picker
            v-decorator="[
              'powerDate',
              { rules: [{ required: true, message: '授权日期不能为空' }] },
            ]"
          />
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
         <a-form-item v-bind="formItemLayout" label="授权日期">
          <a-date-picker
            v-decorator="[
              'powerDate',
              { rules: [{ required: true, message: '授权日期不能为空' }] },
            ]"
          />
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
import moment from "moment";

const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 },
};
export default {
  name: "MdlBChufangEdit",
  props: {
    editVisiable: {
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
    editVisiable() {
      if (this.editVisiable) {
        this.fetchFile(this.type);
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
    handleSearch(value) {
      this.fetchUser(value);
    },
     handleType(type) {
      return this.type == type;
    },
   fetchFile(type) {
      this.$get("mdlBArchive/list", { fileType: this.getFileType(type) }).then((res) => {
        this.fileData = [];
        this.fileData.push(...res.data);
      });
    },
    getFileType(type){
        if(type=="麻精药物处方权"){
           return "麻精药物"
        }
        if(type=="抗菌药物分级管理"){
           return "抗菌药物"
        }
        return ""
    },
    fileChange(value, option) {
      this.mdlBChufang["archiveName"] = option.key.fileName;
      this.mdlBChufang["archiveCode"] = option.key.fileCode;
      this.mdlBChufang["fileId"] = option.key.fileId;
      this.mdlBChufang["fileUrl"] = option.key.fileUrl;
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

      this.form.getFieldDecorator("userAccountName");
      this.form.setFieldsValue({ userAccountName: option.key.userAccountName });

      this.mdlBChufang["deptNew"] = option.key.deptNew;
      this.mdlBChufang["zhicheng"] = option.key.zhicheng;
    },
    setFormValues({ ...mdlBChufang }) {
      let fields = [
        "userAccount",
        "userAccountName",
        "trainDate",
        "powerDate",
        "exiamScore",
        "exiamResult",
        "archiveId",
        "isChufang",
        "level",
      ];
      let fieldDates = ["trainDate","powerDate"];
      Object.keys(mdlBChufang).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key);
          let obj = {};
          if (fieldDates.indexOf(key) !== -1) {
            if (mdlBChufang[key] !== null && mdlBChufang[key] !== "") {
              obj[key] = moment(mdlBChufang[key]);
              this.form.setFieldsValue(obj);
            } else {
              obj[key] = "";
            }
          } else {
            if (key == "archiveId") {
              if( mdlBChufang[key]==null){
                obj[key] = "";
              }
              else {
              obj[key] = mdlBChufang[key].toString();
              }

            } else {
              obj[key] = mdlBChufang[key];
            }

            this.form.setFieldsValue(obj);
          }
        }
      });
      this.mdlBChufang.type = mdlBChufang.type;
      this.mdlBChufang["archiveName"] = mdlBChufang["archiveName"];
      this.mdlBChufang["archiveCode"] = mdlBChufang["archiveCode"];
      this.mdlBChufang["fileId"] = mdlBChufang["fileId"];
      this.mdlBChufang["fileUrl"] = mdlBChufang["fileUrl"];
        this.mdlBChufang["deptNew"] = mdlBChufang["deptNew"];
      this.mdlBChufang["zhicheng"] = mdlBChufang["zhicheng"];
      this.mdlBChufang.id = mdlBChufang.id;
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          let mdlBChufang = this.form.getFieldsValue();
          mdlBChufang.id = this.mdlBChufang.id;
          mdlBChufang.type = this.mdlBChufang.type;
          mdlBChufang["archiveName"] = this.mdlBChufang["archiveName"];
          mdlBChufang["archiveCode"] = this.mdlBChufang["archiveCode"];
          mdlBChufang["fileId"] = this.mdlBChufang["fileId"];
          mdlBChufang["fileUrl"] = this.mdlBChufang["fileUrl"];
          mdlBChufang["deptNew"] = this.mdlBChufang["deptNew"];
      mdlBChufang["zhicheng"] = this.mdlBChufang["zhicheng"];
          this.$put("mdlBChufang", {
            ...mdlBChufang,
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
