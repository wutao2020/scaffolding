package com.wt.adminvue.entity;

    import com.baomidou.mybatisplus.annotation.TableName;
    import com.baomidou.mybatisplus.annotation.IdType;
    import com.baomidou.mybatisplus.annotation.TableId;
    import java.time.LocalDateTime;
    import com.baomidou.mybatisplus.annotation.TableField;
    import java.io.Serializable;
    import io.swagger.annotations.ApiModel;
    import io.swagger.annotations.ApiModelProperty;
    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;

/**
* <p>
    * 
    * </p>
*
* @author wutao
* @since 2021-12-26
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @TableName("sys_menu")
    @ApiModel(value="Menu对象", description="")
    public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

            @TableId(value = "id", type = IdType.AUTO)
    private Long id;

            @ApiModelProperty(value = "父菜单ID，一级菜单为0")
    private Long parentId;

    private String name;

            @ApiModelProperty(value = "菜单URL")
    private String path;

            @ApiModelProperty(value = "授权(多个用逗号分隔，如：user:list,user:create)")
    private String perms;

    private String component;

            @ApiModelProperty(value = "类型     0：目录   1：菜单   2：按钮")
    private Integer type;

            @ApiModelProperty(value = "菜单图标")
    private String icon;

            @ApiModelProperty(value = "排序")
        @TableField("orderNum")
    private Integer orderNum;

    private LocalDateTime created;

    private LocalDateTime updated;

    private Integer statu;


}
