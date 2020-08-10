package com.xikang.ruleengine.easyrule;

/**
 * @Description
 * @Author Snowman1024
 * @Date 2020/3/4 13:58
 * @Version 1.0
 **/
public class ThreeEightRuleLauncher {

//    public static void main(String[] args) {
//        /**
//         * 创建规则执行引擎
//         * 注意: skipOnFirstAppliedRule意思是，只要匹配到第一条规则就跳过后面规则匹配
//         */
//        RulesEngineParameters parameters = new
//                RulesEngineParameters().skipOnFirstAppliedRule(true);
//        RulesEngine rulesEngine = new DefaultRulesEngine(parameters);
//        //创建规则
//        Rules rules = new Rules();
//        rules.register(new RuleClass.EightRule());
//        rules.register(new RuleClass.ThreeRule());
//        rules.register(new RuleClass.ThreeEightRuleUnitGroup(new RuleClass.EightRule(), new RuleClass.ThreeRule()));
//        rules.register(new RuleClass.OtherRule());
//        Facts facts = new Facts();
//
//        facts.put("number", 24);
//        //执行规则
//        rulesEngine.fire(rules, facts);
//
////        for (int i=1 ; i<=50 ; i++){
////            //规则因素，对应的name，要和规则里面的@Fact 一致
////
////            System.out.println();
////        }
//    }
}
