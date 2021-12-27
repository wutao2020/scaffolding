package com.wt.adminvue.entity;

    import com.baomidou.mybatisplus.annotation.TableField;
    import com.baomidou.mybatisplus.annotation.TableName;
    import com.baomidou.mybatisplus.annotation.IdType;
    import com.baomidou.mybatisplus.annotation.TableId;
    import java.time.LocalDateTime;
    import java.io.Serializable;
    import java.util.ArrayList;
    import java.util.List;

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
    @TableName("sys_user")
    @ApiModel(value="User对象", description="")
    public class User implements Serializable {

    private static final long serialVersionUID = 1L;

            @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String username;

    private String password;

    private String avatar;

    private String email;

    private String city;

    private LocalDateTime created;

    private LocalDateTime updated;

    private LocalDateTime lastLogin;

    private Integer statu;

    @TableField(exist = false)
    private List<Role> sysRoles = new ArrayList<>();


}
