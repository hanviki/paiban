<template>
  <a-drawer
    :title="drawerTitle"
    :maskClosable="false"
    width="80%"
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="editVisiable"
    style="height: calc(100% - 15px); overflow: auto; padding-bottom: 13px"
  >
    <a-form :form="form">
      <div style="margin: 5px 0px">
        <table class="formtab">
          <tr>
            <td class="tdRight" style="width: 15%">
              <font class="fontColor">*</font>任务来源:
            </td>
            <td style="width: 35%">
              <a-form-item>
                <a-input
                  placeholder="请输入任务来源"
                  :maxLength="txtLength50"
                  v-decorator="[
                    'taskSource',
                    {
                      rules: [{ required: true, message: '任务来源不能为空' }],
                    },
                  ]"
                />
              </a-form-item>
            </td>
            <td class="tdRight" style="width: 15%">
              <font class="fontColor">*</font>外派类型：
            </td>
            <td style="width: 35%">
              <a-form-item>
                <a-input
                  placeholder="请输入外派类型"
                  :maxLength="txtLength50"
                  v-decorator="[
                    'taskType',
                    {
                      rules: [{ required: true, message: '外派类型不能为空' }],
                    },
                  ]"
                />
              </a-form-item>
            </td>
          </tr>
          <tr>
            <td class="tdRight" style="width: 15%">
              <font class="fontColor">*</font>
              外派事项:
            </td>
            <td colspan="3">
              <a-form-item>
                <a-input
                  placeholder="请输入外派事项"
                  v-decorator="[
                    'taskItem',
                    {
                      rules: [{ required: true, message: '外派事项不能为空' }],
                    },
                  ]"
                />
              </a-form-item>
            </td>
          </tr>
          <tr>
            <td class="tdRight" style="width: 15%">
              <font class="fontColor">*</font>外派开始时间:
            </td>
            <td style="width: 35%">
              <a-form-item>
                <a-date-picker
                  v-decorator="[
                    'startDate',
                    {
                      rules: [
                        { required: true, message: '外派开始时间不能为空' },
                      ],
                    },
                  ]"
                />
              </a-form-item>
            </td>
            <td class="tdRight" style="width: 15%">
              <font class="fontColor">*</font>外派结束时间:
            </td>
            <td style="width: 35%">
              <a-form-item>
                <a-date-picker
                  v-decorator="[
                    'endDate',
                    {
                      rules: [
                        { required: true, message: '外派结束时间不能为空' },
                      ],
                    },
                  ]"
                />
              </a-form-item>
            </td>
          </tr>
          <tr>
            <td class="tdRight" style="width: 15%">
              <font class="fontColor">*</font>外派地址:
            </td>
           
                  <td colspan="3">
                    <a-form-item>
                      <area-select
                        placeholder="请输入省"
                        :defaultValue="defaultValue"
                        @change="selectArea"
                      />
                    </a-form-item>
                  </td>
                 
          </tr>
          <tr>
            <td class="tdRight" style="width: 15%">
              <font class="fontColor">*</font>详细地址:
            </td>
            <td colspan="3">
              <a-form-item>
                <a-input
                  placeholder="请输入详细地址"
                  v-decorator="[
                    'address',
                    {
                      rules: [{ required: true, message: '详细地址不能为空' }],
                    },
                  ]"
                />
              </a-form-item>
            </td>
          </tr>
             <tr>
            <td class="tdRight" style="width: 15%">
              <font class="fontColor">*</font>链接地址:
            </td>
            <td colspan="3">
              <a-form-item>
                <a-input
                  placeholder="请输入链接地址"
                  v-decorator="[
                    'linkUrl',
                    {
                      rules: [{ required: true, message: '链接地址不能为空' }],
                    },
                  ]"
                />
              </a-form-item>
            </td>
          </tr>  <tr>
            <td class="tdRight" style="width: 15%">
              <font class="fontColor">*</font>附件:
            </td>
            <td colspan="3">
             <upload-single-file
                ref="fileagent"
                @uploadRemove="removeAgent_1"
                @uploadSuc="uploadAgent_1"
              >
              </upload-single-file>
            </td>
          </tr>
        </table>
      </div>

      <!-- 项目主要人员 -->
      <a-row type="flex" justify="center">
        <b>
          <h2>外派人员</h2>
        </b>
      </a-row>
      <div style="margin: 5px 0px">
        <a-row type="flex" justify="center">
          <a-col :span="colSpan">
            <xxbBCheck-d ref="xxbBCheckDList"> </xxbBCheck-d>
          </a-col>
        </a-row>
      </div>
      <!-- border:1px solid #2894FF; -->
     
      <!-- 项目第一操作者的最低职称限定 -->
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
      <a-space size="large">
        <a-button @click="handleSubmit(0)" v-if="this.isEdit" :loading="loading"
          >保存</a-button
        >
        <a-popconfirm
          title="确定提交数据？"
          v-if="this.isEdit"
          @confirm="handleSubmit(1)"
          okText="确定"
          cancelText="取消"
        >
          <a-button v-if="this.isEdit" type="primary" :loading="loading"
            >提交</a-button
          >
        </a-popconfirm>
      </a-space>
    </div>
  </a-drawer>
