package com.XZY_SUNSHINE.crm.workbench.clue.service;

import com.XZY_SUNSHINE.crm.workbench.clue.pojo.Clue;

import java.util.List;
import java.util.Map;

public interface clueService {
    int insertClue(Clue clue);
    int queryCluesForPageCounts(Map map);
    List<Clue> queryCluesForPage(Map map);
}
