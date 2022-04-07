package com.electrogrid.service;

import com.electrogrid.entity.User;

import javax.ws.rs.core.Response;

/**
 * Created By lonewol7f
 * Date: 4/6/2022
 */
public interface AuthServiceI {
    Response insertUser(User user);
    boolean isUserExist(String email);
}
