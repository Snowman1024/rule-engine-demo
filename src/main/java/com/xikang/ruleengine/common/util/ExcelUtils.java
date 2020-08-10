package com.xikang.ruleengine.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author Snowman1024
 * @Date 2020/3/13 16:31
 * @Version 1.0
 **/
@Slf4j
public class ExcelUtils {

    /**
     * 处理HTTP请求里是file文件
     *
     * @param request
     * @return 返回List<Map>每个Map存放一行
     */
    public static List<Map> analysisFile(MultipartHttpServletRequest request) throws Exception {
        InputStream inputStream = null;
        try {
            inputStream = request.getFile("file").getInputStream();
        } catch (IOException e) {
            throw e;
        }
        String fileName = request.getFile("file").getOriginalFilename();
        Workbook workbook = null;
        try {
            //判断什么类型文件
            if (fileName.endsWith(".xls") || fileName.endsWith(".xlsx")) {
                workbook = WorkbookFactory.create(inputStream);
            }
        } catch (Exception e) {
            throw e;
        }
        if (workbook == null) {
            return null;
        } else {
            //获取所有的工作表的数量
            int numOfSheet = workbook.getNumberOfSheets();
            log.info("{}--->numOfSheet", numOfSheet);
            List<Map> mapList = new ArrayList<>();
            //遍历表
            for (int i = 0; i < numOfSheet; i++) {
                //获取一个sheet也就是一个工作本。
                Sheet sheet = workbook.getSheetAt(i);
                if (sheet == null) {
                    continue;
                }
                //获取一个sheet有多少Row
                int lastRowNum = sheet.getLastRowNum();
                if (lastRowNum == 0) {
                    continue;
                }
                Row row;
                // 行头
                List<String> headName = new ArrayList<>();
                if (lastRowNum > 0) {
                    row = sheet.getRow(0);
                    // 获取一个Row有多少Cell
                    short lastCellNum = row.getLastCellNum();
                    for (int k = 0; k <= lastCellNum; k++) {
                        if (row.getCell(k) == null) {
                            continue;
                        }
                        String res = getCellVal(row.getCell(k));
                        headName.add(res);
                    }
                }

                // 每一行的内容
                for (int j = 1; j <= lastRowNum; j++) {
                    row = sheet.getRow(j);
                    if (row == null) {
                        continue;
                    }
                    //获取一个Row有多少Cell
                    short lastCellNum = row.getLastCellNum();
                    Map<String, String> rowMap = new HashMap();
                    for (int k = 0; k <= lastCellNum; k++) {
                        if (row.getCell(k) == null) {
                            continue;
                        }
                        String res = getCellVal(row.getCell(k));
                        rowMap.put(headName.get(k), res);
                    }
                    mapList.add(rowMap);
                }
            }
            return mapList;
        }
    }

    private static String getCellVal(Cell cell) {
        //日期格式yyyy-mm-dd
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        DecimalFormat df = new DecimalFormat("0");
        String val;
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    //日期型
                    val = fmt.format(cell.getDateCellValue());
                } else {
                    //数字型
                    val = df.format(cell.getNumericCellValue());
                }
                break;
            case Cell.CELL_TYPE_STRING:
                //文本类型
                val = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                //布尔型
                val = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_BLANK:
                //空白
                val = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_ERROR:
                //错误
                val = "错误";
                break;
            case Cell.CELL_TYPE_FORMULA:
                //公式
                try {
                    val = String.valueOf(cell.getStringCellValue());
                } catch (IllegalStateException e) {
                    val = String.valueOf(cell.getNumericCellValue());
                }
                break;
            default:
                val = cell.getRichStringCellValue() == null ? null : cell.getRichStringCellValue().toString();
        }
        return val;
    }
}
