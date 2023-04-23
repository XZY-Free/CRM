package com.XZY_SUNSHINE.crm.workbench.Tran.Service.Impl;

import com.XZY_SUNSHINE.crm.workbench.Tran.Service.TranHistoryService;
import com.XZY_SUNSHINE.crm.workbench.Tran.mapper.TranHistoryMapper;
import com.XZY_SUNSHINE.crm.workbench.Tran.pojo.TranHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TranHistoryServiceImpl implements TranHistoryService {
    @Autowired
    private TranHistoryMapper tranHistoryMapper;
    @Override
    public List<TranHistory> queryTranHistoryForDetailByTranId(String id) {
        return tranHistoryMapper.selectTranHistoryForDetailByTranId(id);
    }
}
