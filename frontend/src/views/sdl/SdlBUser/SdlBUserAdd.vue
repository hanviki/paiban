<template>
    <a-drawer
            title="新增"
            :maskClosable="false"
            width=650
            placement="right"
            :closable="false"
            @close="onClose"
            :visible="addVisiable"
            style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;">
        <a-form :form="form">
                <a-form-item v-bind="formItemLayout"
                    label="病区名称">
                        <a-input placeholder="请输入病区名称" v-decorator="['bqName', {rules: [{ required: true, message: '病区名称不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="资质类型id">
                        <a-input placeholder="请输入资质类型id" v-decorator="['userType', {rules: [{ required: true, message: '资质类型id不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="资质类型">
                        <a-input placeholder="请输入资质类型" v-decorator="['userTypeName', {rules: [{ required: true, message: '资质类型不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="科室ID">
                        <a-input placeholder="请输入科室ID" v-decorator="['deptId', {rules: [{ required: true, message: '科室ID不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="手机号">
                        <a-input placeholder="请输入手机号" v-decorator="['telephone', {rules: [{ required: true, message: '手机号不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="科室名称">
                        <a-input placeholder="请输入科室名称" v-decorator="['deptName', {rules: [{ required: true, message: '科室名称不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="现岗位职务">
                        <a-input placeholder="请输入现岗位职务" v-decorator="['positionName', {rules: [{ required: true, message: '现岗位职务不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="拟聘岗位职务">
                        <a-input placeholder="请输入拟聘岗位职务" v-decorator="['npPositionName', {rules: [{ required: true, message: '拟聘岗位职务不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="人员类型">
                        <a-input placeholder="请输入人员类型" v-decorator="['rylx', {rules: [{ required: true, message: '人员类型不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="员工工号">
                        <a-input placeholder="请输入员工工号" v-decorator="['yggh', {rules: [{ required: true, message: '员工工号不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="华科人事编号">
                        <a-input placeholder="请输入华科人事编号" v-decorator="['ghHk', {rules: [{ required: true, message: '华科人事编号不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="性别">
                        <a-input placeholder="请输入性别" v-decorator="['sexName', {rules: [{ required: true, message: '性别不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="资质证号">
                        <a-input placeholder="请输入资质证号" v-decorator="['qualifyNo', {rules: [{ required: true, message: '资质证号不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="出生年月">
                        <a-input placeholder="请输入出生年月" v-decorator="['birthday', {rules: [{ required: true, message: '出生年月不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="来校工作时间">
                        <a-input placeholder="请输入来校工作时间" v-decorator="['schoolDate', {rules: [{ required: true, message: '来校工作时间不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="教学职称">
                        <a-input placeholder="请输入教学职称" v-decorator="['zyjsgw', {rules: [{ required: true, message: '教学职称不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="教学证书编号">
                        <a-input placeholder="请输入教学证书编号" v-decorator="['bianhaoJx', {rules: [{ required: true, message: '教学证书编号不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="临床职称">
                        <a-input placeholder="请输入临床职称" v-decorator="['zyjsgwLc', {rules: [{ required: true, message: '临床职称不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="现从事专业及专长">
                        <a-input placeholder="请输入现从事专业及专长" v-decorator="['xcszyjzc', {rules: [{ required: true, message: '现从事专业及专长不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="聘任时间">
                        <a-input placeholder="请输入聘任时间" v-decorator="['appointedDate', {rules: [{ required: true, message: '聘任时间不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="聘任时间（临床）">
                        <a-input placeholder="请输入聘任时间（临床）" v-decorator="['appointedDateLc', {rules: [{ required: true, message: '聘任时间（临床）不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="员工子组">
                        <a-input placeholder="请输入员工子组" v-decorator="['zizu', {rules: [{ required: true, message: '员工子组不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="员工组">
                        <a-input placeholder="请输入员工组" v-decorator="['yuangongzu', {rules: [{ required: true, message: '员工组不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="现任岗位级别">
                        <a-input placeholder="请输入现任岗位级别" v-decorator="['xrgwjb', {rules: [{ required: true, message: '现任岗位级别不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="现任岗位级别聘任时间">
                        <a-input placeholder="请输入现任岗位级别聘任时间" v-decorator="['xrgwjbprsj', {rules: [{ required: true, message: '现任岗位级别聘任时间不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="职称">
                        <a-input placeholder="请输入职称" v-decorator="['zhicheng', {rules: [{ required: true, message: '职称不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="内聘临床专业技术职务">
                        <a-input placeholder="请输入内聘临床专业技术职务" v-decorator="['zyjsNp', {rules: [{ required: true, message: '内聘临床专业技术职务不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="内聘临床时间">
                        <a-input placeholder="请输入内聘临床时间" v-decorator="['zyjsDateNp', {rules: [{ required: true, message: '内聘临床时间不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="内聘教学专业技术职务">
                        <a-input placeholder="请输入内聘教学专业技术职务" v-decorator="['zyjsNpjx', {rules: [{ required: true, message: '内聘教学专业技术职务不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="内聘教学时间">
                        <a-input placeholder="请输入内聘教学时间" v-decorator="['zyjsDateNpjx', {rules: [{ required: true, message: '内聘教学时间不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="学历">
                        <a-input placeholder="请输入学历" v-decorator="['edu', {rules: [{ required: true, message: '学历不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="毕业学校">
                        <a-input placeholder="请输入毕业学校" v-decorator="['eduSchool', {rules: [{ required: true, message: '毕业学校不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="国籍">
                        <a-input placeholder="请输入国籍" v-decorator="['guoji', {rules: [{ required: true, message: '国籍不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="民族">
                        <a-input placeholder="请输入民族" v-decorator="['minzu', {rules: [{ required: true, message: '民族不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="籍贯">
                        <a-input placeholder="请输入籍贯" v-decorator="['jiguan', {rules: [{ required: true, message: '籍贯不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="政治面貌">
                        <a-input placeholder="请输入政治面貌" v-decorator="['politicalStatus', {rules: [{ required: true, message: '政治面貌不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="临床证书编号">
                        <a-input placeholder="请输入临床证书编号" v-decorator="['bianhaoLc', {rules: [{ required: true, message: '临床证书编号不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="行政级别">
                        <a-input placeholder="请输入行政级别" v-decorator="['xingzhengjiebie', {rules: [{ required: true, message: '行政级别不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="身份证号">
                        <a-input placeholder="请输入身份证号" v-decorator="['idCard', {rules: [{ required: true, message: '身份证号不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="参加工作时间">
                        <a-input placeholder="请输入参加工作时间" v-decorator="['workDate', {rules: [{ required: true, message: '参加工作时间不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="医师类别">
                        <a-input placeholder="请输入医师类别" v-decorator="['yishiLb', {rules: [{ required: true, message: '医师类别不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="医师级别">
                        <a-input placeholder="请输入医师级别" v-decorator="['yishiJb', {rules: [{ required: true, message: '医师级别不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="医师执业范围">
                        <a-input placeholder="请输入医师执业范围" v-decorator="['yishiZhiyefanwei', {rules: [{ required: true, message: '医师执业范围不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="医师资格证书编号">
                        <a-input placeholder="请输入医师资格证书编号" v-decorator="['yishiZgzsbianhao', {rules: [{ required: true, message: '医师资格证书编号不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="医师执业证书编码">
                        <a-input placeholder="请输入医师执业证书编码" v-decorator="['yishiZiyebianhao', {rules: [{ required: true, message: '医师执业证书编码不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="人事子范围">
                        <a-input placeholder="请输入人事子范围" v-decorator="['renshizifw', {rules: [{ required: true, message: '人事子范围不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="人事子范围分类">
                        <a-input placeholder="请输入人事子范围分类" v-decorator="['renshizfenlei', {rules: [{ required: true, message: '人事子范围分类不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="是否授权">
                        <a-input placeholder="请输入是否授权" v-decorator="['isAuthority', {rules: [{ required: true, message: '是否授权不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="附件">
                        <a-input placeholder="请输入附件" v-decorator="['fileId', {rules: [{ required: true, message: '附件不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="附件地址">
                        <a-input placeholder="请输入附件地址" v-decorator="['fileUrl', {rules: [{ required: true, message: '附件地址不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="转让效益">
                        <a-input placeholder="请输入转让效益" v-decorator="['patentGood', {rules: [{ required: true, message: '转让效益不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="本人排名">
                        <a-input placeholder="请输入本人排名" v-decorator="['patentRanknum', {rules: [{ required: true, message: '本人排名不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="审核意见">
                        <a-input placeholder="请输入审核意见" v-decorator="['auditSuggestion', {rules: [{ required: true, message: '审核意见不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="附件（临床）">
                        <a-input placeholder="请输入附件（临床）" v-decorator="['fileIdLc', {rules: [{ required: true, message: '附件（临床）不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="附件地址（临床）">
                        <a-input placeholder="请输入附件地址（临床）" v-decorator="['fileUrlLc', {rules: [{ required: true, message: '附件地址（临床）不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="担(兼)任党政职务">
                        <a-input placeholder="请输入担(兼)任党政职务" v-decorator="['djrdzzw', {rules: [{ required: true, message: '担(兼)任党政职务不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="是否通过初级考核">
                        <a-input placeholder="请输入是否通过初级考核" v-decorator="['isChujikh', {rules: [{ required: true, message: '是否通过初级考核不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="通过初级考核时间">
                        <a-input placeholder="请输入通过初级考核时间" v-decorator="['chujikhDate', {rules: [{ required: true, message: '通过初级考核时间不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="是否通过中级考核">
                        <a-input placeholder="请输入是否通过中级考核" v-decorator="['isZhongjikh', {rules: [{ required: true, message: '是否通过中级考核不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="通过中级考核时间">
                        <a-input placeholder="请输入通过中级考核时间" v-decorator="['zhongjikhDate', {rules: [{ required: true, message: '通过中级考核时间不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="职位时间">
                        <a-input placeholder="请输入职位时间" v-decorator="['staffDate', {rules: [{ required: true, message: '职位时间不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="照片id">
                        <a-input placeholder="请输入照片id" v-decorator="['pictureId', {rules: [{ required: true, message: '照片id不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="照片url">
                        <a-input placeholder="请输入照片url" v-decorator="['pictureUrl', {rules: [{ required: true, message: '照片url不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="岗前培训情况">
                        <a-input placeholder="请输入岗前培训情况" v-decorator="['gqpxqk', {rules: [{ required: true, message: '岗前培训情况不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="规范化医师培训情况">
                        <a-input placeholder="请输入规范化医师培训情况" v-decorator="['gfhyspxqk', {rules: [{ required: true, message: '规范化医师培训情况不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="中级水平能力测试情况">
                        <a-input placeholder="请输入中级水平能力测试情况" v-decorator="['zjspnlceqk', {rules: [{ required: true, message: '中级水平能力测试情况不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="年度">
                        <a-input placeholder="请输入年度" v-decorator="['dcaYear', {rules: [{ required: true, message: '年度不能为空' }] }]" />
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
        name: 'SdlBUserAdd',
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
                sdlBUser: {}
            }
        },
        methods: {
            reset () {
                this.loading = false
                this.sdlBUser = {}
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
                    this.$post('sdlBUser', {
                        ...this.sdlBUser
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
                let values = this.form.getFieldsValue([                    'bqName'    ,                     'userType'    ,                     'userTypeName'    ,                     'deptId'    ,                     'telephone'    ,                     'deptName'    ,                     'positionName'    ,                     'npPositionName'    ,                     'rylx'    ,                     'yggh'    ,                     'ghHk'    ,                     'sexName'    ,                     'qualifyNo'    ,                     'birthday'    ,                     'schoolDate'    ,                     'zyjsgw'    ,                     'bianhaoJx'    ,                     'zyjsgwLc'    ,                     'xcszyjzc'    ,                     'appointedDate'    ,                     'appointedDateLc'    ,                     'zizu'    ,                     'yuangongzu'    ,                     'xrgwjb'    ,                     'xrgwjbprsj'    ,                     'zhicheng'    ,                     'zyjsNp'    ,                     'zyjsDateNp'    ,                     'zyjsNpjx'    ,                     'zyjsDateNpjx'    ,                     'edu'    ,                     'eduSchool'    ,                     'guoji'    ,                     'minzu'    ,                     'jiguan'    ,                     'politicalStatus'    ,                     'bianhaoLc'    ,                     'xingzhengjiebie'    ,                     'idCard'    ,                     'workDate'    ,                     'yishiLb'    ,                     'yishiJb'    ,                     'yishiZhiyefanwei'    ,                     'yishiZgzsbianhao'    ,                     'yishiZiyebianhao'    ,                     'renshizifw'    ,                     'renshizfenlei'    ,                     'isAuthority'    ,                     'fileId'    ,                     'fileUrl'    ,                     'patentGood'    ,                     'patentRanknum'    ,                     'auditSuggestion'    ,                     'fileIdLc'    ,                     'fileUrlLc'    ,                     'djrdzzw'    ,                     'isChujikh'    ,                     'chujikhDate'    ,                     'isZhongjikh'    ,                     'zhongjikhDate'    ,                     'staffDate'    ,                     'pictureId'    ,                     'pictureUrl'    ,                     'gqpxqk'    ,                     'gfhyspxqk'    ,                     'zjspnlceqk'    ,                     'dcaYear'                   ])
                if (typeof values !== 'undefined') {
                    Object.keys(values).forEach(_key => { this.sdlBUser[_key] = values[_key] })
                }
            }
        }
    }
</script>
