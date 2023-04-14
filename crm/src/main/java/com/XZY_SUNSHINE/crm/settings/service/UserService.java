package com.XZY_SUNSHINE.crm.settings.service;

import com.XZY_SUNSHINE.crm.settings.mapper.UserMapper;
import com.XZY_SUNSHINE.crm.settings.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<User> queryAllUsers();
    User queryUserByLoginActAndPwd(Map map);
}
