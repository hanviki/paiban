<template>
  <a-modal
    v-model="userInfoVisiable"
    :centered="true"
    :keyboard="false"
    :width="900"
    ok-text="确认"
    cancel-text="取消"
    @ok="saveDept"
    @cancel="hideModal"
    title="移入人员科室"
  >
    <div style="height: 200px; overflow: auto">
      <xl-tree ref="xlTree" title="科室信息"> </xl-tree>
    </div>
    <a-form layout="horizontal">
      <a-row>
        <div>
          <a-col :md="8" :sm="24">
            <a-form-item label="姓名/人事编号" v-bind="formItemLayout">
              <a-input v-model="queryParams.userAccount" />
            </a-form-item>
          </a-col>
        </div>
        <span style="float: right; margin-top: 3px">
          <a-button type="primary" @click="search">查询</a-button>
        </span>
      </a-row>
    </a-form>
    <a-table
      ref="TableInfo"
      :columns="columns"
      :rowKey="(record) => record.userAccount"
      :dataSource="dataSource"
      :loading="loading"
      :rowSelection="{
        selectedRowKeys: selectedRowKeys,
        onChange: onSelectChange,
      }"
      :bordered="bordered"
      :scroll="{ x: 900 }"
    >
    </a-table>
  </a-modal>
</template>

<script>
const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 15, offset: 1 },
};
import XlTree from "./../../common/XlTree.vue";

