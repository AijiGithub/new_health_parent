package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Permission;
import com.itheima.health.service.PermissionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Reference
    private PermissionService permissionService;

    @GetMapping("/findAll")
    public Result findAll() {
        // 调用服务查询
        List<Permission> list = permissionService.findAll();
        // 封装到Result再返回
        return new Result(true, MessageConstant.QUERY_PERMISSION_SUCCESS, list);
    }

    //添加权限
    @PostMapping("/add")
    public Result add(@RequestBody Permission permission) {
        //调用服务添加
        permissionService.add(permission);
        return new Result(true, MessageConstant.ADD_PERMISSION_SUCCESS);
    }

    //分页查询
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        //调用服务分页查询
        PageResult<Permission> pageResult = permissionService.findPage(queryPageBean);
        return new Result(true, MessageConstant.QUERY_PERMISSION_SUCCESS, pageResult);
    }


    //通过id删除
    @PostMapping("/deleteById")
    public Result deleteById(int id) {
        //调用删除业务
        permissionService.deleteById(id);
        return new Result(true, MessageConstant.DELETE_PERMISSION_SUCCESS);
    }

    //通过id查询
    @GetMapping("/findById")
    public Result findById(int id) {
        //调用查询业务
        Permission permission = permissionService.findById(id);
        return new Result(true, MessageConstant.QUERY_PERMISSION_SUCCESS, permission);
    }

    //编辑权限
    @PostMapping("/update")
    public Result update(@RequestBody Permission permission) {
        //调用修改业务
        permissionService.update(permission);
        return new Result(true, MessageConstant.EDIT_PERMISSION_SUCCESS);
    }


}
