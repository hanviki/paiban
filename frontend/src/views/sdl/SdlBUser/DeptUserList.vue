<template>
  <a-card :bordered="false" class="card-area">
    <div :class="advanced ? 'search' : null">
      <a-form layout="horizontal">
        <a-row>
          <div :class="advanced ? null : 'fold'">
           
            <a-col :md="8" :sm="24">
              <a-form-item label="人事编号/姓名" v-bind="formItemLayout">
                <a-input v-model="queryParams.userAccount" />
              </a-form-item>
            </a-col>
           
          </div>
          <span style="float: right; margin-top: 3px">
             <a-button
                  type="primary"
                  @click="exportCustomExcel"
                >导出</a-button>
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
        <a-button type="primary" ghost @click="add">移入数据</a-button>
        <a-button @click="batchDelete">移出数据</a-button>
      </div>
      <!-- 表格区域 -->
      <a-table
        ref="TableInfo"
        :columns="columns"
        :rowKey="(record) => record.userAccount"
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
    <sdlUser-info
      ref="sdlBUserInfoAdd"
      @close="handleAddClose"
      :userInfoVisiable="addVisiable"
    >
    </sdlUser-info>
  </a-card>
</template>

<script>
import SdlUserInfo from "./SdlUserInfo.vue";

const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 15, offset: 1 },
};
export default {
  name: "SdlBUser",
  components: { SdlUserInfo },
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
          title: "人员类型",
          dataIndex: "rylx",
          width: 100,
        },
         {
          title: "人事编码",
          dataIndex: "userAccount",
          width: 120,
        },
        
         {
          title: "姓名",
          dataIndex: "userAccountName",
          width: 100,
        },
         {
          title: "员工工号",
          dataIndex: "yggh",
          width: 100,
        },
        {
          title: "资质类型",
          dataIndex: "userTypeName",
          width: 100,
        },
        {
          title: "科室编号",
          dataIndex: "deptId",
          width: 100,
        },
         {
          title: "科室名称",
          dataIndex: "deptName",
          width: 100,
        },
         {
          title: "病区名称",
          dataIndex: "bqName",
          width: 100,
        },
        {
          title: "华科人事编号",
          dataIndex: "ghHk",
          width: 100,
        },
         {
          title: "性别",
          dataIndex: "sexName",
          width: 100,
        },
        {
          title: "出生年月",
          dataIndex: "birthday",
          width: 100,
        },
       
        {
          title: "员工组",
          dataIndex: "yuangongzu",
          width: 100,
        },
       {
          title: "员工子组",
          dataIndex: "zizu",
          width: 100,
        },
         {
          title: "人事子范围",
          dataIndex: "renshizifw",
          width: 100,
        },
       {
          title: "人事子范围分类",
          dataIndex: "renshizfenlei",
          width: 100,
        },
        //  {
        //   title: "身份证号",
        //   dataIndex: "idCard",
        //   width: 100,
        // },
         {
          title: "行政级别",
          dataIndex: "xingzhengjiebie",
          width: 100,
        },
        {
          title: "职称",
          dataIndex: "zhicheng",
          width: 100,
        },
        {
          title: "岗位等级",
          dataIndex: "xrgwjb",
          width: 100,
        },
        {
          title: "岗位等级开始日期",
          dataIndex: "xrgwjbprsj",
          width: 100,
        },
         {
          title: "临床职称",
          dataIndex: "zyjsgwLc",
          width: 100,
        },
         {
          title: "聘任时间（临床）",
          dataIndex: "appointedDateLc",
          width: 100,
        },
         {
          title: "临床证书编号",
          dataIndex: "bianhaoLc",
          width: 130,
        },
         {
          title: "教学职称",
          dataIndex: "zyjsgw",
          width: 100,
        },
         {
          title: "教学聘任时间",
          dataIndex: "appointedDate",
          width: 100,
        },
        {
          title: "教学证书编号",
          dataIndex: "bianhaoJx",
          width: 130,
        },
        {
          title: "内聘临床专业技术职务",
          dataIndex: "zyjsNp",
          width: 100,
        },
        {
          title: "内聘临床时间",
          dataIndex: "zyjsDateNp",
          width: 100,
        },
        {
          title: "内聘教学专业技术职务",
          dataIndex: "zyjsNpjx",
          width: 100,
        },
        {
          title: "内聘教学时间",
          dataIndex: "zyjsDateNpjx",
          width: 100,
        },
        {
          title: "学历",
          dataIndex: "edu",
          width: 100,
        },
        {
          title: "毕业学校",
          dataIndex: "eduSchool",
          width: 100,
        },
        {
          title: "国籍",
          dataIndex: "guoji",
          width: 100,
        },
        {
          title: "民族",
          dataIndex: "minzu",
          width: 100,
        },
        {
          title: "籍贯",
          dataIndex: "jiguan",
          width: 100,
        },
        {
          title: "政治面貌",
          dataIndex: "politicalStatus",
          width: 100,
        },
         {
          title: "入职时间",
          dataIndex: "schoolDate",
          width: 100,
        },
        {
          title: "参加工作时间",
          dataIndex: "workDate",
          width: 100,
        },
        {
          title: "医师类别",
          dataIndex: "yishiLb",
          width: 100,
        },
        {
          title: "医师级别",
          dataIndex: "yishiJb",
          width: 100,
        },
        {
          title: "医师执业范围",
          dataIndex: "yishiZhiyefanwei",
          width: 100,
        },
        {
          title: "医师资格证书编号",
          dataIndex: "yishiZgzsbianhao",
          width: 130,
        },
        {
          title: "医师执业证书编码",
          dataIndex: "yishiZiyebianhao",
          width: 130,
        },
      ];
    },
  },
  mounted() {
    this.fetch();
  },
  methods: {
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
      this.$message.success("移入成功");
      this.search();
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
      this.$refs.sdlBUserEdit.setFormValues(record);
      this.editVisiable = true;
    },
    batchDelete() {
      if (!this.selectedRowKeys.length) {
        this.$message.warning("请选择需要移出的记录");
        return;
      }
      if (this.selectedRowKeys.length > 1) {
        this.$message.warning("请只选择一条移出的记录");
        return;
      }
      let that = this;
      this.$confirm({
        title: "确定移出所选中的记录?",
        content: "当您点击确定按钮后，这些记录将会被从本科室移出",
        centered: true,
        onOk() {
          let sdlBUserIds = that.selectedRowKeys.join(",");
          that
            .$post("sdlBDeptchange/delete", {
              userAccount: sdlBUserIds,
              operationName: "移出",
            })
            .then(() => {
              
              that.$message.success("移出成功");
             
              that.selectedRowKeys = [];
              that.search();
            });
        },
        onCancel() {
          that.selectedRowKeys = [];
        },
      });
    },
    exportCustomExcel () {
      let { sortedInfo } = this
      let sortField, sortOrder
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field
        sortOrder = sortedInfo.order
      }
      let json = this.columns
     
      let dataJson = JSON.stringify(json)
      let queryParams= this.queryParams
      
      this.$export('sdlBUser/excel', {
        sortField: 'user_account',
        sortOrder: 'ascend',
        dataJson: dataJson,
        ...queryParams
      })
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
      this.$get("sdlBUser/dept", {
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
