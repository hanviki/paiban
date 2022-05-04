<template>
  <a-drawer
    title="协和医院新技术新项目中期反馈表"
    :maskClosable="false"
    width="80%"
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="editVisiable"
    style="height: calc(100% - 55px); overflow: auto; padding-bottom: 53px"
  >
    <a-form :form="form">
      <a-row><a-col :span="24" style="font-size:20px;font-weight:bold;text-align:center;">协和医院新技术新项目中期反馈表</a-col></a-row>
       <a-row style="line-height:60px;"><a-col :span="4" style="border: 1px solid #e8e8e8;">
            项目名称
      </a-col><a-col :span="20" style="border: 1px solid #e8e8e8;">
            {{projectName}}
      </a-col></a-row>
       <a-row style="line-height:60px;"><a-col :span="4" style="border: 1px solid #e8e8e8;">
            项目类型
      </a-col><a-col :span="20" style="border: 1px solid #e8e8e8;">
           <a-radio-group :value="projectType">
              <a-radio :value="1">手术类</a-radio>
              <a-radio :value="2">治疗操作类</a-radio>
              <a-radio :value="0">检验检查类</a-radio>
              <a-radio :value="3">其他类</a-radio>
           </a-radio-group>
      </a-col></a-row>
      <a-row style="line-height:60px;"><a-col :span="4" style="border: 1px solid #e8e8e8;">
            申报科室
      </a-col><a-col :span="8" style="border: 1px solid #e8e8e8;">
           {{deptNew}}
      </a-col><a-col :span="4" style="border: 1px solid #e8e8e8;">
            项目负责人
      </a-col><a-col :span="8" style="border: 1px solid #e8e8e8;">
            {{userAccountName}}
      </a-col></a-row>
       <a-row style="line-height:40px;"><a-col :span="24" style="border: 1px solid #e8e8e8;font-size:20px;;text-align:center;">开   展   情   况</a-col></a-row>
      <a-row style="line-height:60px;">
          <a-col :span="4" style="border: 1px solid #e8e8e8;">
            开展总例数
      </a-col>
        <a-col :span="20" style="border: 1px solid #e8e8e8;height:61px;">
       <a-form-item>  <a-input-number
        style="width:100%"
          placeholder="请输入开展例数"
          :precision="0"
          v-decorator="[
            'kzls',
            { rules: [{ required: true, message: '开展例数不能为空' }] },
          ]"
        /></a-form-item>
      </a-col></a-row>
      <a-row style="line-height:60px;">
       <a-col :span="4" style="border: 1px solid #e8e8e8;">开展情况明细附件</a-col>
       <a-col :span="20" style="border: 1px solid #e8e8e8;height:62px;">
        <upload-single-file
              ref="fileagent"
 @uploadRemove="removeAgent_1"
 @uploadSuc="uploadAgent_1"
 >
</upload-single-file>
       </a-col></a-row>
<a-row >
       <a-col :span="4" style="border: 1px solid #e8e8e8;line-height:88px;">适应症掌握情况
