<template>
  <a-card title="">
    <div>
      <a-button @click="handleAdd" type="primary" :loading="loading"
        >添加行</a-button
      >
      <a-button @click="handleDelete" type="primary" :loading="loading"
        >删除行</a-button
      >
    </div>
    <a-table
      :columns="columns"
      :data-source="dataSource"
      :rowKey="(record) => record.id"
      :rowSelection="{
        selectedRowKeys: selectedRowKeys,
        onChange: onSelectChange,
      }"
      bordered
    >
      <template slot="yuanqu" slot-scope="text, record">
        <div v-if="record.state == 3 || record.state == 1">
          {{ text }}
        </div>
        <div v-else>
          <a-textarea
            @blur="(e) => inputChange(e.target.value, record, 'yuanqu')"
            :value="record.yuanqu"
          >
          </a-textarea>
        </div>
      </template>
      <template slot="deptNew" slot-scope="text, record">
        <div v-if="record.state == 3 || record.state == 1">
          {{ text }}
        </div>
        <div v-else>
          <a-textarea
            @blur="(e) => inputChange(e.target.value, record, 'deptNew')"
            :value="record.deptNew"
          >
          </a-textarea>
        </div>
      </template>
      <template slot="projectName" slot-scope="text, record">
        <div v-if="record.state == 3 || record.state == 1">
          {{ text }}
        </div>
        <div v-else>
          <a-textarea
            @blur="(e) => inputChange(e.target.value, record, 'projectName')"
            :value="record.projectName"
          >
          </a-textarea>
        </div>
      </template>
      <template slot="projectLevel" slot-scope="text, record">
        <div v-if="record.state == 3">
          {{ text }}
        </div>
        <div v-else>
          <a-input-number
            @blur="(e) => inputChange(e.target.value, record, 'projectLevel')"
            :value="record.projectLevel"
            :precision="0"
          >
          </a-input-number>
        </div>
      </template>
      <template slot="projectType" slot-scope="text, record">
        <div v-if="record.state == 3">
          {{ text }}
        </div>
        <div v-else>
          <a-input-number
            @blur="(e) => inputChange(e.target.value, record, 'projectType')"
            :value="record.projectType"
            :precision="0"
          >
          </a-input-number>
        </div>
      </template>
      <template slot="projectLb" slot-scope="text, record">
        <div v-if="record.state == 3">
          {{ text }}
        </div>
        <div v-else>
          <a-input-number
            @blur="(e) => inputChange(e.target.value, record, 'projectLb')"
            :value="record.projectLb"
            :precision="0"
          >
          </a-input-number>
        </div>
      </template>
      <template slot="isxzyljs" slot-scope="text, record">
        <div v-if="record.state == 3">
          {{ text }}
        </div>
        <div v-else>
          <a-input-number
            @blur="(e) => inputChange(e.target.value, record, 'isxzyljs')"
            :value="record.isxzyljs"
            :precision="0"
          >
          </a-input-number>
        </div>
      </template>
      <template slot="applydat" slot-scope="text, record">
        <div v-if="record.state == 3 || record.state == 1">
          {{ text == "" || text == null ? "" : text.substr(0, 10) }}
        </div>
        <div v-else>
          <a-date-picker
            :defaultValue="
              text == '' || text == null ? '' : moment(text, dateFormat)
            "
            @change="(e, f) => handleChange(e, f, record, 'applydat')"
          />
        </div>
      </template>
      <template slot="userAccountName" slot-scope="text, record">
        <div v-if="record.state == 3 || record.state == 1">
          {{ text }}
        </div>
        <div v-else>
          <a-textarea
            @blur="
              (e) => inputChange(e.target.value, record, 'userAccountName')
            "
            :value="record.userAccountName"
          >
          </a-textarea>
        </div>
      </template>
      <template slot="userAccount" slot-scope="text, record">
        <div v-if="record.state == 3 || record.state == 1">
          {{ text }}
        </div>
        <div v-else>
          <a-textarea
            @blur="(e) => inputChange(e.target.value, record, 'userAccount')"
            :value="record.userAccount"
          >
          </a-textarea>
        </div>
      </template>
      <template slot="yggh" slot-scope="text, record">
        <div v-if="record.state == 3 || record.state == 1">
          {{ text }}
        </div>
        <div v-else>
          <a-textarea
            @blur="(e) => inputChange(e.target.value, record, 'yggh')"
            :value="record.yggh"
          >
          </a-textarea>
        </div>
      </template>
      <template slot="sexName" slot-scope="text, record">
        <div v-if="record.state == 3 || record.state == 1">
          {{ text }}
        </div>
        <div v-else>
          <a-textarea
            @blur="(e) => inputChange(e.target.value, record, 'sexName')"
            :value="record.sexName"
          >
          </a-textarea>
        </div>
      </template>
      <template slot="birthday" slot-scope="text, record">
        <div v-if="record.state == 3 || record.state == 1">
          {{ text }}
        </div>
        <div v-else>
          <a-textarea
            @blur="(e) => inputChange(e.target.value, record, 'birthday')"
            :value="record.birthday"
          >
          </a-textarea>
        </div>
      </template>
      <template slot="userdept" slot-scope="text, record">
        <div v-if="record.state == 3 || record.state == 1">
          {{ text }}
        </div>
        <div v-else>
          <a-textarea
            @blur="(e) => inputChange(e.target.value, record, 'userdept')"
            :value="record.userdept"
          >
          </a-textarea>
        </div>
      </template>
      <template slot="edu" slot-scope="text, record">
        <div v-if="record.state == 3 || record.state == 1">
          {{ text }}
        </div>
        <div v-else>
          <a-textarea
            @blur="(e) => inputChange(e.target.value, record, 'edu')"
            :value="record.edu"
          >
          </a-textarea>
        </div>
      </template>
      <template slot="zhicheng" slot-scope="text, record">
        <div v-if="record.state == 3 || record.state == 1">
          {{ text }}
        </div>
        <div v-else>
          <a-textarea
            @blur="(e) => inputChange(e.target.value, record, 'zhicheng')"
            :value="record.zhicheng"
          >
          </a-textarea>
        </div>
      </template>
      <template slot="zhichenglc" slot-scope="text, record">
        <div v-if="record.state == 3 || record.state == 1">
          {{ text }}
        </div>
        <div v-else>
          <a-textarea
            @blur="(e) => inputChange(e.target.value, record, 'zhichenglc')"
            :value="record.zhichenglc"
          >
          </a-textarea>
        </div>
      </template>
      <template slot="zhiwu" slot-scope="text, record">
        <div v-if="record.state == 3 || record.state == 1">
          {{ text }}
        </div>
        <div v-else>
          <a-textarea
            @blur="(e) => inputChange(e.target.value, record, 'zhiwu')"
            :value="record.zhiwu"
          >
          </a-textarea>
        </div>
      </template>
      <template slot="zhuany" slot-scope="text, record">
        <div v-if="record.state == 3 || record.state == 1">
          {{ text }}
        </div>
        <div v-else>
          <a-textarea
            @blur="(e) => inputChange(e.target.value, record, 'zhuany')"
            :value="record.zhuany"
          >
          </a-textarea>
        </div>
      </template>
      <template slot="zhuanc" slot-scope="text, record">
        <div v-if="record.state == 3 || record.state == 1">
          {{ text }}
        </div>
        <div v-else>
          <a-textarea
            @blur="(e) => inputChange(e.target.value, record, 'zhuanc')"
            :value="record.zhuanc"
          >
          </a-textarea>
        </div>
      </template>
      <template slot="telephone" slot-scope="text, record">
        <div v-if="record.state == 3 || record.state == 1">
          {{ text }}
        </div>
        <div v-else>
          <a-textarea
            @blur="(e) => inputChange(e.target.value, record, 'telephone')"
            :value="record.telephone"
          >
          </a-textarea>
        </div>
      </template>
      <template slot="kzsrtdat" slot-scope="text, record">
        <div v-if="record.state == 3 || record.state == 1">
          {{ text == "" || text == null ? "" : text.substr(0, 10) }}
        </div>
        <div v-else>
          <a-date-picker
            :defaultValue="
              text == '' || text == null ? '' : moment(text, dateFormat)
            "
            @change="(e, f) => handleChange(e, f, record, 'kzsrtdat')"
          />
        </div>
      </template>
      <template slot="kzls" slot-scope="text, record">
        <div v-if="record.state == 3">
          {{ text }}
        </div>
        <div v-else>
          <a-input-number
            @blur="(e) => inputChange(e.target.value, record, 'kzls')"
            :value="record.kzls"
            :precision="0"
          >
          </a-input-number>
        </div>
      </template>
      <template slot="projectcontent" slot-scope="text, record">
        <div v-if="record.state == 3 || record.state == 1">
          {{ text }}
        </div>
        <div v-else>
          <a-textarea
            @blur="(e) => inputChange(e.target.value, record, 'projectcontent')"
            :value="record.projectcontent"
          >
          </a-textarea>
        </div>
      </template>
      <template slot="projectkxyj" slot-scope="text, record">
        <div v-if="record.state == 3 || record.state == 1">
          {{ text }}
        </div>
        <div v-else>
          <a-textarea
            @blur="(e) => inputChange(e.target.value, record, 'projectkxyj')"
            :value="record.projectkxyj"
          >
          </a-textarea>
        </div>
      </template>
      <template slot="projectffjslx" slot-scope="text, record">
        <div v-if="record.state == 3 || record.state == 1">
          {{ text }}
        </div>
        <div v-else>
          <a-textarea
            @blur="(e) => inputChange(e.target.value, record, 'projectffjslx')"
            :value="record.projectffjslx"
          >
          </a-textarea>
        </div>
      </template>
      <template slot="projectkey" slot-scope="text, record">
        <div v-if="record.state == 3 || record.state == 1">
          {{ text }}
        </div>
        <div v-else>
          <a-textarea
            @blur="(e) => inputChange(e.target.value, record, 'projectkey')"
            :value="record.projectkey"
          >
          </a-textarea>
        </div>
      </template>
      <template slot="jsfxya" slot-scope="text, record">
        <div v-if="record.state == 3 || record.state == 1">
          {{ text }}
        </div>
        <div v-else>
          <a-textarea
            @blur="(e) => inputChange(e.target.value, record, 'jsfxya')"
            :value="record.jsfxya"
          >
          </a-textarea>
        </div>
      </template>
      <template slot="projectjjshxy" slot-scope="text, record">
        <div v-if="record.state == 3 || record.state == 1">
          {{ text }}
        </div>
        <div v-else>
          <a-textarea
            @blur="(e) => inputChange(e.target.value, record, 'projectjjshxy')"
            :value="record.projectjjshxy"
          >
          </a-textarea>
        </div>
      </template>
      <template slot="jswxmlcc" slot-scope="text, record">
        <div v-if="record.state == 3 || record.state == 1">
          {{ text }}
        </div>
        <div v-else>
          <a-textarea
            @blur="(e) => inputChange(e.target.value, record, 'jswxmlcc')"
            :value="record.jswxmlcc"
          >
          </a-textarea>
        </div>
      </template>
      <template slot="newbdqk" slot-scope="text, record">
        <div v-if="record.state == 3 || record.state == 1">
          {{ text }}
        </div>
        <div v-else>
          <a-textarea
            @blur="(e) => inputChange(e.target.value, record, 'newbdqk')"
            :value="record.newbdqk"
          >
          </a-textarea>
        </div>
      </template>
      <template slot="projectkzqk" slot-scope="text, record">
        <div v-if="record.state == 3 || record.state == 1">
          {{ text }}
        </div>
        <div v-else>
          <a-textarea
            @blur="(e) => inputChange(e.target.value, record, 'projectkzqk')"
            :value="record.projectkzqk"
          >
          </a-textarea>
        </div>
      </template>
      <template slot="fileurl" slot-scope="text, record">
        <div v-if="record.state == 3 || record.state == 1">
          {{ text }}
        </div>
        <div v-else>
          <a-textarea
            @blur="(e) => inputChange(e.target.value, record, 'fileurl')"
            :value="record.fileurl"
          >
          </a-textarea>
        </div>
      </template>
      <template slot="flownum" slot-scope="text, record">
        <div v-if="record.state == 3">
          {{ text }}
        </div>
        <div v-else>
          <a-input-number
            @blur="(e) => inputChange(e.target.value, record, 'flownum')"
            :value="record.flownum"
            :precision="0"
          >
          </a-input-number>
        </div>
      </template>
      <template slot="fileId" slot-scope="text, record">
        <div v-if="record.state == 3 || record.state == 1">
          <a
            :href="record.fileUrl"
            v-if="text != null && text != ''"
            target="_blank"
            >查看</a
          >
        </div>
        <div v-else>
          <a-button type="dashed" block @click="OpenFile(record)">
            {{
              record.fileId != null && record.fileId != "" ? "已上传" : "上传"
            }}
          </a-button>
        </div>
      </template>
      <template slot="isUse" slot-scope="text, record">
        <a-checkbox
          @change="(e) => onIsUseChange(e, record, 'isUse')"
          :checked="text"
        ></a-checkbox>
      </template>
    </a-table>
    <div>
      <a-button @click="handleSave" type="primary" :loading="loading"
        >保存草稿</a-button
      >
      <a-button @click="handleSubmit" type="primary" :loading="loading"
        >提交</a-button
      >
    </div>
    <tableUpload-file
      ref="upFile"
      :fileId="editRecord.fileId"
      :fileVisiable="fileVisiable"
      @setFileId="setFileId"
    >
    </tableUpload-file>
  </a-card>
