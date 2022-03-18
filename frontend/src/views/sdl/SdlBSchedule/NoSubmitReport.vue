<template>
  <a-card :bordered="false" class="card-area">
    <div >
      <a-form layout="horizontal">
        <a-row>
            <a-col :md="8" :sm="24">
              <a-form-item label="开始日期" v-bind="formItemLayout">
                 <a-date-picker
          @change="handleStartDateChange"
        />
              </a-form-item>
            </a-col>
             <a-col :md="8" :sm="24">
              <a-form-item label="结束日期" v-bind="formItemLayout">
                <a-date-picker
          @change="handleEndDateChange"
        />
              </a-form-item>
            </a-col>
          
          <span style="float: right; margin-top: 3px">
            <a-button type="primary" @click="search">查询</a-button>
            <a-button style="margin-left: 8px" @click="reset">重置</a-button>
          </span>
        </a-row>
      </a-form>
    </div>
    <!-- <div>
       <a-button
          type="primary"
          ghost
          @click="exportExcel(0)"
          >导出</a-button
        >
    </div> -->
    <a-table
        ref="TableInfo"
        :columns="columns"
        :rowKey="record => record.deptId"
        :dataSource="dataSource"
        :pagination="false"
        :expandedRowKeys="expandedRowKeys"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        :bordered="bordered"
        :scroll="scroll"
        @expand="expandSubGrid"
      >
        
      </a-table>
  </a-card>
</template>

<script>
import moment from "moment";

const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 15, offset: 1 },
};
export default {
  name: "SdlBSchedule",
  components: {},
  data() {
    return {
      advanced: false,
      dateFormat: "YYYY-MM-DD",
      dataSource: [],
      selectedRowKeys: [],
      expandedRowKeys: [],
      sortedInfo: {
        order: "descend",
        field: "start_date",
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
          deptId: this.$store.state.account.user.deptId
      },
      form: this.$form.createForm(this),
      loading: false,
      bordered: true,
      banci: [],
      zizhi: [],
      deptData: [],
      holidaysName: "",
      dateData: [],
      weekList: [],
      htmData: '',
      currentYear: moment().format("YYYY"),
      scroll: {
        x: 900,
        // y: window.innerHeight - 200 - 100 - 20 - 15
      },
    };
  },
   computed: {
    columns() {
      let { sortedInfo } = this;
      sortedInfo = sortedInfo || {};
      return [
        {
          title: "科室",
          dataIndex: "deptName",
        },
        
         {
          title: "院区",
          dataIndex: "areaIndex",
           customRender: (text, row, index) => {
            if (text == 0) return "本院";
            if (text == 1) return "西院";
            if (text == 2) return "肿瘤";
            if (text == 3) return "金银湖";
          },
        },
        {
          title: "考勤员",
          dataIndex: "userAccountName",
        },
         {
          title: "手机号",
          dataIndex: "telephone",
        },
        {
          title: "开始时间",
          dataIndex: "createTimeFrom",
        },
         {
          title: "结束时间",
          dataIndex: "createTimeTo",
        }
      ];
    },
  },
  mounted() {
    // this.search();
   // this.fetchDept();
  },
  methods: {
      moment,
    onSelectChange(selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys;
    },
    handleStartDateChange(value) {
      if(value==null){
        delete this.queryParams.startDate2
      }
      else{
        this.queryParams.startDate2 = moment(value).format("YYYY-MM-DD");
      }
    },
    handleEndDateChange(value) {
      if(value==null){
        delete this.queryParams.endDate2
      }
      else{
        this.queryParams.endDate2 = moment(value).format("YYYY-MM-DD");
      }
    },

    exportExcel(flag) {
      let queryParams = { ...this.queryParams };
      if (
        queryParams.startDate2 == undefined ||
        queryParams.startDate2 == ""
      ) {
        this.$message.warning("开始日期必选");
      } else if (
        queryParams.endDate2 == undefined ||
        queryParams.endDate2 == ""
      ) {
        this.$message.warning("结束日期必选");
      }  else {
        this.$export("sdlBPersoninfo/excelNoSubmit", {
        ...this.queryParams
      });
      }
     
    },
    search() {
      let { sortedInfo } = this;
      let sortField, sortOrder;
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field;
        sortOrder = sortedInfo.order;
      }
      let queryParams = { ...this.queryParams };
      if (
        queryParams.startDate2 == undefined ||
        queryParams.startDate2 == ""
      ) {
        this.$message.warning("开始日期必选");
      } else if (
        queryParams.endDate2 == undefined ||
        queryParams.endDate2 == ""
      ) {
        this.$message.warning("结束日期必选");
      }  else {
        this.fetch({
          ...queryParams,
        });
      }
    },
    reset() {
      // 取消选中
      this.selectedRowKeys = [];
      // 重置查询参数
      this.queryParams = {};
      this.fetch();
    },
    handleTableChange(pagination, filters, sorter) {
      this.sortedInfo = sorter;
      this.fetch({
        sortField: sorter.field,
        sortOrder: sorter.order,
        ...this.queryParams,
      });
    },
    

    fetch(params = {}) {
      this.loading = true;
      this.$get("sdlBPersoninfo/noSubmit", {
        ...params,
      }).then((r) => {
        this.loading = false;
        let data = r.data;
        this.dataSource = data;
      });
    },
   
  },
};
</script>

<style >
   .cuTable{
     width:1800px;
     border-collapse: collapse;
      border-spacing: 0;
      border-left: 1px solid #888;
      border-top:1px solid #888;
   }
   .cuTable tr{
     border-right: 1px solid #888;
      border-bottom:1px solid #888;
   }
   .cuTable td{
      width:200px;
      border-right: 1px solid #888;
      border-bottom:1px solid #888;
   }
   .heaWeight{
     font-weight: bold;
   }
</style>
<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
