package cc.mrbird.febs.system.dao;

import cc.mrbird.febs.system.domain.Dept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface DeptMapper extends BaseMapper<Dept> {

    @Delete("delete from t_dept where dept_id=#{deptId}")
  void  deleteDeptById(@Param("deptId") String deptId);

    @Select("select dept_id from t_dept where dept_id=#{deptId} or parent_id =#{deptId}")
    List<String> getListIds(@Param("deptId") String deptId);
}