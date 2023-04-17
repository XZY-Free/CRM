package com.XZY_SUNSHINE.crm.workbench.activity.controller;

import com.XZY_SUNSHINE.crm.commons.pojo.ResultObject;
import com.XZY_SUNSHINE.crm.commons.utils.DateFormat;
import com.XZY_SUNSHINE.crm.commons.utils.constants;
import com.XZY_SUNSHINE.crm.commons.utils.uuid;
import com.XZY_SUNSHINE.crm.settings.pojo.User;
import com.XZY_SUNSHINE.crm.settings.service.UserService;
import com.XZY_SUNSHINE.crm.workbench.activity.pojo.Activity;
import com.XZY_SUNSHINE.crm.workbench.activity.service.ActivityService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
public class activityController {
    @Autowired
    private UserService userService;
    @Autowired
    private ActivityService activityService;

    @GetMapping("/workbench/activity/index.do")
    public String activityIndex(HttpServletRequest request){
        List<User> userList = userService.queryAllUsers();
        request.setAttribute("userList",userList);
        return "workbench/activity/index";
    }

    @PostMapping("/workbench/activity/insert")
    @ResponseBody
    public Object insert(Activity activity, HttpSession session){
        activity.setId(uuid.getUUID());
        activity.setCreateTime(DateFormat.dateFormatDate(new Date()));
        User user=(User)session.getAttribute(constants.SESSION_USER);
        activity.setCreateBy(user.getId());
        int insert = activityService.insert(activity);
        ResultObject result = new ResultObject();
        if (insert>0){
            result.setCode(constants.SUCCESS_CODE);
            result.setMessage("创建成功！");
        }else{
            result.setCode(constants.FAIL_CODE);
            result.setMessage("系统忙，请稍后重试。。。。");
        }
        return result;
    }


    @PostMapping("/workbench/activity/selectByConditionForPage.do")
    @ResponseBody
    public Object selectByConditionForPage(String owner,String name,
                                           String startDate,String endDate,
                                           int pageNo,int pageSize){
        HashMap<String,Object> map = new HashMap<>();
        map.put("owner",owner);
        map.put("name",name);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("beginNo",(pageNo-1)*pageSize);
        map.put("pageSize",pageSize);
        List<Activity> activityList = activityService.queryActivityByConditionForPage(map);
        int totalCounts = activityService.queryActivityByConditionForCounts(map);
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("activityList",activityList);
        retMap.put("totalCounts",totalCounts);
        return retMap;
    }

    @PostMapping("/workbench/activity/deleteByIds.do")
    @ResponseBody
    public Object deleteByIds(String[] ids){
        ResultObject resultObject = new ResultObject();
        try{
            int i = activityService.deleteActivityByIds(ids);
            if (i>0){
                resultObject.setCode(constants.SUCCESS_CODE);
            }else{
                resultObject.setCode(constants.FAIL_CODE);
                resultObject.setMessage("系统忙，请稍后重试！");
            }
        }catch (Exception e){
           e.printStackTrace();
       }
        return resultObject;
    }


    @PostMapping("/workbench/activity/selectActivityById.do")
    @ResponseBody
    public Activity selectActivityById(String id){
        Activity activity = activityService.queryActivityById(id);
        return activity;
    }

    @PostMapping("/workbench/activity/updateActivityById.do")
    @ResponseBody
    public Object updateActivityById(Activity activity,HttpSession session){
        ResultObject resultObject = new ResultObject();
        User user = (User) session.getAttribute(constants.SESSION_USER);
        activity.setEditBy(user.getId());
        activity.setEditTime(DateFormat.dateFormatTime(new Date()));
        try {
            int i = activityService.updateActivityById(activity);
            if (i>0){
                resultObject.setCode(constants.SUCCESS_CODE);
                resultObject.setMessage("修改成功！");
            }else{
                resultObject.setCode(constants.FAIL_CODE);
                resultObject.setMessage("系统忙，请稍后重试！");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return resultObject;
    }

    @GetMapping("/workbench/activity/exportActivities.do")
    public void exportActivities(HttpServletResponse response) throws  Exception{
        List<Activity> activities = activityService.queryAllActivities();
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
        response.setContentType("application/octet-stream");
        response.addHeader("Content-Disposition","attachment; filename=市场活动记录.xls");
        workbook.write(response.getOutputStream());
    }

}
