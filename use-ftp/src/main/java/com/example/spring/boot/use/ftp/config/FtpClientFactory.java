package com.example.spring.boot.use.ftp.config;

import com.example.spring.boot.use.ftp.properties.FtpProperties;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * FTPClient工厂
 *
 * @author minus
 * @since 2022/11/26 16:46
 */
@Component
public class FtpClientFactory implements PooledObjectFactory<FTPClient> {

    @Autowired
    private FtpProperties ftpProperties;

    /**
     * 创建并包装FTPClient实例
     */
    @Override
    public PooledObject<FTPClient> makeObject() {
        return new DefaultPooledObject<>(new FTPClient());
    }

    /**
     * 销毁连接，当连接池空闲数量达到上限时，调用此方法销毁连接
     */
    @Override
    public void destroyObject(PooledObject<FTPClient> pooledObject) {
        FTPClient ftpClient = pooledObject.getObject();
        try {
            ftpClient.logout();
            if (ftpClient.isConnected()) {
                ftpClient.disconnect();
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not disconnect from server.", e);
        }
    }

    /**
     * 连接状态检查
     */
    @Override
    public boolean validateObject(PooledObject<FTPClient> pooledObject) {
        FTPClient ftpClient = pooledObject.getObject();
        try {
            return ftpClient.sendNoOp();
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 初始化连接
     */
    @Override
    public void activateObject(PooledObject<FTPClient> pooledObject) {
        FTPClient ftpClient = pooledObject.getObject();
        try {
            ftpClient.connect(ftpProperties.getHost(), ftpProperties.getPort());
            ftpClient.login(ftpProperties.getUsername(), ftpProperties.getPassword());
            ftpClient.setControlEncoding(ftpProperties.getEncoding());
            ftpClient.enterLocalPassiveMode();                               // 被动模式
            ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);         // 设置传输方式为流方式
            ftpClient.changeWorkingDirectory(ftpProperties.getWorkdir());    // 设置工作目录
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);                     // 设置上传文件类型为二进制，否则将无法打开文件
        } catch (IOException e) {
            throw new RuntimeException("Could not connect from server.", e);
        }

    }

    /**
     * 钝化连接
     */
    @Override
    public void passivateObject(PooledObject<FTPClient> pooledObject) {
        FTPClient ftpClient = pooledObject.getObject();
        try {
            ftpClient.changeWorkingDirectory(ftpProperties.getRoot());
            ftpClient.logout();
            if (ftpClient.isConnected()) {
                ftpClient.disconnect();
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not disconnect from server.", e);
        }
    }

}
