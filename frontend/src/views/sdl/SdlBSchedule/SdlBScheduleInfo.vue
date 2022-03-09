<template>
  <a-drawer
    :title="`排班：${startDate}至${endDate}查看`"
    :maskClosable="false"
    width="90%"
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="editVisiable"
    style="height: calc(100% - 55px); overflow: auto; padding-bottom: 53px"
  >
    <a-table
      ref="TableInfo"
      :columns="columns"
      :rowKey="(record) => record.id"
      :dataSource="dataSource"
      :loading="loading"
      :bordered="bordered"
      :pagination="false"
      :scroll="{ x: 900 ,y: tableHeight - 200 - 100,}"
    >
      <template
        v-for="col in listAuditInfo"
        :slot="col.filedName"
        slot-scope="text, record"
      >
        <div :key="col.filedName">
          {{ record[col.filedName] }}
        </div>
        <!-- </template> -->
      </template>
    </a-table>
    <div class="drawer-bootom-button">
      <a-button style="margin-right: 0.8rem" @click="onClose">关闭</a-button>
    </div>
  </a-drawer>
</template>
<script>
import moment from "moment";

const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 },
};
export default {
  name: "SdlBScheduleEdit",
  props: {
    editVisiable: {
      default: false,
    },
  },
  data() {
    return {
      loading: false,
      formItemLayout,
      dataSource: [],
      form: this.$form.createForm(this),
      sdlBSchedule: {},
      columns: [
        {
          title: "值班类型",
          dataIndex: "zizhiName",
          width: 100,
          fixed: "left",
        },
        {
          title: "病区",
          dataIndex: "bqName",
          width: 100,
          fixed: "left",
        },
      ],
      listAuditInfo: [], // 当前用户包含的审核数据
      colsCustom: [],
      bordered: true,
      userData: [],
      optionData: [],
      copyData: [], //复制的数据
      startDate: "",
      endDate: "",
      startDate_hide: "",
      baseId: "",
      deptId: "",
      auditSuggestion: "",
      tableHeight: window.innerHeight,
    };
  },
  methods: {
    reset() {
      this.loading = false;
      this.form.resetFields();
      this.dataSource = [];
      this.userData = [];
      this.copyData = [];
      this.optionData = [];
      this.colsCustom = [];
      this.listAuditInfo = [];
      this.startDate = "";
      this.endDate = "";
      this.startDate_hide = "";
      this.fetchBancibaseId = "";
      this.auditSuggestion = "";
      this.columns = [
        {
          title: "值班类型",
          dataIndex: "zizhiName",
          width: 120,
          fixed: "left",
        },
        {
          title: "病区",
          dataIndex: "bqName",
          width: 120,
          fixed: "left",
        },
      ];
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },

    handleUser(record) {
      var zizhiIds = record.subIds;
      console.info(record);
      var optionData = this.optionData.filter(
        (p) => zizhiIds.indexOf(p.userType) >= 0
      );
      //  console.info(optionData)
      return optionData;
    },
    setFormValues({ ...sdlBSchedule }) {
      this.startDate = moment(sdlBSchedule.startDate).format("YYYY-MM-DD");
      this.startDate_hide = sdlBSchedule.startDate;
      this.endDate = moment(sdlBSchedule.endDate).format("YYYY-MM-DD");
      this.baseId = sdlBSchedule.id;
      this.deptId = sdlBSchedule.deptId;
    },
    fetchUser(value) {
      this.optionData = [];
      if (value == " ") {
        this.optionData = this.userData;
        return;
      }
      const data = this.userData;
      const options = data.filter(function (item, index, array) {
        return item.userAccountName.indexOf(value) >= 0;
      });
      //  console.info(options);
      this.optionData = options;
    },
    handleSelectChange(value, option, record, filedName) {
      record[filedName] = value;
    },
    handleCopy(record, filedName) {
      this.copyData = record[filedName];
    },
    handlePaste(record, col) {
      if (this.copyData == "") {
        this.$message.success("复制数据为空，请重新复制");
      } else {
        let userAccounts = this.userData
          .filter((f) => record.subIds.indexOf(f.userType) >= 0)
          .map((p) => p.userAccount);
        let intersection = this.copyData.filter(
          (t) => userAccounts.indexOf(t) >= 0
        ); //取合集
        // this.listAuditInfo =[]
        //防止行和列刷新  这样同时定位到这个组件 进行刷新
        record[col.filedName] = intersection;
        col.isShow = false;
        record.state = 1;
        setTimeout(() => {
          record.state = 0;
          col.isShow = true;
        }, 300);
      }
    },
    getWeekName(n) {
      let date2 = moment(this.startDate_hide)
        .add(n - 1, "days")
        .format("YYYY-MM-DD");
      if (n == 1) {
        return "周一(" + date2 + ")";
      }
      if (n == 2) {
        return "周二(" + date2 + ")";
      }
      if (n == 3) {
        return "周三(" + date2 + ")";
      }
      if (n == 4) {
        return "周四(" + date2 + ")";
      }
      if (n == 5) {
        return "周五(" + date2 + ")";
      }
      if (n == 6) {
        return "周六(" + date2 + ")";
      }
      if (n == 7) {
        return "周日(" + date2 + ")";
      }
    },
    getWeekHeaderColor(n) {
      if (n == 1) {
        return "LightCyan";
      }
      if (n == 2) {
        return "lightGreen";
      }
      if (n == 3) {
        return "orange";
      }
      if (n == 4) {
        return "lavender";
      }
      if (n == 5) {
        return "lightBlue";
      }
      if (n == 6) {
        return "linen";
      }
      if (n == 7) {
        return "LightSteelBlue";
      }
    },
    handleSubmit() {
      let that = this;

      that.form.validateFields((err, values) => {
        if (!err) {
          that.$confirm({
            title: "确定审核通过此记录?",
            content: "当您点击确定按钮后，这些记录将会被审核",
            centered: true,
            onOk() {
              let sdlBSchedule = {};
              sdlBSchedule.id = that.baseId;
              sdlBSchedule.auditSuggestion = that.auditSuggestion;
              sdlBSchedule.state = 3;
              that
                .$put("sdlBSchedule", {
                  ...sdlBSchedule,
                })
                .then(() => {
                  that.reset();
                  that.$emit("success");
                })
                .catch(() => {
                  that.loading = false;
                });
            },
            onCancel() {
              // that.selectedRowKeys = [];
            },
          });
        }
      });
    },
    handleNoSubmit() {
      let that = this;
      if (that.auditSuggestion == "") {
        this.$message.warning("请输入审核意见");
        return;
      }
      that.form.validateFields((err, values) => {
        if (!err) {
          that.$confirm({
            title: "确定审核不通过此记录?",
            content: "当您点击确定按钮后，这些记录将会被审核不通过",
            centered: true,
            onOk() {
              let sdlBSchedule = {};
              sdlBSchedule.id = that.baseId;
              sdlBSchedule.state = 2;
              sdlBSchedule.auditSuggestion = that.auditSuggestion;
              that
                .$put("sdlBSchedule", {
                  ...sdlBSchedule,
                })
                .then(() => {
                  that.reset();
                  that.$emit("success");
                })
                .catch(() => {
                  that.loading = false;
                });
            },
            onCancel() {
              // that.selectedRowKeys = [];
            },
          });
        }
      });
    },
    fetchBanci() {
      this.$get("sdlBScheduleD/bancidept", {
        deptId: this.deptId,
        startDateFrom: this.startDate,
        startDateTo: this.endDate,
      }).then((r) => {
        //.info(r.data)
        // this.listAuditInfo = r.data;
        const cols = [];
        for (var i = 1; i < 8; i++) {
          let clo = [];
          r.data.forEach((element) => {
            if (
              (element.id == "4" ||
                element.id == "5") &&
              element.holiday.indexOf(i) < 0
            ) {
            } else {
              cols.push({
                filedName: "B" + element.id + "_" + i,
                isShow: true,
              });
              clo.push({
                title: element.muduleName,
                dataIndex: "B" + element.id + "_" + i,
                width: 250,
                customCell: function () {
                  return { style: { backgroundColor: element.colorName } };
                },
                scopedSlots: { customRender: "B" + element.id + "_" + i },
              });
            }
          });
          this.columns.push({
            title: this.getWeekName(i),
            customHeaderCell: (h) => {
              return {
                style: { backgroundColor: this.getWeekHeaderColor(h.key - 1) },
              };
            },
            children: clo,
          });
        }
        this.colsCustom = cols;
        this.listAuditInfo = cols;
        //  console.info(this.listAuditInfo)
        // this.columns.push({
        //   title: "操作",
        //   dataIndex: "action",
        //   width: 130,
        //   scopedSlots: { customRender: "action" },
        // });
      });
    },
    fetch() {
      this.$get("sdlBScheduleD/zizhidept", {
        baseId: this.baseId,
        deptId: this.deptId,
        startDate: this.startDate,
        endDate: this.endDate,
      }).then((r) => {
        let data = r.data;
        data.forEach((element) => {
          let auditList = element.dynamicData;
          //  console.info(auditList)
          if (auditList == null || auditList.length == 0) {
          } else {
            auditList.forEach((element2) => {
              var week = moment(element2.scheduleDate).day();
              if (week == 0) {
                week = week + 7;
              }
              element["B" + element2.banciId + "_" + week] =
                element2.accountName;
              // element.auditNote = element2.auditNote == 'null' ? "" : element2.auditNote
            });
          }
        });
        this.dataSource = data;
      });
    },
    fetchDept() {
      this.$get("sdlBUser", {
        pageSize: 10000,
        page: 1,
        deptId: this.deptId,
      }).then((r) => {
        let data = r.data;
        this.userData = data.rows;
        this.optionData = data.rows;
        this.fetch()
      });
    },
  },
  watch: {
    editVisiable() {
      if (this.editVisiable) {
        this.fetchBanci();
        // setTimeout(() => {
        //   this.fetch();
        // }, 200);
        this.fetchDept();
      }
    },
  },
};
</script>