export default {
  name: "UserInfo",
  data() {
    return {
      userAccount: "",
      dept_id: "",
      queryParams: {
        userAccount: "",
      },
      dataSource: [],
      selectedRowKeys: [],
      loading: false,
      bordered: true,
      formItemLayout,
    };
  },
  props: {
    userInfoVisiable: {
      require: true,
      default: false,
    },
  },
  watch: {
    userInfoVisiable() {
      if (this.userInfoVisiable) {
        this.$get("dept/own").then((r) => {
          this.$refs.xlTree.menuTreeData = r.data.rows.children;
          this.$refs.xlTree.allTreeKeys = r.data.ids;
        });
      }
    },
  },
  components: { XlTree },
  computed: {
    columns() {
      return [
          {
          title: "人事编码",
          dataIndex: "userAccount",
          width: 100,
        },
         {
          title: "姓名",
          dataIndex: "userAccountName",
          width: 100,
        },
        {
          title: "病区名称",
          dataIndex: "bqName",
          width: 100,
        },
        {
          title: "人员类型",
          dataIndex: "userType",
          width: 100,
          customRender: (text, row, index) => {
            switch (text) {
              case 0:
                return "一值班";
              case 1:
                return "二值班";
              case 2:
                return "三值班";
              case 3:
                return "住院医师老总班";
              case 4:
                return "急诊班值班";
              default:
                return text;
            }
          },
        },
        {
          title: "科室ID",
          dataIndex: "deptId",
          width: 100,
        },
        {
          title: "手机号",
          dataIndex: "telephone",
          width: 100,
        },
        {
          title: "科室名称",
          dataIndex: "deptName",
          width: 100,
        },
        {
          title: "现岗位职务",
          dataIndex: "positionName",
          width: 100,
        },
        {
          title: "拟聘岗位职务",
          dataIndex: "npPositionName",
          width: 100,
        },
        {
          title: "性别",
          dataIndex: "sexName",
          width: 100,
        },
        {
          title: "出生年月",
          dataIndex: "birthday",
          width: 100,
        },
        {
          title: "来校工作时间",
          dataIndex: "schoolDate",
          width: 100,
        },
        {
          title: "现专业技术岗位",
          dataIndex: "zyjsgw",
          width: 100,
        },
        {
          title: "现专业技术岗位（临床）",
          dataIndex: "zyjsgwLc",
          width: 100,
        },
        {
          title: "现从事专业及专长",
          dataIndex: "xcszyjzc",
          width: 100,
        },
        {
          title: "聘任时间",
          dataIndex: "appointedDate",
          width: 100,
        },
        {
          title: "本人排名",
          dataIndex: "patentRanknum",
          width: 100,
        },
        {
          title: "是否授权",
          dataIndex: "isAuthority",
          width: 100,
        },
        {
          title: "附件",
          dataIndex: "fileId",
          width: 100,
        },
        {
          title: "是否转让",
          dataIndex: "isZhuanrang",
          width: 100,
        },
        {
          title: "附件地址",
          dataIndex: "fileUrl",
          width: 100,
        },
        {
          title: "转让效益",
          dataIndex: "patentGood",
          width: 100,
        },
        {
          title: "审核意见",
          dataIndex: "auditSuggestion",
          width: 100,
        },
        {
          title: "聘任时间（临床）",
          dataIndex: "appointedDateLc",
          width: 100,
        },
        {
          title: "附件（临床）",
          dataIndex: "fileIdLc",
          width: 100,
        },
        {
          title: "附件地址（临床）",
          dataIndex: "fileUrlLc",
          width: 100,
        },
        {
          title: "员工组",
          dataIndex: "yuangongzu",
          width: 100,
        },
        {
          title: "现任岗位级别",
          dataIndex: "xrgwjb",
          width: 100,
        },
        {
          title: "现任岗位级别聘任时间",
          dataIndex: "xrgwjbprsj",
          width: 100,
        },
        {
          title: "担(兼)任党政职务",
          dataIndex: "djrdzzw",
          width: 100,
        },
        {
          title: "是否通过初级考核",
          dataIndex: "isChujikh",
          width: 100,
        },
        {
          title: "通过初级考核时间",
          dataIndex: "chujikhDate",
          width: 100,
        },
        {
          title: "是否通过中级考核",
          dataIndex: "isZhongjikh",
          width: 100,
        },
        {
          title: "通过中级考核时间",
          dataIndex: "zhongjikhDate",
          width: 100,
        },
        {
          title: "政治面貌",
          dataIndex: "politicalStatus",
          width: 100,
        },
        {
          title: "职员职级",
          dataIndex: "staffGrade",
          width: 100,
        },
        {
          title: "身份证号",
          dataIndex: "idCard",
          width: 100,
        },
        {
          title: "职位时间",
          dataIndex: "staffDate",
          width: 100,
        },
        {
          title: "照片id",
          dataIndex: "pictureId",
          width: 100,
        },
        {
          title: "照片url",
          dataIndex: "pictureUrl",
          width: 100,
        },
        {
          title: "岗前培训情况",
          dataIndex: "gqpxqk",
          width: 100,
        },
        {
          title: "规范化医师培训情况",
          dataIndex: "gfhyspxqk",
          width: 100,
        },
        {
          title: "中级水平能力测试情况",
          dataIndex: "zjspnlceqk",
          width: 100,
        },
      ];
    },
    show: {
      get: function () {
        return this.userInfoVisiable;
      },
      set: function () {},
    },
    sex() {
      switch (this.userInfoData.ssex) {
        case "0":
          return "男";
        case "1":
          return "女";
        case "2":
          return "保密";
        default:
          return this.userInfoData.ssex;
      }
    },
  },
  methods: {
    handleCancleClick() {
      this.$emit("close");
    },
    onSelectChange(selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys;
    },
    hideModal() {
      this.dept_id = "";
      this.userAccount = "";
      this.$emit("close");
    },
    saveDept() {
      if (!this.selectedRowKeys.length) {
        this.$message.warning("请选择移入用户");
        return;
      }
      if (this.selectedRowKeys.length > 1) {
        this.$message.warning("移入用户,请只能选择一条");
        return;
      }
      var depts = this.$refs.xlTree.getAuditKey();
      if (depts == "") {
        this.$message.warning("请选择移入科室");
        return;
      }
      if (depts.indexOf(",") > 0) {
        this.$message.warning("移入科室,只能选择一条");
        return;
      }
      this.$post("sdlBDeptchange", {
        userAccount: this.selectedRowKeys[0],
        deptId: depts,
        operationName: '移入'
      })
        .then(() => {
          this.dept_id = "";
          this.userAccount = "";
          this.$emit("close");
        })
        .catch(() => {
          this.loading = false;
        });
    },
    search() {
      this.fetch({
        ...this.queryParams,
      });
    },
    fetch(params = {}) {
      this.loading = true;

      this.$get("sdlBUser", {
        ...params,
      }).then((r) => {
        let data = r.data;
        this.loading = false;
        this.dataSource = data.rows;
      });
    },
  },
};
</script>
<style lang="less" scoped>
</style>



