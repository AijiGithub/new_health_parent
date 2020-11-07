package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.MemberShowDao;
import com.itheima.health.pojo.MemberMes;
import com.itheima.health.service.MemberShowSerice;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @Author LiuFeng
 * @Date 2020/11/6 15:07
 */
@Service(interfaceClass =MemberShowSerice.class)
public class MemberShowSericeImpl implements MemberShowSerice {
    @Autowired
    private MemberShowDao memberShowDao;



    @Override
    public MemberMes findMemeberMsg() {
        MemberMes memberMes = new MemberMes();
        List<Integer> age = new ArrayList<>();
        //定义数组容器装入年龄段
        Collections.addAll(age,0,18,30,45);
        //定义集合装年龄数据结果
        List<Map<String,Object>> ageProperties = new ArrayList<>();
        //定义年龄字段容器
        List<String> ageText = new ArrayList<>();
        //定义性别数据容器
        List<String>sexText = new ArrayList<>();
        Collections.addAll(sexText,"男性会员","女性会员");
        //存储会员性别字段
        memberMes.setSexText(sexText);
        //定义男女数量容器
        List<Map<String, Object>>sexList = new ArrayList<>();
        //循环遍历查找对应性别人数，并装入Map
        for (int i = sexText.size()-1; i >=0; i--) {
            int sexNum = memberShowDao.findMemeberSex(i+1);
            Map<String, Object> sexMap = new HashMap<>();
            sexMap.put("name",sexText.get(i));
            sexMap.put("value",sexNum);
            sexList.add(sexMap);
        }
        //循环遍历查找年龄段人员,并将查找结果装入Map中
        for (int i = 0; i < age.size(); i++) {
            int agenum1 = age.get(i);
            int agenum2 = -1 ;
            if(i!=age.size()-1){
                 agenum2= age.get(i+1);
            }
            int res = memberShowDao.findMemeberAge(agenum1, agenum2);
            //定义Map容器装数据库结果数据
            Map<String, Object> map = new HashMap<>();
            //将数据装入Map中
            if(i!=age.size()-1) {
                map.put("name",  agenum1 + "-" + agenum2 );
                map.put("value", res);
                ageText.add(agenum1 + "-" + agenum2) ;
            }else {
                map.put("name",  agenum1 +"以上");
                map.put("value", res );
                ageText.add(agenum1 +"以上") ;
            }
            //if(res>0){
                    ageProperties.add(map);
          //  }
        }
        //存储年龄属性
        memberMes.setAgeGroup(ageProperties);
        //存储年龄字段
        memberMes.setAgeGroupText(ageText);
        //存储性别字段
        memberMes.setSexText(sexText);
        //存储性别数量
        memberMes.setSexNum(sexList);
        sexText.addAll(ageText);
        //存储所有的属性字段
        memberMes.setAllText(sexText);
        return memberMes;
    }
}
