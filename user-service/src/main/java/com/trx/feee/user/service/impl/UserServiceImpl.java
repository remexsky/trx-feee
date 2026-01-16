package com.trx.feee.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trx.feee.user.entity.User;
import com.trx.feee.user.mapper.UserMapper;
import com.trx.feee.user.service.UserService;
import com.trx.feee.user.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author TRX
 * @since 2026-01-16
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 用户注册
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean register(String username, String password, String email, String phone) {
        // 检查用户名是否已存在
        User existingUser = userMapper.selectByUsername(username);
        if (existingUser != null) {
            log.warn("Username already exists: {}", username);
            return false;
        }

        // 检查邮箱是否已存在
        if (StringUtils.hasText(email)) {
            User existingEmail = userMapper.selectByEmail(email);
            if (existingEmail != null) {
                log.warn("Email already exists: {}", email);
                return false;
            }
        }

        // 检查手机号是否已存在
        if (StringUtils.hasText(phone)) {
            User existingPhone = userMapper.selectByPhone(phone);
            if (existingPhone != null) {
                log.warn("Phone already exists: {}", phone);
                return false;
            }
        }

        // 创建新用户
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setPhone(phone);
        user.setStatus(1); // 默认启用
        user.setUserType(0); // 默认普通用户
        user.setNickname(username); // 默认昵称等于用户名
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        int result = userMapper.insert(user);
        return result > 0;
    }

    /**
     * 根据用户名查询用户
     */
    @Override
    public User findByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    /**
     * 根据邮箱查询用户
     */
    @Override
    public User findByEmail(String email) {
        return userMapper.selectByEmail(email);
    }

    /**
     * 根据手机号查询用户
     */
    @Override
    public User findByPhone(String phone) {
        return userMapper.selectByPhone(phone);
    }

    /**
     * 更新用户信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUserInfo(UserVO userVO) {
        User user = userMapper.selectById(userVO.getId());
        if (user == null) {
            log.warn("User not found: {}", userVO.getId());
            return false;
        }

        // 更新用户信息
        BeanUtils.copyProperties(userVO, user, "password", "createdAt");
        user.setUpdatedAt(LocalDateTime.now());

        int result = userMapper.updateById(user);
        return result > 0;
    }

    /**
     * 重置密码
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean resetPassword(Long userId, String newPassword) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            log.warn("User not found: {}", userId);
            return false;
        }

        // 更新密码
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setUpdatedAt(LocalDateTime.now());

        int result = userMapper.updateById(user);
        return result > 0;
    }

    /**
     * 启用/禁用用户
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUserStatus(Long userId, Integer status) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            log.warn("User not found: {}", userId);
            return false;
        }

        // 更新状态
        user.setStatus(status);
        user.setUpdatedAt(LocalDateTime.now());

        int result = userMapper.updateById(user);
        return result > 0;
    }
}