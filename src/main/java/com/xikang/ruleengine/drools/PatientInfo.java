package com.xikang.ruleengine.drools;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @Description
 * @Author Snowman1024
 * @Date 2020/3/5 9:22
 * @Version 1.0
 **/
@Data
public class PatientInfo implements Serializable {


    private static final long serialVersionUID = -7166932973242991345L;

    private String patientCode;

    private int gender = 0;

    private int age = -1;

    /**
     * 收缩压
     */
    private double sbp = -1.0;

    /**
     * 舒张压
     */
    private double dbp = -1.0;


    private String caseCode;

}
