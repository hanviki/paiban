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
          <h2>{{projectType}}</h2>
        </b>
      </a-row>
      <div style="margin: 5px 0px">
        <a-row type="flex" justify="center">
          <a-col :span="colSpan">
            <table class="formtab">
              <tr>
                <td class="tdRight" style="width: 13%;"><font class="fontColor">*</font>申请科室：</td>
                <td style="width: 25%;">
                  <a-form-item>
                    <a-select
                    :disabled="!isDeptNew"
                    option-filter-prop="children"
                    placeholder="请选择申请科室"
                    v-decorator="[
                      'deptNew',
                      { rules: [{ required: true, message: '申请科室不能为空' }] },
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
                <td class="tdRight" style="width: 10%;"><font class="fontColor">*</font>项目名称：</td>
                <td colspan="3" style="width: 52%;">
                  <a-form-item>
                    <a-input
                      placeholder="请输入项目名称"
                      :maxLength="txtLength50"
                      v-decorator="[
                        'projectName',
                        { rules: [{ required: true, message: '项目名称不能为空' }] },
                      ]"
                    />
                  </a-form-item>
                </td>
              </tr>
              <tr>
                <td class="tdRight" style="width: 13%;"><font class="fontColor">*</font>
                是否为限制类医疗技术:
                </td>
                <td style="width: 25%;">
                  <a-form-item>
                    <a-radio-group name="radioGroup" v-decorator="['isxzyljs']">
                      <a-radio :value="1"> 是 </a-radio>
                      <a-radio :value="0"> 否 </a-radio>
                    </a-radio-group>
                  </a-form-item>
                </td>
                <td class="tdRight" style="width: 10%;"><font class="fontColor">*</font>项目级别：</td>
                <td style="width: 20%">
                  <a-form-item>
                    <a-select
                      option-filter-prop="children"
                      placeholder="请选择项目级别"
                      :disabled="!isEdit"
                      @change="levelChange"
                      v-decorator="[
                        'projectLevel',
                        { rules: [{ required: true, message: '项目级别不能为空' }] },
                      ]"
                      show-search
                    >
                      <a-select-option v-for="d in projectLevelData" :key="d.value">
                        {{ d.text }}
                      </a-select-option>
                    </a-select>
                  </a-form-item>
                </td>
                <td class="tdRight" style="width: 12%;"><font class="fontColor">*</font>申请日期：</td>
                <td style="width: 20%;">
                  <a-form-item>
                    <a-date-picker
                      v-decorator="[
                        'applydat',
                        { rules: [{ required: true, message: '申请日期不能为空' }] },
                      ]"
                    />
                  </a-form-item>
                </td>
              </tr>
            </table>
          </a-col>
        </a-row>
        <!-- <a-row type="flex" justify="center">
          <a-col flex="500px">
            <a-form-item v-bind="formItemLayout1" label="申请科室">
              <a-select
                option-filter-prop="children"
                placeholder="请选择申请科室"
                v-decorator="[
                  'deptNew',
                  { rules: [{ required: true, message: '申请科室不能为空' }] },
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
          </a-col>
        </a-row>
        <a-row type="flex" justify="center">
          <a-col flex="500px">
            <a-form-item v-bind="formItemLayout1" label="项目名称">
              <a-input
                placeholder="请输入项目名称"
                v-decorator="[
                  'projectName',
                  { rules: [{ required: true, message: '项目名称不能为空' }] },
                ]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row type="flex" justify="center">
          <a-col flex="500px">
            <a-form-item v-bind="formItemLayout1" label="项目级别">
              <a-select
                option-filter-prop="children"
                placeholder="请选择项目级别"
                v-decorator="[
                  'projectLevel',
                  { rules: [{ required: true, message: '项目级别不能为空' }] },
                ]"
                show-search
              >
                <a-select-option v-for="d in projectLevelData" :key="d.value">
                  {{ d.text }}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row type="flex" justify="center">
          <a-col flex="500px">
            <a-form-item v-bind="formItemLayout2" label="是否为限制类医疗技术">
              <a-radio-group name="radioGroup" v-decorator="['isxzyljs']">
                <a-radio :value="1"> 是 </a-radio>
                <a-radio :value="0"> 否 </a-radio>
              </a-radio-group>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row type="flex" justify="center">
          <a-col flex="500px">
            <a-form-item v-bind="formItemLayout1" label="申请日期">
              <a-date-picker
                v-decorator="[
                  'applydat',
                  { rules: [{ required: true, message: '申请日期不能为空' }] },
                ]"
              />
            </a-form-item>
          </a-col>
        </a-row> -->
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
                <td class="tdRight" style="width: 13%;"><font class="fontColor">*</font>姓　　名：</td>
                <td style="width: 25%;">
                  <a-form-item>
                    <select-user-remote
                      ref="user1"
                      v-decorator="[
                        'userAccountName',
                        { rules: [{ required: true, message: '姓名不能为空' }] },
                      ]"
                      @userChang="userChange1"
                    >
                    </select-user-remote>
                </a-form-item>
                </td>
                <td class="tdRight" style="width: 10%;"><font class="fontColor">(只读)</font>性别：</td>
                <td style="width: 20%;">
                  <a-form-item>
                    <a-input
                      placeholder="请输入性别"
                      readOnly
                      v-decorator="['sexName']"
                    />
                  </a-form-item>
                </td>
                <td class="tdRight" style="width: 12%;"><font class="fontColor">(只读)</font>出生年月：</td>
                <td style="width: 20%;">
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
                <td class="tdRight"><font class="fontColor">(只读)</font>学历、学位：</td>
                <td>
                  <a-form-item>
                    <a-input
                      placeholder="请输入学历、学位"
                      readOnly
                      v-decorator="['edu']"
                    />
                  </a-form-item>
                </td>
                <td class="tdRight"><font class="fontColor">(只读)</font>职称：</td>
                <td>
                  <a-form-item>
                    <a-input
                      placeholder="请输入临床职称"
                      readOnly
                      v-decorator="['zhichenglc']"
                    />
                  </a-form-item>
                </td>
                <td class="tdRight">职　　务：</td>
                <td>
                  <a-form-item>
                    <a-input placeholder="请输入职务" :maxLength="txtLength30" v-decorator="['zhiwu']" />
                  </a-form-item>
                </td>
              </tr>
              <tr>
                <td class="tdRight">专　　业：</td>
                <td>
                  <a-form-item>
                    <a-input placeholder="请输入专业" :maxLength="txtLength30" v-decorator="['zhuany']" />
                  </a-form-item>
                </td>
                <td class="tdRight">专　　长：</td>
                <td>
                  <a-form-item>
                      <a-input placeholder="请输入专长" :maxLength="txtLength30" v-decorator="['zhuanc']" />
                  </a-form-item>
                </td>
                <td class="tdRight"><font class="fontColor"></font>手机号码：</td>
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
        <!-- <a-row type="flex" justify="center">
          <a-col :span="7">
            <a-form-item v-bind="formItemLayout" label="姓　　名">
              <select-user-remote
                ref="user1"
                v-decorator="[
                  'userAccountName',
                  { rules: [{ required: true, message: '姓名不能为空' }] },
                ]"
                @userChang="userChange1"
              >
              </select-user-remote>
            </a-form-item>
          </a-col>
          <a-col :span="7">
            <a-form-item v-bind="formItemLayout" label="性　　别">
              <a-input
                placeholder="请输入性别"
                readOnly
                v-decorator="['sexName']"
              />
            </a-form-item>
          </a-col>
          <a-col :span="7">
            <a-form-item v-bind="formItemLayout" label="出生年月">
              <a-input
                placeholder="出生年月"
                readOnly
                v-decorator="['birthday']"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row type="flex" justify="center">
          <a-col :span="7">
            <a-form-item v-bind="formItemLayout" label="学历、学位">
              <a-input
                placeholder="请输入学历、学位"
                readOnly
                v-decorator="['edu']"
              />
            </a-form-item>
          </a-col>
          <a-col :span="7">
            <a-form-item v-bind="formItemLayout" label="临床职称">
              <a-input
                placeholder="请输入临床职称"
                readOnly
                v-decorator="['zhichenglc']"
              />
            </a-form-item>
          </a-col>
          <a-col :span="7">
            <a-form-item v-bind="formItemLayout" label="职　　务">
              <a-input placeholder="请输入职务" v-decorator="['zhiwu']" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row type="flex" justify="center">
          <a-col :span="7">
            <a-form-item v-bind="formItemLayout" label="专　　业">
              <a-input placeholder="请输入专业" v-decorator="['zhuany']" />
            </a-form-item>
          </a-col>
          <a-col :span="7">
            <a-form-item v-bind="formItemLayout" label="专　　长">
              <a-input placeholder="请输入专长" v-decorator="['zhuanc']" />
            </a-form-item>
          </a-col>
          <a-col :span="7">
            <a-form-item v-bind="formItemLayout" label="手机号码">
              <a-input
                placeholder="请输入手机号码"
                readOnly
                v-decorator="['telephone']"
              />
            </a-form-item>
          </a-col>
        </a-row>-->
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
            <xxbBCheck-d ref="xxbBCheckDList"> </xxbBCheck-d>
          </a-col>
        </a-row>
      </div>
      <!-- 项目第一操作者的最低职称限定 -->
      <a-row type="flex" justify="center">
        <b>
          <h2>项目第一操作者的最低职称限定</h2>
        </b>
      </a-row>
      <div style="margin: 5px 0px">
        <a-row type="flex" justify="center">
          <a-col :span="colSpan">
            <table class="formtab">
              <tr>
                <td class="tdRight" style="width:20%"><font class="fontColor">*</font>级　　别：</td>
                <td style="width:80%">
                  <a-form-item>
                  <a-radio-group name="levRadioGroup" v-decorator="['lev']">
                    <a-radio :value="1"> 正高级 </a-radio>
                    <a-radio :value="2"> 副高级以上 </a-radio>
                    <a-radio :value="3"> 中级以上 </a-radio>
                    <a-radio :value="4"> 初级以上 </a-radio>
                    <a-radio :value="5"> 其他 </a-radio>
                  </a-radio-group>
                  </a-form-item>
                </td>
              </tr>
              <tr>
                <td class="tdRight" style="width:20%"><font class="fontColor">*</font>类　　别：</td>
                <td style="width:80%">
                  <a-form-item>
                    <a-radio-group name="typRadioGroup" v-decorator="['typ']">
                      <a-radio :value="1"> 医　　师 </a-radio>
                      <a-radio :value="2"> 护　　士 </a-radio>
                      <a-radio :value="3"> 药剂人员 </a-radio>
                      <a-radio :value="4"> 技术人员 </a-radio>
                      <a-radio :value="5"> 其他 </a-radio>
                    </a-radio-group>
                  </a-form-item>
                </td>
              </tr>
              <tr>
                <td class="tdRight" style="width:20%"><font class="fontColor">*</font>项目的主要内容及意义：</td>
                <td style="width:80%">
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
                <td class="tdRight" style="width:20%"><font class="fontColor">*</font>技术临床应用安全性分析：</td>
                <td style="width:80%">
                  <a-form-item>
                    <a-textarea
                      :rows="8"
                      :maxLength="txtLength500"
                      placeholder="请输入技术临床应用安全性分析"
                      v-decorator="[
                        'lincaqfx',
                        {
                          rules: [
                            { required: true, message: '技术临床应用安全性分析不能为空' },
                          ],
                        },
                      ]"
                    />
                  </a-form-item>
                  <upload-file
                    ref="aqxfxFile"
                    :baseId="xxbBCheck.id"
                    btnTitle="上传相关佐证材料PDF"
                    :isEdit="isEdit"
                    refTab="xxbcheck_aqxfx"
                  >
                  </upload-file>
                </td>
              </tr>
              <tr>
                <td class="tdRight" style="width:20%"><font class="fontColor">*</font>技术临床应用有效性分析：</td>
                <td style="width:80%">
                  <a-form-item>
                    <a-textarea
                      :rows="8"
                      :maxLength="txtLength500"
                      placeholder="请输入技术临床应用有效性分析"
                      v-decorator="[
                        'lincyyfx',
                        {
                          rules: [
                            {
                              required: true,
                              message: '技术临床应用有效性分析不能为空',
                            },
                          ],
                        },
                      ]"
                    />
                  </a-form-item>
                  <upload-file
                    ref="yxxfxFile"
                    :baseId="xxbBCheck.id"
                    btnTitle="上传相关佐证材料PDF"
                    :isEdit="isEdit"
                    refTab="xxbcheck_yxxfx"
                  >
                  </upload-file>
                </td>
              </tr>
              <tr>
                <td class="tdRight" style="width:20%"><font class="fontColor">*</font>科室已具备的技术和<br>设备条件：</td>
                <td style="width:80%">
                  <a-form-item>
                    <a-textarea
                      :rows="8"
                      :maxLength="txtLength500"
                      placeholder="请输入科室已具备的技术和设备条件"
                      v-decorator="[
                        'lincjssb',
                        {
                          rules: [
                            {
                              required: true,
                              message: '科室已具备的技术和设备条件不能为空',
                            },
                          ],
                        },
                      ]"
                    />
                  </a-form-item>
                </td>
              </tr>
              <tr>
                <td class="tdRight" style="width:20%"><font class="fontColor">*</font>适应症：</td>
                <td style="width:80%">
                  <a-form-item>
                    <a-textarea
                      :rows="8"
                      :maxLength="txtLength500"
                      placeholder="请输入适应症"
                      v-decorator="[
                        'syz',
                        { rules: [{ required: true, message: '适应症不能为空' }] },
                      ]"
                    />
                  </a-form-item>
                </td>
              </tr>
              <tr>
                <td class="tdRight" style="width:20%"><font class="fontColor">*</font>禁忌症：</td>
                <td style="width:80%">
                  <a-form-item>
                    <a-textarea
                      :rows="8"
                      :maxLength="txtLength500"
                      placeholder="请输入禁忌症"
                      v-decorator="[
                        'jjz',
                        { rules: [{ required: true, message: '禁忌症不能为空' }] },
                      ]"
                    />
                  </a-form-item>
                </td>
              </tr>
              <tr>
                <td class="tdRight" style="width:20%"><font class="fontColor">*</font>操作规程：</td>
                <td style="width:80%">
                  <a-form-item>
                    <a-textarea
                      :rows="8"
                      :maxLength="txtLength500"
                      placeholder="请输入操作规程"
                      v-decorator="[
                        'czgz',
                        { rules: [{ required: true, message: '操作规程不能为空' }] },
                      ]"
                    />
                  </a-form-item>
                </td>
              </tr>
              <tr>
                <td class="tdRight" style="width:20%"><font v-if="isHong" class="fontColor">*(项目级别:一级、二级必须上传PDF)</font>项目查新报告：</td>
                <td style="width:80%">
                  <a-form-item>
                    <upload-file
                      ref="xmcxbgFile"
                      :baseId="xxbBCheck.id"
                      btnTitle="上传项目查新报告PDF"
                      :isEdit="isEdit"
                      :isCallback="true"
                      @fileData="fileData"
                      refTab="xxbcheck_xmcxbg"
                      v-decorator="[
                          'filexmcxbg',
                          {rules: [{required: isCxbg,message: '未上传项目查新报告PDF'}]},
                        ]"
                    >
                    </upload-file>
                  </a-form-item>
                </td>
              </tr>
              <tr>
                <td class="tdRight" style="width:20%"><font class="fontColor">*</font>新技术新业务临床应用<br>知情同意书：</td>
                <td style="width:80%">
                  <a-form-item>
                  <upload-file
                    ref="lcyyzqtysFile"
                    :baseId="xxbBCheck.id"
                    btnTitle="上传新技术新业务临床应用知情同意书PDF"
                    :isEdit="isEdit"
                    refTab="xxbcheck_lcyyzqtys"
                    :isCallback="true"
                    @fileData="fileZqsData"
                    v-decorator="[
                        'filezqs',
                        {rules: [{required: isZqs,message: '未上传新技术新业务临床应用知情同意书PDF'}]},
                      ]"
                  >
                  </upload-file>
                  </a-form-item>
                </td>
              </tr>
            </table>
          </a-col>
        </a-row>
        
  <!--  <a-row type="flex" justify="center">
          <a-col :span="colSpan">
            <a-form-item v-bind="formItemLayout3" label="级　　别">
              <a-radio-group name="levRadioGroup" v-decorator="['lev']">
                <a-radio :value="1"> 正高级 </a-radio>
                <a-radio :value="2"> 副高级以上 </a-radio>
                <a-radio :value="3"> 中级以上 </a-radio>
                <a-radio :value="4"> 初级以上 </a-radio>
                <a-radio :value="5"> 其他 </a-radio>
              </a-radio-group>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row type="flex" justify="center">
          <a-col :span="colSpan">
            <a-form-item v-bind="formItemLayout3" label="类　　别">
              <a-radio-group name="typRadioGroup" v-decorator="['typ']">
                <a-radio :value="1"> 医 师 </a-radio>
                <a-radio :value="2"> 护 士 </a-radio>
                <a-radio :value="3"> 药剂人员 </a-radio>
                <a-radio :value="4"> 技术人员 </a-radio>
                <a-radio :value="5"> 其他 </a-radio>
              </a-radio-group>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row type="flex" justify="center">
          <a-col :span="colSpan">
            <a-form-item v-bind="formItemLayout3" label="项目的主要内容及意义">
              <a-textarea
                :rows="8"
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
          </a-col>
        </a-row>
        <a-row type="flex" justify="center">
          <a-col :span="colSpan">
            <a-form-item v-bind="formItemLayout3" label="临床应用安全性分析">
              <a-textarea
                :rows="8"
                placeholder="请输入临床应用安全性分析"
                v-decorator="[
                  'lincaqfx',
                  {
                    rules: [
                      { required: true, message: '临床应用安全性分析不能为空' },
                    ],
                  },
                ]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row type="flex" justify="center" style="margin-bottom: 10px">
           <a-col :span="10">
            <upload-file
              ref="aqxfxFile"
              :baseId="xxbBCheck.id"
              :isEdit="isEdit"
              refTab="xxbcheck_aqxfx"
            >
            </upload-file>
           </a-col>
        </a-row>
        <a-row type="flex" justify="center">
          <a-col :span="colSpan">
            <a-form-item
              v-bind="formItemLayout3"
              label="技术临床应用有效性分析"
            >
              <a-textarea
                :rows="8"
                placeholder="请输入技术临床应用有效性分析"
                v-decorator="[
                  'lincyyfx',
                  {
                    rules: [
                      {
                        required: true,
                        message: '技术临床应用有效性分析不能为空',
                      },
                    ],
                  },
                ]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row type="flex" justify="center" style="margin-bottom: 10px">
          <a-col :span="10">
            <upload-file
              ref="yxxfxFile"
              :baseId="xxbBCheck.id"
              :isEdit="isEdit"
              refTab="xxbcheck_yxxfx"
            >
            </upload-file>
          </a-col>
        </a-row>
        <a-row type="flex" justify="center">
          <a-col :span="colSpan">
            <a-form-item
              v-bind="formItemLayout3"
              label="科室已具备的技术和设备条件"
            >
              <a-textarea
                :rows="4"
                placeholder="请输入科室已具备的技术和设备条件"
                v-decorator="[
                  'lincjssb',
                  {
                    rules: [
                      {
                        required: true,
                        message: '科室已具备的技术和设备条件不能为空',
                      },
                    ],
                  },
                ]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row type="flex" justify="center">
          <a-col :span="colSpan">
            <a-form-item v-bind="formItemLayout3" label="适应症">
              <a-textarea
                :rows="5"
                placeholder="请输入适应症"
                v-decorator="[
                  'syz',
                  { rules: [{ required: true, message: '适应症不能为空' }] },
                ]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row type="flex" justify="center">
          <a-col :span="colSpan">
            <a-form-item v-bind="formItemLayout3" label="禁忌症">
              <a-textarea
                :rows="5"
                placeholder="请输入禁忌症"
                v-decorator="[
                  'jjz',
                  { rules: [{ required: true, message: '禁忌症不能为空' }] },
                ]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row type="flex" justify="center">
          <a-col :span="colSpan">
            <a-form-item v-bind="formItemLayout3" label="操作规程">
              <a-textarea
                :rows="12"
                placeholder="请输入操作规程"
                v-decorator="[
                  'czgz',
                  { rules: [{ required: true, message: '操作规程不能为空' }] },
                ]"
              />
            </a-form-item>
          </a-col>
        </a-row> -->
      </div>
    </a-form>
    <div style="margin: 5px 0px">
      <a-row type="flex" justify="center">
        <a-col :span="colSpan">
          <xxbBDeptFlow-look
            ref="xxbBDeptFlowLook"
          >
          </xxbBDeptFlow-look>
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
        <a-button @click="handleSubmit(0)" v-if="this.isEdit" :loading="loading">保存</a-button>
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
    </div>
  </a-drawer>
