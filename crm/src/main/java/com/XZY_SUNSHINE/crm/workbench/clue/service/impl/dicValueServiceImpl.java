package com.XZY_SUNSHINE.crm.workbench.clue.service.impl;

import com.XZY_SUNSHINE.crm.workbench.clue.mapper.DicValueMapper;
import com.XZY_SUNSHINE.crm.workbench.clue.pojo.DicValue;
import com.XZY_SUNSHINE.crm.workbench.clue.service.dicValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class dicValueServiceImpl implements dicValueService {
    @Autowired
    private DicValueMapper dicValueMapper;
    @Override
    public List<DicValue> queryDicValueByTypeCode(String appellation) {
        return dicValueMapper.selectDicValueByTypeCode(appellation);
    }
}
