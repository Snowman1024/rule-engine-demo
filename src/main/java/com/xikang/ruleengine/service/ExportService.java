package com.xikang.ruleengine.service;

import com.xikang.ruleengine.common.util.ExcelUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author Snowman1024
 * @Date 2020/3/13 17:00
 * @Version 1.0
 **/
@Service
public class ExportService {

    /**
     * @param mreq
     * @return
     * @throws Exception
     */
    public boolean analysisFile(MultipartHttpServletRequest mreq) throws Exception {
        List<Map> dataMap = ExcelUtils.analysisFile(mreq);
        return true;
    }
}
