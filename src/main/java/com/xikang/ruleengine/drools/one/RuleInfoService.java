package com.xikang.ruleengine.drools.one;

import com.xikang.ruleengine.common.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description 规则信息业务
 * @Author Snowman1024
 * @Date 2020/3/4 17:28
 * @Version 1.0
 **/
@Slf4j
@Service
public class RuleInfoService {

    /**
     * 获取给定场景下的规则信息列表
     *
     * @param sceneCode 场景Code
     * @return 规则列表
     */
    public List<RuleInfo> getRuleInfoListBySceneId(String sceneCode) {
        Map<String, List<RuleInfo>> sceneId2RuleInfoListMap = getRuleInfoListMap();
        return sceneId2RuleInfoListMap.get(sceneCode);
    }

    /**
     * 获取场景与规则信息列表的Map
     *
     * @return 场景规则信息列表Map，Map(sceneId : List < RuleInfo >)
     */
    public Map<String, List<RuleInfo>> getRuleInfoListMap() {
        Map<String, List<RuleInfo>> sceneId2RuleInfoListMap = new HashMap<>();
        List<RuleInfo> allRuleInfos = generateRuleInfoList();
        for (RuleInfo ruleInfo : allRuleInfos) {
            List<RuleInfo> ruleInfos = sceneId2RuleInfoListMap.computeIfAbsent(ruleInfo.getSceneCode(), k -> new ArrayList<>());
            ruleInfos.add(ruleInfo);
        }
        return sceneId2RuleInfoListMap;
    }

    /**
     * 生成规则信息列表，注意场景id和规则id的对应关系
     *
     * @return 规则信息列表
     */
    private List<RuleInfo> generateRuleInfoList() {

        List<RuleInfo> ruleInfos = new ArrayList<>();

        String sceneCode = "scene_" + CommonUtil.getUUID();
        for (String content : contentList) {
            String caseCodePerScene = "case_" + CommonUtil.getUUID();

            ruleInfos.add(generateRuleInfo(sceneCode, caseCodePerScene, content));
            log.info("caseCodePerScene:{}", caseCodePerScene);
        }


//        int sceneNum = 5;
//        int ruleNumPerScene = 3;
//        int sceneFactor = 10000;
//
//        List<RuleInfo> ruleInfos = new ArrayList<>(sceneNum * ruleNumPerScene);
//        for (int i = 0; i < sceneNum; i++) {
//            long sceneId = sceneFactor * (i + 1);
//            for (int j = 0; j < ruleNumPerScene; j++) {
//                long id = sceneId + (j + 1);
//                ruleInfos.add(generateRuleInfo(sceneId, id));
//            }
//        }

        return ruleInfos;
    }

    /**
     * 生成规则信息
     *
     * @param sceneCode        场景Code
     * @param caseCodePerScene 规则Code
     * @return 规则信息
     */
    private RuleInfo generateRuleInfo(String sceneCode, String caseCodePerScene, String content) {
        RuleInfo ruleInfo = new RuleInfo();
        ruleInfo.setCode(caseCodePerScene);
        ruleInfo.setSceneCode(sceneCode);
        ruleInfo.setContent(generateRuleContent(sceneCode, caseCodePerScene, content));
        return ruleInfo;
    }

    /**
     * 生成规则内容，每个场景id对应一个package，每个规则对应一个唯一的规则名
     * <p>
     * 每次生成规则时记录时间戳，用来验证动态加载效果
     *
     * @param sceneId 场景ID
     * @param id      规则ID
     * @return 规则内容
     */
    @Deprecated
    private String generateRuleContent(long sceneId, long id) {
        String sceneIdStr = String.valueOf(sceneId);
        String idStr = String.valueOf(id);
        String nowStr = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        String content = "package rules.scene_{0};\n" +
                "rule \"rule_{1}\"\n" +
                "    when\n" +
                "        eval(true);\n" +
                "    then\n" +
                "        System.out.println(\"{2} [{3}, {4}]\");\n" +
                "end\n";
        return MessageFormat.format(content, sceneIdStr, idStr, nowStr, sceneIdStr, idStr);
    }

    /**
     * 生成规则内容，每个场景id对应一个package，每个规则对应一个唯一的规则名
     * <p>
     * 每次生成规则时记录时间戳，用来验证动态加载效果
     *
     * @param sceneCode        场景Code
     * @param caseCodePerScene 规则Code
     * @return 规则内容
     */
    private String generateRuleContent(String sceneCode, String caseCodePerScene, String content) {
//        return String.format(content, sceneCode, caseCodePerScene, caseCodePerScene);
        return String.format(content, caseCodePerScene);
    }


    private static List<String> contentList = new ArrayList<>();

