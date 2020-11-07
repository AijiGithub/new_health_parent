package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Permission;
import com.itheima.health.service.PermissionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Reference
    private PermissionService permissionService;

    @GetMapping("/findAll")
    public Result findAll(){
        // 调用服务查询
        List<Permission> list = permissionService.findAll();
        // 封装到Result再返回
        return new Result(true, MessageConstant.QUERY_PERMISSION_SUCCESS,list);
    }

}
