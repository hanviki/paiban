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

@Excel("sdl_dept_banci")
@Data
@Accessors(chain = true)
public class SdlDeptBanci implements Serializable{

private static final long serialVersionUID=1L;

                    @TableId(value = "id" , type = IdType.AUTO)
                
    private Long id;

    /**
     * 系列id
     */
        
        @ExcelField(value ="系列id")
    private Long banciId;

    /**
     * 部门id
     */
        
        @ExcelField(value ="部门id")
    private String deptId;



    public static final String ID ="id" ;

    public static final String BANCI_ID ="banci_id" ;

    public static final String DEPT_ID ="dept_id" ;

        }