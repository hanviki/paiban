<template>
  <div>
    <a-table
      bordered
      :rowKey="(record) => record.deptName"
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
  name: "XxbBProjDeptLook",
  props: {
  },
  data() {
    return {
      dataSource: [],
      baseId: "",
      columns: [
        {
          title: "科室名称",
          dataIndex: "deptName",
        },
        {
          title: "项目贡献度（贡献度之和为1）",
          dataIndex: "gxd",
          width: 300,
        },
        {
          title: "科室主任签名",
          dataIndex: "deptAccountName",
          customRender: (text, row, index) => {
            if (row.deptAccount != null) {
              return row.deptAccountName + ' ' + row.deptAccount
            } else {
              return ""
            }
          },
          width: 400,
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
      this.$get("xxbBCheck/findProjDept", {
        baseId: this.baseId,
      }).then((r) => {
        if (r.data.data.success === 1) {
          this.dataSource = r.data.data.data;
        } else {
          this.$message.error("获取项目参与专科失败.");
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
