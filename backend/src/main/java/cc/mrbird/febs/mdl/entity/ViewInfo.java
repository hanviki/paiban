package cc.mrbird.febs.mdl.entity;

import java.time.LocalDate;
import java.io.Serializable;


import lombok.Data;
import lombok.experimental.Accessors;
import java.util.Date;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import cc.mrbird.febs.common.converter.*;

/**
 * <p>
 * VIEW
 * </p>
 *
 * @author viki
 * @since 2022-05-19
 */

@Excel("view_info")
@Data
@Accessors(chain = true)
public class ViewInfo implements Serializable{

private static final long serialVersionUID=1L;

        
    private Long id;


    /**
     * 姓名
     */
        
        @ExcelField(value ="姓名")
    private String userAccountName;

    /**
     * 发薪号
     */
        
        @ExcelField(value ="发薪号")
    private String userAccount;

    /**
     * 职称
     */
        
        @ExcelField(value ="职称")
    private String zhicheng;

    /**
     * 性别
     */
        
        @ExcelField(value ="性别")
    private String sexName;

    /**
     * 生日
     */
        
        @ExcelField(value ="生日")
    private String birthday;

    /**
     * 级别
     */
        
        @ExcelField(value ="级别")
    private String jb;

    /**
     * 生效时间
     */
        
        @ExcelField(value ="生效时间", writeConverter = DateConverter.class,readConverter = DateReadConverter.class)
    private Date validDate;

    /**
     * 科室
     */
        
        @ExcelField(value ="科室")
    private String deptName;

    /**
     * 备注
     */
        
        @ExcelField(value ="备注")
    private String note;

//    /**
//     * 编码
//     */
//
//    @ExcelField(value ="编码")
//    private String code;
//
//    /**
//     * 手术名称
//     */
//
//    @ExcelField(value ="手术名称")
//    private String name;
//
//    /**
//     * 手术等级
//     */
//
//    @ExcelField(value ="手术等级")
//    private String level;
//
//    /**
//     * 手术切口类别
//     */
//
//    @ExcelField(value ="手术切口类别")
//    private String lb;


    public static final String ID ="id" ;

    public static final String CODE ="code" ;

    public static final String NAME ="name" ;

    public static final String LEVEL ="level" ;

    public static final String LB ="lb" ;

    public static final String USER_ACCOUNT_NAME ="user_account_name" ;

    public static final String USER_ACCOUNT ="user_account" ;

    public static final String ZHICHENG ="zhicheng" ;

    public static final String SEX_NAME ="sex_name" ;

    public static final String BIRTHDAY ="birthday" ;

    public static final String JB ="jb" ;

    public static final String VALID_DATE ="valid_date" ;

    public static final String DEPT_NAME ="dept_name" ;

    public static final String NOTE ="note" ;

        }