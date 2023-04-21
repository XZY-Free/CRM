package com.XZY_SUNSHINE.crm.workbench.clue.service.impl;

import com.XZY_SUNSHINE.crm.workbench.clue.mapper.ClueActivityRelationMapper;
import com.XZY_SUNSHINE.crm.workbench.clue.pojo.ClueActivityRelation;
import com.XZY_SUNSHINE.crm.workbench.clue.service.clueActivityRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class clueActivityRelationServiceImpl implements clueActivityRelationService {
    @Autowired
    private ClueActivityRelationMapper clueActivityRelationMapper;

    @Override
    public int deleteClueActivityRelationById(String ActivityId, String clueId) {
        return clueActivityRelationMapper.deleteClueActivityRelation(ActivityId,clueId);
    }

    @Override
    public int saveRelationByList(List<ClueActivityRelation> clueActivityRelationList) {
        return clueActivityRelationMapper.insertRelationByList(clueActivityRelationList);
    }
}
