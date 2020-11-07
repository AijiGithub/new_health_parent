package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.RoleDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.MyException;
import com.itheima.health.pojo.Role;
import com.itheima.health.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service(interfaceClass = RoleService.class)
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    //添加角色
    @Override
    @Transactional
    public void add(Role role, Integer[] permissionIds, Integer[] menuIds) throws MyException{
        if (StringUtils.isEmpty(role.getName()) || StringUtils.isEmpty(role.getKeyword())){
            throw new MyException("角色名称或角色关键字不能为空!");
        }
        roleDao.add(role);
        // 获取权限id
        Integer roleId = role.getId();

        // 遍历选中的权限ids
        if (null != permissionIds) {
            // 添加角色与权限的关系
            for (Integer permissionId : permissionIds) {
                roleDao.addRolePermission(roleId, permissionId);
            }
        }
        if (null != menuIds) {
            //添加角色与菜单的关系
            for (Integer menuId : menuIds) {
                roleDao.addRoleMenu(roleId, menuId);
            }
        }
    }

    //分页查询角色信息
    @Override
    public PageResult<Role> findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        if (!StringUtils.isEmpty(queryPageBean.getQueryString())) {
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        Page<Role> page = roleDao.findByCondition(queryPageBean.getQueryString());
        PageResult<Role> pageResult = new PageResult<Role>(page.getTotal(), page.getResult());
        return pageResult;
    }

    //构建编辑窗权限列表
    @Override
    public List<Integer> findPermissionIdsByRoleId(int id) {
        return roleDao.findPermissionIdsByRoleId(id);
    }

    //构建编辑窗菜单列表
    @Override
    public List<Integer> findMenuIdsByRoleId(int id) {
        return roleDao.findMenuIdsByRoleId(id);
    }

    //通过角色id查询角色信息
    @Override
    public Role findByroleId(int id) {
        return roleDao.findByroleId(id);
    }

    //提交编辑,修改角色
    @Override
    @Transactional//添加事务控制
    public void update(Role role, Integer[] permissionIds, Integer[] menuIds) throws MyException{
        //判空
        if (StringUtils.isEmpty(role.getName()) || StringUtils.isEmpty(role.getKeyword())){
            throw new MyException("角色名称或角色关键字不能为空!");
        }
        //删除旧权限,旧菜单,添加新关系
        roleDao.deleteRolePermission(role.getId());
        roleDao.deleteRoleMenu(role.getId());
        //添加新关系权限
        if (null != permissionIds) {
            for (Integer permissionId : permissionIds) {
                roleDao.addRolePermission(role.getId(), permissionId);
            }
        }
        //添加新关系菜单
        if (null != menuIds) {
            for (Integer menuId : menuIds) {
                roleDao.addRoleMenu(role.getId(), menuId);
            }
        }
        //修改角色信息

        roleDao.update(role);
    }

    @Override
    @Transactional
    public void deleteById(int id) throws MyException {
        // 判断是否被用户使用了
        int count = roleDao.findCountByRoleId(id);
        //被使用,给提示
        if (count > 0) {
            //自定义异常
            throw new MyException("给角色已经被用户使用,无法删除");
        }
        //没使用
        //先删除关系
        roleDao.deleteRolePermission(id);
        roleDao.deleteRoleMenu(id);
        //删除角色信息
        roleDao.deleteById(id);
    }
}


