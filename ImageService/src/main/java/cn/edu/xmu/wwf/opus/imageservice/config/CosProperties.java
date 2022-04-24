package cn.edu.xmu.wwf.opus.imageservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "qiniu")
@Data
public class CosProperties {
    private String domain; // CDN域名
    private String accessKey; // ACCESS_KEY
    private String secretKey; // SECRET_KEY
    private String bucket; // 空间名称
}
