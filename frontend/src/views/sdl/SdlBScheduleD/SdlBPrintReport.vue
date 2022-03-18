<template>
  <a-card :bordered="false" class="card-area">
    <div >
      <a-form layout="horizontal">
        <a-row>
            <a-col :md="6" :sm="24">
              <a-form-item v-bind="formItemLayout" label="排班科室">
                <a-select v-model="queryParams.deptId">
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
          
          <span style="float: right; margin-top: 3px">
            <a-button type="primary" @click="search">查询</a-button>
            <a-button style="margin-left: 8px" @click="reset">重置</a-button>
          </span>
        </a-row>
      </a-form>
    </div>
    <div>
       <a-button
          type="primary"
          ghost
          @click="exportExcel(0)"
          >导出</a-button
        >
        <a-button
          type="primary"
          ghost
          @click="exportExcel(1)"
          >导出(不含发薪号)</a-button
        >
    </div>
    <div v-html="htmData" style="overflow-x:scroll;width:100%;margin-top: 3px">
      {{htmData}}
    </div>
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
      queryParams: {},
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
  computed: {},
  mounted() {
    // this.search();
    this.fetchDept();
  },
  methods: {
    handleStartDateChange(value) {
      if(value==null){
        delete this.queryParams.scheduleDateFrom
      }
      else{
        this.queryParams.scheduleDateFrom = moment(value).format("YYYY-MM-DD");
      }
    },
    handleEndDateChange(value) {
      if(value==null){
        delete this.queryParams.scheduleDateTo
      }
      else{
        this.queryParams.scheduleDateTo = moment(value).format("YYYY-MM-DD");
      }
    },

    exportExcel(flag) {
      let queryParams = { ...this.queryParams };
      if (
        queryParams.scheduleDateFrom == undefined ||
        queryParams.scheduleDateFrom == ""
      ) {
        this.$message.warning("开始日期必选");
      } else if (
        queryParams.scheduleDateTo == undefined ||
        queryParams.scheduleDateTo == ""
      ) {
        this.$message.warning("结束日期必选");
      } else if (queryParams.deptId == undefined || queryParams.deptId == "") {
        this.$message.warning("科室必选");
      } else {
        this.$export("sdlBScheduleD/excel", {
        ...this.queryParams,
        flag: flag
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
        queryParams.scheduleDateFrom == undefined ||
        queryParams.scheduleDateFrom == ""
      ) {
        this.$message.warning("开始日期必选");
      } else if (
        queryParams.scheduleDateTo == undefined ||
        queryParams.scheduleDateTo == ""
      ) {
        this.$message.warning("结束日期必选");
      } else if (queryParams.deptId == undefined || queryParams.deptId == "") {
        this.$message.warning("科室必选");
      } else {
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
    fetchDept() {
      this.$get("dept/list", { parentId: "0" }).then((res) => {
        this.deptData = [];
        this.deptData.push({
          deptId: "-1",
          deptName: "全部",
        });
        this.deptData.push(...res.data);
      });
    },

    fetch(params = {}) {
      this.loading = true;
      this.$get("sdlBScheduleD/report", {
        ...params,
      }).then((r) => {
        this.loading = false;
        let data = r.data;
      //  this.dataSource = data.rows;
       // this.banci = data.banci;
       // this.zizhi = data.zizhi;
        this.htmData =data.html;
        //this.$refs.htmdiv.innerHTML(data.html)
        console.info(data.html)
       

      });
    },
    getWeekDate(currentDay){
       let week= [];
       let weekofday = moment(dates[0],'YYYY-MM-DD').format('E');
       var last_monday =moment().subtract(weekofday-1,days).format('YYYY-MM-DD');
       var last_tues =moment().add(2-weekofday,days).format('YYYY-MM-DD');
       var last_thr =moment().add(3-weekofday,days).format('YYYY-MM-DD');
       var last_fou =moment().add(4-weekofday,days).format('YYYY-MM-DD');
       var last_fri =moment().add(5-weekofday,days).format('YYYY-MM-DD');
       var last_sat =moment().add(6-weekofday,days).format('YYYY-MM-DD');
       var last_sunday =moment().add(7-weekofday,days).format('YYYY-MM-DD');
       week.push(last_monday);
       week.push(last_tues);
       week.push(last_thr);
       week.push(last_fou);
       week.push(last_fri);
       week.push(last_sat);
       week.push(last_sunday);
    }
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
