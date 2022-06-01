<template>
  <a-upload
    name="file"
    accept=".xlsx"
    :fileList="fileList"
    :beforeUpload="beforeUpload"
  >
    <a-button :loading="uploading" style="width:160px;"><a-icon type="upload" /> {{btnTitle}} </a-button>
  </a-upload>
</template>

<script>
export default {
  name: 'file',
  data () {
    return {
      fileList: [],
      uploading: false
    }
  },
  props: {
    type: {
      default: 0,
      required:true
    },
    btnTitle: {
      default: ''
    }
  },
  methods: {
    beforeUpload (file) {
      var testmsg = file.name.substring(file.name.lastIndexOf('.') + 1)
      testmsg = testmsg.toLowerCase()
      let isExcel = testmsg === 'xlsx'
      // const isExcel = file.type === 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
      if (!(isExcel)) {
        this.$error({
          title: '只能上传.xlsx格式的Excel文档~'
        })
        return
      }
      const isLt2M = file.size / 1024 / 1024 < 3
      if (!isLt2M) {
        this.$error({
          title: '超3M限制，不允许上传~'
        })
        return
      }
      return (isExcel) && isLt2M && this.handleUpload(file)
    },
    handleUpload (file) {
      const formData = new FormData()
      formData.append('file', file)
      let url = '';
      if (this.type == 1) {
        url = 'xxbBResult/resultImport'
      } else if (this.type == 2) {
        url = 'xxbBResult/resultYwImport'
      } else {
        url = 'xxbBResult/resultYnImport'
      }
      this.uploading = true
      this.$upload(url, formData).then((r) => {
        if (r.data.data.success === 1) {
          this.$message.success('Excel导入成功.')
          this.$emit("backQuery")
        } else {
          this.$message.error(r.data.data.message)
        }
        this.uploading = false
      }).catch(() => {
        this.fileList = []
        this.$message.error('Excel导入失败.')
        this.uploading = false
      })
    },
  }
}
</script>
<style scoped>
</style>
