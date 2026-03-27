package com.y212318155.helloserver.service;

import com.y212318155.helloserver.common.Result;
import com.y212318155.helloserver.dto.UserDTO;

public interface UserService {
    // 注册方法
    Result<String> register(UserDTO userDTO);
    // 登录方法
    Result<String> login(UserDTO userDTO);
}