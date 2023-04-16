package com.XZY_SUNSHINE.crm.workbench.activity.service.Impl;

import com.XZY_SUNSHINE.crm.workbench.activity.mapper.ActivityMapper;
import com.XZY_SUNSHINE.crm.workbench.activity.pojo.Activity;
import com.XZY_SUNSHINE.crm.workbench.activity.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityMapper activityMapper;

    @Override
    public int deleteActivityByIds(String[] ids) {
        return activityMapper.deleteActivityByIds(ids);
    }

    @Override
    public int insert(Activity record) {
        return activityMapper.insert(record);
    }

    @Override
    public List<Activity> queryActivityByConditionForPage(Map map) {
        return activityMapper.selectActivityByConditionForPage(map);
    }

    @Override
    public int queryActivityByConditionForCounts(Map map) {
        return activityMapper.selectActivityByConditionForCounts(map);
    }
}
