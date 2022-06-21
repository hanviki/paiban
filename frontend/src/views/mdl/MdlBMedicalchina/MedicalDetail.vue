<template>
  <div>
    <a-row>
      <!-- <a-col :span="8">
        <select-user-remote ref="xxbUser" @userChang="userChange1">
        </select-user-remote>
      </a-col> -->
      <a-col :span="5">
        <a-button class="editable-add-btn" v-if="isEdit" @click="handleAdd"> + 新增授权操作人员 </a-button>
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
            {{ d2.userAccountName + " " + d2.userAccount + " " + d2.birthday}}
            </a-select-option>
        </a-select>
        <div v-else>{{ record.userAccountName + " " + record.userAccount + " " + record.birthday}}</div>
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
// import SelectUserRemote from "../../common/SelectXxbUserRemote";
export default {
  name: "XxbBCheckD",
  // components: {
  //   SelectUserRemote,
  // },
  data() {
    return {
      dataSource: [],
      baseId: "",
      isEdit:true,
      selectUserDataSource: [],
      xxbBCheckD: {userAccount: null},
      // baseUserYggh: null,
      columns: [
        {
          title: "姓名",
          dataIndex: "userAccountName",
          scopedSlots: {
            customRender: "userAccountName",
          },
        },
        {
          title: "性别",
          dataIndex: "sexName",
          width: 60,
        },
        {
          title: "出生日期",
          dataIndex: "birthday",
          width: 100,
        },
        {
          title: "操作",
          dataIndex: "operation",
          scopedSlots: {
            customRender: "operation",
          },
          width: 80,
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
    // setBaseUserYggh(yggh){
    //   console.log('setBaseUserYggh:'+ yggh)
    //   this.baseUserYggh = yggh
    // },
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
      this.$get("mdlBMedicalchina/findCheckD", {
        baseId: this.baseId,
      }).then((r) => {
        if (r.data.data.success === 1) {
          this.dataSource = r.data.data.data;
          this.dataSource.forEach((item,index) => {
            let user = {
              userAccountName: item.userAccountName,
              userAccount: item.userAccount,
              sexname: item.sexname,
              birthday: item.birthday,
              tel: item.telephone
            }
            this.selectUserDataSource.push(user)
          });
        } else {
          this.$message.error("获取授权操作人员失败.");
        }
      });
    },
    onDelete(obj) {
      // const newData = [...this.dataSource];
      // if (newData.length > 1) {
        // const target = newData.filter((item) => obj.yggh === item.yggh)[0];
        // if (target) {
          if (obj.id == null) {
            const index = this.dataSource.indexOf(obj)
            const newList = this.dataSource.slice()
            newList.splice(index, 1)
            this.dataSource = newList
          } else {
            this.$get("mdlBMedicalchina/delCheckD", {
              id: obj.id,
            }).then((r) => {
              if (r.data.data.success === 1) {
                this.$message.success("删除授权操作人员成功.");
                this.dataSource = [];
                this.fetch();
              } else {
                this.$message.error("删除授权操作人员失败.");
              }
            });
          }
        // }
      // } else {
      //   this.$message.error(
      //     "删除授权操作人员失败,至少存在一条授权操作人员数据."
      //   );
      // }
    },
    handleUserSearch (keyword) {
      this.$get("sdlBUserMg/search", {
        userAccount: keyword,
        state: 0,
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
        msg = '不包含此外派人, '
      }
      this.$message.error(
        "增加授权操作人员失败, " + msg +
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
      // if(this.baseUserYggh !== null && this.baseUserYggh === value) {
      //   data = userDataSource.filter((item) => this.baseUserYggh === item.userAccount)[0];
      //   if(data) {
      //     this.showCunZaiError(data, 1)
      //     return
      //   }
      // }
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
       
        record.sexName = target.sexname;
        record.birthday = target.birthday;
      
       // record.telephone = target.tel;
      
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
      this.xxbBCheckD = {userAccount: null};
      // this.$refs.xxbUser.reset();
    },
    handleAdd() {
      let checkD = {
        userAccountName: "",
        userAccount: "",
      
        sexName: "",
        birthday: "",
       
        tel: "",
      }
      this.addDataSource(checkD);
      this.selectUserDataSource = []
      // if(this.xxbBCheckD.yggh !== null){
      //   const newData = [...this.dataSource];
      //   const target = newData.filter(
      //     (item) => this.xxbBCheckD.yggh === item.yggh
      //   )[0];
      //   if (target) {
      //     this.$message.error(
      //       "增加授权操作人员失败," +
      //         target.userAccountName +
      //         " " +
      //         target.userAccount +
      //         " " +
      //         target.yggh +
      //         "已存在."
      //     );
      //     return
      //   }
      //   if (this.baseId == null) {
      //     this.addDataSource(this.xxbBCheckD);
      //   } else {
      //     this.$get("xxbBCheck/addCheckD", {
      //       ...this.xxbBCheckD,
      //     }).then((r) => {
      //       if (r.data.data.success === 1) {
      //         let obj = r.data.data.data;
      //         if (obj.id !== null) {
      //           this.addDataSource(obj);
      //         } else {
      //           this.$message.error("增加授权操作人员失败.");
      //           this.cleanData();
      //         }
      //       } else {
      //         this.$message.error("增加授权操作人员失败或异常.");
      //         this.cleanData();
      //       }
      //     });
      //   }
      // }
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
