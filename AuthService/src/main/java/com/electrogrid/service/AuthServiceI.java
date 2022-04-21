package com.electrogrid.service;

import com.electrogrid.entity.Credentials;
import com.electrogrid.entity.User;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;

/**
 * Created By lonewol7f
 * Date: 4/11/2022
 */
public interface AuthServiceI {
    Response insertUser(User user);
    Response login(Credentials credentials);
    Response updateUserRole(User user);
    Response deleteUser(int id);
    Object getUserById(int id);
    boolean isUserExist(String email);
    boolean isUserExistById(int id);
    Response validate(ContainerRequestContext containerRequestContext);
}
