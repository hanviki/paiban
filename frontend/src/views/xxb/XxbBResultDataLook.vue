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
  name: "XxbBResultDataLook",
  // components: {
  //   SelectUserRemote,
  // },
  data() {
    return {
      dataSource: [],
      baseId: "",
      columns: [
         {
          title: "序号",
          dataIndex: "displayIndex",
          width: 60,
          fixed: "left",
        },
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
    fetch() {
      this.$get("xxbBResult/findResultD", {
        baseId: this.baseId,
      }).then((r) => {
        if (r.data.data.success === 1) {
          this.dataSource = r.data.data.data;
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
