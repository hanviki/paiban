package cc.mrbird.febs.sdl.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.rfc.CustomUser;
import cc.mrbird.febs.sdl.dao.SdlBUserMgMapper;
import cc.mrbird.febs.sdl.entity.CustomDept;
import cc.mrbird.febs.sdl.entity.SdlBUserMg;
import cc.mrbird.febs.sdl.service.ISdlBUserMgService;
import cc.mrbird.febs.system.dao.DeptMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-10-18
 */
@Slf4j
@Service("ISdlBUserMgService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class SdlBUserMgServiceImpl extends ServiceImpl<SdlBUserMgMapper, SdlBUserMg> implements ISdlBUserMgService {

    @Autowired
    private DeptMapper deptMapper;

    @Override
    public IPage<SdlBUserMg> findSdlBUserMgs(QueryRequest request, SdlBUserMg sdlBUser) {
        try {
            List<String> deptIds = this.deptMapper.getListIds(sdlBUser.getDeptId());
            LambdaQueryWrapper<SdlBUserMg> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SdlBUserMg::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(sdlBUser.getUserAccount())) {
                queryWrapper.and(wrap -> wrap.eq(SdlBUserMg::getUserAccount, sdlBUser.getUserAccount()).or()
                        .like(SdlBUserMg::getUserAccountName, sdlBUser.getUserAccount()));

            }
            if (sdlBUser.getState() != null) {
                queryWrapper.eq(SdlBUserMg::getState, sdlBUser.getState());
            }
            if (StringUtils.isNotBlank(sdlBUser.getDeptId())) {
                queryWrapper.in(SdlBUserMg::getDeptId, deptIds);
                // queryWrapper.apply("sdl_b_user_mg.dept_id in (select t_dept.dept_id from t_dept where t_dept.DEPT_ID='"+sdlBUser.getDeptId()+"' or t_dept.PARENT_ID='"+sdlBUser.getDeptId()+"' )");
            }
            queryWrapper.ne(SdlBUserMg::getState, 0);//只显示2或者3的


            Page<SdlBUserMg> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;

        }
    }

    @Override
    public SdlBUserMg findObjByAccount(String account) {
        try {
            LambdaQueryWrapper<SdlBUserMg> wrapper = new LambdaQueryWrapper();
            wrapper.eq(SdlBUserMg::getUserAccount,account);
            List<SdlBUserMg> list = this.baseMapper.selectList(wrapper);
            return  list.size() == 0 ? null : list.get(0);

        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }
    @Override
    public List<cc.mrbird.febs.sdl.entity.CustomUser> findSdlBUserMgs(SdlBUserMg sdlBUser) {
        try {
            List<String> deptIds = this.deptMapper.getListIds(sdlBUser.getDeptId());


            return  this.baseMapper.findSdlBUserMgByDeptId(deptIds);

        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;

        }
    }
    @Override
    public List<SdlBUserMg> findSdlBUserMgs_search( SdlBUserMg sdlBUser) {
        try {

            LambdaQueryWrapper<SdlBUserMg> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SdlBUserMg::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(sdlBUser.getUserAccount())) {
                queryWrapper.and(wrap -> wrap.eq(SdlBUserMg::getUserAccount, sdlBUser.getUserAccount()).or()
                        .like(SdlBUserMg::getUserAccountName, sdlBUser.getUserAccount()));

            }

                queryWrapper.ne(SdlBUserMg::getState, 0);//只显示2或者3的

           // queryWrapper.eq(SdlBUserMg::getRylx,"职工");
          //  queryWrapper.apply("sdl_b_user_mg.yuangongzu!='规培' and sdl_b_user_mg.yuangongzu!='博士后' and (sdl_b_user_mg.renshizifw ='医师' or  (sdl_b_user_mg.renshizifw='技术' and sdl_b_user_mg.renshizfenlei='卫生'))");

            IPage<SdlBUserMg> page = new Page<>();
            page.setPages(1L);
            page.setSize(5);
            return  this.baseMapper.selectPage(page,queryWrapper).getRecords();

        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<SdlBUserMg> findSdlBUserMgsYwc(QueryRequest request, SdlBUserMg sdlBUser) {
        try {
            List<String> deptIds = this.deptMapper.getListIds(sdlBUser.getDeptId());
            LambdaQueryWrapper<SdlBUserMg> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SdlBUserMg::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(sdlBUser.getUserAccount())) {
                queryWrapper.and(wrap -> wrap.eq(SdlBUserMg::getUserAccount, sdlBUser.getUserAccount()).or()
                        .like(SdlBUserMg::getUserAccountName, sdlBUser.getUserAccount()));

            }

            if (StringUtils.isNotBlank(sdlBUser.getDeptId())) {
                queryWrapper.in(SdlBUserMg::getDeptId, deptIds);
                // queryWrapper.apply("sdl_b_user_mg.dept_id in (select t_dept.dept_id from t_dept where t_dept.DEPT_ID='"+sdlBUser.getDeptId()+"' or t_dept.PARENT_ID='"+sdlBUser.getDeptId()+"' )");
            }
            if (StringUtils.isNotBlank(sdlBUser.getSchoolDateFrom())) {
                queryWrapper
                        .ge(SdlBUserMg::getSchoolDate, sdlBUser.getSchoolDateFrom())
                ;
            }
            if (StringUtils.isNotBlank(sdlBUser.getYishiZgzsbianhao())) {
                queryWrapper.apply("(sdl_b_user_mg.yishi_zgzsbianhao is null or sdl_b_user_mg.yishi_zgzsbianhao='')");
            }
            if (StringUtils.isNotBlank(sdlBUser.getYishiZiyebianhao())) {
                queryWrapper.apply("(sdl_b_user_mg.yishi_ziyebianhao is null or sdl_b_user_mg.yishi_ziyebianhao='')");
            }
            if (StringUtils.isNotBlank(sdlBUser.getSchoolDateTo())) {
                queryWrapper.le(SdlBUserMg::getSchoolDate, sdlBUser.getSchoolDateTo());
            }
            queryWrapper.apply("sdl_b_user_mg.yuangongzu!='规培' and sdl_b_user_mg.yuangongzu!='博士后' and (sdl_b_user_mg.renshizifw ='医师' or  (sdl_b_user_mg.renshizifw='技术' and sdl_b_user_mg.renshizfenlei='卫生'))");
            if (StringUtils.isNotBlank(sdlBUser.getAuditMan())) {
                List<String> states = Arrays.asList(sdlBUser.getAuditMan().replace("[", "").replace("]", "").replace("\"", "").split(","));
                queryWrapper.in(SdlBUserMg::getState, states);
            }
            queryWrapper.ne(SdlBUserMg::getState, 0);//只显示2或者3的


            Page<SdlBUserMg> page = new Page<>();
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
    public IPage<SdlBUserMg> findSdlBUserMgsYwc2(QueryRequest request, SdlBUserMg sdlBUser) {
        try {
           // List<String> deptIds = this.deptMapper.getListIds(sdlBUser.getDeptId());
            LambdaQueryWrapper<SdlBUserMg> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SdlBUserMg::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(sdlBUser.getUserAccount())) {
                queryWrapper.and(wrap -> wrap.eq(SdlBUserMg::getUserAccount, sdlBUser.getUserAccount()).or()
                        .like(SdlBUserMg::getUserAccountName, sdlBUser.getUserAccount()));

            }

            if (StringUtils.isNotBlank(sdlBUser.getDeptId())) {
                queryWrapper.eq(SdlBUserMg::getDeptNew, sdlBUser.getDeptId());
                // queryWrapper.apply("sdl_b_user_mg.dept_id in (select t_dept.dept_id from t_dept where t_dept.DEPT_ID='"+sdlBUser.getDeptId()+"' or t_dept.PARENT_ID='"+sdlBUser.getDeptId()+"' )");
            }
            if (StringUtils.isNotBlank(sdlBUser.getSchoolDateFrom())) {
                queryWrapper
                        .ge(SdlBUserMg::getSchoolDate, sdlBUser.getSchoolDateFrom())
                ;
            }
            if (StringUtils.isNotBlank(sdlBUser.getYishiZgzsbianhao())) {
                queryWrapper.apply("(sdl_b_user_mg.yishi_zgzsbianhao is null or sdl_b_user_mg.yishi_zgzsbianhao='')");
            }
            if (StringUtils.isNotBlank(sdlBUser.getYishiZiyebianhao())) {
                queryWrapper.apply("(sdl_b_user_mg.yishi_ziyebianhao is null or sdl_b_user_mg.yishi_ziyebianhao='')");
            }
            if (StringUtils.isNotBlank(sdlBUser.getSchoolDateTo())) {
                queryWrapper.le(SdlBUserMg::getSchoolDate, sdlBUser.getSchoolDateTo());
            }
            queryWrapper.apply("sdl_b_user_mg.yuangongzu!='规培' and sdl_b_user_mg.yuangongzu!='博士后' and (sdl_b_user_mg.renshizifw ='医师' or  (sdl_b_user_mg.renshizifw='技术' and sdl_b_user_mg.renshizfenlei='卫生'))");
            if (StringUtils.isNotBlank(sdlBUser.getAuditMan())) {
                List<String> states = Arrays.asList(sdlBUser.getAuditMan().replace("[", "").replace("]", "").replace("\"", "").split(","));
                queryWrapper.in(SdlBUserMg::getState, states);
            }
            queryWrapper.ne(SdlBUserMg::getState, 0);//只显示2或者3的


            Page<SdlBUserMg> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<SdlBUserMg> findSdlBUserMgsHz(QueryRequest request, SdlBUserMg sdlBUser) {
        try {
           // List<String> deptIds = this.deptMapper.getListIds(sdlBUser.getDeptId());
            LambdaQueryWrapper<SdlBUserMg> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SdlBUserMg::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(sdlBUser.getUserAccount())) {
                queryWrapper.and(wrap -> wrap.eq(SdlBUserMg::getUserAccount, sdlBUser.getUserAccount()).or()
                        .like(SdlBUserMg::getUserAccountName, sdlBUser.getUserAccount()));

            }
//            if (sdlBUser.getState()!=null) {
//                queryWrapper.eq(SdlBUserMg::getState, sdlBUser.getState());
//            }
            if (StringUtils.isNotBlank(sdlBUser.getDeptId())) {
                queryWrapper.eq(SdlBUserMg::getDeptNew, sdlBUser.getDeptId());
                // queryWrapper.apply("sdl_b_user_mg.dept_id in (select t_dept.dept_id from t_dept where t_dept.DEPT_ID='"+sdlBUser.getDeptId()+"' or t_dept.PARENT_ID='"+sdlBUser.getDeptId()+"' )");
            }
            if (StringUtils.isNotBlank(sdlBUser.getSchoolDateFrom())) {
                queryWrapper
                        .ge(SdlBUserMg::getSchoolDate, sdlBUser.getSchoolDateFrom())
                ;
            }
            if (StringUtils.isNotBlank(sdlBUser.getSexName())) { //单科会诊、多学科会诊
                if(sdlBUser.getSexName().equals("单科会诊")){
                    queryWrapper.in(SdlBUserMg::getZhicheng,"副高,正高,中级".split(","));
                }
                else if(sdlBUser.getSexName().equals("多学科会诊")){
                    queryWrapper.apply(" (zhicheng in ('副高','正高') or  (doc_type ='医技' and zhicheng ='中级' )  or LENGTH(zyjs_np)>0 or  LENGTH(zyjs_npjx)>0) ");
                }
            }
            if (StringUtils.isNotBlank(sdlBUser.getYishiZgzsbianhao())) {
                queryWrapper.apply("(sdl_b_user_mg.yishi_zgzsbianhao is null or sdl_b_user_mg.yishi_zgzsbianhao='')");
            }
            if (StringUtils.isNotBlank(sdlBUser.getYishiZiyebianhao())) {
                queryWrapper.apply("(sdl_b_user_mg.yishi_ziyebianhao is null or sdl_b_user_mg.yishi_ziyebianhao='')");
            }
            if (StringUtils.isNotBlank(sdlBUser.getSchoolDateTo())) {
                queryWrapper.le(SdlBUserMg::getSchoolDate, sdlBUser.getSchoolDateTo());
            }
            queryWrapper.apply("sdl_b_user_mg.yuangongzu!='规培' and sdl_b_user_mg.yuangongzu!='博士后' and (sdl_b_user_mg.renshizifw ='医师' or  (sdl_b_user_mg.renshizifw='技术' and sdl_b_user_mg.renshizfenlei='卫生'))");
            if (StringUtils.isNotBlank(sdlBUser.getAuditMan())) {
                List<String> states = Arrays.asList(sdlBUser.getAuditMan().replace("[", "").replace("]", "").replace("\"", "").split(","));
                queryWrapper.in(SdlBUserMg::getState, states);
            }
            queryWrapper.ne(SdlBUserMg::getState, 0);//只显示2或者3的


            Page<SdlBUserMg> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<SdlBUserMg> findSdlBUserMgList(QueryRequest request, SdlBUserMg sdlBUser) {
        try {
            Page<SdlBUserMg> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findSdlBUserMg(page, sdlBUser);
        } catch (Exception e) {
            log.error("获取失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createSdlBUserMg(SdlBUserMg sdlBUser) {
        sdlBUser.setId(UUID.randomUUID().toString());
        sdlBUser.setCreateTime(new Date());
        sdlBUser.setIsDeletemark(1);
        this.save(sdlBUser);
    }

    @Override
    @Transactional
    public void updateSdlBUserMg(SdlBUserMg sdlBUser) {
        sdlBUser.setModifyTime(new Date());
        this.baseMapper.updateSdlBUserMg(sdlBUser);
    }

    @Override
    @Transactional
    public void deleteSdlBUserMgs(String[] Ids) {
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