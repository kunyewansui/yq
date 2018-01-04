package com.xiaosuokeji.server.util;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSStatusPair;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Excel相关工具类
 *（导入导出示例可参考：mscrm项目）
 * Created with IntelliJ IDEA.
 * User: guobaikun
 * Date: 2017/11/27
 * Time: 10:01
 */

public class ExcelUtil {

    private final static String excel2003L = ".xls";    //2003- 版本的excel

    private final static String excel2007U = ".xlsx";   //2007+ 版本的excel

    public static XSStatusPair EXCEL_TEMPLATE_FORMAT_WRONG = XSStatusPair.build(10305, "模板格式错误，请检查");

    /**
     * 根据文件后缀，自适应上传文件的版本
     */
    public static Workbook getWorkbook(MultipartFile file) throws Exception {
        Workbook wb = null;

        String fileName = file.getOriginalFilename();
        // 获取图片的扩展名
        String suffix = fileName.substring(fileName.lastIndexOf("."));

        if (excel2003L.equals(suffix)) {
            wb = new HSSFWorkbook(file.getInputStream());  //2003-
        } else if (excel2007U.equals(suffix)) {
            wb = new XSSFWorkbook(file.getInputStream());  //2007+
        } else {
            throw new XSBusinessException(XSStatusPair.build(null, "EXCEL的文件格式有,只支持.xls和.xlsx"));
        }
        return wb;
    }

    /**
     * 检验模板标题是否正确（只能验证一级标题）
     */
    public static void validateHeader(Sheet sheet, String[] header) throws XSBusinessException {
        //检验模板格式
        Row r = sheet.getRow(0);
        if (r == null) return;
        for (int i = 0; i < header.length; i++) {
            Cell cell = r.getCell(i);
            if (cell == null) {
                throw new XSBusinessException(EXCEL_TEMPLATE_FORMAT_WRONG);
            }
            String v = cell.getStringCellValue().replace("*", "");
            if (!v.trim().equals(header[i])) {
                throw new XSBusinessException(EXCEL_TEMPLATE_FORMAT_WRONG);
            }

        }
    }

    /**
     * 设置表头的单元格样式
     */
    public static CellStyle getHeadStyle(XSSFWorkbook wb) {
        // 创建单元格样式
        CellStyle cellStyle = wb.createCellStyle();
        // 设置单元格的背景颜色为淡蓝色
        cellStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // 设置单元格居中对齐
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        // 设置单元格垂直居中对齐
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 创建单元格内容显示不下时自动换行
        //cellStyle.setWrapText(true);
        // 设置单元格字体样式
        XSSFFont font = wb.createFont();
        // 设置字体加粗
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        font.setFontName("宋体");
        font.setFontHeight((short) 200);
        cellStyle.setFont(font);
        //设置单元格边框样式
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        return cellStyle;
    }

    /**
     * 设置表体的单元格样式
     */
    public static XSSFCellStyle getBodyStyle(XSSFWorkbook wb) {
        // 创建单元格样式
        XSSFCellStyle cellStyle = wb.createCellStyle();
        // 设置单元格居中对齐
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        // 设置单元格垂直居中对齐
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 创建单元格内容显示不下时自动换行
        //cellStyle.setWrapText(true);
        // 设置单元格字体样式
        XSSFFont font = wb.createFont();
        // 设置字体加粗
        font.setFontName("宋体");
        font.setFontHeight((short) 200);
        cellStyle.setFont(font);
        //设置单元格边框样式
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        return cellStyle;
    }

    /**
     * 自动调整列宽
     */
    public static void autoSizeAllColumn(Sheet sheet){
        List<Integer> rows = new ArrayList<>();
        for(int i=0;i<=sheet.getLastRowNum();i++){
            rows.add(sheet.getRow(i).getPhysicalNumberOfCells());
        }
        Integer maxCol = Collections.max(rows);
        for(int i=0; i<maxCol; i++){
            sheet.autoSizeColumn((short)i);
        }
    }
}
