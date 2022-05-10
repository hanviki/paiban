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
          title: "记分部门",
          dataIndex: "deptName",
          width: 100,
        },
        {
          title: "记分指标",
          dataIndex: "indict",
          width: 200,
        },
        {
          title: "具体事由",
          dataIndex: "note",
          width: 200,
        },
        {
          title: "记分额度",
          dataIndex: "score",
          width: 80,
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
        }];
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
      
      this.$get("mdlBBadrecord/info", {
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
