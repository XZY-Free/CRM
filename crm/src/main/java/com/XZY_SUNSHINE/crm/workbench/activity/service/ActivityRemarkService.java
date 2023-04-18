package com.XZY_SUNSHINE.crm.workbench.activity.service;

import com.XZY_SUNSHINE.crm.workbench.activity.pojo.ActivityRemark;

import java.util.List;

public interface ActivityRemarkService {
    int updateByActivityRemarkId(ActivityRemark activityRemark);
    int insert(ActivityRemark activityRemark);
    List<ActivityRemark> queryActivityRemarkForDetailByActivityId(String id);
}
