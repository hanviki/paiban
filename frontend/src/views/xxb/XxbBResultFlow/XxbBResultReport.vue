<template>
  <a-card :bordered="false" class="card-area">
    <div :class="advanced ? 'search' : null">
      <a-form layout="horizontal">
        <a-row>
          <div :class="advanced ? null : 'fold'"></div>
          <a-col :md="7" :sm="24">
            <a-form-item label="申报日期" v-bind="formItemLayout">
              <a-date-picker v-model="queryParams.applydatFrom" @change="onSqStartChange" style="width:120px" />- 
              <a-date-picker v-model="queryParams.applydatTo" @change="onSqEndChange" style="width:120px"/>
            </a-form-item>
            <a-form-item label="申报科室" v-bind="formItemLayout">
              <a-select
                option-filter-prop="children"
                v-model="queryParams.deptNew"
                placeholder="请选择科室"
                style="width:250px"
                allowClear
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
          <a-col :md="7" :sm="24">
            <a-form-item label="导入分数日期" v-bind="formItemLayout">
              <a-date-picker v-model="queryParams.importdatFrom" @change="onImportStartChange" style="width:120px" />- 
              <a-date-picker v-model="queryParams.importdatTo" @change="onImportEndChange" style="width:120px"/>
            </a-form-item>
            <a-form-item label="项目名称" v-bind="formItemLayout">
              <a-input style="width:250px" placeholder="请输入项目名称" v-model="queryParams.projectName" />
            </a-form-item>
            <a-form-item label="审核状态" v-bind="formItemLayout">
              <a-select style="width:150px"placeholder="请选择审核状态" v-model="queryParams.state" allowClear>
                <a-select-option :value="0">
                  未提交
                </a-select-option>
                <a-select-option :value="1">
                  已提交
                </a-select-option>
                <a-select-option :value="2">
                  已审核
                </a-select-option>
                <a-select-option :value="3">
                  已退回
                </a-select-option>
                <a-select-option :value="9">
                  终止申报
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :md="9" :sm="24">
            <a-row>
              <a-col :span="8" style="margin-top:5px"><a-checkbox v-model="isDf" @change="isDfChange">需要打分的项目</a-checkbox></a-col>
              <a-col :span="8">
                <a-button @click="exportCgj" style="width:170px"><a-icon type="download" />导出成果奖模板</a-button>
              </a-col>
              <a-col :span="8">
              <importResult-data
                type="1"
                btnTitle="导入成果奖"
                @backQuery="backQuery"
              >
              </importResult-data>
              </a-col>
            </a-row>
            <a-row style="margin: 3px 0px">
              <a-col :span="8">&nbsp;
              </a-col>
              <a-col :span="8">
                <a-button @click="exportYw" style="width:170px"><a-icon type="download" />导出院外打分模板</a-button>
              </a-col>
              <a-col :span="8">
              <importResult-data
                type="2"
                btnTitle="批量院外打分"
                @backQuery="backQuery"
              >
              </importResult-data>
              </a-col>
            </a-row>
            <a-row style=" margin: 3px 0px">
              <a-col :span="8">
                <a-upload
                  accept=".xlsx"
                  :fileList="fileList"
                  :beforeUpload="beforeUpload"
                >
                  <a-button>
                    <a-icon type="upload"  /> 上传批量关联文件
                  </a-button>
                </a-upload>
              </a-col>
              <a-col :span="8">
                <a-button @click="exportYn" style="width:170px"><a-icon type="download" />导出院内打分模板</a-button>
              </a-col>
              <a-col :span="8">
              <importResult-data
                type="3"
                btnTitle="批量院内打分"
                @backQuery="backQuery"
              >
              </importResult-data>
              </a-col>
            </a-row>
          </a-col>
          <span style="float: right; margin: 5px 0px">
            <a-button type="primary" @click="search">查询</a-button>
            <a-button style="margin-left: 8px" @click="reset">重置</a-button>
            <a @click="toggleAdvanced" style="margin-left: 8px">
              {{ advanced ? "收起" : "展开" }}
              <a-icon :type="advanced ? 'up' : 'down'" />
            </a>
          </span>
        </a-row>
      </a-form>
    </div>
    <div>
      <!-- 表格区域 -->
      <a-table
        ref="TableInfo"
        :columns="columns"
        :rowKey="(record) => record.id"
        :dataSource="dataSource"
        :pagination="pagination"
        :loading="loading"
        :rowSelection="{
          selectedRowKeys: selectedRowKeys,
          onChange: onSelectChange,
        }"
        @change="handleTableChange"
        :bordered="bordered"
        :scroll="{ x: 900 }"
      >
        <template slot="remark" slot-scope="text, record">
          <a-popover placement="topLeft">
            <template slot="content">
              <div style="max-width: 200px">{{ text }}</div>
            </template>
            <p style="width: 200px; margin-bottom: 0">{{ text }}</p>
          </a-popover>
        </template>
        <template slot="ywtotal" slot-scope="text, record">
          <a v-if="text != null && text != ''" @click="openDf(record,1)">{{text}}</a>
          <label v-else>{{text}}</label>
        </template>
        <template slot="yntotal" slot-scope="text, record">
          <a v-if="text != null && text != ''" @click="openDf(record,2)">{{text}}</a>
          <label v-else>{{text}}</label>
        </template>
        <template slot="operation" slot-scope="text, record">
          <a-icon
            v-hasPermission="['xxbBResult:view']"
            type="eye"
            theme="twoTone"
            twoToneColor="#42b983"
            @click="view(record)"
            title="查看"></a-icon>
          <a-divider type="vertical" />
          <a-icon 
            type="download"
            @click="download(record)"
            title="下载申报表"
           ></a-icon>
           <a-divider type="vertical" />
          <a-icon 
            type="fire"
            @click="downloadExcel(record)"
            title="下载申报表附件"
           ></a-icon>
           <a-divider v-if="record.filename ? true : false" type="vertical" />
           <a-icon type="link" 
            v-if="record.filename ? true : false"
            @click="downloadGlExcel(record)"
            title="下载关联文件"
           />
        </template>
      </a-table>
    </div>
    <xxbBR-flow
      ref="xxbBRFlow"
      @close="handleEditClose"
      @success="handleEditSuccess"
      :editVisiable="editVisiable"
    >
    </xxbBR-flow>
    <a-modal :maskClosable="false" :footer="null" v-model="lookVisiable" width="85%" :title="openTitle" @ok="handleLookOk">
      <xxbBResultP-look
        ref="xxbBResultPLook"
      >
      </xxbBResultP-look>
    </a-modal>
  </a-card>
