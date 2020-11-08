package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.MyException;
import com.itheima.health.pojo.Permission;

import java.util.List;

public interface PermissionService {
    //查询所有权限
    List<Permission> findAll();

    //新增权限
    void add(Permission permission) throws MyException;

    //分页查询所有权限
    PageResult<Permission> findPage(QueryPageBean queryPageBean);

    //通过id删除权限
    void deleteById(int id) throws MyException;

    //通过id查询要编辑权限信息
    Permission findById(int id);

    //更新权限
    void update(Permission permission);
}
