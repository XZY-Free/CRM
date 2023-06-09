package com.XZY_SUNSHINE.crm.workbench.activity.service.Impl;

import com.XZY_SUNSHINE.crm.workbench.activity.mapper.ActivityRemarkMapper;
import com.XZY_SUNSHINE.crm.workbench.activity.pojo.ActivityRemark;
import com.XZY_SUNSHINE.crm.workbench.activity.service.ActivityRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ActivityRemarkServiceImpl implements ActivityRemarkService {
    @Autowired
    private ActivityRemarkMapper activityRemarkMapper;

    @Override
    public int deleteRemarkById(String id) {
        return activityRemarkMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateByActivityRemarkId(ActivityRemark activityRemark) {
        return activityRemarkMapper.updateByPrimaryKeySelective(activityRemark);
    }

    @Override
    public int insert(ActivityRemark activityRemark) {
        return activityRemarkMapper.insert(activityRemark);
    }

    @Override
    public List<ActivityRemark> queryActivityRemarkForDetailByActivityId(String id) {
        return activityRemarkMapper.selectActivityRemarkForDetailByActivityId(id);
    }
}
