package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.itheima.health.dao.PermissionDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.MyException;
import com.itheima.health.pojo.Menu;
import com.itheima.health.pojo.Permission;
import com.itheima.health.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service(interfaceClass = PermissionService.class)
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionDao permissionDao;

    //查询所有权限
    @Override
    public List<Permission> findAll() {
        return permissionDao.findAll();
    }

    //新增权限
    @Override
    public void add(Permission permission) throws MyException{
        //判空
        if (StringUtils.isEmpty(permission.getName())){
            throw new MyException("权限名称不能为空");
        }
        permissionDao.add(permission);
    }

    //分页查询所有权限
    @Override
    public PageResult<Permission> findPage(QueryPageBean queryPageBean) {
        //页码与页码大小
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        //判断是否有查询条件，如果有要实现模糊查询
        if(!StringUtil.isEmpty(queryPageBean.getQueryString())){
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        //条件查询，查询语句会被分页
        Page<Permission> page = permissionDao.findPage(queryPageBean.getQueryString());
        PageResult<Permission> pageResult = new PageResult<Permission>(page.getTotal(), page.getResult());
        return pageResult;

    }


    //通过id删除权限
    @Override
    @Transactional
    public void deleteById(int id) throws MyException{
        //判断是否被角色表使用了
         int count = permissionDao.findRoleByPermission(id);
         if (count > 0){
             throw new MyException("被角色使用了,无法删除");
         }
         permissionDao.deleteById(id);
    }

    //通过id查询要编辑权限信息
    @Override
    public Permission findById(int id) {

        return permissionDao.findById(id);
    }

    //更新权限
    @Override
    public void update(Permission permission) {
          permissionDao.update(permission);
    }
}
