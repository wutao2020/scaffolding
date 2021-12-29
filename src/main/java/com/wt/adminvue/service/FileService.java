package com.wt.adminvue.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author 吴涛
 * @version 1.0.0
 * @ClassName FileService.java
 * @Description TODO
 * @createTime 2021年11月23日 10:24:00
 */
public interface FileService {
    /**
     * 文件上传至阿里云
     */
    String upload(MultipartFile file, String module) throws IOException;

    List<String> uploadList(List<MultipartFile> fileList, String prodMarke) throws IOException;
}
