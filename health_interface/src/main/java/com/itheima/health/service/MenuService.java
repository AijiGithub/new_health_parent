package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.MyException;
import com.itheima.health.pojo.Menu;

import java.util.List;

public interface MenuService {

    //添加菜单
    void add(Menu menu);

    //查询所有
    List<Menu> findAll();

    //分页条件查询
    PageResult<Menu> findPage(QueryPageBean queryPageBean);

    //通过id删除
    void deleteById(int id) throws MyException;

    //通过id查询
    Menu findById(int id);

    //编辑菜单
    void update(Menu menu);
}
