<template>
  <a-drawer
    :title="drawerTitle"
    :maskClosable="false"
    width="85%"
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
                <tr> <td class="tdRight" style="width: 13%;"><font class="fontColor">*</font>所在院区：</td>
                <td style="width: 25%;">  <a-form-item>
                   <a-select
                    placeholder="请选择所在院区"
                    v-decorator="[
                      'yuanqu',
                      { rules: [{ required: true, message: '所在院区不能为空' }] },
                    ]"
                  >
                    <a-select-option
                      key="本院"
                      value="本院"
                    >
                     本院
                    </a-select-option>
                     <a-select-option
                      key="西院"
                      value="西院"
                    >
                     西院
                    </a-select-option>
                     <a-select-option
                      key="肿瘤"
                      value="肿瘤"
                    >
                     肿瘤
                    </a-select-option>
                     <a-select-option
                      key="金银湖"
                      value="金银湖"
                    >
                     金银湖
                    </a-select-option>
                  </a-select>
                  </a-form-item>
                </td><td colspan="4"></td>
                </tr>
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
            <xxbBLcone-d ref="xxbBLconeDList" @jisuan="jisuan"> </xxbBLcone-d>
          </a-col>
        </a-row>
      </div>
      <!-- 项目主要人员情况 -->
      <a-row type="flex" justify="center">
        <b>
          <h2>项目主要人员情况</h2>
        </b>
      </a-row>
      <div style="margin: 5px 0px;text-align:center">
        <a-row type="flex" justify="center">
          <a-col :span="colSpan">
            <table class="formtab">
              <tr>
                <td class="tdRight" rowspan="3" style="width:20%">职称结构：</td>
                <td colspan="3" style="width:20%">
                  医　　生
                </td>
                <td colspan="3" style="width:20%">
                  护　　士
                </td>
                <td colspan="3" style="width:20%">
                  技术人员
                </td>
                <td colspan="3" style="width:20%">
                  其　　他
                </td>
              </tr>
              <tr>
                <td>高级</td>
                <td>中级</td>
                <td>初级</td>
                <td>高级</td>
                <td>中级</td>
                <td>初级</td>
                <td>高级</td>
                <td>中级</td>
                <td>初级</td>
                <td>高级</td>
                <td>中级</td>
                <td>初级</td>
              </tr>
              <tr>
                <td>{{dataRs.zcysgj}}</td>
                <td>{{dataRs.zcyszj}}</td>
                <td>{{dataRs.zcyscj}}</td>
                <td>{{dataRs.zchsgj}}</td>
                <td>{{dataRs.zchszj}}</td>
                <td>{{dataRs.zchscj}}</td>
                <td>{{dataRs.zcjsgj}}</td>
                <td>{{dataRs.zcjszj}}</td>
                <td>{{dataRs.zcjscj}}</td>
                <td>{{dataRs.zcqtgj}}</td>
                <td>{{dataRs.zcqtzj}}</td>
                <td>{{dataRs.zcqtcj}}</td>
              </tr>
              <tr>
                <td class="tdRight" rowspan="3" style="width:20%">学历结构：</td>
                <td colspan="3" style="width:20%">
                  博士研究生
                </td>
                <td colspan="3" style="width:20%">
                  硕士研究生
                </td>
                <td colspan="3" style="width:20%">
                  本科学士
                </td>
                <td colspan="3" style="width:20%">
                  专科及其他
                </td>
              </tr>
              <tr>
                <td colspan="3" style="width:20%">
                  {{dataRs.xlbs}}
                </td>
                <td colspan="3" style="width:20%">
                  {{dataRs.xlyjs}}
                </td>
                <td colspan="3" style="width:20%">
                  {{dataRs.xlbk}}
                </td>
                <td colspan="3" style="width:20%">
                  {{dataRs.xlqt}}
                </td>
              </tr>
            </table>
          </a-col>
        </a-row>
      </div>
      <!-- 项目级类型 -->
      <a-row type="flex" justify="center">
        <b>
          <h2>项目级类型</h2>
        </b>
      </a-row>
      <div style="margin: 5px 0px">
        <a-row type="flex" justify="center">
          <a-col :span="colSpan">
            <table class="formtab">
              <tr>
                <td class="tdRight" style="width:20%"><font class="fontColor">*</font>项目类型：</td>
                <td style="width:80%">
                  <a-form-item>
                    <a-radio-group name="levRadioGroup" v-decorator="['projectLb']">
                      <a-radio :value="1"> 手术类 </a-radio>
                      <a-radio :value="2"> 治疗操作类 </a-radio>
                      <a-radio :value="3"> 其他类 </a-radio>
                    </a-radio-group>
                  </a-form-item>
                </td>
              </tr>
            </table>
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
            </table>
          </a-col>
        </a-row>
      </div>
      <!-- 可行性论证报告 -->
      <a-row type="flex" justify="center">
        <b>
          <h2>可行性论证报告</h2>
        </b>
      </a-row>
      <div style="margin: 5px 0px">
        <a-row type="flex" justify="center">
          <a-col :span="colSpan">
            <table class="formtab">
              <tr>
                <td class="tdRight" style="width:20%"><font class="fontColor">*</font>项目的主要内容：</td>
                <td style="width:80%">
                  <a-form-item>
                    <a-textarea
                      :rows="8"
                      :maxLength="txtLength500"
                      placeholder="请输入项目的主要内容"
                      v-decorator="[
                        'projectcontent',
                        {
                          rules: [
                            {
                              required: true,
                              message: '项目的主要内容不能为空',
                            },
                          ],
                        },
                      ]"
                    />
                  </a-form-item>
                </td>
              </tr>
              <tr>
                <td class="tdRight" style="width:20%"><font class="fontColor">*</font>项目的目的、意义及<br>创新之处：</td>
                <td style="width:80%">
                  <a-form-item>
                    <a-textarea
                      :rows="8"
                      :maxLength="txtLength500"
                      placeholder="请输入项目的目的、意义及创新之处"
                      v-decorator="[
                        'projectmdyycx',
                        {
                          rules: [
                            {
                              required: true,
                              message: '项目的目的、意义及创新之处不能为空',
                            },
                          ],
                        },
                      ]"
                    />
                  </a-form-item>
                </td>
              </tr>
              <tr>
                <td class="tdRight" style="width:20%"><font class="fontColor">*</font>国内外研究与应用概况：</td>
                <td style="width:80%">
                  <a-form-item>
                    <a-textarea
                      :rows="8"
                      :maxLength="txtLength500"
                      placeholder="请输入国内外研究与应用概况"
                      v-decorator="[
                        'gnwyjyygk',
                        {
                          rules: [
                            {
                              required: true,
                              message: '国内外研究与应用概况不能为空',
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
                <td class="tdRight" style="width:20%"><font class="fontColor">*</font>可行性分析及实施计划：</td>
                <td style="width:80%">
                  <a-form-item>
                    <a-textarea
                      :rows="8"
                      :maxLength="txtLength500"
                      placeholder="请输入可行性分析及实施计划"
                      v-decorator="[
                        'kxxfxssjh',
                        {rules: [{required: true,message: '可行性分析及实施计划不能为空'}],
                        },
                      ]"
                    />
                  </a-form-item>
                </td>
              </tr>
              <tr>
                <td class="tdRight" style="width:20%"><font class="fontColor">*</font>项目可解决的临床问题及<br>效益分析：</td>
                <td style="width:80%">
                  <a-form-item>
                    <a-textarea
                      :rows="8"
                      :maxLength="txtLength500"
                      placeholder="请输入项目可解决的临床问题及效益分析"
                      v-decorator="[
                        'projectlcwtxyfx',
                        {
                          rules: [{ required: true, message: '项目可解决的临床问题及效益分析不能为空'}],
                        },
                      ]"
                    />
                  </a-form-item>
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
                        { rules: [{ required: true, message: '科室已具备的技术和设备条件不能为空'}] },
                      ]"
                    />
                  </a-form-item>
                </td>
              </tr>
              <tr>
                <td class="tdRight" style="width:20%"><font class="fontColor">*</font>操作人员技术培训及<br>岗位职责情况：</td>
                <td style="width:80%">
                  <a-form-item>
                    <a-textarea
                      :rows="8"
                      :maxLength="txtLength500"
                      placeholder="请输入操作人员技术培训及岗位职责情况"
                      v-decorator="[
                        'ryjjpxgwzzqk',
                        { rules: [{ required: true, message: '操作人员技术培训及岗位职责情况不能为空' }] },
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
                <td class="tdRight" style="width:20%"><font class="fontColor">*</font>可能造成的不良后果、<br>并发症及相应的防范措施：</td>
                <td style="width:80%">
                  <a-form-item>
                    <a-textarea
                      :rows="8"
                      :maxLength="txtLength500"
                      placeholder="请输入可能造成的不良后果、并发症及相应的防范措施"
                      v-decorator="[
                        'blhgbfzffcs',
                        { rules: [{ required: true, message: '可能造成的不良后果、并发症及相应的防范措施不能为空' }] },
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
                   <upload-file
                    ref="czgzFile"
                    :baseId="xxbBCheck.id"
                    btnTitle="上传相关操作规程PDF"
                    :isEdit="isEdit"
                    refTab="xxbcheck_czgz"
                  >
                  </upload-file>
                </td>
              </tr>
              <tr>
                <td class="tdRight" style="width:20%"><font class="fontColor">*</font>质控措施：</td>
                <td style="width:80%">
                  <a-form-item>
                    <a-textarea
                      :rows="8"
                      :maxLength="txtLength500"
                      placeholder="请输入质控措施"
                      v-decorator="[
                        'zkcs',
                        { rules: [{ required: true, message: '质控措施不能为空' }] },
                      ]"
                    />
                  </a-form-item>
                </td>
              </tr>
              <tr>
                <td class="tdRight" style="width:20%"><font class="fontColor">*</font>是否在我院诊疗科目范畴：</td>
                <td style="width:80%">
                  <a-form-item>
                    <a-textarea
                      :rows="8"
                      :maxLength="txtLength500"
                      placeholder="请输入是否在我院诊疗科目范畴"
                      v-decorator="[
                        'sfwyzlkmfw',
                        { rules: [{ required: true, message: '是否在我院诊疗科目范畴不能为空' }] },
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
                    refTab="xxbcheck_xmcxbg"
                    :isCallback="true"
                    @fileData="fileCxbgData"
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
import XxbBLconeD from "../XxbBLcone/XxbBLconeD";
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
  name: "XxbBLconeEdit",
  components: {
    SelectUserRemote,
    XxbBLconeD,
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
      isZqs: true,
      isHong: true,
      txtLength50: 50,
      txtLength500: 500,
      txtLength30: 30,
      drawerTitle: '新增',
      projectType: '',
      colSpan: 23,
      xxbBCheck: {},
      dataRs: {
        zcysgj: 0,
        zcyszj: 0,
        zcyscj: 0,
        zchsgj: 0,
        zchszj: 0,
        zchscj: 0,
        zcjsgj: 0,
        zcjszj: 0,
        zcjscj: 0,
        zcqtgj: 0,
        zcqtzj: 0,
        zcqtcj: 0,
        xlbs: 0,
        xlyjs: 0,
        xlbk: 0,
        xlqt: 0
      },
    };
  },
  mounted() {},
  methods: {
    reset() {
      this.loading = false;
      this.isEdit = false;
      this.isDeptNew = true
      this.xxbBCheck = {}
      this.$refs.xxbBLconeDList.reset();
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
    jisuan(dataRs){
      this.dataRs = dataRs
    },
    userChange1(value, target) {
      this.xxbBCheck.userAccountName = target.userAccountName;
      this.xxbBCheck.userAccount = target.userAccount;
      this.xxbBCheck.yggh = target.yggh;
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
    fileCxbgData (fileList) {
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
      this.projectType = projectType
      this.drawerTitle = tit
      this.isDeptNew = true
      if(tit=='查看') {
        this.isEdit = false;
      }
      this.isZqs = true
      this.isCxbg = true
      this.isUpdate = false;
      this.fetchDept();
      if (xxbBCheck === undefined || xxbBCheck === null || xxbBCheck === "") {
        this.isEdit = true;
        this.getId()
        this.getAccountInfo()
        this.xxbBCheck.state = 0
        this.xxbBCheck.isxzyljs = 1
        this.xxbBCheck.projectLevel = 1
        this.xxbBCheck.projectLb = 1
        this.xxbBCheck.lev = 1
        this.xxbBCheck.typ = 1
        this.xxbBCheck.applydat = moment(new Date())
        this.form.getFieldDecorator("isxzyljs");
        this.form.getFieldDecorator("projectLevel");
        this.form.getFieldDecorator("projectLb");
        this.form.getFieldDecorator("lev");
        this.form.getFieldDecorator("typ");
        this.form.getFieldDecorator("applydat");
         this.form.getFieldDecorator("yuanqu");
        this.form.setFieldsValue({
          isxzyljs: this.xxbBCheck.isxzyljs,
          projectLevel: this.xxbBCheck.projectLevel,
          projectLb: this.xxbBCheck.projectLb,
          lev: this.xxbBCheck.lev,
          typ: this.xxbBCheck.typ,
          applydat: this.xxbBCheck.applydat,
          yuanqu: this.xxbBCheck.yuanqu,
        });
      } else {
        debugger
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
          "projectLb",
          "isxzyljs",
          "applydat",
          "yuanqu",
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
          "projectmdyycx",
          "gnwyjyygk",
          "kxxfxssjh",
          "projectlcwtxyfx",
          "ryjjpxgwzzqk",
          "blhgbfzffcs",
          "zkcs",
          "sfwyzlkmfw",
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
        this.$refs.xxbBLconeDList.setFieldValues(xxbBCheck == null? null : xxbBCheck.id,this.isEdit);
        this.$refs.xxbBDeptFlowLook.setFieldValues(xxbBCheck == null? null : xxbBCheck.id,
        1,xxbBCheck == null? 0 : xxbBCheck.state,0);
      }, 100);

      if (this.xxbBCheck.projectLevel == 1 || this.xxbBCheck.projectLevel == 2) {
        this.isHong = true
      } else {
        this.isHong = false
      }
    },
    handleSubmit(type) {
      // let fileCount = this.$refs.xmcxbgFile.getFileCount();
      // this.filexmcxbgState(this.xxbBCheck.projectLevel,fileCount);
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
          xxbBCheck.projectType = 1
          xxbBCheck.userdept = this.xxbBCheck.userdept;
          xxbBCheck.checkDataList = this.$refs.xxbBLconeDList.getFieldValues();
          xxbBCheck.projDeptList = []
          this.$put("xxbBCheck/editInfo", {
            data: JSON.stringify(xxbBCheck),
          }).then((r) => {
              if (r.data.data.success === 1) {
                this.isUpdate = true;
                if (type == 1) {
                  this.reset();
                  this.$emit("success");
                } else {
                  this.$refs.xxbBLconeDList.setFieldValues(
                    this.xxbBCheck.id,this.isEdit
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
