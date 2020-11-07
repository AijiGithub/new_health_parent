package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.itheima.health.dao.MenuDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.MyException;
import com.itheima.health.pojo.Menu;
import com.itheima.health.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(interfaceClass = MenuService.class)
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDao menuDao;

    //添加菜单
    @Override
    public void add(Menu menu) {
        menuDao.add(menu);
    }

    //查询所有
    @Override
    public List<Menu> findAll() {
        return menuDao.findAll();
    }

    //分页条件查询
    @Override
    public PageResult<Menu> findPage(QueryPageBean queryPageBean) {
        //页码与页码大小
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        //判断是否有查询条件，如果有要实现模糊查询
        if(!StringUtil.isEmpty(queryPageBean.getQueryString())){
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        //条件查询，查询语句会被分页
        Page<Menu> page = menuDao.findPage(queryPageBean.getQueryString());
        PageResult<Menu> pageResult = new PageResult<Menu>(page.getTotal(), page.getResult());
        return pageResult;
    }



    //通过id删除
    @Override
    public void deleteById(int id) {
        //判断是否被角色表使用了
        int count = menuDao.findRoleByMenu(id);
        if(count > 0){
            throw new MyException("该菜单被使用了，不能删除！！");
        }
        //没有使用，可以删除
        menuDao.deleteById(id);
    }

    //通过id查询
    @Override
    public Menu findById(int id) {
        return menuDao.findById(id);
    }

    //编辑菜单
    @Override
    public void update(Menu menu) {
        menuDao.update(menu);
    }
}
