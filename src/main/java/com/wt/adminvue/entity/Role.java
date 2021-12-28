package com.wt.adminvue.entity;

    import com.baomidou.mybatisplus.annotation.IdType;
    import com.baomidou.mybatisplus.annotation.TableField;
    import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
    import java.util.ArrayList;
    import java.util.List;

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
@TableName("sys_role")
@ApiModel(value="Role对象", description="")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    private String code;

    @ApiModelProperty(value = "备注")
    private String remark;

    private LocalDateTime created;

    private LocalDateTime updated;

    private Integer statu;

    @TableField(exist = false)
    private List<Long> menuIds = new ArrayList<>();
}
