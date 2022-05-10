<template>
  <div style="width:100%" v-show="state > 0">
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
      </a-col>
    </a-row>
  </div>
</template>

<script>
export default {
  name: "XxbBDeptFlowLook",
  props: {},
  data() {
    return {
      dataSource: [],
      form: this.$form.createForm(this),
      baseId: "",
      projectType: 0,
      querystate: 0,
      state: 0,
      isFlowcontent: true,
      loading: false,
    };
  },
  mounted() {},
  methods: {
    setFieldValues(id,projectType,state,querystate) {
      this.querystate = querystate
      this.state = state
      this.projectType = projectType
      this.dataSource = [];
      this.baseId = id;
      if (this.baseId !== null && this.baseId !== '') {
        this.fetch();
      }
    },
    reset(){
      this.isFlowcontent = false
      this.projectType = 0
      this.querystate = 0
      this.state = 0
      this.baseId = null
      this.dataSource = []
    },
    fetch() {
      this.dataSource = []
      if(this.state > 0){
        this.$get("xxbBCheck/findDeptFlow", {
          baseId: this.baseId,state: this.querystate
        }).then((r) => {
          if (r.data.data.success === 1) {
            r.data.data.data.forEach((item,index) => {
              this.dataSource.push(item)
            });
          } else {
            this.$message.error("获取科室流程失败.");
          }
        });
      }
    },
  },
};
</script>
<style lang="less" scoped>
</style>
