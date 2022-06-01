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
      <a-row type="flex" justify="center">
        <b>
          <h2>新技术新业务成果奖申报书 </h2>
        </b>
      </a-row>
      <div style="margin: 5px 0px">
        <a-row type="flex" justify="center">
          <a-col :span="colSpan">
            <table class="formtab">
              <tr>
                <td class="tdRight" style="width: 13%">
                  <font class="fontColor">*</font>科室：
                </td>
                <td style="width: 25%">
                  <a-form-item>
                    <a-select
                      :disabled="!isDeptNew"
                      option-filter-prop="children"
                      placeholder="请选择科室"
                      v-decorator="[
                        'deptNew',
                        {
                          rules: [
                            { required: true, message: '科室不能为空' },
                          ],
                        },
                      ]"
                      show-search
                    >
                      <a-select-option
                        v-for="d in deptData"
                        :key="d.deptId"
                        :value="`${d.deptId}`"
                      >
                        {{ d.deptName }}
                      </a-select-option>
                    </a-select>
                  </a-form-item>
                </td>
                <td class="tdRight" style="width: 10%">
                  <font class="fontColor">*</font>项目名称：
                </td>
                <td colspan="3" style="width: 52%">
                  <a-form-item>
                    <a-row>
                      <a-col :span="19">
                    <a-input
                      placeholder="请输入项目名称"
                      :maxLength="txtLength50"
                      v-decorator="[
                        'projectName',
                        {
                          rules: [
                            { required: true, message: '项目名称不能为空' },
                          ],
                        },
                      ]"
                    />
                    </a-col>
                    <a-col :span="1">
                      &nbsp;
                    </a-col>
                    <a-col :span="4">
                  <a-button @click="selectProject" v-show="drawerTitle !='查看' && xxbBResult.state == 0 ? true: false">
                    选择项目
                  </a-button>
                  </a-col>
                  </a-row>
                  </a-form-item>
                </td>
              </tr>
              <tr>
                <td class="tdRight" style="width: 13%">
                  <font class="fontColor">*</font>
                  申报日期：
                </td>
                <td style="width: 25%">
                  <a-form-item>
                    <a-date-picker
                      v-decorator="[
                        'applydat',
                        {
                          rules: [
                            { required: true, message: '申报日期不能为空' },
                          ],
                        },
                      ]"
                    />
                  </a-form-item>
                </td>
                <td class="tdRight" style="width: 10%">
                  <font class="fontColor">*</font>开展时间：
                </td>
                <td style="width: 20%">
                  <a-form-item>
                    <a-date-picker
                      v-decorator="[
                        'kzsrtdat',
                        {
                          rules: [
                            { required: true, message: '开展时间不能为空' },
                          ],
                        },
                      ]"
                    />
                  </a-form-item>
                </td>
                <td class="tdRight" style="width: 12%">
                  <font class="fontColor">*</font>开展例数：
                </td>
                <td style="width: 20%">
                  <a-form-item>
                    <a-input-number
                      :min="0"
                      :step="1"
                      v-decorator="[
                        'kzls',
                        {
                          rules: [
                            { required: true, message: '开展例数不能为空' },
                          ],
                        },
                      ]"
                    >
                    </a-input-number>
                  </a-form-item>
                </td>
              </tr>
            </table>
          </a-col>
        </a-row>
      </div>
      <!-- 项  目  负  责  人 -->
      <a-row type="flex" justify="center">
        <b>
          <h2>项 目 负 责 人</h2>
        </b>
      </a-row>
      <div style="margin: 5px 0px">
        <a-row type="flex" justify="center">
          <a-col :span="colSpan">
            <table class="formtab">
              <tr>
                <td class="tdRight" style="width: 13%">
                  <font class="fontColor">*</font>所在院区：
                </td>
                <td style="width: 25%">
                  <a-form-item>
                    <a-select
                      placeholder="请选择所在院区"
                      v-decorator="[
                        'yuanqu',
                        {
                          rules: [
                            { required: true, message: '所在院区不能为空' },
                          ],
                        },
                      ]"
                    >
                      <a-select-option key="本院" value="本院">
                        本院
                      </a-select-option>
                      <a-select-option key="西院" value="西院">
                        西院
                      </a-select-option>
                      <a-select-option key="肿瘤" value="肿瘤">
                        肿瘤
                      </a-select-option>
                      <a-select-option key="金银湖" value="金银湖">
                        金银湖
                      </a-select-option>
                    </a-select>
                  </a-form-item>
                </td>
                <td colspan="4"></td>
              </tr>
              <tr>
                <td class="tdRight" style="width: 13%">
                  <font class="fontColor">*</font>负责人：
                </td>
                <td style="width: 25%">
                  <a-form-item>
                    <select-user-remote
                      ref="user1"
                      v-decorator="[
                        'userAccountName',
                        {
                          rules: [{ required: true, message: '主要负责人不能为空' }],
                        },
                      ]"
                      @userChang="userChange1"
                    >
                    </select-user-remote>
                  </a-form-item>
                </td>
                <td class="tdRight" style="width: 10%">
                  <font class="fontColor">(只读)</font>性别：
                </td>
                <td style="width: 20%">
                  <a-form-item>
                    <a-input
                      placeholder="请输入性别"
                      readOnly
                      v-decorator="['sexName']"
                    />
                  </a-form-item>
                </td>
                <td class="tdRight" style="width: 12%">
                  <font class="fontColor">(只读)</font>出生年月：
                </td>
                <td style="width: 20%">
                  <a-form-item>
                    <a-input
                      placeholder="出生年月"
                      readOnly
                      v-decorator="['birthday']"
                    />
                  </a-form-item>
                </td>
              </tr>
              <tr>
                <td class="tdRight">
                  <font class="fontColor">(只读)</font>学历、学位：
                </td>
                <td>
                  <a-form-item>
                    <a-input
                      placeholder="请输入学历、学位"
                      readOnly
                      v-decorator="['edu']"
                    />
                  </a-form-item>
                </td>
                <td class="tdRight">
                  <font class="fontColor">(只读)</font>职称：
                </td>
                <td>
                  <a-form-item>
                    <a-input
                      placeholder="请输入临床职称"
                      readOnly
                      v-decorator="['zhichenglc']"
                    />
                  </a-form-item>
                </td>
                <td class="tdRight">
                  <font class="fontColor"></font>手机号码：
                </td>
                <td>
                  <a-form-item>
                    <a-input
                      placeholder="请输入手机号码"
                      :maxLength="txtLength30"
                      v-decorator="['telephone']"
                    />
                  </a-form-item>
                </td>
              </tr>
            </table>
          </a-col>
        </a-row>
      </div>
      <!-- 项目主要人员 -->
      <a-row type="flex" justify="center">
        <b>
          <h2>项目主要人员</h2>
        </b>
      </a-row>
      <!-- border:1px solid #2894FF; -->
      <div style="margin: 5px 0px">
        <a-row type="flex" justify="center">
          <a-col :span="colSpan">
            <xxbBResult-d ref="xxbBResultDList"> </xxbBResult-d>
          </a-col>
        </a-row>
      </div>
      <!-- 项目主要内容 -->
      <a-row type="flex" justify="center">
        <b>
          <h2>项目主要内容</h2>
        </b>
      </a-row>
      <div style="margin: 5px 0px">
        <a-row type="flex" justify="center">
          <a-col :span="colSpan">
            <table class="formtab">
              <tr>
                <td colspan="2" style="width:100%"><font class="fontColor">*</font>
                <b>一、项目主要内容、目标和意义（摘要），重点是对学科的推动力：</b>
                </td>
              </tr>
              <tr>
                <td colspan="2" style="width:100%">
                  <a-form-item>
                    <a-textarea
                      :rows="8"
                      :maxLength="txtLength500"
                      placeholder="请输入项目的主要内容及意义"
                      v-decorator="[
                        'projectcontent',
                        {
                          rules: [
                            {
                              required: true,
                              message: '项目的主要内容及意义不能为空',
                            },
                          ],
                        },
                      ]"
                    />
                  </a-form-item>
                </td>
              </tr>
              <tr>
                <td colspan="2" style="width:100%"><font class="fontColor">*</font>
                <b>二、项目的科学依据（包括国内外进展、新颖性和创新性）：</b>
                </td>
              </tr>
              <tr>
                <td colspan="2" style="width:100%">
                  <a-form-item>
                    <a-textarea
                      :rows="8"
                      :maxLength="txtLength500"
                      placeholder="请输入项目的科学依据"
                      v-decorator="[
                        'projectkxyj',
                        {
                          rules: [
                            {
                              required: true,
                              message: '项目的科学依据不能为空',
                            },
                          ],
                        },
                      ]"
                    />
                  </a-form-item>
                </td>
              </tr>
              <tr>
                <td colspan="2" style="width:100%"><font class="fontColor">*</font>
                <b>三、项目采取的方法、技术路线：</b>
                </td>
              </tr>
              <tr>
                <td colspan="2" style="width:100%">
                  <a-form-item>
                    <a-textarea
                      :rows="8"
                      :maxLength="txtLength500"
                      placeholder="请输入项目采取的方法、技术路线"
                      v-decorator="[
                        'projectffjslx',
                        {
                          rules: [
                            {
                              required: true,
                              message: '项目采取的方法、技术路线不能为空',
                            },
                          ],
                        },
                      ]"
                    />
                  </a-form-item>
                </td>
              </tr>
              <tr>
                <td colspan="2" style="width:100%"><font class="fontColor">*</font>
                <b>四、项目解决的关键问题：</b>
                </td>
              </tr>
              <tr>
                <td colspan="2" style="width:100%">
                  <a-form-item>
                    <a-textarea
                      :rows="8"
                      :maxLength="txtLength500"
                      placeholder="请输入项目解决的关键问题"
                      v-decorator="[
                        'projectkey',
                        {
                          rules: [
                            {
                              required: true,
                              message: '项目解决的关键问题不能为空',
                            },
                          ],
                        },
                      ]"
                    />
                  </a-form-item>
                </td>
              </tr>
              <tr>
                <td colspan="2" style="width:100%"><font class="fontColor">*</font>
                <b>五、技术存在的主要风险及预案：</b>
                </td>
              </tr>
              <tr>
                <td colspan="2" style="width:100%">
                  <a-form-item>
                    <a-textarea
                      :rows="8"
                      :maxLength="txtLength500"
                      placeholder="请输入技术存在的主要风险"
                      v-decorator="[
                        'jsfxya',
                        {
                          rules: [
                            {
                              required: true,
                              message: '技术存在的主要风险不能为空',
                            },
                          ],
                        },
                      ]"
                    />
                  </a-form-item>
                </td>
              </tr>
              <tr>
                <td colspan="2" style="width:100%"><font class="fontColor">*</font>
                <b>六、项目产生的经济效益、社会效益：</b>
                </td>
              </tr>
              <tr>
                <td colspan="2" style="width:100%">
                  <a-form-item>
                    <a-textarea
                      :rows="8"
                      :maxLength="txtLength500"
                      placeholder="请输入项目产生的经济效益、社会效益"
                      v-decorator="[
                        'projectjjshxy',
                        {
                          rules: [
                            {
                              required: true,
                              message: '项目产生的经济效益、社会效益不能为空',
                            },
                          ],
                        },
                      ]"
                    />
                  </a-form-item>
                </td>
              </tr>
              <tr>
                <td colspan="2" style="width:100%"><font class="fontColor">*</font>
                <b>七、主要技术文献目录及出处：</b>
                </td>
              </tr>
              <tr>
                <td colspan="2" style="width:100%">
                  <a-form-item>
                    <a-textarea
                      :rows="8"
                      :maxLength="txtLength500"
                      placeholder="请输入主要技术文献目录及出处"
                      v-decorator="[
                        'jswxmlcc',
                        {
                          rules: [
                            {
                              required: true,
                              message: '主要技术文献目录及出处不能为空',
                            },
                          ],
                        },
                      ]"
                    />
                  </a-form-item>
                </td>
              </tr>
              <tr>
                <td colspan="2" style="width:100%"><font class="fontColor">*</font>
                <b>八、新闻报道情况：</b>
                </td>
              </tr>
              <tr>
                <td colspan="2" style="width:100%">
                  <a-form-item>
                    <a-textarea
                      :rows="8"
                      :maxLength="txtLength500"
                      placeholder="请输入新闻报道情况"
                      v-decorator="[
                        'newbdqk',
                        {
                          rules: [
                            {
                              required: true,
                              message: '新闻报道情况不能为空',
                            },
                          ],
                        },
                      ]"
                    />
                  </a-form-item>
                </td>
              </tr>
              <tr>
                <td colspan="2" style="width:100%"><font class="fontColor">*</font>
                <b>九、项目开展情况：</b>
                </td>
              </tr>
              <tr>
                <td colspan="2" style="width:100%">
                  <a-form-item>
                    <a-textarea
                      :rows="8"
                      :maxLength="txtLength500"
                      placeholder="请输入项目开展情况"
                      v-decorator="[
                        'projectkzqk',
                        {
                          rules: [
                            {
                              required: true,
                              message: '项目开展情况不能为空',
                            },
                          ],
                        },
                      ]"
                    />
                  </a-form-item>
                </td>
              </tr>
              <tr>
                <td colspan="2" style="width:100%">
                  <a-form-item>
                  <a-row>
                    <a-col :span=8>
                      <upload-file
                        ref="xmkzqkFile"
                        :baseId="xxbBResult.id"
                        btnTitle="上传项目开展情况Excel"
                        :isEdit="isEdit"
                        suffix=".xlsx"
                        refTab="xxbresult_xmkzqk"
                        :isCallback="true"
                        @fileData="fileData"
                        v-decorator="[
                            'filexmkzqk',
                            {rules: [{required: isXmkzqk,message: '未上传项目开展情况Excel'}]},
                          ]"
                      >
                      </upload-file>
                    </a-col>
                    <a-col :span=4>
                      <a-button @click="downXmkzqkTemp" v-if="this.isEdit">下载项目开展情况Excel模板</a-button>
                    </a-col>
                  </a-row>
                  </a-form-item>
                </td>
              </tr>
            </table>
          </a-col>
        </a-row>
      </div>
    </a-form>
    <div style="margin: 5px 0px">
      <a-row type="flex" justify="center">
        <a-col :span="colSpan">
          <xxbBResultF-look ref="xxbBResultFLook"> </xxbBResultF-look>
        </a-col>
      </a-row>
    </div>
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
          >保存</a-button>
        <a-popconfirm
          title="确定提交数据？"
          v-if="this.isEdit"
          @confirm="handleSubmit(1)"
          okText="确定"
          cancelText="取消"
        >
          <a-button v-if="this.isEdit" type="primary" :loading="loading"
            >提交</a-button>
        </a-popconfirm>
      </a-space>
      <a-modal v-model="visibleCheckSelect" :maskClosable="false" :footer="null" title="选择项目" width="80%" @ok="handleCheckSelectOk">
        <xxbBCheck-select ref="xxbBCheckSelect" @setCheckValue="setCheckValue"></xxbBCheck-select>
      </a-modal>
    </div>
  </a-drawer>
