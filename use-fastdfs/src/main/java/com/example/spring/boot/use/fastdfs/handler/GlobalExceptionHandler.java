package com.example.spring.boot.use.fastdfs.handler;

import com.example.spring.boot.use.fastdfs.common.exception.*;
import com.example.spring.boot.use.fastdfs.model.ResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 *
 * @author minus
 * @since 2022-11-21 23:17
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 运行时异常
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ResultVO<Object> handler(Exception e) {
        logger.error("运行时异常：{}", e.getMessage());
        return ResultVO.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

    // 客户端错误输入异常
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ResultVO<Object> handler(BadRequestException e) {
        logger.error("客户端错误输入异常：{}", e.getMessage());
        return ResultVO.fail(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    // 操作失败异常
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(OperationFailureException.class)
    public ResultVO<Object> handler(OperationFailureException e) {
        logger.error("操作失败异常：{}", e.getMessage());
        return ResultVO.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

    // 身份认证异常
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public ResultVO<Object> handler(UnauthorizedException e) {
        logger.error("身份认证异常：{}", e.getMessage());
        return ResultVO.fail(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
    }

    // 权限鉴权异常
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    public ResultVO<Object> handler(ForbiddenException e) {
        logger.error("权限鉴权异常：{}", e.getMessage());
        return ResultVO.fail(HttpStatus.FORBIDDEN.value(), e.getMessage());
    }

    // 资源未找到异常
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResultVO<Object> handler(ResourceNotFoundException e) {
        logger.error("资源未找到异常：{}", e.getMessage());
        return ResultVO.fail(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

}
