package com.XZY_SUNSHINE.crm.workbench.clue.service;

import com.XZY_SUNSHINE.crm.workbench.clue.pojo.ClueRemark;

import java.util.List;

public interface clueRemarkService {
    int updateClueRemarkById(ClueRemark clueRemark);
    int deleteClueRemarkById(String id);
    int saveClueRemark(ClueRemark clueRemark);
    List<ClueRemark> queryClueRemarkByClueId(String id);
}
