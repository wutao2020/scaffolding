package com.wt.adminvue.service.impl;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.wt.adminvue.config.OssProperties;
import com.wt.adminvue.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author 吴涛
 * @version 1.0.0
 * @ClassName FileServiceImpl.java
 * @Description TODO
 * @createTime 2021年11月23日 10:27:00
 */
@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private OssProperties ossProperties;
    @Override
    public String upload(MultipartFile file, String module) throws IOException {
        String endpoint = ossProperties.getEndpoint();
        String keyid = ossProperties.getKeyid();
        String keysecret = ossProperties.getKeysecret();
        String bucketname = ossProperties.getBucketname();

        //判断oss实例是否存在：如果不存在则创建，如果存在则获取
        OSS ossClient = new OSSClientBuilder().build(endpoint, keyid, keysecret);
        if (!ossClient.doesBucketExist(bucketname)) {
            //创建bucket
            ossClient.createBucket(bucketname);
            //设置oss实例的访问权限：公共读
            ossClient.setBucketAcl(bucketname, CannedAccessControlList.PublicRead);
        }

        //构建日期路径：avatar/2019/02/26/文件名

        //文件名：uuid.扩展名
        String fileName = UUID.randomUUID().toString();
        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String key = module +  "/" + fileName + fileExtension;

        //文件上传至阿里云
        ossClient.putObject(ossProperties.getBucketname(), key, file.getInputStream());

        // 关闭OSSClient。
        ossClient.shutdown();

        //返回url地址
        return "https://" + bucketname + "." + endpoint + "/" + key;
    }

    @Override
    public List<String> uploadList(List<MultipartFile> fileList, String module) throws IOException {
        String endpoint = ossProperties.getEndpoint();
        String keyid = ossProperties.getKeyid();
        String keysecret = ossProperties.getKeysecret();
        String bucketname = ossProperties.getBucketname();
        List<String> list=new ArrayList<>();
        //判断oss实例是否存在：如果不存在则创建，如果存在则获取
        OSS ossClient = new OSSClientBuilder().build(endpoint, keyid, keysecret);
        if (!ossClient.doesBucketExist(bucketname)) {
            //创建bucket
            ossClient.createBucket(bucketname);
            //设置oss实例的访问权限：公共读
            ossClient.setBucketAcl(bucketname, CannedAccessControlList.PublicRead);
        }

        //构建日期路径：avatar/2019/02/26/文件名
        if (fileList!=null&&fileList.size()>0){
            for (MultipartFile file:fileList){
                //文件名：uuid.扩展名
                String fileName = UUID.randomUUID().toString();
                String originalFilename = file.getOriginalFilename();
                String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String key = module +  "/" + fileName + fileExtension;
                //文件上传至阿里云
                ossClient.putObject(ossProperties.getBucketname(), key, file.getInputStream());
                String path="https://" + bucketname + "." + endpoint + "/" + key;;
                list.add(path);
            }
        }
        // 关闭OSSClient。
        ossClient.shutdown();
        //返回url地址
        return list;
    }
}
