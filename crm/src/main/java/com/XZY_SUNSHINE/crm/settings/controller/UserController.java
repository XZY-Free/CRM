package com.XZY_SUNSHINE.crm.settings.controller;

import com.XZY_SUNSHINE.crm.commons.pojo.ResultObject;
import com.XZY_SUNSHINE.crm.commons.utils.DateFormat;
import com.XZY_SUNSHINE.crm.commons.utils.constants;
import com.XZY_SUNSHINE.crm.settings.pojo.User;
import com.XZY_SUNSHINE.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/settings/qx/user/toLogin.do")
    public String toLogin(){
        return "settings/qx/user/login";
    }
    @PostMapping("/settings/qx/user/login.do")
    @ResponseBody
    public Object login(String loginAct, String loginPwd, String isRem, HttpServletRequest request, HttpSession session, HttpServletResponse response){
        Map<String, Object> map = new HashMap<>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);
        User user = userService.queryUserByLoginActAndPwd(map);
        ResultObject result = new ResultObject();
        if (user==null){
            //返回用户名或者密码错误
            result.setCode(constants.FAIL_CODE);
            result.setMessage("用户名或者密码错误!");
        }else{
//            用户已过期
            if (user.getExpireTime().compareTo(DateFormat.dateFormatTime(new Date()))<0){
                result.setCode(constants.FAIL_CODE);
                result.setMessage("用户已过期");
            } else if ("0".equals(user.getLockState())) {
//                用户状态被锁定,
                result.setCode(constants.FAIL_CODE);
                result.setMessage("用户状态被锁定，请联系管理员解除。");
//                ip受限
            } else if (!user.getAllowIps().contains(request.getRemoteAddr())) {
                result.setCode(constants.FAIL_CODE);
                result.setMessage("IP受限！");
            }else{
                // *登录成功之后,所有业务页面显示当前用户的名称
                // *实现10天记住密码
                session.setAttribute(constants.SESSION_USER,user);
                Cookie cookie1 = new Cookie("loginAct", user.getLoginAct());
                Cookie cookie2 = new Cookie("loginPwd", user.getLoginPwd());
                if ("true".equals(isRem)) {
                    cookie1.setMaxAge(60 * 60 * 24 * 10);
                    cookie2.setMaxAge(60 * 60 * 24 * 10);
                }else {
                    cookie1.setMaxAge(0);
                    cookie2.setMaxAge(0);
                }
                response.addCookie(cookie1);
                response.addCookie(cookie2);
                result.setCode(constants.SUCCESS_CODE);
                result.setMessage("登陆成功！");
            }

        }
        return result;
    }


    //实现安全退出功能
    @GetMapping("/settings/qx/user/logout.do")
    public String logout(HttpServletResponse response,HttpSession session){
        Cookie cookie1 = new Cookie("loginAct", "1");
        Cookie cookie2 = new Cookie("loginPwd", "1");
        cookie1.setMaxAge(0);
        cookie2.setMaxAge(0);
        response.addCookie(cookie1);
        response.addCookie(cookie2);
        session.invalidate();
        return "redirect:/";
    }
}
