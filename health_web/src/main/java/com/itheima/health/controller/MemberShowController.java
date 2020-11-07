package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.MemberMes;
import com.itheima.health.service.MemberShowSerice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author LiuFeng
 * @Date 2020/11/6 12:23
 */
@RestController
@RequestMapping("memberShow")
public class MemberShowController {
    @Reference
    private MemberShowSerice memberShowSerice;

    @RequestMapping("findMemeberMsg")
    public Result findMemeberMsg(){
        MemberMes ageMessage = memberShowSerice.findMemeberMsg();

        return new Result(true,"查询成功",ageMessage);
    }
}
