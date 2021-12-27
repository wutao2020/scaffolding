package com.wt.adminvue.dto;

import com.wt.adminvue.page.PageQueryDTO;
import lombok.Data;

/**
 * @author 吴涛
 * @version 1.0.0
 * @ClassName UserDto.java
 * @Description TODO
 * @createTime 2021年12月27日 18:01:00
 */
@Data
public class UserDto extends PageQueryDTO {
    private String username;
}
