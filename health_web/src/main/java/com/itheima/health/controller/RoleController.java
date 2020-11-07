package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Role;
import com.itheima.health.service.RoleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Reference
    private RoleService roleService;

    /**
     * 新增角色
     *
     * @param role
     * @param permissionIds
     * @param menuIds
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody Role role, Integer[] permissionIds, Integer[] menuIds) {
        // 调用服务添加
        roleService.add(role, permissionIds, menuIds);
        return new Result(true, MessageConstant.ADD_ROLE_SUCCESS);
    }

    /**
     * 分页查询角色列表
     *
     * @param queryPageBean
     * @return
     */
    @PostMapping("findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        // 调用服务分页查询
        PageResult<Role> pageResult = roleService.findPage(queryPageBean);
        return new Result(true, MessageConstant.QUERY_ROLE_SUCCESS, pageResult);
    }

    /**
     * 通过角色id查询角色信息
     * @param id
     * @return
     */
    @GetMapping("findByroleId")
    public Result findByroleId(int id) {
        Role role = roleService.findByroleId(id);
        return new Result(true,"通过角色id查询角色信息成功",role);
    }

    /**
     * 构建编辑窗权限列表
     * @param id
     * @return
     */
    @GetMapping("findPermissionIdsByRoleId")
    public Result findPermissionIdsByRoleId(int id) {
        //调用业务查询
        List<Integer> permissionIds = roleService.findPermissionIdsByRoleId(id);
        return new Result(true, "查询角色对应的权限Ids成功", permissionIds);
    }

    /**
     * 构建编辑窗菜单列表
     *
     * @param id
     * @return
     */
    @GetMapping("findMenuIdsByRoleId")
    public Result findMenuIdsByRoleId(int id) {
        //调用业务查询
        List<Integer> menuIds = roleService.findMenuIdsByRoleId(id);
        return new Result(true, "查询角色对应的菜单Ids成功", menuIds);
    }

    /**
     * 提交编辑,修改角色
     * @param role
     * @param permissionIds
     * @param menuIds
     * @return
     */
    @PostMapping("update")
    public Result update(@RequestBody Role role, Integer[] permissionIds, Integer[] menuIds){
        roleService.update(role,permissionIds,menuIds);
        return new Result(true,"修改角色信息成功");
    }
    //通过id删除角色
    @PostMapping("deleteById")
    public Result deleteById(int id){
        roleService.deleteById(id);
        return new Result(true,"删除角色信息成功");
    }
}
