<template>
  <div style="width:100%" v-show="state > 0">
    <a-row type="flex" justify="center"> <h2>审核流程</h2> </a-row>
    <a-row type="flex" justify="center" v-if="isView" style="margin: 5px 50px;">
      <table class="formtab">
        <tr>
          <td rowspan="6" style="width:20%;border: 1px solid #e8e8e8;">科室鉴定意见</td>
        </tr>
        <tr>
          <td style="width:80%;border-top: 1px solid #e8e8e8;border-right: 1px solid #e8e8e8;">该项目达到以下水平（在相应□中打“√”）</td>
        </tr>
        <tr>
          <td style="width:80%;border-right: 1px solid #e8e8e8;">
            一级水平：
            <a-checkbox v-model="gjlx" disabled>
              国际领先
            </a-checkbox>
          </td>
        </tr>
        <tr>
          <td style="width:80%;border-right: 1px solid #e8e8e8;">
            二级水平：
            <a-checkbox v-model="gjxj" disabled>
              国际先进
            </a-checkbox>
            <a-checkbox v-model="gnlx" disabled>
              国内领先
            </a-checkbox>
          </td>
        </tr>
        <tr>
          <td style="width:80%;border-right: 1px solid #e8e8e8;">
            三级水平：
            <a-checkbox v-model="gnxj" disabled>
              国内先进
            </a-checkbox>
          </td>
        </tr>
        <tr>
          <td style="width:80%;border-bottom: 1px solid #e8e8e8;border-right: 1px solid #e8e8e8;">
            四级水平：
            <a-checkbox v-model="snlx" disabled>
              省内领先
            </a-checkbox>
            <a-checkbox v-model="snxj" disabled>
              省内先进
            </a-checkbox>
          </td>
        </tr>
      </table>
    </a-row>
    <a-row type="flex" justify="center" style="margin: 5px 28px;">
      <a-col :span="23">
          <div style="border:1px solid #000;margin:5px 0px" v-for="item in dataSource" :key="item.id">
            <a-row type="flex" style="padding:5px 5px;background-color:#ccffff;" justify="center">
              <a-col :span=24>
              <b>{{
                item.flownum == 1 ? '本科室申报项目审核' : '医务处审核'
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
  name: "XxbBResultFLook",
  props: {},
  data() {
    return {
      dataSource: [],
      form: this.$form.createForm(this),
      baseId: "",
      querystate: 0,
      state: 0,
      gjlx: false,
      gjxj: false,
      gnlx: false,
      gnxj: false,
      snlx: false,
      snxj: false,
      isView: false,
      isFlowcontent: true,
      loading: false,
    };
  },
  mounted() {},
  methods: {
    setFieldValues(id,state,querystate,ngjlx,ngjxj,ngnlx,ngnxj,nsnlx,nsnxj) {
      debugger
      this.querystate = querystate
      this.state = state
      if (ngjlx != null) {
        this.isView = true
        this.gjlx = ngjlx == 0 ? false : true;
        this.gjxj = ngjxj == 0 ? false : true;
        this.gnlx = ngnlx == 0 ? false : true;
        this.gnxj = ngnxj == 0 ? false : true;
        this.snlx = nsnlx == 0 ? false : true;
        this.snxj = nsnxj == 0 ? false : true;
      }
      this.dataSource = [];
      this.baseId = id;
      if (this.baseId !== null && this.baseId !== '') {
        this.fetch();
      }
    },
    reset(){
      this.isFlowcontent = false
      this.querystate = 0
      this.state = 0
      this.isView = false
      this.gjlx = false;
      this.gjxj = false;
      this.gnlx = false;
      this.gnxj = false;
      this.snlx = false;
      this.snxj = false;
      this.baseId = null
      this.dataSource = []
    },
    fetch() {
      this.dataSource = []
      if(this.state > 0){
        this.$get("xxbBResult/findResultFlow", {
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
.formtab {
  width: 100%;
  padding: 5px;
}
.formtab {
  tr td {
    padding: 12px;
    word-wrap: break-word;    word-break: break-all;
  }
  .tdRight {
    text-align:center;
  }
  .tdTitle {
    border: 0px;
    text-align: center;
  }
}
</style>
