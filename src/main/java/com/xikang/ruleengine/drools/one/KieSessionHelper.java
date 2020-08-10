package com.xikang.ruleengine.drools.one;

import org.kie.api.KieBase;
import org.kie.api.definition.KiePackage;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * @Description KieSession助手类
 * @Author Snowman1024
 * @Date 2020/3/4 17:38
 * @Version 1.0
 **/
@Component
public class KieSessionHelper {

    @Autowired
    private RuleLoader ruleLoader;

    /**
     * 获取KieSession
     *
     * @param sceneCode 场景Code
     * @return KieSession
     */
    public KieSession getKieSessionBySceneId(String sceneCode) {
        KieContainer container = ruleLoader.getKieContainerBySceneId(sceneCode);
        if (null != container) {
            return container.getKieBase().newKieSession();
        }
        return null;
    }
}
