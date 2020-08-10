package com.xikang.ruleengine.controller;

import com.xikang.ruleengine.vo.Apple;
import com.xikang.ruleengine.vo.Food;
import com.xikang.ruleengine.vo.Fruit;
import com.xikang.ruleengine.vo.Plate;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description
 * @Author Snowman1024
 * @Date 2020/3/16 10:02
 * @Version 1.0
 **/
@Slf4j
public class PECSController {

    public <T> T getOject(Class<T> c) throws IllegalAccessException, InstantiationException {
        T t = c.newInstance();
        return t;
    }


    /**
     * PECS（Producer Extends Consumer Super）
     * o	频繁往外读取内容的，适合用上界Extends。
     * o	经常往里插入的，适合用下界Super。
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            //上边界
            Plate<? extends Fruit> p = new Plate<Apple>(new Apple());
            log.info("p;{}", p);

            //上界<? extends T>不能往里存，只能往外取
//            p.setItem(new Fruit());
//            p.setItem(new Apple());
            Fruit pt = p.getItem();
//            Apple pa = p.getItem();


            //下边界
            Plate<? super Fruit> t = new Plate<Food>(new Food());
            log.info("t;{}", t);

            //下界<? super T>不影响往里存，但往外取只能放在Object对象里
            t.setItem(new Fruit());
            t.setItem(new Apple());
//            Fruit nf = t.getItem();
//            Apple na = t.getItem();
            Object no = t.getItem();


        } catch (Exception e) {
            log.error(e.toString());
        } finally {
            log.info("finally:{}");
        }

    }

}
