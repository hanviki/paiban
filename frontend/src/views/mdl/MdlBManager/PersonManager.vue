<template>
  <div v-if="dataSource.length>0">
    <div>
      <!-- 表格区域 -->
      <a-table
        ref="TableInfo"
        :columns="columns"
        :rowKey="(record) => record.id"
        :dataSource="dataSource"
        :pagination="false"
        :loading="loading"
        :bordered="bordered"
        :scroll="{ x: 900 }"
      >
        
      </a-table>
    </div>
  </div>
</template>

<script>
import moment from "moment";


export default {
  name: "MdlBManager",
  data() {
    return {
      advanced: false,
      dataSource: [],
      selectedRowKeys: [],
      sortedInfo: null,
      paginationInfo: null,
      queryParams: {},
    
      loading: false,
      bordered: true
    };
  },
  props: {
     type: {
       default: ''
     }
  },
  computed: {
    columns() {
      let { sortedInfo } = this;
      sortedInfo = sortedInfo || {};
      return [
        {
          title: "科室名称",
          dataIndex: "deptId",
          width: 100,
        },
        {
          title: "类型",
          dataIndex: "type",
          width: 100,
        },
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
          title: "出生年月",
          dataIndex: "birthday",
          width: 100,
        },
        {
          title: "手机号",
          dataIndex: "tel",
          width: 100,
        },
        {
          title: "电子邮箱",
          dataIndex: "email",
          width: 100,
        },
         {
          title: "任职开始时间",
          dataIndex: "startDate",
          width: 100,
           customRender: (text, row, index) => {
            if (text == null) return "";
            return moment(text).format("YYYY-MM-DD");
          },
        },
          {
          title: "任职结束时间",
          dataIndex: "endDate",
           customRender: (text, row, index) => {
            if (text == null) return "";
            return moment(text).format("YYYY-MM-DD");
          },
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
    reset() {
      // 取消选中
      this.selectedRowKeys = [];
     
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
      
      this.$get("mdlBManager", {
        type: this.type,
        userAccount: this.$store.state.account.user.username
      }).then((r) => {
        let data = r.data;
       
        this.loading = false;
        this.dataSource = data.rows;
       
      });
    },
  },
};
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