</template>

<script>
import SelectUserRemote from "../../common/SelectXxbUserRemote";
import XxbBResultD from "./XxbBResultD";
import XxbBCheckSelect from "./XxbBCheckSelect";
import UploadFile from "../../common/UploadFile";
import XxbBResultFLook from "../XxbBResultFLook";
import moment from "moment";

const formItemLayout1 = {
  labelCol: {
    span: 7,
  },
  wrapperCol: {
    span: 14,
  },
};
const formItemLayout2 = {
  labelCol: {
    span: 11,
  },
  wrapperCol: {
    span: 10,
  },
};
const formItemLayout3 = {
  labelCol: {
    span: 5,
  },
  wrapperCol: {
    span: 17,
  },
};
const formItemLayout = {
  labelCol: {
    span: 7,
  },
  wrapperCol: {
    span: 14,
  },
};
export default {
  name: "XxbBResultEdit",
  components: {
    SelectUserRemote,
    XxbBResultD,
    UploadFile,
    XxbBResultFLook,
    XxbBCheckSelect
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
      formItemLayout1,
      formItemLayout2,
      formItemLayout3,
      form: this.$form.createForm(this),
      deptData: [],
      visibleCheckSelect: false,
      isEdit: false,
      isDeptNew: true,
      txtLength50: 50,
      txtLength500: 500,
      txtLength30: 30,
      drawerTitle: "新增",
      colSpan: 23,
      isXmkzqk: false,
      xxbBResult: {},
    };
  },
  mounted() {},
  methods: {
    reset() {
      this.loading = false;
      this.isEdit = false;
      this.visibleCheckSelect = false
      this.isDeptNew = true;
      this.xxbBResult = {};
      this.$refs.xxbBResultDList.reset();
      this.$refs.user1.reset();
      this.form.resetFields();
    },
    selectProject () {
      this.visibleCheckSelect = true
      setTimeout(() => {
        this.$refs.xxbBCheckSelect.query();
      }, 200);
    },
    setCheckValue (check) {
      this.visibleCheckSelect = false
      this.xxbBResult.deptNew = check.deptNew
      this.xxbBResult.projectName = check.projectName
      this.xxbBResult.yuanqu = check.yuanqu
      this.xxbBResult.applydat = check.applydat
      this.xxbBResult.userAccountName = check.userAccountName;
      this.xxbBResult.userAccount = check.userAccount;
      this.xxbBResult.yggh = check.yggh;
      this.xxbBResult.sexName = check.sexName;
      this.xxbBResult.birthday = check.birthday;
      this.xxbBResult.edu = check.edu;
      this.xxbBResult.zhicheng = check.zhicheng;
      this.xxbBResult.zhichenglc = check.zhichenglc;
      this.xxbBResult.userdept = check.deptNew;
      this.xxbBResult.telephone = check.telephone;
      this.form.getFieldDecorator("deptNew");
      this.form.getFieldDecorator("projectName");
      this.form.getFieldDecorator("yuanqu");
      this.form.getFieldDecorator("applydat");
      this.form.getFieldDecorator("userAccountName");
      this.form.getFieldDecorator("sexName");
      this.form.getFieldDecorator("birthday");
      this.form.getFieldDecorator("edu");
      this.form.getFieldDecorator("zhichenglc");
      this.form.getFieldDecorator("telephone");
      this.form.setFieldsValue({
        deptNew: check.deptNew,
        projectName: check.projectName,
        yuanqu: check.yuanqu,
        applydat: check.applydat,
        userAccountName: check.userAccountName,
        sexName: check.sexName,
        birthday: check.birthday,
        edu: check.edu,
        zhichenglc: check.zhichenglc,
        telephone: check.telephone,
      });
      setTimeout(() => {
        this.$refs.user1.userData = [
          {
            userAccountName: check.userAccountName,
            userAccount: check.userAccount,
            yggh: check.yggh,
          },
        ];
        this.$refs.user1.defaultValue = check.userAccount;
      }, 100);
      this.$get("xxbBResult/selectProjectUserCheckD", {
        id: check.id
      }).then((r) => {
        this.$refs.xxbBResultDList.setCheckProjectData(r.data.data.data)
      })
      .catch(() => {
        this.$message.error("获取Check明细数据失败!");
      });
    },
    handleCheckSelectOk () {
      this.visibleCheckSelect = false
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
    downXmkzqkTemp () {
      this.$download('xxbBResult/downXmkzqkTemplate', {
      }, `新技术新业务临床应用病例汇总表${new Date().getTime()}.xlsx`)
    },
    fileData (fileList) {
      if(fileList.length == 0) {
        this.isXmkzqk = true
        this.$nextTick(() => {
          this.form.validateFields(['filexmkzqk'], { force: this.isXmkzqk })
        })
        this.form.getFieldDecorator("filexmkzqk");
        this.form.setFieldsValue({
          filexmkzqk: ''
        });
      } else {
        this.isXmkzqk = false
        this.$nextTick(() => {
          this.form.validateFields(['filexmkzqk'], { force: this.isXmkzqk })
        })
        this.form.getFieldDecorator("filexmkzqk");
        this.form.setFieldsValue({
          filexmkzqk: '1'
        });
      }
    },
    fetchDept() {
      this.$get("sdlBUser/deptNew", {}).then((res) => {
        this.deptData = [];
        this.deptData.push(...res.data);
      });
    },
    getId() {
      this.$get("xxbBResult/getId", {}).then((r) => {
        this.form.getFieldDecorator("deptNew");
        let id = r.data.data;
        this.xxbBResult.id = id;
      });
    },
    getAccountInfo() {
      this.$get("sdlBUserMg/getAccountInfo").then((r) => {
        this.form.getFieldDecorator("deptNew");
        if (r.data != null) {
          this.xxbBResult.deptNew = r.data.deptNew;
        } else {
          this.xxbBResult.deptNew = "";
        }
        this.form.setFieldsValue({
          deptNew: this.xxbBResult.deptNew,
        });
      });
    },
    userChange1(value, target) {
      this.xxbBResult.userAccountName = target.userAccountName;
      this.xxbBResult.userAccount = target.userAccount;
      this.xxbBResult.yggh = target.yggh;
      this.xxbBResult.sexName = target.sexname;
      this.xxbBResult.birthday = target.birthday;
      this.xxbBResult.edu = target.edu;
      this.xxbBResult.zhicheng = target.zhicheng;
      this.xxbBResult.zhichenglc = target.zhichenglc;
      this.xxbBResult.userdept = target.deptNew;
      this.xxbBResult.telephone = target.tel;
      this.form.getFieldDecorator("userAccountName");
      this.form.getFieldDecorator("sexName");
      this.form.getFieldDecorator("birthday");
      this.form.getFieldDecorator("edu");
      this.form.getFieldDecorator("zhichenglc");
      this.form.getFieldDecorator("telephone");
      this.form.setFieldsValue({
        userAccountName: target.userAccountName,
        sexName: target.sexname,
        birthday: target.birthday,
        edu: target.edu,
        zhichenglc: target.zhichenglc,
        telephone: target.tel,
      });
    },
    setFormValues(xxbBResult, tit) {
      this.isDeptNew = true;
      this.isXmkzqk = true;
      this.visibleCheckSelect = false
      this.drawerTitle = tit;
      if (tit == "查看") {
        this.isEdit = false;
      }
      this.isUpdate = false;
      this.fetchDept();

      if (
        xxbBResult === undefined ||
        xxbBResult === null ||
        xxbBResult === ""
      ) {
        this.isEdit = true;
        this.getId();
        this.getAccountInfo();
        this.xxbBResult.state = 0;
        this.xxbBResult.applydat = moment(new Date());
        this.form.getFieldDecorator("applydat");
        this.form.getFieldDecorator("kzsrtdat");
        this.form.getFieldDecorator("yuanqu");
        this.form.setFieldsValue({
          applydat: this.xxbBResult.applydat,
          kzsrtdat: this.xxbBResult.applydat,
          yuanqu: this.xxbBResult.yuanqu,
        });
      } else {
        if (tit != "查看") {
          if (xxbBResult.state == 0 || xxbBResult.state == 3) {
            this.isEdit = true;
          } else {
            this.isEdit = false;
          }
        }
        if (xxbBResult.state != 0) {
          this.isDeptNew = false;
        }
        setTimeout(() => {
          this.$refs.user1.userData = [
            {
              userAccountName: xxbBResult.userAccountName,
              userAccount: xxbBResult.userAccount,
              yggh: xxbBResult.yggh,
            },
          ];
          this.$refs.user1.defaultValue = xxbBResult.userAccount;
        }, 100);
        let fields = [
          "deptNew",
          "projectName",
          "applydat",
          "kzsrtdat",
          "kzls",
          "yuanqu",
          "userAccountName",
          "sexName",
          "birthday",
          "edu",
          "zhichenglc",
        //   "zhiwu",
        //   "zhuany",
        //   "zhuanc",
          "telephone",
          "projectcontent",
          "projectkxyj",
          "projectffjslx",
          "projectkey",
          "jsfxya",
          "projectjjshxy",
          "jswxmlcc",
          "newbdqk",
          "projectkzqk",
          "filexmkzqk"
          
        ];
        let fieldDates = ["applydat", "kzsrtdat"];
        Object.keys(xxbBResult).forEach((key) => {
          if (fields.indexOf(key) !== -1) {
            this.form.getFieldDecorator(key);
            let obj = {};
            if (fieldDates.indexOf(key) !== -1) {
              if (xxbBResult[key] !== null && xxbBResult[key] !== "") {
                obj[key] = moment(xxbBResult[key]);
                this.form.setFieldsValue(obj);
              } else {
                obj[key] = "";
              }
            } else {
              obj[key] = xxbBResult[key];
              this.form.setFieldsValue(obj);
            }
          }
        });

        this.xxbBResult.id = xxbBResult.id;
        this.xxbBResult.state = xxbBResult.state;
        this.xxbBResult.userAccount = xxbBResult.userAccount;
        this.xxbBResult.yggh = xxbBResult.yggh;
        this.xxbBResult.userdept = xxbBResult.userdept;
      }
      setTimeout(() => {
        this.$refs.xxbBResultDList.setFieldValues(
          xxbBResult == null ? null : xxbBResult.id,
          this.isEdit
        );
        if (xxbBResult == null) {
          this.$refs.xxbBResultFLook.setFieldValues(null, 0, 0, 0, 0, 0, 0, 0, 0);
        } else {
            this.$refs.xxbBResultFLook.setFieldValues(
            xxbBResult.id, xxbBResult.state, 0,
            xxbBResult.gjlx,xxbBResult.gjxj,xxbBResult.gnlx,xxbBResult.gnxj,xxbBResult.snlx,xxbBResult.snxj
          );
        }
      }, 100);
    },
    handleSubmit(type) {
      let btn = type == 0 ? "保存" : "提交";
      this.form.validateFields((err, values) => {
        if (!err) {
          let xxbBResult = this.form.getFieldsValue();
          xxbBResult.id = this.xxbBResult.id;
          xxbBResult.state = type == 0 ? this.xxbBResult.state : 1;
          xxbBResult.flownum = type == 0 ? 0 : 1;
          xxbBResult.userAccount = this.xxbBResult.userAccount;
          xxbBResult.yggh = this.xxbBResult.yggh;
          xxbBResult.zhicheng = this.xxbBResult.zhicheng;
          xxbBResult.userdept = this.xxbBResult.userdept;
          xxbBResult.ResultDataList =
            this.$refs.xxbBResultDList.getFieldValues();
          this.$put("xxbBResult/editInfo", {
            data: JSON.stringify(xxbBResult),
          }).then((r) => {
              if (r.data.data.success === 1) {
                this.isUpdate = true;
                if (type == 1) {
                  this.reset();
                  this.$emit("success");
                } else {
                  this.$refs.xxbBResultDList.setFieldValues(
                    this.xxbBResult.id,
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
