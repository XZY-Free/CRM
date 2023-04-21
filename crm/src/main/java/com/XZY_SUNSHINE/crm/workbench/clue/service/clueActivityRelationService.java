package com.XZY_SUNSHINE.crm.workbench.clue.service;

import com.XZY_SUNSHINE.crm.workbench.clue.pojo.ClueActivityRelation;

import java.util.List;

public interface clueActivityRelationService {
    int  deleteClueActivityRelationById(String ActivityId,String clueId);
    int saveRelationByList(List<ClueActivityRelation> clueActivityRelationList);
}
