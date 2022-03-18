<template>
  <a-card :bordered="false" class="card-area">
    <div :class="advanced ? 'search' : null">
      <a-form layout="horizontal">
        <a-row>
          <div :class="advanced ? null : 'fold'">
            <a-col :md="5" :sm="24">
                 <a-form-item v-bind="formItemLayout" label="科室">
                <a-select v-model="queryParams.deptId"
                  option-filter-prop="children"
         :filter-option="filterOption"
         show-search>
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
            <a-col :md="5" :sm="24">
             <a-form-item label="发薪号/姓名" v-bind="formItemLayout">
                <a-input v-model="queryParams.userAccount" />
              </a-form-item>
            </a-col>
             <a-col :md="7" :sm="24">
              <a-form-item label="变更日期" v-bind="formItemLayout2">
                 <a-date-picker style="width:45%"
          @change="handleStartDateChange"
        />-<a-date-picker style="width:45%"
          @change="handleEndDateChange"
        />
              </a-form-item>
              </a-col>
               <a-col :md="7" :sm="24">
              <a-form-item label="任职时间" v-bind="formItemLayout2">
                  <a-date-picker
          @change="handleRzChange" style="width:45%"
        />-<a-date-picker style="width:45%"
          @change="handleRzEndChange"
        />
              </a-form-item>
               </a-col>
          </div>
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
        <a-button
          
          type="primary"
          ghost
          @click="add"
          >新增{{type}}</a-button
        >
        <a-button  @click="batchDelete"
          >删除</a-button
        >
        <!-- <a-dropdown >
          <a-menu slot="overlay">
            <a-menu-item key="export-data" @click="exportExcel"
              >导出Excel</a-menu-item
            >
          </a-menu>
          <a-button>
            更多操作
            <a-icon type="down" />
          </a-button>
        </a-dropdown> -->
        <!-- <import-excel
          v-hasPermission="['mdlBManager:import']"
          templateUrl="mdlBManager/downTemplate"
          @succ="handleRefesh"
          url="mdlBManager/import"
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
            type="setting"
            theme="twoTone"
            twoToneColor="#4a9ff5"
            @click="edit(record)"
            title="修改"
          ></a-icon>
        </template>
      </a-table>
    </div>
    <!-- 新增字典 -->
    <mdlBManager-add
      :type="type"
      @close="handleAddClose"
      @success="handleAddSuccess"
      :addVisiable="addVisiable"
    >
    </mdlBManager-add>
    <!-- 修改字典 -->
    <mdlBManager-edit
      ref="mdlBManagerEdit"
      :type="type"
      @close="handleEditClose"
      @success="handleEditSuccess"
      :editVisiable="editVisiable"
    >
    </mdlBManager-edit>
  </a-card>
</template>

<script>
import MdlBManagerAdd from "./MdlBManagerAdd";
import MdlBManagerEdit from "./MdlBManagerEdit";
import moment from "moment";

