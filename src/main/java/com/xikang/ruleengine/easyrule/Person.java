package com.xikang.ruleengine.easyrule;

import lombok.Data;

/**
 * @Description
 * @Author Snowman1024
 * @Date 2020/3/4 14:29
 * @Version 1.0
 **/
@Data
public class Person {

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    private String name;

    private boolean adult;

    private int age;
}
