package com.eden.domain;

import com.eden.annotation.TransferLabel;
import jdk.nashorn.internal.objects.annotations.Getter;

/**
 * @created by eden
 * @since 2019-06-23 14:40:52
 */


public class TargetUserInfo {

    @TransferLabel("岗位类型")
    private String postType;



    @TransferLabel("年龄")
    private Integer age;



    @TransferLabel(isObject = true)
    private EduInfo eduInfo = new EduInfo();


    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }


    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public EduInfo getEduInfo() {
        return eduInfo;
    }

    public void setEduInfo(EduInfo eduInfo) {
        this.eduInfo = eduInfo;
    }

    @Override
    public String toString() {
        return "TargetUserInfo{" +
                "postType='" + postType + '\'' +
                ", age=" + age +
                ", eduInfo=" + eduInfo +
                '}';
    }
}
