package com.XZY_SUNSHINE.crm.workbench.clue.service.impl;

import com.XZY_SUNSHINE.crm.workbench.clue.mapper.ClueMapper;
import com.XZY_SUNSHINE.crm.workbench.clue.pojo.Clue;
import com.XZY_SUNSHINE.crm.workbench.clue.service.clueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class clueServiceImpl implements clueService {
    @Autowired
    private ClueMapper clueMapper;

    @Override
    public int insertClue(Clue clue) {
        return clueMapper.insert(clue);
    }

    @Override
    public int queryCluesForPageCounts(Map map) {
        return clueMapper.selectCluesForPageCounts(map);
    }

    @Override
    public List<Clue> queryCluesForPage(Map map) {
        return clueMapper.selectCluesForPage(map);
    }
}
