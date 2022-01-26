<template>
  <a-drawer
    title="附件上传"
    :maskClosable="false"
    width="80%"
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="editVisiable"
    style="height: calc(100% - 55px); overflow: auto"
  >
    <a-form :form="form" :layout="formLayout">
        <a-col :sm="5"  :offset="1">
          <a-form-item v-bind="formItemLayout" label="人事编号">
            <a-input
            :disabled="true"
              v-decorator="['userAccount' ,{ rules: [{  message: '人事编号不能为空' }] },]"
            />
          </a-form-item>
        </a-col>
        <a-col :sm="5"  :offset="1">
          <a-form-item v-bind="formItemLayout" label="姓名">
            <a-input
              :disabled="true"
              v-decorator="['userAccountName']"
            />
          </a-form-item>
        </a-col>
        <a-col :sm="5"  :offset="1">
          <a-form-item v-bind="formItemLayout" label="科室">
            <a-input
              :disabled="true"
              v-decorator="['deptName']"
            />
            <a-input
              type="hidden"
              v-decorator="['deptId']"
            />
          </a-form-item>
        </a-col>

        <a-col :sm="5"  :offset="1">
          <a-form-item v-bind="formItemLayout" label="出生日期">
            <a-input
              :disabled="true"
              v-decorator="['birthday']"
            />
          </a-form-item>
        </a-col>
        <a-col :sm="5"  :offset="1">
          <a-form-item
           v-bind="formItemLayout"
            label="病区"
          >
            <a-input
              :disabled="true"
              v-decorator="['bqName']"
            />
          </a-form-item>
        </a-col>
        <a-col :sm="5"  :offset="1">
          <a-form-item v-bind="formItemLayout" label="性别">
            <a-input
              :disabled="true"
              v-decorator="['sexName']"
            />
          </a-form-item>
        </a-col>

        <a-col :sm="5"  :offset="1">
          <a-form-item v-bind="formItemLayout" label="人员类别">
            <a-input
              :disabled="true"
              v-decorator="['rylx']"
            />
          </a-form-item>
        </a-col>
         <a-col :sm="5"  :offset="1">
        <a-form-item v-bind="formItemLayout" label="员工组">
          <a-input
            :disabled="true"
            v-decorator="[
              'yuangongzu',
            ]"
          />
        </a-form-item>
      </a-col>
       <a-col :sm="5"  :offset="1">
        <a-form-item v-bind="formItemLayout" label="人事子范围">
          <a-input
          :disabled="true"
            v-decorator="[
              'renshizifw',
       
            ]"
          />
        </a-form-item>
      </a-col>
       <a-col :sm="5"  :offset="1">
        <a-form-item v-bind="formItemLayout" label="职称">
          <a-input
            :disabled="true"
            v-decorator="[
              'zhicheng',
            
            ]"
          />
        </a-form-item>
      </a-col>
      <a-col :sm="5"  :offset="1">
        <a-form-item v-bind="formItemLayout" label="临床职称">
          <a-input
          :disabled="true"
            v-decorator="[
              'zyjsgwLc',
            ]"
          />
        </a-form-item>
      </a-col>
      <a-col :sm="5"  :offset="1">
        <a-form-item v-bind="formItemLayout" label="临床聘任时间">
          <a-input
           :disabled="true"
            v-decorator="[
              'appointedDateLc',
            
            ]"
          />
        </a-form-item>
      </a-col>
 <a-col :sm="5"  :offset="1">
        <a-form-item v-bind="formItemLayout" label="教学职称">
          <a-input
            :disabled="true"
            v-decorator="[
              'zyjsgw',
            
            ]"
          />
        </a-form-item>
      </a-col>
       <a-col :sm="5"  :offset="1">
        <a-form-item v-bind="formItemLayout" label="教学聘任时间">
          <a-input
            :disabled="true"
            v-decorator="[
              'appointedDate',
             
            ]"
          />
        </a-form-item>
      </a-col>
      <a-col :sm="5"  :offset="1">
        <a-form-item v-bind="formItemLayout" label="内聘临床职务">
          <a-input
            :disabled="true"
            v-decorator="[
              'zyjsNp',
             
            ]"
          />
        </a-form-item>
      </a-col>
       <a-col :sm="5"  :offset="1">
        <a-form-item v-bind="formItemLayout" label="内聘临床时间">
          <a-input
            :disabled="true"
            v-decorator="[
              'zyjsDateNp',
             
            ]"
          />
        </a-form-item>
      </a-col>

      <a-col :sm="5"  :offset="1">
        <a-form-item v-bind="formItemLayout" label="内聘教学职务">
          <a-input
            :disabled="true"
            v-decorator="[
              'zyjsNpjx',
             
            ]"
          />
        </a-form-item>
      </a-col>
      <a-col :sm="5"  :offset="1">
        <a-form-item v-bind="formItemLayout" label="内聘教学时间">
          <a-input
            :disabled="true"
            v-decorator="[
              'zyjsDateNpjx',
            ]"
          />
        </a-form-item>
      </a-col>
      <a-col :sm="5"  :offset="1">
        <a-form-item v-bind="formItemLayout" label="学历">
          <a-input
            :disabled="true"
            v-decorator="[
              'edu',
            
            ]"
          />
        </a-form-item>
      </a-col>

       <a-col :sm="5"  :offset="1">
        <a-form-item v-bind="formItemLayout" label="来校工作时间">
          <a-input
            
            :disabled="true"
            v-decorator="[
              'schoolDate',
            ]"
          />
        </a-form-item>
      </a-col>
     <a-col :sm="5"  :offset="1">
        <a-form-item v-bind="formItemLayout" label="医师类别">
          <a-input
            
            v-decorator="[
              'yishiLb',
             
            ]"
          />
        </a-form-item>
      </a-col>
       <a-col :sm="5"  :offset="1">
        <a-form-item v-bind="formItemLayout" label="医师级别">
          <a-input
           
            v-decorator="[
              'yishiJb',
            
            ]"
          />
        </a-form-item>
      </a-col>
      <a-col :sm="5"  :offset="1">
        <a-form-item v-bind="formItemLayout" label="医师执业范围">
          <a-input
            
            v-decorator="[
              'yishiZhiyefanwei',
            
            ]"
          />
        </a-form-item>
      </a-col>
      <a-col :sm="5"  :offset="1">
        <a-form-item v-bind="formItemLayout" label="医师资格证号">
          <a-input
           
            v-decorator="[
              'yishiZgzsbianhao',
            
            ]"
          />
        </a-form-item>
      </a-col>
      <a-col :sm="5"  :offset="1">
        <a-form-item v-bind="formItemLayout" label="医师执业证号">
          <a-input
           
            v-decorator="[
              'yishiZiyebianhao',
             
            ]"
          />
        </a-form-item>
      </a-col>
     
      
      <a-col :sm="5" :offset="1">
        <a-form-item v-bind="formItemLayout" label="资格证书附件">
          <upload-single-file
            ref="fileagent1"
            @uploadRemove="removeAgent_1"
            @uploadSuc="uploadAgent_1"
          >
          </upload-single-file>
        </a-form-item>
      </a-col>
      <a-col :sm="5" :offset="1">
        <a-form-item v-bind="formItemLayout" label="执业证书附件">
          <upload-single-file
            ref="fileagent2"
            @uploadRemove="removeAgent_2"
            @uploadSuc="uploadAgent_2"
          >
          </upload-single-file>
        </a-form-item>
      </a-col>
      <a-col :sm="5" :offset="1">
        <a-form-item v-bind="formItemLayout" label="电子证照">
          <upload-single-file
            ref="fileagent3"
            @uploadRemove="removeAgent_3"
            @uploadSuc="uploadAgent_3"
          >
          </upload-single-file>
        </a-form-item>
      </a-col>
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
        >保存</a-button
      >
    </div>
  </a-drawer>
