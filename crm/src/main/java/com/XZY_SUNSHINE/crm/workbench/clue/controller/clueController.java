package com.XZY_SUNSHINE.crm.workbench.clue.controller;

import com.XZY_SUNSHINE.crm.commons.pojo.ResultObject;
import com.XZY_SUNSHINE.crm.commons.utils.DateFormat;
import com.XZY_SUNSHINE.crm.commons.utils.constants;
import com.XZY_SUNSHINE.crm.commons.utils.uuid;
import com.XZY_SUNSHINE.crm.settings.pojo.User;
import com.XZY_SUNSHINE.crm.settings.service.UserService;
import com.XZY_SUNSHINE.crm.workbench.clue.pojo.Clue;
import com.XZY_SUNSHINE.crm.workbench.clue.pojo.DicValue;
import com.XZY_SUNSHINE.crm.workbench.clue.service.clueService;
import com.XZY_SUNSHINE.crm.workbench.clue.service.dicValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class clueController {

    @Autowired
    private UserService userService;
    @Autowired
    private clueService clueService;
    @Autowired
    private dicValueService dicValueService;
    @GetMapping("/workbench/clue/index.do")
    public String index(HttpServletRequest request){
        List<User> userList = userService.queryAllUsers();
        List<DicValue> appellationList = dicValueService.queryDicValueByTypeCode("appellation");
        List<DicValue> clueStateList = dicValueService.queryDicValueByTypeCode("clueState");
        List<DicValue> sourceList = dicValueService.queryDicValueByTypeCode("source");
        request.setAttribute("userList",userList);
        request.setAttribute("appellationList",appellationList);
        request.setAttribute("clueStateList",clueStateList);
        request.setAttribute("sourceList",sourceList);
        return "workbench/clue/index";
    }

    @PostMapping("/workbench/clue/queryClueForPage.do")
    @ResponseBody
    public Object queryCluesForPage(String fullname,String owner,
                                    String phone,String mphone,
                                    String company,String state,
                                    String source, int pageNo,
                                    int pageSize){

        Map<String, Object> map = new HashMap<>();
        map.put("fullname",fullname);
        map.put("owner",owner);
        map.put("phone",phone);
        map.put("company",company);
        map.put("state",state);
        map.put("source",source);
        map.put("mphone",mphone);
        map.put("beginNo",(pageNo-1)*pageSize);
        map.put("pageSize",pageSize);
        List<Clue> clueList = clueService.queryCluesForPage(map);
        int counts = clueService.queryCluesForPageCounts(map);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("clueList",clueList);
        resultMap.put("totalCounts",counts);
        return  resultMap;
    }

    @PostMapping("/workbench/clue/save.do")
    @ResponseBody
    public Object saveClue(Clue clue, HttpSession session){
        User user = (User) session.getAttribute(constants.SESSION_USER);
        clue.setId(uuid.getUUID());
        clue.setCreateBy(user.getId());
        clue.setCreateTime(DateFormat.dateFormatTime(new Date()));
        ResultObject resultObject = new ResultObject();
        try{
            int i = clueService.insertClue(clue);
            if (i>0){
                resultObject.setCode(constants.SUCCESS_CODE);
            }else{
                resultObject.setCode(constants.FAIL_CODE);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultObject;


    }
}
