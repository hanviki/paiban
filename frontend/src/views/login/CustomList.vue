<template>
  <div class="container">
    <div class="content">
       <a-button  style="margin-bottom: 4px ;width:80%; margin-left:10%;"
                  type="primary"
                  @click="exportCustomExcel"
                >导出</a-button>
       <a-table
      :columns="columns"
      :data-source="dataSource"
      :rowKey="record => record.NACHN"
      :pagination="false"
      bordered
    >
       </a-table>
    </div>
  </div>
</template>
<script>


export default {
  name: 'custom',
  components: {},
  data () {
    return {
      componentName: 'Login',
      mainContent: 'main-content',
      id: '',
      str: '',
      dataSource: [],
    }
  },
  watch: {
    '$route.params.id': {
      handler (e) {
        this.id = e
        this.fetch(e)
      },
      immediate: true,
      deep: true
    }
  },
  computed: {
    systemName () {
      return '华中科技大学同济医学院附属协和医院人力资源信息综合管理平台'
    },
   columns () {
      return [{
        title: '序号',
        dataIndex: 'SERNO',
        width: 60
      },
      {
        title: '科室',
        dataIndex: 'KS',
        width: 120
        
      },
      {
        title: '姓名',
        dataIndex: 'NACHN',
        width: 120
        
      },
      {
        title: '性别',
        dataIndex: 'GESCH',
        width: 60
        
      },
       {
        title: '人员性质',
        dataIndex: 'RYXZ',
        width: 100
        
      },
      ]
   }
  },
  methods: {
     fetch (id) {
       this.$get('weather/info',{id: id}).then((r) => {
          this.dataSource = r.data.data

       });
     },
     exportCustomExcel () {
     
      let json = this.columns
     // json.splice(this.columns.length - 1, 1) //移出第一个
      let dataJson = JSON.stringify(json)

      this.$export('weather/excel', {
        dataJson: dataJson,
        id: this.id
      })
     }
  }
}
</script>

<style lang="less" scoped>
.container {
  display: flex;
  flex-direction: column;
  height: 100vh;
  overflow: auto;
  background-size: 100%;
  .content {
    padding: 32px 0;
    flex: 1;
    @media (min-width: 768px) {
      padding: 116px 0 10px;
    }
    .top {
      text-align: center;
      .header {
        height: 50px;
        line-height: 50px;
        a {
          text-decoration: none;
        }
        .logo {
          width: 40px;
          height: 19px;
          vertical-align: center;
          margin-right: 16px;
        }
        .title {
          font-size: 28px;
          color: rgba(0, 0, 0, 0.85);
          font-family: "Myriad Pro", "Helvetica Neue", Arial, Helvetica,
            sans-serif;
          font-weight: 600;
          position: relative;
          top: 6px;
        }
      }
      .desc {
        font-size: 14px;
        color: rgba(0, 0, 0, 0.45);
        margin-top: 12px;
        margin-bottom: 40px;
      }
    }
    .main-content {
      width: 368px;
      margin: 0 auto;
      @media screen and (max-width: 576px) {
        width: 95%;
      }
      @media screen and (max-width: 320px) {
        .captcha-button {
          font-size: 14px;
        }
      }
    }
    .main-content2 {
      width: 736px;
      margin: 0 auto;
      @media screen and (max-width: 576px) {
        width: 95%;
      }
      @media screen and (max-width: 320px) {
        .captcha-button {
          font-size: 14px;
        }
      }
    }
  }
}
</style>
