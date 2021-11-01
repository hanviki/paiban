<template>
  <a-card :bordered="false" class="card-area">
    <div :class="advanced ? 'search' : null">
      <a-form layout="horizontal">
        <a-row>
          <div :class="advanced ? null : 'fold'">
            <a-col :md="8" :sm="24">
              <a-form-item label="申请人账号\姓名" v-bind="formItemLayout">
                <a-input v-model="queryParams.userAccount" />
              </a-form-item>
            </a-col>
           
              <a-col :md="8" :sm="24">
                <a-form-item label="状态" v-bind="formItemLayout">
                  <a-select
                    style="width: 100%"
                    v-model="searchState"
                    @change="handleSeachChange"
                  >
                    <a-select-option value="0"> 全部 </a-select-option>
                    <a-select-option value="1"> 待审核 </a-select-option>
                    <a-select-option value="3"> 已审核 </a-select-option>
                    <a-select-option value="2"> 审核未通过 </a-select-option>
                  </a-select>
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
        <!-- <template
        slot="auditSuggestion"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==2">
          {{text}}
        </div>
        <div v-else>
          <div key="auditSuggestion">
            <a-textarea
              @blur="e => inputChange(e.target.value,record,'auditSuggestion')"
              :value="record.auditSuggestion"
            >
            </a-textarea>
          </div>
        </div>
      </template> -->
        <template slot="operation" slot-scope="text, record">
          <a-icon
            v-show="record.state == 1"
            type="setting"
            theme="twoTone"
            twoToneColor="#4a9ff5"
            @click="edit(record)"
            title="审核"
          ></a-icon>
        </template>
      </a-table>
    </div>
    <sdlBZizhiapply-edit
      ref="sdlBZizhiapplyEdit"
      @close="handleEditClose"
      @success="handleEditSuccess"
      :editVisiable="editVisiable"
    >
    </sdlBZizhiapply-edit>
  </a-card>
</template>