</a-col>
 <a-col :span="8" style="border: 1px solid #e8e8e8;line-height:80px;">
    <a-form-item v-bind="formItemLayout" label="主要适应症">
        <a-input
          placeholder="请输入主要适应症"
          v-decorator="[
            'zysyz',
            { rules: [{ required: true, message: '主要适应症不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="是否严格掌握适应症">
          <a-radio-group name="radioGroup"  v-decorator="[
            'isYgzwsyz',
            {
              rules: [
                { required: true, message: '是否严格掌握适应症不能为空' },
              ],
            },
          ]">
                      <a-radio value="是"> 是 </a-radio>
                      <a-radio value="否"> 否 </a-radio>
                    </a-radio-group>
       
      </a-form-item>
</a-col>
 <a-col :span="4" style="border: 1px solid #e8e8e8;line-height:88px;">禁忌症掌握情况
</a-col>
<a-col :span="8" style="border: 1px solid #e8e8e8; line-height:80px;">
      <a-form-item v-bind="formItemLayout" label="主要禁忌症">
        <a-input
          placeholder="请输入主要禁忌症"
          v-decorator="[
            'zyjjz',
            { rules: [{ required: true, message: '主要禁忌症不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="是否严格掌握禁忌症">
          <a-radio-group name="radioGroup"  v-decorator="[
            'isYgzwjjz',
            {
              rules: [
                { required: true, message: '是否严格掌握禁忌症不能为空' },
              ],
            },
          ]">
                      <a-radio value="是"> 是 </a-radio>
                      <a-radio value="否"> 否 </a-radio>
                    </a-radio-group>
       
      </a-form-item>
</a-col>
</a-row>
<a-row style="line-height:40px;"> <a-col :span="24" style="border: 1px solid #e8e8e8;font-size:20px;text-align:center;">主   要   成   果
</a-col></a-row>
<a-row style="line-height:60px;"><a-col :span="4" style="border: 1px solid #e8e8e8;">临床应用效果
</a-col>
<a-col :span="20" style="border: 1px solid #e8e8e8;">
    <a-row style="height:60px;"><a-col :sm="8">
      <a-form-item v-bind="formItemLayout" label="治愈几例">
        <a-input-number
         :precision="0"
         style="width:100%"
          placeholder="请输入治愈几例"
          v-decorator="[
            'zy',
            { rules: [{ required: true, message: '治愈几例不能为空' }] },
          ]"
        />
      </a-form-item>
      </a-col>
      <a-col :sm="8">
      <a-form-item v-bind="formItemLayout" label="好转几例">
        <a-input-number
        style="width:100%"
          :precision="0"
          placeholder="请输入好转几例"
          v-decorator="[
            'hz',
            { rules: [{ required: true, message: '好转几例不能为空' }] },
          ]"
        />
      </a-form-item>
       </a-col>
      <a-col :sm="8">
      <a-form-item v-bind="formItemLayout" label="治愈好转率">
        <a-input
          placeholder="请输入治愈好转率"
          v-decorator="[
            'zyPercent',
            { rules: [{ required: true, message: '治愈好转率不能为空' }] },
          ]"
        />
      </a-form-item>
      </a-col>
    </a-row>
</a-col>
      </a-row>
      <a-row style="line-height:100px;"> <a-col :span="4" style="border: 1px solid #e8e8e8;">经济效益与社会效益
</a-col>
      <a-col :span="20" style="border: 1px solid #e8e8e8;">
       <a-form-item> <a-textarea
         :rows="4"
          placeholder="请输入经济效益与社会效益"
          v-decorator="[
            'economic',
            {
              rules: [
                { required: true, message: '经济效益与社会效益不能为空' },
              ],
            },
          ]"
        /></a-form-item>
        </a-col>
      </a-row>
      <a-row style="line-height:40px;"> <a-col :span="24" style="border: 1px solid #e8e8e8;font-size:20px;text-align:center;">目  前  问  题  及  改  进  措  施
</a-col></a-row>
<a-row style="line-height:100px;"><a-col :span="4" style="border: 1px solid #e8e8e8;">不良反应或并发症
</a-col>
<a-col :span="20" >
    <a-row>
        <a-col :span="4">
   <a-form-item> <a-radio-group name="radioGroup" @change="syzChange"  v-decorator="[
            'isBadresult',
            {
              rules: [
                { required: true, message: '是否不良反应或并发症不能为空' },
              ],
            },
          ]">
                      <a-radio value="无"> 无 </a-radio>
                      <a-radio value="有"> 有 </a-radio>
                    </a-radio-group></a-form-item>
        </a-col>
      
      <template v-if="isShow">
            <a-col :span="4">
      <a-form-item>  <a-input-number
           style="width:100%"
          :precision="0"
          placeholder="请输入几例"
          v-decorator="[
            'bad'
          ]"
        /></a-form-item></a-col>
         <a-col :span="15" :offset="1">
     <a-form-item>   <a-textarea
          :rows="4"
          placeholder="请输入不良反应或并发症主要内容"
          v-decorator="[
            'badresult',
          ]"
        /></a-form-item>
         </a-col>
  
      </template>
      </a-row>
      </a-col></a-row>
      <a-row style="line-height:100px;"><a-col :span="4" style="border: 1px solid #e8e8e8;">拟采取的改进措施
</a-col>
      <a-col :span="20" style="border: 1px solid #e8e8e8;">
      <a-form-item>  <a-textarea
         :rows="4"
          placeholder="请输入拟采取的改进措施"
          v-decorator="[
            'cs',
            {
              rules: [{ required: true, message: '拟采取的改进措施不能为空' }],
            },
          ]"
        /></a-form-item>
      </a-col></a-row>
       <a-row style="line-height:100px;">
         <a-col :span="4" style="border: 1px solid #e8e8e8;">审核意见
