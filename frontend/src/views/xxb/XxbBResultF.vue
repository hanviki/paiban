<template>
  <div style="width:100%">
    <a-row type="flex" justify="center"> <h2>审核流程</h2> </a-row>、
    <a-row type="flex" justify="center" style="margin: 5px 50px;">
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
            <a-checkbox v-model="gjlx" :disabled="isCheck" @change="onChange">
              国际领先
            </a-checkbox>
          </td>
        </tr>
        <tr>
          <td style="width:80%;border-right: 1px solid #e8e8e8;">
            二级水平：
            <a-checkbox v-model="gjxj" :disabled="isCheck" @change="onChange">
              国际先进
            </a-checkbox>
            <a-checkbox v-model="gnlx" :disabled="isCheck" @change="onChange">
              国内领先
            </a-checkbox>
          </td>
        </tr>
        <tr>
          <td style="width:80%;border-right: 1px solid #e8e8e8;">
            三级水平：
            <a-checkbox v-model="gnxj" :disabled="isCheck" @change="onChange">
              国内先进
            </a-checkbox>
          </td>
        </tr>
        <tr>
          <td style="width:80%;border-bottom: 1px solid #e8e8e8;border-right: 1px solid #e8e8e8;">
            四级水平：
            <a-checkbox v-model="snlx" :disabled="isCheck" @change="onChange">
              省内领先
            </a-checkbox>
            <a-checkbox v-model="snxj" :disabled="isCheck" @change="onChange">
              省内先进
            </a-checkbox>
          </td>
        </tr>
      </table>
      <p style="color:red" v-show="isGx">项目达到以下水平未勾选!</p>
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
  name: "XxbBResultFlow",
  props: {},
  data() {
    return {
      dataSource: [],
      form: this.$form.createForm(this),
      baseId: "",
      backflowcontent: '',
      isEdit: true,
      querystate: 0,
      isFlowcontent: true,
      isGx: false,
      flownum: 0,
      gjlx: false,
      gjxj: false,
      gnlx: false,
      gnxj: false,
      snlx: false,
      snxj: false,
      isCheck: false,
      resultFlow: null,
      loading: false,
    };
  },
  mounted() {},
  methods: {
    setFieldValues(id,querystate,isEdit,ngjlx,ngjxj,ngnlx,ngnxj,nsnlx,nsnxj) {
      this.isEdit = isEdit
      this.querystate = querystate
      this.backflowcontent = ''
      this.dataSource = [];
      this.baseId = id;
      this.gjlx = ngjlx == 0 ? false : true;
      this.gjxj = ngjxj == 0 ? false : true;
      this.gnlx = ngnlx == 0 ? false : true;
      this.gnxj = ngnxj == 0 ? false : true;
      this.snlx = nsnlx == 0 ? false : true;
      this.snxj = nsnxj == 0 ? false : true;
      this.resultFlow = null
      if (this.baseId !== null && this.baseId !== '') {
        this.fetch();
      }
    },
    reset(){
      this.isEdit = false
      this.isGx = false
      this.backflowcontent = ''
      this.isFlowcontent = false
      this.flownum = 0
      this.baseId = null
      this.isCheck = false
      this.gjlx = false;
      this.gjxj = false;
      this.gnlx = false;
      this.gnxj = false;
      this.snlx = false;
      this.snxj = false;
      this.dataSource = []
    },
    fetch() {
      this.dataSource = []
      this.$get("xxbBResult/findResultFlow", {
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
              this.resultFlow = obj
              this.isFlowcontent = true
              this.backflowcontent = this.resultFlow.backflowcontent
              this.flownum = this.resultFlow.flownum
              if(this.flownum==1) {
                this.isCheck = false
                if (!this.gjlx && !this.gjxj && !this.gnlx && !this.gnxj && !this.snlx && !this.snxj) {
                  this.isGx = true
                } else {
                  this.isGx = false
                }
              } else {
                this.isCheck = true
                this.isGx = false
              }
              this.form.getFieldDecorator("flowcontent");
              this.form.setFieldsValue({
                flowcontent: this.resultFlow.flowcontent
              });
            } else {
              this.isGx = false
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
    onChange () {
      if (!this.gjlx && !this.gjxj && !this.gnlx && !this.gnxj && !this.snlx && !this.snxj) {
        this.isGx = true
      } else {
        this.isGx = false
      }
    },
    handleSubmit(type) {
      let btn = type == 1 ? '同意审核' : type == 2 ? '驳回审核' : '保存'
      if(type == 9){
        btn = '终止项目'
      }

      if (type != 2 && this.isGx) {
        this.$message.error("项目达到以下水平未勾选!");
        return
      }
      this.form.validateFields((err, values) => {
        if (!err) {
          let resultFlow = this.form.getFieldsValue();
          this.resultFlow.flowcontent = resultFlow.flowcontent
          this.resultFlow.createTime = null
          this.resultFlow.flowDate = null
          if (this.flownum == 1) {
            this.resultFlow.gjlx = this.gjlx ? 1 : 0;
            this.resultFlow.gjxj = this.gjxj ? 1 : 0;
            this.resultFlow.gnlx = this.gnlx ? 1 : 0;
            this.resultFlow.gnxj = this.gnxj ? 1 : 0;
            this.resultFlow.snlx = this.snlx ? 1 : 0;
            this.resultFlow.snxj = this.snxj ? 1 : 0;
          }
          if (type == 1) {
            this.resultFlow.state = 1
          }
          if (type == 2) {
            this.resultFlow.state = 2
          }
           if (type == 9) {
            this.resultFlow.state = 9
          }
          this.$put("xxbBResult/editResultFlow", {
            ...this.resultFlow
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
