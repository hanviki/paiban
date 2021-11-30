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
          title: "人员类型",
          dataIndex: "rylx",
          width: 100,
        },
         {
          title: "人事编码",
          dataIndex: "userAccount",
          width: 120,
        },
        
         {
          title: "姓名",
          dataIndex: "userAccountName",
          width: 100,
        },
         {
          title: "员工工号",
          dataIndex: "yggh",
          width: 100,
        },
        {
          title: "资质类型",
          dataIndex: "userTypeName",
          width: 100,
        },
        {
          title: "科室编号",
          dataIndex: "deptId",
          width: 100,
        },
         {
          title: "科室名称",
          dataIndex: "deptName",
          width: 100,
        },
         {
          title: "病区名称",
          dataIndex: "bqName",
          width: 100,
        },
        {
          title: "华科人事编号",
          dataIndex: "ghHk",
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
          title: "员工组",
          dataIndex: "yuangongzu",
          width: 100,
        },
       {
          title: "员工子组",
          dataIndex: "zizu",
          width: 100,
        },
         {
          title: "人事子范围",
          dataIndex: "renshizifw",
          width: 100,
        },
       {
          title: "人事子范围分类",
          dataIndex: "renshizfenlei",
          width: 100,
        },
        //  {
        //   title: "身份证号",
        //   dataIndex: "idCard",
        //   width: 100,
        // },
         {
          title: "行政级别",
          dataIndex: "xingzhengjiebie",
          width: 100,
        },
        {
          title: "职称",
          dataIndex: "zhicheng",
          width: 100,
        },
        {
          title: "岗位等级",
          dataIndex: "xrgwjb",
          width: 100,
        },
        {
          title: "岗位等级开始日期",
          dataIndex: "xrgwjbprsj",
          width: 100,
        },
         {
          title: "临床职称",
          dataIndex: "zyjsgwLc",
          width: 100,
        },
         {
          title: "聘任时间（临床）",
          dataIndex: "appointedDateLc",
          width: 100,
        },
         {
          title: "临床证书编号",
          dataIndex: "bianhaoLc",
          width: 130,
        },
         {
          title: "教学职称",
          dataIndex: "zyjsgw",
          width: 100,
        },
         {
          title: "教学聘任时间",
          dataIndex: "appointedDate",
          width: 100,
        },
        {
          title: "教学证书编号",
          dataIndex: "bianhaoJx",
          width: 130,
        },
       
    
       
        {
          title: "内聘临床专业技术职务",
          dataIndex: "zyjsNp",
          width: 100,
        },
        {
          title: "内聘临床时间",
          dataIndex: "zyjsDateNp",
          width: 100,
        },
        {
          title: "内聘教学专业技术职务",
          dataIndex: "zyjsNpjx",
          width: 100,
        },
        {
          title: "内聘教学时间",
          dataIndex: "zyjsDateNpjx",
          width: 100,
        },
        {
          title: "学历",
          dataIndex: "edu",
          width: 100,
        },
        {
          title: "毕业学校",
          dataIndex: "eduSchool",
          width: 100,
        },
        {
          title: "国籍",
          dataIndex: "guoji",
          width: 100,
        },
        {
          title: "民族",
          dataIndex: "minzu",
          width: 100,
        },
        {
          title: "籍贯",
          dataIndex: "jiguan",
          width: 100,
        },
        {
          title: "政治面貌",
          dataIndex: "politicalStatus",
          width: 100,
        },
         {
          title: "入职时间",
          dataIndex: "schoolDate",
          width: 100,
        },
        {
          title: "参加工作时间",
          dataIndex: "workDate",
          width: 100,
        },
        {
          title: "医师类别",
          dataIndex: "yishiLb",
          width: 100,
        },
        {
          title: "医师级别",
          dataIndex: "yishiJb",
          width: 100,
        },
        {
          title: "医师执业范围",
          dataIndex: "yishiZhiyefanwei",
          width: 100,
        },
        {
          title: "医师资格证书编号",
          dataIndex: "yishiZgzsbianhao",
          width: 130,
        },
        {
          title: "医师执业证书编码",
          dataIndex: "yishiZiyebianhao",
          width: 130,
        }
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
          this.$emit("succ");
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



