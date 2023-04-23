package com.XZY_SUNSHINE.crm.workbench.Tran.Service;

import com.XZY_SUNSHINE.crm.workbench.Tran.pojo.TranHistory;

import java.util.List;

public interface TranHistoryService {
    List<TranHistory> queryTranHistoryForDetailByTranId(String id);
}
