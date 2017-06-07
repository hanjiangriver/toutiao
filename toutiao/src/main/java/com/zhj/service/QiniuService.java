package com.zhj.service;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.zhj.ToutiaoUtil.ToutiaoUtil;

@Service
public class QiniuService {

	private static final Logger logger = LoggerFactory.getLogger(QiniuService.class);
    //设置好账号的ACCESS_KEY和SECRET_KEY
    String ACCESS_KEY = "nnyloGnEWIYxgBU01C7F5kZ5ox4pGrRsCK1P4uFd";
    String SECRET_KEY = "-Y486z5Z8SQ746v7B3rar5es5oDLUDju2NDhVW_k";
    //要上传的空间
    String bucketname = "zhjphoto";

    //密钥配置
    Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    //创建上传对象
  //构造一个带指定Zone对象的配置类
    Configuration cfg = new Configuration(Zone.zone2());
    //...其他参数参考类注释
    UploadManager uploadManager = new UploadManager(cfg);

    //private static String QINIU_IMAGE_DOMAIN = "http://7xsetu.com1.z0.glb.clouddn.com/";
    

    //简单上传，使用默认策略，只需要设置上传的空间名就可以了
    public String getUpToken() {
        return auth.uploadToken(bucketname);
    }

    public String saveImage(MultipartFile file) throws IOException {
        try {
            int dotPos = file.getOriginalFilename().lastIndexOf(".");
            if (dotPos < 0) {
                return null;
            }
            String fileExt = file.getOriginalFilename().substring(dotPos + 1).toLowerCase();
            if (!ToutiaoUtil.isFileAllowed(fileExt)) {
                return null;
            }

            String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt;
            //调用put方法上传
            Response res = uploadManager.put(file.getBytes(), fileName, getUpToken());           
            //打印返回的信息
//            System.err.println(res.bodyString());
//            return null;
            if (res.isOK() && res.isJson()) {
                return ToutiaoUtil.QINIU_IMAGE_DOMAIN + JSONObject.parseObject(res.bodyString()).get("key");
            } else {
                logger.error("七牛异常:" + res.bodyString());
                return null;
            }
        } catch (QiniuException e) {
            // 请求失败时打印的异常的信息
            logger.error("七牛异常:" + e.getMessage());
            return null;
        }
    }
	
}
