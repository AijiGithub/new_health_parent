package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.MyException;
import com.itheima.health.pojo.Role;

import java.util.List;

public interface RoleService {

//------添加角色------//
    void add(Role role, Integer[] permissionIds, Integer[] menuIds) throws MyException;

//----------分页查询角色列表-----------//
    PageResult<Role> findPage(QueryPageBean queryPageBean);

//-----------编辑角色-------------//
    //构建编辑窗权限列表
    List<Integer> findPermissionIdsByRoleId(int id);

    //构建编辑窗菜单列表
    List<Integer> findMenuIdsByRoleId(int id);

    //通过角色id查询角色信息
    Role findByroleId(int roleId);

    //提交编辑,修改角色
    void update(Role role, Integer[] permissionIds, Integer[] menuIds) throws MyException;

//------------通过id删除角色------------//
    void deleteById(int id) throws MyException;
}
