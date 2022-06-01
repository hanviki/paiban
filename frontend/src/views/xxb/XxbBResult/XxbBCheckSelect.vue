<template>
  <div>
    <a-row>
      <a-col :span="2"> 项目名称：</a-col>
      <a-col :span="6">
        <a-input-search placeholder="输入项目名称" v-model="queryParams.projectName" enter-button @search="search" />
      </a-col>
    </a-row>
    <br>
    <!-- 表格区域 -->
    <a-table
      :columns="columns"
      :rowKey="(record) => record.id"
      :loading="loading"
      :data-source="dataSource"
      size="small"
      bordered
      :scroll="{ x: 600 }"
    >
      <a
        slot="operation"
        slot-scope="text, record, index"
        @click="() => selectCheck(record)"
        >选择</a
      >
    </a-table>
  </div>
</template>

<script>
import moment from "moment";
export default {
  name: "XxbBCheckSelect",
  props: {},
  data() {
    return {
      dataSource: [],
      queryParams: {projectName: ''},
      user: this.$store.state.account.user,
      loading: false,
    };
  },
  computed: {
    columns() {
      return [{
          title: "操作",
          dataIndex: "operation",
          scopedSlots: {
            customRender: "operation",
          },
          fixed: "left",
          width: 80,
        },
        {
          title: "院区",
          dataIndex: "yuanqu",
          fixed: "left",
          width: 60,
        },
        {
          title: "申请科室",
          dataIndex: "deptNew",
          fixed: "left",
          width: 120,
        },
        {
          title: "项目名称",
          dataIndex: "projectName",
          fixed: "left",
          width: 200,
        },
        {
          title: "是否为限制类医疗技术",
          dataIndex: "isxzyljs",
          customRender: (text, row, index) => {
            switch (text) {
              case 0:
                return '否'
              case 1:
                return '是'
              default:
                return text
            }
          },
          width: 100,
        },
        {
          title: "项目级别",
          dataIndex: "projectLevel",
          customRender: (text, row, index) => {
            switch (text) {
              case 1:
                return '一级'
              case 2:
                return '二级'
              case 3:
                return '三级'
              case 4:
                return '四级'
              default:
                return text
            }
          },
          width: 80,
        },
        {
          title: "申请日期",
          dataIndex: "applydat",
          customRender: (text, row, index) => {
            if (text == null) return "";
            return moment(text).format("YYYY-MM-DD");
          },
          width: 100,
        },
        {
          title: "项目负责人",
          dataIndex: "userAccountName",
          customRender: (text, row, index) => {
            return row.userAccountName + ' ' + row.userAccount + ' ' + row.yggh
          },
          width: 150,
        },
        {
          title: "第一操作者的最低级别",
          dataIndex: "lev",
          customRender: (text, row, index) => {
            switch (text) {
              case 1:
                return '正高级'
              case 2:
                return '副高级以上'
              case 3:
                return '中级以上'
              case 4:
                return '初级以上'
              case 5:
                return '其他'
              default:
                return text
            }
          },
          width: 100,
        },
        {
          title: "第一操作者类别",
          dataIndex: "typ",
          customRender: (text, row, index) => {
            switch (text) {
              case 1:
                return '医师'
              case 2:
                return '护士'
              case 3:
                return '药剂人员'
              case 4:
                return '技术人员'
              case 5:
                return '其他'
              default:
                return text
            }
          },
          width: 100,
        },
      ];
    },
  },
  mounted() {},
  methods: {
    query () {
      this.queryParams.projectName = ''
      this.search();
    },
    selectCheck(record) {
      this.$emit("setCheckValue",record);
    },
    search() {
      this.dataSource = [];
      this.loading = true;
      this.$get("xxbBResult/findUserCheck", {
        ...this.queryParams,
      })
        .then((r) => {
          this.loading = false;
          this.dataSource = r.data.data.data;
        })
        .catch(() => {
          this.loading = false;
          this.$message.error("获取列表失败!");
        });
    },
  },
};
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
