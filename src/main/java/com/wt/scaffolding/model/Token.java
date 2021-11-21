package com.wt.scaffolding.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 
 * </p>
 *
 * @author 吴涛
 * @since 2021-11-18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Token {
    private String token;
    /** 登陆时间戳（毫秒） */
    private Long loginTime;
}
