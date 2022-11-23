package com.example.spring.boot.use.file.service;

import javax.servlet.http.HttpServletResponse;

/**
 * 结合nginx代理的文件下载验证
 *
 * @author minus
 * @since 2022/11/24 1:14
 */
public interface PermissionService {

    void downloadFile(String filename, String token, HttpServletResponse response);

}
