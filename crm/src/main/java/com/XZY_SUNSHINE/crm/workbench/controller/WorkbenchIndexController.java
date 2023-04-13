package com.XZY_SUNSHINE.crm.workbench.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WorkbenchIndexController {
    @GetMapping("/workbench/index")
    public String index(){
        return "workbench/index";
    }
    @GetMapping("/workbench/main/index.do")
    public String MainIndex(){
        return "workbench/main/index";
    }
}
