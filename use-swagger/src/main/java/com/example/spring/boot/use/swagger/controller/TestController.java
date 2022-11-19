package com.example.spring.boot.use.swagger.controller;

import com.example.spring.boot.use.swagger.common.ApiConst;
import com.example.spring.boot.use.swagger.common.model.ResultVO;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

/**
 * 测试
 *
 * @author minus
 * @since 2022/11/19 22:00
 */
@RestController
@RequestMapping(ApiConst.API_URL + "/test")
@Api(tags = "测试")
public class TestController {

    @GetMapping("/get1/{id}")
    @ApiOperation(value = "API-01-第一种接口参数注释")
    public ResultVO<Object> testGet1(@ApiParam("主键id") @PathVariable String id) {
        return ResultVO.suc(id);
    }

    @GetMapping("/get2/{id}")
    @ApiOperation(value = "API-02-第二种接口参数注释")
    @ApiImplicitParam(name = "id", value = "主键id")
    public ResultVO<Object> testGet2(@PathVariable String id) {
        return ResultVO.fail(400, id);
    }

    @GetMapping("/get3/{id}")
    @ApiOperation(value = "API-03-第三种接口参数注释")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键id"),
            @ApiImplicitParam(name = "param1", value = "参数"),
            @ApiImplicitParam(name = "param2", value = "参数")
    })
    public ResultVO<Object> testGet3(
            @PathVariable String id,
            @RequestParam(defaultValue = "1") String param1,
            @RequestParam String param2
    ) {
        return ResultVO.fail(400, id + param1 + param2);
    }

}
