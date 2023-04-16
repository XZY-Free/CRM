package com.XZY_SUNSHINE.crm.workbench.activity.service;

import com.XZY_SUNSHINE.crm.workbench.activity.pojo.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityService {
    int deleteActivityByIds(String[] ids);
    int insert(Activity record);
    List<Activity> queryActivityByConditionForPage(Map map);
    int queryActivityByConditionForCounts(Map map);
}
