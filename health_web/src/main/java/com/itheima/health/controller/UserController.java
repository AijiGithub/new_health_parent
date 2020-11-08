package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Menu;
import com.itheima.health.pojo.Role;
import com.itheima.health.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>
 *
 * </p>
 *
 * @author: Eric
 * @since: 2020/10/31
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Reference
    private UserService userService;
    /**
     * 获取登陆用户名
     * @return
     */
    @GetMapping("/getUsername")
    public Result getUsername(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("登陆的用户名:" + user.getUsername());
        return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,user.getUsername());
    }


    @GetMapping("/getMenuByUsername")
    public Result getMenuByUsername(String username){
        List<Menu> menu = null;
        try {
            if(null != username){
                menu = userService.getMenuByUsername(username);
                }
            return new Result(true, MessageConstant.GET_MENU_SUCCESS,menu);
        } catch (Exception e) {
            return new Result(false,"查询菜单列表失败");
        }
    }

    @GetMapping("/findAll")
    public Result findAll(){
        // 调用服务查询所有的角色
        List<Role> list = userService.findAll();
        // 封装返回的结果
        return new Result(true, MessageConstant.QUERY_ROLE_SUCCESS,list);
    }
    @PostMapping("/add")
    public Result add(@RequestBody com.itheima.health.pojo.User user, Integer[] roles ){
        // 调用服务添加
        userService.add(user,roles);
        return new Result(true, MessageConstant.ADD_USER_SUCCESS);
    }

    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        // 调用业务来分页，获取分页结果
        PageResult<com.itheima.health.pojo.User> pageResult = userService.findPage(queryPageBean);

        //return pageResult;
        // 返回给页面, 包装到Result
        return new Result(true, MessageConstant.QUERY_USER_SUCCESS,pageResult);
    }

    @GetMapping("/findById")
    public Result findById(int userId){
        // 调用业务服务
        com.itheima.health.pojo.User user = userService.findById(userId);
        return new Result(true, MessageConstant.QUERY_USER_SUCCESS,user);
    }

    @GetMapping("/findRoleIdsByUserId")
    public Result findRoleIdsByUserId(int userId){
        // 调用服务查询
        List<Integer> roleIds = userService.findRoleIdsByUserId(userId);
        return new Result(true, MessageConstant.QUERY_ROLE_SUCCESS,roleIds);
    }

    @PostMapping("/update")
    public Result update(@RequestBody com.itheima.health.pojo.User user, Integer[] roles){
        // 调用业务服务
        userService.update(user, roles);
        // 响应结果
        return new Result(true, MessageConstant.EDIT_USER_SUCCESS);
    }


    @PostMapping("/deleteById")
    public Result deleteById(int id){
        //调用业务服务删除
        userService.deleteById(id);
        return new Result(true, MessageConstant.DELETE_USER_SUCCESS);
    }

}
