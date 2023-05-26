package com.example.spring.boot.use.mongo.repository;

import com.example.spring.boot.use.mongo.entity.FileDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * 文件存储（大于16MB）
 *
 * @author minus
 * @since 2023/05/24 20:57
 */
@Repository
public interface FileDocumentRepository extends MongoRepository<FileDocument, String> {
}
