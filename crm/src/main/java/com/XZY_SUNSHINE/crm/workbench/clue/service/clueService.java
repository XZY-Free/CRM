package com.XZY_SUNSHINE.crm.workbench.clue.service;

import com.XZY_SUNSHINE.crm.workbench.clue.pojo.Clue;

import java.util.List;
import java.util.Map;

public interface clueService {
    void toConvert(Map map);
    Clue queryClueById(String id);
    int deleteClueByIds(String[] ids);
    int updateClue(Clue clue);
    Clue queryByPrimaryKey(String id);
    int insertClue(Clue clue);
    int queryCluesForPageCounts(Map map);
    List<Clue> queryCluesForPage(Map map);
}
