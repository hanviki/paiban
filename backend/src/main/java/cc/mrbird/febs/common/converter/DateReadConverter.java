package cc.mrbird.febs.common.converter;

import cn.hutool.Hutool;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import com.wuwenze.poi.convert.ReadConverter;
import com.wuwenze.poi.exception.ExcelKitReadConverterException;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class DateReadConverter implements ReadConverter{
    @Override
    public Date convert(Object value) throws ExcelKitReadConverterException {
        if(value!=null && value.toString()!=""){
            return DateUtil.parse(value.toString().replace("T00:00:00.000",""));
        }
        else{
            return  null;
        }
    }
}
