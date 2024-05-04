package com.example.spring.boot.use.minio.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * MinIO 配置信息
 *
 * @author minus
 * @since 2024/5/4 14:01
 */
@Data
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {

    private String host;

    private String accessKey;

    private String secretKey;

}
