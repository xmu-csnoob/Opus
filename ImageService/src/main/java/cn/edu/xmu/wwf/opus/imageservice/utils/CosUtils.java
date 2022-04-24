package cn.edu.xmu.wwf.opus.imageservice.utils;

import cn.edu.xmu.wwf.opus.imageservice.config.CosProperties;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
@Component
public class CosUtils
{
    private final String domain;
    private final String bucket;

    // 七牛文件上传管理器
    private final Configuration cfg;
    private final Auth auth;

    @Autowired
    public CosUtils(CosProperties properties)
    {
        String ak = properties.getAccessKey();
        String sk = properties.getSecretKey();
        this.domain = properties.getDomain(); // CDN域名
        this.bucket = properties.getBucket();

        // //构造一个带指定 Region 对象的配置类
        cfg = new Configuration(Region.autoRegion());
        auth = Auth.create(ak,sk);
    }

    /**
     * 上传图片到七牛云
     * @return 图片url
     * */
    public String uploadImage(InputStream in, String key)
    {
        try {
            UploadManager uploadManager = new UploadManager(cfg);
            String upToken = auth.uploadToken(bucket);
            Response response = uploadManager.put(in,key,upToken,null, null);
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            return String.format("http://%s/%s",this.domain,putRet.key);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        return null;
    }
}

