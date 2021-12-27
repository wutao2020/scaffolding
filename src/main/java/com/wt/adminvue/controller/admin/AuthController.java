package com.wt.adminvue.controller.admin;

import cn.hutool.core.lang.UUID;
import com.google.code.kaptcha.Producer;
import com.wt.adminvue.model.CaptchaModel;
import com.wt.adminvue.security.AccountUser;
import com.wt.adminvue.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/admin/auth")
public class AuthController {
    @Autowired
    private Producer producer;
    @Autowired
    private RedisUtil redisUtil;
    @GetMapping("/captcha")
    public Result<CaptchaModel> captcha() throws IOException {

        String key = UUID.randomUUID().toString();
        String code = producer.createText();

        BufferedImage image = producer.createImage(code);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outputStream);

        BASE64Encoder encoder = new BASE64Encoder();
        String str = "data:image/jpeg;base64,";

        String base64Img = str + encoder.encode(outputStream.toByteArray());

        redisUtil.hset(Const.CAPTCHA_KEY, key, code, 120);
        CaptchaModel model = new CaptchaModel();
        model.setCaptchaImg(base64Img);
        model.setRandomCode(key);
        return ResultGenerator.genSuccessResult(model);
    }
    @PostMapping("/getUserInfo")
    public Result<AccountUser> getUserInfo(){

        return ResultGenerator.error(ResultCode.EXCEL_DATA_IMPORT_ERROR);
    }
}