<script>
import SdlBZizhiapplyEdit from "./SdlBZizhiapplyEdit2";
const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 15, offset: 1 },
};
export default {
  name: "SdlBZizhiapply",
  components: { SdlBZizhiapplyEdit},
  data() {
    return {
      advanced: false,
      dataSource: [],
      selectedRowKeys: [],
      sortedInfo: {
          filed: 'state ,create_time',
          order: 'descend'
      },
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
      queryParams: {
        
      },
      editVisiable: false,
      auditSuggestion: "",
      auditId: "",
      loading: false,
      bordered: true,
      searchState: '0'
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
          width: 120,
        },
        
         {
          title: "姓名",
          dataIndex: "userAccountName",
          width: 100,
        },
           {
          title: "人员类型",
          dataIndex: "rylx",
          width: 100,
        },
        {
          title: "科室",
          dataIndex: "deptName",
          width: 100,
        },
        {
          title: "病区",
          dataIndex: "bqName",
          width: 100,
        },
        // {
        //   title: "申请日期",
        //   dataIndex: "applyDate",
        //   width: 100,
        // },
        {
          title: "变更类型",
          dataIndex: "applyType",
          width: 100,
        },
       
         {
          title: "原资质",
          dataIndex: "userTypeName",
          width: 100,
        },
        {
          title: "新资质",
          dataIndex: "userNewTypeName",
          width: 100,
        },
        {
          title: "申报理由",
          dataIndex: "note",
          width: 300,
        },
      

         {
          title: "员工工号",
          dataIndex: "yggh",
          width: 100,
        },
       
        // {
        //   title: "华科人事编号",
        //   dataIndex: "ghHk",
        //   width: 100,
        // },
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
      //  {
      //     title: "员工子组",
      //     dataIndex: "zizu",
      //     width: 100,
      //   },
         {
          title: "人事子范围",
          dataIndex: "renshizifw",
          width: 100,
        },
      //  {
      //     title: "人事子范围分类",
      //     dataIndex: "renshizfenlei",
      //     width: 100,
      //   },
        //  {
        //   title: "身份证号",
        //   dataIndex: "idCard",
        //   width: 100,
        // },
        //  {
        //   title: "行政级别",
        //   dataIndex: "xingzhengjiebie",
        //   width: 100,
        // },
        {
          title: "职称",
          dataIndex: "zhicheng",
          width: 100,
        },
        // {
        //   title: "岗位等级",
        //   dataIndex: "xrgwjb",
        //   width: 100,
        // },
        // {
        //   title: "岗位等级开始日期",
        //   dataIndex: "xrgwjbprsj",
        //   width: 100,
        // },
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
        //  {
        //   title: "临床证书编号",
        //   dataIndex: "bianhaoLc",
        //   width: 130,
        // },
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
        // {
        //   title: "教学证书编号",
        //   dataIndex: "bianhaoJx",
        //   width: 130,
        // },
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
        // {
        //   title: "毕业学校",
        //   dataIndex: "eduSchool",
        //   width: 100,
        // },
        // {
        //   title: "国籍",
        //   dataIndex: "guoji",
        //   width: 100,
        // },
        // {
        //   title: "民族",
        //   dataIndex: "minzu",
        //   width: 100,
        // },
        // {
        //   title: "籍贯",
        //   dataIndex: "jiguan",
        //   width: 100,
        // },
        // {
        //   title: "政治面貌",
        //   dataIndex: "politicalStatus",
        //   width: 100,
        // },
         {
          title: "入职时间",
          dataIndex: "schoolDate",
          width: 100,
        },
        // {
        //   title: "参加工作时间",
        //   dataIndex: "workDate",
        //   width: 100,
        // },
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
         {
          title: "审核意见",
          dataIndex: "auditSuggestion",
          width: 130,
          fixed: "right",
        },
        {
          title: "审核状态",
          dataIndex: "state",
          width: 100,
          customRender: (text, row, index) => {
            switch (text) {
              case 1:
                return <a-tag color="green">未审核</a-tag>;
              case 2:
                return <a-tag color="red">审核未通过</a-tag>;
              case 3:
                return <a-tag color="#f50">已审核</a-tag>;
              default:
                return text;
            }
          },
          fixed: "right",
        },
        {
          title: "审核",
          dataIndex: "operation",
          scopedSlots: { customRender: "operation" },
          fixed: "right",
          width: 100,
        },
      ];
    },
  },
  mounted() {
    this.search();
  },
  methods: {
    onSelectChange(selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys;
    },
     handleEditSuccess() {
      this.editVisiable = false;
      this.$message.success("审核完成");
      this.search();
    },
    handleEditClose() {
      this.editVisiable = false;
    },
    edit(record) {
      this.$refs.sdlBZizhiapplyEdit.setFormValues(record);
      this.editVisiable = true;
    },
    toggleAdvanced() {
      this.advanced = !this.advanced;
      if (!this.advanced) {
        this.queryParams.comments = "";
      }
    },
    handleSeachChange(value){
        this.queryParams.state= value
       this.search()
    },
    audit(record) {
      this.auditVisiable = true;
      this.auditSuggestion = "";
      this.auditId = record.id;
    },
    handleCancle() {
      this.auditVisiable = false;
      this.auditSuggestion = "";
      this.auditId = "";
    },
    inputChange (value, record, filedName) {
     // console.info(value)
      record[filedName] = value
    },
    handleAudit() {
        let that =this
      that.$confirm({
        title: "确定审核通过所选中的记录?",
        content: "当您点击确定按钮后，这些记录将会被审核通过",
        centered: true,
        onOk() {
          that.loading = true;
          that.$put("sdlBZizhiapply", {
            auditSuggestion: that.auditSuggestion,
            id: that.auditId,
            state: 3,
          })
            .then(() => {
              that.loading = false;
              that.handleCancle();
            })
            .catch(() => {
              that.loading = false;
            });
        },
        onCancel() {},
      });
    },
    handleNoAudit() {
         let that =this
      that.$confirm({
        title: "确定审核不通过所选中的记录?",
        content: "当您点击确定按钮后，这些记录将会被审核不通过",
        centered: true,
        onOk() {
          that.loading = true;
          that.$put("sdlBZizhiapply", {
            auditSuggestion: that.auditSuggestion,
            id: that.auditId,
            state: 3,
          })
            .then(() => {
              that.loading = false;
              that.handleCancle();
            })
            .catch(() => {
              that.loading = false;
            });
        },
        onCancel() {},
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
      this.$export("sdlBZizhiapply/excel", {
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
       let queryParams = { ...this.queryParams }
      if(queryParams.state==0){
          delete queryParams.state
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
      this.auditId=''
      this.auditSuggestion= ''
      this.searchState= "0"
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
      this.$get("sdlBZizhiapply", {
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
