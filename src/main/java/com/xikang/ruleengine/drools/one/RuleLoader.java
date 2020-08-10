package com.xikang.ruleengine.drools.one;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xikang.ruleengine.common.Constant;
import lombok.extern.slf4j.Slf4j;
import org.drools.core.definitions.rule.impl.RuleImpl;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.builder.model.KieBaseModel;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.builder.model.KieSessionModel;
import org.kie.api.conf.EqualityBehaviorOption;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.definition.KiePackage;
import org.kie.api.definition.process.Process;
import org.kie.api.definition.rule.Query;
import org.kie.api.definition.rule.Rule;
import org.kie.api.definition.type.FactType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @Description 规则加载器
 * @Author Snowman1024
 * @Date 2020/3/4 17:32
 * @Version 1.0
 **/
@Slf4j
@Component
public class RuleLoader implements ApplicationRunner {

    /**
     * key:kcontainerName,value:KieContainer，每个场景对应一个KieContainer
     */
    private final ConcurrentMap<String, KieContainer> kieContainerMap = new ConcurrentHashMap<>();

    @Autowired
    private RuleInfoService ruleInfoService;


    /**
     * 构造kcontainerName
     *
     * @param sceneCode 场景Code
     * @return kcontainerName
     */
    private String buildKcontainerName(String sceneCode) {
        return Constant.RULE_CONTAINER_NAME + sceneCode;
    }

    /**
     * 构造kbaseName
     *
     * @param sceneCode 场景Code
     * @return kbaseName
     */
    private String buildKbaseName(String sceneCode) {
        return Constant.RULE_BASE_NAME + sceneCode;
    }

    /**
     * 构造ksessionName
     *
     * @param sceneCode 场景Code
     * @return ksessionName
     */
    private String buildKsessionName(String sceneCode) {
        return Constant.RULE_SESSION_NAME + sceneCode;
    }


    /**
     * 启动时载入所有的场景，规则
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        reloadAll();
    }


    /**
     * 通过场景code获取container
     *
     * @param sceneCode
     * @return
     */
    KieContainer getKieContainerBySceneId(String sceneCode) {
        return kieContainerMap.get(buildKcontainerName(sceneCode));
    }


    /**
     * 重新加载所有规则
     */
    public void reloadAll() {
        Map<String, List<RuleInfo>> sceneId2RuleInfoListMap = ruleInfoService.getRuleInfoListMap();
        for (Map.Entry<String, List<RuleInfo>> entry : sceneId2RuleInfoListMap.entrySet()) {
            String sceneCode = entry.getKey();
            reload(sceneCode, entry.getValue());
        }
        log.info("reload all success");
    }

    /**
     * 重新加载给定场景下的规则
     *
     * @param sceneCode 场景Code
     */
    public void reload(String sceneCode) {
        List<RuleInfo> ruleInfos = ruleInfoService.getRuleInfoListBySceneId(sceneCode);
        reload(sceneCode, ruleInfos);
        log.info("{}:reload success", sceneCode);
    }

    /**
     * 重新加载给定场景给定规则列表，对应一个kmodule
     *
     * @param sceneCode 场景Code
     * @param ruleInfos 规则列表
     */
//    private void reload(String sceneCode, List<RuleInfo> ruleInfos) {
//        KieServices kieServices = KieServices.Factory.get();
//        KieModuleModel kieModuleModel = kieServices.newKieModuleModel();
//        KieBaseModel kieBaseModel = kieModuleModel.newKieBaseModel(buildKbaseName(sceneCode));
//        kieBaseModel.setDefault(true);
//        kieBaseModel.addPackage(String.format(Constant.RULE_PACKAGE_NAME, sceneCode));
//        kieBaseModel.newKieSessionModel(buildKsessionName(sceneCode));
//
//        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
//        for (RuleInfo ruleInfo : ruleInfos) {
//            String fullPath = String.format(Constant.RULE_DRL_PATH, sceneCode, ruleInfo.getCode());
//            kieFileSystem.write(fullPath, ruleInfo.getContent());
//        }
//        kieFileSystem.writeKModuleXML(kieModuleModel.toXML());
//
//        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem).buildAll();
//        Results results = kieBuilder.getResults();
//        if (results.hasMessages(Message.Level.ERROR)) {
//            log.error("{} reload rule error:{}", sceneCode, results.getMessages());
////            throw new IllegalStateException("rule error");
//        }
//
//        KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
//        kieContainerMap.put(buildKcontainerName(sceneCode), kieContainer);
//
//        log.info("sceneCode:{}", sceneCode);
//    }


//    <kmodule xmlns="http://jboss.org/kie/6.0.0/kmodule">
    //    <kbase name="ttt" packages="com.xk...">
    //        <ksession name="sessioname"/>
    //    </kbase>
//   </kmodule>
    private void reload(String sceneCode, List<RuleInfo> ruleInfos) {

        KieServices kieServices = KieServices.Factory.get();
        //创建 kmodule
        KieModuleModel kieModuleModel = kieServices.newKieModuleModel();
        //创建 kbase
        KieBaseModel kieBaseModel = kieModuleModel.newKieBaseModel(buildKbaseName(sceneCode))
                //每个module中只能有一个默认
                .setDefault(true)
//                .setEqualsBehavior(EqualityBehaviorOption.EQUALITY)
//                .setEventProcessingMode(EventProcessingOption.STREAM)
//                .addPackage("com.xikang.rule")
                ;
        //创建ksession
        KieSessionModel kieSessionModel = kieBaseModel.newKieSessionModel(buildKsessionName(sceneCode));

        //配置其 kbase 和 ksession，转换为XML，将XML添加到KieFileSysstem
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        for (RuleInfo ruleInfo : ruleInfos) {
            String fullPath = String.format(Constant.RULE_DRL_PATH2, sceneCode, ruleInfo.getCode());
            kieFileSystem.write(fullPath, ruleInfo.getContent());
        }
        kieFileSystem.writeKModuleXML(kieModuleModel.toXML());

        //通过 kieBuilder 构建
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem).buildAll();
        Results results = kieBuilder.getResults();
        if (results.hasMessages(Message.Level.ERROR)) {
            log.error("{} reload rule error:{}", sceneCode, results.getMessages());
            throw new IllegalStateException("rule error");
        }

        KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
        kieContainer.dispose();

//        //监视Maven存储库，以检查是否已安装新版本的Kie项目
//        KieScanner kScanner = kieServices.newKieScanner(kieContainer);
//        // Start the KieScanner polling the Maven repository every 10 seconds
//        kScanner.start( 10000L );

        kieContainerMap.put(buildKcontainerName(sceneCode), kieContainer);

        //获取默认的KieBase
        KieBase kieBase = kieContainer.getKieBase();
        KieSession kieSession = kieBase.newKieSession();
        Collection<KiePackage> kiePackageCollection = kieBase.getKiePackages();

        for (KiePackage kiePackage : kiePackageCollection) {
            Collection<FactType> factTypeCollection = kiePackage.getFactTypes();
            Collection<Process> processes = kiePackage.getProcesses();
            Collection<Query> queries = kiePackage.getQueries();
            Collection<Rule> rules = kiePackage.getRules();
            for (Rule rule : rules) {
                RuleImpl ruleImpl = (RuleImpl) rule;

            }
        }

        log.info("sceneCode:{}", sceneCode);

        //
//        kieContainer.dispose();
//        kieFileSystem.delete(fullPath);
//        kieBaseModel.removeKieSessionModel(buildKsessionName(ruleCode));
//        kieModuleModel.removeKieBaseModel(buildKbaseName(ruleCode));
    }
}
