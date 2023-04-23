package com.XZY_SUNSHINE.crm.workbench.Tran.Service;

import com.XZY_SUNSHINE.crm.workbench.Tran.pojo.FunnelVO;
import com.XZY_SUNSHINE.crm.workbench.Tran.pojo.Tran;

import java.util.List;
import java.util.Map;

public interface TranService {
    List<FunnelVO> queryFunnelVOList();
    Tran queryTranForDetailById(String id);
    void createTran(Map map);
}
