package cc.mrbird.febs.sdl.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.rfc.CustomUser;
import cc.mrbird.febs.sdl.entity.CustomDept;
import cc.mrbird.febs.sdl.entity.SdlBUser;
import cc.mrbird.febs.sdl.dao.SdlBUserMapper;
import cc.mrbird.febs.sdl.service.ISdlBControlService;
import cc.mrbird.febs.sdl.service.ISdlBUserService;
import cc.mrbird.febs.system.dao.DeptMapper;
import cc.mrbird.febs.system.domain.Dept;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.time.LocalDate;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-10-18
 */
@Slf4j
@Service("ISdlBUserService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class SdlBUserServiceImpl extends ServiceImpl<SdlBUserMapper, SdlBUser> implements ISdlBUserService {

    @Autowired
    private DeptMapper deptMapper;

    @Override
    public IPage<SdlBUser> findSdlBUsers(QueryRequest request, SdlBUser sdlBUser) {
        try {
            List<String> deptIds = this.deptMapper.getListIds(sdlBUser.getDeptId());
            LambdaQueryWrapper<SdlBUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SdlBUser::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(sdlBUser.getUserAccount())) {
                queryWrapper.and(wrap -> wrap.eq(SdlBUser::getUserAccount, sdlBUser.getUserAccount()).or()
                        .like(SdlBUser::getUserAccountName, sdlBUser.getUserAccount()));

            }
            if (sdlBUser.getState() != null) {
                queryWrapper.eq(SdlBUser::getState, sdlBUser.getState());
            }
            if (StringUtils.isNotBlank(sdlBUser.getDeptId())) {
                queryWrapper.in(SdlBUser::getDeptId, deptIds);
                // queryWrapper.apply("sdl_b_user.dept_id in (select t_dept.dept_id from t_dept where t_dept.DEPT_ID='"+sdlBUser.getDeptId()+"' or t_dept.PARENT_ID='"+sdlBUser.getDeptId()+"' )");
            }
            queryWrapper.ne(SdlBUser::getState, 0);//只显示2或者3的


            Page<SdlBUser> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;

        }
    }


    @Override
    public List<cc.mrbird.febs.sdl.entity.CustomUser> findSdlBUsers(SdlBUser sdlBUser) {
        try {
            List<String> deptIds = this.deptMapper.getListIds(sdlBUser.getDeptId());


            return  this.baseMapper.findSdlBUserByDeptId(deptIds);

        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;

        }
    }
    @Override
    public List<SdlBUser> findSdlBUsers_search( SdlBUser sdlBUser) {
        try {

            LambdaQueryWrapper<SdlBUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SdlBUser::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(sdlBUser.getUserAccount())) {
                queryWrapper.and(wrap -> wrap.eq(SdlBUser::getUserAccount, sdlBUser.getUserAccount()).or()
                        .like(SdlBUser::getUserAccountName, sdlBUser.getUserAccount()));

            }
            if (sdlBUser.getState() != null) {

            }
            else{
                queryWrapper.ne(SdlBUser::getState, 0);//只显示2或者3的
            }
           // queryWrapper.eq(SdlBUser::getRylx,"职工");
            queryWrapper.apply("sdl_b_user.yuangongzu!='规培' and sdl_b_user.yuangongzu!='博士后' and (sdl_b_user.renshizifw ='医师' or  (sdl_b_user.renshizifw='技术' and sdl_b_user.renshizfenlei='卫生'))");

            IPage<SdlBUser> page = new Page<>();
            page.setPages(1L);
            page.setSize(20);
            return  this.baseMapper.selectPage(page,queryWrapper).getRecords();

        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<SdlBUser> findSdlBUsersYwc(QueryRequest request, SdlBUser sdlBUser) {
        try {
            List<String> deptIds = this.deptMapper.getListIds(sdlBUser.getDeptId());
            LambdaQueryWrapper<SdlBUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SdlBUser::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(sdlBUser.getUserAccount())) {
                queryWrapper.and(wrap -> wrap.eq(SdlBUser::getUserAccount, sdlBUser.getUserAccount()).or()
                        .like(SdlBUser::getUserAccountName, sdlBUser.getUserAccount()));

            }

            if (StringUtils.isNotBlank(sdlBUser.getDeptId())) {
                queryWrapper.in(SdlBUser::getDeptId, deptIds);
                // queryWrapper.apply("sdl_b_user.dept_id in (select t_dept.dept_id from t_dept where t_dept.DEPT_ID='"+sdlBUser.getDeptId()+"' or t_dept.PARENT_ID='"+sdlBUser.getDeptId()+"' )");
            }
            if (StringUtils.isNotBlank(sdlBUser.getSchoolDateFrom())) {
                queryWrapper
                        .ge(SdlBUser::getSchoolDate, sdlBUser.getSchoolDateFrom())
                ;
            }
            if (StringUtils.isNotBlank(sdlBUser.getYishiZgzsbianhao())) {
                queryWrapper.apply("(sdl_b_user.yishi_zgzsbianhao is null or sdl_b_user.yishi_zgzsbianhao='')");
            }
            if (StringUtils.isNotBlank(sdlBUser.getYishiZiyebianhao())) {
                queryWrapper.apply("(sdl_b_user.yishi_ziyebianhao is null or sdl_b_user.yishi_ziyebianhao='')");
            }
            if (StringUtils.isNotBlank(sdlBUser.getSchoolDateTo())) {
                queryWrapper.le(SdlBUser::getSchoolDate, sdlBUser.getSchoolDateTo());
            }
            queryWrapper.apply("sdl_b_user.yuangongzu!='规培' and sdl_b_user.yuangongzu!='博士后' and (sdl_b_user.renshizifw ='医师' or  (sdl_b_user.renshizifw='技术' and sdl_b_user.renshizfenlei='卫生'))");
            if (StringUtils.isNotBlank(sdlBUser.getAuditMan())) {
                List<String> states = Arrays.asList(sdlBUser.getAuditMan().replace("[", "").replace("]", "").replace("\"", "").split(","));
                queryWrapper.in(SdlBUser::getState, states);
            }
            queryWrapper.ne(SdlBUser::getState, 0);//只显示2或者3的


            Page<SdlBUser> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    /**
     * 医师执业资质
     * @param request
     * @param sdlBUser
     * @return
     */
    @Override
    public IPage<SdlBUser> findSdlBUsersYwc2(QueryRequest request, SdlBUser sdlBUser) {
        try {
           // List<String> deptIds = this.deptMapper.getListIds(sdlBUser.getDeptId());
            LambdaQueryWrapper<SdlBUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SdlBUser::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(sdlBUser.getUserAccount())) {
                queryWrapper.and(wrap -> wrap.eq(SdlBUser::getUserAccount, sdlBUser.getUserAccount()).or()
                        .like(SdlBUser::getUserAccountName, sdlBUser.getUserAccount()));

            }

            if (StringUtils.isNotBlank(sdlBUser.getDeptId())) {
                queryWrapper.eq(SdlBUser::getDeptNew, sdlBUser.getDeptId());
                // queryWrapper.apply("sdl_b_user.dept_id in (select t_dept.dept_id from t_dept where t_dept.DEPT_ID='"+sdlBUser.getDeptId()+"' or t_dept.PARENT_ID='"+sdlBUser.getDeptId()+"' )");
            }
            if (StringUtils.isNotBlank(sdlBUser.getSchoolDateFrom())) {
                queryWrapper
                        .ge(SdlBUser::getSchoolDate, sdlBUser.getSchoolDateFrom())
                ;
            }
            if (StringUtils.isNotBlank(sdlBUser.getYishiZgzsbianhao())) {
                queryWrapper.apply("(sdl_b_user.yishi_zgzsbianhao is null or sdl_b_user.yishi_zgzsbianhao='')");
            }
            if (StringUtils.isNotBlank(sdlBUser.getYishiZiyebianhao())) {
                queryWrapper.apply("(sdl_b_user.yishi_ziyebianhao is null or sdl_b_user.yishi_ziyebianhao='')");
            }
            if (StringUtils.isNotBlank(sdlBUser.getSchoolDateTo())) {
                queryWrapper.le(SdlBUser::getSchoolDate, sdlBUser.getSchoolDateTo());
            }
            queryWrapper.apply("sdl_b_user.yuangongzu!='规培' and sdl_b_user.yuangongzu!='博士后' and (sdl_b_user.renshizifw ='医师' or  (sdl_b_user.renshizifw='技术' and sdl_b_user.renshizfenlei='卫生'))");
            if (StringUtils.isNotBlank(sdlBUser.getAuditMan())) {
                List<String> states = Arrays.asList(sdlBUser.getAuditMan().replace("[", "").replace("]", "").replace("\"", "").split(","));
                queryWrapper.in(SdlBUser::getState, states);
            }
            queryWrapper.ne(SdlBUser::getState, 0);//只显示2或者3的


            Page<SdlBUser> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<SdlBUser> findSdlBUsersHz(QueryRequest request, SdlBUser sdlBUser) {
        try {
           // List<String> deptIds = this.deptMapper.getListIds(sdlBUser.getDeptId());
            LambdaQueryWrapper<SdlBUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SdlBUser::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(sdlBUser.getUserAccount())) {
                queryWrapper.and(wrap -> wrap.eq(SdlBUser::getUserAccount, sdlBUser.getUserAccount()).or()
                        .like(SdlBUser::getUserAccountName, sdlBUser.getUserAccount()));

            }
//            if (sdlBUser.getState()!=null) {
//                queryWrapper.eq(SdlBUser::getState, sdlBUser.getState());
//            }
            if (StringUtils.isNotBlank(sdlBUser.getDeptId())) {
                queryWrapper.eq(SdlBUser::getDeptNew, sdlBUser.getDeptId());
                // queryWrapper.apply("sdl_b_user.dept_id in (select t_dept.dept_id from t_dept where t_dept.DEPT_ID='"+sdlBUser.getDeptId()+"' or t_dept.PARENT_ID='"+sdlBUser.getDeptId()+"' )");
            }
            if (StringUtils.isNotBlank(sdlBUser.getSchoolDateFrom())) {
                queryWrapper
                        .ge(SdlBUser::getSchoolDate, sdlBUser.getSchoolDateFrom())
                ;
            }
            if (StringUtils.isNotBlank(sdlBUser.getSexName())) { //单科会诊、多学科会诊
                if(sdlBUser.getSexName().equals("单科会诊")){
                    queryWrapper.in(SdlBUser::getZhicheng,"副高,正高,中级".split(","));
                }
                else if(sdlBUser.getSexName().equals("多学科会诊")){
                    queryWrapper.apply(" (zhicheng in ('副高','正高') or  (doc_type ='医技' and zhicheng ='中级' )  or LENGTH(zyjs_np)>0 or  LENGTH(zyjs_npjx)>0) ");
                }
            }
            if (StringUtils.isNotBlank(sdlBUser.getYishiZgzsbianhao())) {
                queryWrapper.apply("(sdl_b_user.yishi_zgzsbianhao is null or sdl_b_user.yishi_zgzsbianhao='')");
            }
            if (StringUtils.isNotBlank(sdlBUser.getYishiZiyebianhao())) {
                queryWrapper.apply("(sdl_b_user.yishi_ziyebianhao is null or sdl_b_user.yishi_ziyebianhao='')");
            }
            if (StringUtils.isNotBlank(sdlBUser.getSchoolDateTo())) {
                queryWrapper.le(SdlBUser::getSchoolDate, sdlBUser.getSchoolDateTo());
            }
            queryWrapper.apply("sdl_b_user.yuangongzu!='规培' and sdl_b_user.yuangongzu!='博士后' and (sdl_b_user.renshizifw ='医师' or  (sdl_b_user.renshizifw='技术' and sdl_b_user.renshizfenlei='卫生'))");
            if (StringUtils.isNotBlank(sdlBUser.getAuditMan())) {
                List<String> states = Arrays.asList(sdlBUser.getAuditMan().replace("[", "").replace("]", "").replace("\"", "").split(","));
                queryWrapper.in(SdlBUser::getState, states);
            }
            queryWrapper.ne(SdlBUser::getState, 0);//只显示2或者3的


            Page<SdlBUser> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<SdlBUser> findSdlBUserList(QueryRequest request, SdlBUser sdlBUser) {
        try {
            Page<SdlBUser> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findSdlBUser(page, sdlBUser);
        } catch (Exception e) {
            log.error("获取失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createSdlBUser(SdlBUser sdlBUser) {
        sdlBUser.setId(UUID.randomUUID().toString());
        sdlBUser.setCreateTime(new Date());
        sdlBUser.setIsDeletemark(1);
        this.save(sdlBUser);
    }

    @Override
    @Transactional
    public void updateSdlBUser(SdlBUser sdlBUser) {
        sdlBUser.setModifyTime(new Date());
        this.baseMapper.updateSdlBUser(sdlBUser);
    }

    @Override
    @Transactional
    public void deleteSdlBUsers(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
    }
    @Override
    @Transactional
    public List<CustomDept> getDeptNew(){
        return this.baseMapper.getDeptNew();
    }
    @Override
    @Transactional
    public List<CustomUser> getUserAccounts() {
        return this.baseMapper.getUserAccounts();
    }
}