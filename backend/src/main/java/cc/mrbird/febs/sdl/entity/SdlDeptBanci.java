package cc.mrbird.febs.sdl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
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
 * @since 2021-11-08
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
    private Integer banciId;

    /**
     * 部门id
     */
        
        @ExcelField(value ="部门id")
    private String deptId;

    /**
     * 班次名称
     */
        
        @ExcelField(value ="班次名称")
    private String banciName;

    /**
     * 部门名称
     */
        
        @ExcelField(value ="部门名称")
    private String deptName;

    /**
     * 有效开始时间
     */
        
        @ExcelField(value ="有效开始时间", writeConverter = DateConverter.class)
    private Date startDate;
    private transient String startDateFrom;
    private transient String startDateTo;

    /**
     * 有效结束时间
     */
        
        @ExcelField(value ="有效结束时间", writeConverter = DateConverter.class)
    private Date endDate;
    private transient String endDateFrom;
    private transient String endDateTo;



    public static final String ID ="id" ;

    public static final String BANCI_ID ="banci_id" ;

    public static final String DEPT_ID ="dept_id" ;

    public static final String BANCI_NAME ="banci_name" ;

    public static final String DEPT_NAME ="dept_name" ;

    public static final String START_DATE ="start_date" ;

    public static final String END_DATE ="end_date" ;

        }