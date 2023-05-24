package com.example.spring.boot.use.mongo.repository;

import com.example.spring.boot.use.mongo.entity.FileModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * 文件存储（小于16MB）
 *
 * @author minus
 * @since 2023/05/19 20:57
 */
@Repository
public interface FileModelRepository extends MongoRepository<FileModel, String> {
}
