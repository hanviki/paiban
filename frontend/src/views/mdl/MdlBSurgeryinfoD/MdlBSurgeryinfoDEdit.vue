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
                        label="父Id">
                        <a-input placeholder="请输入父Id" v-decorator="['baseId', {rules: [{ required: true, message: '父Id不能为空' }] }]" />
                </a-form-item>
                </a-col>
                <a-col :sm="6">
                <a-form-item
                        v-bind="formItemLayout"
                        label="手术目录Id">
                        <a-input placeholder="请输入手术目录Id" v-decorator="['sugeryId', {rules: [{ required: true, message: '手术目录Id不能为空' }] }]" />
                </a-form-item>
                </a-col>
                <a-col :sm="6">
                <a-form-item
                        v-bind="formItemLayout"
                        label="科室">
                        <a-input placeholder="请输入科室" v-decorator="['deptNew', {rules: [{ required: true, message: '科室不能为空' }] }]" />
                </a-form-item>
                </a-col>
                <a-col :sm="6">
                <a-form-item
                        v-bind="formItemLayout"
                        label="编码">
                        <a-input placeholder="请输入编码" v-decorator="['code', {rules: [{ required: true, message: '编码不能为空' }] }]" />
                </a-form-item>
                </a-col>
                <a-col :sm="6">
                <a-form-item
                        v-bind="formItemLayout"
                        label="手术名称">
                        <a-input placeholder="请输入手术名称" v-decorator="['name', {rules: [{ required: true, message: '手术名称不能为空' }] }]" />
                </a-form-item>
                </a-col>
                <a-col :sm="6">
                <a-form-item
                        v-bind="formItemLayout"
                        label="手术等级">
                        <a-input placeholder="请输入手术等级" v-decorator="['level', {rules: [{ required: true, message: '手术等级不能为空' }] }]" />
                </a-form-item>
                </a-col>
                <a-col :sm="6">
                <a-form-item
                        v-bind="formItemLayout"
                        label="手术切口类别">
                        <a-input placeholder="请输入手术切口类别" v-decorator="['lb', {rules: [{ required: true, message: '手术切口类别不能为空' }] }]" />
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
        name: 'MdlBSurgeryinfoDEdit',
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
            mdlBSurgeryinfoD: {}
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
            setFormValues ({...mdlBSurgeryinfoD}) {
                let fields = [  'baseId'    , 'sugeryId'    , 'deptNew'    , 'code'    , 'name'    , 'level'    , 'lb'          ]
                let fieldDates = [              ]
                Object.keys(mdlBSurgeryinfoD).forEach((key) => {
                    if (fields.indexOf(key) !== -1) {
                    this.form.getFieldDecorator(key)
                    let obj = {}
                    if(fieldDates.indexOf(key)!==-1)
                    {
                        if(mdlBSurgeryinfoD[key]!==null && mdlBSurgeryinfoD[key]!=='') {
                            obj[key] = moment(mdlBSurgeryinfoD[key])
                            this.form.setFieldsValue(obj)
                        }
                        else
                        {
                            obj[key] =''
                        }
                    } else {
                        obj[key] = mdlBSurgeryinfoD[key]
                        this.form.setFieldsValue(obj)
                    }

                }
            })
                this.mdlBSurgeryinfoD. id                             = mdlBSurgeryinfoD. id                                        },
            handleSubmit () {
                this.form.validateFields((err, values) => {
                    if (!err) {
                    let mdlBSurgeryinfoD = this.form.getFieldsValue()
                    mdlBSurgeryinfoD. id                            = this.mdlBSurgeryinfoD.id
                    this.$put('mdlBSurgeryinfoD', {
                        ...mdlBSurgeryinfoD
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
