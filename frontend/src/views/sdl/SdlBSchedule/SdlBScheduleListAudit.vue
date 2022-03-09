<template>
  <a-card :bordered="false" class="card-area">
    <div :class="advanced ? 'search' : null">
      <a-form layout="horizontal">
        <a-row>
          <div :class="advanced ? null : 'fold'">
            <a-col :md="6" :sm="24">
              <a-form-item v-bind="formItemLayout" label="排班科室">
                <a-select v-model="queryParams.deptId"
                  option-filter-prop="children"
         :filter-option="filterOption"
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
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item label="排班周" v-bind="formItemLayout">
                <a-week-picker
                  :format="dateFormat"
                  style="width: 100%"
                  @change="handleWeekChange"
                >
                </a-week-picker>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item label="排班状态" v-bind="formItemLayout">
                <a-select style="width: 100%" v-model="queryParams.state">
                  <a-select-option value="-1"> 全部 </a-select-option>
                  <a-select-option value="0"> 未提交 </a-select-option>
                  <a-select-option value="1"> 已提交 </a-select-option>
                  <a-select-option value="2"> 审核未通过 </a-select-option>
                  <a-select-option value="3"> 已审核 </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item label="排班修改状态" v-bind="formItemLayout">
                <a-select
                  style="width: 100%"
                  v-model="queryParams.stateApplyFlag"
                >
                  <a-select-option value="-1"> 全部 </a-select-option>
                  <!-- <a-select-option value="1"> 未发起申请 </a-select-option> -->
                  <a-select-option value="2"> 申请待审核 </a-select-option>
                  <a-select-option value="3"> 申请未通过 </a-select-option>
                  <a-select-option value="4"> 申请已通过 </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
          </div>
          <span style="float: right; margin-top: 3px">
            <a-button type="primary" @click="search">查询</a-button>
            <a-button style="margin-left: 8px" @click="reset">重置</a-button>
          </span>
        </a-row>
      </a-form>
    </div>
    <!-- 表格区域 -->
    <div class="operator">
      <a-button type="primary" ghost @click="audit">批量审核通过</a-button>
    </div>
    <a-table
      ref="TableInfo"
      :columns="columns"
      :rowKey="(record) => record.id"
      :dataSource="dataSource"
      :pagination="pagination"
      :loading="loading"
      :rowSelection="{
        selectedRowKeys: selectedRowKeys,
        onChange: onSelectChange,
      }"
      @change="handleTableChange"
      :bordered="bordered"
      :scroll="{ x: 900 }"
    >
      <template slot="remark" slot-scope="text, record">
        <a-popover placement="topLeft">
          <template slot="content">
            <div style="max-width: 200px">{{ text }}</div>
          </template>
          <p style="width: 200px; margin-bottom: 0">{{ text }}</p>
        </a-popover>
      </template>
      <template slot="operation" slot-scope="text, record">
         <a-button
            icon="search"
            @click="info(record)"
          >查看</a-button>
        <a-button v-if="record.state == 1" icon="edit" @click="edit(record)"
          >审核</a-button>
      </template>
    </a-table>

    <!-- 审核界面-->
    <sdlBSchedule-edit
      ref="sdlBScheduleEdit"
      @close="handleEditClose"
      @success="handleEditSuccess"
      :editVisiable="editVisiable"
    >
    </sdlBSchedule-edit>
     <sdlBSchedule-info
      ref="sdlBScheduleInfo"
      @close="handleInfoClose"
      :editVisiable="infoVisiable"
    >
     </sdlBSchedule-info>
    <a-modal
      v-model="applyVisiable"
      :centered="true"
      :keyboard="false"
      :width="800"
      title="批量审核数据"
    >
      <template slot="footer">
        <a-button key="back" @click="hideModal"> 取消 </a-button>
        <a-button @click="handleSubmit" type="primary" :loading="loading"
          >审核通过</a-button
        >
        <a-button @click="handleNoSubmit" type="danger" :loading="loading"
          >审核不通过</a-button
        >
      </template>
      <a-textarea
        :rows="8"
        placeholder="请输入审核意见"
        v-model="auditSuggestion"
      />
    </a-modal>
  </a-card>
</template>

<script>
import SdlBScheduleEdit from "./SdlBScheduleListEdit";
import SdlBScheduleInfo from "./SdlBScheduleInfo";
import moment from "moment";

