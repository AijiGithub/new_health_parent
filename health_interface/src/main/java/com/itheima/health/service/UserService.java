package com.itheima.health.service;

import com.itheima.health.pojo.Menu;
import com.itheima.health.pojo.User;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author: Eric
 * @since: 2020/10/31
 */
public interface UserService {

    /**
     * 通过用户名查询用户信息，包含角色及权限信息
     * @param username
     * @return
     */
    User findUserByUsername(String username);

    List<Menu> getMenuByUsername(String username);
}
