<template>
    <a-drawer
            title="修改"
            :maskClosable="false"
            width=650
            placement="right"
            :closable="false"
            @close="onClose"
            :visible="editVisiable"
            style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;">
        <a-form :form="form">
                <a-form-item
                        v-bind="formItemLayout"
                        label="操作类型">
                        <a-input placeholder="请输入操作类型" v-decorator="['operationName', {rules: [{ required: true, message: '操作类型不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="原病区名称">
                        <a-input placeholder="请输入原病区名称" v-decorator="['bqName', {rules: [{ required: true, message: '原病区名称不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="当前病区名称">
                        <a-input placeholder="请输入当前病区名称" v-decorator="['bqNewName', {rules: [{ required: true, message: '当前病区名称不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="0是一直班">
                        <a-input placeholder="请输入0是一直班" v-decorator="['userType', {rules: [{ required: true, message: '0是一直班不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="科室ID">
                        <a-input placeholder="请输入科室ID" v-decorator="['deptId', {rules: [{ required: true, message: '科室ID不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="科室新ID">
                        <a-input placeholder="请输入科室新ID" v-decorator="['deptNewId', {rules: [{ required: true, message: '科室新ID不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="手机号">
                        <a-input placeholder="请输入手机号" v-decorator="['telephone', {rules: [{ required: true, message: '手机号不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="原科室名称">
                        <a-input placeholder="请输入原科室名称" v-decorator="['deptName', {rules: [{ required: true, message: '原科室名称不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="当前科室名称">
                        <a-input placeholder="请输入当前科室名称" v-decorator="['deptNewName', {rules: [{ required: true, message: '当前科室名称不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="现岗位职务">
                        <a-input placeholder="请输入现岗位职务" v-decorator="['positionName', {rules: [{ required: true, message: '现岗位职务不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="拟聘岗位职务">
                        <a-input placeholder="请输入拟聘岗位职务" v-decorator="['npPositionName', {rules: [{ required: true, message: '拟聘岗位职务不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="性别">
                        <a-input placeholder="请输入性别" v-decorator="['sexName', {rules: [{ required: true, message: '性别不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="出生年月">
                        <a-input placeholder="请输入出生年月" v-decorator="['birthday', {rules: [{ required: true, message: '出生年月不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="来校工作时间">
                        <a-input placeholder="请输入来校工作时间" v-decorator="['schoolDate', {rules: [{ required: true, message: '来校工作时间不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="现专业技术岗位">
                        <a-input placeholder="请输入现专业技术岗位" v-decorator="['zyjsgw', {rules: [{ required: true, message: '现专业技术岗位不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="现专业技术岗位（临床）">
                        <a-input placeholder="请输入现专业技术岗位（临床）" v-decorator="['zyjsgwLc', {rules: [{ required: true, message: '现专业技术岗位（临床）不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="现从事专业及专长">
                        <a-input placeholder="请输入现从事专业及专长" v-decorator="['xcszyjzc', {rules: [{ required: true, message: '现从事专业及专长不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="聘任时间">
                        <a-input placeholder="请输入聘任时间" v-decorator="['appointedDate', {rules: [{ required: true, message: '聘任时间不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="本人排名">
                        <a-input placeholder="请输入本人排名" v-decorator="['patentRanknum', {rules: [{ required: true, message: '本人排名不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="是否授权">
                        <a-input placeholder="请输入是否授权" v-decorator="['isAuthority', {rules: [{ required: true, message: '是否授权不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="附件">
                        <a-input placeholder="请输入附件" v-decorator="['fileId', {rules: [{ required: true, message: '附件不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="是否转让">
                        <a-input placeholder="请输入是否转让" v-decorator="['isZhuanrang', {rules: [{ required: true, message: '是否转让不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="附件地址">
                        <a-input placeholder="请输入附件地址" v-decorator="['fileUrl', {rules: [{ required: true, message: '附件地址不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="转让效益">
                        <a-input placeholder="请输入转让效益" v-decorator="['patentGood', {rules: [{ required: true, message: '转让效益不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="审核意见">
                        <a-input placeholder="请输入审核意见" v-decorator="['auditSuggestion', {rules: [{ required: true, message: '审核意见不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="聘任时间（临床）">
                        <a-input placeholder="请输入聘任时间（临床）" v-decorator="['appointedDateLc', {rules: [{ required: true, message: '聘任时间（临床）不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="附件（临床）">
                        <a-input placeholder="请输入附件（临床）" v-decorator="['fileIdLc', {rules: [{ required: true, message: '附件（临床）不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="附件地址（临床）">
                        <a-input placeholder="请输入附件地址（临床）" v-decorator="['fileUrlLc', {rules: [{ required: true, message: '附件地址（临床）不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="员工组">
                        <a-input placeholder="请输入员工组" v-decorator="['yuangongzu', {rules: [{ required: true, message: '员工组不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="现任岗位级别">
                        <a-input placeholder="请输入现任岗位级别" v-decorator="['xrgwjb', {rules: [{ required: true, message: '现任岗位级别不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="现任岗位级别聘任时间">
                        <a-input placeholder="请输入现任岗位级别聘任时间" v-decorator="['xrgwjbprsj', {rules: [{ required: true, message: '现任岗位级别聘任时间不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="担(兼)任党政职务">
                        <a-input placeholder="请输入担(兼)任党政职务" v-decorator="['djrdzzw', {rules: [{ required: true, message: '担(兼)任党政职务不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="是否通过初级考核">
                        <a-input placeholder="请输入是否通过初级考核" v-decorator="['isChujikh', {rules: [{ required: true, message: '是否通过初级考核不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="通过初级考核时间">
                        <a-input placeholder="请输入通过初级考核时间" v-decorator="['chujikhDate', {rules: [{ required: true, message: '通过初级考核时间不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="是否通过中级考核">
                        <a-input placeholder="请输入是否通过中级考核" v-decorator="['isZhongjikh', {rules: [{ required: true, message: '是否通过中级考核不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="通过中级考核时间">
                        <a-input placeholder="请输入通过中级考核时间" v-decorator="['zhongjikhDate', {rules: [{ required: true, message: '通过中级考核时间不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="政治面貌">
                        <a-input placeholder="请输入政治面貌" v-decorator="['politicalStatus', {rules: [{ required: true, message: '政治面貌不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="职员职级">
                        <a-input placeholder="请输入职员职级" v-decorator="['staffGrade', {rules: [{ required: true, message: '职员职级不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="身份证号">
                        <a-input placeholder="请输入身份证号" v-decorator="['idCard', {rules: [{ required: true, message: '身份证号不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="职位时间">
                        <a-input placeholder="请输入职位时间" v-decorator="['staffDate', {rules: [{ required: true, message: '职位时间不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="照片id">
                        <a-input placeholder="请输入照片id" v-decorator="['pictureId', {rules: [{ required: true, message: '照片id不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="照片url">
                        <a-input placeholder="请输入照片url" v-decorator="['pictureUrl', {rules: [{ required: true, message: '照片url不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="岗前培训情况">
                        <a-input placeholder="请输入岗前培训情况" v-decorator="['gqpxqk', {rules: [{ required: true, message: '岗前培训情况不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="规范化医师培训情况">
                        <a-input placeholder="请输入规范化医师培训情况" v-decorator="['gfhyspxqk', {rules: [{ required: true, message: '规范化医师培训情况不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="中级水平能力测试情况">
                        <a-input placeholder="请输入中级水平能力测试情况" v-decorator="['zjspnlceqk', {rules: [{ required: true, message: '中级水平能力测试情况不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="">
                        <a-input placeholder="请输入" v-decorator="['dcaYear', {rules: [{ required: true, message: '不能为空' }] }]" />
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
    import moment from 'moment'

    const formItemLayout = {
        labelCol: { span: 3 },
        wrapperCol: { span: 18 }
    }
    export default {
        name: 'SdlBDeptchangeEdit',
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
            sdlBDeptchange: {}
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
            setFormValues ({...sdlBDeptchange}) {
                let fields = [    'operationName'    , 'bqName'    , 'bqNewName'    , 'userType'    , 'deptId'    , 'deptNewId'    , 'telephone'    , 'deptName'    , 'deptNewName'    , 'positionName'    , 'npPositionName'    , 'sexName'    , 'birthday'    , 'schoolDate'    , 'zyjsgw'    , 'zyjsgwLc'    , 'xcszyjzc'    , 'appointedDate'    , 'patentRanknum'    , 'isAuthority'    , 'fileId'    , 'isZhuanrang'    , 'fileUrl'    , 'patentGood'              , 'auditSuggestion'     , 'appointedDateLc'    , 'fileIdLc'    , 'fileUrlLc'    , 'yuangongzu'    , 'xrgwjb'    , 'xrgwjbprsj'    , 'djrdzzw'    , 'isChujikh'    , 'chujikhDate'    , 'isZhongjikh'    , 'zhongjikhDate'    , 'politicalStatus'    , 'staffGrade'    , 'idCard'    , 'staffDate'    , 'pictureId'    , 'pictureUrl'    , 'gqpxqk'    , 'gfhyspxqk'    , 'zjspnlceqk'    , 'dcaYear'    ]
                let fieldDates = [                                                            ]
                Object.keys(sdlBDeptchange).forEach((key) => {
                    if (fields.indexOf(key) !== -1) {
                    this.form.getFieldDecorator(key)
                    let obj = {}
                    if(fieldDates.indexOf(key)!==-1)
                    {
                        if(sdlBDeptchange[key]!=='') {
                            obj[key] = moment(sdlBDeptchange[key])
                        }
                        else
                        {
                            obj[key] =''
                        }
                    } else {
                        obj[key] = sdlBDeptchange[key]
                    }
                    this.form.setFieldsValue(obj)
                }
            })
                this.sdlBDeptchange. id                                                                                                                         = sdlBDeptchange. id                                                                                                                                    },
            handleSubmit () {
                this.form.validateFields((err, values) => {
                    if (!err) {
                    let sdlBDeptchange = this.form.getFieldsValue()
                    sdlBDeptchange. id                                                                                                                        = this.sdlBDeptchange.id
                    this.$put('sdlBDeptchange', {
                        ...sdlBDeptchange
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
