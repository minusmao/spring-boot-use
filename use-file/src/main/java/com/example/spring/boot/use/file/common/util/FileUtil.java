package com.example.spring.boot.use.file.common.util;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

/**
 * 文件相关工具类
 *
 * @author minus
 * @since 2022/11/23 22:21
 */
public class FileUtil {

    /**
     * 文件保存到指定路径
     *
     * @param file        文件
     * @param fileDirPath 文件目录路径
     * @return 保存结果
     */
    public static boolean fileWrite(MultipartFile file, String fileDirPath) {
        // 生成文件名和路径
        String filename = UUID.randomUUID().toString();
        // 获得文件后缀
        filename += '.' + FilenameUtils.getExtension(file.getOriginalFilename());
        String filePath = fileDirPath + "/" + filename;
        // 创建文件夹
        File fileDir = new File(fileDirPath);
        if (!fileDir.exists()) {
            if (!fileDir.mkdirs()) {
                return false;
            }
        }

        // 存入文件
        try {
            file.transferTo(new File(filePath));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
