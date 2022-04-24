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
                        <a-input placeholder="请输入姓名" v-decorator="['deptAccountName', {rules: [{ required: true, message: '姓名不能为空' }] }]" />
                </a-form-item>
                </a-col>
                <a-col :sm="6">
                <a-form-item
                        v-bind="formItemLayout"
                        label="人事编号">
                        <a-input placeholder="请输入人事编号" v-decorator="['deptAccount', {rules: [{ required: true, message: '人事编号不能为空' }] }]" />
                </a-form-item>
                </a-col>
                <a-col :sm="6">
                <a-form-item
                        v-bind="formItemLayout"
                        label="员工工号">
                        <a-input placeholder="请输入员工工号" v-decorator="['deptYggh', {rules: [{ required: true, message: '员工工号不能为空' }] }]" />
                </a-form-item>
                </a-col>
                <a-col :sm="6">
                <a-form-item
                        v-bind="formItemLayout"
                        label="科室名称">
                        <a-input placeholder="请输入科室名称" v-decorator="['deptName', {rules: [{ required: true, message: '科室名称不能为空' }] }]" />
                </a-form-item>
                </a-col>
                <a-col :sm="6">
                <a-form-item
                        v-bind="formItemLayout"
                        label="贡献度">
                        <a-input placeholder="请输入贡献度" v-decorator="['gxd', {rules: [{ required: true, message: '贡献度不能为空' }] }]" />
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
        name: 'XxbBProjdeptEdit',
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
            xxbBProjdept: {}
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
            setFormValues ({...xxbBProjdept}) {
                let fields = [  'pid'    , 'deptAccountName'    , 'deptAccount'    , 'deptYggh'    , 'deptName'    , 'gxd'      ]
                let fieldDates = [         ]
                Object.keys(xxbBProjdept).forEach((key) => {
                    if (fields.indexOf(key) !== -1) {
                    this.form.getFieldDecorator(key)
                    let obj = {}
                    if(fieldDates.indexOf(key)!==-1)
                    {
                        if(xxbBProjdept[key]!==null && xxbBProjdept[key]!=='') {
                            obj[key] = moment(xxbBProjdept[key])
                            this.form.setFieldsValue(obj)
                        }
                        else
                        {
                            obj[key] =''
                        }
                    } else {
                        obj[key] = xxbBProjdept[key]
                        this.form.setFieldsValue(obj)
                    }

                }
            })
                this.xxbBProjdept. id                   = xxbBProjdept. id                              },
            handleSubmit () {
                this.form.validateFields((err, values) => {
                    if (!err) {
                    let xxbBProjdept = this.form.getFieldsValue()
                    xxbBProjdept. id                  = this.xxbBProjdept.id
                    this.$put('xxbBProjdept', {
                        ...xxbBProjdept
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
