<template>
  <a-card :bordered="false" class="card-area">
    <div :class="advanced ? 'search' : null">
      <a-form layout="horizontal">
        <a-row>
          <div :class="advanced ? null : 'fold'"></div>
          <a-col :md="4" :sm="24">
            <a-form-item label="项目名称" v-bind="formItemLayout">
              <a-input v-model="queryParams.projectName" />
            </a-form-item>
          </a-col>
           <a-col :md="4" :sm="24">
            <a-form-item label="项目类型" v-bind="formItemLayout">
              <a-select v-model="queryParams.projectType">
                  <a-select-option :value="-1">全部</a-select-option>
                 <a-select-option :value="0">检验检查类</a-select-option>
                 <a-select-option :value="1">临床类-单科申报</a-select-option>
                 <a-select-option :value="2">临床类-多科联合申报</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
           <a-col :md="4" :sm="24">
            <a-form-item label="审核状态" v-bind="formItemLayout">
              <a-select v-model="queryParams.state">
                  <a-select-option :value="-1">全部</a-select-option>
                 <a-select-option :value="0">未提交</a-select-option>
                 <a-select-option :value="1">已提交</a-select-option>
                 <a-select-option :value="2">已审核</a-select-option>
                 <a-select-option :value="3">审核未通过</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :md="4" :sm="24">
            <a-form-item label="上会状态" v-bind="formItemLayout">
              <a-select v-model="queryParams.shstate">
                  <a-select-option :value="-1">全部</a-select-option>
                 <a-select-option :value="1">上会通过</a-select-option>
                 <a-select-option :value="2">上会未通过</a-select-option>
                 <a-select-option :value="3">未上会</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
           <a-col :md="4" :sm="24">
            <a-form-item label="项目进度状态" v-bind="formItemLayout">
              <a-select v-model="queryParams.xmjdstate">
                  <a-select-option :value="-1">全部</a-select-option>
                 <a-select-option :value="1">已开始</a-select-option>
                 <a-select-option :value="2">中期总结已提交</a-select-option>
                 <a-select-option :value="3">中期总结已驳回重填</a-select-option>
                 <a-select-option :value="4">继续开展</a-select-option>
                 <a-select-option :value="5">中止开展</a-select-option>
                 <a-select-option :value="6">项目延期 </a-select-option>
                 <a-select-option :value="7">总结反馈已提交</a-select-option>
                 <a-select-option :value="8">总结反馈已驳回重填</a-select-option>
                 <a-select-option :value="9">转化为常规技术</a-select-option>
                 <a-select-option :value="10">终止开展</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
           <template v-if="advanced">
          <a-col :md="8" :sm="24">
            <a-form-item label="申请日期" v-bind="formItemLayout">
              <a-date-picker @change="onSqStartChange" style="width:45%" />- 
              <a-date-picker @change="onSqEndChange" style="width:45%"/>
            </a-form-item>
          </a-col>
           <a-col :md="8" :sm="24">
            <a-form-item label="开始日期" v-bind="formItemLayout">
              <a-date-picker @change="(a,b)=>onDateChange(a,b,'srtdatFrom')" style="width:45%" />- 
              <a-date-picker @change="(a,b)=>onDateChange(a,b,'srtdatTo')" style="width:45%"/>
            </a-form-item>
          </a-col>
          <a-col :md="8" :sm="24">
            <a-form-item label="结束日期" v-bind="formItemLayout">
              <a-date-picker @change="(a,b)=>onDateChange(a,b,'enddatFrom')" style="width:45%" />- 
              <a-date-picker @change="(a,b)=>onDateChange(a,b,'enddatTo')" style="width:45%"/>
            </a-form-item>
          </a-col>
           <a-col :md="8" :sm="24">
            <a-form-item label="中期反馈日期" v-bind="formItemLayout">
              <a-date-picker @change="(a,b)=>onDateChange(a,b,'zqDateFrom')" style="width:45%" />- 
              <a-date-picker @change="(a,b)=>onDateChange(a,b,'zqDateTo')" style="width:45%"/>
            </a-form-item>
          </a-col>
           <a-col :md="8" :sm="24">
            <a-form-item label="总结反馈日期" v-bind="formItemLayout">
              <a-date-picker @change="(a,b)=>onDateChange(a,b,'mqDateFrom')" style="width:45%" />- 
              <a-date-picker @change="(a,b)=>onDateChange(a,b,'mqDateTo')" style="width:45%"/>
            </a-form-item>
          </a-col>
           </template>
          <span style="float: right; margin-top: 3px">
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
      <div class="operator">
          <a-button  type="primary" ghost @click="exportExcel">导出</a-button>
        
        <!-- <import-excel
          v-hasPermission="['xxbBCheck:import']"
          templateUrl="xxbBCheck/downTemplate"
          @succ="handleRefesh"
          url="xxbBCheck/import"
        >
        </import-excel> -->
      </div>
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
     <template slot="operation" slot-scope="text, record">
          <a-icon
            type="eye"
            theme="twoTone"
            twoToneColor="#42b983"
            @click="view(record)"
            title="查看"></a-icon>
             <a-divider type="vertical" />
          <a-icon
            type="message"
            theme="twoTone"
            twoToneColor="#4a9ff5"
            @click="flowLook(record)"
            title="流程"
          ></a-icon>
          <a-divider type="vertical" />
          <a-icon 
            type="download"
            @click="download(record)"
            title="下载pdf"
           ></a-icon>
          <a-divider type="vertical" />
          <a-icon
            type="cloud"
            theme="twoTone"
            twoToneColor="#42b983"
            @click="viewZq(record)"
            title="中期报告"></a-icon>
            <a-divider type="vertical" />
             <a-icon
            type="file"
            theme="twoTone"
            twoToneColor="#42b983"
            @click="viewMq(record)"
            title="末期报告"></a-icon>
            <a-divider type="vertical" />
            <a-icon 
           
            type="down"
            @click="download_zq(record)"
            title="下载中期报告文档"
           ></a-icon>
           <a-divider type="vertical" />
            <a-icon 
           
            type="export"
            @click="download_mq(record)"
            title="下载总结报告文档"
           ></a-icon>
        </template>
      </a-table>
    </div>
    <a-modal :maskClosable="false"
      :footer="null" 
      v-model="lookVisiable" 
      width="860px" 
      @ok="handleLookOk"
    >
      <xxbB-show
        ref="xxbBShow"
        @open="openEdit"
        @close="closeLook"
      >
      </xxbB-show>
    </a-modal>
  <xxbB-flow
      ref="xxbBFlow"
      @close="handleEditClose"
      @success="handleEditSuccess"
      :editVisiable="editVisiable"
    >
    </xxbB-flow>
    <xxb-b-zq-view
     ref="zqView"
      :baseId="editId"
      @close="handleZqEditClose"
      :editVisiable="editZqVisiable"
    >

    </xxb-b-zq-view>
    <xxb-b-mq-view
       ref="mqView"
      :baseId="editId"
      @close="handleMqEditClose"
      :editVisiable="editMqVisiable"
    >
    </xxb-b-mq-view>
    
    <a-modal :maskClosable="false" :footer="null" v-model="lookFlowVisiable" width="85%" title="浏览流程" @ok="handleLookFlowOk">
      <xxbBDeptFlow-look
        ref="xxbBDeptFlowLook"
      >
      </xxbBDeptFlow-look>
    </a-modal>
  </a-card>
