package com.XZY_SUNSHINE.crm.workbench.activity.controller;

import com.XZY_SUNSHINE.crm.commons.pojo.ResultObject;
import com.XZY_SUNSHINE.crm.commons.utils.*;
import com.XZY_SUNSHINE.crm.settings.pojo.User;
import com.XZY_SUNSHINE.crm.settings.service.UserService;
import com.XZY_SUNSHINE.crm.workbench.activity.pojo.Activity;
import com.XZY_SUNSHINE.crm.workbench.activity.pojo.ActivityRemark;
import com.XZY_SUNSHINE.crm.workbench.activity.service.ActivityRemarkService;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
public class activityController {
    @Autowired
    private UserService userService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private ActivityRemarkService activityRemarkService;

    @GetMapping("/workbench/activity/index.do")
    public String activityIndex(HttpServletRequest request){
        List<User> userList = userService.queryAllUsers();
        request.setAttribute("userList",userList);
        return "/workbench/activity/index";
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
        return activityService.queryActivityById(id);
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
        HSSFUtils.exportActivities(activities,response);
    }


    @GetMapping("/workbench/activity/exportActivitiesByIds.do")
    public void exportActivitiesByIds(String id,HttpServletResponse response) throws Exception{
        String[] ids = id.split(",");
        List<Activity> activities = activityService.queryAllActivitiesByIds(ids);
        HSSFUtils.exportActivities(activities,response);
    }


    @PostMapping("/workbench/activity/saveActivities.do")
    @ResponseBody
    public Object saveActivities(MultipartFile activityFile,HttpSession session) throws Exception {
        User user = (User) session.getAttribute(constants.SESSION_USER);
        HSSFWorkbook workbook = new HSSFWorkbook(activityFile.getInputStream());
        HSSFSheet sheet = workbook.getSheetAt(0);
        List<Activity> activities = new ArrayList<>();
        HSSFRow row = null;
        HSSFCell cell = null;
        Activity activity = null;
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            activity = new Activity();
            activity.setId(uuid.getUUID());
            activity.setOwner(user.getId());
            activity.setCreateBy(user.getId());
            activity.setCreateTime(DateFormat.dateFormatDate(new Date()));
            row = sheet.getRow(i);
            for (int j = 0; j < row.getLastCellNum(); j++) {
                cell = row.getCell(j);
                if (j == 0) {
                    activity.setName(HSSFUtils.getValue(cell));
                } else if (j == 1) {
                    activity.setStartDate(HSSFUtils.getValue(cell));
                } else if (j == 2) {
                    activity.setEndDate(HSSFUtils.getValue(cell));
                } else if (j == 3) {
                    activity.setCost(HSSFUtils.getValue(cell));
                } else if (j == 4) {
                    activity.setDescription(HSSFUtils.getValue(cell));
                }
            }
            activities.add(activity);
        }
        int i = activityService.saveActivityByList(activities);
        ResultObject resultObject = new ResultObject();
        if (i > 0) {
            resultObject.setCode(constants.SUCCESS_CODE);
        } else {
            resultObject.setCode(constants.FAIL_CODE);
        }
        return resultObject;

    }
    @GetMapping("/workbench/activity/activityDetail.do")
    public String activityDetail(String id,HttpServletRequest request){
        Activity activity = activityService.queryActivityById(id);
        List<ActivityRemark> activityRemarks = activityRemarkService.queryActivityRemarkForDetailByActivityId(id);
        request.setAttribute("activity",activity);
        request.setAttribute("activityRemarks",activityRemarks);
        return "/workbench/activity/detail";
    }

    @PostMapping("/workbench/activityRemark/save")
    @ResponseBody
    public Object saveActivityRemark(ActivityRemark activityRemark,HttpSession session){
        User user = (User) session.getAttribute(constants.SESSION_USER);
        activityRemark.setCreateBy(user.getId());
        activityRemark.setCreateTime(DateFormat.dateFormatTime(new Date()));
        activityRemark.setId(uuid.getUUID());
        ResultObject resultObject = new ResultObject();
        try {
            int i = activityRemarkService.insert(activityRemark);
            if (i > 0) {
                resultObject.setCode(constants.SUCCESS_CODE);
            }else{
                resultObject.setCode(constants.FAIL_CODE);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultObject;
    }

    @PostMapping("/workbench/ActivityDetail/update")
    @ResponseBody
    public Object UpdateActivityRemark(ActivityRemark activityRemark,HttpSession session){
        User user = (User) session.getAttribute(constants.SESSION_USER);
        activityRemark.setEditFlag("1");
        activityRemark.setEditTime(DateFormat.dateFormatTime(new Date()));
        activityRemark.setEditBy(user.getId());
        ResultObject resultObject = new ResultObject();
        try {
            int i = activityRemarkService.updateByActivityRemarkId(activityRemark);
            if (i>0){
                resultObject.setCode(constants.SUCCESS_CODE);
            }else{
                resultObject.setCode(constants.FAIL_CODE);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultObject;
    }

}
