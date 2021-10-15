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
          title: "人事编码",
          dataIndex: "userAccount",
          width: 100,
        },
         {
          title: "姓名",
          dataIndex: "userAccountName",
          width: 100,
        },
        {
          title: "病区名称",
          dataIndex: "bqName",
          width: 100,
        },
        {
          title: "人员类型",
          dataIndex: "userType",
          width: 100,
          customRender: (text, row, index) => {
            switch (text) {
              case 0:
                return "一值班";
              case 1:
                return "二值班";
              case 2:
                return "三值班";
              case 3:
                return "住院医师老总班";
              case 4:
                return "急诊班值班";
              default:
                return text;
            }
          },
        },
        {
          title: "科室ID",
          dataIndex: "deptId",
          width: 100,
        },
        {
          title: "手机号",
          dataIndex: "telephone",
          width: 100,
        },
        {
          title: "科室名称",
          dataIndex: "deptName",
          width: 100,
        },
        {
          title: "现岗位职务",
          dataIndex: "positionName",
          width: 100,
        },
        {
          title: "拟聘岗位职务",
          dataIndex: "npPositionName",
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
          title: "来校工作时间",
          dataIndex: "schoolDate",
          width: 100,
        },
        {
          title: "现专业技术岗位",
          dataIndex: "zyjsgw",
          width: 100,
        },
        {
          title: "现专业技术岗位（临床）",
          dataIndex: "zyjsgwLc",
          width: 100,
        },
        {
          title: "现从事专业及专长",
          dataIndex: "xcszyjzc",
          width: 100,
        },
        {
          title: "聘任时间",
          dataIndex: "appointedDate",
          width: 100,
        },
        {
          title: "本人排名",
          dataIndex: "patentRanknum",
          width: 100,
        },
        {
          title: "是否授权",
          dataIndex: "isAuthority",
          width: 100,
        },
        {
          title: "附件",
          dataIndex: "fileId",
          width: 100,
        },
        {
          title: "是否转让",
          dataIndex: "isZhuanrang",
          width: 100,
        },
        {
          title: "附件地址",
          dataIndex: "fileUrl",
          width: 100,
        },
        {
          title: "转让效益",
          dataIndex: "patentGood",
          width: 100,
        },
        {
          title: "审核意见",
          dataIndex: "auditSuggestion",
          width: 100,
        },
        {
          title: "聘任时间（临床）",
          dataIndex: "appointedDateLc",
          width: 100,
        },
        {
          title: "附件（临床）",
          dataIndex: "fileIdLc",
          width: 100,
        },
        {
          title: "附件地址（临床）",
          dataIndex: "fileUrlLc",
          width: 100,
        },
        {
          title: "员工组",
          dataIndex: "yuangongzu",
          width: 100,
        },
        {
          title: "现任岗位级别",
          dataIndex: "xrgwjb",
          width: 100,
        },
        {
          title: "现任岗位级别聘任时间",
          dataIndex: "xrgwjbprsj",
          width: 100,
        },
        {
          title: "担(兼)任党政职务",
          dataIndex: "djrdzzw",
          width: 100,
        },
        {
          title: "是否通过初级考核",
          dataIndex: "isChujikh",
          width: 100,
        },
        {
          title: "通过初级考核时间",
          dataIndex: "chujikhDate",
          width: 100,
        },
        {
          title: "是否通过中级考核",
          dataIndex: "isZhongjikh",
          width: 100,
        },
        {
          title: "通过中级考核时间",
          dataIndex: "zhongjikhDate",
          width: 100,
        },
        {
          title: "政治面貌",
          dataIndex: "politicalStatus",
          width: 100,
        },
        {
          title: "职员职级",
          dataIndex: "staffGrade",
          width: 100,
        },
        {
          title: "身份证号",
          dataIndex: "idCard",
          width: 100,
        },
        {
          title: "职位时间",
          dataIndex: "staffDate",
          width: 100,
        },
        {
          title: "照片id",
          dataIndex: "pictureId",
          width: 100,
        },
        {
          title: "照片url",
          dataIndex: "pictureUrl",
          width: 100,
        },
        {
          title: "岗前培训情况",
          dataIndex: "gqpxqk",
          width: 100,
        },
        {
          title: "规范化医师培训情况",
          dataIndex: "gfhyspxqk",
          width: 100,
        },
        {
          title: "中级水平能力测试情况",
          dataIndex: "zjspnlceqk",
          width: 100,
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
    exportExcel() {
      let { sortedInfo } = this;
      let sortField, sortOrder;
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field;
        sortOrder = sortedInfo.order;
      }
      this.$export("sdlBUser/excel", {
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
