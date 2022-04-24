<template>
  <div>
    <a-row type="flex" justify="center"> <h1>填 表 说 明</h1> </a-row>
    <a-row type="flex" justify="center" style="margin: 5px 28px;">
      <font size="3" style="color:rgba(0, 0, 0, 0.85);letter-spacing:1.5px;">
      　　一、申报范围：<br>
      　　申报的新技术新业务必须是在我院首次应用于临床，经过临床研究论证且安全性、有效性<br>
      确切的诊断和治疗技术。安全性、有效性不确切的医疗技术，不得申报。<br>
      　　新技术新项目中，如有涉及使用药品、医疗器械或具有相似属性的相关产品、制剂等的，<br>
      在药品、医疗器械或具有相似属性的相关产品、制剂等未经食品药品监督管理部门批准上市前，<br>
      不得申报开展临床应用，应按照临床研究的相关规定执行。<br>
      　　临床研究项目不在申报范围之列。 <br>
      　　二、“项目级别”分为以下四级：<br>
      　　一级：是指具有原创性创新、在国际上首先开展的，或领先于国际水平的，代表着本学科<br>
          近年和未来十年发展方向的技术、方法，该技术的开展能够大力促进和带动学科发展；<br>
      　　二级：指与国际先进水平同步，或紧随开展的项目和技术，或在国内首先开展，领先于国<br>内水平的项目和技术。<br>
      　　三级：是指与国内先进水平同步或紧随开展的项目和技术。<br>
      　　四级：是指在省内首先开展，或领先于省内水平的项目和技术，或与省内先进水平同步，<br>或紧随开展的项目和技术。<br>
      　　三、具有下列情形之一的，为限制类技术，申报获批后由医院组织向省卫建委备案：<br>
      　　（一）技术难度大、风险高，对医疗机构的服务能力、人员水平有较高专业要求，需要设<br>置限定条件的；<br>
      　　（二）需要消耗稀缺资源的； <br>
      　　（三）涉及重大伦理风险的；<br>
      　　（四）存在不合理临床应用，需要重点管理的。<br>
      　　四、国家限制临床应用的医疗技术（2015版）目录为：一、安全性、有效性确切，但是技术<br>
      难度大、风险高，对医疗机构的服务能力和人员技术水平有较高要求，需要限定条件的医疗<br>
      技术。如：造血干细胞（包括脐带血造血干细胞）移植治疗血液系统疾病技术，质子、重离子<br>
      加速器放射治疗技术，放射性粒子植入治疗技术（包括口腔颌面部恶性肿瘤放射性粒子植入治<br>
      疗技术），肿瘤深部热疗和全身热疗技术，肿瘤消融治疗技术，心室辅助装置应用技术，颅颌面<br>
      畸形颅面外科矫治术，口腔颌面部肿瘤颅颌联合根治术，人工智能辅助诊断、治疗技术等。二、<br>
      存在重大伦理风险或使用稀缺资源，需要严格管理的医疗技术。如：同种胰岛移植治疗糖尿病<br>
      技术，同种异体组织移植治疗技术（仅限于角膜、骨、软骨、皮肤移植治疗技术），性别重置技术<br>
      等。<br>
      　　五、“项目类型”分为以下四种类： <br>
      　　1.手术类； 2.治疗操作类； 3.检验检查类；4.其他类<br>
      　　六、主要操作人员范围限定是指可执行本项技术的操作人员的最低技术级别与技术类别。<br>
      填写时请在旁边“口”内以“√”表示。<br>
      　　七、医疗新技术项目申请的可行性报告包括以下内容（若表格不够，可另附页填写）：<br>
      　　1.项目主要内容； <br>
      　　2.项目目的、意义及创新之处；<br>
      　　3.项目依据：包括国内外研究及应用概况、采用的技术方案、可解决的主要问题、科室已具备<br>
      的技术和设备条件及准备情况、实施计划及相关效益分析等；<br>
      　　4.科室实施此新技术制定的相应质控措施、操作规程及相关人员的职责范围；<br>
      　　5.新技术开展的适应症、禁忌症、可能造成的不良后果和并发症以及相应防范措施；<br>
      　　6.安全性、有效性佐证材料；<br> 
      　　7.相应的病人知情同意书；<br>
      　　8.协作科室或单位的技术情况、承担责任及审批意见等。<br>
      　　八、申报一级、二级水平的新技术新项目还必须提交有资质部门出具的项目查新报告。查<br>
      新报告题目应与申报的新技术新项目一致，报告中必须写明国际、国内和省内开展情况。<br>
      　　九、申报的新技术新项目中如有涉及使用新的药品、医疗器械或具有相似属性的相关产品、<br>
      制剂等的，必须提交药品、医疗器械或具有相似属性的相关产品、制剂的“三证”，即：厂家生<br>
      产许可证、厂家经营许可证、国家食品药品监督管理部门批准上市的批文。<br>
    </font>
    </a-row>
    <a-affix :offsetBottom="0">
      <div class="formbutton">
      <a-button
          type="primary"
          @click="apply"
          :disabled="disBtn"
          :loading="loading"
          style="width: 10%; margin-right: 0.8rem"
          >确定{{count == 0 ? '': '('+ count+')'}}</a-button>
      </div>
    </a-affix>
  </div>
</template>

<script>
export default {
  name: "XxbBLconeShow",
  props: {},
  data() {
    return {
      count: 0,
      seconds: 3,
      disBtn: true,
      timer: null,
      loading: false,
    };
  },
  mounted() {},
  methods: {
    apply () {
      this.$emit("open");
    },
    show(){
      this.disBtn = false
      // clearInterval(this.timer)
      // this.timer = null
      // this.disBtn = true
      // const seconds = this.seconds;
      // if(!this.timer) {
      //   this.count = seconds
      //   this.timer = setInterval(() => {
      //     if(this.count > 0 && this.count <= seconds) {
      //       this.count --;
      //     } else {
      //       clearInterval(this.timer)
      //       this.timer = null
      //       this.disBtn = false
      //     }
      //   }, 1000);
      // }
    }
  },
};
</script>
<style lang="less" scoped>
.formbutton {
  width: 100%;
  padding: 10px 0px;
  background: #fff;
  text-align: center;
  border-top: 1px solid #e8e8e8;
}
</style>
