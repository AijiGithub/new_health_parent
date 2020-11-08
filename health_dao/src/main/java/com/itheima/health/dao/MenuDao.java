package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.Menu;

import java.util.List;

public interface MenuDao {

    //添加菜单
    void add(Menu menu);

    //查询所有
    List<Menu> findAll();

    //条件查询
    Page<Menu> findPage(String queryString);

    //提供根据菜单的id查询角色表与菜单关系表的个数
    int findRoleByMenu(int id);

    //通过id删除
    void deleteById(int id);

    //通过id查询111
    Menu findById(int id);

    //编辑菜单
    void update(Menu menu);
}
