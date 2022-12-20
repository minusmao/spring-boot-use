package com.example.spring.boot.use.mongo.service;

import com.example.spring.boot.use.mongo.entity.Route;
import org.springframework.data.domain.Page;

/**
 * 道路信息
 *
 * @author minus
 * @since 2022/12/20 20:56
 */
public interface RouteService {

    void saveRoute(Route route);

    void removeRoute(String id);

    Route updateRoute(Route route);

    Route getRouteById(String id);

    Page<Route> pageRoute(Integer page, Integer size, String routeName, String areaCode);

}
