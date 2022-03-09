<template>
  <a-card :bordered="false" class="card-area myArea">
    <a-form :form="form" layout="vertical">
      <a-row>
        <a-col :span="6">
          <div style="padding: 0px 15px 15px 15px; background-color: white">
            <div style="border-top: 2px solid #00a694">
              <a-form-item label="性别">
                {{ user.sexName }}
              </a-form-item>
              <a-form-item label="工号">
                {{ user.yggh }}
              </a-form-item>
              <a-form-item label="出生年月">
                {{ user.birthday }}
              </a-form-item>
              <a-form-item label="籍贯">
                {{ user.jiguan }}
              </a-form-item>
              <a-form-item label="身份证号">
                {{ user.idCard }}
              </a-form-item>
              <a-form-item label="政治面貌">
                {{ user.politicalStatus }}
              </a-form-item>
              <a-form-item label="最后学位">
                {{ user.edu }}
              </a-form-item>
              <a-form-item label="来院日期">
                {{ user.schoolDate }}
              </a-form-item>
              <a-form-item label="专业技术职务">
                {{ user.xcszyjzc }}
              </a-form-item>
              <a-form-item label="职称类型">
                {{ user.zhicheng }}
              </a-form-item>
            </div>
          </div>
        </a-col>
        <a-col :span="18">
          <div>
            <a-row>
              <a-col :span="12">
                <div style="padding: 0px 15px 15px 15px">
                  <div style="border-top: 2px solid #00a694">
                    <a-form-item label="执业范围">
                      {{ user.yishiZhiyefanwei }}
                    </a-form-item>
                    <a-form-item label="医疗技术专长">
                      {{ user.xcszyjzc }}
                    </a-form-item>
                    <a-form-item label="医疗情况记载"> </a-form-item>
                  </div>
                </div>
              </a-col>
              <a-col :span="12">
                <div
                  style="padding: 0px 15px 15px 15px; background-color: white"
                >
                  <div style="border-top: 2px solid #00a694">
                    <a-form-item label="医师资格证号">
                      {{ user.yishiZgzsbianhao }}
                    </a-form-item>
                    <a-form-item label="医师执业证号">
                      {{ user.yishiZiyebianhao }}
                    </a-form-item>
                    <a-form-item label="所在科室">
                      {{ user.deptName }}
                    </a-form-item>
                  </div>
                </div>
              </a-col>
            </a-row>
          </div>
          <div style="padding: 0px 15px 15px 15px"> 
          <a-card
            style="
              border-top: 2px solid #00a694;
              border-left: 0px;
              border-right: 0px;
              border-bottom: 0px !important;
            "
          >
            <template slot="title">
              <a-icon type="home" style="color: #00a694;font-size:20px;"></a-icon
              ><span style="color: #00a694; font-weight: bold;font-size:20px;"> 资质权限</span>
            </template>
            
          </a-card>
          <a-card title="医疗主任资质">
          </a-card>
          <a-card title="带组资质">
          </a-card>
          <a-card title="质控员资质">
          </a-card>
          <a-card title="值班资质">
          </a-card>
          <a-card title="会诊资质">
          </a-card>
          <a-card title="处方资质">
          </a-card>
          <a-card title="抗菌药物权限资质">
          </a-card>
          <a-card title="毒麻精资质">
          </a-card>
          <a-card title="手术分级资质">
          </a-card>
           <a-card title="高风险操作资质">
          </a-card>
           <a-card
            style="
              margin-top: 10px;
              border-top: 2px solid #00a694;
              border-left: 0px;
              border-right: 0px;
              border-bottom: 0px !important;
            "
          >
            <template slot="title">
              <a-icon type="heat-map" style="color: #00a694;font-size:20px;"></a-icon
              ><span style="color: #00a694; font-weight: bold;font-size:20px;"> 学习、工作经历和职称评定</span>
            </template>
          </a-card>
           <a-card title="主要学习经历">
          </a-card>
          <a-card title="国内进修、学习经历">
          </a-card>
          <a-card title="国外留学、进修、学习经历">
          </a-card>
          <a-card title="主要工作经历">
          </a-card>
          <a-card title="职称评定">
          </a-card>
          </div>
        </a-col>
      </a-row>
    </a-form>
  </a-card>
</template>
<script>
import moment from "moment";
const formItemLayout = {
  labelCol: { span: 6 },
  wrapperCol: { span: 18 },
};
export default {
  name: "SdlBScheduleAdd",
  props: {
    addVisiable: {
      default: false,
    },
  },
  data() {
    return {
      loading: false,
      formItemLayout,
      dateFormat: "YYYY-MM",
      form: this.$form.createForm(this),
      sdlBSchedule: {},
      startDate: "",
      endDate: "",
      user: {},
    };
  },
  mounted() {
    this.fetch();
  },
  methods: {
    fetch() {
      this.$get("sdlBUser", {
        userAccount: this.$store.state.account.user.username,
      }).then((r) => {
        this.user = r.data.rows[0];
      });
    },
  },
};
</script>

<style lang="less">
.myArea{
.ant-col {
  .ant-form-item-label {
    background: #fafafa;
    border-top: 1px #ddd dotted;
    padding: 8px 2px;
    label {
      font-weight: bold !important;
    }
  }
}
.ant-form-item-control {
  padding: 0px 5px;
}
}
</style>
<style lang="less" scoped>
@import "../../../static/less/Common";
</style>