<template>
  <a-card :bordered="false" class="card-area">
    <div :class="advanced ? 'search' : null">
      <a-form layout="horizontal">
        <a-row>
          <div :class="advanced ? null : 'fold'">
            <a-col :md="6" :sm="24">
              <a-form-item label="人员类型" v-bind="formItemLayout">
                <a-input v-model="queryParams.type" />
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item label="发薪号" v-bind="formItemLayout">
                <a-input v-model="queryParams.userAccount" />
              </a-form-item>
            </a-col>
          <a-col :md="6" :sm="24">
              <a-form-item label="开始日期" v-bind="formItemLayout">
                 <a-date-picker
          @change="handleStartDateChange"
        />
              </a-form-item>
            </a-col>
             <a-col :md="6" :sm="24">
              <a-form-item label="结束日期" v-bind="formItemLayout">
                <a-date-picker
          @change="handleEndDateChange"
        />
              </a-form-item>
            </a-col>
            <template v-if="advanced">
            <a-col :md="6" :sm="24" >
               <a-form-item label="所在部门" v-bind="formItemLayout">
              <a-input v-model="queryParams.deptNew" />
               </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24" >
               <a-form-item label="记分部门" v-bind="formItemLayout">
              <a-input v-model="queryParams.deptName" />
               </a-form-item>
            </a-col>
             <a-col :md="6" :sm="24" >
                <a-form-item label="指标大类" v-bind="formItemLayout">
              <a-input v-model="queryParams.lb" />
                </a-form-item>
            </a-col>
          </template>
          </div>
          <span style="float: right; margin-top: 3px">
            <a-button type="primary" @click="openUploadFile">上传附件</a-button>
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
        :scroll="{ x: 1200 }"
        :expandedRowKeys="expandedRowKeys"
        @expand="expandSubGrid"
      >
       <template
                slot="userAccount"
                slot-scope="text, record"
              >
                <a
                  href="#"
                  @click="showUserInfo(text)"
                >{{text}}</a>
              </template>
         <a-table
          ref="subTable"
          slot="expandedRowRender"
          slot-scope="record"
          :columns="innerColumns"
          :dataSource="record.innerData"
          :pagination="false"
          :rowKey="record2 => record2.id"
          :scroll="{y: 200}"
        >
          
        </a-table> 
      </a-table>
    </div>
     <a-modal
      :maskClosable="false"
      v-model="lookVisiable"
      width="400px"
      @ok="handleLookOk"
      title="上传附件"
    >
      <a-form-item label="附件" v-bind="formItemLayout">
       <upload-single-file
            ref="fileagent"
            @uploadRemove="removeAgent_1"
            @uploadSuc="uploadAgent_1"
          >
          </upload-single-file>
      </a-form-item>
      <a-form-item label="备注" v-bind="formItemLayout">
        <a-textarea v-model="note" :rows="2" >
        </a-textarea>
      </a-form-item>
    </a-modal>
    <mdl-b-badarchive :fileVisiable="fileVisiable" ref="fileShow"
     :userAccount="userAccount" @close="handleCloseFile"
    >
    </mdl-b-badarchive>
  </a-card>
</template>

<script>
import moment from "moment";
import UploadSingleFile from "../../common/uploadSingleFile"
import MdlBBadarchive from '../MdlBBadarchive/MdlBBadarchive.vue'

