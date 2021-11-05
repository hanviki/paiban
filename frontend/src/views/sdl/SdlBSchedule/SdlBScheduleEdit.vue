<template>
  <a-drawer
    :title="`排班：${startDate}至${endDate}`"
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
      :scroll="{
        x: 900,
        y: tableHeight - 200 - 100,
      }"
    >
      <template slot="zizhiName" slot-scope="text, record">
        <div>
          {{ text }}
        </div>
      </template>
      <template slot="bqName" slot-scope="text, record">
        <div>
          {{ text }}
        </div>
      </template>
      <template
        v-for="col in listAuditInfo"
        :slot="col.filedName"
        slot-scope="text, record"
      >
        <div :key="col.filedName">
          <!-- <div :key="`B${col.banciId}_${countWeek}`"> -->
          <!-- <template slot="user" slot-scope="text, record"> -->
          <a-popover title="单元格数据处理" placement="right">
            <template slot="content">
              <a-button
                @click="handleCopy(record, col.filedName)"
                :loading="loading"
                >复制</a-button
              >
              <a-button @click="handlePaste(record, col)" :loading="loading"
                >粘贴</a-button
              >
            </template>
            <a-select
              style="width: 100%"
              mode="multiple"
              v-if="col.isShow || record.state != 1"
              :default-value="record[col.filedName]"
              :filter-option="false"
              @search="(v) => fetchUser(v, record, col.filedName)"
              placeholder="请选择排班人员"
              @change="
                (e, f) => handleSelectChange(e, f, record, col.filedName)
              "
            >
              <a-select-option
                v-for="item in handleUser(record, col.filedName)"
                :key="item.userAccount"
                :value="item.userAccount"
              >
                {{ item.userAccount + "_" + item.userAccountName }}
              </a-select-option>
            </a-select>
          </a-popover>
        </div>
        <!-- </template> -->
      </template>
    </a-table>
    <div class="drawer-bootom-button">
      <a-popconfirm
        title="确定放弃编辑？"
        @confirm="onClose"
        okText="确定"
        cancelText="取消"
      >
        <a-button style="margin-right: 0.8rem">取消</a-button>
      </a-popconfirm>
      <a-button @click="handleSubmit" type="primary" :loading="loading"
        >提交</a-button
      >
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
          scopedSlots: { customRender: "zizhiName" },
        },
        {
          title: "病区",
          dataIndex: "bqName",
          width: 100,
          fixed: "left",
          scopedSlots: { customRender: "bqName" },
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
      windowHeight: document.documentElement.clientHeight,
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
      this.columns = [
        {
          title: "值班类型",
          dataIndex: "zizhiName",
          width: 120,
          fixed: "left",
          scopedSlots: { customRender: "zizhiName" },
        },
        {
          title: "病区",
          dataIndex: "bqName",
          width: 120,
          fixed: "left",
          scopedSlots: { customRender: "bqName" },
        },
      ];
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },
    resertUser() {
      this.optionData = this.userData;
    },
    handleUser(record, filedName) {
      //  console.info(filedName+"_2")
      let options = record[filedName + "_2"];
      if (options != undefined && options.length > 0) {
        var zizhiIds = record.subIds;
        // console.info(record);
        var optionData = options.filter(
          (p) => zizhiIds.indexOf(p.userType) >= 0
        );
        //  console.info(optionData)
        return optionData;
      } else {
        return [];
      }
    },
    setFormValues({ ...sdlBSchedule }) {
      this.startDate = moment(sdlBSchedule.startDate).format("YYYY-MM-DD");
      this.startDate_hide = sdlBSchedule.startDate;
      this.endDate = moment(sdlBSchedule.endDate).format("YYYY-MM-DD");
      this.baseId = sdlBSchedule.id;
    },
    fetchUser(value2, record, filedName) {
      let value = value2.trim();
      if (value == "") {
        record[filedName + "_2"] = this.userData;
        return;
      }
      console.info(value);

      const data = this.userData;
      const options = data.filter(function (item, index, array) {
        return item.userAccountName.indexOf(value) >= 0;
      });
      //  console.info(options);
      record[filedName + "_2"] = options;
    },
    handleSelectChange(value, option, record, filedName) {
      console.info(filedName);
      record[filedName] = value;
    },
    handleCopy(record, filedName) {
      console.info(record[filedName]);
      this.copyData = record[filedName];
    },
    handlePaste(record, col) {
      if (this.copyData == "") {
        this.$message.success("复制数据为空，请重新复制");
      } else {
        this.optionData = this.userData;
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

      // setTimeout(()=>{
      //   this.listAuditInfo= this.colsCustom
      // },300)
      // const data= this.dataSource
      // this.dataSource=[]
      // setTimeout(()=>{
      //   this.dataSource= data
      // },300)
    },
    getWeekName(n) {
      if (n == 1) {
        return "周一";
      }
      if (n == 2) {
        return "周二";
      }
      if (n == 3) {
        return "周三";
      }
      if (n == 4) {
        return "周四";
      }
      if (n == 5) {
        return "周五";
      }
      if (n == 6) {
        return "周六";
      }
      if (n == 7) {
        return "周日";
      }
    },
    handleSubmit() {
      let dynamicData = [];
      const data = this.dataSource;
      const cols = this.listAuditInfo;
      data.forEach((record) => {
        cols.forEach((element) => {
          var filedName = element.filedName;
          if (record[filedName] != null && record[filedName] != "") {
            var weekIndex = parseInt(
              filedName.substring(filedName.indexOf("_") + 1)
            );
            var obj = {
              scheduleDate: moment(this.startDate_hide)
                .add(weekIndex - 1, "days")
                .format("YYYY-MM-DD"),
              banciId: filedName
                .substring(0, filedName.indexOf("_"))
                .replace("B", ""),
              accountId: record[filedName],
              bqId: record.bqId,
              deptId: record.deptId,
              bqName: record.bqName,
              deptName: record.deptName,
              zizhiId: record.zizhiId,
              zizhiName: record.zizhiName,
              baseId: this.baseId,
            };
            dynamicData.push(obj);
          }
        });
      });
      let jsonStr = JSON.stringify(dynamicData);
      this.$post("sdlBScheduleD/add", {
        jsonStr: jsonStr,
        startDate: this.startDate,
        endDate: this.endDate,
      })
        .then(() => {
          this.reset();
          this.$emit("success");
        })
        .catch(() => {
          this.loading = false;
        });
    },
    fetchBanci() {
      this.$get("sdlBScheduleD/banci", {}).then((r) => {
        //.info(r.data)
        // this.listAuditInfo = r.data;
        const cols = [];
        for (var i = 1; i < 8; i++) {
          let clo = [];
          r.data.forEach((element) => {
            cols.push({
              filedName: "B" + element.banciId + "_" + i,
              isShow: true,
            });
            clo.push({
              title: element.banciName,
              dataIndex: "B" + element.banciId + "_" + i,
              width: 250,
              scopedSlots: { customRender: "B" + element.banciId + "_" + i },
            });
          });
          this.columns.push({
            title: this.getWeekName(i),

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
      this.$get("sdlBScheduleD/zizhi", { baseId: this.baseId }).then((r) => {
        let data = r.data;
        data.forEach((element) => {
          let auditList = element.dynamicData;
            console.info(this.colsCustom.length)
            this.colsCustom.forEach((element2) => {
              element[element2.filedName + "_2"] = this.userData; //存储用户数据
            });
          //  console.info(auditList)
          if (auditList == null || auditList.length == 0) {
           
          } else {
            auditList.forEach((element2) => {
              var week = moment(element2.scheduleDate).day();
              if (week == 0) {
                week = week + 7;
              }
              element["B" + element2.banciId + "_" + week] = JSON.parse(
                element2.accountId
              );
            });
          }
        });
        console.info(data);
        this.dataSource = data;
      });
    },
    fetchDept() {
      this.$get("sdlBUser/dept", {
        pageSize: 10000,
        page: 1,
      }).then((r) => {
        let data = r.data;
        this.userData = data.rows;
        this.optionData = data.rows;
      });
    }
  },
  watch: {
    editVisiable() {
      if (this.editVisiable) {
        this.fetchBanci();
        this.fetchDept();
        setTimeout(() => {
          this.fetch();
        }, 200);
      }
    },
  },
  mounted() {
    window.onresize = () => {
      // this.resender()
    };
  },
};
</script>
