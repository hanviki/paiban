package cc.mrbird.febs.sdl.entity;

import lombok.Data;

@Data
public class CustomUser {
    /**
     * 姓名
     */


    private String userAccountName;

    /**
     * 人事编号
     */


    private String userAccount;

    /**
     * 资质类型id
     */


    private Integer userType;

    /**
     * 资质类型
     */


    private String userTypeName;
}
