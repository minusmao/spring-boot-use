package com.example.spring.boot.use.ftp.config;

import com.example.spring.boot.use.ftp.properties.FtpProperties;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * FTPClient连接池
 * 1.可以获取池中空闲链接
 * 2.可以将链接归还到池中
 * 3.当池中空闲链接不足时，可以创建链接
 *
 * @author minus
 * @since 2022/11/26 17:24
 */
@Component
public class FtpClientPool {

    private final GenericObjectPool<FTPClient> internalPool;

    /**
     * 构造函数：初始化连接池
     *
     * @param factory FTPClient工厂
     */
    public FtpClientPool(@Autowired FtpClientFactory factory, @Autowired FtpProperties ftpProperties) {
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxTotal(ftpProperties.getPoolMaxTotal());  // 最大实例总数
        poolConfig.setMinIdle(ftpProperties.getPoolMinIdle());    // 最小空闲实例的数量
        poolConfig.setMaxIdle(ftpProperties.getPoolMaxIdle());    // 最大空闲实例的数量
        poolConfig.setMaxWaitMillis(ftpProperties.getPoolMaxWaitMillis());    // 最大borrowObject()阻塞时间
        this.internalPool = new GenericObjectPool<>(factory, poolConfig);
    }

    /**
     * 从连接池中取连接
     *
     * @return FTPClient
     */
    public FTPClient getFTPClient() {
        try {
            return internalPool.borrowObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将链接归还到连接池
     *
     * @param ftpClient FTPClient
     */
    public void returnFTPClient(FTPClient ftpClient) {
        try {
            internalPool.returnObject(ftpClient);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 销毁池子
     */
    public void destroy() {
        try {
            internalPool.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
