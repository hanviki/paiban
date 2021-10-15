package cc.mrbird.febs.sdl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;


import lombok.Data;
import lombok.experimental.Accessors;
import java.util.Date;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import cc.mrbird.febs.common.converter.*;

/**
 * <p>
 * 
 * </p>
 *
 * @author viki
 * @since 2021-10-13
 */

@Excel("sdl_dept_zizhi")
@Data
@Accessors(chain = true)
public class SdlDeptZizhi implements Serializable{

private static final long serialVersionUID=1L;

                    @TableId(value = "id" , type = IdType.AUTO)
                
    private Long id;

    /**
     * 资质id
     */
        
        @ExcelField(value ="资质id")
    private Long banciId;

    /**
     * 部门id
     */
        
        @ExcelField(value ="部门id")
    private String deptId;

    /**
     * 是否到病区
     */
        
        @ExcelField(value ="是否到病区")
    private Boolean isBq;



    public static final String ID ="id" ;

    public static final String BANCI_ID ="banci_id" ;

    public static final String DEPT_ID ="dept_id" ;

    public static final String IS_BQ ="is_bq" ;

        }