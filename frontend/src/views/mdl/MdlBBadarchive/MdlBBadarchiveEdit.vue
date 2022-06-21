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
                        label="姓名">
                        <a-input placeholder="请输入姓名" v-decorator="['userAccountName', {rules: [{ required: true, message: '姓名不能为空' }] }]" />
                </a-form-item>
                </a-col>
                <a-col :sm="6">
                <a-form-item
                        v-bind="formItemLayout"
                        label="账号">
                        <a-input placeholder="请输入账号" v-decorator="['userAccount', {rules: [{ required: true, message: '账号不能为空' }] }]" />
                </a-form-item>
                </a-col>
                <a-col :sm="6">
                <a-form-item
                        v-bind="formItemLayout"
                        label="文件时间">
                        <a-date-picker v-decorator="[ 'fileDate', {rules: [{ required: true, message: '文件时间不能为空' }] }]" />
                </a-form-item>
                </a-col>
                <a-col :sm="6">
                <a-form-item
                        v-bind="formItemLayout"
                        label="备注">
                        <a-input placeholder="请输入备注" v-decorator="['note', {rules: [{ required: true, message: '备注不能为空' }] }]" />
                </a-form-item>
                </a-col>
                <a-col :sm="6">
                <a-form-item
                        v-bind="formItemLayout"
                        label="附件ID">
                        <a-input placeholder="请输入附件ID" v-decorator="['fileId', {rules: [{ required: true, message: '附件ID不能为空' }] }]" />
                </a-form-item>
                </a-col>
                <a-col :sm="6">
                <a-form-item
                        v-bind="formItemLayout"
                        label="附件地址">
                        <a-input placeholder="请输入附件地址" v-decorator="['fileUrl', {rules: [{ required: true, message: '附件地址不能为空' }] }]" />
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
        name: 'MdlBBadarchiveEdit',
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
            mdlBBadarchive: {}
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
            setFormValues ({...mdlBBadarchive}) {
                let fields = [  'userAccountName'    , 'userAccount'    , 'fileDate'    , 'note'    , 'fileId'    , 'fileUrl'          ]
                let fieldDates = [     'fileDate'             ]
                Object.keys(mdlBBadarchive).forEach((key) => {
                    if (fields.indexOf(key) !== -1) {
                    this.form.getFieldDecorator(key)
                    let obj = {}
                    if(fieldDates.indexOf(key)!==-1)
                    {
                        if(mdlBBadarchive[key]!==null && mdlBBadarchive[key]!=='') {
                            obj[key] = moment(mdlBBadarchive[key])
                            this.form.setFieldsValue(obj)
                        }
                        else
                        {
                            obj[key] =''
                        }
                    } else {
                        obj[key] = mdlBBadarchive[key]
                        this.form.setFieldsValue(obj)
                    }

                }
            })
                this.mdlBBadarchive. id                           = mdlBBadarchive. id                                      },
            handleSubmit () {
                this.form.validateFields((err, values) => {
                    if (!err) {
                    let mdlBBadarchive = this.form.getFieldsValue()
                    mdlBBadarchive. id                          = this.mdlBBadarchive.id
                    this.$put('mdlBBadarchive', {
                        ...mdlBBadarchive
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
