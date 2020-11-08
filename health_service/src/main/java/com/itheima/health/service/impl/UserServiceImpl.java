package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.dao.UserDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.MyException;
import com.itheima.health.pojo.Menu;
import com.itheima.health.pojo.Role;
import com.itheima.health.pojo.User;
import com.itheima.health.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public void add(User user,Integer[] roles) {
        userDao.add(user);
        /*user.setId(userDao.getId(user));*/
        if(null!=roles){
            for (Integer role : roles) {
                userDao.addUserRole(user.getId(),role);
            }
        }
    }

    @Override
    public PageResult<User> findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        // 模糊查询 拼接 %
        // 判断是否有查询条件
        if (!StringUtil.isEmpty(queryPageBean.getQueryString())) {
            // 有查询条件，拼接%
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        // 紧接着的查询语句会被分页
        Page<User> page = userDao.findByCondition(queryPageBean.getQueryString());
        // 封装到分页结果对象中
        PageResult<User> pageResult = new PageResult<User>(page.getTotal(), page.getResult());
        return pageResult;
    }

    @Override
    public List<Role> findAll() {
        return userDao.findAll();
    }

    @Override
    public User findById(int userId) {
        return userDao.findById(userId);
    }

    @Override
    public List<Integer> findRoleIdsByUserId(int userId) {
        return userDao.findRoleIdsByUserId(userId);
    }

    @Override
    @Transactional
    public void update(User user, Integer[] roles) {
        userDao.update(user);
        // 删除旧关系
        userDao.deleteUserRole(user.getId());
        // 建立新关系
        if(null != roles){
            for (Integer role : roles) {
                userDao.addUserRole(user.getId(), role);
            }
        }
    }

    @Override
    @Transactional
    public void deleteById(int id) throws MyException {
        int cnt = userDao.findCountByUserId(id);
        //被使用了则不能删除
        if(cnt > 0){
            throw new MyException(MessageConstant.DELETE_USER_FAIL);
        }
        //没使用就可以调用dao删除
        userDao.deleteById(id);
    }
}
