package com.example.spring.boot.use.mongo.controller;

import com.example.spring.boot.use.mongo.common.ApiConst;
import com.example.spring.boot.use.mongo.entity.Route;
import com.example.spring.boot.use.mongo.model.ResultVO;
import com.example.spring.boot.use.mongo.service.RouteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * 道路信息
 *
 * @author minus
 * @since 2022/12/20 20:57
 */
@RestController
@RequestMapping(ApiConst.API_URL + "/route")
@Api(tags = "道路信息")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @PostMapping
    @ApiOperation(value = "API-01-新增道路信息")
    public ResultVO<Object> saveRoute(@RequestBody Route route) {
        routeService.saveRoute(route);
        return ResultVO.suc();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "API-02-删除道路信息")
    public ResultVO<Object> removeRoute(
            @ApiParam("主键id") @PathVariable String id
    ) {
        routeService.removeRoute(id);
        return ResultVO.suc();
    }

    @PutMapping
    @ApiOperation(value = "API-03-更新道路信息")
    public ResultVO<Route> updateRoute(@RequestBody Route route) {
        return ResultVO.suc(routeService.updateRoute(route));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "API-04-查询道路信息")
    public ResultVO<Route> getRouteById(
            @ApiParam("主键id") @PathVariable String id
    ) {
        return ResultVO.suc(routeService.getRouteById(id));
    }

    @GetMapping("/{page}/{size}")
    @ApiOperation(value = "API-05-查询道路信息分页")
    public ResultVO<Page<Route>> pageRoute(
            @ApiParam("页码") @PathVariable Integer page,
            @ApiParam("页大小") @PathVariable Integer size,
            @ApiParam("道路名称") @RequestParam(required = false) String routeName,
            @ApiParam("行政区划代码") @RequestParam(required = false) String areaCode
    ) {
        return ResultVO.suc(routeService.pageRoute(page, size, routeName, areaCode));
    }

}
