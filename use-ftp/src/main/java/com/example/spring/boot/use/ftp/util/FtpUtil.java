package com.example.spring.boot.use.ftp.util;

import com.example.spring.boot.use.ftp.config.FtpClientPool;
import com.example.spring.boot.use.ftp.properties.FtpProperties;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

/**
 * FTP工具类
 *
 * @author minus
 * @since 2022/11/26 17:34
 */
@Component
public class FtpUtil {

    @Autowired
    FtpProperties ftpProperties;

    @Autowired
    FtpClientPool ftpClientPool;

    @Autowired
    private ResourceLoader resourceLoader;

    /**
     * 向FTP服务器上传文件
     *
     * @param file 上传到FTP服务器上的文件
     * @return 返回文件名
     */
    public String uploadToFtp(MultipartFile file) {
        FTPClient ftpClient = ftpClientPool.getFTPClient();
        // 生成文件名
        String filename = UUID.randomUUID().toString();
        // 获得文件后缀
        filename += '.' + FilenameUtils.getExtension(file.getOriginalFilename());
        // 开始进行文件上传
        try (InputStream input = file.getInputStream()) {
            // 执行文件传输
            boolean result = ftpClient.storeFile(ftpProperties.getWorkdir() + "/" + filename, input);
            if (!result) {
                throw new RuntimeException("上传失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("上传失败");
        } finally {
            // 归还资源
            ftpClientPool.returnFTPClient(ftpClient);
        }
        return filename;
    }

    /**
     * 通过ResourceLoader访问FTP文件
     *
     * @param fileName 需要读取的文件名
     * @return 返回文件对应的Entity
     */
    public Resource getFptResource(String fileName) {
        String username = ftpProperties.getUsername();
        String password = ftpProperties.getPassword();
        String host = ftpProperties.getHost();
        String workdir = ftpProperties.getWorkdir();
        // 拼接资源路径，结构：ftp://root:root@192.168.xx.xx/+fileName
        String resourcePath = "ftp://" + username + ":" + password + "@" + host + workdir + "/" + fileName;

        return resourceLoader.getResource(resourcePath);
    }

}
