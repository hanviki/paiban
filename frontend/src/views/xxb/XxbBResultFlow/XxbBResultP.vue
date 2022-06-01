<template>
  <div>
    <a-row style="padding: 5px 0px 15px 0px;font-size:16px">
      <a-col :span="5">
        <b>科　　　室：{{obj.deptNew}}</b>
      </a-col>
      <a-col :span="7">
        <b>项目名称：{{obj.projectName}}</b>
      </a-col>
      <a-col :span="8">
        <b>项目负责人：{{obj.userAccountName}}  {{obj.userAccount}}  {{obj.zhichenglc}}</b>
      </a-col>
      <a-col :span="4">
        <b>{{state == 1 ? '院外评审得分：' + obj.ywtotal : '院内评审得分：' + obj.yntotal}}</b>
      </a-col>
    </a-row>
    <a-table
      bordered
      :rowKey="(record) => record.id"
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
  name: "XxbBResultP",
  data() {
    return {
      dataSource: [],
      obj: [],
      baseId: "",
      state: 0,
      xxbBResultP: {},
      columns: []
    };
  },
  mounted() {},
  methods: {
    setFieldValues(obj, openstate) {
      this.columns = [
        {
          title: openstate == 1 ? '外审专家' : '院内专家',
          dataIndex: "dfname",
          fixed: "left",
          width: 150,
        },
        {
          title: openstate == 1 ? '外审单位' : '专家科室',
          dataIndex: "dfwork",
          fixed: "left",
          width: 300,
        },
        {
          title: openstate == 1 ? '外审专家职称' : '专家职称',
          dataIndex: "dfzhic",
          width: 200,
        },
        {
          title: "创新性",
          dataIndex: "cxx",
          width: 100,
        },
        {
          title: "实用性",
          dataIndex: "syx",
          width: 100,
        },
        {
          title: "技术难度",
          dataIndex: "jsnd",
          width: 100,
        },
        {
          title: "效益性",
          dataIndex: "xyx",
          width: 100,
        },
        {
          title: openstate == 1 ? '外审评分' : '院内评审最终得分',
          dataIndex: "total",
          width: 200,
          fixed: "right",
        },
      ],
      this.state = openstate
      this.xxbBResultP = {};
      this.dataSource = [];
      this.obj = obj;
      this.baseId = obj.id;
      if (this.baseId !== null && this.baseId !== '') {
        this.fetch();
      }
    },
    reset(){
      this.xxbBResultP = {};
      this.baseId = null
      this.dataSource = []
    },
    fetch() {
      this.$get("xxbBResult/findResultP", {
        baseId: this.baseId,
        state: this.state
      }).then((r) => {
        if (r.data.data.success === 1) {
          this.dataSource = r.data.data.data;
        } else {
          this.$message.error("获取打分数据失败.");
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
