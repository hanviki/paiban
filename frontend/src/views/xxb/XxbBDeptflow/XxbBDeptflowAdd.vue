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
                    label="checkId">
                        <a-input placeholder="请输入checkId" v-decorator="['pid', {rules: [{ required: true, message: 'checkId不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="姓名">
                        <a-input placeholder="请输入姓名" v-decorator="['flowAccountName', {rules: [{ required: true, message: '姓名不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="人事编号">
                        <a-input placeholder="请输入人事编号" v-decorator="['flowAccount', {rules: [{ required: true, message: '人事编号不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="员工工号">
                        <a-input placeholder="请输入员工工号" v-decorator="['flowYggh', {rules: [{ required: true, message: '员工工号不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="用户科室">
                        <a-input placeholder="请输入用户科室" v-decorator="['flowdept', {rules: [{ required: true, message: '用户科室不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="流程内容">
                        <a-input placeholder="请输入流程内容" v-decorator="['flowcontent', {rules: [{ required: true, message: '流程内容不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="流程数">
                        <a-input placeholder="请输入流程数" v-decorator="['flownum', {rules: [{ required: true, message: '流程数不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="审核时间">
                        <a-date-picker showTime format='YYYY-MM-DD HH:mm:ss' v-decorator="[ 'flowDate', {rules: [{ required: true, message: '审核时间不能为空' }] }]" />
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
        name: 'XxbBDeptflowAdd',
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
                xxbBDeptflow: {}
            }
        },
        methods: {
            reset () {
                this.loading = false
                this.xxbBDeptflow = {}
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
                    this.$post('xxbBDeptflow', {
                        ...this.xxbBDeptflow
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
                let values = this.form.getFieldsValue([                    'pid'    ,                     'flowAccountName'    ,                     'flowAccount'    ,                     'flowYggh'    ,                     'flowdept'    ,                     'flowcontent'    ,                     'flownum'    ,                     'flowDate'                   ])
                if (typeof values !== 'undefined') {
                    Object.keys(values).forEach(_key => { this.xxbBDeptflow[_key] = values[_key] })
                }
            }
        }
    }
</script>
