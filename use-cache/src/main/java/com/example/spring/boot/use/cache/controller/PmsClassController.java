package com.example.spring.boot.use.cache.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.spring.boot.use.cache.common.ApiConst;
import com.example.spring.boot.use.cache.entity.PmsClass;
import com.example.spring.boot.use.cache.model.ResultVO;
import com.example.spring.boot.use.cache.service.PmsClassService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 人员管理-班级 前端控制器
 * </p>
 *
 * @author minus
 * @since 2022-11-21
 */
@RestController
@RequestMapping(ApiConst.API_URL + "/pms-class")
@Api(tags = "人员管理-班级")
public class PmsClassController extends BaseController {

    @Autowired
    private PmsClassService pmsClassService;

    @PostMapping
    @ApiOperation(value = "API-01-新增班级")
    public ResultVO<Object> saveClass(@RequestBody PmsClass pmsClass) {
        return pmsClassService.saveClass(pmsClass);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "API-02-删除班级")
    public ResultVO<Object> removeClass(
            @ApiParam("主键id") @PathVariable String id
    ) {
        return pmsClassService.removeClass(id);
    }

    @PutMapping
    @ApiOperation(value = "API-03-更新班级")
    public ResultVO<Object> updateClass(@RequestBody PmsClass pmsClass) {
        return pmsClassService.updateClass(pmsClass);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "API-04-查询班级")
    public ResultVO<PmsClass> getClassById(
            @ApiParam("主键id") @PathVariable String id
    ) {
        return pmsClassService.getClassById(id);
    }

    @GetMapping("/{current}/{size}")
    @ApiOperation(value = "API-05-查询班级分页")
    public ResultVO<Page<PmsClass>> pageClass(
            @ApiParam("页码") @PathVariable Long current,
            @ApiParam("页大小") @PathVariable Long size
    ) {
        return pmsClassService.pageClass(new Page<>(current, size));
    }

}
