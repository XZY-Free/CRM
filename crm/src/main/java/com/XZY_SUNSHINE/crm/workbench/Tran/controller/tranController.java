package com.XZY_SUNSHINE.crm.workbench.Tran.controller;

import com.XZY_SUNSHINE.crm.commons.pojo.ResultObject;
import com.XZY_SUNSHINE.crm.commons.utils.constants;
import com.XZY_SUNSHINE.crm.settings.pojo.User;
import com.XZY_SUNSHINE.crm.settings.service.UserService;
import com.XZY_SUNSHINE.crm.workbench.Contacts.Service.ContactsService;
import com.XZY_SUNSHINE.crm.workbench.Customer.Service.CustomerService;
import com.XZY_SUNSHINE.crm.workbench.Tran.Service.TranService;
import com.XZY_SUNSHINE.crm.workbench.clue.pojo.DicValue;
import com.XZY_SUNSHINE.crm.workbench.clue.service.dicValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

@Controller
public class tranController {
    @Autowired
    private TranService tranService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ContactsService contactsService;
    @Autowired
    private UserService userService;
    @Autowired
    private dicValueService dicValueService;
    @GetMapping("/workbench/tran/index.do")
    public String tranIndex(){
        return "workbench/transaction/index";
    }
    @GetMapping("/workbench/tran/toCreate.do")
    public String createTran(HttpServletRequest request){
        List<User> userList = userService.queryAllUsers();
        List<DicValue> stageList = dicValueService.queryDicValueByTypeCode("stage");
        List<DicValue> transactionTypeList = dicValueService.queryDicValueByTypeCode("transactionType");
        List<DicValue> sourceList = dicValueService.queryDicValueByTypeCode("source");
        request.setAttribute("userList",userList);
        request.setAttribute("stageList",stageList);
        request.setAttribute("transactionTypeList",transactionTypeList);
        request.setAttribute("sourceList",sourceList);
        return "workbench/transaction/save";

    }
    @PostMapping("/workbench/tran/selectContactsByName")
    @ResponseBody
    public Object selectContacts(String name){
        return contactsService.queryContactsByName(name);
    }
    @PostMapping("/workbench/tran/stagePossibility")
    @ResponseBody
    public Object getPossibility(String stage){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("possibility");
        return resourceBundle.getString(stage);
    }

    @PostMapping("/workbench/tran/searchCustomerByName")
    @ResponseBody
    public List<String> searchCustomerByName(String name){
        System.out.println(name);
        return customerService.queryCustomerByName(name);
    }

    @PostMapping("/workbench/tran/createTran.do")
    @ResponseBody
    public Object toCreateTran(@RequestParam Map map, HttpSession session){
        User user = (User) session.getAttribute(constants.SESSION_USER);
        map.put(constants.SESSION_USER,user);
        ResultObject resultObject = new ResultObject();
        try{
            tranService.createTran(map);
            resultObject.setCode(constants.SUCCESS_CODE);
        }catch (Exception e){
            e.printStackTrace();
            resultObject.setCode(constants.FAIL_CODE);
        }
        return resultObject;
    }
}
