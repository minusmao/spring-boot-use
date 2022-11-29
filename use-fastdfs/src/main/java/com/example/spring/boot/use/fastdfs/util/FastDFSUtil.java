package com.example.spring.boot.use.fastdfs.util;

import com.github.tobato.fastdfs.domain.conn.FdfsWebServer;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.Charset;

/**
 * FastDFS工具类
 *
 * @author minus
 * @since 2022/11/28 0:06
 */
@Component
public class FastDFSUtil {

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Autowired
    private FdfsWebServer fdfsWebServer;

    /**
     * 文件上传（使用InputStream类型）
     *
     * @param inputStream 输入流
     * @param size        文件大小
     * @param extension   文件扩展名
     * @return 文件路径信息
     */
    public StorePath uploadFile(InputStream inputStream, long size, String extension) {
        return fastFileStorageClient.uploadFile(inputStream, size, extension, null);
    }

    /**
     * 文件上传（使用MultipartFile类型）
     *
     * @param file 上传文件
     * @return 文件路径信息
     * @throws IOException 文件流错误
     */
    public StorePath uploadFile(MultipartFile file) throws IOException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        return uploadFile(file.getInputStream(), file.getSize(), extension);
    }

    /**
     * 文件上传（使用普通File类型）
     *
     * @param file 上传文件
     * @return 文件路径信息
     * @throws IOException 文件流错误
     */
    public StorePath uploadFile(File file) throws IOException {
        String extension = FilenameUtils.getExtension(file.getName());
        return uploadFile(new FileInputStream(file), file.length(), extension);
    }

    /**
     * 文件上传（使用byte流类型）
     *
     * @param bytes     文件字节
     * @param extension 文件扩展名
     * @return 文件路径信息
     */
    public StorePath uploadFile(byte[] bytes, String extension) {
        return uploadFile(new ByteArrayInputStream(bytes), bytes.length, extension);
    }

    /**
     * 文件上传（使用String文本类型）
     *
     * @param content   文本内容
     * @param charset   字符集
     * @param extension 文件扩展名
     * @return 文件路径信息
     */
    public StorePath uploadFile(String content, Charset charset, String extension) {
        return uploadFile(content.getBytes(charset), extension);
    }

    /**
     * 下载文件
     *
     * @param fullPath 文件路径
     * @return 文件字节数组
     */
    public byte[] downloadFile(String fullPath) {
        StorePath storePath = StorePath.parseFromUrl(fullPath);
        DownloadByteArray downloadByteArray = new DownloadByteArray();
        return fastFileStorageClient.downloadFile(storePath.getGroup(), storePath.getPath(), downloadByteArray);
    }

    /**
     * 删除文件
     *
     * @param fullPath 文件路径
     */
    public void deleteFile(String fullPath) {
        if (StringUtils.isBlank(fullPath)) {
            return;
        }
        StorePath storePath = StorePath.parseFromUrl(fullPath);
        fastFileStorageClient.deleteFile(storePath.getGroup(), storePath.getPath());
    }

    /**
     * 根据StorePath路径信息对象获取文件名称
     *
     * @param storePath 文件路径信息
     * @return 文件名称
     */
    public String getFilename(StorePath storePath) {
        String path = storePath.getPath();
        return path.substring(path.lastIndexOf('/') + 1);
    }

    /**
     * 根据StorePath路径信息对象获取文件完整URL地址
     *
     * @param storePath 文件路径信息
     * @return 文件完整URL地址
     */
    public String getRestAccessUrl(StorePath storePath) {
        return fdfsWebServer.getWebServerUrl() + storePath.getFullPath();
    }

    /**
     * 根据fullPath文件路径获得获取文件完整URL地址
     *
     * @param fullPath 文件路径
     * @return 文件完整URL地址
     */
    public String getRestAccessUrl(String fullPath) {
        return fdfsWebServer.getWebServerUrl() + fullPath;
    }

}
