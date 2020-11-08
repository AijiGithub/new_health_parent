package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.Menu;
import com.itheima.health.pojo.Role;
import com.itheima.health.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author: Eric
 * @since: 2020/10/31
 */
public interface UserDao {
    /**
     * 通过用户名查询用户信息，包含角色及权限信息
     * @param username
     * @return
     */
    User findByUsername(String username);

    List<Menu> getMenuByUsername(String username);

    List<Menu> findSecondMenu(Integer id);

    void add(User user);

    void addUserRole(@Param("userId") Integer userId, @Param("roleId") Integer roleId);

    /*Integer getId(User user);*/


    Page<User> findByCondition(String queryString);

    List<Role> findAll();

    User findById(int userId);

    List<Integer> findRoleIdsByUserId(int userId);

    void update(User user);

    void deleteUserRole(Integer id);

    int findCountByUserId(int id);

    void deleteById(int id);
}