</template>

<script>
import moment from "moment";
import XxbBRFlow from "../XxbBRFlow";
import XxbBResultPLook from "./XxbBResultP";
import ImportResultData from "../../common/ImportResultData";
const formItemLayout = {
  labelCol: { span: 7 },
  wrapperCol: { span: 16, offset: 1 },
};
export default {
  name: "XxbBRReport",
  components: { XxbBRFlow, ImportResultData, XxbBResultPLook },
  data() {
    return {
      advanced: false,
      dataSource: [],
      selectedRowKeys: [],
      sortedInfo: null,
      paginationInfo: null,
      formItemLayout,
      pagination: {
        pageSizeOptions: ["10", "20", "30", "40", "100"],
        defaultCurrent: 1,
        defaultPageSize: 10,
        showQuickJumper: true,
        showSizeChanger: true,
        showTotal: (total, range) =>
          `显示 ${range[0]} ~ ${range[1]} 条记录，共 ${total} 条记录`,
      },
      queryParams: { },
      editVisiable: false,
      isDf: false,
      lookVisiable: false,
      size: 10,
      deptData: [],
      openTitle: '',
      fileList: [],
      loading: false,
      bordered: true,
    };
  },
  computed: {
    columns() {
      let { sortedInfo } = this;
      sortedInfo = sortedInfo || {};
      return [
        {
          title: "申报科室",
          dataIndex: "deptNew",
          fixed: "left",
          width: 120,
        },
        {
          title: "项目名称",
          dataIndex: "projectName",
          fixed: "left",
          width: 250,
        },
        {
          title: "申报日期",
          dataIndex: "applydat",
          customRender: (text, row, index) => {
            if (text == null) return "";
            return moment(text).format("YYYY-MM-DD");
          },
          width: 100,
        },
        {
          title: "项目负责人",
          dataIndex: "userAccountName",
          customRender: (text, row, index) => {
            return row.userAccountName + ' ' + row.userAccount + ' ' + row.yggh
          },
          width: 200,
        },
        {
          title: "开展日期",
          dataIndex: "kzsrtdat",
          customRender: (text, row, index) => {
            if (text == null) return "";
            return moment(text).format("YYYY-MM-DD");
          },
          width: 100,
        },
        {
          title: "开展例数",
          dataIndex: "kzls",
          width: 100,
        },
        {
          title: "评奖年度",
          dataIndex: "pfnd",
          width: 100,
        },
        {
          title: "院外评审得分",
          dataIndex: "ywtotal",
          scopedSlots: { customRender: "ywtotal" },
          width: 100,
        },
        {
          title: "院内评审得分",
          dataIndex: "yntotal",
          scopedSlots: { customRender: "yntotal" },
          width: 100,
        },
        {
          title: "最终得分",
          dataIndex: "abtotal",
          width: 100,
        },
        {
          title: "导入分数日期",
          dataIndex: "importdat",
          customRender: (text, row, index) => {
            if (text == null) return "";
            return moment(text).format("YYYY-MM-DD");
          },
          width: 100,
        },
        {
          title: "获奖情况",
          dataIndex: "hjqk",
          width: 100,
        },
        {
          title: "审核状态",
          dataIndex: "state",
          customRender: (text, row, index) => {
            switch (text) {
              case 0:
                return '未提交'
              case 1:
                return '已提交'
              case 2:
                return '已审核'
              case 3:
                return '已退回'
              case 9:
                return '终止申报'
              default:
                return text
            }
          },
          fixed: "right",
          width: 80,
        },
        {
          title: "操作",
          dataIndex: "operation",
          scopedSlots: { customRender: "operation" },
          fixed: "right",
          width: 160,
        },
      ];
    },
  },
  mounted() {
    this.fetchDept()
    this.fetch();
  },
  methods: {
    moment,
    handleLookOk () {
      this.lookVisiable = false
    },
    fetchDept() {
      this.$get("sdlBUser/deptNew", {}).then((res) => {
        this.deptData = [];
        this.deptData.push(...res.data);
      });
    },
    openDf(record, state) {
      this.lookVisiable = true
      this.openTitle = state == 1 ? '浏览院外评审得分' : '浏览院内评审得分'
      setTimeout(() => {
        this.$refs.xxbBResultPLook.setFieldValues(record, state);
      }, 100);
    },
    onSqStartChange (date, dateString) {
      this.queryParams.applydatFrom = dateString;
    },
    onSqEndChange (date, dateString) {
      this.queryParams.applydatTo = dateString;
    },
    onImportStartChange (date, dateString) {
      this.queryParams.importdatFrom = dateString;
    },
    onImportEndChange (date, dateString) {
      this.queryParams.importdatTo = dateString;
    },
    backQuery () {
      this.search();
    },
    isDfChange () {
      this.search();
    },
    onSelectChange(selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys;
    },
    toggleAdvanced() {
      this.advanced = !this.advanced;
      if (!this.advanced) {
        this.queryParams.comments = "";
      }
    },
    handleEditSuccess() {
      this.editVisiable = false;
      this.search();
    },
    handleEditClose() {
      this.editVisiable = false;
    },
    view(record) {
      this.$refs.xxbBRFlow.setFormValues(record,'查看','成果奖申报书 医务处审核');
      this.editVisiable = true;
    },
    beforeUpload (file) {
      var testmsg = file.name.substring(file.name.lastIndexOf('.') + 1)
      testmsg = testmsg.toLowerCase()
      let isExcel = testmsg === 'xlsx'
      // const isExcel = file.type === 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
      if (!(isExcel)) {
        this.$error({
          title: '只能上传.xlsx格式的Excel文档~'
        })
        return
      }
      const isLt3M = file.size / 1024 < 3001
      if (!isLt3M) {
        this.$error({
          title: '文件超3MB限制，不允许上传~'
        })
        return
      }
      return isExcel && isLt3M && this.customRequest(file)
    },
    customRequest (file) {
      if (!this.selectedRowKeys.length) {
        this.$message.warning("请选择需要关联文件的记录");
        return;
      }
      const formData = new FormData()
      let ids = this.selectedRowKeys.join(",")
      formData.append('file', file)
      formData.append('ids', ids)
      this.$upload('xxbBResult/uploadGlFile', formData).then((r) => {
        if (r.data.data.success === 1) {
          this.search();
        } else {
          this.$message.error(r.data.data.message)
        }
      }).catch(() => {
        this.$message.error('关联文件上传失败.')
      })
    },
    exportCgj () {
      if (!this.selectedRowKeys.length) {
        this.$message.warning("请选择需要导出成果奖的记录");
        return;
      }
      let formData = {}
      formData.ids = this.selectedRowKeys.join(",");
      this.$download('xxbBResult/resultExcel', {
      ...formData
      }, `${new Date().getTime()}_` + '成果奖模板.xlsx')
    },
    exportYw () {
      if (!this.selectedRowKeys.length) {
        this.$message.warning("请选择需要导出院外打分的记录");
        return;
      }
      let formData = {}
      formData.ids = this.selectedRowKeys.join(",");
      this.$download('xxbBResult/resultYwExcel', {
      ...formData
      }, `${new Date().getTime()}_` + '院外打分模板.xlsx')
    },
    exportYn () {
      if (!this.selectedRowKeys.length) {
        this.$message.warning("请选择需要导出院内打分的记录");
        return;
      }
      let formData = {}
      formData.ids = this.selectedRowKeys.join(",");
      this.$download('xxbBResult/resultYnExcel', {
      ...formData
      }, `${new Date().getTime()}_` + '院内打分模板.xlsx')
    },
    download (record) {
      let formData = {}
      formData.id = record.id
      this.$download('xxbBResult/downloadFile', {
        ...formData
      }, `${new Date().getTime()}_` + record.deptNew + '-' + 
        record.projectName + '-' + record.userAccountName + '.pdf')
    },
    downloadExcel (record) {
      let formData = {}
      formData.id = record.id
      this.$download('xxbBResult/excelFile', {
        ...formData
      }, `${new Date().getTime()}_` + record.deptNew + '-' + 
        record.projectName + '-' + record.userAccountName + '.xlsx')
    },
    downloadGlExcel (record) {
      let formData = {}
      formData.id = record.id
      this.$download('xxbBResult/excelGlFile', {
        ...formData
      }, record.filename)
    },
    changeState () {
      this.search()
    },
    search() {
      let { sortedInfo } = this;
      let sortField, sortOrder;
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field;
        sortOrder = sortedInfo.order;
      }
      if (this.paginationInfo) {
        this.paginationInfo.current = this.pagination.defaultCurrent;
      }
      this.fetch({
        sortField: sortField,
        sortOrder: sortOrder,
        ...this.queryParams,
      });
    },
    reset() {
      // 取消选中
      this.selectedRowKeys = [];
      // 重置分页
      this.$refs.TableInfo.pagination.current = this.pagination.defaultCurrent;
      if (this.paginationInfo) {
        this.paginationInfo.current = this.pagination.defaultCurrent;
        this.paginationInfo.pageSize = this.pagination.defaultPageSize;
      }
      // 重置列排序规则
      this.sortedInfo = null;
      this.paginationInfo = null;
      // 重置查询参数
      this.queryParams = {};
      this.isDf = false
      this.fetch();
    },
    handleTableChange(pagination, filters, sorter) {
      this.sortedInfo = sorter;
      this.paginationInfo = pagination;
      this.fetch({
        sortField: sorter.field,
        sortOrder: sorter.order,
        ...this.queryParams,
      });
    },
    fetch(params = {}) {
      this.loading = true;
      if (this.paginationInfo) {
        // 如果分页信息不为空，则设置表格当前第几页，每页条数，并设置查询分页参数
        this.$refs.TableInfo.pagination.current = this.paginationInfo.current;
        this.$refs.TableInfo.pagination.pageSize = this.paginationInfo.pageSize;
        params.pageSize = this.paginationInfo.pageSize;
        params.pageNum = this.paginationInfo.current;
      } else {
        // 如果分页信息为空，则设置为默认值
        params.pageSize = this.pagination.defaultPageSize;
        params.pageNum = this.pagination.defaultCurrent;
      }
      params.sortField = 'create_Time'
      params.sortOrder = 'descend'
      params.isDf = this.isDf
      if(params.deptNew == undefined) {
        params.deptNew = null
      }
      if(params.state == undefined) {
        params.state = null
      }
      this.$get("xxbBResult/reportList", {
        ...params,
      }).then((r) => {
        let data = r.data;
        const pagination = { ...this.pagination };
        pagination.total = data.total;
        this.loading = false;
        this.dataSource = data.rows;
        this.pagination = pagination;
      });
    },
  },
};
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
