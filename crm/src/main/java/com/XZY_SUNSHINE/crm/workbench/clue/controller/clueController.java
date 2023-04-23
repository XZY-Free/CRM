package com.XZY_SUNSHINE.crm.workbench.clue.controller;

import com.XZY_SUNSHINE.crm.commons.pojo.ResultObject;
import com.XZY_SUNSHINE.crm.commons.utils.DateFormat;
import com.XZY_SUNSHINE.crm.commons.utils.constants;
import com.XZY_SUNSHINE.crm.commons.utils.uuid;
import com.XZY_SUNSHINE.crm.settings.pojo.User;
import com.XZY_SUNSHINE.crm.settings.service.UserService;
import com.XZY_SUNSHINE.crm.workbench.activity.pojo.Activity;
import com.XZY_SUNSHINE.crm.workbench.activity.service.ActivityService;
import com.XZY_SUNSHINE.crm.workbench.clue.pojo.Clue;
import com.XZY_SUNSHINE.crm.workbench.clue.pojo.ClueActivityRelation;
import com.XZY_SUNSHINE.crm.workbench.clue.pojo.ClueRemark;
import com.XZY_SUNSHINE.crm.workbench.clue.pojo.DicValue;
import com.XZY_SUNSHINE.crm.workbench.clue.service.clueActivityRelationService;
import com.XZY_SUNSHINE.crm.workbench.clue.service.clueRemarkService;
import com.XZY_SUNSHINE.crm.workbench.clue.service.clueService;
import com.XZY_SUNSHINE.crm.workbench.clue.service.dicValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class clueController {

    @Autowired
    private UserService userService;
    @Autowired
    private clueService clueService;
    @Autowired
    private dicValueService dicValueService;
    @Autowired
    private clueRemarkService clueRemarkService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private clueActivityRelationService clueActivityRelationService;
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


    @PostMapping("/workbench/clue/selectClueById.do")
    @ResponseBody
    public Clue selectById(String id){
        return clueService.queryByPrimaryKey(id);
    }

    @PostMapping("/workbench/clue/updateClueById.do")
    @ResponseBody
    public Object updateClueById(Clue clue,HttpSession session){
        User user = (User) session.getAttribute(constants.SESSION_USER);
        clue.setEditBy(user.getId());
        clue.setEditTime(DateFormat.dateFormatTime(new Date()));
        ResultObject resultObject = new ResultObject();
        try{
            int i = clueService.updateClue(clue);
            if (i>0){
                resultObject.setCode(constants.SUCCESS_CODE);
            }else{
                resultObject.setMessage(constants.FAIL_CODE);
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return  resultObject;
    }

    @PostMapping("/workbench/clue/deleteByIds.do")
    @ResponseBody
    public Object deleteClueByIds(String[] ids){
        ResultObject resultObject = new ResultObject();
        try{
            int i = clueService.deleteClueByIds(ids);
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

    @GetMapping("/workbench/clueDetail/index.do")
    public String clueDetailIndex(String id,HttpServletRequest request){
        Clue clue = clueService.queryClueById(id);
        List<ClueRemark> clueRemarkList = clueRemarkService.queryClueRemarkByClueId(id);
        List<Activity> activityList = activityService.queryActivityByClueId(id);
        request.setAttribute("activityList",activityList);
        request.setAttribute("clueRemarkList",clueRemarkList);
        request.setAttribute("clue",clue);
        return "workbench/clue/detail";
    }

    @PostMapping("/workbench/clueRemark/save")
    @ResponseBody
    public Object saveClueRemark(ClueRemark clueRemark,HttpSession session){
        User user = (User) session.getAttribute(constants.SESSION_USER);
        clueRemark.setId(uuid.getUUID());
        clueRemark.setCreateBy(user.getId());
        clueRemark.setCreateTime(DateFormat.dateFormatTime(new Date()));
        ResultObject resultObject = new ResultObject();
        try{
            int i = clueRemarkService.saveClueRemark(clueRemark);
            if (i>0){
                resultObject.setCode(constants.SUCCESS_CODE);
            }else{
                resultObject.setCode(constants.FAIL_CODE);
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return resultObject;
    }

    @PostMapping("/workbench/clueRemark/delete")
    @ResponseBody
    public Object deleteClueRemarkById(String id){
        ResultObject resultObject = new ResultObject();
        try{
            int i = clueRemarkService.deleteClueRemarkById(id);
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

    @PostMapping("/workbench/clueRemark/update")
    @ResponseBody
    public Object updateClueRemark(ClueRemark clueRemark,HttpSession session){
        User user = (User) session.getAttribute(constants.SESSION_USER);
        clueRemark.setEditBy(user.getId());
        clueRemark.setEditTime(DateFormat.dateFormatTime(new Date()));
        clueRemark.setEditFlag("1");
        ResultObject resultObject = new ResultObject();
        try{
            int i = clueRemarkService.updateClueRemarkById(clueRemark);
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


    @PostMapping("/workbench/clueDetail/selectActivityLikeName.do")
    @ResponseBody
    public Object selectActivityLikeName(String name,String id){
        return activityService.queryActivityLikeName(name,id);
    }

    @PostMapping("/workbench/clueActivityRelation/save")
    @ResponseBody
    public Object saveClueActivityRelation(String clueId,String[] activityIds){
        List<ClueActivityRelation> clueActivityRelationList = new ArrayList<>();
        for (int i = 0; i < activityIds.length; i++) {
            ClueActivityRelation clueActivityRelation = new ClueActivityRelation();
            clueActivityRelation.setId(uuid.getUUID());
            clueActivityRelation.setActivityId(activityIds[i]);
            clueActivityRelation.setClueId(clueId);
            clueActivityRelationList.add(clueActivityRelation);
        }
        ResultObject resultObject = new ResultObject();
        try{
            int i = clueActivityRelationService.saveRelationByList(clueActivityRelationList);
            if (i>0){
                resultObject.setCode(constants.SUCCESS_CODE);
            }else{
                resultObject.setCode(constants.FAIL_CODE);
            }
        }catch (Exception e ){
            e.printStackTrace();
        }

        return resultObject;

    }


    @PostMapping("/workbench/clueDetail/cancelBind.do")
    @ResponseBody
    public Object cancelBind(String activityId,String clueId){
        ResultObject resultObject = new ResultObject();
        try{
            int i = clueActivityRelationService.deleteClueActivityRelationById(activityId, clueId);
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


    @GetMapping("/workbench/convert/index.do")
    public String convertIndex(String id,HttpServletRequest request){
        Clue clue = clueService.queryClueById(id);
        List<DicValue> stageList = dicValueService.queryDicValueByTypeCode("stage");
        request.setAttribute("clue",clue);
        request.setAttribute("stageList",stageList);
        return "workbench/clue/convert";
    }

    @PostMapping("/workbench/convert/selectActivity")
    @ResponseBody
    public List<Activity> selectActivity(String name,String id){
        return activityService.queryActivityLikeNameForConvert(name,id);
    }
    @PostMapping("/workbench/convert/selectActivityByName")
    @ResponseBody
    public List<Activity> selectActivityByName(String name){
        return activityService.queryActivityByName(name);
    }

    @PostMapping("/workbench/convert/toConvert")
    @ResponseBody
    public Object toConvert(String clueId,String isCreate,String money,
                            String tradeName, String expectedTime,
                            String stage, String activityId,HttpSession session){
        ResultObject resultObject = new ResultObject();
        User user = (User) session.getAttribute(constants.SESSION_USER);
        Map<String, Object> map = new HashMap<>();
        map.put("clueId",clueId);
        map.put("isCreate",isCreate);
        map.put("money",money);
        map.put("tradeName",tradeName);
        map.put("expectedTime",expectedTime);
        map.put("stage",stage);
        map.put("activityId",activityId);
        map.put(constants.SESSION_USER,user);
        try{
            clueService.toConvert(map);
            resultObject.setCode(constants.SUCCESS_CODE);
        }catch (Exception e){
            e.printStackTrace();
            resultObject.setCode(constants.FAIL_CODE);
        }
        return resultObject;

    }
}
