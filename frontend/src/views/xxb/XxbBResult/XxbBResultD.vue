<template>
  <div>
    <a-row>
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
        slot="displayIndex"
        slot-scope="text, record, index"
      >
        <div key='displayIndex'>
          <a-input-number
            v-if="isEdit"
            :value="text"
            :min="0"
            :step="1"
            @change="(e) => handleChange2(e, record)"
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
  name: "XxbBResultD",
  data() {
    return {
      dataSource: [],
      baseId: "",
      isEdit:true,
      selectUserDataSource: [],
      xxbBResultD: {userAccount: null},
      // baseUserYggh: null,
      columns: [
         {
          title: "序号",
          dataIndex: "displayIndex",
          scopedSlots: {
            customRender: "displayIndex",
          },
          fixed: "left",
          width: 100,
        },
        {
          title: "姓名",
          dataIndex: "userAccountName",
          scopedSlots: {
            customRender: "userAccountName",
          },
          fixed: "left",
          width: 250,
        },
        {
          title: "性别",
          dataIndex: "sexName",
          width: 60,
        },
        {
          title: "年龄",
          dataIndex: "age",
          width: 60,
        },
        {
          title: "学历",
          dataIndex: "edu",
          width: 80,
        },
        {
          title: "临床职称",
          dataIndex: "zhichenglc",
          width: 100,
        },
        {
          title: "手机号码",
          dataIndex: "telephone",
          width: 100,
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
      this.xxbBResultD = {userAccount: null};
      this.dataSource = [];
      this.baseId = id;
      if (this.baseId !== null && this.baseId !== '') {
        this.fetch();
      }
    },
    setCheckProjectData (checkData) {
      this.dataSource = []
      this.selectUserDataSource = []
      setTimeout(() => {
        checkData.forEach((item,index) => {
          // let data = this.dataSource.filter((item1) => item.userAccount === item1.userAccount);
          // if (data.length == 0) {
          let resultD = {
            userAccountName: item.userAccountName,
            userAccount: item.userAccount,
            yggh: item.yggh,
            sexName: item.sexName,
            birthday: item.birthday,
            edu: item.edu,
            zhicheng: item.zhicheng,
            zhichenglc: item.zhichenglc,
            userdept: item.userdept,
            age: item.age,
            telephone: item.telephone,
            renshizifw: item.renshizifw,
            displayIndex: item.displayIndex
          }
          this.addDataSource(resultD);
          // }
        });
        this.setSelectUserDataSource()
      }, 100);
    },
    reset(){
      this.xxbBResultD = {userAccount: null};
      this.baseId = null
      this.dataSource = []
    },
    getFieldValues() {
      this.cleanData();
      return this.dataSource;
    },
    setSelectUserDataSource () {
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
        deptNew: item.userdept,
        tel: item.telephone,
        renshizifw: item.renshizifw
      }
      this.selectUserDataSource.push(user)
    });
    },
    fetch() {
      this.selectUserDataSource = []
      this.$get("xxbBResult/findResultD", {
        baseId: this.baseId,
      }).then((r) => {
        if (r.data.data.success === 1) {
          this.dataSource = r.data.data.data;
          this.setSelectUserDataSource()
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
      } else {
        this.$get("xxbBResult/delResultD", {
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
        record.userdept = target.deptNew;
        record.telephone = target.tel;
        record.renshizifw = target.renshizifw
      }
    },
     handleChange2(value,record){
record.displayIndex= value
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
      this.xxbBResultD = {userAccount: null};
    },
    handleAdd() {
      let resultD = {
        userAccountName: "",
        userAccount: "",
        yggh: "",
        sexName: "",
        birthday: "",
        edu: "",
        zhicheng: "",
        zhichenglc: "",
        userdept: "",
        age: "",
        telephone: "",
        renshizifw: '',
        displayIndex: ''
      }
      this.addDataSource(resultD);
      this.selectUserDataSource = []
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
