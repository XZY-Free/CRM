package com.XZY_SUNSHINE.crm.commons.utils;

import com.XZY_SUNSHINE.crm.workbench.activity.pojo.Activity;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;

public class HSSFUtils {
    public static void exportActivities(List<Activity> activities, HttpServletResponse response) throws Exception{
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("市场活动记录");
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("Id");
        cell = row.createCell(1);
        cell.setCellValue("所有者");
        cell = row.createCell(2);
        cell.setCellValue("名称");
        cell = row.createCell(3);
        cell.setCellValue("开始日期");
        cell = row.createCell(4);
        cell.setCellValue("结束日期");
        cell = row.createCell(5);
        cell.setCellValue("花费");
        cell = row.createCell(6);
        cell.setCellValue("描述");
        cell = row.createCell(7);
        cell.setCellValue("创建日期");
        cell = row.createCell(8);
        cell.setCellValue("创建者");
        cell = row.createCell(9);
        cell.setCellValue("编辑日期");
        cell = row.createCell(10);
        cell.setCellValue("编辑者");
        for (int i = 1; i <= activities.size(); i++) {
            row = sheet.createRow(i);
            Activity activity = activities.get(i - 1);
            cell = row.createCell(0);
            cell.setCellValue(activity.getId());
            cell = row.createCell(1);
            cell.setCellValue(activity.getOwner());
            cell = row.createCell(2);
            cell.setCellValue(activity.getName());
            cell = row.createCell(3);
            cell.setCellValue(activity.getStartDate());
            cell = row.createCell(4);
            cell.setCellValue(activity.getEndDate());
            cell = row.createCell(5);
            cell.setCellValue(activity.getCost());
            cell = row.createCell(6);
            cell.setCellValue(activity.getDescription());
            cell = row.createCell(7);
            cell.setCellValue(activity.getCreateTime());
            cell = row.createCell(8);
            cell.setCellValue(activity.getCreateBy());
            cell = row.createCell(9);
            cell.setCellValue(activity.getEditTime());
            cell = row.createCell(10);
            cell.setCellValue(activity.getEditBy());
        }
        response.setContentType("application/octet-stream;charset='UTF-8'");
        String fileName="市场活动记录.xls";
        fileName = URLEncoder.encode(fileName,"UTF-8");
        response.addHeader("Content-Disposition","attachment; filename="+fileName);
        workbook.write(response.getOutputStream());
    }

    public static String getValue(HSSFCell cell){
        String ret="";
        if(cell.getCellType()==HSSFCell.CELL_TYPE_STRING){
            ret=cell.getStringCellValue();
        }else if(cell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
            ret=cell.getNumericCellValue()+"";
        }else if(cell.getCellType()==HSSFCell.CELL_TYPE_BOOLEAN){
            ret=cell.getBooleanCellValue()+"";
        }else if(cell.getCellType()==HSSFCell.CELL_TYPE_FORMULA){
            ret=cell.getCellFormula();
        }else{
            ret="";
        }

        return ret;
    }
}