</a-col>
        <a-col :span="20">
   <a-form-item><a-textarea
          disabled
         :rows="4"
          v-decorator="[
            'auditSuggestion',
            
          ]"
        /></a-form-item>
        </a-col></a-row>
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
       <a-button @click="handleSubmit(0)" type="primary" :loading="loading"
        >保存</a-button>
      <a-button @click="handleSubmit(1)" type="primary" :loading="loading"
        >提交</a-button>
      
    </div>
  </a-drawer>
</template>
<script>
import moment from "moment";
import UploadSingleFile from "../../common/uploadSingleExcelFile";

const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 16 },
};
export default {
  name: "XxbBZqEdit",
  props: {
    editVisiable: {
      default: false,
    },
    baseId: {
      default: "",
    },
  },
  components: { UploadSingleFile },
  data() {
    return {
      loading: false,
      formItemLayout,
      form: this.$form.createForm(this),
      xxbBZq: {
        id: "",
        baseId: this.baseId,
      },
      isShow: false,
      projectName: '',
      deptNew: '',
      projectType: '',
      userAccountName: ''
    };
  },
  watch: {
    editVisiable() {
      if (this.editVisiable) {
        this.fetch(this.baseId);
      }
    },
  },
  methods: {
    reset() {
      this.loading = false;
      this.$refs.fileagent.reset();
      this.form.resetFields();
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },
    syzChange(e) {
      if (e!=null && e.target.value == "有") {
       this.isShow=true
      } else {
        this.isShow= false
      }
    },
    uploadAgent_1(fileId, fileUrl) {
      this.xxbBZq.fileId = fileId;
      this.xxbBZq.fileUrl = fileUrl;
    },
    removeAgent_1() {
      this.xxbBZq.fileId = "";
      this.xxbBZq.fileUrl = "";
    },
    setProject({...project}){
      this.projectName= project.projectName
      this.projectType = project.projectType
      this.deptNew = project.deptNew
      this.userAccountName= project.userAccountName
    },
    setFormValues({ ...xxbBZq }) {
      let fields = [
        "kzls",

        "zysyz",
        "isYgzwsyz",
        "zyjjz",
        "isYgzwjjz",
        "zy",
        "hz",
        "zyPercent",
        "economic",
        "isBadresult",
        "bad",
        "badresult",
        "cs",
        "auditSuggestion"
      ];
      let fieldDates = [];
      Object.keys(xxbBZq).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key);
          let obj = {};
          if (fieldDates.indexOf(key) !== -1) {
            if (xxbBZq[key] !== null && xxbBZq[key] !== "") {
              obj[key] = moment(xxbBZq[key]);
              this.form.setFieldsValue(obj);
            } else {
              obj[key] = "";
            }
          } else {
            obj[key] = xxbBZq[key];
            this.form.setFieldsValue(obj);
          }
        }
      });
      this.xxbBZq.fileId = xxbBZq.fileId;
      this.xxbBZq.fileUrl = xxbBZq.fileUrl;
      this.xxbBZq.id = xxbBZq.id;
      this.xxbBZq.baseId = xxbBZq.baseId;
      let that = this;
      setTimeout(() => {
        that.$refs.fileagent.setForm(xxbBZq.fileId);
      }, 100);
    },
    handleSubmit(state) {
      this.form.validateFields((err, values) => {
        if (!err) {
          let xxbBZq = this.form.getFieldsValue();
           console.info(xxbBZq)
          xxbBZq.fileId = this.xxbBZq.fileId;
          xxbBZq.fileUrl = this.xxbBZq.fileUrl;
          xxbBZq.id = this.xxbBZq.id;
          xxbBZq.baseId = this.baseId;
          this.$post("xxbBZq", {
            ...xxbBZq,
            state: state,
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

    fetch() {
      this.$get("xxbBZq", {
        baseId: this.baseId,
      }).then((r) => {
        let data = r.data;
        if (data.rows.length > 0) {
          var xxbBZq = data.rows[0];
          if(xxbBZq.isYgzwjjz=='有'){ 
              this.isShow =true
              }
          this.setFormValues(xxbBZq);
        }
      });
    },
  },
};
</script>