const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 15, offset: 1 },
};
export default {
  name: "SdlBSchedule",
  components: { SdlBScheduleEdit, SdlBScheduleInfo },
  data() {
    return {
      advanced: false,
      dateFormat: "YYYY-MM-DD",
      dataSource: [],
      selectedRowKeys: [],
      sortedInfo: {
        order: "descend",
        field: "start_date",
      },
      paginationInfo: null,
      formItemLayout,
      pagination: {
        pageSizeOptions: ["10", "20", "30", "40", "100"],
        defaultCurrent: 1,
        defaultPageSize: 10,
        showQuickJumper: true,
        showSizeChanger: true,
        showTotal: (total, range) =>
          `显示 ${range[0]} ~ ${range[1]} 条记录，共 ${total} 条记录`,
      },
      queryParams: {},
      form: this.$form.createForm(this),
      addVisiable: false,
      editVisiable: false,
      infoVisiable: false,
      loading: false,
      bordered: true,
      applyVisiable: false,
      editRcordId: "", //申请ID
      deptData: [],
      auditSuggestion: "",
      applyVisiable: false,
    };
  },
  computed: {
    columns() {
      let { sortedInfo } = this;
      sortedInfo = sortedInfo || {};
      return [
        {
          title: "科室",
          dataIndex: "deptName",
          width: 100,
        },
        {
          title: "开始日期",
          dataIndex: "startDate",
          width: 100,
          customRender: (text, row, index) => {
            if (text == null) return "";
            return moment(text).format("YYYY-MM-DD");
          },
        },
        {
          title: "结束日期",
          dataIndex: "endDate",
          width: 100,
          customRender: (text, row, index) => {
            if (text == null) return "";
            return moment(text).format("YYYY-MM-DD");
          },
        },
        {
          title: "考勤状态",
          dataIndex: "state",
          width: 100,
          customRender: (text, row, index) => {
            switch (text) {
              case 0:
                return <a-tag color="purple">未提交</a-tag>;
              case 1:
                return <a-tag color="green">已提交</a-tag>;
              case 2:
                return <a-tag color="red">审核未通过</a-tag>;
              case 3:
                return <a-tag color="#f50">已审核</a-tag>;
              case 9:
                return <a-tag color="orange">已完成</a-tag>;
              default:
                return text;
            }
          },
        },
        {
          title: "排班修改状态",
          dataIndex: "stateApplyFlag",
          width: 100,
          customRender: (text, row, index) => {
            switch (text) {
              case 1:
                return <a-tag color="red">未发起申请</a-tag>;
              case 2:
                return <a-tag color="green">待审核</a-tag>;
              case 3:
                return <a-tag color="red">申请未通过</a-tag>;
              case 4:
                return <a-tag color="#f50">已通过申请</a-tag>;
              case 0:
                return "";
              default:
                return text;
            }
          },
        },
        {
          title: "申请理由",
          dataIndex: "note",
          width: 200,
        },
        {
          title: "审核意见",
          dataIndex: "auditSuggestion",
          width: 200,
        },
        {
          title: "操作",
          dataIndex: "operation",
          scopedSlots: { customRender: "operation" },
          fixed: "right",
          width: 250,
        },
      ];
    },
  },
  mounted() {
    this.search();
    this.fetchDept();
  },
  methods: {
    onSelectChange(selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys;
    },
    toggleAdvanced() {
      this.advanced = !this.advanced;
      if (!this.advanced) {
        this.queryParams.comments = "";
      }
    },
    handleWeekChange(weekData) {
      console.info(weekData);
      if (weekData == null) {
        this.queryParams.startDateFrom = "";
      } else {
        this.queryParams.startDateFrom = moment(weekData)
          .day(1)
          .format("YYYY-MM-DD"); // 周一日期
      }
    },
    hideModal() {
      this.applyVisiable = false;
    },
        filterOption(input, option) {
      return (
        option.componentOptions.children[0].text.toLowerCase().indexOf(input.toLowerCase()) >= 0
      );
    },
    audit() {
      if (!this.selectedRowKeys.length) {
        this.$message.warning("请选择需要审核的记录");
      } else {
        this.auditSuggestion = '';
        this.applyVisiable = true;
      }
    },
    saveApply() {
      this.form.validateFields((err, values) => {
        if (!err) {
          let sdlBSchedule = this.form.getFieldsValue();
          sdlBSchedule.id = this.editRcordId;
          this.$put("sdlBSchedule/apply", {
            ...sdlBSchedule,
          })
            .then(() => {
              this.applyVisiable = false;
              this.editRcordId = "";
              this.$message.success("发起申请成功");
              this.search();
            })
            .catch(() => {
              this.loading = false;
            });
        }
      });
    },
    getButtonState(record) {
      //1是编辑 2是提交申请 3.改登  4.都不显示
      if (record.stateApplyFlag != 0) {
        //只要是申请审核通过,都是编辑
        if (record.stateApplyFlag == 4) {
          //申请审核通过
          return 1;
        }
        if (record.stateApplyFlag == 3) {
          //申请审核不通过
          return 3;
        }
        if (record.stateApplyFlag == 1) {
          //待发起审核
          return 2;
        }
        if (record.stateApplyFlag == 2) {
          //申请中
          return 4;
        }
      }
      if (record.isExpired) {
        //过期
        return 3;
      }
      return 1;
    },
    apply(record) {
      this.applyVisiable = true;
      this.editRcordId = record.id;
    },
    handleAddSuccess() {
      this.addVisiable = false;
      this.$message.success("新增成功");
      this.search();
    },
    handleAddClose() {
      this.addVisiable = false;
    },
    add() {
      this.addVisiable = true;
    },
    handleEditSuccess() {
      this.editVisiable = false;
      this.$message.success("修改成功");
      this.search();
    },
    handleEditClose() {
      this.editVisiable = false;
    },
    handleInfoClose() {
      this.infoVisiable = false;
    },
    info(record){
      this.$refs.sdlBScheduleInfo.setFormValues(record);
      this.infoVisiable = true;
    },
    handleSubmit() {
      let that = this;
      if (!this.selectedRowKeys.length) {
        this.$message.warning("请选择需要审核通过的记录");
        return;
      }
      that.$confirm({
        title: "确定审核通过此记录?",
        content: "当您点击确定按钮后，这些记录将会被审核",
        centered: true,
        onOk() {
          let sdlBScheduleIds = that.selectedRowKeys.join(",");
          that
            .$put("sdlBSchedule/batch", {
              ids: sdlBScheduleIds,
              auditSuggestion: that.auditSuggestion,
              state: 3,
            })
            .then(() => {
              that.hideModal();
              that.search();
            })
            .catch(() => {
              that.loading = false;
            });
        },
        onCancel() {
          // that.selectedRowKeys = [];
        },
      });
    },
    handleNoSubmit() {
      let that = this;
      if (!this.selectedRowKeys.length) {
        this.$message.warning("请选择需要审核不通过的记录");
        return;
      }
      if (that.auditSuggestion == "") {
        this.$message.warning("请输入审核意见");
        return;
      }

      that.$confirm({
        title: "确定审核不通过此记录?",
        content: "当您点击确定按钮后，这些记录将会被审核不通过",
        centered: true,
        onOk() {
          let sdlBScheduleIds = that.selectedRowKeys.join(",");
          that
            .$put("sdlBSchedule/batch", {
              ids: sdlBScheduleIds,
              auditSuggestion: that.auditSuggestion,
              state: 2,
            })
            .then(() => {
              that.hideModal();
              that.search();
            })
            .catch(() => {
              that.loading = false;
            });
        },
        onCancel() {
          // that.selectedRowKeys = [];
        },
      });
    },
    edit(record) {
      this.$refs.sdlBScheduleEdit.setFormValues(record);
      this.editVisiable = true;
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
          let sdlBScheduleIds = that.selectedRowKeys.join(",");
          that.$delete("sdlBSchedule/" + sdlBScheduleIds).then(() => {
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
      this.$export("sdlBSchedule/excel", {
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
      let queryParams = { ...this.queryParams };
      if (queryParams.deptId == "-1") {
        delete queryParams.deptId;
      }
      if (queryParams.state == -1) {
        delete queryParams.state;
      }
      if (queryParams.stateApplyFlag == -1) {
        delete queryParams.stateApplyFlag;
      }
      this.fetch({
        sortField: sortField,
        sortOrder: sortOrder,
        ...queryParams,
      });
    },
    reset() {
      // 取消选中
      this.selectedRowKeys = [];
      // 重置分页
      this.$refs.TableInfo.pagination.current = this.pagination.defaultCurrent;
      if (this.paginationInfo) {
        this.paginationInfo.current = this.pagination.defaultCurrent;
        this.paginationInfo.pageSize = this.pagination.defaultPageSize;
      }
      // 重置列排序规则
      this.sortedInfo = null;
      this.paginationInfo = null;
      // 重置查询参数
      this.queryParams = {};
      this.fetch();
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
    fetchDept() {
      this.$get("dept/list", { parentId: "0" }).then((res) => {
        this.deptData = [];
        this.deptData.push({
          deptId: "-1",
          deptName: "全部",
        });
        this.deptData.push(...res.data);
      });
    },
    fetch(params = {}) {
      this.loading = true;
      if (this.paginationInfo) {
        // 如果分页信息不为空，则设置表格当前第几页，每页条数，并设置查询分页参数
        this.$refs.TableInfo.pagination.current = this.paginationInfo.current;
        this.$refs.TableInfo.pagination.pageSize = this.paginationInfo.pageSize;
        params.pageSize = this.paginationInfo.pageSize;
        params.pageNum = this.paginationInfo.current;
      } else {
        // 如果分页信息为空，则设置为默认值
        params.pageSize = this.pagination.defaultPageSize;
        params.pageNum = this.pagination.defaultCurrent;
      }
       if (params.deptId == "-1") {
        delete params.deptId;
      }
      if (params.state == -1) {
        delete params.state;
      }
      if (params.stateApplyFlag == -1) {
        delete params.stateApplyFlag;
      }
      this.$get("sdlBSchedule/auditList", {
        ...params,
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
