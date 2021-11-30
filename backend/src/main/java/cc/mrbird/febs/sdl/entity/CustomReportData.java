package cc.mrbird.febs.sdl.entity;

import lombok.Data;

@Data
public class CustomReportData {
    private Integer key;
    private String value;
    private int displayIndex;

    public CustomReportData(Integer key,String value){
        this.key =key;
        this.value =value;
    }
}
