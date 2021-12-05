package cn.timelost.hr.controller;

import java.io.IOException;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.upyun.RestManager;
import com.upyun.UpException;

import cn.hutool.core.util.StrUtil;
import cn.timelost.hr.config.UpYunConfig;
import cn.timelost.hr.enums.ResultEnum;
import cn.timelost.hr.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;

/**
 * @author: Jyf
 * @Date: 2021/1/31 17:39
 */
@RestController
@Slf4j
@RequestMapping("/image")
public class ImageController {
    
    @Resource
    private UpYunConfig upYunConfig;
    
    @PostMapping("/upload")
    public ResultVo upload(@RequestParam("file") MultipartFile multipartFile) throws IOException, UpException {
        if (StrUtil.isBlank(upYunConfig.getBucketName()) || StrUtil.isBlank(upYunConfig.getUsername()) ||
                StrUtil.isBlank(upYunConfig.getPassword()) || StrUtil.isBlank(upYunConfig.getImageHost())) {
            return ResultVo.fail(ResultEnum.UPLOAD_NOT_ENABLE);
        }
        RestManager manager = new RestManager(upYunConfig.getBucketName(), upYunConfig.getUsername(),
                upYunConfig.getPassword());
        String fileName = "/ehr/" + genUniqueKey() + ".jpg";
        Response response = manager.writeFile(fileName, multipartFile.getInputStream(), (Map) null);
        String imgUrl = upYunConfig.getImageHost() + fileName;
        if (response.isSuccessful()) {
            log.info(imgUrl);
            return ResultVo.success(imgUrl);
        }
        return ResultVo.fail(ResultEnum.UPLOAD_FAIL);
    }
    
    public static synchronized String genUniqueKey() {
        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;
        return System.currentTimeMillis() + String.valueOf(number);
    }
}