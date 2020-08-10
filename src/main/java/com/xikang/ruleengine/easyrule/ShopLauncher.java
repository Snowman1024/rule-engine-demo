package com.xikang.ruleengine.easyrule;

/**
 * @Description
 * @Author Snowman1024
 * @Date 2020/3/4 14:29
 * @Version 1.0
 **/
public class ShopLauncher {

//    public static void main(String[] args) throws Exception {
//
//        //创建规则1
//        Rule ageRule = new MVELRule()
//                .name("age easyrule")
//                .description("Check if person's age is > 18 and marks the person as adult")
//                .priority(1)
//                .when("person.age > 18")
//                .then("person.setAdult(true);");
//
//        //创建规则2
//        Rule alcoholRule = MVELRuleFactory.createRuleFrom(new FileReader("alcohol-easyrule.yml"));
//
//        Rules rules = new Rules();
//        rules.register(ageRule);
//        rules.register(alcoholRule);
//
//
//        //创建一个Person实例(Fact)
//        Person tom = new Person("Tom", 14);
//
//        //设置参数
//        Facts facts = new Facts();
//        facts.put("person", tom);
//
//        //创建规则执行引擎，并执行规则
//        RulesEngine rulesEngine = new DefaultRulesEngine();
//        System.out.println("Tom: Hi! can I have some Vodka please?");
//        rulesEngine.fire(rules, facts);
//    }
}
