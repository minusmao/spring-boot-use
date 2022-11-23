package com.example.spring.boot.use.file.controller;

import com.example.spring.boot.use.file.common.ApiConst;
import com.example.spring.boot.use.file.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * 结合nginx代理的文件下载验证
 *
 * @author minus
 * @since 2022/11/23 21:33
 */
@RestController
@RequestMapping(ApiConst.API_URL + "/permission")
@Api(tags = "结合nginx代理的文件下载验证")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping
    @ApiOperation(value = "API-01-文件下载验证")
    public void downloadFile(
            @ApiParam("文件名") @RequestParam String filename,
            @ApiParam("验证令牌") @RequestParam String token,
            HttpServletResponse response
    ) {
        permissionService.downloadFile(filename, token, response);
    }

}
