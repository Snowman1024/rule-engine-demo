package com.xikang.ruleengine.drools.three;

import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.drools.core.impl.KnowledgeBaseImpl;
import org.drools.decisiontable.InputType;
import org.drools.decisiontable.SpreadsheetCompiler;
import org.kie.api.KieBase;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.builder.DecisionTableConfiguration;
import org.kie.internal.builder.DecisionTableInputType;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;

import java.io.*;

/**
 * @Description
 * @Author Snowman1024
 * @Date 2020/3/17 9:29
 * @Version 1.0
 **/
public class DroolsSvs {

    /**
     * 加载XLS文件，获取KieSession
     *
     * @param xlsPath
     *            决策表XLS文件路径
     * @return KieSession
     */
//    public static KieSession getKieSessionByXls(String xlsPath) {
//        DecisionTableConfiguration dtc = KnowledgeBuilderFactory.newDecisionTableConfiguration();
//        dtc.setInputType(DecisionTableInputType.XLS);
//        KnowledgeBuilder knowledgeBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
//        knowledgeBuilder.add(ResourceFactory.newClassPathResource(xlsPath, DroolsSvs.class), ResourceType.DTABLE, dtc);
//
//        KnowledgeBase knowledgeBase = knowledgeBuilder.newKnowledgeBase();
//        knowledgeBase.addKnowledgePackages(knowledgeBuilder.getKnowledgePackages());
//        return knowledgeBase.newStatefulKnowledgeSession();
//    }
//
//    /**
//     * 加载DRL字符串，获取KieSession
//     *
//     * @param drlStr
//     *            DRL字符串
//     * @return
//     */
//    public static KieSession getKieSessionByDrlString(String drlStr) {
//        Resource resource = ResourceFactory.newReaderResource((Reader) new StringReader(drlStr));
//        KnowledgeBuilder knowledgeBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
//        knowledgeBuilder.add(resource, ResourceType.DRL);
//
//        KnowledgeBase knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase();
//        knowledgeBase.addKnowledgePackages(knowledgeBuilder.getKnowledgePackages());
//        return knowledgeBase.newStatefulKnowledgeSession();
//    }

    /**
     * 将XLS文件编译为DRL字符串
     *
     * @param xlsPath 决策表XLS文件路径
     * @return DRL字符串
     * @throws Exception
     */
    public static String compile2DRL(String xlsPath) throws Exception {
        FileInputStream fis = new FileInputStream(new File(xlsPath));
        SpreadsheetCompiler converter = new SpreadsheetCompiler();
        return converter.compile(fis, InputType.XLS);
    }

    /**
     * 注入POJO，执行规则
     *
     * @param kieSession
     * @param pojos
     */
    public static void execute(KieSession kieSession, DroolsSpreadsheet... pojos) {
        for (DroolsSpreadsheet pojo : pojos)
            kieSession.insert(pojo);
        kieSession.fireAllRules();
        kieSession.dispose();
    }

    public static void main(String[] args) {
        try {
            String xlsPath = "Drools.xls";
            String drlStr = DroolsSvs.compile2DRL(xlsPath);

            // KieSession kieSession = DroolsSvs.getKieSessionByXls(xlsPath);
//            KieSession kieSession = DroolsSvs.getKieSessionByDrlString(drlStr);
//
//            DroolsSpreadsheet pojo1 = new DroolsSpreadsheet();
//            pojo1.setParam1("Orthopedics");
//            pojo1.setParam2("31");
//            pojo1.setParam3("1000");
//            pojo1.setParam4("B");
//            DroolsSpreadsheet pojo2 = new DroolsSpreadsheet();
//            pojo2.setParam1("Orthopedics");
//            pojo2.setParam2("31");
//            pojo2.setParam3("1800");
//            pojo2.setParam4("B");
//
//            DroolsSvs.execute(kieSession, pojo1, pojo2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
