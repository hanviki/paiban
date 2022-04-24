<template>
  <div>
    <a-row>
      <!-- <a-col :span="8">
        <select-user-remote ref="xxbUser" @userChang="userChange1">
        </select-user-remote>
      </a-col> -->
      <a-col :span="5">
        <a-button class="editable-add-btn" v-if="isEdit" @click="handleAdd"> + 新增项目主要人员 </a-button>
      </a-col>
    </a-row>
    <a-table
      bordered
      :rowKey="(record) => record.userAccount"
      :data-source="dataSource"
      :columns="columns"
      :pagination="false"
      size="small"
      :scroll="{ x: 900 }"
    >
      <template
        v-for="col in ['zhiwu','zhuany','zhuanc']"
        :slot="col"
        slot-scope="text, record, index"
      >
        <div :key="col">
          <a-input
            style="margin: -5px 0"
            v-if="isEdit"
            :maxLength="30"
            :value="text"
            @change="(e) => handleChange(e.target.value, record.userAccount, col)"
          />
          <div v-else>{{ text }}</div>
        </div>
      </template>
      <template
        slot='userAccountName'
        slot-scope="text, record, index"
      >
        <div key='userAccountName'>
        <a-select
            show-search
            :value="record.userAccount"
            v-if="isEdit"
            placeholder="请输入用户姓名"
            style="width: 100%"
            :default-active-first-option="false"
            :filter-option="false"
            :not-found-content="null"
            @search="handleUserSearch"
            @focus="handleUserFocus(record)"
            @change="e => handleUserChange(e, record)"
        >
            <a-icon
            slot="suffixIcon"
            type="search"
            ></a-icon>
            <a-select-option
            v-for="d2 in selectUserDataSource"
            :key="d2.userAccount"
            >
            {{ d2.userAccountName + " " + d2.userAccount + " " + d2.yggh}}
            </a-select-option>
        </a-select>
        <div v-else>{{ record.userAccountName + " " + record.userAccount + " " + record.yggh}}</div>
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
  name: "XxbBLcmanyD",
  data() {
    return {
      dataSource: [],
      baseId: "",
      isEdit:true,
      selectUserDataSource: [],
      xxbBCheckD: {userAccount: null},
      dataRs: {
        zcysgj: 0,
        zcyszj: 0,
        zcyscj: 0,
        zchsgj: 0,
        zchszj: 0,
        zchscj: 0,
        zcjsgj: 0,
        zcjszj: 0,
        zcjscj: 0,
        zcqtgj: 0,
        zcqtzj: 0,
        zcqtcj: 0,
        xlbs: 0,
        xlyjs: 0,
        xlbk: 0,
        xlqt: 0
      },
      // baseUserYggh: null,
      columns: [
        {
          title: "姓名",
          dataIndex: "userAccountName",
          scopedSlots: {
            customRender: "userAccountName",
          },
          fixed: "left",
          width: 280,
        },
        {
          title: "性别",
          dataIndex: "sexName",
          width: 55,
        },
        {
          title: "年龄",
          dataIndex: "age",
          width: 55,
        },
        {
          title: "学历",
          dataIndex: "edu",
          width: 70,
        },
        {
          title: "临床职称",
          dataIndex: "zhichenglc",
          width: 90,
        },
        {
          title: "职务",
          dataIndex: "zhiwu",
          scopedSlots: {
            customRender: "zhiwu",
          },
          width: 130,
        },
        {
          title: "专业",
          dataIndex: "zhuany",
          scopedSlots: {
            customRender: "zhuany",
          },
          width: 130,
        },
        {
          title: "专长",
          dataIndex: "zhuanc",
          scopedSlots: {
            customRender: "zhuanc",
          },
          width: 130,
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
      this.xxbBCheckD = {userAccount: null};
      this.dataSource = [];
      this.baseId = id;
      if (this.baseId !== null && this.baseId !== '') {
        this.fetch();
      }
    },
    reset(){
      this.xxbBCheckD = {userAccount: null};
      this.baseId = null
      this.dataSource = []
    },
    getFieldValues() {
      this.cleanData();
      return this.dataSource;
    },
    fetch() {
      this.selectUserDataSource = []
      this.$get("xxbBCheck/findCheckD", {
        baseId: this.baseId,
      }).then((r) => {
        if (r.data.data.success === 1) {
          this.dataSource = r.data.data.data;
          this.dataSource.forEach((item,index) => {
            let user = {
              userAccountName: item.userAccountName,
              userAccount: item.userAccount,
              yggh: item.yggh,
              sexname: item.sexname,
              birthday: item.birthday,
              age: item.age,
              edu: item.edu,
              zhicheng: item.zhicheng,
              zhichenglc: item.zhichenglc,
              zhiwu: item.zhiwu,
              deptNew: item.userdept,
              tel: item.telephone,
              renshizifw: item.renshizifw
            }
            this.selectUserDataSource.push(user)
          });
          this.jisuanFor()
        } else {
          this.$message.error("获取项目主要人员失败.");
        }
      });
    },
    onDelete(obj) {
      if (obj.id == null) {
        const index = this.dataSource.indexOf(obj)
        const newList = this.dataSource.slice()
        newList.splice(index, 1)
        this.dataSource = newList
        setTimeout(() => {
          this.jisuanFor()
        }, 100);
      } else {
        this.$get("xxbBCheck/delCheckD", {
          id: obj.id,
        }).then((r) => {
          if (r.data.data.success === 1) {
            this.$message.success("删除项目主要人员成功.");
            this.dataSource = [];
            this.fetch();
          } else {
            this.$message.error("删除项目主要人员失败.");
          }
        });
      }
    },
    handleUserSearch (keyword) {
      this.$get("sdlBUserMg/search", {
        userAccount: keyword,
        state: 0,
        rylx: '职工'
      }).then((r) => {
        this.selectUserDataSource = r.data;
      });
    },
    handleUserFocus(item){
      this.selectUserDataSource = []
    },
    showCunZaiError(data,type){
      let msg = ''
      if(type===1){
        msg = '不包含项目负责人, '
      }
      this.$message.error(
        "增加项目主要人员失败, " + msg +
          data.userAccountName +
          " " +
          data.userAccount +
          " " +
          data.yggh +
          "已存在."
      );
    },
    handleUserChange (value, record) {
      let data = null
      const userDataSource = [...this.selectUserDataSource];
      const newDataSource = [...this.dataSource];
      data = newDataSource.filter((item) => value === item.userAccount)[0];
      if(data) {
        this.showCunZaiError(data, 0)
        return
      }
      const target = userDataSource.filter(item => value === item.userAccount)[0]
      if (target) {
        record.userAccountName = target.userAccountName;
        record.userAccount = target.userAccount;
        record.yggh = target.yggh;
        record.sexName = target.sexname;
        record.birthday = target.birthday;
        record.age = target.age;
        record.edu = target.edu;
        record.zhicheng = target.zhicheng;
        record.zhichenglc = target.zhichenglc;
        record.zhiwu = target.zhiwu;
        record.userdept = target.deptNew;
        record.telephone = target.tel;
        record.zhuany = "";
        record.renshizifw = target.renshizifw

        setTimeout(() => {
          this.jisuanFor()
        }, 100);
      }
    },
    jisuanFor() {
      this.dataRs = {
        zcysgj: 0, zcyszj: 0, zcyscj: 0,
        zchsgj: 0, zchszj: 0, zchscj: 0,
        zcjsgj: 0, zcjszj: 0, zcjscj: 0,
        zcqtgj: 0, zcqtzj: 0, zcqtcj: 0,
        xlbs: 0, xlyjs: 0, xlbk: 0, xlqt: 0
      }
      
      this.dataSource.forEach((item,index) => {
        this.jisuan(item)
      });
      this.$emit("jisuan",this.dataRs)
    },
    jisuan (target) {
      //renshizifw 医师、护理、技术、其他
      //edu 博士、硕士、本科、其他
      //zhicheng 正高副高、中级、初级
      if (target.renshizifw=='医师'){
        if(target.zhicheng == '正高' || target.zhicheng == '副高' ) {
          this.dataRs.zcysgj += 1
        } else if(target.zhicheng == '中级' ) {
          this.dataRs.zcyszj += 1
        } else {
          this.dataRs.zcyscj += 1
        }
      } else if (target.renshizifw=='护理'){
        if(target.zhicheng == '正高' || target.zhicheng == '副高' ) {
          this.dataRs.zchsgj += 1 
        } else if(target.zhicheng == '中级' ) {
          this.dataRs.zchszj += 1 
        } else {
          this.dataRs.zchscj += 1 
        }
      } else if (target.renshizifw=='技术'){
        if(target.zhicheng == '正高' || target.zhicheng == '副高' ) {
          this.dataRs.zcjsgj += 1 
        } else if(target.zhicheng == '中级' ) {
          this.dataRs.zcjszj += 1 
        } else {
          this.dataRs.zcjscj += 1 
        }
      } else {
        if(target.zhicheng == '正高' || target.zhicheng == '副高' ) {
          this.dataRs.zcqtgj += 1 
        } else if(target.zhicheng == '中级' ) {
          this.dataRs.zcqtzj += 1 
        } else {
          this.dataRs.zcqtcj += 1 
        }
      }

      if (target.edu=='博士'){
        this.dataRs.xlbs += 1
      } else if (target.edu=='硕士'){
        this.dataRs.xlyjs += 1
      } else if (target.edu=='本科'){
        this.dataRs.xlbk += 1
      } else {
        this.dataRs.xlqt += 1
      }
    },
    handleChange(value, key, column) {
      const newData = [...this.dataSource];
      const target = newData.filter((item) => key === item.userAccount)[0];
      if (target) {
        target[column] = value;
        this.dataSource = newData;
      }
    },
    cleanData() {
      this.xxbBCheckD = {userAccount: null};
    },
    handleAdd() {
      let checkD = {
        userAccountName: "",
        userAccount: "",
        yggh: "",
        sexName: "",
        birthday: "",
        edu: "",
        zhicheng: "",
        zhichenglc: "",
        zhiwu: "",
        userdept: "",
        // telephone: target.tel,
        zhuany: "",
        age: "",
        tel: "",
        renshizifw: ''
      }
      this.addDataSource(checkD);
      this.selectUserDataSource = []
    },
    addDataSource(obj) {
      const { dataSource } = this;
      const newData = obj;
      this.dataSource = [newData, ...dataSource];
      this.jisuanFor()
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
