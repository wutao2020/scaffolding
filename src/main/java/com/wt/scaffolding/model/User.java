package com.wt.scaffolding.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
@TableName("sys_user")
@ApiModel(value = "User对象", description = "")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("username")
    private String username;

    @TableField("`password`")
    private String password;

    @TableField("nickname")
    private String nickname;

    @TableField("headImgUrl")
    private String headImgUrl;

    @TableField("phone")
    private String phone;

    @TableField("telephone")
    private String telephone;

    @TableField("email")
    private String email;

    @TableField("birthday")
    private LocalDate birthday;

    @TableField("sex")
    private Integer sex;

    @TableField("`status`")
    private Integer status;

    @TableField("createTime")
    private LocalDateTime createTime;

    @TableField("updateTime")
    private LocalDateTime updateTime;


}
