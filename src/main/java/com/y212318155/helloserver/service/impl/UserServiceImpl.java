package com.y212318155.helloserver.service.impl;

import com.y212318155.helloserver.common.Result;
import com.y212318155.helloserver.common.ResultCode;
import com.y212318155.helloserver.dto.UserDTO;
import com.y212318155.helloserver.service.UserService;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private static final Map<String, UserDTO> userMap = new HashMap<>();

    @Override
    public Result<String> register(UserDTO dto) {
        if (userMap.containsKey(dto.getUsername())) {
            return Result.error(ResultCode.USER_HAS_EXISTED);
        }
        userMap.put(dto.getUsername(), dto);
        return Result.success("注册成功");
    }

    @Override
    public Result<String> login(UserDTO dto) {
        UserDTO user = userMap.get(dto.getUsername());
        if (user == null) {
            return Result.error(ResultCode.USER_NOT_EXIST);
        }
        if (!user.getPassword().equals(dto.getPassword())) {
            return Result.error(ResultCode.PASSWORD_ERROR);
        }

        // 登录成功，返回 token
        String token = "token-" + dto.getUsername();
        return Result.success(token);
    }
}