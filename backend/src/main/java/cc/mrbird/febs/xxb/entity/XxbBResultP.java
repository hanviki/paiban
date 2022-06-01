package cc.mrbird.febs.xxb.entity;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2022-05-27
 */

@Excel("xxb_b_result_p")
@Data
@Accessors(chain = true)
public class XxbBResultP implements Serializable{

private static final long serialVersionUID=1L;

    /**
     * resultfId
     */
                            
        @ExcelField(value ="resultfId")
    private String id;

    /**
     * resultId
     */
        
        @ExcelField(value ="resultId")
    private String pid;

    /**
     * 打分人
     */
        
        @ExcelField(value ="打分人")
    private String dfname;

    /**
     * 打分人科室单位
     */
        
        @ExcelField(value ="打分人科室单位")
    private String dfwork;

    /**
     * 打分人职称
     */
        
        @ExcelField(value ="打分人职称")
    private String dfzhic;

    /**
     * 创新性
     */
        
        @ExcelField(value ="创新性")
    private Double cxx;

    /**
     * 实用性
     */
        
        @ExcelField(value ="实用性")
    private Double syx;

    /**
     * 技术难度
     */
        
        @ExcelField(value ="技术难度")
    private Double jsnd;

    /**
     * 效益性
     */
        
        @ExcelField(value ="效益性")
    private Double xyx;

    /**
     * 评分
     */
        
        @ExcelField(value ="评分")
    private Double total;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
        
        @ExcelField(value ="创建时间", writeConverter = DateConverter.class,readConverter = DateReadConverter.class)
    private Date createTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;

    /**
     * 状态
     */
    @TableField("STATE")
        
        @ExcelField(value ="状态")
    private Integer state;

    /**
     * 是否删除
     */
    @TableField("IS_DELETEMARK")
        
        @ExcelField(value ="是否删除")
    private Integer isDeletemark;



    public static final String ID ="id" ;

    public static final String PID ="pid" ;

    public static final String DFNAME ="dfname" ;

    public static final String DFWORK ="dfwork" ;

    public static final String DFZHIC ="dfzhic" ;

    public static final String CXX ="cxx" ;

    public static final String SYX ="syx" ;

    public static final String JSND ="jsnd" ;

    public static final String XYX ="xyx" ;

    public static final String TOTAL ="total" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String STATE ="STATE" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

        }