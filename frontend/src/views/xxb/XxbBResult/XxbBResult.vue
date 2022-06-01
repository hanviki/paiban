<template>
  <a-card :bordered="false" class="card-area">
    <div :class="advanced ? 'search' : null">
      <a-form layout="horizontal">
        <a-row>
          <div :class="advanced ? null : 'fold'"></div>
          <a-col :md="10" :sm="24">
            <a-form-item label="申报日期" v-bind="formItemLayout">
              <a-date-picker @change="onSqStartChange" style="width:115px" />- 
              <a-date-picker @change="onSqEndChange" style="width:115px"/>
            </a-form-item>
          </a-col>
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
          v-hasPermission="['xxbBResult:add']"
          type="primary"
          ghost
          @click="add"
          >新增</a-button
        >
        <a-button v-hasPermission="['xxbBResult:delete']" @click="batchDelete"
          >删除</a-button
        >
        <a-dropdown v-hasPermission="['xxbBResult:export']">
          <a-menu slot="overlay">
            <a-menu-item key="export-data" @click="exportExcel"
              >导出Excel</a-menu-item
            >
          </a-menu>
          <a-button>
            更多操作
            <a-icon type="down" />
          </a-button>
        </a-dropdown>
        <!-- <import-excel
          v-hasPermission="['xxbBResult:import']"
          templateUrl="xxbBResult/downTemplate"
          @succ="handleRefesh"
          url="xxbBResult/import"
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
            v-hasPermission="['xxbBResult:view']"
            type="eye"
            theme="twoTone"
            twoToneColor="#42b983"
            @click="view(record)"
            title="查看"></a-icon>
          <a-divider v-hasPermission="['xxbBResult:update']" 
            v-show="record.state==0 || record.state == 3? true : false" type="vertical" />
          <a-icon
            v-hasPermission="['xxbBResult:update']"
            type="setting"
            theme="twoTone"
            v-show="record.state==0 || record.state == 3? true : false"
            twoToneColor="#4a9ff5"
            @click="edit(record)"
            title="修改"
          ></a-icon>
          <a-divider
            v-show="record.state> 0? true : false" type="vertical" />
          <a-icon
            v-show="record.state> 0? true : false"
            type="message"
            theme="twoTone"
            twoToneColor="#4a9ff5"
            @click="flowLook(record)"
            title="流程"
          ></a-icon>
          <!-- <a-badge
            v-hasNoPermission="['xxbBResult:update']"
            status="warning"
            text="无权限"
          ></a-badge> -->
        </template>
      </a-table>
    </div>
    <!-- 新增字典 -->
    <!-- <xxbBResult-add
      ref="xxbBResultAdd"
      @close="handleAddClose"
      @success="handleAddSuccess"
      :addVisiable="addVisiable"
    >
    </xxbBResult-add> -->
    <!-- 修改字典 -->
    <xxbBResult-edit
      ref="xxbBResultEdit"
      @close="handleEditClose"
      @success="handleEditSuccess"
      :editVisiable="editVisiable"
    >
    </xxbBResult-edit>
    <a-modal :maskClosable="false" :footer="null" v-model="lookFlowVisiable" width="85%" title="浏览流程" @ok="handleLookFlowOk">
      <xxbBResultF-look
        ref="xxbBResultFLook"
      >
      </xxbBResultF-look>
    </a-modal>
  </a-card>
</template>

<script>
import XxbBResultEdit from "./XxbBResultEdit";
import XxbBResultFLook from "../XxbBResultFLook";
import moment from "moment";

const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 15, offset: 1 },
};
export default {
  name: "xxbBResult",
  components: { XxbBResultEdit, XxbBResultFLook },
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
      queryParams: {},
      addVisiable: false,
      editVisiable: false,
      lookVisiable: false,
      lookFlowVisiable: false,
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
          width: 80,
        },
        {
          title: "操作",
          dataIndex: "operation",
          scopedSlots: { customRender: "operation" },
          fixed: "right",
          width: 130,
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
        this.$refs.xxbBResultFLook.setFieldValues(record.id, record.state,0,null,null,null,null,null,null);
      }, 100);
    },
    onSqStartChange (date, dateString) {
      this.queryParams.applydatFrom = dateString;
    },
    onSqEndChange (date, dateString) {
      this.queryParams.applydatTo = dateString;
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
    add() {
      this.editVisiable = true;
      this.$refs.xxbBResultEdit.setFormValues(null,'新增');
    },
    handleEditSuccess() {
      this.editVisiable = false;
      this.$message.success("提交成功");
      this.search();
    },
    handleEditClose() {
      this.editVisiable = false;
    },
    view(record) {
      this.$refs.xxbBResultEdit.setFormValues(record,'查看');
      this.editVisiable = true;
    },
    edit(record) {
      this.$refs.xxbBResultEdit.setFormValues(record,'修改');
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
          let xxbBResultIds = that.selectedRowKeys.join(",");
          that.$delete("xxbBResult/" + xxbBResultIds).then(() => {
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
      this.$export("xxbBResult/excel", {
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
      this.$get("xxbBResult", {
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
