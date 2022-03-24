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
                    label="父Id">
                        <a-input placeholder="请输入父Id" v-decorator="['baseId', {rules: [{ required: true, message: '父Id不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="人员类型，0是核心1是其他">
                        <a-input placeholder="请输入人员类型，0是核心1是其他" v-decorator="['type', {rules: [{ required: true, message: '人员类型，0是核心1是其他不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="姓名">
                        <a-input placeholder="请输入姓名" v-decorator="['userAccountName', {rules: [{ required: true, message: '姓名不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="发薪号">
                        <a-input placeholder="请输入发薪号" v-decorator="['userAccount', {rules: [{ required: true, message: '发薪号不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="科室">
                        <a-input placeholder="请输入科室" v-decorator="['deptNew', {rules: [{ required: true, message: '科室不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="职称">
                        <a-input placeholder="请输入职称" v-decorator="['zhicheng', {rules: [{ required: true, message: '职称不能为空' }] }]" />
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
        name: 'MdlBMdtDAdd',
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
                mdlBMdtD: {}
            }
        },
        methods: {
            reset () {
                this.loading = false
                this.mdlBMdtD = {}
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
                    this.$post('mdlBMdtD', {
                        ...this.mdlBMdtD
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
                let values = this.form.getFieldsValue([                    'baseId'    ,                     'type'    ,                     'userAccountName'    ,                     'userAccount'    ,                     'deptNew'    ,                     'zhicheng'                   ])
                if (typeof values !== 'undefined') {
                    Object.keys(values).forEach(_key => { this.mdlBMdtD[_key] = values[_key] })
                }
            }
        }
    }
</script>
