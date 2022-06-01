<template>
  <a-upload
    :accept="suffix"
    :disabled="!isEdit"
    :file-list="fileList"
    :multiple="false"
    :remove="handleImageRemove"
    :beforeUpload="beforeUpload"
  >
    <a-button size="small" v-if="isUpload"> <a-icon type="upload" /> {{btnTitle}} </a-button>
  </a-upload>
</template>

<script>
export default {
  name: 'file',
  data () {
    return {
      fileList: [],
      uploading: false,
      isUpload: true
    }
  },
  props: {
    baseId: {
      default: '',
      required:true
    },
    refTab: {
      default: 'all'
    },
    btnTitle: {
      default: '上传文件'
    },
    size: {
      default: 1
    },
    suffix: {
      default: '.pdf'
    },
    isEdit: {
      default: true
    },
    isCallback: {
      default: false
    }
  },
  watch: {
    baseId: function () {
      if (this.baseId !== null && this.baseId !== '') {
        var _this = this
        setTimeout(() => {
          _this.findFileList(_this.baseId)
        }, 100)
      } else {
        this.isUpload = true
        this.fileList = []
      }
    },
    immediate: true, // watch侦听操作内的函数会立刻被执行
    deep: true
  },
  mounted () {
    setTimeout(() => {
      if (this.baseId !== null && this.baseId !== '') {
        this.findFileList(this.baseId)
      } else {
        this.fileList = []
      }
    }, 100)
  },
  methods: {
    reset() {
      this.fileList = []
      this.isUpload = false
    },
    getFileCount(){
      return this.fileList.length;
    },
    beforeUpload (file) {
      let isType = true;
      // if (this.suffix === ".pdf") {
      //   isType = file.type === 'application/pdf'
      //   if (!(isType)) {
      //     this.$error({
      //       title: '只能上传pdf 格式~'
      //     })
      //     return
      //   }
      // }
      var testmsg = file.name.substring(file.name.lastIndexOf('.') + 1)
      testmsg = testmsg.toLowerCase()
      isType = '.' + testmsg === this.suffix
      if (!isType) {
            this.$error({
            title: '只能上传'+ this.suffix +' 格式~'
          })
          return
      }
      const isLt3M = file.size / 1024 < 3001
      if (!isLt3M) {
        this.$error({
          title: '文件超3MB限制，不允许上传~'
        })
        return
      }
      return isType && isLt3M && this.customRequest(file)
    },
    customRequest (file) {
      if (this.baseId == null || this.baseId == '') {
        this.$message.error('上传失败,请先保存数据.')
        return
      }
      const formData = new FormData()
      formData.append('file', file)
      formData.append('id', this.baseId)
      formData.append('refTab', this.refTab)
      formData.append('suffix', this.suffix)
      this.uploading = true
      let that = this
      this.$upload('comFile/uploadFile', formData).then((r) => {
        if (r.data.data.success === 1) {
          r.data.data.url =that.$baseUrl + r.data.data.url
          r.data.data.thumbUrl =that.$baseUrl + r.data.data.thumbUrl
          that.fileList.push(r.data.data)
          this.uploading = false
          this.$message.success('上传成功.')
          this.lableErr = ''
          if (this.isCallback === true) {
            this.$emit("fileData",that.fileList)
          }
          this.setBtnVisible()
        } else {
          this.$message.error(r.data.data.message)
        }
      }).catch(() => {
        this.uploading = false
        this.$message.error('上传失败.')
      })
    },
    handleImageRemove (file) {
      let that = this
      this.$confirm({
        title: '确定删除所选中的文件?',
        content: '当您点击确定按钮后，这些文件将会被彻底删除',
        centered: true,
        onOk () {
          let formData = {
            id: file.uid,
            refTab: that.refTab
          }
          that.$post('comFile/deleteFile', formData).then((r) => {
            that.uploading = false
            if (r.data.data.success === 1) {
              that.$message.success('删除文件成功')
              const index = that.fileList.indexOf(file)
              const newFileList = that.fileList.slice()
              newFileList.splice(index, 1)
              that.fileList = newFileList
              if (that.isCallback === true) {
                that.$emit("fileData",that.fileList)
              }
              that.setBtnVisible()
            } else {
              that.$message.error(r.data.data.message)
            }
          }).catch(() => {
            that.$message.error('删除文件失败.')
          })
        },
        onCancel () {
        }
      })
    },
    handleCancel () {
      this.previewVisible = false
    },
    setBtnVisible () {
      if (!this.isEdit) {
        this.isUpload = false
      } else {
        if (this.fileList.length === this.size) {
          this.isUpload = false
        } else {
          this.isUpload = true
        }
      }
    },
    findFileList (id) {
      let formData = {}
      formData.id = id
      formData.refTab = this.refTab
      this.fileList = []
      this.$post('comFile/fileList', {
        ...formData
      }).then((r) => {
        for (let data of r.data.data) {
          console.info(this.$baseUrl)
          data.url = this.$baseUrl + data.url
          data.thumbUrl = this.$baseUrl + data.thumbUrl
          this.fileList.push(data)
        }
        if (this.isCallback === true) {
          this.$emit("fileData",this.fileList)
        }
        this.setBtnVisible()
      })
    }
  }
}
</script>
<style scoped>
</style>