</template>

<script>
import SelectUserRemote from "../../common/SelectXxbUserRemote";
import XxbBCheckD from "../XxbBCheck/XxbBCheckD";
import UploadFile from "../../common/UploadFile";
import XxbBDeptFlowLook from "../XxbBDeptFlowLook";
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
  name: "XxbBCheckEdit",
  components: {
    SelectUserRemote,
    XxbBCheckD,
    UploadFile,
    XxbBDeptFlowLook
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
      projectLevelData: [
        {
          value: 1,
          text: "一级",
        },
        {
          value: 2,
          text: "二级",
        },
        {
          value: 3,
          text: "三级",
        },
        {
          value: 4,
          text: "四级",
        },
      ],
      t: 10,
      isEdit: false,
      isDeptNew: true,
      isCxbg: true,
      isZqs:true,
      isHong: true,
      txtLength50: 50,
      txtLength500: 500,
      txtLength30: 30,
      drawerTitle: '新增',
      projectType: '',
      colSpan: 23,
      xxbBCheck: {},
    };
  },
  mounted() {},
  methods: {
    reset() {
      this.loading = false;
      this.isEdit = false;
      this.isDeptNew = true
      this.xxbBCheck = {}
      this.$refs.xxbBCheckDList.reset();
      this.$refs.yxxfxFile.reset();
      this.$refs.aqxfxFile.reset();
      this.$refs.user1.reset();
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
    fetchDept() {
      this.$get("sdlBUser/deptNew", {}).then((res) => {
        this.deptData = [];
        this.deptData.push(...res.data);
      });
    },
    getId() {
      this.$get("xxbBCheck/getId", {}).then((r) => {
        this.form.getFieldDecorator("deptNew");
        let id = r.data.data
        this.xxbBCheck.id = id
      });
    },
    getAccountInfo() {
      this.$get("sdlBUserMg/getAccountInfo").then((r) => {
        this.form.getFieldDecorator("deptNew");
        if(r.data != null) {
          this.xxbBCheck.deptNew = r.data.deptNew
        } else {
          this.xxbBCheck.deptNew = ''
        }
        this.form.setFieldsValue({
          deptNew: this.xxbBCheck.deptNew
        });
      });
    },
    userChange1(value, target) {
      this.xxbBCheck.userAccountName = target.userAccountName;
      this.xxbBCheck.userAccount = target.userAccount;
      this.xxbBCheck.yggh = target.yggh;
      // this.$refs.xxbBCheckDList.setBaseUserYggh(target.yggh);
      this.xxbBCheck.sexName = target.sexname;
      this.xxbBCheck.birthday = target.birthday;
      this.xxbBCheck.edu = target.edu;
      this.xxbBCheck.zhicheng = target.zhicheng;
      this.xxbBCheck.zhichenglc = target.zhichenglc;
      this.xxbBCheck.userdept = target.deptNew;
      this.xxbBCheck.telephone = target.tel;
      this.form.getFieldDecorator("userAccountName");
      // this.form.getFieldDecorator("userAccount");
      // this.form.getFieldDecorator("yggh");
      this.form.getFieldDecorator("sexName");
      this.form.getFieldDecorator("birthday");
      this.form.getFieldDecorator("edu");
      this.form.getFieldDecorator("zhichenglc");
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
    levelChange(value) {
      this.xxbBCheck.projectLevel = value
      let fileCount = this.$refs.xmcxbgFile.getFileCount();
      this.filexmcxbgState(value,fileCount);
    },
    fileZqsData (fileList) {
      if(fileList.length == 0) {
        this.isZqs = true
        this.$nextTick(() => {
          this.form.validateFields(['filezqs'], { force: this.isZqs })
        })
        this.form.getFieldDecorator("filezqs");
        this.form.setFieldsValue({
          filezqs: ''
        });
      } else {
        this.isZqs = false
        this.$nextTick(() => {
          this.form.validateFields(['filezqs'], { force: this.isZqs })
        })
        this.form.getFieldDecorator("filezqs");
        this.form.setFieldsValue({
          filezqs: '1'
        });
      }
    },
    filexmcxbgState (value,fileCount) {
      if(value !== undefined) {
        if ((value === 1 || value === 2) && fileCount === 0) {
          this.isCxbg = true
          this.$nextTick(() => {
            this.form.validateFields(['filexmcxbg'], { force: this.isCxbg })
          })
        } else {
          this.isCxbg = false
          this.$nextTick(() => {
            this.form.validateFields(['filexmcxbg'], { force: this.isCxbg })
          })
        }
        if (value == 1 || value == 2) {
            this.isHong = true
          } else {
            this.isHong = false
          }
      }
    },
    fileData (fileList) {
      this.filexmcxbgState(this.xxbBCheck.projectLevel,fileList.length);
      if(fileList.length > 0) {
        this.form.getFieldDecorator("filexmcxbg");
        this.form.setFieldsValue({
          filexmcxbg: '1'
        });
      } else {
        this.form.getFieldDecorator("filexmcxbg");
        this.form.setFieldsValue({
          filexmcxbg: ''
        });
      }
    },
    setFormValues(xxbBCheck,tit,projectType) {
      this.isDeptNew = true
      this.projectType = projectType
      this.drawerTitle = tit
      if(tit=='查看') {
        this.isEdit = false;
      }
      this.isCxbg = true
      this.isZqs = true
      this.isUpdate = false;
      this.fetchDept();
      
      if (xxbBCheck === undefined || xxbBCheck === null || xxbBCheck === "") {
        this.isEdit = true;
        this.getId()
        this.getAccountInfo()
        this.xxbBCheck.state = 0
        this.xxbBCheck.isxzyljs = 1
        this.xxbBCheck.projectLevel = 1
        this.xxbBCheck.lev = 1
        this.xxbBCheck.typ = 1
        this.xxbBCheck.applydat = moment(new Date())
        this.form.getFieldDecorator("isxzyljs");
        this.form.getFieldDecorator("projectLevel");
        this.form.getFieldDecorator("lev");
        this.form.getFieldDecorator("typ");
        this.form.getFieldDecorator("applydat");
        this.form.setFieldsValue({
          isxzyljs: this.xxbBCheck.isxzyljs,
          projectLevel: this.xxbBCheck.projectLevel,
          lev: this.xxbBCheck.lev,
          typ: this.xxbBCheck.typ,
          applydat: this.xxbBCheck.applydat
        });
      } else {
        if(tit!='查看') {
          if(xxbBCheck.state == 0 || xxbBCheck.state == 3){
            this.isEdit = true;
          } else {
            this.isEdit = false;
          }
        }
        if(xxbBCheck.state != 0){
          this.isDeptNew = false
        }
        setTimeout(() => {
          this.$refs.user1.userData = [
            {
              userAccountName: xxbBCheck.userAccountName,
              userAccount: xxbBCheck.userAccount,
              yggh: xxbBCheck.yggh,
            },
          ];
          this.$refs.user1.defaultValue = xxbBCheck.userAccount;
        }, 100);
        let fields = [
          "deptNew",
          "projectName",
          "projectLevel",
          "isxzyljs",
          "applydat",
          "userAccountName",
          // "userAccount",
          // "yggh",
          "sexName",
          // "userdept",
          "birthday",
          "edu",
          "zhichenglc",
          "zhiwu",
          "zhuany",
          "zhuanc",
          "telephone",
          "lev",
          "typ",
          "projectcontent",
          "lincaqfx",
          "lincyyfx",
          "lincjssb",
          "syz",
          "jjz",
          "czgz",
          'filexmcxbg',
          'filezqs'
          // "srtdat",
          // "enddat",
          // "ycdat",
          // "shstate",
          // "xmjdstate",
        ];
        // let fieldDates = ["applydat", "srtdat", "enddat", "ycdat"];
        let fieldDates = ["applydat"];
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
              obj[key] = xxbBCheck[key];
              this.form.setFieldsValue(obj);
            }
          }
        });

        this.xxbBCheck.id = xxbBCheck.id;
        this.xxbBCheck.state = xxbBCheck.state;
        this.xxbBCheck.userAccount = xxbBCheck.userAccount;
        this.xxbBCheck.yggh = xxbBCheck.yggh;
        this.xxbBCheck.userdept = xxbBCheck.userdept;
        this.xxbBCheck.projectLevel = xxbBCheck.projectLevel
      }
      setTimeout(() => {
        this.$refs.xxbBCheckDList.setFieldValues(xxbBCheck == null? null : xxbBCheck.id,this.isEdit);
        this.$refs.xxbBDeptFlowLook.setFieldValues(xxbBCheck == null? null : xxbBCheck.id,
        0,xxbBCheck == null? 0 : xxbBCheck.state,0);
      }, 100);
      if (this.xxbBCheck.projectLevel == 1 || this.xxbBCheck.projectLevel == 2) {
        this.isHong = true
      } else {
        this.isHong = false
      }
    },
    handleSubmit(type) {
      let fileCount = this.$refs.xmcxbgFile.getFileCount();
      this.filexmcxbgState(this.xxbBCheck.projectLevel,fileCount);
      let btn = type == 0 ? "保存" : "提交";
      this.form.validateFields((err, values) => {
        if (!err) {
          let xxbBCheck = this.form.getFieldsValue();
          xxbBCheck.id = this.xxbBCheck.id;
          xxbBCheck.state = type == 0 ? this.xxbBCheck.state : 1
          xxbBCheck.flownum = type == 0 ? 0 : 1
          xxbBCheck.userAccount = this.xxbBCheck.userAccount;
          xxbBCheck.yggh = this.xxbBCheck.yggh;
          xxbBCheck.zhicheng = this.xxbBCheck.zhicheng;
          xxbBCheck.projectType = 0
          xxbBCheck.projectLb = 0
          xxbBCheck.userdept = this.xxbBCheck.userdept;
          xxbBCheck.checkDataList = this.$refs.xxbBCheckDList.getFieldValues();
          xxbBCheck.projDeptList = []
          this.$put("xxbBCheck/editInfo", {
            data: JSON.stringify(xxbBCheck),
          })
            .then((r) => {
              if (r.data.data.success === 1) {
                this.isUpdate = true;
                // if (this.xxbBCheck.id == null || this.xxbBCheck.id == "") {
                //   console.log('我是空')
                //   this.xxbBCheck.id = r.data.data.data;
                // }
                if (type == 1) {
                  this.reset();
                  this.$emit("success");
                } else {
                  this.$refs.xxbBCheckDList.setFieldValues(
                    this.xxbBCheck.id,this.isEdit);
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
    text-align:right;
  }
  .tdTitle {
    border: 0px;
    text-align: center;
  }
}
.fontColor{
    color:#f5222d;
  }
</style>
