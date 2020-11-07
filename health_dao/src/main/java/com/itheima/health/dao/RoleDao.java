package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleDao {
//-------------添加角色-------------//
    void add(Role role);
    //添加角色与权限的关系
    void addRolePermission(@Param("roleId") Integer roleId, @Param("permissionId")Integer permissionId);
    //添加角色与菜单的关系
    void addRoleMenu(@Param("roleId")Integer roleId,@Param("menuId") Integer menuId);


//-------------分页模糊查询----------------//
    Page<Role> findByCondition(String queryString);

//------------编辑------------------//
    //构建编辑窗权限id
    List<Integer> findPermissionIdsByRoleId(int roleId);
    //构建编辑窗菜单id
    List<Integer> findMenuIdsByRoleId(int roleId);

    //通过角色id查询角色信息
    Role findByroleId(int id);

//--------更新角色信息------------//
    void update(Role role);
    //删除旧权限,旧菜单
    void deleteRolePermission(Integer id);
    void deleteRoleMenu(Integer id);

//------------删除角色------------//
    // 判断是否被用户使用了
    int findCountByRoleId(int id);
    //通过id删除角色
    void deleteById(int id);
}