</template>

<script>
import moment from 'moment';
import TableUploadFile from '../../common/TableUploadFile'
export default {
    data() {
        return {
            dateFormat: 'YYYY-MM-DD',
            dataSource: [],
            selectedRowKeys: [],
            loading: false,
            CustomVisiable: false,
            idNums: 10000,
            fileVisiable: false,
            editRecord: {
                fileId: ''
            }
        }
    },
    components: {
        TableUploadFile
    },
    mounted() {
        this.fetch()
    },
    methods: {
        moment,
        showFile(record) {
            window.location.href = record.fileUrl
        },
        OpenFile(record) {
            this.editRecord = record
            this.fileVisiable = true
            if (record.fileId != undefined && record.fileId != '') {
                this.$refs.upFile.fetch(record.fileId)
            }
        },
        setFileId(fileId, fileUrl) {
            this.fileVisiable = false
            console.log(fileUrl)
            /**
             const dataSource = [...this.dataSource]
             console.log(this.editRecord.id)
             let record=dataSource.filter(p=>p.id===this.editRecord.id)
             console.log(record)*/
            this.editRecord["fileId"] = fileId
            this.editRecord["fileUrl"] = fileUrl
            //this.dataSource =[...dataSource]
        },
        onSelectChange(selectedRowKeys, selectedRows) {
            // console.log(selectedRows)
            if (selectedRows.length > 0) {
                if (selectedRows[0].state != 3 && selectedRows[0].state != 1) {
                    this.selectedRowKeys = selectedRowKeys
                }
            } else {
                this.selectedRowKeys = selectedRowKeys
            }
        },
        handleChange(date, dateStr, record, filedName) {
            const value = dateStr
            record[filedName] = value
        },
        inputChange(value, record, filedName) {
            console.info(value)
            record[filedName] = value
        },
        handleSelectChange(value, option, record, filedName) {
            console.info(value)
            record[filedName] = value
        },
        onIsUseChange(e, record, filedName) {
            record[filedName] = e.target.checked;
        },
        handleAdd() {
            for (let i = 0; i < 4; i++) {
                this.dataSource.push({
                    id: (this.idNums + i + 1).toString(),
                    state: 0,
                    yuanqu: '',
                    deptNew: '',
                    projectName: '',
                    projectLevel: '',
                    projectType: '',
                    projectLb: '',
                    isxzyljs: '',
                    applydat: '',
                    userAccountName: '',
                    userAccount: '',
                    yggh: '',
                    sexName: '',
                    birthday: '',
                    userdept: '',
                    edu: '',
                    zhicheng: '',
                    zhichenglc: '',
                    zhiwu: '',
                    zhuany: '',
                    zhuanc: '',
                    telephone: '',
                    kzsrtdat: '',
                    kzls: '',
                    projectcontent: '',
                    projectkxyj: '',
                    projectffjslx: '',
                    projectkey: '',
                    jsfxya: '',
                    projectjjshxy: '',
                    jswxmlcc: '',
                    newbdqk: '',
                    projectkzqk: '',
                    fileurl: '',
                    flownum: '',
                    isUse: false
                })
            }
            this.idNums = this.idNums + 4
        },
        handleSave() {
            const dataSourceAll = [...this.dataSource]
            const dataSource = dataSourceAll.filter(p => p.state == 0 || p.state == 2)
            let dataAdd = []
            dataSource.forEach(element => {
                if (element.yuanqu != '' || element.deptNew != '' || element.projectName != '' || element.projectLevel != '' || element.projectType != '' || element.projectLb != '' || element.isxzyljs != '' || element.applydat != '' || element.userAccountName != '' || element.userAccount != '' || element.yggh != '' || element.sexName != '' || element.birthday != '' || element.userdept != '' || element.edu != '' || element.zhicheng != '' || element.zhichenglc != '' || element.zhiwu != '' || element.zhuany != '' || element.zhuanc != '' || element.telephone != '' || element.kzsrtdat != '' || element.kzls != '' || element.projectcontent != '' || element.projectkxyj != '' || element.projectffjslx != '' || element.projectkey != '' || element.jsfxya != '' || element.projectjjshxy != '' || element.jswxmlcc != '' || element.newbdqk != '' || element.projectkzqk != '' || element.fileurl != '' || element.flownum != '') {
                    dataAdd.push(element)
                }
            });
            if (dataAdd.length === 0) {
                this.$message.warning('请填写数据！！！')
            } else {
                let jsonStr = JSON.stringify(dataAdd)
                this.loading = true
                this.$post('xxbBResult/addNew', {
                    jsonStr: jsonStr,
                    state: 0
                }).then(() => {
                    // this.reset()
                    this.$message.success('保存成功')
                    this.fetch()
                    this.loading = false
                }).catch(() => {
                    this.loading = false
                })
            }
        },
        handleSubmit() {
            let that = this
            this.$confirm({
                title: '确定提交全部记录?',
                content: '当您点击确定按钮后，信息将不能修改',
                centered: true,
                onOk() {
                    const dataSourceAll = [...that.dataSource]
                    const dataSource = dataSourceAll.filter(p => p.state == 0 || p.state == 2)
                    let dataAdd = []
                    dataSource.forEach(element => {
                        if (element.yuanqu != '' || element.deptNew != '' || element.projectName != '' || element.projectLevel != '' || element.projectType != '' || element.projectLb != '' || element.isxzyljs != '' || element.applydat != '' || element.userAccountName != '' || element.userAccount != '' || element.yggh != '' || element.sexName != '' || element.birthday != '' || element.userdept != '' || element.edu != '' || element.zhicheng != '' || element.zhichenglc != '' || element.zhiwu != '' || element.zhuany != '' || element.zhuanc != '' || element.telephone != '' || element.kzsrtdat != '' || element.kzls != '' || element.projectcontent != '' || element.projectkxyj != '' || element.projectffjslx != '' || element.projectkey != '' || element.jsfxya != '' || element.projectjjshxy != '' || element.jswxmlcc != '' || element.newbdqk != '' || element.projectkzqk != '' || element.fileurl != '' || element.flownum != '') {
                            dataAdd.push(element)
                        }
                    });
                    if (dataAdd.length === 0) {
                        that.$message.warning('请填写数据！！！')
                    } else {
                        let jsonStr = JSON.stringify(dataAdd)
                        that.loading = true
                        that.$post('xxbBResult/addNew', {
                            jsonStr: jsonStr,
                            state: 1
                        }).then(() => {
                            //this.reset()
                            that.$message.success('提交成功')
                            that.fetch()
                            that.CustomVisiable = false //提交之后 不能再修改
                            that.loading = false
                        }).catch(() => {
                            that.loading = false
                        })
                    }
                },
                onCancel() {
                    that.selectedRowKeys = []
                }
            })

        },
        handleDelete() {
            if (!this.selectedRowKeys.length) {
                this.$message.warning('请选择需要删除的记录')
                return
            }
            let that = this
            this.$confirm({
                title: '确定删除所选中的记录?',
                content: '当您点击确定按钮后，这些记录将会被彻底删除',
                centered: true,
                onOk() {
                    let dcaBPatentIds = that.selectedRowKeys.join(',')
                    const dataSource = [...that.dataSource];
                    let new_dataSource = dataSource.filter(p => that.selectedRowKeys.indexOf(p.id) < 0)
                    that.dataSource = new_dataSource
                    that.$message.success('删除成功')
                    that.selectedRowKeys = []
                },
                onCancel() {
                    that.selectedRowKeys = []
                }
            })
        },
        fetch() {
            this.$get('xxbBResult/custom', {}).then((r) => {
                let data = r.data
                this.dataSource = data.rows

                for (let i = 0; i < 4; i++) {
                    this.dataSource.push({
                        id: (this.idNums + i + 1).toString(),
                        state: 0,
                        yuanqu: '',
                        deptNew: '',
                        projectName: '',
                        projectLevel: '',
                        projectType: '',
                        projectLb: '',
                        isxzyljs: '',
                        applydat: '',
                        userAccountName: '',
                        userAccount: '',
                        yggh: '',
                        sexName: '',
                        birthday: '',
                        userdept: '',
                        edu: '',
                        zhicheng: '',
                        zhichenglc: '',
                        zhiwu: '',
                        zhuany: '',
                        zhuanc: '',
                        telephone: '',
                        kzsrtdat: '',
                        kzls: '',
                        projectcontent: '',
                        projectkxyj: '',
                        projectffjslx: '',
                        projectkey: '',
                        jsfxya: '',
                        projectjjshxy: '',
                        jswxmlcc: '',
                        newbdqk: '',
                        projectkzqk: '',
                        fileurl: '',
                        flownum: '',
                        isUse: false
                    })
                    this.idNums = this.idNums + 4
                }
            })
        }
    },
    computed: {
        columns() {
            return [{
                    title: '院区',
                    dataIndex: 'yuanqu',
                    width: 130,
                    scopedSlots: {
                        customRender: 'yuanqu'
                    }
                },
                {
                    title: '申请科室',
                    dataIndex: 'deptNew',
                    width: 130,
                    scopedSlots: {
                        customRender: 'deptNew'
                    }
                },
                {
                    title: '项目名称',
                    dataIndex: 'projectName',
                    width: 130,
                    scopedSlots: {
                        customRender: 'projectName'
                    }
                },
                {
                    title: '项目级别',
                    dataIndex: 'projectLevel',
                    width: 130,
                    scopedSlots: {
                        customRender: 'projectLevel'
                    }
                },
                {
                    title: '项目类型',
                    dataIndex: 'projectType',
                    width: 130,
                    scopedSlots: {
                        customRender: 'projectType'
                    }
                },
                {
                    title: '项目类别',
                    dataIndex: 'projectLb',
                    width: 130,
                    scopedSlots: {
                        customRender: 'projectLb'
                    }
                },
                {
                    title: '是否为限制类医疗技术',
                    dataIndex: 'isxzyljs',
                    width: 130,
                    scopedSlots: {
                        customRender: 'isxzyljs'
                    }
                },
                {
                    title: '申请日期',
                    dataIndex: 'applydat',
                    width: 130,
                    scopedSlots: {
                        customRender: 'applydat'
                    }
                },
                {
                    title: '姓名',
                    dataIndex: 'userAccountName',
                    width: 130,
                    scopedSlots: {
                        customRender: 'userAccountName'
                    }
                },
                {
                    title: '人事编号',
                    dataIndex: 'userAccount',
                    width: 130,
                    scopedSlots: {
                        customRender: 'userAccount'
                    }
                },
                {
                    title: '员工工号',
                    dataIndex: 'yggh',
                    width: 130,
                    scopedSlots: {
                        customRender: 'yggh'
                    }
                },
                {
                    title: '性别',
                    dataIndex: 'sexName',
                    width: 130,
                    scopedSlots: {
                        customRender: 'sexName'
                    }
                },
                {
                    title: '出生年月',
                    dataIndex: 'birthday',
                    width: 130,
                    scopedSlots: {
                        customRender: 'birthday'
                    }
                },
                {
                    title: '用户科室',
                    dataIndex: 'userdept',
                    width: 130,
                    scopedSlots: {
                        customRender: 'userdept'
                    }
                },
                {
                    title: '学历、学位',
                    dataIndex: 'edu',
                    width: 130,
                    scopedSlots: {
                        customRender: 'edu'
                    }
                },
                {
                    title: '职称',
                    dataIndex: 'zhicheng',
                    width: 130,
                    scopedSlots: {
                        customRender: 'zhicheng'
                    }
                },
                {
                    title: '临床职称',
                    dataIndex: 'zhichenglc',
                    width: 130,
                    scopedSlots: {
                        customRender: 'zhichenglc'
                    }
                },
                {
                    title: '职务',
                    dataIndex: 'zhiwu',
                    width: 130,
                    scopedSlots: {
                        customRender: 'zhiwu'
                    }
                },
                {
                    title: '专业',
                    dataIndex: 'zhuany',
                    width: 130,
                    scopedSlots: {
                        customRender: 'zhuany'
                    }
                },
                {
                    title: '专长',
                    dataIndex: 'zhuanc',
                    width: 130,
                    scopedSlots: {
                        customRender: 'zhuanc'
                    }
                },
                {
                    title: '手机号码',
                    dataIndex: 'telephone',
                    width: 130,
                    scopedSlots: {
                        customRender: 'telephone'
                    }
                },
                {
                    title: '开展日期',
                    dataIndex: 'kzsrtdat',
                    width: 130,
                    scopedSlots: {
                        customRender: 'kzsrtdat'
                    }
                },
                {
                    title: '开展例数',
                    dataIndex: 'kzls',
                    width: 130,
                    scopedSlots: {
                        customRender: 'kzls'
                    }
                },
                {
                    title: '项目的主要内容及意义',
                    dataIndex: 'projectcontent',
                    width: 130,
                    scopedSlots: {
                        customRender: 'projectcontent'
                    }
                },
                {
                    title: '项目的科学依据',
                    dataIndex: 'projectkxyj',
                    width: 130,
                    scopedSlots: {
                        customRender: 'projectkxyj'
                    }
                },
                {
                    title: '项目采取的方法、技术路线',
                    dataIndex: 'projectffjslx',
                    width: 130,
                    scopedSlots: {
                        customRender: 'projectffjslx'
                    }
                },
                {
                    title: '项目解决的关键问题',
                    dataIndex: 'projectkey',
                    width: 130,
                    scopedSlots: {
                        customRender: 'projectkey'
                    }
                },
                {
                    title: '技术存在的主要风险及预案',
                    dataIndex: 'jsfxya',
                    width: 130,
                    scopedSlots: {
                        customRender: 'jsfxya'
                    }
                },
                {
                    title: '项目产生的经济效益、社会效益',
                    dataIndex: 'projectjjshxy',
                    width: 130,
                    scopedSlots: {
                        customRender: 'projectjjshxy'
                    }
                },
                {
                    title: '主要技术文献目录及出处',
                    dataIndex: 'jswxmlcc',
                    width: 130,
                    scopedSlots: {
                        customRender: 'jswxmlcc'
                    }
                },
                {
                    title: '新闻报道情况',
                    dataIndex: 'newbdqk',
                    width: 130,
                    scopedSlots: {
                        customRender: 'newbdqk'
                    }
                },
                {
                    title: '项目开展情况',
                    dataIndex: 'projectkzqk',
                    width: 130,
                    scopedSlots: {
                        customRender: 'projectkzqk'
                    }
                },
                {
                    title: '附件地址',
                    dataIndex: 'fileurl',
                    width: 130,
                    scopedSlots: {
                        customRender: 'fileurl'
                    }
                },
                {
                    title: '流程数',
                    dataIndex: 'flownum',
                    width: 130,
                    scopedSlots: {
                        customRender: 'flownum'
                    }
                },
                {
                    title: '状态',
                    dataIndex: 'state',
                    width: 100,
                    customRender: (text, row, index) => {
                        switch (text) {
                            case 0:
                                return <a-tag color = "purple" > 未提交 </a-tag>
                            case 1:
                                return <a-tag color = "green" > 已提交 </a-tag>
                            case 2:
                                return <a-tag color = "red" > 审核未通过 </a-tag>
                            case 3:
                                return <a-tag color = "#f50" > 已审核 </a-tag>
                            default:
                                return text
                        }
                    }
                },
                {
                    title: '审核意见',
                    dataIndex: 'auditSuggestion'
                },
                {
                    title: '是否用于本次评审',
                    dataIndex: 'isUse',
                    scopedSlots: {
                        customRender: 'isUse'
                    },
                    width: 80
                },
                {
                    title: '附件',
                    dataIndex: 'fileId',
                    scopedSlots: {
                        customRender: 'fileId'
                    },
                    width: 80
                }
            ]
        }
    },
}
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
