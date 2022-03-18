<template>
  <a-card :bordered="false" class="card-area">
    <div :class="advanced ? 'search' : null">
      <a-form layout="horizontal">
        <a-row>
          <div :class="advanced ? null : 'fold'">
            <a-col :md="8" :sm="24">
              <a-form-item label="发薪号/姓名" v-bind="formItemLayout">
                <a-input v-model="queryParams.userAccount" />
              </a-form-item>
            </a-col>
             <a-col :md="8" :sm="24">
               <a-form-item v-bind="formItemLayout" label="科室">
                <a-select v-model="queryParams.deptNew"
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
          </div>
          <span style="float: right; margin-top: 3px">
            <a-button  @click="exportExcel">导出</a-button>
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
       
      </a-table>
    </div>
    <!-- 新增字典 -->
    
  </a-card>
</template>

<script>
import moment from "moment";

const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 15, offset: 1 },
};
export default {
  name: "MdlBChufang",
  components: {  },
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
      deptData: []
    };
  },
  props: {
    type: {
      default: "",
    },
  },
  computed: {
    columns() {
      let { sortedInfo, type } = this;
      sortedInfo = sortedInfo || {};
    
        return [
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
            title: "科室",
            dataIndex: "deptName",
            width: 100,
          },
           {
            title: "医师资格证书",
            dataIndex: "yishiZgzsbianhao",
            width: 100,
          },
            {
            title: "医师执业证书",
            dataIndex: "yishiZgzsbianhao",
            width: 100,
          },
          {
            title: "考试日期",
            dataIndex: "trainDate",
            customRender: (text, row, index) => {
              if (text == null) return "";
              return moment(text).format("YYYY-MM-DD");
            },
            width: 100,
          },
          {
            title: "考核分数",
            dataIndex: "exiamScore",
            width: 100,
          },
          {
            title: "考核结果",
            dataIndex: "exiamResult",
            width: 100,
          }
         
        ];
  
  
    }
  },
  mounted() {
    this.fetch();
    this.fetchDept();
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
    handleUrl(url) {
      return this.$baseUrl + url;
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
      this.$refs.mdlBChufangEdit.setFormValues(record);
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
          let mdlBChufangIds = that.selectedRowKeys.join(",");
          that.$delete("mdlBChufang/" + mdlBChufangIds).then(() => {
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
    exportExcel () {
     
      let json = this.columns
     // json.splice(this.columns.length - 1, 1) //移出第一个
      let dataJson = JSON.stringify(json)

      this.$export('mdlBChufang/excelChuFang', {
        dataJson: dataJson,
        id: this.id
      })
     },
      filterOption(input, option) {
      return (
        option.componentOptions.children[0].text
          .toLowerCase()
          .indexOf(input.toLowerCase()) >= 0
      );
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
    search() {
      let { sortedInfo } = this;
      let sortField, sortOrder;
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field;
        sortOrder = sortedInfo.order;
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
       if(params.deptNew=="-1"){
        delete params.deptNew
      }
      this.$get("mdlBChufang/chufang", {
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
