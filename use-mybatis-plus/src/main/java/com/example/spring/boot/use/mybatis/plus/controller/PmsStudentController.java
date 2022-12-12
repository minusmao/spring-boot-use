package com.example.spring.boot.use.mybatis.plus.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.spring.boot.use.mybatis.plus.common.ApiConst;
import com.example.spring.boot.use.mybatis.plus.entity.PmsStudent;
import com.example.spring.boot.use.mybatis.plus.model.ResultVO;
import com.example.spring.boot.use.mybatis.plus.service.PmsStudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 人员管理-学生 前端控制器
 * </p>
 *
 * @author minus
 * @since 2022-11-21
 */
@RestController
@RequestMapping(ApiConst.API_URL + "/pms-student")
@Api(tags = "人员管理-学生")
public class PmsStudentController extends BaseController {

    @Autowired
    private PmsStudentService pmsStudentService;

    @PostMapping
    @ApiOperation(value = "API-01-新增学生")
    public ResultVO<Object> saveStudent(@RequestBody PmsStudent pmsStudent) {
        pmsStudentService.saveStudent(pmsStudent);
        return ResultVO.suc();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "API-02-删除学生")
    public ResultVO<Object> removeStudent(
            @ApiParam("主键id") @PathVariable String id
    ) {
        pmsStudentService.removeStudent(id);
        return ResultVO.suc();
    }

    @PutMapping
    @ApiOperation(value = "API-03-更新学生")
    public ResultVO<PmsStudent> updateStudent(@RequestBody PmsStudent pmsStudent) {
        return ResultVO.suc(pmsStudentService.updateStudent(pmsStudent));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "API-04-查询学生")
    public ResultVO<PmsStudent> getStudentById(
            @ApiParam("主键id") @PathVariable String id
    ) {
        return ResultVO.suc(pmsStudentService.getStudentById(id));
    }

    @GetMapping("/{current}/{size}")
    @ApiOperation(value = "API-05-查询学生分页")
    public ResultVO<Page<PmsStudent>> pageStudent(
            @ApiParam("页码") @PathVariable Long current,
            @ApiParam("页大小") @PathVariable Long size
    ) {
        return ResultVO.suc(pmsStudentService.pageStudent(new Page<>(current, size)));
    }

}