const formItemLayout2 = {
  labelCol: { span: 6 },
  wrapperCol: { span: 18 },
};
const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 16 },
};
export default {
  name: "MdlBManager",
  components: { MdlBManagerAdd, MdlBManagerEdit },
  data() {
    return {
      advanced: false,
      dataSource: [],
      selectedRowKeys: [],
      sortedInfo: null,
      paginationInfo: null,
      formItemLayout,
      formItemLayout2,
      pagination: {
        pageSizeOptions: ["10", "20", "30", "40", "100"],
        defaultCurrent: 1,
        defaultPageSize: 10,
        showQuickJumper: true,
        showSizeChanger: true,
        showTotal: (total, range) =>
          `显示 ${range[0]} ~ ${range[1]} 条记录，共 ${total} 条记录`,
      },
      queryParams: {},
      addVisiable: false,
      editVisiable: false,
      loading: false,
      bordered: true,
      deptData: []
    };
  },
  props: {
     type: {
       default: ''
     }
  },
  computed: {
    columns() {
      let { sortedInfo } = this;
      sortedInfo = sortedInfo || {};
      return [
        {
          title: "科室名称",
          dataIndex: "deptId",
          width: 100,
        },
        {
          title: "类型",
          dataIndex: "type",
          width: 100,
        },
        {
          title: "发薪号",
          dataIndex: "userAccount",
          width: 100,
        },
           {
          title: "姓名",
          dataIndex: "userAccountName",
          width: 100,
        },
        {
          title: "出生年月",
          dataIndex: "birthday",
          width: 100,
        },
        {
          title: "手机号",
          dataIndex: "tel",
          width: 100,
        },
        {
          title: "电子邮箱",
          dataIndex: "email",
          width: 100,
        },
         {
          title: "任职开始时间",
          dataIndex: "startDate",
          width: 100,
           customRender: (text, row, index) => {
            if (text == null) return "";
            return moment(text).format("YYYY-MM-DD");
          },
        },
          {
          title: "任职结束时间",
          dataIndex: "endDate",
           customRender: (text, row, index) => {
            if (text == null) return "";
            return moment(text).format("YYYY-MM-DD");
          },
          width: 100,
        },
        {
          title: "操作",
          dataIndex: "operation",
          scopedSlots: { customRender: "operation" },
          fixed: "right",
          width: 100,
        },
      ];
    },
  },
  mounted() {
    this.fetchDept();
    this.fetch();
    
  },
  
  methods: {
    moment,
    handleRefesh() {
      this.search();
    },
    fetchDept() {
      this.$get("sdlBUser/deptNew", {  }).then((res) => {
        this.deptData = [];
         this.deptData.push({
          deptId: "-1",
          deptName: "全部",
        });
       if(res.data[0]!=null){
         this.deptData.push(...res.data);
        }
      });
    },
    filterOption(input, option) {
      return (
        option.componentOptions.children[0].text
          .toLowerCase()
          .indexOf(input.toLowerCase()) >= 0
      );
    },
    onSelectChange(selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys;
    },
    handleStartDateChange(value) {
      if(value==null){
         delete this.queryParams.modifyTimeFrom
      }
      else{
        this.queryParams.modifyTimeFrom = moment(value).format("YYYY-MM-DD");
      }
    },
    handleEndDateChange(value) {
      if(value==null){
         delete this.queryParams.modifyTimeTo
      }
      else{
      this.queryParams.modifyTimeTo = moment(value).format("YYYY-MM-DD");
      }
    },
    handleRzChange(value){
      if(value==null){
        delete this.queryParams.startDateFrom
      }
      else{
        this.queryParams.startDateFrom = moment(value).format("YYYY-MM-DD");
      }
    },
      handleRzEndChange(value){
      if(value==null){
        delete this.queryParams.startDateTo
      }
      else{
        this.queryParams.startDateTo = moment(value).format("YYYY-MM-DD");
      }
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
    add() {
      this.addVisiable = true;
    },
    handleEditSuccess() {
      this.editVisiable = false;
      this.$message.success("修改成功");
      this.search();
    },
    handleEditClose() {
      this.editVisiable = false;
    },
    edit(record) {
      this.$refs.mdlBManagerEdit.setFormValues(record);
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
          let mdlBManagerIds = that.selectedRowKeys.join(",");
          that.$delete("mdlBManager/" + mdlBManagerIds).then(() => {
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
      let { sortedInfo } = this;
      let sortField, sortOrder;
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field;
        sortOrder = sortedInfo.order;
      }
      this.$export("mdlBManager/excel", {
        sortField: sortField,
        sortOrder: sortOrder,
        ...this.queryParams,
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
      let queryParams ={...this.queryParams}
      if(queryParams.deptId=="-1"){
        delete queryParams.deptId
      }
      this.fetch({
        sortField: sortField,
        sortOrder: sortOrder,
        ...queryParams,
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
       if(params.deptId=="-1"){
        delete params.deptId
      }
      this.$get("mdlBManager", {
        type: this.type,
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