</template>

<script>
import XxbBCheckD from "./MdlBDetail.vue";
import UploadSingleFile from "../../common/uploadSingleFile";
import moment from "moment";
import AreaSelect from "../../common/AreaSelect.vue";
const formItemLayout = {
  labelCol: {
    span: 7,
  },
  wrapperCol: {
    span: 14,
  },
};
export default {
  name: "XxbBCheckEdit",
  components: {
    UploadSingleFile,
    XxbBCheckD,
    AreaSelect,
  },
  props: {
    editVisiable: {
      default: false,
    },
  },
  data() {
    return {
      loading: false,
      isUpdate: false,
      formItemLayout,
      form: this.$form.createForm(this),
      isEdit: false,
      txtLength50: 50,
      txtLength500: 500,
      txtLength30: 30,
      drawerTitle: "新增",
      projectType: "",
      colSpan: 23,
      xxbBCheck: {},
      defaultValue: []
    };
  },
  mounted() {},
  methods: {
    reset() {
      this.loading = false;
      this.isEdit = false;

      this.xxbBCheck = {};
      this.$refs.fileagent.reset();
      this.$refs.xxbBCheckDList.reset();
      this.form.resetFields();
    },
    onClose() {
      this.reset();
      if (this.isUpdate) {
        this.$emit("success");
      } else {
        this.$emit("close");
      }
      this.isUpdate = false;
    },
    uploadAgent_1(fileId, fileUrl) {
      this.xxbBCheck.fileId = fileId;
      this.xxbBCheck.fileUrl = fileUrl;
    },
    removeAgent_1() {
      this.xxbBCheck.fileId = "";
      this.xxbBCheck.fileUrl = "";
    },
    selectArea(selectedArea) {
      this.xxbBCheck.province = selectedArea;
    },
    getId() {
      this.$get("xxbBCheck/getId", {}).then((r) => {
        let id = r.data.data;
        this.xxbBCheck.id = id;
      });
    },
    userChange1(value, target) {
      this.xxbBCheck.userAccountName = target.userAccountName;
      this.xxbBCheck.userAccount = target.userAccount;

      // this.$refs.xxbBCheckDList.setBaseUserYggh(target.yggh);
      this.xxbBCheck.sexName = target.sexname;
      this.xxbBCheck.birthday = target.birthday;

      this.xxbBCheck.telephone = target.tel;
      this.form.getFieldDecorator("userAccountName");
      // this.form.getFieldDecorator("userAccount");
      // this.form.getFieldDecorator("yggh");
      this.form.getFieldDecorator("sexName");
      this.form.getFieldDecorator("birthday");

      // this.form.getFieldDecorator("userdept");
      this.form.getFieldDecorator("telephone");
      this.form.setFieldsValue({
        userAccountName: target.userAccountName,
        // userAccount: target.userAccount,
        // yggh: target.yggh,
        sexName: target.sexname,
        birthday: target.birthday,
        edu: target.edu,
        zhichenglc: target.zhichenglc,
        telephone: target.tel,
        // userdept: target.deptNew
      });
    },

    setFormValues(xxbBCheck, tit) {
      this.drawerTitle = tit;
      if (tit == "查看") {
        this.isEdit = false;
      }

      this.isUpdate = false;

      if (xxbBCheck === undefined || xxbBCheck === null || xxbBCheck === "") {
        this.isEdit = true;
        this.getId();
      } else {
        if (tit != "查看") {
          this.isEdit = true;
        }

        // setTimeout(() => {
        //   this.$refs.user1.userData = [
        //     {
        //       userAccountName: xxbBCheck.userAccountName,
        //       userAccount: xxbBCheck.userAccount,
        //       yggh: xxbBCheck.yggh,
        //     },
        //   ];
        //   this.$refs.user1.defaultValue = xxbBCheck.userAccount;
        // }, 100);
        let fields = [
          "taskSource",
          "taskType",
          "taskItem",
          "startDate",
          "endDate",
          "province",
          // "city",
          // "street",
          "address",
          "linkUrl",
        ];
        // let fieldDates = ["applydat", "srtdat", "enddat", "ycdat"];
        let fieldDates = [""];
        Object.keys(xxbBCheck).forEach((key) => {
          if (fields.indexOf(key) !== -1) {
            this.form.getFieldDecorator(key);
            let obj = {};
            if (fieldDates.indexOf(key) !== -1) {
              if (xxbBCheck[key] !== null && xxbBCheck[key] !== "") {
                obj[key] = moment(xxbBCheck[key]);
                this.form.setFieldsValue(obj);
              } else {
                obj[key] = "";
              }
            } else {
              if(key=='province'){
                this.xxbBCheck.province= JSON.parse(xxbBCheck[key])
                this.defaultValue= JSON.parse(xxbBCheck[key])
              }
              else{
              obj[key] = xxbBCheck[key];
              }
              this.form.setFieldsValue(obj);
            }
          }
        });
        this.xxbBCheck.fileId = xxbBCheck.fileId;
        this.xxbBCheck.fileUrl = xxbBCheck.fileUrl;
        this.xxbBCheck.id = xxbBCheck.id;
      }

      setTimeout(() => {
        this.$refs.xxbBCheckDList.setFieldValues(
          xxbBCheck == null ? null : xxbBCheck.id,
          this.isEdit
        );
        if (xxbBCheck !== null) {
          this.$refs.fileagent.setForm(xxbBCheck.fileId);
        }
      }, 200);
    },
    handleSubmit(type) {
      let btn = type == 0 ? "保存" : "提交";
      this.form.validateFields((err, values) => {
        if (!err) {
          let xxbBCheck = this.form.getFieldsValue();
          xxbBCheck.id = this.xxbBCheck.id;
          xxbBCheck.fileId = this.xxbBCheck.fileId;
          xxbBCheck.fileUrl = this.xxbBCheck.fileUrl;
          xxbBCheck.province = this.xxbBCheck.province
          xxbBCheck.checkDataList = this.$refs.xxbBCheckDList.getFieldValues();
          this.$put("mdlBForeign/editInfo", {
            data: JSON.stringify(xxbBCheck),
          })
            .then((r) => {
              if (r.data.data.success === 1) {
                this.isUpdate = true;

                if (type == 1) {
                  this.reset();
                  this.$emit("success");
                } else {
                  this.$refs.xxbBCheckDList.setFieldValues(
                    this.xxbBCheck.id,
                    this.isEdit
                  );
                  this.$message.success(btn + "数据成功.");
                }
              } else {
                this.$message.error(btn + "数据失败.");
              }
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
<style lang="less" scoped>
.formtab {
  width: 100%;
  padding: 5px;
}
.formtab {
  tr td {
    padding: 5px;
    border: 1px solid #e8e8e8;
    color: rgba(0, 0, 0, 0.85);
  }
  .tdRight {
    text-align: right;
  }
  .tdTitle {
    border: 0px;
    text-align: center;
  }
}
.fontColor {
  color: #f5222d;
}
</style>
