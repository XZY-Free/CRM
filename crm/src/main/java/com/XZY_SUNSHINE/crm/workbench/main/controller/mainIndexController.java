package com.XZY_SUNSHINE.crm.workbench.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class mainIndexController {
    @GetMapping("/workbench/main/index.do")
    public String MainIndex(){
        return "workbench/main/index";
    }
}