</template>
<script>
import moment from "moment";
import UploadSingleFile from "../common/uploadSingleFile";

const formItemLayout = {
  labelCol: { span: 6 },
  wrapperCol: { span: 18 },
};
export default {
  name: "SdlBUserEdit",
  props: {
    editVisiable: {
      default: false,
    },
  },
  components: { UploadSingleFile },
  computed: {
    formItemLayout() {
      const { formLayout } = this;
      return formLayout === "horizontal" ? formItemLayout : {};
    },
  },
  data() {
    return {
      loading: false,
      form: this.$form.createForm(this),
      sdlBUser: {},
      formLayout: "vertical",
    };
  },
  methods: {
    reset() {
      this.loading = false;
      this.$refs.fileagent1.reset();
        this.$refs.fileagent2.reset();
        this.$refs.fileagent3.reset();
      this.form.resetFields();
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },
    uploadAgent_1(fileId, fileUrl) {
      this.sdlBUser.fileId = fileId;
      this.sdlBUser.fileUrl = fileUrl;
    },
    removeAgent_1() {
      this.sdlBUser.fileId = "";
      this.sdlBUser.fileUrl = "";
    },
    uploadAgent_2(fileId, fileUrl) {
      this.sdlBUser.fileIdLc = fileId;
      this.sdlBUser.fileUrlLc = fileUrl;
    },
    removeAgent_2() {
      this.sdlBUser.fileIdLc = "";
      this.sdlBUser.fileUrlLc = "";
    },
    uploadAgent_3(fileId, fileUrl) {
      this.sdlBUser.pictureId = fileId;
      this.sdlBUser.pictureUrl = fileUrl;
    },
    removeAgent_3() {
      this.sdlBUser.pictureId = "";
      this.sdlBUser.pictureUrl = "";
    },
    setFormValues({ ...sdlBUser }) {
      let fields = [
        "userAccount",
        "userAccountName",
        "bqName",
        "userType",
        "userTypeName",
        "deptId",
        "telephone",
        "deptName",
        "positionName",
        "npPositionName",
        "rylx",
        "yggh",
        "ghHk",
        "sexName",
        "qualifyNo",
        "birthday",
        "schoolDate",
        "zyjsgw",
        "bianhaoJx",
        "zyjsgwLc",
        "xcszyjzc",
        "appointedDate",
        "appointedDateLc",
        "zizu",
        "yuangongzu",
        "xrgwjb",
        "xrgwjbprsj",
        "zhicheng",
        "zyjsNp",
        "zyjsDateNp",
        "zyjsNpjx",
        "zyjsDateNpjx",
        "edu",
        "eduSchool",
        "guoji",
        "minzu",
        "jiguan",
        "politicalStatus",
        "bianhaoLc",
        "xingzhengjiebie",
        "idCard",
        "workDate",
        "yishiLb",
        "yishiJb",
        "yishiZhiyefanwei",
        "yishiZgzsbianhao",
        "yishiZiyebianhao",
        "renshizifw",
        "renshizfenlei",
        "isAuthority",
        "fileId",
        "fileUrl",
        "patentGood",
        "patentRanknum",
        "auditSuggestion",
        "fileIdLc",
        "fileUrlLc",
        "djrdzzw",
        "isChujikh",
        "chujikhDate",
        "isZhongjikh",
        "zhongjikhDate",
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
      this.sdlBUser.id = sdlBUser.id;
       this.sdlBUser.fileId = sdlBUser.fileId;
      this.sdlBUser.fileUrl = sdlBUser.fileUrl;
        this.sdlBUser.fileIdLc = sdlBUser.fileIdLc;
      this.sdlBUser.fileUrlLc = sdlBUser.fileUrlLc;
        this.sdlBUser.pictureId = sdlBUser.pictureId;
      this.sdlBUser.pictureUrl = sdlBUser.pictureUrl;
      var that =this
      setTimeout(()=>{
        that.$refs.fileagent1.setForm(sdlBUser.fileId);
        that.$refs.fileagent2.setForm(sdlBUser.fileIdLc);
        that.$refs.fileagent3.setForm(sdlBUser.pictureId);
      },100)
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          let sdlBUser = {}; // this.form.getFieldsValue();
          sdlBUser.yishiLb =  this.form.getFieldValue('yishiLb')
          sdlBUser.yishiJb =  this.form.getFieldValue('yishiJb')
          sdlBUser.yishiZhiyefanwei =  this.form.getFieldValue('yishiZhiyefanwei')
          sdlBUser.yishiZgzsbianhao =  this.form.getFieldValue('yishiZgzsbianhao')
          sdlBUser.yishiZiyebianhao =  this.form.getFieldValue('yishiZiyebianhao')
          sdlBUser.id = this.sdlBUser.id;
          sdlBUser.fileIdLc = this.sdlBUser.fileIdLc;
          sdlBUser.fileUrlLc = this.sdlBUser.fileUrlLc;
          sdlBUser.pictureId = this.sdlBUser.pictureId;
          sdlBUser.pictureUrl = this.sdlBUser.pictureUrl;
          sdlBUser.fileId = this.sdlBUser.fileId;
          sdlBUser.fileUrl = this.sdlBUser.fileUrl;
          this.$put("sdlBUser", {
            ...sdlBUser,
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
