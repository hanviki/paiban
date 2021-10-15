<template>
  <a-drawer
    title="新增申请资质"
    :maskClosable="false"
    width="900"
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="addVisiable"
    style="height: calc(100% - 55px); overflow: auto; padding-bottom: 53px"
  >
    <a-form layout="horizontal">
      <div>
        <a-row>
          <a-col :sm="20">
            <a-form-item
              label="人事编号"
              v-bind="{ labelCol: { span: 5 }, wrapperCol: { span: 13 } }"
            >
              <a-input v-model="queryParams.userAccount" />
            </a-form-item>
          </a-col>
          <a-col :sm="4">
            <span style="float: right; margin-top: 3px; width: 60px">
              <a-button type="primary" @click="search">查询</a-button>
            </span>
          </a-col>
        </a-row>
      </div>
    </a-form>
    <a-form :form="form">
      <a-row>
        <a-col :sm="8">
          <a-form-item v-bind="formItemLayout" label="人事编号">
            <a-input
              placeholder="请输入人事编号"
              :disabled="true"
              v-decorator="[
                'userAccount',
                { rules: [{ required: true, message: '人事编号不能为空' }] },
              ]"
            />
          </a-form-item>
        </a-col>
        <a-col :sm="8">
          <a-form-item v-bind="formItemLayout" label="姓名">
            <a-input
              placeholder="请输入姓名"
              :disabled="true"
              v-decorator="[
                'userAccountName',
                { rules: [{ required: true, message: '姓名不能为空' }] },
              ]"
            />
          </a-form-item>
        </a-col>
        <a-col :sm="8">
          <a-form-item v-bind="formItemLayout" label="科室">
            <a-input
              placeholder="请输入科室"
              :disabled="true"
              v-decorator="[
                'deptName',
                { rules: [{ required: true, message: '科室不能为空' }] },
              ]"
            />
          </a-form-item>
        </a-col>
        <a-col :sm="8">
          <a-form-item v-bind="formItemLayout" label="病区">
            <a-input
              placeholder="请输入病区"
              :disabled="true"
              v-decorator="[
                'bqName',
                { rules: [{ required: true, message: '病区不能为空' }] },
              ]"
            />
          </a-form-item>
        </a-col>
        <a-col :sm="8">
          <a-form-item v-bind="formItemLayout" label="科室ID">
            <a-input
              placeholder="请输入科室ID"
              :disabled="true"
              v-decorator="[
                'deptId',
                { rules: [{ required: true, message: '科室ID不能为空' }] },
              ]"
            />
          </a-form-item>
        </a-col>
        <a-col :sm="8">
          <a-form-item v-bind="formItemLayout" label="申请日期">
            <a-date-picker
            :disabled="true"
              v-decorator="[
                'applyDate',
                { rules: [{ required: true, message: '申请日期不能为空' }] },
              ]"
            />
          </a-form-item>
        </a-col>
        <a-col :sm="8">
          <a-form-item v-bind="formItemLayout" label="申请资质类型">
            <a-select style="width: 100%"  >
              <a-select-option value="普通申请资质">
                普通申请资质
              </a-select-option>
              <a-select-option value="医师申请资质">
                医师申请资质
              </a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :sm="8">
          <a-form-item v-bind="formItemLayout" label="申请资质">
             <a-select style="width: 100%" v-decorator="['userType']">
              <a-select-option v-for="d in data" :key="d.id" :value="d.id">
                {{ d.muduleName }}
              </a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :sm="24">
          <a-form-item v-bind="{ labelCol: { span: 3 }, wrapperCol: { span: 21 } }" label="申请理由">
           <a-textarea
      v-decorator="['note']"
      placeholder="请填写申请理由"
      :auto-size="{ minRows: 5 }"
    />
          </a-form-item>
        </a-col>
      </a-row>
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
  labelCol: { span: 8 },
  wrapperCol: { span: 15 },
};
export default {
  name: "SdlBZizhiapplyAdd",
  props: {
    addVisiable: {
      default: false,
    },
  },
  data() {
    return {
      loading: false,
      formItemLayout,
      queryParams: {
        userAccount: "",
      },
      form: this.$form.createForm(this),
      sdlBZizhiapply: {},
      data: []
    };
  },
  watch: {
    addVisiable() {
      if(this.addVisiable){
         this.$get("sdlDZizhi").then(res=>{
           console.info(res.data.rows)
           this.data= res.data.rows
         })
      }
    }
      
  },
  methods: {
    reset() {
      this.loading = false;
      this.sdlBZizhiapply = {};
      this.form.resetFields();
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },
    search() {
      this.$get("sdlBUser", {
        ...this.queryParams,
      }).then((r) => {
        let data = r.data;
        if (data.rows.length == 1) {
          this.setFormValues(data.rows[0]);
        }
      });
    },
    setFormValues({ ...sdlBUser }) {
      let fields = [
        "userAccount",
        "userAccountName",
        "bqName",
        "userType",
        "deptId",
        "telephone",
        "deptName",
        "positionName",
        "npPositionName",
        "sexName",
        "birthday",
        "schoolDate",
        "zyjsgw",
        "zyjsgwLc",
        "xcszyjzc",
        "appointedDate",
        "patentRanknum",
        "isAuthority",
        "fileId",
        "isZhuanrang",
        "fileUrl",
        "patentGood",
        "auditSuggestion",
        "appointedDateLc",
        "fileIdLc",
        "fileUrlLc",
        "yuangongzu",
        "xrgwjb",
        "xrgwjbprsj",
        "djrdzzw",
        "isChujikh",
        "chujikhDate",
        "isZhongjikh",
        "zhongjikhDate",
        "politicalStatus",
        "staffGrade",
        "idCard",
        "staffDate",
        "pictureId",
        "pictureUrl",
        "gqpxqk",
        "gfhyspxqk",
        "zjspnlceqk",
        "dcaYear",
      ];
      let fieldDates = [];
      Object.keys(sdlBUser).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key);
          let obj = {};
          if (fieldDates.indexOf(key) !== -1) {
            if (sdlBUser[key] !== "") {
              obj[key] = moment(sdlBUser[key]);
            } else {
              obj[key] = "";
            }
          } else {
            obj[key] = sdlBUser[key];
          }
          this.form.setFieldsValue(obj);
        }
      });
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          this.setFields();
          this.$post("sdlBZizhiapply", {
            ...this.sdlBZizhiapply,
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
        "rylxId",
        "rylxName",
        "deptName",
        "bqName",
        "deptId",
        "applyDate",
        "applyType",
        "schoolDate",
        "positionName",
        "positionPrdate",
        "orignType",
        "newType",
        "note",
        "auditDate",
      ]);
      if (typeof values !== "undefined") {
        Object.keys(values).forEach((_key) => {
          this.sdlBZizhiapply[_key] = values[_key];
        });
      }
    },
  },
};
</script>
