package com.example.spring.boot.use.mongo.service.impl;

import com.example.spring.boot.use.mongo.entity.Route;
import com.example.spring.boot.use.mongo.repository.RouteRepository;
import com.example.spring.boot.use.mongo.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

/**
 * 道路信息
 *
 * @author minus
 * @since 2022/12/20 20:56
 */
@Service
public class RouteServiceImpl implements RouteService {

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void saveRoute(Route route) {
//        routeRepository.insert(route);    // insert()插入，如果id重复则报错

        mongoTemplate.insert(route);    // insert()插入，如果id重复则报错
    }

    @Override
    public void removeRoute(String id) {
//        routeRepository.deleteById(id);

        mongoTemplate.remove(new Query(Criteria.where("_id").is(id)), Route.class);
    }

    @Override
    public Route updateRoute(Route route) {
//        return routeRepository.save(route);    // save()插入或更新，如果存在实体类的id则更新

//        return mongoTemplate.save(route);    // save()插入或更新，如果存在实体类的id则更新
        // upsert()插入或更新，如果匹配到id则更新
        mongoTemplate.upsert(
                new Query(Criteria.where("_id").is(route.getId())),
                new Update().set("length", route.getLength()),
                Route.class
        );
        return route;
    }

    @Override
    public Route getRouteById(String id) {
//        return routeRepository.findRouteById(id);

        return mongoTemplate.findById(id, Route.class);
    }

    @Override
    public Page<Route> pageRoute(Integer page, Integer size, String routeName, String areaCode) {
        // 创建分页对象
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "updateTime"));
        return routeRepository.findAll(routeName, areaCode, pageRequest);
    }

}
