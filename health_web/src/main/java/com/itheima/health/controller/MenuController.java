package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Menu;
import com.itheima.health.service.MenuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Reference
    private MenuService menuService;

    @GetMapping("/findAll")
    public Result findAll(){
     List<Menu> list =  menuService.findAll();
     return new Result(true,MessageConstant.QUERY_MENU_SUCCESS,list);

    }

    //添加菜单
    @PostMapping("/add")
    public Result add(@RequestBody Menu menu){
        //调用服务添加
        menuService.add(menu);
        return new Result(true, MessageConstant.ADD_MENU_SUCCESS);
    }

    //分页查询
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
    //调用服务分页查询
     PageResult<Menu> pageResult = menuService.findPage(queryPageBean);
     return new Result(true,MessageConstant.QUERY_MENU_SUCCESS,pageResult);
    }


    //通过id删除
    @PostMapping("/deleteById")
    public Result deleteById(int id){
        //调用删除业务
        menuService.deleteById(id);
        return new Result(true,MessageConstant.DELETE_MENU_SUCCESS);
    }

    //通过id查询
    @GetMapping("/findById")
    public Result findById(int id){
        //调用查询业务
        Menu menu = menuService.findById(id);
        return new Result(true,MessageConstant.QUERY_MENU_SUCCESS,menu);
    }

    //编辑菜单
    @PostMapping("/update")
    public Result update(@RequestBody Menu menu){
        //调用修改业务
        menuService.update(menu);
        return new Result(true,MessageConstant.EDIT_MENU_SUCCESS);
    }

}
