package com.example.spring.boot.use.file.service.impl;

import com.example.spring.boot.use.file.common.exception.OperationFailureException;
import com.example.spring.boot.use.file.common.util.FileUtil;
import com.example.spring.boot.use.file.model.FileInfoVO;
import com.example.spring.boot.use.file.model.ResultVO;
import com.example.spring.boot.use.file.service.UploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件上传
 *
 * @author minus
 * @since 2022/11/23 21:53
 */
@Service
public class UploadServiceImpl implements UploadService {

    @Value("${file.path}")
    private String filePath;

    @Override
    public ResultVO<FileInfoVO> uploadFile(MultipartFile file) {
        // 保存文件到本地
        if (!FileUtil.fileWrite(file, filePath)) {
            throw new OperationFailureException("文件上传失败");
        }
        // 获取文件信息
        FileInfoVO fileInfo = new FileInfoVO();
        fileInfo.setFileName(file.getOriginalFilename());
        fileInfo.setContentType(file.getContentType());
        fileInfo.setFileSize(file.getSize());

        return ResultVO.suc(fileInfo);
    }

    @Override
    public ResultVO<List<FileInfoVO>> uploadFiles(MultipartFile file1, MultipartFile file2) {
        // 保存文件1到本地
        if (!FileUtil.fileWrite(file1, filePath)) {
            throw new OperationFailureException("文件上传失败");
        }
        // 获取文件1信息
        FileInfoVO fileInfo1 = new FileInfoVO();
        fileInfo1.setFileName(file1.getOriginalFilename());
        fileInfo1.setContentType(file1.getContentType());
        fileInfo1.setFileSize(file1.getSize());

        // 保存文件2到本地
        if (!FileUtil.fileWrite(file2, filePath)) {
            throw new OperationFailureException("文件上传失败");
        }
        // 获取文件2信息
        FileInfoVO fileInfo2 = new FileInfoVO();
        fileInfo2.setFileName(file2.getOriginalFilename());
        fileInfo2.setContentType(file2.getContentType());
        fileInfo2.setFileSize(file2.getSize());

        List<FileInfoVO> fileInfos = new ArrayList<>();
        fileInfos.add(fileInfo1);
        fileInfos.add(fileInfo2);

        return ResultVO.suc(fileInfos);
    }

}
