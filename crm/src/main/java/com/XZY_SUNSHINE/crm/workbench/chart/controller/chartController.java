package com.XZY_SUNSHINE.crm.workbench.chart.controller;

import com.XZY_SUNSHINE.crm.workbench.Tran.Service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class chartController {
    @Autowired
    private TranService tranService;
    @GetMapping("/workbench/tranChart/index.do")
    public String tranChartIndex(){
        return "workbench/chart/transaction/index";
    }
    @PostMapping("/workbench/chart/transaction/queryCountOfTranGroupByStage.do")
    @ResponseBody
    public Object queryCount(){
        return tranService.queryFunnelVOList();
    }
}
