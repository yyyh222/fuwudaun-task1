package com.y212318155.helloserver.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.y212318155.helloserver.dto.UserDTO;
import com.y212318155.helloserver.entity.User;
import com.y212318155.helloserver.entity.UserInfo;
import com.y212318155.helloserver.mapper.UserInfoMapper;
import com.y212318155.helloserver.mapper.UserMapper;
import com.y212318155.helloserver.service.UserService;
import com.y212318155.helloserver.util.JwtUtil;
import com.y212318155.helloserver.util.Result;
import com.y212318155.helloserver.vo.UserDetailVO;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private JwtUtil jwtUtil;

    private static final String CACHE_KEY_PREFIX = "user:detail:";

    // 注册
    @Override
    public Result<String> register(UserDTO dto) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, dto.getUsername());
        User exist = userMapper.selectOne(wrapper);
        if (exist != null) {
            return Result.error("用户名已存在");
        }
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        userMapper.insert(user);
        return Result.success("注册成功");
    }

    // 登录（实验9 JWT 最终版）
    @Override
    public Result<String> login(UserDTO dto) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, dto.getUsername());
        User user = userMapper.selectOne(wrapper);

        if (user == null) {
            return Result.error("用户不存在");
        }
        if (!user.getPassword().equals(dto.getPassword())) {
            return Result.error("密码错误");
        }

        // 生成 JWT 令牌（和我给你的 JwtUtil 完全匹配）
        String token = jwtUtil.generateToken(user.getUsername());
        return Result.success(token);
    }

    // 根据ID查用户
    @Override
    public Result<String> getUserById(Long id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            return Result.error("用户不存在");
        }
        return Result.success("查询成功，用户ID：" + id);
    }

    // 分页
    @Override
    public Result<Object> getUserPage(Integer pageNum, Integer pageSize) {
        Page<User> page = new Page<>(pageNum, pageSize);
        userMapper.selectPage(page, null);
        return Result.success(page);
    }

    // ===================== 实验7 Redis & 多表 =====================

    // 查询用户详情
    @Override
    public Result<UserDetailVO> getUserDetail(Long userId) {
        String key = CACHE_KEY_PREFIX + userId;

        String json = stringRedisTemplate.opsForValue().get(key);
        if (json != null && !json.isBlank()) {
            try {
                UserDetailVO vo = JSONUtil.toBean(json, UserDetailVO.class);
                return Result.success(vo);
            } catch (Exception e) {
                stringRedisTemplate.delete(key);
            }
        }

        UserDetailVO detail = userInfoMapper.getUserDetail(userId);
        if (detail == null) {
            return Result.error("用户不存在");
        }

        stringRedisTemplate.opsForValue().set(
                key,
                JSONUtil.toJsonStr(detail),
                10,
                TimeUnit.MINUTES
        );

        return Result.success(detail);
    }

    // 更新用户信息
    @Override
    @Transactional
    public Result<String> updateUserInfo(UserInfo userInfo) {
        if (userInfo == null || userInfo.getUserId() == null) {
            return Result.error("参数错误");
        }

        LambdaQueryWrapper<UserInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserInfo::getUserId, userInfo.getUserId());
        userInfoMapper.update(userInfo, wrapper);

        stringRedisTemplate.delete(CACHE_KEY_PREFIX + userInfo.getUserId());
        return Result.success("更新成功");
    }

    // 删除用户
    @Override
    @Transactional
    public Result<String> deleteUser(Long userId) {
        userMapper.deleteById(userId);

        LambdaQueryWrapper<UserInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserInfo::getUserId, userId);
        userInfoMapper.delete(wrapper);

        stringRedisTemplate.delete(CACHE_KEY_PREFIX + userId);
        return Result.success("删除成功");
    }
}