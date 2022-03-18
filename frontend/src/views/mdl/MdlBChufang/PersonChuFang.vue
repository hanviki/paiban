<template>
  <div > <div v-if="type!=='考试管理'">{{dataSource.length>0 ?'是':'否'}}</div>
     <div v-if="type=='考试管理'">
 
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
       if (this.type === "考试管理") {
        return [
          {
            title: "考试日期",
            dataIndex: "trainDate",
            customRender: (text, row, index) => {
              if (text == null) return "";
              return moment(text).format("YYYY-MM-DD");
            },
            width: 100,
          },
          {
            title: "考核分数",
            dataIndex: "exiamScore",
            width: 100,
          },
          {
            title: "考核结果",
            dataIndex: "exiamResult",
            width: 100,
          },
        ];
      } else if (this.type === "麻精药物处方权") {
        return [
         
           {
            title: "职称",
            dataIndex: "zhicheng",
            width: 100,
          },
          {
            title: "是否处方",
            dataIndex: "isChufang",
            width: 100,
          },
           {
            title: "授权日期",
            dataIndex: "powerDate",
            customRender: (text, row, index) => {
              if (text == null) return "";
              return moment(text).format("YYYY-MM-DD");
            },
            width: 100,
          },
          {
            title: "考试日期",
            dataIndex: "trainDate",
            customRender: (text, row, index) => {
              if (text == null) return "";
              return moment(text).format("YYYY-MM-DD");
            },
            width: 100,
          },
          {
            title: "考核分数",
            dataIndex: "exiamScore",
            width: 100,
          },
          {
            title: "考核结果",
            dataIndex: "exiamResult",
            width: 100,
          },
{
            title: "授权文件名称",
            dataIndex: "archiveName",
            width: 100,
          },
          {
            title: "授权文件编码",
            dataIndex: "archiveCode",
            width: 100,
          },
          {
            title: "附件ID",
            dataIndex: "fileId",
            width: 100,
            customRender: (text, row, index) => {
              if (text != null && text != "") {
                return (
                  <a href={this.handleUrl(row.fileUrl)} target="_blank">
                    查看
                  </a>
                );
              }
              return "";
            },
          },
        
        ];
      } else {
        return [
         
  {
            title: "科室",
            dataIndex: "deptNew",
            width: 100,
          },
           {
            title: "职称",
            dataIndex: "zhicheng",
            width: 100,
          },
          {
            title: "级别",
            dataIndex: "level",
            width: 100,
          },
           {
            title: "授权日期",
            dataIndex: "powerDate",
            customRender: (text, row, index) => {
              if (text == null) return "";
              return moment(text).format("YYYY-MM-DD");
            },
            width: 100,
          },
          {
            title: "考试日期",
            dataIndex: "trainDate",
            customRender: (text, row, index) => {
              if (text == null) return "";
              return moment(text).format("YYYY-MM-DD");
            },
            width: 100,
          },
          {
            title: "考核分数",
            dataIndex: "exiamScore",
            width: 100,
          },
          {
            title: "考核结果",
            dataIndex: "exiamResult",
            width: 100,
          },
          {
            title: "授权文件名称",
            dataIndex: "archiveName",
            width: 100,
          },
          {
            title: "授权文件编码",
            dataIndex: "archiveCode",
            width: 100,
          },

          {
            title: "附件ID",
            dataIndex: "fileId",
            width: 100,
            customRender: (text, row, index) => {
              if (text != null && text != "") {
                return (
                  <a href={this.handleUrl(row.fileUrl)} target="_blank">
                    查看
                  </a>
                );
              }
              return "";
            },
          }
        ];
      }
    },
  },
  mounted() {
    this.fetch();
    
  },
  
  methods: {
    moment,
     handleUrl(url) {
      return this.$baseUrl + url;
    },
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
      let url ="mdlBChufang";
      let type = this.type
      if(type=="基本处方权"){
        url ="mdlBChufang/chufang"
        type= "考试管理"
      }
      this.$get(url, {
        type: type,
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
