package cn.edu.xmu.wwf.opus.imageservice.utils;

import com.obs.services.ObsClient;
import com.obs.services.model.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class CosUtils {

    @Value("0UPLYTKYU1C2HPEXB0FF")//ak
    private String ak;
    @Value("NZBwyRqititEyoyUYJNWAUlGPZEKSjLfXZmTSwzc")//sk
    private String sk;
    @Value("opus")//桶名称
    private String bucketName;
    @Value("obs.cn-east-3.myhuaweicloud.com")//终端节点访问
    private String endpoint;

    /**
     * 上传文件--流式
     * @param fileName 上传文件名称
     * @param fileType 文件路径
     * @param is 文件流
     * @return
     */
    public boolean uploadFile(String fileName, FileType fileType, InputStream is) {
        ObsClient obsClient = null;
        try {
            String objectName = fileType.getType().concat("/").concat(fileName);
            obsClient = new ObsClient(ak, sk, endpoint);
            HeaderResponse response = obsClient.putObject(bucketName, objectName, is);
            // 可选：调用成功后，记录调用成功的HTTP状态码和服务端请求ID
            int statusCode = response.getStatusCode();
            if (200 == statusCode) {
                return true;
            }
            obsClient.close();
        } catch (IOException e) {
            log.info("文件上传失败：{}", e.getMessage(), e);
        }
        return false;
    }

    /**
     * 上传文件--字节数组
     * @param fileName 上传文件名称
     * @param fileType 文件路径
     * @param is 文件流
     * @return
     */
    public boolean uploadFileByte(String fileName, FileType fileType, byte[] is) {
        ObsClient obsClient = null;
        try {
            String objectName = fileType.getType().concat("/").concat(fileName);
            obsClient = new ObsClient(ak, sk, endpoint);
            HeaderResponse response = obsClient.putObject(bucketName, objectName, new ByteArrayInputStream(is));
            // 可选：调用成功后，记录调用成功的HTTP状态码和服务端请求ID
            int statusCode = response.getStatusCode();
            if (200 == statusCode) {
                return true;
            }
            obsClient.close();
        } catch (IOException e) {
            log.info("文件上传失败：{}", e.getMessage(), e);
        }
        return false;
    }

    /**
     * 下载文件
     * @param fileName 文件名称
     * @param fileType 文件路径
     * @return
     */
    public String getDownloadUrl(String fileName, FileType fileType) {
        ObsClient obsClient = null;
        obsClient = new ObsClient(ak, sk, endpoint);
        // URL有效期，3600秒.5分钟
        long expireSeconds = 3600L;
        String objectName = fileType.getType().concat("/").concat(fileName);
        TemporarySignatureRequest request = new TemporarySignatureRequest(HttpMethodEnum.GET, expireSeconds);
        request.setBucketName(bucketName);
        request.setObjectKey(objectName);
        TemporarySignatureResponse response = obsClient.createTemporarySignature(request);
        return response.getSignedUrl();
    }

    /**
     * 获取上传地址
     * @param fileName 文件名称
     * @param fileType 文件路径
     * @return
     */
    public String getUploadUrl(String fileName, FileType fileType) {
        try {
            // 创建ObsClient实例
            ObsClient obsClient = new ObsClient(ak, sk, endpoint);
            // URL有效期，3600秒
            long expireSeconds = 3600L;
            Map<String, String> headers = new HashMap<String, String>();
            headers.put("Content-Type", "application/octet-stream");
            String objectName = fileType.getType().concat("/").concat(fileName);
            TemporarySignatureRequest request = new TemporarySignatureRequest(HttpMethodEnum.PUT, expireSeconds);
            request.setBucketName(bucketName);
            request.setObjectKey(objectName);
            request.setHeaders(headers);
            TemporarySignatureResponse response = obsClient.createTemporarySignature(request);
            return response.getSignedUrl();
        } catch (Exception e) {
            log.error("获取上传地址异常：{}", e.getMessage(), e);
        }
        return null;
    }

    /**
     * 下载文件返回字节数组
     * @param fileName 文件名称
     * @param fileType 文件路径
     * @return
     */
    public byte[] downloadFile(String fileName, FileType fileType) {
        try {
            InputStream inputStream = downloadFileInputStream(fileName, fileType);
            byte[] bytes = IOUtils.toByteArray(inputStream);
            return bytes;
        } catch (Exception e) {
            log.error("下载文件异常：{}", e.getMessage(), e);
        }
        return null;
    }
    /**
     * 上传视频
     * @param fileName 文件名称
     * @param fileType 文件路径
     * @return
     */
    public boolean uploadFileVideo(String fileName, FileType fileType, InputStream is) {
        try {
            String objectName = fileType.getType().concat("/").concat(fileName);
            ObsClient obsClient = new ObsClient(ak, sk, endpoint);
            // 添加 ContentType (添加后可在浏览器中直接浏览，而非下载链接)
            obsClient.putObject(bucketName, objectName, is);
            obsClient.close();
            return true;
        } catch (Exception e) {
            log.error("上传视频文件异常：{}", e.getMessage(), e);
        }
        return false;
    }

    /**
     * 下载文件返回流式
     * @param fileName 文件名称
     * @param fileType 文件路径
     * @return
     */
    public InputStream downloadFileInputStream(String fileName, FileType fileType) {
        try {
            String objectName = fileType.getType().concat("/").concat(fileName);
            // 用户拿到STS临时凭证后，通过其中的安全令牌（SecurityToken）和临时访问密钥（AccessKeyId和AccessKeySecret）生成OSSClient。
            ObsClient obsClient = new ObsClient(ak, sk, endpoint);
            ObsObject obsObject = obsClient.getObject(bucketName, objectName);
            obsClient.close();
            return obsObject.getObjectContent();
        } catch (Exception e) {
            log.error("下载文件异常：{}", e.getMessage(), e);
        }
        return null;
    }


    public enum FileType {
        TEST("TEST", "测试")
        ;

        private String type;
        private String desc;

        FileType(String type, String desc) {
            this.type = type;
            this.desc = desc;
        }

        public String getType() {
            return type;
        }

        public String getDesc() {
            return desc;
        }
    }
}

