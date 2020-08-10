package com.xikang.ruleengine.drools.one;

import com.xikang.ruleengine.drools.PatientInfo;
import lombok.extern.slf4j.Slf4j;
import org.drools.decisiontable.InputType;
import org.drools.decisiontable.SpreadsheetCompiler;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @Description 规则测试
 * @Author Snowman1024
 * @Date 2020/3/4 17:40
 * @Version 1.0
 **/
@Slf4j
@RequestMapping("/rule")
@RestController
public class RuleController {

    @Autowired
    private RuleLoader ruleLoader;

    @Autowired
    private KieSessionHelper kieSessionHelper;

    @GetMapping("/")
    public String index() {
        log.info("index");
        return "success";
    }

    /**
     * 重新加载所有规则
     */
    @GetMapping("reload")
    public String reload() {
        log.info("reload all");
        ruleLoader.reloadAll();
        return "success";
    }

    /**
     * 重新加载给定场景下的规则
     *
     * @param sceneCode 场景Code
     */
    @GetMapping("reload/{sceneCode}")
    public String reload(@PathVariable("sceneCode") String sceneCode) {
        log.info("reload scene:{}", sceneCode);
        ruleLoader.reload(sceneCode);
        return "success";
    }

    /**
     * 触发给定场景规则
     *
     * @param sceneCode 场景Codee
     */
    @GetMapping("fire/{sceneCode}")
    public String fire(@PathVariable("sceneCode") String sceneCode) {
        log.info("fire scene:{}", sceneCode);
        KieSession kieSession = kieSessionHelper.getKieSessionBySceneId(sceneCode);

        if (null == kieSession) {
            return "sceneCode invalid";
        }
        PatientInfo patientInfo1 = new PatientInfo();
        patientInfo1.setPatientCode("001");
        patientInfo1.setSbp(90);
        patientInfo1.setDbp(60);
        kieSession.insert(patientInfo1);


        PatientInfo patientInfo2 = new PatientInfo();
        patientInfo2.setPatientCode("002");
        patientInfo2.setSbp(110);
        patientInfo2.setDbp(80);
        kieSession.insert(patientInfo2);

        kieSession.fireAllRules();
//        kieSession.fireAllRules(1);  只执行第一个满足条件的规则，乱序执行的情况下
        String caseCode1 = patientInfo1.getCaseCode();
        String caseCode2 = patientInfo2.getCaseCode();

        log.info("caseCode1:{}", caseCode1);
        log.info("caseCode2:{}", caseCode2);

        kieSession.dispose();


        return "success";
    }


    @GetMapping("/getDrl")
    public String getDrlFromExcel() {
        String excelFile = "E:\\drools_demo\\tutorial-drools-decisiontable-master\\src\\main\\resources\\net\\cloudburo\\drools\\rules\\DroolsDiscount.xlsx";
//        DecisionTableConfiguration configuration = KnowledgeBuilderFactory.newDecisionTableConfiguration();
//        configuration.setInputType(DecisionTableInputType.XLS);
//        Resource dt = ResourceFactory.newClassPathResource(excelFile, getClass());
//        DecisionTableProviderImpl decisionTableProvider = new DecisionTableProviderImpl();
//        String drl = decisionTableProvider.loadFromResource(dt, null);

        String drl = "";
        try {
            File file = new File(excelFile);
            InputStream is = new FileInputStream(file);
            SpreadsheetCompiler compiler = new SpreadsheetCompiler();
            drl = compiler.compile(is, InputType.XLS);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return drl;
    }


}
