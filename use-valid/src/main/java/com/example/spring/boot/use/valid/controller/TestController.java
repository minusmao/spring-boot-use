package com.example.spring.boot.use.valid.controller;

import com.example.spring.boot.use.valid.common.ApiConst;
import com.example.spring.boot.use.valid.common.valid.group.GroupSave;
import com.example.spring.boot.use.valid.common.valid.group.GroupUpdate;
import com.example.spring.boot.use.valid.entity.PmsPerson;
import com.example.spring.boot.use.valid.model.ResultVO;
import com.example.spring.boot.use.valid.util.ValidUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * 测试
 *
 * @author minus
 * @since 2022/12/5 13:45
 */
@RestController
@RequestMapping(ApiConst.API_URL + "/test")
@Validated    // 支持方法参数校验
@Api(tags = "测试")
public class TestController {

    @Autowired
    private ValidUtil validUtil;

    @PostMapping
    @ApiOperation(value = "API-01-测试实体参数校验（GroupSave分组）")
    public ResultVO<PmsPerson> testObjectSave(
            @Validated({GroupSave.class}) @RequestBody PmsPerson pmsPerson
    ) {
        return ResultVO.suc(pmsPerson);
    }

    @PutMapping
    @ApiOperation(value = "API-02-测试实体参数校验（GroupUpdate分组）")
    public ResultVO<PmsPerson> testObjectUpdate(
            @Validated({GroupUpdate.class}) @RequestBody PmsPerson pmsPerson
    ) {
        return ResultVO.suc(pmsPerson);
    }

    @PostMapping("/doValidate")
    @ApiOperation(value = "API-03-测试手动执行实体参数校验")
    public ResultVO<PmsPerson> testDoValidate(
            @RequestBody PmsPerson pmsPerson
    ) {
        if (validUtil.isValidate(pmsPerson, GroupSave.class)) {
            return ResultVO.suc(pmsPerson);
        } else {
            List<String> messages = validUtil.doValidateForMessages(pmsPerson, GroupSave.class);
            for (String message : messages) {
                System.out.println(message);
            }
            return ResultVO.fail(400, messages.get(0), pmsPerson);
        }
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "API-04-测试方法参数校验")
    public ResultVO<String> testMethod(
            @ApiParam("主键id") @PathVariable @Length(min = 1, max = 10) String id,
            @ApiParam("分组id") @RequestParam @Size(min = 1, max = 10, message = "分组id长度需要在1和10之间") String groupId
    ) {
        return ResultVO.suc(id + groupId);
    }

}
