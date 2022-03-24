<template>
    <div>
         <div class="operator">
           <select-user-remote ref="user" @userChang="userChange" style="width:400px"></select-user-remote>
        <a-button
          
          type="primary"
          ghost
          @click="add"
          >新增</a-button
        >
        <a-button  @click="batchDelete"
          >删除</a-button
        >
      </div>
      <!-- 表格区域 -->
      <a-table
        ref="TableInfo"
        :columns="columns"
        :rowKey="(record) => record.userAccount"
        :dataSource="dataSource"
        :pagination="false"
        :rowSelection="{
          selectedRowKeys: selectedRowKeys,
          onChange: onSelectChange,
        }"
        :loading="loading"
        :bordered="bordered"
        :scroll="{ x: 900 }"
      >
        
      </a-table>
    </div>
</template>

<script>
import moment from "moment";
import SelectUserRemote from '../../common/SelectUserRemote.vue';


export default {
  components: { SelectUserRemote },
  name: "AddUser",
  data() {
    return {
      dataSource: [],
      queryParams: {},
       sortedInfo: null,
       selectedRowKeys: [],
      paginationInfo: null,
      loading: false,
      bordered: true,
      userData: [],
      selectUser: {}
    };
  },
  props: {
     baseId: {
       default: ''
     },
     type: {
       default: 0
     }
  },
  computed: {
    columns() {
      let { sortedInfo } = this;
      sortedInfo = sortedInfo || {};
      return  [
         {
          title: "姓名",
          dataIndex: "userAccountName",
          width: 150,
        },
         {
          title: "发薪号",
          dataIndex: "userAccount",
          width: 100,
        },
         {
          title: "科室",
          dataIndex: "deptNew",
          width: 100,
        },
      ];
    },
  },
  mounted() {
    //this.fetch();
    
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
      this.dataSource= [];
      this.$refs.user.reset()
      //this.fetch();
    },
     onSelectChange(selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys;
    },
     userChange(value,option) {
         console.info()
         this.selectUser =option.key;
    },
    add(){
        let flag=0;
       const data= this.dataSource
       data.forEach((el)=>{
           if(el.userAccount==this.selectUser.userAccount){
               flag=1
           }
       })
       if(flag==0){
       data.push(this.selectUser)
       this.dataSource =data
       }
       else{
         this.$message.warning("此用户已经添加，请勿重新添加");
       }
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
          let accounts = that.selectedRowKeys;
          let data= that.dataSource
         
            data = data.filter(p=>accounts.indexOf(p.userAccount)<0)
          
          that.dataSource= data
        
            that.$message.success("删除成功");
            that.selectedRowKeys = [];
        },
        onCancel() {
          that.selectedRowKeys = [];
        },
      });
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
    fetchUser(userAccount) {
      this.$get("sdlBUser/search", {
        userAccount,
      }).then((r) => {
        console.log(r)
        this.userData = r.data;
      });
    },
    fetch(params = {}) {
      this.loading = true;
      
      this.$get("mdlBMdtD", {
        baseId: this.baseId,
        type: this.type,
        pageSize: 10000
      }).then((r) => {
        let data = r.data;
       
        this.loading = false;
        this.dataSource = data.rows;
       
      });
    },
    getAll(){
         const data= this.dataSource
         let json= []
         data.forEach((el)=>{
             json.push({
                baseId: this.baseId,
                userAccount: el.userAccount,
                userAccountName: el.userAccountName,
                deptNew: el.deptNew,
                type: this.type
             })
         })
         return JSON.stringify(json)
    }
  },
};
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
