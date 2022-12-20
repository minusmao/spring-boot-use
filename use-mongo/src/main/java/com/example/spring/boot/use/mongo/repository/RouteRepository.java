package com.example.spring.boot.use.mongo.repository;

import com.example.spring.boot.use.mongo.entity.Route;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * 道路信息
 *
 * @author minus
 * @since 2022/12/20 20:50
 */
@Repository
public interface RouteRepository extends MongoRepository<Route, String> {

    Route findRouteById(String id);

    /*
        笔记：?0 -> 取第一个参数
             ?#{表达式} -> 取表达式结果，在{}中可以通过[0]取第一个参数
             {$exists:true} -> 查询存在该字段的记录
             {$regex:[0]} -> 模糊匹配[0]
        参考链接：https://www.jianshu.com/p/24a44c4c7651
        参考链接：https://blog.csdn.net/zhj_1121/article/details/108230827
     */
    @Query(value = "{$and: [" +
            "{'routeName': ?#{([0] == null) or ([0].length() == 0) ? {$exists:true} : {$regex:[0]}}}," +
            "{'areaCode': ?#{([1] == null) or ([1].length() == 0) ? {$exists:true} : [1]}}" +
            "]}")
    Page<Route> findAll(String routeName, String areaCode, PageRequest pageRequest);

}
