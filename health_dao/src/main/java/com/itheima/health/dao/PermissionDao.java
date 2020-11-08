package com.itheima.health.dao;

import com.itheima.health.pojo.Permission;

import java.util.List;

public interface PermissionDao {
    //查询所有权限列表
    List<Permission> findAll();
}
