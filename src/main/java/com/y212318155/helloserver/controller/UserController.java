package com.y212318155.helloserver.controller;

import com.y212318155.helloserver.common.Result;
import com.y212318155.helloserver.dto.UserDTO;
import com.y212318155.helloserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    // 依赖注入 UserService
    @Autowired
    private UserService userService;

    // 1. 注册接口：POST /api/users
    @PostMapping
    public Result<String> register(@RequestBody UserDTO userDTO) {
        return userService.register(userDTO);
    }

    // 2. 登录接口：POST /api/users/login
    @PostMapping("/login")
    public Result<String> login(@RequestBody UserDTO userDTO) {
        return userService.login(userDTO);
    }

    // 3. 获取用户信息（测试用）：GET /api/users/{id}
    @GetMapping("/{id}")
    public Result<String> getUser(@PathVariable("id") Long id) {
        return Result.success("查询成功，正在返回 ID 为 " + id + " 的用户信息");
    }
}