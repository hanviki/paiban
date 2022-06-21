<template>
  <a-card :bordered="false" class="card-area">
    <div :class="advanced ? 'search' : null">
      <a-form layout="horizontal">
        <a-row>
          <div :class="advanced ? null : 'fold'">
            <a-col :md="8" :sm="24">
              <a-form-item label="科室" v-bind="formItemLayout">
                <a-input v-model="queryParams.deptNew" />
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="项目负责人" v-bind="formItemLayout">
                <a-input v-model="queryParams.fzr" />
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="项目名称" v-bind="formItemLayout">
                <a-input v-model="queryParams.xmmc" />
              </a-form-item>
            </a-col>
            <template v-if="advanced">
              <a-col :md="8" :sm="24">
                <a-form-item label="年度" v-bind="formItemLayout">
                  <a-input v-model="queryParams.year" />
                </a-form-item>
              </a-col>
            </template>
          </div>
          <span style="float: right; margin-top: 3px">
               <a-button type="primary" @click="exportExcel">导出</a-button>
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
        icon="plus"
          v-hasPermission="['mdlDMdtOld:add']"
          type="primary"
          ghost
          @click="add"
          >新增</a-button
        >
        <a-button v-hasPermission="['mdlDMdtOld:delete']" @click="batchDelete" icon="delete"
          >删除</a-button
        >
       
        <import-excel
          v-hasPermission="['mdlDMdtOld:import']"
          templateUrl="mdlDMdtOld/downTemplate"
          @succ="handleRefesh"
          url="mdlDMdtOld/import"
        >
        </import-excel>
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
            v-hasPermission="['mdlDMdtOld:update']"
            type="setting"
            theme="twoTone"
            twoToneColor="#4a9ff5"
            @click="edit(record)"
            title="修改"
          ></a-icon>
          <a-badge
            v-hasNoPermission="['mdlDMdtOld:update']"
            status="warning"
            text="无权限"
          ></a-badge>
        </template>
      </a-table>
    </div>
   
  </a-card>
</template>

<script>

import ImportExcel from "../../common/ImportExcel";
import moment from "moment";

const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 15, offset: 1 },
};
export default {
  name: "MdlDMdtOld",
  components: {  ImportExcel },
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
          title: "科室",
          dataIndex: "deptNew",
          width: 100,
        },
        {
          title: "项目负责人",
          dataIndex: "fzr",
          width: 100,
        },
        {
          title: "职称",
          dataIndex: "zhicheng",
          width: 100,
        },
        {
          title: "项目参与人1",
          dataIndex: "cy1",
          width: 100,
        },
        {
          title: "项目参与人2",
          dataIndex: "cy2",
          width: 100,
        },
        {
          title: "项目参与人3",
          dataIndex: "cy3",
          width: 100,
        },
        {
          title: "项目参与人4",
          dataIndex: "cy4",
          width: 100,
        },
        {
          title: "项目参与人5",
          dataIndex: "cy5",
          width: 100,
        },
        {
          title: "项目参与人6",
          dataIndex: "cy6",
          width: 100,
        },
        {
          title: "项目参与人7",
          dataIndex: "cy7",
          width: 100,
        },
        {
          title: "项目参与人8",
          dataIndex: "cy8",
          width: 100,
        },
        {
          title: "项目参与人9",
          dataIndex: "cy9",
          width: 100,
        },
        {
          title: "项目参与人10",
          dataIndex: "cy10",
          width: 100,
        },
        {
          title: "项目名称",
          dataIndex: "xmmc",
          width: 200,
        },
        {
          title: "年度",
          dataIndex: "year",
          width: 100,
        },
        {
          title: "项目水平（按领先水平划分）",
          dataIndex: "xmsp",
          width: 100,
        }
      
      ];
    },
  },
  mounted() {
    this.fetch();
  },
  methods: {
    moment,
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
      this.$refs.mdlDMdtOldEdit.setFormValues(record);
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
          let mdlDMdtOldIds = that.selectedRowKeys.join(",");
          that.$delete("mdlDMdtOld/" + mdlDMdtOldIds).then(() => {
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
      this.$export("mdlDMdtOld/excel", {
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
      this.$get("mdlDMdtOld", {
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