const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 15, offset: 1 },
};
export default {
  name: "MdlBBadrecord",
  components: { UploadSingleFile, MdlBBadarchive  },
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
      expandedRowKeys: [],
      addVisiable: false,
      editVisiable: false,
      loading: false,
      bordered: true,
      indData: [],
      lookVisiable: false,
      fileId: '',
      fileUrl: '',
      fileVisiable: false,
      userAccount: '',
      note: ''
    };
  },
  computed: {
    columns() {
      let { sortedInfo } = this;
      sortedInfo = sortedInfo || {};
      return [
        {
          title: "发薪号",
          dataIndex: "userAccount",
          scopedSlots: { customRender: 'userAccount' },
          width: 200,
        },
        {
          title: "姓名",
          dataIndex: "userAccountName",
          width: 200,
        },
        {
          title: "人员类型",
          dataIndex: "type",
          width: 100,
        },
        {
          title: "工号",
          dataIndex: "yggh",
          width: 80,
        },
        
        {
          title: "记分额度",
          dataIndex: "score",
          width: 80,
          customCell: function (record2,index2) {
            if(record2.score!=null && record2.score>=12){
              return { style: { backgroundColor: 'red',color: 'white' } };
            }
            if(record2.score!=null && record2.score>=6){
              return { style: { backgroundColor: 'blue',color: 'white' } };
            }
            return { style: {} };
          },
        },
        {
          title: "操作",
          dataIndex: "operation",
          scopedSlots: { customRender: "operation" },
          width: 200,
        },
      ];
    },
     innerColumns() {
      return [
          {
          title: "发薪号",
          dataIndex: "userAccount",
          width: 80,
        },
        {
          title: "姓名",
          dataIndex: "userAccountName",
          width: 80,
        },
       {
          title: "所在部门",
          dataIndex: "deptNew",
          width: 100,
        },
        {
          title: "人员子范围",
          dataIndex: "rszfw",
          width: 100,
        },
        {
          title: "工号",
          dataIndex: "yggh",
          width: 60,
        },
        {
          title: "记分部门",
          dataIndex: "deptName",
          width: 80,
        },
         {
          title: "记分人员",
          dataIndex: "jfPerson",
          width: 100,
        },
         {
          title: "指标大类",
          dataIndex: "lb",
          width: 80,
        },
        {
          title: "指标编码",
          dataIndex: "code",
          width: 60,
        },
        {
          title: "记分指标",
          dataIndex: "indict",
          width: 150,
        },
        {
          title: "具体事由",
          dataIndex: "note",
          width: 150,
        },
        {
          title: "记分额度",
          dataIndex: "score",
          width: 60,
        },
        {
          title: "记录时间",
          dataIndex: "startDate",
          customRender: (text, row, index) => {
            if (text == null) return "";
            return moment(text).format("YYYY-MM-DD");
          },
          width: 100,
        },
        {
          title: "附件",
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
          width: 80,
        }
      ]}
  },
  mounted() {
    this.fetch();
   // this.fetchIndict();
  },
  
  methods: {
    moment,
    showUserInfo(userAccount){
       this.userAccount= userAccount;
       this.fileVisiable =true
       this.$refs.fileShow.fetch(userAccount)
    },
    handleCloseFile(){
      this.fileVisiable= false
    },
    openUploadFile(){
       if (!this.selectedRowKeys.length) {
        this.$message.warning("请选择不良计分记录");
        return;
      }
      this.lookVisiable = true;
      this.fileId = ''
      this.fileUrl = ''
      this.note= ''
    },
    handleLookOk() {
      if (this.fileId == "") {
        this.$message.warning("请选择附件");
        return false;
      } else {
        const data = this.dataSource
        let dataSelect= data.filter(p=>this.selectedRowKeys.indexOf(p.userAccount)>=0);
        let cls= []
        dataSelect.forEach(item=>{
           cls.push({
              userAccount: item.userAccount,
              userAccountName: item.userAccountName
           })
        });
        this.$post("mdlBBadarchive", {
          archive: JSON.stringify(cls),
          fileId: this.fileId,
          fileUrl: this.fileUrl,
          note: this.note
        }).then((r) => {
          this.selectedRowKeys = []
          this.$message.success("上传成功");
          this.$refs.fileagent.reset();
          this.lookVisiable = false;
          this.search();
        });
      }
    },
      uploadAgent_1(fileId, fileUrl) {
      this.fileId = fileId;
      this.fileUrl = fileUrl;
    },
    removeAgent_1() {
      this.fileId = "";
      this.fileUrl = "";
    },
    expandSubGrid (expanded, record) {//获取供应计划的数量
      if (expanded) {
        this.expandedRowKeys.push(record.userAccount)
        this.handleSubData(record) //获取子表数据
      } else {
        let expandedRowKeys = this.expandedRowKeys.filter(RowKey => RowKey !== record.userAccount)
        this.expandedRowKeys = expandedRowKeys
      }
    },
    handleSubData (record) {
      this.loading = true
      let queryParams = { ...this.queryParams };
      queryParams.userAccount = record.userAccount
      this.$get('mdlBBadrecord/sub', {
        ...queryParams,
        pageSize: 100000
      }).then((r) => {
        let data = r.data
        this.loading = false
        record.innerData = data
      })
    },
    fetchIndict() {
      this.$get("mdlDBadscore", {
        pageNum: 1,
        pageSize: 1000,
      }).then((r) => {
        console.log(r);
        this.indData = r.data.rows;
      });
    },
    filterOption(input, option) {
      return (
        option.componentOptions.children[0].text
          .toLowerCase()
          .indexOf(input.toLowerCase()) >= 0
      );
    },
     handleStartDateChange(value) {
      if(value==null){
        delete this.queryParams.startDateFrom
      }
      else{
        this.queryParams.startDateFrom = moment(value).format("YYYY-MM-DD");
      }
    },
    handleEndDateChange(value) {
      if(value==null){
        delete this.queryParams.startDateTo
      }
      else{
        this.queryParams.startDateTo = moment(value).format("YYYY-MM-DD");
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
      this.$refs.mdlBBadrecordEdit.setFormValues(record);
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
          let mdlBBadrecordIds = that.selectedRowKeys.join(",");
          that.$delete("mdlBBadrecord/" + mdlBBadrecordIds).then(() => {
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
      this.$export("mdlBBadrecord/excel", {
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
      this.$get("mdlBBadrecord/statistic", {
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
