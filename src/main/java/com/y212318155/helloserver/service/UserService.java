package com.y212318155.helloserver.service;

import com.y212318155.helloserver.dto.UserDTO;
import com.y212318155.helloserver.entity.UserInfo;
import com.y212318155.helloserver.util.Result;
import com.y212318155.helloserver.vo.UserDetailVO;

public interface UserService {
    Result<String> register(UserDTO dto);
    Result<String> login(UserDTO dto);
    Result<String> getUserById(Long id);
    Result<UserDetailVO> getUserDetail(Long userId);
    Result<String> updateUserInfo(UserInfo userInfo);
    Result<String> deleteUser(Long userId);
    Result<Object> getUserPage(Integer pageNum, Integer pageSize);
}