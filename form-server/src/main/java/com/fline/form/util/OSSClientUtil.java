package com.fline.form.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.fline.form.exception.ImgException;
import com.itextpdf.io.codec.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * 阿里云 OSS文件类
 *
 */
public class OSSClientUtil {

    Log log = LogFactory.getLog(OSSClientUtil.class);
    // endpoint以杭州为例，其它region请按实际情况填写
    private String endpoint = "http://172.28.2.2";
    // accessKey
    private String accessKeyId = "LTAI4G3nAyiJHEVk9JmRfTqn";
    private String accessKeySecret = "So478qYSu1cIyj427kihuL8DrAcRuq";
    //空间
    private String bucketName = "ycyztb20200422";

    //文件地址
    private final String url="http://ycyztb20200422.oss-cn-ningxia-a-internal.aliyuncs.com/";
    //文件存储目录根目录
    public static final String ROOT = "form_center/";
    //APP 文件存储路径
    public static final String APP = "app/";
    //PC 文件存储路径
    public static final String PC = "pc/";

    private OSS ossClient;

    public OSSClientUtil() {
        ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }

    /**
     * 初始化
     */
    public void init() {
        ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }

    /**
     * 销毁
     */
    public void destory() {
        ossClient.shutdown();
    }

    /**
     * 上传图片
     *
     * @param url
     */
    public void uploadImg2Oss(String url,String path) {
        File fileOnServer = new File(url);
        FileInputStream fin;
        try {
            fin = new FileInputStream(fileOnServer);
            String[] split = url.split("/");
            this.uploadFile2OSS(fin, split[split.length - 1],path);
        } catch (FileNotFoundException e) {
            throw new ImgException("图片上传失败");
        }
    }

    /**
     * Base64 位 上传图片
     * @param base64
     * * @param path OSS 存储路径
     * @return
     */
    public String uploadImg2OssBase64(String base64,String path) {
        byte[] bytes = Base64.decode(base64);
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        String stuffix =null;
        try {
            stuffix = UUID.randomUUID().toString()+".pdf";
            this.uploadFile2OSS(bais, stuffix,path);
        } catch (Exception e) {
            throw new ImgException("图片上传失败");
        }
        return stuffix;
    }

    /**
     * Base64 位 上传图片
     * @param base64
     * * @param path OSS 存储路径
     * @return
     */
    public String uploadImg2OssBase64(String base64,String postfix,String path) {
        byte[] bytes = Base64.decode(base64);
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        String stuffix =null;
        try {
            stuffix = UUID.randomUUID().toString()+postfix;
            this.uploadFile2OSS(bais, stuffix,path);
        } catch (Exception e) {
            throw new ImgException("图片上传失败");
        }
        return stuffix;
    }


    public String uploadImg2Oss(MultipartFile file,String path) {
        if (file.getSize() > 1024 * 1024) {
            throw new ImgException("上传图片大小不能超过1M！");
        }
        String originalFilename = file.getOriginalFilename();
        String substring = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        Random random = new Random();
        String name = random.nextInt(10000) + System.currentTimeMillis() + substring;
        try {
            InputStream inputStream = file.getInputStream();
            this.uploadFile2OSS(inputStream, name,path);
            return name;
        } catch (Exception e) {
            throw new ImgException("图片上传失败");
        }
    }


    /**
     * 上传到OSS服务器  如果同名文件会覆盖服务器上的
     *
     * @param instream 文件流
     * @param fileName 文件名称 包括后缀名
     * @param path OSS 存储路径
     * @return 出错返回"" ,唯一MD5数字签名
     */
    public String uploadFile2OSS(InputStream instream, String fileName,String path) {
        String ret = "";
        try {
            //创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(instream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(getcontentType(fileName.substring(fileName.lastIndexOf("."))));
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            //上传文件
            PutObjectResult putResult = ossClient.putObject(bucketName, path + fileName, instream, objectMetadata);
            ret = putResult.getETag();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                if (instream != null) {
                    instream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    /**
     * Description: 判断OSS服务文件上传时文件的contentType
     *
     * @param FilenameExtension 文件后缀
     * @return String
     */
    public static String getcontentType(String FilenameExtension) {
        if (FilenameExtension.equalsIgnoreCase(".bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase(".jpeg") ||
                FilenameExtension.equalsIgnoreCase(".jpg") ||
                FilenameExtension.equalsIgnoreCase(".png")) {
            return "image/jpeg";
        }
        if (FilenameExtension.equalsIgnoreCase(".html")) {
            return "text/html";
        }
        if (FilenameExtension.equalsIgnoreCase(".txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equalsIgnoreCase(".vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equalsIgnoreCase(".pptx") ||
                FilenameExtension.equalsIgnoreCase(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equalsIgnoreCase(".docx") ||
                FilenameExtension.equalsIgnoreCase(".doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equalsIgnoreCase(".xml")) {
            return "text/xml";
        }
        return "image/jpeg";
    }

    /**
     * 获得url链接
     *
     * @param key
     * @return
     */
    public String getUrl(String key) {
        // 设置URL过期时间为10年  3600l* 1000*24*365*10
        /*Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24);
        // 生成URL
        URL url = ossClient.generatePresignedUrl(bucketName, key, expiration);
        if (url != null) {
            return url.toString();
        }*/
        return url+key;
    }
}