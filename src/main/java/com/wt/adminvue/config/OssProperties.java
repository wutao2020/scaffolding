package com.wt.adminvue.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 吴涛
 * @version 1.0.0
 * @ClassName OssProperties.java
 * @Description TODO
 * @createTime 2021年11月23日 10:18:00
 */
@Data
@Component
//注意prefix要写到最后一个 "." 符号之前
@ConfigurationProperties(prefix="aliyun.oss")
public class OssProperties {
    private String endpoint;
    private String keyid;
    private String keysecret;
    private String bucketname;
    private String domain;
}