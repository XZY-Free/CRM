package com.XZY_SUNSHINE.crm.workbench.clue.service.impl;

import com.XZY_SUNSHINE.crm.workbench.clue.pojo.ClueRemark;
import com.XZY_SUNSHINE.crm.workbench.clue.service.clueRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.XZY_SUNSHINE.crm.workbench.clue.mapper.ClueRemarkMapper;

import java.util.List;

@Service
public class clueRemarkServiceImpl implements clueRemarkService {
    @Autowired
    private ClueRemarkMapper clueRemarkMapper;
    @Override
    public List<ClueRemark> queryClueRemarkByClueId(String id) {
        return clueRemarkMapper.selectClueRemarkByClueId(id);
    }
}
