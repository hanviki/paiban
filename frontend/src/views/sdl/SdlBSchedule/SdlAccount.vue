
<template>
  <a-modal
    v-model="applyVisiable"
    :centered="true"
    :keyboard="false"
    :width="900"
    :footer="null"
    cancel-text="取消"
    @cancel="hideModal"
    title="排班记录"
  >
    <a-table
      ref="TableInfo"
      :columns="columns"
      :rowKey="(record) => record.id"
      :dataSource="dataSource"
      :loading="loading"
      :bordered="bordered"
      :scroll="{ x: 800 }"
    >
    </a-table>
  </a-modal>
</template>
<script>
import moment from 'moment'
const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 15, offset: 1 },
};
export default {
  name: "SdlBScheduleDetail",
  components: {},
  data() {
    return {
      advanced: false,
      dataSource: [],
      selectedRowKeys: [],
      sortedInfo: null,
      paginationInfo: null,
      formItemLayout,

      loading: false,
      bordered: true,
    };
  },
  props: {
    applyVisiable: {
      require: true,
      default: false,
    },
    queryParams: {
        default: {}
    }
  },
  watch: {
    applyVisiable() {
      if (this.applyVisiable) {
        this.fetch(this.queryParams)
      }
    },
  },
  computed: {
    columns() {
      return [
           {
          title: "姓名",
          dataIndex: "accountName",
          width: 100,
        },
         {
          title: "发薪号",
          dataIndex: "accountId",
          width: 100,
        },
        {
          title: "科室",
          dataIndex: "deptName",
          width: 100,
        },
        {
          title: "病区",
          dataIndex: "bqName",
          width: 100,
        },
        {
          title: "排班日期",
          dataIndex: "scheduleDate",
          width: 100,
           customRender: (text, row, index) => {
            if(text == null) return ''
            return moment(text).format('YYYY-MM-DD')
          },
        },
        {
          title: "班次",
          dataIndex: "banci",
          width: 100,
        },
        {
          title: "人员类型",
          dataIndex: "zizhiName",
          width: 100,
        },
      ];
    },
  },

  methods: {
    reset() {
      // 取消选中
      this.selectedRowKeys = [];
      this.dataSource= []
    },
    hideModal(){
       this.$emit("closeAccount");
    },
    fetch(params = {}) {
      this.loading = true;
      this.$get("sdlBScheduleDetail/accountDetail", {
        ...params,
      }).then((r) => {
        let data = r.data;
        this.loading = false;
        this.dataSource = data;
      });
    },
  },
};
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>