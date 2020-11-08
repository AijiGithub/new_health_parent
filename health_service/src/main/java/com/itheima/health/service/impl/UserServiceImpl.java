package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.UserDao;
import com.itheima.health.pojo.Menu;
import com.itheima.health.pojo.User;
import com.itheima.health.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author: Eric
 * @since: 2020/10/31
 */
@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 通过用户名查询用户信息，包含角色及权限信息
     * @param username
     * @return
     */
    @Override
    public User findUserByUsername(String username) {
        return userDao.findByUsername(username);
    }

//   通过用户名查询用户可访问菜单
    @Override
    public List<Menu> getMenuByUsername(String username) {
       List<Menu> menus = userDao.getMenuByUsername(username);
       if(null != menus){ for (Menu menu : menus) {
           List<Menu> secondMenu = userDao.findSecondMenu(menu.getId());
           menu.setChildren(secondMenu);
         }
       }
        return menus;
    }
}
