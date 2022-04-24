<template>
  <div>
    <a-table
      bordered
      :rowKey="(record) => record.userAccount"
      :data-source="dataSource"
      :columns="columns"
      :pagination="false"
      size="small"
      :scroll="{ x: 900 }"
    >
    </a-table>
  </div>
</template>

<script>
export default {
  name: "XxbBDataLook",
  // components: {
  //   SelectUserRemote,
  // },
  data() {
    return {
      dataSource: [],
      baseId: "",
      dataRs: {
        zcysgj: 0,
        zcyszj: 0,
        zcyscj: 0,
        zchsgj: 0,
        zchszj: 0,
        zchscj: 0,
        zcjsgj: 0,
        zcjszj: 0,
        zcjscj: 0,
        zcqtgj: 0,
        zcqtzj: 0,
        zcqtcj: 0,
        xlbs: 0,
        xlyjs: 0,
        xlbk: 0,
        xlqt: 0
      },
      columns: [
        {
          title: "姓名",
          dataIndex: "userAccountName",
          customRender: (text, row, index) => {
            return row.userAccountName + ' ' + row.userAccount + ' ' + row.yggh
          },
          fixed: "left",
          width: 260,
        },
        {
          title: "性别",
          dataIndex: "sexName",
          width: 60,
        },
        {
          title: "年龄",
          dataIndex: "age",
          width: 60,
        },
        {
          title: "学历",
          dataIndex: "edu",
          width: 80,
        },
        {
          title: "临床职称",
          dataIndex: "zhichenglc",
          width: 100,
        },
        {
          title: "职务",
          dataIndex: "zhiwu",
          width: 150,
        },
        {
          title: "专业",
          dataIndex: "zhuany",
          width: 150,
        },
        {
          title: "专长",
          dataIndex: "zhuanc",
          width: 150,
        },
      ],
    };
  },
  mounted() {},
  methods: {
    setFieldValues(id) {
      this.dataSource = [];
      this.baseId = id;
      if (this.baseId !== null && this.baseId !== '') {
        this.fetch();
      }
    },
    reset(){
      this.baseId = null
      this.dataSource = []
    },
    jisuanFor() {
      this.dataRs = {
        zcysgj: 0, zcyszj: 0, zcyscj: 0,
        zchsgj: 0, zchszj: 0, zchscj: 0,
        zcjsgj: 0, zcjszj: 0, zcjscj: 0,
        zcqtgj: 0, zcqtzj: 0, zcqtcj: 0,
        xlbs: 0, xlyjs: 0, xlbk: 0, xlqt: 0
      }
      
      this.dataSource.forEach((item,index) => {
        this.jisuan(item)
      });
      this.$emit("jisuan",this.dataRs)
    },
    jisuan (target) {
      //renshizifw 医师、护理、技术、其他
      //edu 博士、硕士、本科、其他
      //zhicheng 正高副高、中级、初级
      if (target.renshizifw=='医师'){
        if(target.zhicheng == '正高' || target.zhicheng == '副高' ) {
          this.dataRs.zcysgj += 1
        } else if(target.zhicheng == '中级' ) {
          this.dataRs.zcyszj += 1
        } else {
          this.dataRs.zcyscj += 1
        }
      } else if (target.renshizifw=='护理'){
        if(target.zhicheng == '正高' || target.zhicheng == '副高' ) {
          this.dataRs.zchsgj += 1 
        } else if(target.zhicheng == '中级' ) {
          this.dataRs.zchszj += 1 
        } else {
          this.dataRs.zchscj += 1 
        }
      } else if (target.renshizifw=='技术'){
        if(target.zhicheng == '正高' || target.zhicheng == '副高' ) {
          this.dataRs.zcjsgj += 1 
        } else if(target.zhicheng == '中级' ) {
          this.dataRs.zcjszj += 1 
        } else {
          this.dataRs.zcjscj += 1 
        }
      } else {
        if(target.zhicheng == '正高' || target.zhicheng == '副高' ) {
          this.dataRs.zcqtgj += 1 
        } else if(target.zhicheng == '中级' ) {
          this.dataRs.zcqtzj += 1 
        } else {
          this.dataRs.zcqtcj += 1 
        }
      }

      if (target.edu=='博士'){
        this.dataRs.xlbs += 1
      } else if (target.edu=='硕士'){
        this.dataRs.xlyjs += 1
      } else if (target.edu=='本科'){
        this.dataRs.xlbk += 1
      } else {
        this.dataRs.xlqt += 1
      }
    },
    fetch() {
      this.$get("xxbBCheck/findCheckD", {
        baseId: this.baseId,
      }).then((r) => {
        if (r.data.data.success === 1) {
          this.dataSource = r.data.data.data;
          this.jisuanFor()
        } else {
          this.$message.error("获取项目主要人员失败.");
        }
      });
    },
  },
};
</script>

<style scoped>
.editable-cell {
  position: relative;
}

.editable-cell-input-wrapper,
.editable-cell-text-wrapper {
  padding-right: 24px;
}

.editable-cell-text-wrapper {
  padding: 5px 24px 5px 5px;
}

.editable-cell-icon,
.editable-cell-icon-check {
  position: absolute;
  right: 0;
  width: 20px;
  cursor: pointer;
}

.editable-cell-icon {
  line-height: 18px;
  display: none;
}

.editable-cell-icon-check {
  line-height: 28px;
}

.editable-cell:hover .editable-cell-icon {
  display: inline-block;
}

.editable-cell-icon:hover,
.editable-cell-icon-check:hover {
  color: #108ee9;
}

.editable-add-btn {
  margin-bottom: 8px;
}
</style>
