package com.XZY_SUNSHINE.crm.workbench.activity.service;

import com.XZY_SUNSHINE.crm.workbench.activity.pojo.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityService {
    List<Activity> queryAllActivitiesByIds(String[] ids);
    List<Activity> queryAllActivities();
    int updateActivityById(Activity activity);
    Activity queryActivityById(String id);
    int deleteActivityByIds(String[] ids);
    int insert(Activity record);
    List<Activity> queryActivityByConditionForPage(Map map);
    int queryActivityByConditionForCounts(Map map);
}
