package com.xikang.ruleengine.easyrule;

import lombok.extern.slf4j.Slf4j;
import org.jeasy.rules.annotation.*;
import org.jeasy.rules.support.UnitRuleGroup;

/**
 * @Description 优先级注解：return 数值越小，优先级越高
 * @Author Snowman1024
 * @Date 2020/3/4 13:47
 * @Version 1.0
 **/
@Slf4j
public class RuleClass {


    @Rule(priority = 1, name = "被3整除", description = "number如果被3整除，打印：number is three")
    public static class ThreeRule {

        /**
         * 条件判断注解：如果return true， 执行Action
         */
        @Condition
        public boolean isThree(@Fact("number") int number) {
            return number % 3 == 0;
        }

        /**
         * 执行方法注解
         *
         * @param number
         */
        @Action
        public void threeAction(@Fact("number") int number) {
            log.info("====={} ====:被3整除 ", number);
        }

    }


    @Rule(priority = 2, name = "被8整除", description = "number如果被8整除，打印：number is eight")
    public static class EightRule {

        @Condition
        public boolean isEight(@Fact("number") int number) {
            return number % 8 == 0;
        }

        @Action
        public void eightAction(@Fact("number") int number) {
            log.info("====={} ====:被8整除 ", number);
        }

    }

    @Rule(name = "被3和8同时整除", description = "这是一个组合规则")
    public static class ThreeEightRuleUnitGroup extends UnitRuleGroup {

        public ThreeEightRuleUnitGroup(Object... rules) {
            for (Object rule : rules) {
                addRule(rule);
            }
        }

        @Override
        public int getPriority() {
            return 0;
        }
    }


    @Rule(priority = 3, name = "既不被3整除也不被8整除", description = "打印number自己")
    public static class OtherRule {

        @Condition
        public boolean isOther(@Fact("number") int number) {
            return number % 3 != 0 || number % 8 != 0;
        }

        @Action
        public void printSelf(@Fact("number") int number) {
            log.info("====={} ====:既不被3整除也不被8整除 ", number);

        }

    }


}
