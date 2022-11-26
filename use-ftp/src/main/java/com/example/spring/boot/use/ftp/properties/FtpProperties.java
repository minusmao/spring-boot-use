package com.example.spring.boot.use.ftp.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * FTP配置信息
 *
 * @author minus
 * @since 2022/11/26 16:15
 */
@Data
@ConfigurationProperties(prefix = "ftp")
public class FtpProperties {

    private String host;

    private Integer port;

    private String username;

    private String password;

    private String encoding;

    private String root;

    private String workdir;

    private int poolMaxTotal;

    private int poolMinIdle;

    private int poolMaxIdle;

    private int poolMaxWaitMillis;

}
