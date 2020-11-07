package com.itheima.health.dao;

import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @Author LiuFeng
 * @Date 2020/11/6 15:12
 */
public interface MemberShowDao {

   int findMemeberSex(int sexId);

   int findMemeberAge(@Param("age1") int age1 ,@Param("age2") int age2 );
}
