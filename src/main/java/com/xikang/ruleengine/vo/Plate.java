package com.xikang.ruleengine.vo;

/**
 * @Description
 * @Author Snowman1024
 * @Date 2020/3/16 10:03
 * @Version 1.0
 **/
public class Plate<T> {

    private T item;

    public Plate(T t) {
        this.item = t;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }
}
