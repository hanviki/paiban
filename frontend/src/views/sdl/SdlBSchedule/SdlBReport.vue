<template>
  <a-card :bordered="false" class="card-area">
    <div :class="advanced ? 'search' : null">
      <a-form layout="horizontal">
        <a-row>
          <div :class="advanced ? null : 'fold'">
            <a-col :md="8" :sm="24">
              <a-form-item v-bind="formItemLayout" label="排班科室">
                <a-select v-model="queryParams.deptId"
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
            <a-col :md="8" :sm="24">
              <a-form-item label="发薪号" v-bind="formItemLayout">
                <a-input v-model="queryParams.accountId" />
              </a-form-item>
            </a-col>
             <a-col :md="8" :sm="24">
              <a-form-item label="姓名" v-bind="formItemLayout">
                <a-input v-model="queryParams.accountName" />
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="年度" v-bind="formItemLayout">
                <a-select style="width: 100%" @change="handleChange" 
                :defaultValue="currentYear">
                  <a-select-option value="2021"> 2021 </a-select-option>
                <a-select-option value="2022"> 2022 </a-select-option>
                <a-select-option value="2023"> 2023 </a-select-option>
                <a-select-option value="2024"> 2024 </a-select-option>
                <a-select-option value="2025"> 2025 </a-select-option>
                <a-select-option value="2026"> 2026 </a-select-option>
                <a-select-option value="2027"> 2027 </a-select-option>
                <a-select-option value="2028"> 2028 </a-select-option>
                <a-select-option value="2029"> 2029 </a-select-option>
                <a-select-option value="2030"> 2030 </a-select-option>
                <a-select-option value="2031"> 2031 </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="节假日" v-bind="formItemLayout">
                <a-select
                  style="width: 100%"
                  @change="handleHolidayChange"
                  v-model="holidaysName"
                >
                  <a-select-option v-for="item in selectHolidays" :key="item.id" :value="item.id"> {{item.holidayName}} </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
          </div>
          <span style="float: right; margin-top: 3px">
             <a-button  @click="exportExcel">导出</a-button>
            <a-button type="primary" @click="search">查询</a-button>
            <a-button style="margin-left: 8px" @click="reset">重置</a-button>
          </span>
        </a-row>
      </a-form>
    </div>
    <!-- 表格区域 -->
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
         <a-table
          ref="subTable"
          slot="expandedRowRender"
          slot-scope="record"
          :columns="innerColumns"
          :dataSource="record.innerData"
          :pagination="false"
          :rowKey="record2 => record2.accountId"
        >
         <template slot="operation" slot-scope="text, record">
        <a-button
            icon="search"
            @click="viewInfo(record)"
          >查看</a-button>
      </template>
        </a-table> 
      </a-table>
      <sdl-account
        :applyVisiable="viewInfoVisalboe"
        @closeAccount="handleCloseAccount"
        :queryParams="infoParams"
      >
      </sdl-account>
  </a-card>
</template>

<script>
import moment from "moment";
import SdlAccount from './SdlAccount.vue';

const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 15, offset: 1 },
};
export default {
  name: "SdlBSchedule",
  components: {SdlAccount },
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
      holidaysData: [],
      selectHolidays: [],
      deptData: [],
      holidaysName: '',
      currentYear: moment().format("YYYY"),
      scroll: {
        x: 900,
        // y: window.innerHeight - 200 - 100 - 20 - 15
      },
      viewInfoVisalboe: false,
      infoParams: {}
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
          title: "排班天数",
          dataIndex: "cishu",
        },
      ];
    },
    innerColumns() {
      return [
        {
          title: "发薪号",
          dataIndex: "accountId",
        },
        {
          title: "姓名",
          dataIndex: "accountName",
        },
        {
          title: "排班天数",
          dataIndex: "cishu",
        },
        {
          title: "查看",
          dataIndex: "operation",
          scopedSlots: { customRender: "operation" },
        },
      ];
    },
  },
  mounted() {
   // this.search();
    this.fetchDept();
    this.fetchHoliday();
   
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
    handleChange(year) {
       let hods =this.holidaysData.filter(
           p=>moment(p.startDate).format("YYYY")==year
       );
       console.info(hods)
       if(hods.length==0){
        this.holidaysName = ''
        this.selectHolidays =[]
        this.queryParams.scheduleDateFrom = ""
        this.queryParams.scheduleDateTo = ""
       }
       else{
         this.holidaysName= hods[0].id
         this.selectHolidays =hods;
         this.handleHolidayChange(hods[0].id)
       }
      
      
    },
        filterOption(input, option) {
      return (
        option.componentOptions.children[0].text.toLowerCase().indexOf(input.toLowerCase()) >= 0
      );
    },
    handleHolidayChange(value){
       
       let record = this.selectHolidays.filter(p=>p.id==value);
       this.queryParams.scheduleDateFrom = moment(record[0].startDate).format("YYYY-MM-DD")
       this.queryParams.scheduleDateTo = moment(record[0].endDate).format("YYYY-MM-DD")
    },
     expandSubGrid (expanded, record) {//获取供应计划的数量
      if (expanded) {
        this.expandedRowKeys.push(record.deptId)
        this.handleSubData(record) //获取子表数据
      } else {
        let expandedRowKeys = this.expandedRowKeys.filter(RowKey => RowKey !== record.deptId)
        this.expandedRowKeys = expandedRowKeys
      }
    },
    handleSubData (record) {
      this.loading = true
      let deptId= record.deptId
      let queryParams = { ...this.queryParams };
      queryParams.deptId = record.deptId
      this.$get('sdlBScheduleDetail/deptSub', {
        ...queryParams,
        pageSize: 10000
      }).then((r) => {
        let data = r.data
        this.loading = false
        record.innerData = data
      })
    },
    exportExcel() {
      let { sortedInfo } = this;
      let sortField, sortOrder;
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field;
        sortOrder = sortedInfo.order;
      }
        let queryParams = { ...this.queryParams };
        queryParams.flag= 0;
      if(queryParams.scheduleDateFrom==undefined||queryParams.scheduleDateFrom==""){
        this.$message.warning("节假日必选");
      }
      else {
      let dataJson = JSON.stringify(this.columns)
      this.$export("sdlBScheduleDetail/deptExcel", {
        sortField: sortField,
        sortOrder: sortOrder,
        dataJson: dataJson,
        ...queryParams,
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
      if(queryParams.scheduleDateFrom==undefined||queryParams.scheduleDateFrom==""){
        this.$message.warning("节假日必选");
      }
      else {
     
      this.fetch({
        sortField: sortField,
        sortOrder: sortOrder,
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
    handleCloseAccount(){
      this.viewInfoVisalboe= false;
    },
    viewInfo(record){
       this.viewInfoVisalboe= true;
       this.infoParams = { ...this.queryParams };
       this.infoParams.accountId= record.accountId;
       this.infoParams.flag= 0;
       this.infoParams.deptId = record.deptId
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
    fetchHoliday() {
      this.$get("sdlDHoliday/all", {}).then((res) => {
        this.holidaysData = res.data;
        this.handleChange(this.currentYear)
      });
    },
    fetch(params = {}) {
      this.loading = true;
      this.$get("sdlBScheduleDetail/dept", {
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

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
