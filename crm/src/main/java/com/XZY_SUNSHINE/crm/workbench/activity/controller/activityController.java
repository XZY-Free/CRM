package com.XZY_SUNSHINE.crm.workbench.activity.controller;

import com.XZY_SUNSHINE.crm.commons.pojo.ResultObject;
import com.XZY_SUNSHINE.crm.commons.utils.DateFormat;
import com.XZY_SUNSHINE.crm.commons.utils.constants;
import com.XZY_SUNSHINE.crm.commons.utils.uuid;
import com.XZY_SUNSHINE.crm.settings.pojo.User;
import com.XZY_SUNSHINE.crm.settings.service.UserService;
import com.XZY_SUNSHINE.crm.workbench.activity.pojo.Activity;
import com.XZY_SUNSHINE.crm.workbench.activity.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
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

}
