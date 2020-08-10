package com.xikang.ruleengine.controller;

import com.xikang.ruleengine.common.Result;
import com.xikang.ruleengine.service.ExportService;
import com.xikang.ruleengine.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description
 * @Author Snowman1024
 * @Date 2020/3/13 16:58
 * @Version 1.0
 **/
@Slf4j
@RestController
@RequestMapping(value = "/excel")
public class ExportController {

    @Autowired
    private ExportService exportService;


    /**
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping("/doUpload")
    public Result doUpload(HttpServletRequest request) throws IOException {
        MultipartHttpServletRequest mreq = null;
        if (request instanceof MultipartHttpServletRequest) {
            mreq = (MultipartHttpServletRequest) request;
        } else {
            return Result.failure();
        }
        try {
            boolean flag = exportService.analysisFile(mreq);
            if (!flag) {
                return Result.failure();
            }
            return Result.success();
        } catch (Exception e) {
            return Result.success();
        }
    }


    /**
     * 由于ajax无法直接导出excel,所以第一次把请求生成的ExcelParam缓存起来,然后前端再次window.open(url);
     */
    public static Map<String, MemberVO> excelParamCache = new ConcurrentHashMap<>();

    /**
     * 第一步缓存参数
     *
     * @param memberVO
     * @return
     */
    @PostMapping(value = "/export")
    public Result export(@RequestBody MemberVO memberVO) {
        String key = UUID.randomUUID().toString();
        excelParamCache.put(key, memberVO);
        return Result.success(key);
    }

    /**
     * 执行导出
     *
     * @param key
     * @param response
     * @return
     */
    @RequestMapping(value = "doExport")
    public Result doExport(String key, HttpServletResponse response) {
        if (key == null) {
            return Result.failure();
        }
        try {
            if (!excelParamCache.containsKey(key)) {
                return Result.failure();
            }
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("信息表");

            // headers表示excel表中第一行的表头 在excel表中添加表头
            String[] headers = {"id", "uid", "地址", "城市"};
            HSSFRow row = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                HSSFCell cell = row.createCell(i);
                HSSFRichTextString text = new HSSFRichTextString(headers[i]);
                cell.setCellValue(text);
            }

            // 新增数据行，并且设置单元格数据
            int rowNum = 1;
            //在表中存放查询到的数据放入对应的列
            for (int i = 0; i < 4; i++) {
                HSSFRow row1 = sheet.createRow(rowNum);
                row1.createCell(0).setCellValue(1);
                row1.createCell(1).setCellValue(2);
                row1.createCell(2).setCellValue(3);
                row1.createCell(3).setCellValue("hhh");
                rowNum++;
            }

            // 设置要导出的文件的名字
            String fileName = "报表-" + new Date() + ".xls";
            response.setContentType("application/octet-stream");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            response.flushBuffer();
            workbook.write(response.getOutputStream());
        } catch (Exception e) {
            excelParamCache.remove(key);
        } finally {
            excelParamCache.remove(key);
        }
        return Result.success();
    }
}
