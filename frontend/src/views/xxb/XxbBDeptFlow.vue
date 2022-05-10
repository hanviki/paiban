<template>
  <div style="width:100%">
    <a-row type="flex" justify="center"> <h2>审核流程</h2> </a-row>
    <a-row type="flex" justify="center" style="margin: 5px 28px;">
      <a-col :span="23">
          <div style="border:1px solid #000;margin:5px 0px" v-for="item in dataSource" :key="item.id">
            <a-row type="flex" style="padding:5px 5px;background-color:#ccffff;" justify="center">
              <a-col :span=24>
              <b>{{
                item.flownum == 1 ? '本科室申报项目审核' : 
                item.flownum == 3 ? '医务处审核' :
                item.flownum == 2 && projectType != 2 ? '医务处审核' : '多科室联合申报审核'
               }}</b>
              </a-col>
            </a-row>
            <a-row type="flex" style="padding:8px 5px" justify="center">
              <a-col :span=3>
                姓　　名：
              </a-col>
              <a-col :span=6>
                {{ item.flowAccountName + " " + item.flowAccount}}
              </a-col>
              <a-col :span=3>
                科　　室：
              </a-col>
              <a-col :span=5>
                {{ item.flowdept}}
              </a-col>
              <a-col :span=3>
                审核状态：
              </a-col>
              <a-col :span=4>
                {{ item.resultstate == 0 ? '未审核' : item.resultstate == 1 ? '同意' : '拒绝'}}
              </a-col>
            </a-row>
            <a-row type="flex" style="padding:8px 20px 8px 5px" justify="center">
              <a-col :span=3>
                审核意见：
              </a-col>
              <a-col :span=21>
                {{ item.flowcontent }}
              </a-col>
            </a-row>
            <br>
          </div>
        <a-form :form="form">
          <a-form-item label="上次审核驳回意见" v-show="isFlowcontent && backflowcontent">
            　　{{backflowcontent}}
          </a-form-item>
          <a-form-item label="审核意见" v-show="isFlowcontent">
            <a-textarea
              :rows="3"
              :maxLength="100"
              placeholder="审核意见"
              v-decorator="[
                'flowcontent',
              
              ]"
            />
          </a-form-item>
        </a-form>
      </a-col>
    </a-row>
  </div>
</template>

<script>
export default {
  name: "XxbBDeptFlow",
  props: {},
  data() {
    return {
      dataSource: [],
      form: this.$form.createForm(this),
      baseId: "",
      projectType: 0,
      backflowcontent: '',
      isEdit: true,
      querystate: 0,
      isFlowcontent: true,
      deptFlow: null,
      loading: false,
    };
  },
  mounted() {},
  methods: {
    setFieldValues(id,projectType,querystate,isEdit) {
      this.isEdit = isEdit
      this.querystate = querystate
      this.backflowcontent = ''
      this.projectType = projectType
      this.dataSource = [];
      this.baseId = id;
      this.deptFlow = null
      if (this.baseId !== null && this.baseId !== '') {
        this.fetch();
      }
    },
    reset(){
      this.isEdit = false
      this.backflowcontent = ''
      this.isFlowcontent = false
      this.projectType = 0
      this.baseId = null
      this.dataSource = []
    },
    fetch() {
      this.dataSource = []
      this.$get("xxbBCheck/findDeptFlow", {
        baseId: this.baseId,state: this.querystate
      }).then((r) => {
        if (r.data.data.success === 1) {
          r.data.data.data.forEach((item,index) => {
            if (item.state == 1){
              this.dataSource.push(item)
            }
          });
          if (this.isEdit) {
            let obj = r.data.data.data.filter(item => 0 === item.state)[0]
            if(obj!==null && obj!==undefined){
              this.deptFlow = obj
              this.isFlowcontent = true
              this.backflowcontent = this.deptFlow.backflowcontent
              this.form.getFieldDecorator("flowcontent");
              this.form.setFieldsValue({
                flowcontent: this.deptFlow.flowcontent
              });
            } else {
              this.isFlowcontent = false
            }
            
            this.$emit("isBtnEdit",this.isFlowcontent)
          } else {
            this.isFlowcontent = false
          }
          this.$nextTick(() => {
            this.form.validateFields(['flowcontent'], { force: this.isFlowcontent })
          })
        } else {
          this.$message.error("获取科室流程失败.");
        }
      });
    },
    handleSubmit(type) {
      let btn = type == 1 ? '同意审核' : type == 2 ? '驳回审核' : '保存'
      if(type == 9){
        btn = '终止项目'
      }
      this.form.validateFields((err, values) => {
        if (!err) {
          let deptFlow = this.form.getFieldsValue();
          this.deptFlow.flowcontent = deptFlow.flowcontent
          this.deptFlow.createTime = null
          this.deptFlow.flowDate = null
          if (type == 1) {
            this.deptFlow.state = 1
          }
          if (type == 2) {
            this.deptFlow.state = 2
          }
           if (type == 9) {
            this.deptFlow.state = 9
          }
          this.$put("xxbBCheck/editDeptFlow", {
            ...this.deptFlow
          }).then((r) => {
            if (r.data.data.success === 1) {
              this.isUpdate = true;
              if (type == 1 || type == 2 || type == 9) {
                this.reset();
                this.$emit("success");
              } else {
                this.fetch()
                this.$message.success(btn + "数据成功.");
              }
            } else {
              this.$message.error(btn + "数据失败.");
            }
          })
          .catch(() => {
            this.loading = false;
          });
        }
      });
    },
  },
};
</script>
<style lang="less" scoped>
</style>
