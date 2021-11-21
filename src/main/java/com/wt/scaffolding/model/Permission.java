package com.wt.scaffolding.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author 吴涛
 * @since 2021-11-17
 */
@Getter
@Setter
@TableName("sys_permission")
@ApiModel(value = "Permission对象", description = "")
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("parentId")
    private Integer parentId;

    @TableField("`name`")
    private String name;

    @TableField("css")
    private String css;

    @TableField("href")
    private String href;

    @TableField("`type`")
    private Integer type;

    @TableField("permission")
    private String permission;

    @TableField("sort")
    private Integer sort;
    private List<Permission> child;


}
