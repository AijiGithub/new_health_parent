package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.itheima.health.Utils.QiNiuUtils;
import com.itheima.health.constant.JedisKeyConstant;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author: Eric
 * @since: 2020/10/28
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealMobileController {

    private static final Logger log = LoggerFactory.getLogger(SetmealMobileController.class);

    @Autowired
    private JedisPool jedisPool;

    @Reference
    private SetmealService setmealService;

    /**
     * 套餐列表
     *
     * @return
     */
    @GetMapping("/getSetmeal")
    public Result getSetmeal() {
        Jedis jedis = jedisPool.getResource();
        //从redis读取缓存json数据
        String setmealJson = jedis.get(JedisKeyConstant.GETSETMEAL_LIST_JEDIS_KEY);
        List<Setmeal> setmealList = null;
        //判断缓存数据是否为空
        if (StringUtils.isEmpty(setmealJson)) {
            // 缓存为空去数据库查询套餐列表
            synchronized (SetmealMobileController.class) {//加锁，防止高并发的时候疯狂请求数据库
                setmealJson = jedis.get(JedisKeyConstant.GETSETMEAL_LIST_JEDIS_KEY);
                if (StringUtils.isEmpty(setmealJson)) {
                    setmealList = setmealService.findAll();
                    log.debug("套餐列表缓存为空去数据库读取：" + setmealList);
                    //把数据库中的数据放入Redis中缓存(设置缓存一个月过期，在一定程度上确保Redis和数据库数据一致)
                    jedis.setex(JedisKeyConstant.GETSETMEAL_LIST_JEDIS_KEY, 30 * 24 * 60 * 60, JSON.toJSONString(setmealList));
                }
            }
        } else {
            log.debug("套餐列表从redis缓存中获取数据：" + setmealJson);
            //把缓存中获取的套餐列表json数据转换成实体类
            setmealList = JSON.parseArray(setmealJson, Setmeal.class);
        }
        //关闭jedis
        jedis.close();
        // 前端需要显示图片，要拼接图片的完整路径 java8 stream流操作
        setmealList.forEach(s -> s.setImg(QiNiuUtils.DOMAIN + s.getImg()));
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmealList);
    }

    /**
     * 套餐详情
     */
    @GetMapping("/findDetailById")
    public Result findDetailById(int id) {
        String redisKey = JedisKeyConstant.GETSETMEAL_DETAILS_LIST_JEDIS__KEY + id;
        Jedis jedis = jedisPool.getResource();
        String setmealJson = jedis.get(redisKey);
        Setmeal setmeal = null;
        if (StringUtils.isEmpty(setmealJson)) {
            synchronized (SetmealMobileController.class) {//加锁，防止高并发的时候疯狂请求数据库
                setmealJson = jedis.get(redisKey);
                if (StringUtils.isEmpty(setmealJson)) {
                    // redis缓存为空调用服务查询
                    log.debug("redis套餐详情列表的Redis缓存为空,从数据库读取");
                    setmeal = setmealService.findDetailById(id);
                    //把套餐详情存储到redis里面(设置缓存一个月过期，在一定程度上确保Redis和数据库数据一致)
                    jedis.setex(redisKey, 30 * 24 * 60 * 60, JSON.toJSONString(setmeal));
                }
            }
        } else {
            log.debug("从redis中获取套餐详情列表的数据：" + setmealJson);
            //把redis中缓存的套餐详情列表json转换成实体类对象
            setmeal = JSON.parseObject(setmealJson, Setmeal.class);
        }
        //关闭jedis
        jedis.close();
        // 图片的完整路径
        setmeal.setImg(QiNiuUtils.DOMAIN + setmeal.getImg());
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmeal);
    }

    /**
     * 套餐详情
     */
    @GetMapping("/findDetailById2")
    public Result findDetailById2(int id) {
        // 调用服务查询
        Setmeal s = setmealService.findDetailById2(id);
        // 图片的完整路径
        s.setImg(QiNiuUtils.DOMAIN + s.getImg());
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, s);
    }

    /**
     * 套餐详情
     */
    @GetMapping("/findDetailById3")
    public Result findDetailById3(int id) {
        // 调用服务查询
        Setmeal s = setmealService.findDetailById3(id);
        // 图片的完整路径
        s.setImg(QiNiuUtils.DOMAIN + s.getImg());
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, s);
    }

    /**
     * 套餐基本信息
     */
    @GetMapping("/findById")
    public Result findById(int id) {
        // 调用服务查询
        Setmeal s = setmealService.findById(id);
        // 图片的完整路径
        s.setImg(QiNiuUtils.DOMAIN + s.getImg());
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, s);
    }
}
