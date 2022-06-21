<template>
    <a-drawer
            title="新增"
            :maskClosable="false"
            width="80%"
            placement="right"
            :closable="false"
            @close="onClose"
            :visible="addVisiable"
            style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;">
        <a-form :form="form">
                <a-form-item v-bind="formItemLayout"
                    label="姓名">
                        <a-input placeholder="请输入姓名" v-decorator="['userAccountName', {rules: [{ required: true, message: '姓名不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="账号">
                        <a-input placeholder="请输入账号" v-decorator="['userAccount', {rules: [{ required: true, message: '账号不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="文件时间">
                        <a-date-picker v-decorator="[ 'fileDate', {rules: [{ required: true, message: '文件时间不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="备注">
                        <a-input placeholder="请输入备注" v-decorator="['note', {rules: [{ required: true, message: '备注不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="附件ID">
                        <a-input placeholder="请输入附件ID" v-decorator="['fileId', {rules: [{ required: true, message: '附件ID不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="附件地址">
                        <a-input placeholder="请输入附件地址" v-decorator="['fileUrl', {rules: [{ required: true, message: '附件地址不能为空' }] }]" />
                </a-form-item>
        </a-form>
        <div class="drawer-bootom-button">
            <a-popconfirm title="确定放弃编辑？" @confirm="onClose" okText="确定" cancelText="取消">
                <a-button style="margin-right: .8rem">取消</a-button>
            </a-popconfirm>
            <a-button @click="handleSubmit" type="primary" :loading="loading">提交</a-button>
        </div>
    </a-drawer>
</template>
<script>
    const formItemLayout = {
        labelCol: { span: 3 },
        wrapperCol: { span: 18 }
    }
    export default {
        name: 'MdlBBadarchiveAdd',
        props: {
            addVisiable: {
                default: false
            }
        },
        data () {
            return {
                loading: false,
                formItemLayout,
                form: this.$form.createForm(this),
                mdlBBadarchive: {}
            }
        },
        methods: {
            reset () {
                this.loading = false
                this.mdlBBadarchive = {}
                this.form.resetFields()
            },
            onClose () {
                this.reset()
                this.$emit('close')
            },
            handleSubmit () {
                this.form.validateFields((err, values) => {
                    if (!err) {
                    this.setFields()
                    this.$post('mdlBBadarchive', {
                        ...this.mdlBBadarchive
                    }).then(() => {
                        this.reset()
                        this.$emit('success')
                    }).catch(() => {
                        this.loading = false
                    })
                }
            })
            },
            setFields () {
                let values = this.form.getFieldsValue([                    'userAccountName'    ,                     'userAccount'    ,                     'fileDate'    ,                     'note'    ,                     'fileId'    ,                     'fileUrl'                   ])
                if (typeof values !== 'undefined') {
                    Object.keys(values).forEach(_key => { this.mdlBBadarchive[_key] = values[_key] })
                }
            }
        }
    }
</script>
