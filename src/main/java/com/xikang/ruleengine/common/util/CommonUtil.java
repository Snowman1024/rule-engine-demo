package com.xikang.ruleengine.common.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @Description
 * @Author Snowman1024
 * @Date 2019/11/12 10:54
 * @Version 1.0
 **/
public class CommonUtil {

    private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

    public static String getUUID() {
        String id = UUID.randomUUID().toString();
        id = id.replace("-", "");
        return id;
    }

    public static String getSerialNum() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return formatter.format(new Date());
    }

    /**
     * 计算BMI
     *
     * @param height
     * @param weight
     * @return
     */
    public static BigDecimal calcBmi(String height, String weight) {
        if (StringUtils.isBlank(height) || StringUtils.isBlank(weight)) {
            return null;
        }
        BigDecimal bmi = null;
        try {
            BigDecimal h = new BigDecimal(height);
            BigDecimal w = new BigDecimal(weight);
            BigDecimal he = h.divide(new BigDecimal(100));
            bmi = w.divide(he.multiply(he), 1, RoundingMode.HALF_UP);
        } catch (Exception e) {
            logger.error("计算BMI异常:{}", e);
        }
        return bmi;
    }

    /**
     * BMI分类：中国参考标准
     *
     * @param bmi
     * @return
     */
    public static String bmiClassify(Double bmi) {
        String result = "";
        if (null == bmi) {
            return result;
        }
        if (bmi < 18.5) {
            result = "偏瘦";
        } else if (bmi >= 18.5 && bmi < 24) {
            result = "正常";
        } else if (bmi >= 24 && bmi < 27) {
            result = "偏胖";
        } else if (bmi >= 27 && bmi < 30) {
            result = "肥胖";
        } else if (bmi >= 30) {
            result = "重度肥胖";
        }

        return result;
    }


}
