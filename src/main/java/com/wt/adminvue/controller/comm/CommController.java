package com.wt.adminvue.controller.comm;

import com.wt.adminvue.dto.FileModel;
import com.wt.adminvue.service.FileService;
import com.wt.adminvue.util.Result;
import com.wt.adminvue.util.ResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 吴涛
 * @version 1.0.0
 * @ClassName CommController.java
 * @Description TODO
 * @createTime 2021年12月29日 14:04:00
 */
@Api(value = "农民信箱", description = "公共")
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/common")
public class CommController {
    @Autowired
    private FileService fileService;
    /**
     * 文件上传
     *
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/uploadFileUrl", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("文件上传")
    public Result uploadFileUrl(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "fileType", required = true)
            @ApiParam("文件类型(1头像)")
                    Integer fileType) throws IOException {
        FileModel model =new FileModel();
        if (fileType!=null&&fileType.intValue()==1){
            String path=fileService.upload(file, "headportrait");
            model.setFilePath(path);
        }

        return ResultGenerator.genSuccessResult(model);
    }
    /**
     * 文件上传
     *
     * @param fileList
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/uploadFileUrlList", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("多文件上传")
    public Result<List<String>> uploadFileUrlList(
            @RequestParam("fileList") List<MultipartFile> fileList,
            @RequestParam(value = "fileType", required = true)
            @ApiParam("文件类型(1是产销对接，2是防灾减灾，3轮播,4提问，5其他)")
                    Integer fileType) throws IOException{
        List<String>path = new ArrayList<>();
        if (fileType!=null&&fileType.intValue()==1){
            path=fileService.uploadList(fileList, "prodMarke");
        }else if (fileType!=null&&fileType.intValue()==2){
            path=fileService.uploadList(fileList, "reduction");
        }else if (fileType!=null&&fileType.intValue()==3){
            path=fileService.uploadList(fileList, "map");
        }else if (fileType!=null&&fileType.intValue()==4){
            path=fileService.uploadList(fileList, "questions");
        }else if (fileType!=null&&fileType.intValue()==5){
            path=fileService.uploadList(fileList, "qita");
        }
        List<FileModel> list=new ArrayList<>();
        if (path!=null&&path.size()>0){
            path.forEach(s -> {
                FileModel model=new FileModel();
                model.setFilePath(s);
                list.add(model);
            });
        }
        return ResultGenerator.genSuccessResult(list);
    }
}
