<template>
    <a-drawer
            title="修改"
            :maskClosable="false"
            width="80%"
            placement="right"
            :closable="false"
            @close="onClose"
            :visible="editVisiable"
            style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;">
        <a-form :form="form">
                <a-col :sm="6">
                <a-form-item
                        v-bind="formItemLayout"
                        label="checkId">
                        <a-input placeholder="请输入checkId" v-decorator="['pid', {rules: [{ required: true, message: 'checkId不能为空' }] }]" />
                </a-form-item>
                </a-col>
                <a-col :sm="6">
                <a-form-item
                        v-bind="formItemLayout"
                        label="姓名">
                        <a-input placeholder="请输入姓名" v-decorator="['flowAccountName', {rules: [{ required: true, message: '姓名不能为空' }] }]" />
                </a-form-item>
                </a-col>
                <a-col :sm="6">
                <a-form-item
                        v-bind="formItemLayout"
                        label="人事编号">
                        <a-input placeholder="请输入人事编号" v-decorator="['flowAccount', {rules: [{ required: true, message: '人事编号不能为空' }] }]" />
                </a-form-item>
                </a-col>
                <a-col :sm="6">
                <a-form-item
                        v-bind="formItemLayout"
                        label="员工工号">
                        <a-input placeholder="请输入员工工号" v-decorator="['flowYggh', {rules: [{ required: true, message: '员工工号不能为空' }] }]" />
                </a-form-item>
                </a-col>
                <a-col :sm="6">
                <a-form-item
                        v-bind="formItemLayout"
                        label="用户科室">
                        <a-input placeholder="请输入用户科室" v-decorator="['flowdept', {rules: [{ required: true, message: '用户科室不能为空' }] }]" />
                </a-form-item>
                </a-col>
                <a-col :sm="6">
                <a-form-item
                        v-bind="formItemLayout"
                        label="流程内容">
                        <a-input placeholder="请输入流程内容" v-decorator="['flowcontent', {rules: [{ required: true, message: '流程内容不能为空' }] }]" />
                </a-form-item>
                </a-col>
                <a-col :sm="6">
                <a-form-item
                        v-bind="formItemLayout"
                        label="流程数">
                        <a-input placeholder="请输入流程数" v-decorator="['flownum', {rules: [{ required: true, message: '流程数不能为空' }] }]" />
                </a-form-item>
                </a-col>
                <a-col :sm="6">
                <a-form-item
                        v-bind="formItemLayout"
                        label="审核时间">
                        <a-date-picker showTime format='YYYY-MM-DD HH:mm:ss' v-decorator="[ 'flowDate', {rules: [{ required: true, message: '审核时间不能为空' }] }]" />
                </a-form-item>
                </a-col>
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
    import moment from 'moment'

    const formItemLayout = {
        labelCol: { span: 3 },
        wrapperCol: { span: 18 }
    }
    export default {
        name: 'XxbBDeptflowEdit',
        props: {
            editVisiable: {
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
                this.form.resetFields()
            },
            onClose () {
                this.reset()
                this.$emit('close')
            },
            setFormValues ({...xxbBDeptflow}) {
                let fields = [  'pid'    , 'flowAccountName'    , 'flowAccount'    , 'flowYggh'    , 'flowdept'    , 'flowcontent'    , 'flownum'      , 'flowDate'    ]
                let fieldDates = [            'flowDate'    ]
                Object.keys(xxbBDeptflow).forEach((key) => {
                    if (fields.indexOf(key) !== -1) {
                    this.form.getFieldDecorator(key)
                    let obj = {}
                    if(fieldDates.indexOf(key)!==-1)
                    {
                        if(xxbBDeptflow[key]!==null && xxbBDeptflow[key]!=='') {
                            obj[key] = moment(xxbBDeptflow[key])
                            this.form.setFieldsValue(obj)
                        }
                        else
                        {
                            obj[key] =''
                        }
                    } else {
                        obj[key] = xxbBDeptflow[key]
                        this.form.setFieldsValue(obj)
                    }

                }
            })
                this.xxbBDeptflow. id                       = xxbBDeptflow. id                                  },
            handleSubmit () {
                this.form.validateFields((err, values) => {
                    if (!err) {
                    let xxbBDeptflow = this.form.getFieldsValue()
                    xxbBDeptflow. id                      = this.xxbBDeptflow.id
                    this.$put('xxbBDeptflow', {
                        ...xxbBDeptflow
                    }).then(() => {
                        this.reset()
                        this.$emit('success')
                    }).catch(() => {
                        this.loading = false
                    })
                }
            })
            }
        }
    }
</script>
