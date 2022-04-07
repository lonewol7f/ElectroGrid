package com.electrogrid.service;

import com.electrogrid.entity.Credentials;
import com.electrogrid.entity.User;

import javax.ws.rs.core.Response;

/**
 * Created By lonewol7f
 * Date: 4/6/2022
 */
public interface AuthServiceI {
    Response insertUser(User user);
    Response login(Credentials credentials);
    boolean isUserExist(String email);
}
