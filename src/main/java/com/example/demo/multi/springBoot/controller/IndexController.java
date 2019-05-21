package com.example.demo.multi.springBoot.controller;

import com.example.demo.multi.springBoot.entity.User;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

/**
 *
 *
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/8/12 18:49
 * @modefied:
 */
@RestController
@RequestMapping("/")
@Api(tags = "主入口", produces = "application/json,application/xml", hidden = false, description = "详细描述")
public class IndexController {
    private static final String MSG_APP_WELCOME = "欢迎来到Spring-Boot的世界！";

    @GetMapping("/id/{id}")
    @ApiOperation(value = "简述", notes = "详细描述（赘述）", consumes = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "参数描述", allowableValues = "1,2,3,4", required = true,
            dataType = "int", paramType = "path", example = "10", defaultValue = "2")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功")
    })
    public String index(@PathVariable Integer id){
        return MSG_APP_WELCOME;
    }

    @PostMapping("/user")
    @ApiOperation(value = "简述user", notes = "详细描述（赘述）user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user", value = "user参数描述", required = true,
                    dataType = "com.example.demo.multi.springBoot.entity.User", paramType = "body", examples =
                    @Example({
                           @ExampleProperty(mediaType = "id", value = "1"),
                           @ExampleProperty(mediaType = "name", value = "Alisa"),
                           @ExampleProperty(mediaType = "male", value = "false"),
                    })
            )
    })
    public User post(@RequestBody User user){
        return user;
    }
}
