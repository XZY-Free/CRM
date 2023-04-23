package com.XZY_SUNSHINE.crm.workbench.Tran.Service;

import com.XZY_SUNSHINE.crm.workbench.Tran.pojo.Tran;

import java.util.Map;

public interface TranService {
    Tran queryTranForDetailById(String id);
    void createTran(Map map);
}
