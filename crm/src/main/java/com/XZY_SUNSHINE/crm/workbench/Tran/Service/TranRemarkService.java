package com.XZY_SUNSHINE.crm.workbench.Tran.Service;

import com.XZY_SUNSHINE.crm.workbench.Tran.pojo.TranRemark;

import java.util.List;

public interface TranRemarkService {
    List<TranRemark> queryTranRemarkForDetailByTranId(String id);
}
