package com.wt.adminvue.entity;

import lombok.Data;

import java.util.List;

/**
 * @author 吴涛
 * @version 1.0.0
 * @ClassName SysMenuModel.java
 * @Description TODO
 * @createTime 2021年12月27日 16:26:00
 */
@Data
public class SysMenuModel {
    private Long id;
    private String name;
    private String title;
    private String icon;
    private String path;
    private String component;
    private List<SysMenuModel> children;
}
