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
                    label="手术目录Id">
                        <a-input placeholder="请输入手术目录Id" v-decorator="['sugeryId', {rules: [{ required: true, message: '手术目录Id不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="科室">
                        <a-input placeholder="请输入科室" v-decorator="['deptNew', {rules: [{ required: true, message: '科室不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="编码">
                        <a-input placeholder="请输入编码" v-decorator="['code', {rules: [{ required: true, message: '编码不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="手术名称">
                        <a-input placeholder="请输入手术名称" v-decorator="['name', {rules: [{ required: true, message: '手术名称不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="手术等级">
                        <a-input placeholder="请输入手术等级" v-decorator="['level', {rules: [{ required: true, message: '手术等级不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="手术切口类别">
                        <a-input placeholder="请输入手术切口类别" v-decorator="['lb', {rules: [{ required: true, message: '手术切口类别不能为空' }] }]" />
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
        name: 'MdlBSurgeryinfoDAdd',
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
                mdlBSurgeryinfoD: {}
            }
        },
        methods: {
            reset () {
                this.loading = false
                this.mdlBSurgeryinfoD = {}
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
                    this.$post('mdlBSurgeryinfoD', {
                        ...this.mdlBSurgeryinfoD
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
                let values = this.form.getFieldsValue([                    'baseId'    ,                     'sugeryId'    ,                     'deptNew'    ,                     'code'    ,                     'name'    ,                     'level'    ,                     'lb'                   ])
                if (typeof values !== 'undefined') {
                    Object.keys(values).forEach(_key => { this.mdlBSurgeryinfoD[_key] = values[_key] })
                }
            }
        }
    }
</script>
