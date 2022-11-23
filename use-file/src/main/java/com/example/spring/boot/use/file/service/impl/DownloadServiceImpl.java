package com.example.spring.boot.use.file.service.impl;

import com.example.spring.boot.use.file.common.exception.BadRequestException;
import com.example.spring.boot.use.file.common.exception.ResourceNotFoundException;
import com.example.spring.boot.use.file.service.DownloadService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 文件下载
 *
 * @author minus
 * @since 2022/11/23 23:45
 */
@Service
public class DownloadServiceImpl implements DownloadService {

    @Autowired
    private ResourceLoader resourceLoader;

    @Value("${file.path}")
    private String filePath;

    @Override
    public void downloadFile(String filename, HttpServletResponse response) {
        // 查看文件是否存在
        File file = new File(filePath + "/" + filename);
        if (!file.exists()) {
            throw new ResourceNotFoundException("文件不存在");
        }
        // 设置响应头
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Content-Length", "" + file.length());
        /*
           Content-Disposition的作用：告知浏览器以何种方式显示响应返回的文件，用浏览器打开还是以附件的形式下载到本地保存
             attachment表示以附件方式下载  inline表示在线打开  "Content-Disposition: inline; filename=文件名.mp3"
             filename表示文件的默认名称，因为网络传输只支持URL编码，因此需要将文件名URL编码后进行传输，前端收到后需要反编码才能获取到真正的名称
         */
        String filenameEncode = URLEncoder.encode(file.getName(), StandardCharsets.UTF_8);
        response.addHeader("Content-Disposition", "attachment;filename=" + filenameEncode);
        // 输出响应流
        try (InputStream inputStream = new FileInputStream(file);
             OutputStream outputStream = response.getOutputStream()
        ) {
            byte[] buf = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, len);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new ResourceNotFoundException("文件不存在");
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResourceNotFoundException("文件流错误");
        }
    }

    @Override
    public ResponseEntity<InputStreamResource> downloadFile(String filename) throws IOException {
        String downloadFilePath = filePath + "/" + filename;
        FileSystemResource file = new FileSystemResource(downloadFilePath);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getFilename()));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(file.getInputStream()));
    }

    @Override
    public void downloadResource(Integer mode, HttpServletResponse response) {
        // 获取项目路径
        String projectPath = System.getProperty("user.dir");
        String resourcePath = switch (mode) {
            case 1 -> "file:" + projectPath + "/doc/docker/docker-compose.yml";
            case 2 -> "classpath:application.yml";
            default -> throw new BadRequestException("资源匹配模式参数错误");
        };
        // 判断资源是否存在
        Resource resource = resourceLoader.getResource(resourcePath);
        if (!resource.exists()) {
            throw new ResourceNotFoundException("资源不存在");
        }
        // 设置响应头
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.addHeader("Pragma", "no-cache");
        response.addHeader("Content-Disposition", "attachment;filename=test.txt");
        // 输出响应流
        try(InputStream inputStream = resource.getInputStream();
            OutputStream outputStream = response.getOutputStream()) {
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResourceNotFoundException("文件流错误");
        }
    }

}
