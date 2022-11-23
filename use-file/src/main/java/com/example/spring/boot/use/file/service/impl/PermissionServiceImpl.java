package com.example.spring.boot.use.file.service.impl;

import com.example.spring.boot.use.file.common.exception.ForbiddenException;
import com.example.spring.boot.use.file.service.PermissionService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 结合nginx代理的文件下载验证
 * 参考文档：https://blog.csdn.net/itmm_wang/article/details/107705613
 *
 * @author minus
 * @since 2022/11/24 1:14
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Override
    public void downloadFile(String filename, String token, HttpServletResponse response) {
        // 验证token
        if (!token.equals("eyJhbGciOiJIUzI1NiJ9")) {
            throw new ForbiddenException("无访问权限");
        }

        // 已被授权访问
        // 文件下载
        String filenameEncode = URLEncoder.encode(filename, StandardCharsets.UTF_8);
        response.setHeader("Content-Disposition", "inline; filename=\"" + filenameEncode + "\"");
        // 文件以二进制流传输
//        response.setHeader("Content-Type", "application/octet-stream;charset=utf-8");  // 设置此项会触发下载
        // 返回真实文件路径交由 Nginx 处理，保证前端无法看到真实的文件路径。
        // 这里的 "/file_server" 为 Nginx 中配置的下载服务名
        response.setHeader("X-Accel-Redirect", "/file-server/" + filename);
        // 限速，单位字节，默认不限
        // response.setHeader("X-Accel-Limit-Rate","1024");
        // 是否使用Nginx缓存，默认yes
        // response.setHeader("X-Accel-Buffering","yes");
        response.setHeader("X-Accel-Charset", "utf-8");

        // 禁止浏览器缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "No-cache");
        response.setHeader("Expires", "0");
    }

}
