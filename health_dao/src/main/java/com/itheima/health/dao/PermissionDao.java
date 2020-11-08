package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.Permission;

import java.util.List;

public interface PermissionDao {
    //查询所有权限列表
    List<Permission> findAll();

    void add(Permission permission);

    Page<Permission> findPage(String queryString);

    int findRoleByPermission(int id);

    void deleteById(int id);

    Permission findById(int id);

    void update(Permission permission);
}
