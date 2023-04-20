package com.XZY_SUNSHINE.crm.workbench.clue.service;

import com.XZY_SUNSHINE.crm.workbench.clue.pojo.ClueRemark;

import java.util.List;

public interface clueRemarkService {
    List<ClueRemark> queryClueRemarkByClueId(String id);
}