</template>

<script>
// import XxbBCheckAdd from "./XxbBCheckAdd";
import XxbBShow from "../XxbBShow";

import XxbBDeptFlowLook from "../XxbBDeptFlowLook";
// import ImportExcel from "../../common/ImportExcel";
import moment from "moment";
import XxbBFlow from '../XxbBFlow.vue';
import XxbBMqView from '../XxbBMq/XxbBMqView.vue'
import XxbBZqView from '../XxbBZq/XxbBZqView.vue'

const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 15, offset: 1 },
};
export default {
  name: "XxbBCheck",
  components: { XxbBShow, XxbBDeptFlowLook,XxbBFlow,XxbBMqView, XxbBZqView},
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
      projectType: "",
      queryParams: {},
      addVisiable: false,
      editVisiable: false,
      lookVisiable: false,
      lookFlowVisiable: false,
      loading: false,
      bordered: true,
      editId: '',
      editMqVisiable: false,
      editZqVisiable: false,

    };
  },
  computed: {
    columns() {
      let { sortedInfo } = this;
      sortedInfo = sortedInfo || {};
      return [
           {
          title: "项目类型",
          dataIndex: "projectType",
          customRender: (text, row, index) => {
            switch (text) {
              case 0:
                return '检验检查类'
              case 1:
                return '临床类-单科申报'
              case 2:
                return '临床类-多科联合申报'
              default:
                return text
            }
          },
          fixed: "left",
          width: 100,
        },
        {
          title: "申请科室",
          dataIndex: "deptNew",
          fixed: "left",
          width: 120,
        },
        {
          title: "项目名称",
          dataIndex: "projectName",
          fixed: "left",
          width: 200,
        },
        {
          title: "是否为限制类医疗技术",
          dataIndex: "isxzyljs",
          customRender: (text, row, index) => {
            switch (text) {
              case 0:
                return '否'
              case 1:
                return '是'
              default:
                return text
            }
          },
          width: 100,
        },
        {
          title: "项目级别",
          dataIndex: "projectLevel",
          customRender: (text, row, index) => {
            switch (text) {
              case 1:
                return '一级'
              case 2:
                return '二级'
              case 3:
                return '三级'
              case 4:
                return '四级'
              default:
                return text
            }
          },
          width: 80,
        },
        {
          title: "申请日期",
          dataIndex: "applydat",
          customRender: (text, row, index) => {
            if (text == null) return "";
            return moment(text).format("YYYY-MM-DD");
          },
          width: 100,
        },
         {
          title: "开始日期",
          dataIndex: "srtdat",
          customRender: (text, row, index) => {
            if (text == null) return "";
            return moment(text).format("YYYY-MM-DD");
          },
          width: 100,
        },
        {
          title: "结束日期",
          dataIndex: "enddat",
          customRender: (text, row, index) => {
            if (text == null) return "";
            return moment(text).format("YYYY-MM-DD");
          },
          width: 100,
        },
         {
          title: "中期反馈日期",
          dataIndex: "zqDate",
          customRender: (text, row, index) => {
            if (text == null) return "";
            return moment(text).format("YYYY-MM-DD");
          },
          width: 100,
        },
         {
          title: "总结反馈日期",
          dataIndex: "mqDate",
          customRender: (text, row, index) => {
            if (text == null) return "";
            return moment(text).format("YYYY-MM-DD");
          },
          width: 100,
        },
        {
          title: "项目延期日期",
          dataIndex: "yanzhanDate",
          customRender: (text, row, index) => {
            if (text == null) return "";
            return moment(text).format("YYYY-MM-DD");
          },
          width: 100,
        },
         {
          title: "参与专科",
          dataIndex: "comments",
          width: 220,
        },
        {
          title: "项目负责人",
          dataIndex: "userAccountName",
          customRender: (text, row, index) => {
            return row.userAccountName + ' ' + row.userAccount + ' ' + row.yggh
          },
          width: 150,
        },
        {
          title: "第一操作者的最低级别",
          dataIndex: "lev",
          customRender: (text, row, index) => {
            switch (text) {
              case 1:
                return '正高级'
              case 2:
                return '副高级以上'
              case 3:
                return '中级以上'
              case 4:
                return '初级以上'
              case 5:
                return '其他'
              default:
                return text
            }
          },
          width: 100,
        },
        {
          title: "第一操作者类别",
          dataIndex: "typ",
          customRender: (text, row, index) => {
            switch (text) {
              case 1:
                return '医师'
              case 2:
                return '护士'
              case 3:
                return '药剂人员'
              case 4:
                return '技术人员'
              case 5:
                return '其他'
              default:
                return text
            }
          },
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
              default:
                return text
            }
          },
          width: 80,
        },
        {
          title: "上会状态",
          dataIndex: "shstate",
          customRender: (text, row, index) => {
            switch (text) {
              case 0:
                return ''
              case 1:
                return <a-tag color="green">通过</a-tag>;
              case 2:
                return <a-tag color="red">未通过</a-tag>;
              default:
                return text
            }
          },
          width: 80,
        },
         {
          title: "项目进度状态",
          dataIndex: "xmjdstate",
          customRender: (text, row, index) => {
            switch (text) {
              case 1:
                return "已开始";
              case 2:
                return "中期总结已提交";
              case 3:
                return "中期总结已驳回重填";
              case 4:
                return "继续开展";
              case 5:
                return "中止开展";
              case 6:
                return "项目延期";
              case 7:
                return '总结反馈已提交';
              case 8:
                return '总结反馈已驳回重填';
              case 9:
                return '转化为常规技术';
              case 10:
                return '终止开展';
              default:
                return '';
            }
          },
          width: 100,
        },
        {
          title: "操作",
          dataIndex: "operation",
          scopedSlots: { customRender: "operation" },
          fixed: "right",
          width: 200,
        },
      ];
    },
  },
  mounted() {
    this.fetch();
  },
  methods: {
    moment,
    handleLookFlowOk () {
      this.lookFlowVisiable = false
    },
    flowLook(record) {
      this.lookFlowVisiable = true;
      setTimeout(() => {
        this.$refs.xxbBDeptFlowLook.setFieldValues(record.id, 1, record.state,0);
      }, 100);
    },
    closeLook() {
      this.lookVisiable = false
    },
    handleLookOk(){
      this.lookVisiable = false
    },
    onSqStartChange (date, dateString) {
        if(date==null){
            delete this.queryParams.applydatFrom
        }
        else{
          this.queryParams.applydatFrom = dateString;
        }
    },
    onSqEndChange (date, dateString) {
        if(date==null){
            delete this.queryParams.applydatTo
        }
        else{
            this.queryParams.applydatTo = dateString;
        }
    },
    onDateChange(date,dateString,filedName){
         if(date==null){
            delete this.queryParams[filedName]
        }
        else{
            this.queryParams[filedName] = dateString;
        }
    },
    handleRefesh() {
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
    handleAddSuccess() {
      this.addVisiable = false;
      this.$message.success("新增成功");
      this.search();
    },
    handleAddClose() {
      this.addVisiable = false;
    },
    download (record) {
      let formData = {}
      formData.id = record.id
      this.$download('xxbBCheck/downloadFile', {
        ...formData
      }, `${new Date().getTime()}_` + record.deptNew + '-' + 
        record.projectName + '-' + record.userAccountName + '.pdf')
    },
    download_zq (record) {
      this.$download('xxbBZq/doc', {
        baseId: record.id
      }, `${new Date().getTime()}_` + 
        record.projectName + '_中期反馈表.docx')
    },
    download_mq (record) {
      this.$download('xxbBMq/doc', {
        baseId: record.id
      }, `${new Date().getTime()}_` + 
        record.projectName + '_总结反馈表.docx')
    },
    add() {
      // this.addVisiable = true;
      // this.$refs.xxbBCheckAdd.setFormValues();
      this.lookVisiable = true;
      setTimeout(() => {
        this.$refs.xxbBShow.show()
      }, 100);
    },
    openEdit () {
      this.lookVisiable = false
      this.editVisiable = true;
      this.$refs.xxbBCheckEdit.setFormValues(null,'新增',this.projectType);
    },
   handleZqEditClose() {
      this.editZqVisiable = false;
    },
    handleMqEditClose() {
      this.editMqVisiable = false;
    },
    handleEditSuccess() {
      this.editVisiable = false;
      this.$message.success("提交成功");
      this.search();
    },
    handleEditClose() {
      this.editVisiable = false;
    },
    handleType(type){
      if(type==0) return '检验检查类'
      if(type==1) return '临床类-单科申报'
      if(type==2) return '临床类-多科联合申报'
    },
    view(record) {
      this.$refs.xxbBFlow.setFormValues(record,'查看',this.handleType(record.projectType));
      this.editVisiable = true;
    },
    viewZq(record) {
      this.editId= record.id;
      this.$refs.zqView.setProject(record);
      this.editZqVisiable = true;
    },
     viewMq(record) {
       this.editId= record.id;
      this.$refs.mqView.setProject(record);
      this.editMqVisiable = true;
    },
    edit(record) {
      this.$refs.xxbBFlow.setFormValues(record,'查看',this.handleType(record.projectType));
      this.editVisiable = true;
    },
    batchDelete() {
      if (!this.selectedRowKeys.length) {
        this.$message.warning("请选择需要删除的记录");
        return;
      }
      let that = this;
      this.$confirm({
        title: "确定删除所选中的记录?",
        content: "当您点击确定按钮后，这些记录将会被彻底删除",
        centered: true,
        onOk() {
          let xxbBCheckIds = that.selectedRowKeys.join(",");
          that.$delete("xxbBCheck/" + xxbBCheckIds).then(() => {
            that.$message.success("删除成功");
            that.selectedRowKeys = [];
            that.search();
          });
        },
        onCancel() {
          that.selectedRowKeys = [];
        },
      });
    },
    exportExcel() {
      
      let params =  {...this.queryParams}
       params.sortField = 'create_Time'
      params.sortOrder = 'descend'

      if(params.projectType=='-1'){
          delete params.projectType
      }
       if(params.state=='-1'){
          delete params.state
      }
       if(params.shstate=='-1'){
          delete params.shstate
      }
       if(params.xmjdstate=='-1'){
          delete params.xmjdstate
      }
      params.yggh= this.$store.state.account.user.username; //获取当前审核人
      this.$export("xxbBCheck/excel", {
        ...params,
      });
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

      if(params.projectType=='-1'){
          delete params.projectType
      }
       if(params.state=='-1'){
          delete params.state
      }
       if(params.shstate=='-1'){
          delete params.shstate
      }
       if(params.xmjdstate=='-1'){
          delete params.xmjdstate
      }
      params.yggh= this.$store.state.account.user.username; //获取当前审核人
      this.$get("xxbBCheck/report", {
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
