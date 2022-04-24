<template>
  <div>
    <a-row>
      <!-- <a-col :span="8">
        <select-user-remote ref="xxbUser" @userChang="userChange1">
        </select-user-remote>
      </a-col> -->
      <a-col :span="5">
        <a-button class="editable-add-btn" v-if="isEdit" @click="handleAdd"> + 新增项目参与专科 </a-button>
      </a-col>
    </a-row>
    <a-table
      bordered
      :rowKey="(record) => record.deptName"
      :data-source="dataSource"
      :columns="columns"
      :pagination="false"
      size="small"
      :scroll="{ x: 900 }"
    >
      <template
        v-for="col in ['gxd']"
        :slot="col"
        slot-scope="text, record, index"
      >
        <div :key="col">
          <a-input-number 
            style="margin: -5px 0" 
            v-if="isEdit"
            :value="text"
            :min="0"
            :max="1"
            :step="0.01"
            @change="e => handleChange(e, record)"
          />
          <div v-else>{{ text }}</div>
        </div>
      </template>
      <template
        slot='deptName'
        slot-scope="text, record, index"
      >
        <div key='deptName'>
        <a-select
          v-if="isEdit"
          style="width: 100%"
          option-filter-prop="children"
          placeholder="请选择申请科室"
          :value="record.deptName"
          @change="e => handleProjDeptChange(e, record)"
          show-search
        >
          <a-select-option
            v-for="d in deptData"
            :key="d.deptId"
            :value="`${d.deptId}`"
          >
            {{ d.deptName }}
          </a-select-option>
        </a-select>
        <div v-else>{{ text }}</div>
        </div>
      </template>
      <template slot="operation" slot-scope="text, record">
        <a-popconfirm
           v-if="isEdit"
          title="是否删除?"
          @confirm="() => onDelete(record)"
        >
          <a style="color: red" href="javascript:;">删除</a>
        </a-popconfirm>
      </template>
    </a-table>
  </div>
</template>

<script>
export default {
  name: "XxbBProjDept",
  props: {
    deptData: {
      default: [],
    },
  },
  data() {
    return {
      dataSource: [],
      baseId: "",
      isEdit:true,
      xxbBProjDept: {deptName: null},
      columns: [
        {
          title: "科室名称",
          dataIndex: "deptName",
          scopedSlots: {
            customRender: "deptName",
          },
          fixed: "left",
          width: 350,
        },
        {
          title: "项目贡献度（贡献度之和为1）",
          dataIndex: "gxd",
          scopedSlots: {
            customRender: "gxd",
          },
          width: 200,
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
          width: 200,
        },
        {
          title: "操作",
          dataIndex: "operation",
          scopedSlots: {
            customRender: "operation",
          },
          fixed: "right",
          width: 75,
        },
      ],
    };
  },
  mounted() {},
  methods: {
    setFieldValues(id,isEdit) {
      this.isEdit = isEdit
      this.xxbBProjDept = {deptName: null};
      this.dataSource = [];
      this.baseId = id;
      if (this.baseId !== null && this.baseId !== '') {
        this.fetch();
      }
    },
    reset(){
      this.xxbBProjDept = {deptName: null};
      this.baseId = null
      this.dataSource = []
    },
    getFieldValues() {
      this.cleanData();
      return this.dataSource;
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
    onDelete(obj) {
      if (obj.id == null) {
        const index = this.dataSource.indexOf(obj)
        const newList = this.dataSource.slice()
        newList.splice(index, 1)
        this.dataSource = newList
      } else {
        this.$get("xxbBCheck/delProjDept", {
          id: obj.id
        }).then((r) => {
          if (r.data.data.success === 1) {
            this.$message.success("删除项目参与专科成功.");
            this.dataSource = [];
            this.fetch();
          } else {
            this.$message.error("删除项目参与专科失败.");
          }
        });
      }
    },
    handleChange(value, record) {
      record.gxd = value
      const newDataSource = [...this.dataSource];
      let sumGxd = 0;
      newDataSource.forEach((item,index) => {
        sumGxd += item.gxd
      });
      if (sumGxd > 1) {
        record.gxd = 0
      }
    },
    handleProjDeptChange (value, record) {
      let data = null
      const newDataSource = [...this.dataSource];
      data = newDataSource.filter((item) => value === item.deptName)[0];
      if(data) {
        this.$message.error(
          "项目参与专科-科室名称：" + 
            value +
            "已存在."
        );
        record.deptName = ""
        return
      }
      record.deptName = value
    },
    cleanData() {
      this.xxbBProjDept = {deptName: null};
    },
    handleAdd() {
      let projDept = {
        deptName: '',
        gxd: ''
      }
      this.addDataSource(projDept);
    },
    addDataSource(obj) {
      const { dataSource } = this;
      const newData = obj;
      this.dataSource = [newData, ...dataSource];
      this.cleanData();
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
