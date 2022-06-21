<template>
  <a-card :bordered="false" class="card-area">
    <div :class="advanced ? 'search' : null">
      <a-form layout="horizontal">
        <a-row>
          <div :class="advanced ? null : 'fold'">
            <a-col :md="7" :sm="24">
              <a-form-item label="发薪号/姓名" v-bind="formItemLayout">
                <a-input v-model="queryParams.userAccount" />
              </a-form-item>
            </a-col>
            <a-col :md="7" :sm="24">
              <a-form-item v-bind="formItemLayout" label="科室">
                <a-select
                  v-model="queryParams.deptId"
                  option-filter-prop="children"
                  :filter-option="filterOption"
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
             <a-col :md="10" :sm="24">
                <a-form-item label="入职日期" v-bind="formItemLayout">
                  <a-date-picker @change="onfzDateFromChange" style="width:48%"/>-
                  <a-date-picker @change="onfzDateToChange" style="width:48%"/>
                </a-form-item>
              </a-col>
           
            <template v-if="advanced">
             
              <a-col :md="7" :sm="24">
                <a-form-item label="医师资格证书" v-bind="formItemLayout">
                  <a-checkbox @change="onChang_zg" />
                </a-form-item>
              </a-col>
              <a-col :md="7" :sm="24">
                <a-form-item label="医师执业证书" v-bind="formItemLayout">
                  <a-checkbox @change="onChang_zy" />
                </a-form-item>
              </a-col>
               <a-col :md="10" :sm="24">
              <a-form-item label="人员状态" v-bind="formItemLayout">
                <a-select
                  style="width: 100%"
                  mode="multiple"
                  v-model="queryParams.auditMan"
                >
                  <a-select-option value="3"> 在职 </a-select-option>
                  <a-select-option value="0"> 离职 </a-select-option>
                  <a-select-option value="2"> 退休 </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            </template>
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
        <a-button type="primary" icon="file-excel" ghost @click="exportExcel">导出</a-button>
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
        <template slot="operation" slot-scope="text, record">
          <a-icon
            type="setting"
            theme="twoTone"
            twoToneColor="#4a9ff5"
            @click="edit(record)"
            title="查看"
          ></a-icon>
        </template>
      </a-table>
    </div>
    <!-- 新增字典 -->
    <!-- <sdlBUser-add
      @close="handleAddClose"
      @success="handleAddSuccess"
      :addVisiable="addVisiable"
    >
    </sdlBUser-add> -->
    <!-- 修改字典 -->
    <user-info-view
      ref="sdlBUserEdit"
      @close="handleEditClose"
      @success="handleEditSuccess"
      :editVisiable="editVisiable"
      :isPersonal="1"
    >
    </user-info-view>
  </a-card>
</template>

<script>
//import SdlBUserAdd from "./SdlBUserAdd";
import moment from "moment";
import UserInfoView from "./UserInfoView";

const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 16 },
};
export default {
  name: "SdlBUser",
  components: { UserInfoView },
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
      deptData: [],
    };
  },
  computed: {
    columns() {
      let { sortedInfo } = this;
      sortedInfo = sortedInfo || {};
      return [
         {
          title: "科室名称",
          dataIndex: "deptNew",
          width: 100,
          fixed: "left",
        },
          {
          title: "姓名",
          dataIndex: "userAccountName",
          width: 100,
          fixed: "left",
        },
           {
          title: "发薪号",
          dataIndex: "userAccount",
          width: 120,
          fixed: "left",
        },
          {
          title: "出生年月",
          dataIndex: "birthday",
          width: 100,
        },
           {
          title: "身份证",
          dataIndex: "idCard",
          width: 150,
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
        {
          title: "人员类型",
          dataIndex: "rylx",
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
          title: "主执业机构",
          dataIndex: "gqpxqk",
          width: 100,
        },
         {
          title: "多点执业机构1",
          dataIndex: "gfhyspxqk",
          width: 100,
        },
         {
          title: "多点执业机构2",
          dataIndex: "zjspnlceqk",
          width: 100,
        },
       
        {
          title: "资格证书附件",
          dataIndex: "fileId",
          customRender: (text, row, index) => {
            if (text != null && text != "") {
              return (
                <a href={this.$baseUrl + row.fileUrl} target="_blank">
                  查看
                </a>
              );
            }
            return "";
          },
          width: 100,
        },
        {
          title: "执业证书附件",
          dataIndex: "fileIdLc",
          customRender: (text, row, index) => {
            if (text != null && text != "") {
              return (
                <a href={this.$baseUrl + row.fileUrlLc} target="_blank">
                  查看
                </a>
              );
            }
            return "";
          },
          width: 100,
        },
        {
          title: "电子证照",
          dataIndex: "pictureId",
          customRender: (text, row, index) => {
            if (text != null && text != "") {
              return (
                <a href={this.$baseUrl + row.pictureUrl} target="_blank">
                  查看
                </a>
              );
            }
            return "";
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
    onSelectChange(selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys;
    },
    onfzDateFromChange(date, dateString) {
      this.queryParams.schoolDateFrom = dateString;
    },
    onfzDateToChange(date, dateString) {
      this.queryParams.schoolDateTo = dateString;
    },
    onChang_zg(e) {
      if (e.target.checked) {
        this.queryParams.yishiZgzsbianhao = "1";
      } else {
        delete this.queryParams.yishiZgzsbianhao;
      }
    },
    onChang_zy(e) {
      if (e.target.checked) {
        this.queryParams.yishiZiyebianhao = "1";
      } else {
        delete this.queryParams.yishiZiyebianhao;
      }
    },
    fetchDept() {
      this.$get("sdlBUser/deptNew", { }).then((res) => {
        this.deptData = [];
        this.deptData.push({
          deptId: "-1",
          deptName: "全部",
        });
        console.info(res)
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
        this.$message.warning("请选择需要删除的记录");
        return;
      }
      let that = this;
      this.$confirm({
        title: "确定删除所选中的记录?",
        content: "当您点击确定按钮后，这些记录将会被彻底删除",
        centered: true,
        onOk() {
          let sdlBUserIds = that.selectedRowKeys.join(",");
          that.$delete("sdlBUser/" + sdlBUserIds).then(() => {
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
      let json = [...this.columns];
      json.splice(this.columns.length - 1, 1); //移出第一个
      console.info(json);
      let dataJson = JSON.stringify(json);
      let queryParams ={...this.queryParams};
      if(queryParams.deptId=='-1'){
        delete queryParams.deptId
      }

      this.$export("sdlBUser/excelYszy", {
        sortField: sortField,
        sortOrder: sortOrder,
        dataJson,
        ...queryParams,
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
      let queryParams ={...this.queryParams};
      if(queryParams.deptId=='-1'){
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
      this.$get("sdlBUser/ywc2", {
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
@import "../../../static/less/Common";
</style>
