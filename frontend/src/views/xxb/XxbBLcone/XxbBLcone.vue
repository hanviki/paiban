<template>
  <a-card :bordered="false" class="card-area">
    <div :class="advanced ? 'search' : null">
      <a-form layout="horizontal">
        <a-row>
          <div :class="advanced ? null : 'fold'"></div>
          <a-col :md="10" :sm="24">
            <a-form-item label="申请日期" v-bind="formItemLayout">
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
          v-hasPermission="['xxbBCheck:add']"
          type="primary"
          ghost
          @click="add"
          >新增</a-button
        >
        <a-button v-hasPermission="['xxbBCheck:delete']" @click="batchDelete"
          >删除</a-button
        >
        <a-dropdown v-hasPermission="['xxbBCheck:export']">
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
            v-hasPermission="['xxbBCheck:view']"
            type="eye"
            theme="twoTone"
            twoToneColor="#42b983"
            @click="view(record)"
            title="查看"></a-icon>
          <a-divider v-hasPermission="['xxbBCheck:update']" 
            v-show="record.state==0 || record.state == 3? true : false" type="vertical" />
          <a-icon
            v-hasPermission="['xxbBCheck:update']"
            v-show="record.state==0 || record.state == 3? true : false"
            type="setting"
            theme="twoTone"
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
            v-hasNoPermission="['xxbBCheck:update']"
            status="warning"
            text="无权限"
          ></a-badge> -->
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
    <!-- 新增字典 -->
    <!-- <xxbBLcone-add
      ref="xxbBLconeAdd"
      @close="handleAddClose"
      @success="handleAddSuccess"
      :addVisiable="addVisiable"
    >
    </xxbBLcone-add> -->
    <!-- 修改字典 -->
    <xxbBLcone-edit
      ref="xxbBLconeEdit"
      @close="handleEditClose"
      @success="handleEditSuccess"
      :editVisiable="editVisiable"
    >
    </xxbBLcone-edit>
    <a-modal :maskClosable="false" :footer="null" v-model="lookFlowVisiable" width="85%" title="浏览流程" @ok="handleLookFlowOk">
      <xxbBDeptFlow-look
        ref="xxbBDeptFlowLook"
      >
      </xxbBDeptFlow-look>
    </a-modal>
  </a-card>
</template>

<script>
// import XxbBLconeAdd from "./XxbBLconeAdd";
import XxbBShow from "../XxbBShow";
import XxbBLconeEdit from "./XxbBLconeEdit";
import XxbBDeptFlowLook from "../XxbBDeptFlowLook";
// import ImportExcel from "../../common/ImportExcel";
import moment from "moment";

const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 15, offset: 1 },
};
export default {
  name: "XxbBLcone",
  components: { XxbBLconeEdit, XxbBShow, XxbBDeptFlowLook },
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
      projectType: "临床类-单科申报",
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
          title: "项目负责人",
          dataIndex: "userAccountName",
          customRender: (text, row, index) => {
            return row.userAccountName + ' ' + row.userAccount + ' ' + row.yggh
          },
          width: 150,
        },
        {
          title: "项目类型",
          dataIndex: "projectLb",
          customRender: (text, row, index) => {
            switch (text) {
              case 1:
                return '手术类'
              case 2:
                return '治疗操作类'
              case 3:
                return '其他类'
              default:
                return text
            }
          },
          width: 100,
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
              case 9:
                return '终止申报'
              default:
                return text
            }
          },
          width: 100,
        },
        {
          title: "上会状态",
          dataIndex: "shstate",
          customRender: (text, row, index) => {
            switch (text) {
              case 0:
                return ''
              case 1:
                return '通过'
              case 2:
                return '不通过'
              default:
                return text
            }
          },
          width: 100,
        },
        {
          title: "操作",
          dataIndex: "operation",
          scopedSlots: { customRender: "operation" },
          fixed: "right",
          width: 120,
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
      // this.addVisiable = true;
      // this.$refs.xxbBLconeAdd.setFormValues();
      this.lookVisiable = true;
      setTimeout(() => {
        this.$refs.xxbBShow.show()
      }, 100);
    },
    openEdit () {
      this.lookVisiable = false
      this.editVisiable = true;
      this.$refs.xxbBLconeEdit.setFormValues(null,'新增',this.projectType);
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
      this.$refs.xxbBLconeEdit.setFormValues(record,'查看',this.projectType);
      this.editVisiable = true;
    },
    edit(record) {
      this.$refs.xxbBLconeEdit.setFormValues(record,'修改',this.projectType);
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
      let { sortedInfo } = this;
      let sortField, sortOrder;
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field;
        sortOrder = sortedInfo.order;
      }
      this.$export("xxbBCheck/excel", {
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
      params.projectType = 1
      this.$get("xxbBCheck", {
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
