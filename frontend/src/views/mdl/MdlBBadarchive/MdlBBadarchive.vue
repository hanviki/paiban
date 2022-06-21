<template>
  <a-modal
    v-model="show"
    :centered="true"
    :keyboard="false"
    :footer="null"
    :width="900"
    @cancel="handleCancleClick"
    title="附件列表"
  >
    <div>
      <!-- 表格区域 -->
      <a-table
        ref="TableInfo"
        :columns="columns"
        :rowKey="(record) => record.id"
        :dataSource="dataSource"
        :pagination="pagination"
        :loading="loading"
        :bordered="bordered"
        :scroll="{ x: 900 }"
      >
        <template slot="operation" slot-scope="text, record">
          <a-icon
            type="setting"
            theme="twoTone"
            twoToneColor="#4a9ff5"
            @click="edit(record)"
            title="删除"
          ></a-icon>
        </template>
      </a-table>
    </div>
  </a-modal>
</template>

<script>
import moment from "moment";

const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 15, offset: 1 },
};
export default {
  name: "MdlBBadarchive",
  components: {},
  data() {
    return {
      advanced: false,
      dataSource: [],
      selectedRowKeys: [],
      sortedInfo: null,
      paginationInfo: null,
      formItemLayout,
      pagination: false,
      queryParams: {},
      loading: false,
      bordered: true,
    };
  },
  props: {
    fileVisiable: {
      require: true,
      default: false,
    },
    userAccount: {
      default: "",
    },
  },
  computed: {
    columns() {
      let { sortedInfo } = this;
      sortedInfo = sortedInfo || {};
      return [
        {
          title: "姓名",
          dataIndex: "userAccountName",
          width: 100,
        },
        {
          title: "账号",
          dataIndex: "userAccount",
          width: 100,
        },
        {
          title: "文件时间",
          dataIndex: "fileDate",
          customRender: (text, row, index) => {
            if (text == null) return "";
            return moment(text).format("YYYY-MM-DD");
          },
          width: 100,
        },
        {
          title: "备注",
          dataIndex: "note",
          width: 100,
        },
        {
          title: "附件",
          dataIndex: "fileId",
          customRender: (text, row, index) => {
            if (text != null && text != "") {
              return (
                <a href={this.$baseUrl + row.fileUrl} target="_blank">
                  查看
                </a>
              );
            }
            return "";
          },
          width: 80,
        },
        {
          title: "删除",
          dataIndex: "operation",
          scopedSlots: { customRender: "operation" },
          fixed: "right",
          width: 100,
        },
      ];
    },
    show: {
      get: function () {
        return this.fileVisiable;
      },
      set: function () {},
    },
  },

  methods: {
    moment,

    handleCancleClick() {
      this.$emit("close");
    },
    edit(record) {
      let that = this;
      this.$confirm({
        title: "确定删除此记录?",
        content: "当您点击确定按钮后，此记录将会被彻底删除",
        centered: true,
        onOk() {
          let mdlBBadarchiveIds = record.id;
          that.$delete("mdlBBadarchive/" + mdlBBadarchiveIds).then(() => {
            that.$message.success("删除成功");
            that.selectedRowKeys = [];
            that.search();
          });
        },
        onCancel() {
          that.selectedRowKeys = [];
        },
      });
    },
    exportExcel() {
      let { sortedInfo } = this;
      let sortField, sortOrder;
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field;
        sortOrder = sortedInfo.order;
      }
      this.$export("mdlBBadarchive/excel", {
        sortField: sortField,
        sortOrder: sortOrder,
        ...this.queryParams,
      });
    },
    search() {
      let { sortedInfo } = this;
      let sortField, sortOrder;
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field;
        sortOrder = sortedInfo.order;
      }

      this.fetch(this.userAccount);
    },
    reset() {
      // 取消选中
      this.selectedRowKeys = [];
      // 重置分页

      // 重置列排序规则
      this.sortedInfo = null;

      // 重置查询参数
      this.queryParams = {};
      this.fetch();
    },
    fetch(userAccount) {
      this.loading = true;

      this.$get("mdlBBadarchive", {
        userAccount: userAccount,
      }).then((r) => {
        let data = r.data;
        const pagination = { ...this.pagination };
        pagination.total = data.total;
        this.loading = false;
        this.dataSource = data.rows;
        this.pagination = pagination;
      });
    },
  },
};
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
