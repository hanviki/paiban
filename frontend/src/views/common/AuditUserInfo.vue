<template>
  <a-drawer
    title="用户基本资料"
    placement="left"
    :closable="true"
    :mask="false"
    :visible="visibleUserInfo"
    @close="onClose"
  >
    <a-form-item
      v-bind="formItemLayout"
      label="姓名"
    >
      {{dcaBUser.userAccountName}}
    </a-form-item>
    <a-form-item
      v-bind="formItemLayout"
      label="人事编号"
    >
      {{dcaBUser.userAccount}}
    </a-form-item>
    <a-form-item
      v-bind="formItemLayout"
      label="所在科室"
    >
      {{dcaBUser.ks}}
    </a-form-item>
    <a-form-item
      v-bind="formItemLayout"
      label="联系电话"
    >
      {{dcaBUser.telephone}}
    </a-form-item>
    <a-form-item
      v-bind="formItemLayout"
      label="人事子范围"
    >
      {{dcaBUser.rszfw}}
    </a-form-item>
    <a-form-item
      v-bind="formItemLayout"
      label="医师类别"
    >
      {{dcaBUser.yishiLb}}
    </a-form-item>
    <a-form-item
      v-bind="formItemLayout"
      label="医技"
    >
      {{dcaBUser.patentGood}}
    </a-form-item>
    <a-form-item
      v-bind="formItemLayout"
      label="入职时间"
    >
      {{dcaBUser.schoolDate==null?"":moment(dcaBUser.schoolDate).format('YYYY-MM-DD')}}
    </a-form-item>
  </a-drawer>

</template>

<script>
const formItemLayout = {
  labelCol: { span: 12 },
  wrapperCol: { span: 12 }
}
import moment from 'moment';
export default {
  data () {
    return {
      dateFormat: 'YYYY-MM-DD',
      formItemLayout,
      loading: false,
      dcaBUser: {
        userAccountName: '',
        userAccount: '',
        deptName: '',
        ks: '',
        telephone: '',
        doctorDesc: '',
      },
      mess: ''
    }
  },
  props: {
    visibleUserInfo: {
      default: false
    },
    userAccount: {
      default: ''
    }
  },
  mounted () {

  },
  watch: {
    userAccount () {
      if (this.visibleUserInfo) {
        this.getUserInfo(this.userAccount)
      }
    }
  },
  methods: {
    moment,
    onClose () {
      //this.dcaBUser = {}
      //this.userAccount = ''
      this.$emit('close')
    },
    inputChange (value) {
      console.info(value)
      this.mess = value
    },
    sendMess () {
      if (this.mess != '') {
        this.$post('user/mess', {
          tel: this.dcaBUser.telephone,
          message: this.mess
        }).then((r) => {
          this.$message.success('发送成功')
        }
        )
      }
    },
    getUserInfo (userAccount) {
      if (userAccount != '') {
        this.$get('sdlBUser', {
          userAccount: userAccount
        }).then((r) => {
          let data = r.data
          if (data.rows.length > 0
          ) {
            this.dcaBUser = data.rows[0]
            this.mess = ''
          }
        }
        )
      }
    }
  }
}
</script>

<style>
</style>