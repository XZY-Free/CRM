package com.XZY_SUNSHINE.crm.workbench.Tran.Service.Impl;

import com.XZY_SUNSHINE.crm.workbench.Tran.Service.TranRemarkService;
import com.XZY_SUNSHINE.crm.workbench.Tran.mapper.TranRemarkMapper;
import com.XZY_SUNSHINE.crm.workbench.Tran.pojo.TranRemark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TranRemarkServiceImpl implements TranRemarkService {
    @Autowired
    private TranRemarkMapper tranRemarkMapper;
    @Override
    public List<TranRemark> queryTranRemarkForDetailByTranId(String id) {
        return tranRemarkMapper.selectTranRemarkForDetailByTranId(id);
    }
}
