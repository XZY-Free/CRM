package com.XZY_SUNSHINE.crm.workbench.clue.service;

import com.XZY_SUNSHINE.crm.workbench.clue.pojo.DicValue;

import java.util.List;

public interface dicValueService {
    List<DicValue> queryDicValueByTypeCode(String appellation);
}
