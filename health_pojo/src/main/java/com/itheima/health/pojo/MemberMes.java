package com.itheima.health.pojo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Author LiuFeng
 * @Date 2020/11/7 0:40
 */
public class MemberMes implements Serializable {
    private List<Integer> age;
    private List<Map<String,Object>> ageGroup;
    private List<String>ageGroupText;
    private List<Map<String,Object>> sexNum;
    private List<String> sexText;
    private List<String> allText;

    public List<String> getAllText() {
        return allText;
    }

    public void setAllText(List<String> allText) {
        this.allText = allText;
    }

    public List<Map<String, Object>> getSexNum() {
        return sexNum;
    }

    public void setSexNum(List<Map<String, Object>> sexNum) {
        this.sexNum = sexNum;
    }

    public List<String> getSexText() {
        return sexText;
    }

    public void setSexText(List<String> sexText) {
        this.sexText = sexText;
    }

    public List<Integer> getAge() {
        return age;
    }

    public void setAge(List<Integer> age) {
        this.age = age;
    }

    public List<Map<String, Object>> getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(List<Map<String, Object>> ageGroup) {
        this.ageGroup = ageGroup;
    }

    public List<String> getAgeGroupText() {
        return ageGroupText;
    }

    public void setAgeGroupText(List<String> ageGroupText) {
        this.ageGroupText = ageGroupText;
    }

    @Override
    public String toString() {
        return "MemberMes{" +
                "age=" + age +
                ", ageGroup=" + ageGroup +
                ", ageGroupText=" + ageGroupText +
                ", sexNum=" + sexNum +
                ", sexText=" + sexText +
                ", allText=" + allText +
                '}';
    }
}
