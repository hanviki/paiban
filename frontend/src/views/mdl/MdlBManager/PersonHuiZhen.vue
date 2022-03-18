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
      return  [
         {
          title: "科室名称",
          dataIndex: "deptNew",
          width: 150,
        },
         {
          title: "医师资格证书编号",
          dataIndex: "yishiZgzsbianhao",
          width: 100,
        },
         {
          title: "医师执业证书编号",
          dataIndex: "yishiZiyebianhao",
          width: 100,
        },
        {
          title: "职称",
          dataIndex: "zhicheng",
          width: 100,
        },
         {
          title: "教学职称",
          dataIndex: "zyjsgw",
          width: 100,
        },
        {
          title: "临床职称",
          dataIndex: "zyjsgwLc",
          width: 100,
        },
         {
          title: "内聘临床专业技术职务",
          dataIndex: "zyjsNp",
          width: 100,
        },
         {
          title: "内聘教学专业技术职务",
          dataIndex: "zyjsNpjx",
          width: 100,
        },
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
      
      this.$get("sdlBUser/hz", {
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
