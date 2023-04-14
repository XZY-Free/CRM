package com.XZY_SUNSHINE.crm.settings.service.Impl;

import com.XZY_SUNSHINE.crm.settings.mapper.UserMapper;
import com.XZY_SUNSHINE.crm.settings.pojo.User;
import com.XZY_SUNSHINE.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper usermapper;

    @Override
    public List<User> queryAllUsers() {
        return usermapper.selectAllUsers();
    }

    @Override
    public User queryUserByLoginActAndPwd(Map map) {
        return usermapper.selectUserByLoginActAndPwd(map);
    }
}