    static {

//        String c1 = "package " + Constant.RULE_PACKAGE_NAME + ";\n" +
        String c1 = "package com.xikang.rule;\n" +
                "import com.xikang.ruleengine.drools.PatientInfo;\n" +
                "dialect \"java\"\n" +
//                "rule \"" + Constant.RULE_NAME + "\"\n" +
                "rule \"规则case1\"\n" +
                "when\n" +
                "    item : PatientInfo()\n" +
                "then\n" +
                "    if(item.getSbp()<=100 || item.getDbp()<=70){\n" +
                "       item.setCaseCode(\"%s\");\n" +
                "    }\n" +
                "end";

        String c2 = "package com.xikang.rule;\n" +
                "import com.xikang.ruleengine.drools.PatientInfo;\n" +
                "dialect \"java\"\n" +
//                "rule \"rule_%s\"\n" +
                "rule \"规则case2\"\n" +
                "when\n" +
                "    item : PatientInfo()\n" +
                "then\n" +
                "    if((item.getSbp()>100 && item.getSbp()<=120) || (item.getDbp()>70 && item.getDbp()<=90)){\n" +
                "       item.setCaseCode(\"%s\");\n" +
                "    }\n" +
                "end";


        String c3 = "package com.xikang.rule.package;\n" +
                "\n" +
                "//generated from Decision Table\n" +
                "import net.cloudburo.drools.model.Customer;\n" +
                "import net.cloudburo.drools.model.Customer.CustomerLifeStage;\n" +
                "import net.cloudburo.drools.model.Customer.CustomerNeed;\n" +
                "import net.cloudburo.drools.model.Customer.CustomerAssets;\n" +
                "import net.cloudburo.drools.model.Offer;\n" +
                "import net.cloudburo.drools.model.Offer.ProductPackage;\n" +
                "import net.cloudburo.drools.model.Offer.Product;\n" +
                "\n" +
                "global net.cloudburo.drools.model.Offer offer;\n" +
                "\n" +
                "\n" +
                "// rule values at A11, header at A6\n" +
                "rule \"ProductPackageSelection\"\n" +
                "\twhen\n" +
                "\t\t$customer:Customer($customer.getLifeStage() in (CustomerLifeStage.CAREERFOCUSED))\n" +
                "\tthen\n" +
                "\t\toffer.setFinancialPackage(ProductPackage.CAREERFOCUSED_PACKAGE);\n" +
                "end\n" +
                "\n" +
                "// rule values at A12, header at A6\n" +
                "rule \"DiscountNone\"\n" +
                "\twhen\n" +
                "\t\t$customer:Customer($customer.getLifeStage() in (CustomerLifeStage.CAREERFOCUSED), $customer.getAssets() in (CustomerAssets.TO50K))\n" +
                "\tthen\n" +
                "end\n" +
                "\n" +
                "// rule values at A13, header at A6\n" +
                "rule \"DiscountLevel1\"\n" +
                "\twhen\n" +
                "\t\t$customer:Customer($customer.getLifeStage() in (CustomerLifeStage.CAREERFOCUSED), $customer.getAssets() in (CustomerAssets.FROM50KTO150K))\n" +
                "\tthen\n" +
                "\t\toffer.setDiscount(5);\n" +
                "end\n" +
                "\n" +
                "// rule values at A14, header at A6\n" +
                "rule \"DiscountLevel2\"\n" +
                "\twhen\n" +
                "\t\t$customer:Customer($customer.getLifeStage() in (CustomerLifeStage.CAREERFOCUSED), $customer.getAssets() in (CustomerAssets.FROM150KTO300K))\n" +
                "\tthen\n" +
                "\t\toffer.setDiscount(10);\n" +
                "end\n" +
                "\n" +
                "// rule values at A15, header at A6\n" +
                "rule \"DiscountLevel3\"\n" +
                "\twhen\n" +
                "\t\t$customer:Customer($customer.getLifeStage() in (CustomerLifeStage.CAREERFOCUSED), $customer.getAssets() in (CustomerAssets.OVER300K))\n" +
                "\tthen\n" +
                "\t\toffer.setDiscount(15);\n" +
                "end\n" +
                "\n" +
                "// rule values at A16, header at A6\n" +
                "rule \"NeedsAssessmentInsurance\"\n" +
                "\twhen\n" +
                "\t\t$customer:Customer($customer.getNeeds() contains (CustomerNeed.LIFEINSURANCE))\n" +
                "\tthen\n" +
                "\t\toffer.addSingleProduct(Product.INSURANCE);\n" +
                "end\n" +
                "\n" +
                "// rule values at A17, header at A6\n" +
                "rule \"NeedsAssessmentMortage1\"\n" +
                "\twhen\n" +
                "\t\t$customer:Customer($customer.getAssets() in (CustomerAssets.FROM50KTO150K), $customer.getNeeds() contains (CustomerNeed.MORTAGE))\n" +
                "\tthen\n" +
                "\t\toffer.addSingleProduct(Product.LOAN);\n" +
                "end\n" +
                "\n" +
                "// rule values at A18, header at A6\n" +
                "rule \"NeedsAssessmentMortage2\"\n" +
                "\twhen\n" +
                "\t\t$customer:Customer($customer.getAssets() in (CustomerAssets.FROM150KTO300K), $customer.getNeeds() contains (CustomerNeed.MORTAGE))\n" +
                "\tthen\n" +
                "\t\toffer.addSingleProduct(Product.LOAN);\n" +
                "end\n" +
                "\n" +
                "// rule values at A19, header at A6\n" +
                "rule \"NeedsAssessmentMortage3\"\n" +
                "\twhen\n" +
                "\t\t$customer:Customer($customer.getAssets() in (CustomerAssets.OVER300K), $customer.getNeeds() contains (CustomerNeed.MORTAGE))\n" +
                "\tthen\n" +
                "\t\toffer.addSingleProduct(Product.SUPERLOAN);\n" +
                "end\n" +
                "\n";

        contentList.add(c1);
        contentList.add(c2);
//        contentList.add(c3);
    }
}
