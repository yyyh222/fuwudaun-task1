package com.y212318155.helloserver.controller;

import com.y212318155.helloserver.common.Result;
import com.y212318155.helloserver.entity.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    // 查询用户（GET）
    @GetMapping("/{id}")
    public Result<String> getUser(@PathVariable("id") Long id) {
        String data = "查询成功，正在返回 ID 为 " + id + " 的用户信息";
        return Result.success(data);
    }

    // 新增用户（POST）
    @PostMapping
    public Result<String> addUser(@RequestBody User user) {
        String data = "新增成功，接收到用户：" + user.getName() + "，年龄：" + user.getAge();
        return Result.success(data);
    }

    // 更新用户（PUT）
    @PutMapping("/{id}")
    public Result<String> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        String data = "更新成功，用户 ID：" + id + "，新信息：" + user.getName() + "，年龄：" + user.getAge();
        return Result.success(data);
    }

    // 删除用户（DELETE）
    @DeleteMapping("/{id}")
    public Result<String> deleteUser(@PathVariable("id") Long id) {
        String data = "删除成功，用户 ID：" + id;
        return Result.success(data);
    }
}