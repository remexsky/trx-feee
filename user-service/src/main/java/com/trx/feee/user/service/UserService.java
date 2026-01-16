package com.trx.feee.user.service;

import com.trx.feee.user.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.trx.feee.user.vo.UserVO;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author TRX
 * @since 2026-01-16
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     * @param username 用户名
     * @param password 密码
     * @param email 邮箱
     * @param phone 手机号
     * @return 注册结果
     */
    boolean register(String username, String password, String email, String phone);

    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户信息
     */
    User findByUsername(String username);

    /**
     * 根据邮箱查询用户
     * @param email 邮箱
     * @return 用户信息
     */
    User findByEmail(String email);

    /**
     * 根据手机号查询用户
     * @param phone 手机号
     * @return 用户信息
     */
    User findByPhone(String phone);

    /**
     * 更新用户信息
     * @param userVO 用户信息
     * @return 更新结果
     */
    boolean updateUserInfo(UserVO userVO);

    /**
     * 重置密码
     * @param userId 用户ID
     * @param newPassword 新密码
     * @return 重置结果
     */
    boolean resetPassword(Long userId, String newPassword);

    /**
     * 启用/禁用用户
     * @param userId 用户ID
     * @param status 用户状态：0-禁用，1-启用
     * @return 操作结果
     */
    boolean updateUserStatus(Long userId, Integer status);

}