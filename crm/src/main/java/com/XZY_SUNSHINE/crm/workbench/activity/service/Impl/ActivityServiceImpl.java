package com.XZY_SUNSHINE.crm.workbench.activity.service.Impl;

import com.XZY_SUNSHINE.crm.workbench.activity.mapper.ActivityMapper;
import com.XZY_SUNSHINE.crm.workbench.activity.pojo.Activity;
import com.XZY_SUNSHINE.crm.workbench.activity.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityMapper activityMapper;
    @Override
    public int insert(Activity record) {
        return activityMapper.insert(record);
    }
}
