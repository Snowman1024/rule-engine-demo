package com.xikang.ruleengine.drools.one;

import lombok.Data;
import lombok.ToString;

/**
 * @Description 规则信息
 * @Author Snowman1024
 * @Date 2020/3/4 17:28
 * @Version 1.0
 **/
@Data
@ToString
public class RuleInfo {

    /**
     * 规则code，全局唯一
     */
    private String code;

    /**
     * 场景code，一个场景对应多个规则，一个场景对应一个业务场景，一个场景对应一个kmodule
     */
    private String sceneCode;

    /**
     * 规则内容，既drl文件内容
     */
    private String content;
}
